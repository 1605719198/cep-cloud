package com.jlkj.human.hm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.human.hm.domain.HumanresourcePerorgTree;
import com.jlkj.human.hm.mapper.HumanresourcePerorgTreeMapper;
import com.jlkj.human.hm.service.IHumanresourcePerorgTreeService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 人员机构树型结构存储表 服务实现类
 * </p>
 *
 * @author xin
 * @since 2022-05-17
 */
@Service
public class HumanresourcePerorgTreeServiceImpl extends ServiceImpl<HumanresourcePerorgTreeMapper, HumanresourcePerorgTree> implements IHumanresourcePerorgTreeService {

    private static final String DEPT = "dept";
    /**
     * 人员组织机构树结构列表
     * @return
     */
    @Override
    public List<HumanresourcePerorgTree> getOrganizationTreeList() {
        List<HumanresourcePerorgTree> returnList = new ArrayList<HumanresourcePerorgTree>();
        List<String> tempList = new ArrayList<>();

        //查询人员资料和机构资料
        List<HumanresourcePerorgTree> humanresourcePerorgTrees = baseMapper.selectList(new QueryWrapper<HumanresourcePerorgTree>().orderByDesc("flag"));
        //循环取出id值，存入tempList
        if (StringUtils.isNotNull(humanresourcePerorgTrees)) {
            for (HumanresourcePerorgTree organization : humanresourcePerorgTrees) {
                tempList.add(organization.getId());
            }
        }
        for (Iterator<HumanresourcePerorgTree> iterator = humanresourcePerorgTrees.iterator(); iterator.hasNext();) {
            HumanresourcePerorgTree organization = (HumanresourcePerorgTree) iterator.next();
            //如果是Root节点，遍历该父节点的所有子节点
            if (!tempList.contains(organization.getParentId())) {
                //进行递归操作
                recursionFn(humanresourcePerorgTrees,organization);
                returnList.add(organization);
            }
        }
        if (returnList.isEmpty()) {
            returnList = humanresourcePerorgTrees;
        }
        return returnList.stream().map(HumanresourcePerorgTree::new).collect(Collectors.toList());
    }

    /**
     * 获取机构及人员树结构资料(去除无人员部门树结构)
     *
     * @return
     */
    @Override
    public List<HumanresourcePerorgTree> getOrganizationDelTreeList() {
        List<HumanresourcePerorgTree> organizationTreeList = getOrganizationTreeList();
        removeChildren(organizationTreeList);
        return organizationTreeList;
    }

    /**
     * 递归删除无人员部门
     * @param list
     */
    public void removeChildren(List<HumanresourcePerorgTree> list) {
        for (Iterator<HumanresourcePerorgTree> iterator = list.iterator(); iterator.hasNext();) {
            HumanresourcePerorgTree item = (HumanresourcePerorgTree) iterator.next();
            if (DEPT.equals(item.getFlag())) {
                if (item.getChildren().size() < 1) {
                    iterator.remove();
                }
                removeChildren(item.getChildren());
            }
        }
    }

    /**
     * 获取机构部门数结构资料
     * @return
     */
    @Override
    public List<HumanresourcePerorgTree> getOrganizationDeptTreeList() {
        List<HumanresourcePerorgTree> returnList = new ArrayList<HumanresourcePerorgTree>();
        List<String> tempList = new ArrayList<>();

        //查询机构资料
        List<HumanresourcePerorgTree> humanresourcePerorgTrees = baseMapper.selectList(new QueryWrapper<HumanresourcePerorgTree>().eq("flag","dept"));
        //循环取出id值，存入tempList
        if (StringUtils.isNotNull(humanresourcePerorgTrees)) {
            for (HumanresourcePerorgTree organization : humanresourcePerorgTrees) {
                tempList.add(organization.getId());
            }
        }
        for (Iterator<HumanresourcePerorgTree> iterator = humanresourcePerorgTrees.iterator(); iterator.hasNext();) {
            HumanresourcePerorgTree organization = (HumanresourcePerorgTree) iterator.next();
            //如果是Root节点，遍历该父节点的所有子节点
            if (!tempList.contains(organization.getParentId())) {
                //进行递归操作
                recursionFn(humanresourcePerorgTrees,organization);
                returnList.add(organization);
            }
        }
        if (returnList.isEmpty()) {
            returnList = humanresourcePerorgTrees;
        }
        return returnList.stream().map(HumanresourcePerorgTree::new).collect(Collectors.toList());
    }


    /**
     * 递归列表
     * @param list
     * @param organization
     */
    private void recursionFn(List<HumanresourcePerorgTree> list, HumanresourcePerorgTree organization) {
        //得到子节点列表
        List<HumanresourcePerorgTree> childList = getChildList(list,organization);
        organization.setChildren(childList);
        for (HumanresourcePerorgTree orgChild : childList) {
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
    private List<HumanresourcePerorgTree> getChildList(List<HumanresourcePerorgTree> list, HumanresourcePerorgTree organization) {
        List<HumanresourcePerorgTree> orgList = new ArrayList<HumanresourcePerorgTree>();
        //声明迭代器
        Iterator<HumanresourcePerorgTree> iterator = list.iterator();
        while (iterator.hasNext()) {
            HumanresourcePerorgTree humanOrg = (HumanresourcePerorgTree) iterator.next();
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
    private boolean hasChild(List<HumanresourcePerorgTree> list, HumanresourcePerorgTree organization) {
        return getChildList(list,organization).size() > 0;
    }
}
