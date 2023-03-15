package com.jlkj.human.hm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.dto.human.hm.OrganizationDTO;
import com.jlkj.human.hm.domain.Organization;
import com.jlkj.human.hm.domain.PerorgTree;
import com.jlkj.human.hm.domain.Personnel;
import com.jlkj.human.hm.dto.OrganizationTreeDTO;
import com.jlkj.human.hm.mapper.OrganizationMapper;
import com.jlkj.human.hm.mapper.PersonnelMapper;
import com.jlkj.human.hm.service.IOrganizationService;
import com.jlkj.human.hm.service.IPerorgTreeService;
import lombok.Cleanup;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 机构数据 服务实现类
 * </p>
 *
 * @author xin
 * @since 2022-05-05
 */
@Service
public class OrganizationServiceImpl extends ServiceImpl<OrganizationMapper, Organization> implements IOrganizationService {

    private static final String DEPT = "dept";
    private static final String USER = "user";
    private static final String DEPT_ICON = "el-icon-self-bumenguanli";
    private static final String USER_ICON = "el-icon-self-nvxing";
    @Autowired
    private PersonnelMapper personnelMapper;
    @Autowired
    private OrganizationMapper organizationMapper;
    @Autowired
    private IPerorgTreeService humanresourcePerorgTreeService;
    @Autowired
    JedisPool jedisPool;

    /**
     * 新增机构资料
     */
    @Override
    public void saveData(OrganizationDTO organizationDTO) {
        Organization organization = new Organization();
        BeanUtils.copyProperties(organizationDTO, organization);
        baseMapper.insert(organization);
        @Cleanup Jedis jedis = jedisPool.getResource();
        jedis.unlink("system-department-tree");
        // 同步至组织机构树资料
        PerorgTree perorgTree = new PerorgTree();
        perorgTree.setId(organizationDTO.getId());
        perorgTree.setParentId(organizationDTO.getParentId());
        perorgTree.setFlag(DEPT);
        perorgTree.setIcon(DEPT_ICON);
        perorgTree.setLabel(organizationDTO.getOrganizationName());
        perorgTree.setParentName(organizationDTO.getParentName());
        humanresourcePerorgTreeService.save(perorgTree);
    }

    /**
     * 修改机构资料
     * @param organizationDTO
     * @return
     */
    @Override
    public void updateData(OrganizationDTO organizationDTO) {
        Organization humanresourceOrganization = new Organization();
        BeanUtils.copyProperties(organizationDTO,humanresourceOrganization);
        PerorgTree perorgTree = new PerorgTree();
        perorgTree.setId(humanresourceOrganization.getId());
        perorgTree.setParentId(humanresourceOrganization.getParentId());
        perorgTree.setFlag(DEPT);
        perorgTree.setIcon(DEPT_ICON);
        perorgTree.setParentName(humanresourceOrganization.getParentName());
        perorgTree.setLabel(humanresourceOrganization.getOrganizationName());
        Organization organization = baseMapper.selectById(humanresourceOrganization.getId());
        if (StringUtils.isNotNull(organization)) {
            baseMapper.updateById(humanresourceOrganization);
            humanresourcePerorgTreeService.updateById(perorgTree);
        } else {
           baseMapper.insert(humanresourceOrganization);
           humanresourcePerorgTreeService.save(perorgTree);
        }
        @Cleanup Jedis jedis = jedisPool.getResource();
        jedis.unlink("system-department-tree");
    }

    /**
     * 删除机构资料
     * @param organizationDTO
     * @return
     */
    @Override
    public void removeData(OrganizationDTO organizationDTO) {
        Organization organization = new Organization();
        BeanUtils.copyProperties(organizationDTO, organization);
        baseMapper.deleteById(organization);
        humanresourcePerorgTreeService.removeById(organization.getId());
        @Cleanup Jedis jedis = jedisPool.getResource();
        jedis.unlink("system-department-tree");
    }

