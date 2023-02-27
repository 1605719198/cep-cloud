package com.jlkj.human.hm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.dto.human.hm.HumanresourceOrganizationDTO;
import com.jlkj.human.hm.domain.HumanresourceOrganization;
import com.jlkj.human.hm.domain.HumanresourcePerorgTree;
import com.jlkj.human.hm.domain.HumanresourcePersonnel;
import com.jlkj.human.hm.dto.HumanresourceOrganizationTreeDTO;
import com.jlkj.human.hm.mapper.HumanresourceOrganizationMapper;
import com.jlkj.human.hm.mapper.HumanresourcePersonnelMapper;
import com.jlkj.human.hm.service.IHumanresourceOrganizationService;
import com.jlkj.human.hm.service.IHumanresourcePerorgTreeService;
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
public class HumanresourceOrganizationServiceImpl extends ServiceImpl<HumanresourceOrganizationMapper, HumanresourceOrganization> implements IHumanresourceOrganizationService {

    private static final String DEPT = "dept";
    private static final String USER = "user";
    private static final String DEPT_ICON = "el-icon-self-bumenguanli";
    private static final String USER_ICON = "el-icon-self-nvxing";
    @Autowired
    private HumanresourcePersonnelMapper humanresourcePersonnelMapper;
    @Autowired
    private HumanresourceOrganizationMapper humanresourceOrganizationMapper;
    @Autowired
    private IHumanresourcePerorgTreeService humanresourcePerorgTreeService;
    @Autowired
    JedisPool jedisPool;

    /**
     * 新增机构资料
     */
    @Override
    public void saveData(HumanresourceOrganizationDTO humanresourceOrganizationDTO) {
        HumanresourceOrganization humanresourceOrganization = new HumanresourceOrganization();
        BeanUtils.copyProperties(humanresourceOrganizationDTO,humanresourceOrganization);
        baseMapper.insert(humanresourceOrganization);
        @Cleanup Jedis jedis = jedisPool.getResource();
        jedis.unlink("system-department-tree");
        // 同步至组织机构树资料
        HumanresourcePerorgTree humanresourcePerorgTree = new HumanresourcePerorgTree();
        humanresourcePerorgTree.setId(humanresourceOrganizationDTO.getId());
        humanresourcePerorgTree.setParentId(humanresourceOrganizationDTO.getParentId());
        humanresourcePerorgTree.setFlag(DEPT);
        humanresourcePerorgTree.setIcon(DEPT_ICON);
        humanresourcePerorgTree.setLabel(humanresourceOrganizationDTO.getOrganizationName());
        humanresourcePerorgTree.setParentName(humanresourceOrganizationDTO.getParentName());
        humanresourcePerorgTreeService.save(humanresourcePerorgTree);
    }

    /**
     * 修改机构资料
     * @param humanresourceOrganizationDTO
     * @return
     */
    @Override
    public void updateData(HumanresourceOrganizationDTO humanresourceOrganizationDTO) {
        HumanresourceOrganization humanresourceOrganization = new HumanresourceOrganization();
        BeanUtils.copyProperties(humanresourceOrganizationDTO,humanresourceOrganization);
        HumanresourcePerorgTree humanresourcePerorgTree = new HumanresourcePerorgTree();
        humanresourcePerorgTree.setId(humanresourceOrganization.getId());
        humanresourcePerorgTree.setParentId(humanresourceOrganization.getParentId());
        humanresourcePerorgTree.setFlag(DEPT);
        humanresourcePerorgTree.setIcon(DEPT_ICON);
        humanresourcePerorgTree.setParentName(humanresourceOrganization.getParentName());
        humanresourcePerorgTree.setLabel(humanresourceOrganization.getOrganizationName());
        HumanresourceOrganization organization = baseMapper.selectById(humanresourceOrganization.getId());
        if (StringUtils.isNotNull(organization)) {
            baseMapper.updateById(humanresourceOrganization);
            humanresourcePerorgTreeService.updateById(humanresourcePerorgTree);
        } else {
           baseMapper.insert(humanresourceOrganization);
           humanresourcePerorgTreeService.save(humanresourcePerorgTree);
        }
        @Cleanup Jedis jedis = jedisPool.getResource();
        jedis.unlink("system-department-tree");
    }

