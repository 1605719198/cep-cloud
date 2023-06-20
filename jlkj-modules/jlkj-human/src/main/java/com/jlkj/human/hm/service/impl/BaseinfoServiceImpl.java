package com.jlkj.human.hm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.human.hm.domain.Baseinfo;
import com.jlkj.human.hm.dto.BaseInfoDTO;
import com.jlkj.human.hm.mapper.BaseinfoMapper;
import com.jlkj.human.hm.service.IBaseinfoService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author HuangBing
* @description 针对表【human_hm_baseinfo(选单配置表)】的数据库操作Service实现
* @createDate 2023-03-07 18:33:11
*/
@Service
public class BaseinfoServiceImpl extends ServiceImpl<BaseinfoMapper, Baseinfo>
    implements IBaseinfoService {

    /**
     * 选单配置树结构列表
     * @return
     */
    @Override
    public List<Baseinfo> getBaseInfoTreeList() {
        List<Baseinfo> returnList = new ArrayList<>();
        List<String> tempList = new ArrayList<>();

        //查询选单配置资料
        List<Baseinfo> baseinfoTrees = baseMapper.selectList(new QueryWrapper<Baseinfo>().orderByDesc("update_date"));
        //循环取出id值，存入tempList
        if (StringUtils.isNotNull(baseinfoTrees)) {
            for (Baseinfo baseInfo : baseinfoTrees) {
                tempList.add(baseInfo.getUuid());
            }
        }
        for (Iterator<Baseinfo> iterator = baseinfoTrees.iterator(); iterator.hasNext();) {
            Baseinfo baseInfo = (Baseinfo) iterator.next();
            //如果是Root节点，遍历该父节点的所有子节点
            if (!tempList.contains(baseInfo.getParentId())) {
                //进行递归操作
                recursionFn(baseinfoTrees,baseInfo);
                returnList.add(baseInfo);
            }
        }
        if (returnList.isEmpty()) {
            returnList = baseinfoTrees;
        }
        return returnList.stream().map(Baseinfo::new).collect(Collectors.toList());
    }

    /**
     * 递归列表
     * @param list
     * @param baseInfo
     */
    private void recursionFn(List<Baseinfo> list, Baseinfo baseInfo) {
        //得到子节点列表
        List<Baseinfo> childList = getChildList(list,baseInfo);
        baseInfo.setChildren(childList);
        for (Baseinfo orgChild : childList) {
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
    private List<Baseinfo> getChildList(List<Baseinfo> list, Baseinfo baseInfo) {
        List<Baseinfo> orgList = new ArrayList<>();
        //声明迭代器
        Iterator<Baseinfo> iterator = list.iterator();
        while (iterator.hasNext()) {
            Baseinfo baseinfoOrg = (Baseinfo) iterator.next();
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
    private boolean hasChild(List<Baseinfo> list, Baseinfo baseInfo) {
        return getChildList(list,baseInfo).size() > 0;
    }

    /**
     * 获取专业下拉选单列表
     */
    @Override
    public Object getDegreeMajor(BaseInfoDTO humanresourceBaseInfoDTO) {
        List<String> baseInfoList = humanresourceBaseInfoDTO.getBaseInfoList();
        HashMap<String, List<Baseinfo>> map = new HashMap<>(16);
        for (String item : baseInfoList) {
            LambdaQueryWrapper<Baseinfo> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Baseinfo::getDicNo, item);
            Baseinfo baseInfo = getOne(queryWrapper);
            List<Baseinfo> list = lambdaQuery().eq(Baseinfo::getParentId, baseInfo.getUuid()).list();
            map.put(item, list);
        }
        return AjaxResult.success("查询成功", map);
    }

}