    /**
     * 组机构树结构列表
     * @return
     */
    @Override
    public List<OrganizationTreeDTO> getOrganizationTreeList() {
        List<OrganizationTreeDTO> returnList = new ArrayList<OrganizationTreeDTO>();
        List<String> tempList = new ArrayList<>();

        //合并人员资料和机构资料
        List<OrganizationTreeDTO> humanresourceOrganizationTreeList = new ArrayList<>();
        humanresourceOrganizationTreeList.addAll(mergePersonnelList());
        humanresourceOrganizationTreeList.addAll(mergeOrganizationList());
        //循环取出id值，存入tempList
        if (StringUtils.isNotNull(humanresourceOrganizationTreeList)) {
            for (OrganizationTreeDTO organization : humanresourceOrganizationTreeList) {
                tempList.add(organization.getId());
            }
        }
        for (Iterator<OrganizationTreeDTO> iterator = humanresourceOrganizationTreeList.iterator(); iterator.hasNext();) {
            OrganizationTreeDTO organization = (OrganizationTreeDTO) iterator.next();
            //如果是Root节点，遍历该父节点的所有子节点
            if (!tempList.contains(organization.getParentId())) {
                //进行递归操作
                recursionFn(humanresourceOrganizationTreeList,organization);
                returnList.add(organization);
            }
        }
        if (returnList.isEmpty()) {
            returnList = humanresourceOrganizationTreeList;
        }
        return returnList.stream().map(OrganizationTreeDTO::new).collect(Collectors.toList());
    }



    /**
     * 处理人员资料
     * @return
     */
    public List<OrganizationTreeDTO> mergePersonnelList() {
        //查询出所有人员资料
        List<Personnel> personnelList = personnelMapper.listAll();
        List<OrganizationTreeDTO> personnelCollect = new ArrayList<>();
        //采用流的方式获取人员资料信息
        if(StringUtils.isNotNull(personnelList)) {
            personnelCollect = personnelList.stream().map(item -> {
                OrganizationTreeDTO treeDTO = new OrganizationTreeDTO();
                BeanUtils.copyProperties(item, treeDTO);
                //标注数据为人员信息
                treeDTO.setFlag(USER);
                treeDTO.setIcon(USER_ICON);
                treeDTO.setParentId(item.getAlternateField14());
                treeDTO.setParentName(item.getDepartmentName());
                treeDTO.setLabel(item.getFullName());
                return treeDTO;
            }).collect(Collectors.toList());
        }
        return personnelCollect;
    }
    /**
     * 处理机构资料
     * @return
     */
    public List<OrganizationTreeDTO> mergeOrganizationList() {
        //查询出所有机构数据
        List<Organization> organizations = organizationMapper.listAll();
        List<OrganizationTreeDTO> organizationCollect = new ArrayList<>();
        //处理获取机构资料信息
        if (StringUtils.isNotNull(organizations)) {
            organizationCollect = organizations.stream().map(item -> {
                OrganizationTreeDTO treeDTO = new OrganizationTreeDTO();
                BeanUtils.copyProperties(item, treeDTO);
                //标注数据为机构信息
                treeDTO.setFlag(DEPT);
                treeDTO.setIcon(DEPT_ICON);
                treeDTO.setLabel(item.getOrganizationName());
                return treeDTO;
            }).collect(Collectors.toList());
        }
        return organizationCollect;
    }

    /**
     * 递归列表
     * @param list
     * @param organization
     */
    private void recursionFn(List<OrganizationTreeDTO> list, OrganizationTreeDTO organization) {
        //得到子节点列表
        List<OrganizationTreeDTO> childList = getChildList(list,organization);
        organization.setChildren(childList);
        for (OrganizationTreeDTO orgChild : childList) {
            if (hasChild(list, orgChild)) {
                recursionFn(list, orgChild);
            }
        }
    }

    /**
     * 得到子节点列表
     * @param list
     * @param organization
     * @return
     */
    private List<OrganizationTreeDTO> getChildList(List<OrganizationTreeDTO> list, OrganizationTreeDTO organization) {
        List<OrganizationTreeDTO> orgList = new ArrayList<OrganizationTreeDTO>();
        //声明迭代器
        Iterator<OrganizationTreeDTO> iterator = list.iterator();
        while (iterator.hasNext()) {
            OrganizationTreeDTO humanOrg = (OrganizationTreeDTO) iterator.next();
            //判断当前迭代对象的夫ID是否等于传入的ID
            if (StringUtils.isNotNull(humanOrg.getParentId()) && humanOrg.getParentId().equals(organization.getId())) {
                orgList.add(humanOrg);
            }
        }
        return orgList;
    }

    /**
     * 判断是否有子节点
     * @param list
     * @param organization
     * @return
     */
    private boolean hasChild(List<OrganizationTreeDTO> list, OrganizationTreeDTO organization) {
        return getChildList(list,organization).size() > 0;
    }
}
