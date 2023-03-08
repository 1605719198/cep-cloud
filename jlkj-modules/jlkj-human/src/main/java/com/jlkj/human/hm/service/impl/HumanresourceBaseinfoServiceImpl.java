package com.jlkj.human.hm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.human.hm.domain.HumanresourceBaseinfo;
import com.jlkj.human.hm.mapper.HumanresourceBaseinfoMapper;
import com.jlkj.human.hm.service.HumanresourceBaseinfoService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author 89373
* @description 针对表【human_hm_baseinfo(选单配置表)】的数据库操作Service实现
* @createDate 2023-03-07 18:33:11
*/
@Service
public class HumanresourceBaseinfoServiceImpl extends ServiceImpl<HumanresourceBaseinfoMapper, HumanresourceBaseinfo>
    implements HumanresourceBaseinfoService {

    /**
     * 选单配置树结构列表
     * @return
     */
    @Override
    public List<HumanresourceBaseinfo> getBaseInfoTreeList() {
        List<HumanresourceBaseinfo> returnList = new ArrayList<>();
        List<String> tempList = new ArrayList<>();

        //查询选单配置资料
        List<HumanresourceBaseinfo> humanresourceBaseinfoTrees = baseMapper.selectList(new QueryWrapper<HumanresourceBaseinfo>().orderByDesc("update_date"));
        //循环取出id值，存入tempList
        if (StringUtils.isNotNull(humanresourceBaseinfoTrees)) {
            for (HumanresourceBaseinfo baseInfo : humanresourceBaseinfoTrees) {
                tempList.add(baseInfo.getUuid());
            }
        }
        for (Iterator<HumanresourceBaseinfo> iterator = humanresourceBaseinfoTrees.iterator(); iterator.hasNext();) {
            HumanresourceBaseinfo baseInfo = (HumanresourceBaseinfo) iterator.next();
            //如果是Root节点，遍历该父节点的所有子节点
            if (!tempList.contains(baseInfo.getParentId())) {
                //进行递归操作
                recursionFn(humanresourceBaseinfoTrees,baseInfo);
                returnList.add(baseInfo);
            }
        }
        if (returnList.isEmpty()) {
            returnList = humanresourceBaseinfoTrees;
        }
        return returnList.stream().map(HumanresourceBaseinfo::new).collect(Collectors.toList());
    }

    /**
     * 递归列表
     * @param list
     * @param baseInfo
     */
    private void recursionFn(List<HumanresourceBaseinfo> list, HumanresourceBaseinfo baseInfo) {
        //得到子节点列表
        List<HumanresourceBaseinfo> childList = getChildList(list,baseInfo);
        baseInfo.setChildren(childList);
        for (HumanresourceBaseinfo orgChild : childList) {
            if (hasChild(list, orgChild)) {
                recursionFn(list, orgChild);
            }
        }
    }

    /**
     * 得到子节点列表
     * @param list
     * @param baseInfo
     * @return
     */
    private List<HumanresourceBaseinfo> getChildList(List<HumanresourceBaseinfo> list, HumanresourceBaseinfo baseInfo) {
        List<HumanresourceBaseinfo> orgList = new ArrayList<>();
        //声明迭代器
        Iterator<HumanresourceBaseinfo> iterator = list.iterator();
        while (iterator.hasNext()) {
            HumanresourceBaseinfo baseinfoOrg = (HumanresourceBaseinfo) iterator.next();
            //判断当前迭代对象的父ID是否等于传入的ID
            if (StringUtils.isNotNull(baseinfoOrg.getParentId()) && baseinfoOrg.getParentId().equals(baseInfo.getUuid())) {
                orgList.add(baseinfoOrg);
            }
        }
        return orgList;
    }

    /**
     * 判断是否有子节点
     * @param list
     * @param baseInfo
     * @return
     */
    private boolean hasChild(List<HumanresourceBaseinfo> list, HumanresourceBaseinfo baseInfo) {
        return getChildList(list,baseInfo).size() > 0;
    }

}




