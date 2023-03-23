package com.jlkj.safety.si.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.safety.si.entity.SafeSiCheckType;
import com.jlkj.safety.si.mapper.SafeCheckTypeMapper;
import com.jlkj.safety.si.service.SafeCheckTypeService;
import com.jlkj.safety.si.utils.ResponseUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 安全巡检类型 服务实现类
 * </p>
 *
 * @author su
 * @since 2021-01-07
 */
@Service
public class SafeCheckTypeServiceImpl extends ServiceImpl<SafeCheckTypeMapper, SafeSiCheckType> implements SafeCheckTypeService {

    @Resource
    private SafeCheckTypeMapper safeCheckTypeMapper;

    @Override
    @Transactional(readOnly = true)
    public Object getSafeCheckTypePageList(Map<String, Object> params) {
        return ResponseUtil.toResult(params, safeCheckTypeMapper.getSafeCheckTypePageList(ResponseUtil.initPage(params), params));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object insertSafeCheckType(Map<String, Object> params) {
        Map<String, Object> query = new HashMap<>(1);
        query.put("check_type_name", params.get("check_type_name").toString());
        if (listByMap(query).size() == 0) {
            SafeSiCheckType safeSiCheckType = new SafeSiCheckType();
            safeSiCheckType.setId(IdUtil.randomUUID());
            safeSiCheckType.setCheckTypeName(params.get("check_type_name").toString());
            safeSiCheckType.setCreateUserId(params.get("create_user_id").toString());
            safeSiCheckType.setCreateUserName(params.get("create_user_name").toString());
            safeSiCheckType.setCeateTime(DateUtil.parseLocalDateTime(DateUtil.now()));
            if (save(safeSiCheckType)) {
                return ResponseUtil.toResult(params, "安全巡检类型保存成功");
            } else {
                return ResponseUtil.toError(params, "安全巡检类型保存失败");
            }
        }
        else {
            return ResponseUtil.toError(params, "巡检类型名称已存在");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object updateSafeCheckType(Map<String, Object> params) {
        SafeSiCheckType safeSiCheckType = getById(params.get("id").toString());
        if (null != safeSiCheckType) {
            QueryWrapper<SafeSiCheckType> queryWrapper = new QueryWrapper<>();
            queryWrapper
                    .eq("check_type_name", params.get("check_type_name").toString())
                    .ne("id", params.get("id").toString());
            if (listMaps(queryWrapper).size() == 0) {
                safeSiCheckType.setCheckTypeName(params.get("check_type_name").toString());
                if (updateById(safeSiCheckType)) {
                    return ResponseUtil.toResult(params, "安全巡检类型保存成功");
                } else {
                    return ResponseUtil.toError(params, "安全巡检类型保存失败");
                }
            }
            else {
                return ResponseUtil.toError(params, "巡检类型名称已存在");
            }
        }
        else {
            return ResponseUtil.toError(params, "安全巡检类型不存在");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object deleteSafeCheckType(Map<String, Object> params) {
        SafeSiCheckType safeSiCheckType = getById(params.get("id").toString());
        if (null != safeSiCheckType) {
            if (removeById(safeSiCheckType.getId())) {
                return ResponseUtil.toResult(params, "安全巡检类型删除成功");
            } else {
                return ResponseUtil.toError(params, "安全巡检类型删除失败");
            }
        }
        else {
            return ResponseUtil.toError(params, "安全巡检类型不存在");
        }
    }
}