    /**
     * 删除机构资料
     * @param humanresourceOrganizationDTO
     * @return
     */
    @Override
    public void removeData(HumanresourceOrganizationDTO humanresourceOrganizationDTO) {
        HumanresourceOrganization humanresourceOrganization = new HumanresourceOrganization();
        BeanUtils.copyProperties(humanresourceOrganizationDTO,humanresourceOrganization);
        baseMapper.deleteById(humanresourceOrganization);
        humanresourcePerorgTreeService.removeById(humanresourceOrganization.getId());
        @Cleanup Jedis jedis = jedisPool.getResource();
        jedis.unlink("system-department-tree");
    }

    /**
     * 组机构树结构列表
     * @return
     */
    @Override
    public List<HumanresourceOrganizationTreeDTO> getOrganizationTreeList() {
        List<HumanresourceOrganizationTreeDTO> returnList = new ArrayList<HumanresourceOrganizationTreeDTO>();
        List<String> tempList = new ArrayList<>();

        //合并人员资料和机构资料
        List<HumanresourceOrganizationTreeDTO> humanresourceOrganizationTreeList = new ArrayList<>();
        humanresourceOrganizationTreeList.addAll(mergePersonnelList());
        humanresourceOrganizationTreeList.addAll(mergeOrganizationList());
        //循环取出id值，存入tempList
        if (StringUtils.isNotNull(humanresourceOrganizationTreeList)) {
            for (HumanresourceOrganizationTreeDTO organization : humanresourceOrganizationTreeList) {
                tempList.add(organization.getId());
            }
        }
        for (Iterator<HumanresourceOrganizationTreeDTO> iterator = humanresourceOrganizationTreeList.iterator();iterator.hasNext();) {
            HumanresourceOrganizationTreeDTO organization = (HumanresourceOrganizationTreeDTO) iterator.next();
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
        return returnList.stream().map(HumanresourceOrganizationTreeDTO::new).collect(Collectors.toList());
    }



    /**
     * 处理人员资料
     * @return
     */
    public List<HumanresourceOrganizationTreeDTO> mergePersonnelList() {
        //查询出所有人员资料
        List<HumanresourcePersonnel> humanresourcePersonnelList = humanresourcePersonnelMapper.listAll();
        List<HumanresourceOrganizationTreeDTO> personnelCollect = new ArrayList<>();
        //采用流的方式获取人员资料信息
        if(StringUtils.isNotNull(humanresourcePersonnelList)) {
            personnelCollect = humanresourcePersonnelList.stream().map(item -> {
                HumanresourceOrganizationTreeDTO treeDTO = new HumanresourceOrganizationTreeDTO();
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
    public List<HumanresourceOrganizationTreeDTO> mergeOrganizationList() {
        //查询出所有机构数据
        List<HumanresourceOrganization> humanresourceOrganizations = humanresourceOrganizationMapper.listAll();
        List<HumanresourceOrganizationTreeDTO> organizationCollect = new ArrayList<>();
        //处理获取机构资料信息
        if (StringUtils.isNotNull(humanresourceOrganizations)) {
            organizationCollect = humanresourceOrganizations.stream().map(item -> {
                HumanresourceOrganizationTreeDTO treeDTO = new HumanresourceOrganizationTreeDTO();
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
    private void recursionFn(List<HumanresourceOrganizationTreeDTO> list, HumanresourceOrganizationTreeDTO organization) {
        //得到子节点列表
        List<HumanresourceOrganizationTreeDTO> childList = getChildList(list,organization);
        organization.setChildren(childList);
        for (HumanresourceOrganizationTreeDTO orgChild : childList) {
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
    private List<HumanresourceOrganizationTreeDTO> getChildList(List<HumanresourceOrganizationTreeDTO> list, HumanresourceOrganizationTreeDTO organization) {
        List<HumanresourceOrganizationTreeDTO> orgList = new ArrayList<HumanresourceOrganizationTreeDTO>();
        //声明迭代器
        Iterator<HumanresourceOrganizationTreeDTO> iterator = list.iterator();
        while (iterator.hasNext()) {
            HumanresourceOrganizationTreeDTO humanOrg = (HumanresourceOrganizationTreeDTO) iterator.next();
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
    private boolean hasChild(List<HumanresourceOrganizationTreeDTO> list, HumanresourceOrganizationTreeDTO organization) {
        return getChildList(list,organization).size() > 0;
    }
}
