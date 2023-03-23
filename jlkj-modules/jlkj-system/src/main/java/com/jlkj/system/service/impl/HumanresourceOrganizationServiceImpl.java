package com.jlkj.system.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.system.dto.department.GetDepartmentListDTO;
import com.jlkj.system.entity.HumanresourceOrganization;
import com.jlkj.system.mapper.HumanresourceOrganizationMapper;
import com.jlkj.system.service.HumanresourceOrganizationService;
import lombok.Cleanup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.*;
import java.util.stream.Collectors;

import static com.jlkj.common.core.constant.CommonConstant.DEPARTMENT_KEY;

/**
* @author zyf
* @description 针对表【human_hm_organization(机构数据)】的数据库操作Service实现
* @createDate 2022-07-05 08:30:49
*/
@Service
public class HumanresourceOrganizationServiceImpl extends ServiceImpl<HumanresourceOrganizationMapper, HumanresourceOrganization>
    implements HumanresourceOrganizationService{

    @Autowired
    JedisPool jedisPool;

    public Object getDepartmentTree() {
        @Cleanup Jedis jedis = jedisPool.getResource();
        if(jedis.exists(DEPARTMENT_KEY)) {
            return  AjaxResult.success(JSONArray.parseObject(jedis.get(DEPARTMENT_KEY), List.class));
        }
        List<HumanresourceOrganization> list = list(new LambdaQueryWrapper<HumanresourceOrganization>()
                .orderByAsc(HumanresourceOrganization::getOrganizationCode));
        List<Map<String, Object>> result = new ArrayList<>();
        //先找根节点
        Optional<HumanresourceOrganization> optional = list.stream().filter(humanresourceOrganization -> "".equals(humanresourceOrganization.getOrganizationTierId())).findFirst();
        optional.ifPresent(humanresourceOrganization -> {
            result.add(recursiveDepartmentTree(humanresourceOrganization, list));
        });
        jedis.setex(DEPARTMENT_KEY, 120, JSON.toJSONString(result));
        return  AjaxResult.success(result);
    }

    /**
     * 递归叶子节点
     */
    private Map<String, Object> recursiveDepartmentTree(HumanresourceOrganization parent, List<HumanresourceOrganization> list) {
        List<Map<String, Object>> children = new ArrayList<>();
        Map<String, Object> parentNode = new HashMap<>(1);
        parentNode.put("label", parent.getOrganizationName());
        parentNode.put("id", parent.getId());
        parentNode.put("domain", parent);
        //如果存在子节点，进递归
        List<HumanresourceOrganization> childNodes = list.stream()
                .filter(humanresourceOrganization -> humanresourceOrganization.getParentId().equals(parent.getId()))
                .collect(Collectors.toList());
        if (childNodes.size() > 0) {
            childNodes.forEach(org -> children.add(recursiveDepartmentTree(org, list)));
            parentNode.put("children", children);
        };
        return parentNode;
    }

    public Object getDepartmentList(GetDepartmentListDTO dto) {
        Page<HumanresourceOrganization> page = new Page<>(dto.getCurrent(), dto.getSize());
        IPage<HumanresourceOrganization> list = page(page, new LambdaQueryWrapper<HumanresourceOrganization>()
                .eq(!dto.getDepartment_id().isEmpty(), HumanresourceOrganization::getParentId, dto.getDepartment_id())
                .like(!dto.getDepartment_name().isEmpty(), HumanresourceOrganization::getOrganizationName, dto.getDepartment_name())
                .orderByAsc(HumanresourceOrganization::getOrganizationCode));
        return AjaxResult.success(list);
    }
}




