package com.jlkj.human.hm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.human.hm.domain.PerorgTree;
import com.jlkj.human.hm.mapper.PerorgTreeMapper;
import com.jlkj.human.hm.service.IPerorgTreeService;
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
public class PerorgTreeServiceImpl extends ServiceImpl<PerorgTreeMapper, PerorgTree> implements IPerorgTreeService {

    private static final String DEPT = "dept";
    /**
     * 人员组织机构树结构列表
     * @return
     */
    @Override
    public List<PerorgTree> getOrganizationTreeList() {
        List<PerorgTree> returnList = new ArrayList<PerorgTree>();
        List<String> tempList = new ArrayList<>();

        //查询人员资料和机构资料
        List<PerorgTree> perorgTrees = baseMapper.selectList(new QueryWrapper<PerorgTree>().orderByDesc("flag"));
        //循环取出id值，存入tempList
        if (StringUtils.isNotNull(perorgTrees)) {
            for (PerorgTree organization : perorgTrees) {
                tempList.add(organization.getId());
            }
        }
        for (Iterator<PerorgTree> iterator = perorgTrees.iterator(); iterator.hasNext();) {
            PerorgTree organization = (PerorgTree) iterator.next();
            //如果是Root节点，遍历该父节点的所有子节点
            if (!tempList.contains(organization.getParentId())) {
                //进行递归操作
                recursionFn(perorgTrees,organization);
                returnList.add(organization);
            }
        }
        if (returnList.isEmpty()) {
            returnList = perorgTrees;
        }
        return returnList.stream().map(PerorgTree::new).collect(Collectors.toList());
    }

    /**
     * 获取机构及人员树结构资料(去除无人员部门树结构)
     *
     * @return
     */
    @Override
    public List<PerorgTree> getOrganizationDelTreeList() {
        List<PerorgTree> organizationTreeList = getOrganizationTreeList();
        removeChildren(organizationTreeList);
        return organizationTreeList;
    }

    /**
     * 递归删除无人员部门
     * @param list
     */
    public void removeChildren(List<PerorgTree> list) {
        for (Iterator<PerorgTree> iterator = list.iterator(); iterator.hasNext();) {
            PerorgTree item = (PerorgTree) iterator.next();
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
    public List<PerorgTree> getOrganizationDeptTreeList() {
        List<PerorgTree> returnList = new ArrayList<PerorgTree>();
        List<String> tempList = new ArrayList<>();

        //查询机构资料
        List<PerorgTree> perorgTrees = baseMapper.selectList(new QueryWrapper<PerorgTree>().eq("flag","dept"));
        //循环取出id值，存入tempList
        if (StringUtils.isNotNull(perorgTrees)) {
            for (PerorgTree organization : perorgTrees) {
                tempList.add(organization.getId());
            }
        }
        for (Iterator<PerorgTree> iterator = perorgTrees.iterator(); iterator.hasNext();) {
            PerorgTree organization = (PerorgTree) iterator.next();
            //如果是Root节点，遍历该父节点的所有子节点
            if (!tempList.contains(organization.getParentId())) {
                //进行递归操作
                recursionFn(perorgTrees,organization);
                returnList.add(organization);
            }
        }
        if (returnList.isEmpty()) {
            returnList = perorgTrees;
        }
        return returnList.stream().map(PerorgTree::new).collect(Collectors.toList());
    }


    /**
     * 递归列表
     * @param list
     * @param organization
     */
    private void recursionFn(List<PerorgTree> list, PerorgTree organization) {
        //得到子节点列表
        List<PerorgTree> childList = getChildList(list,organization);
        organization.setChildren(childList);
        for (PerorgTree orgChild : childList) {
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
    private List<PerorgTree> getChildList(List<PerorgTree> list, PerorgTree organization) {
        List<PerorgTree> orgList = new ArrayList<PerorgTree>();
        //声明迭代器
        Iterator<PerorgTree> iterator = list.iterator();
        while (iterator.hasNext()) {
            PerorgTree humanOrg = (PerorgTree) iterator.next();
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
    private boolean hasChild(List<PerorgTree> list, PerorgTree organization) {
        return getChildList(list,organization).size() > 0;
    }
}
