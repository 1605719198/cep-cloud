package com.jlkj.safety.si.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.safety.si.entity.SafeSiWorkType;
import com.jlkj.safety.si.mapper.SafeWorkTypeMapper;
import com.jlkj.safety.si.service.SafeWorkTypeService;
import com.jlkj.safety.si.utils.ResponseUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 工种 服务实现类
 * </p>
 *
 * @author su
 * @since 2021-01-07
 */
@Service
public class SafeWorkTypeServiceImpl extends ServiceImpl<SafeWorkTypeMapper, SafeSiWorkType> implements SafeWorkTypeService {

    @Resource
    private SafeWorkTypeMapper safeWorkTypeMapper;

    @Override
    @Transactional(readOnly = true)
    public Object getSafeWorkTypePageList(Map<String, Object> params) {
        return ResponseUtil.toResult(params, safeWorkTypeMapper.getSafeWorkTypePageList(ResponseUtil.initPage(params), params));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object insertSafeWorkType(Map<String, Object> params) {
        Map<String, Object> query = new HashMap<>(0);
        query.put("work_type_name", params.get("work_type_name").toString());
        if (listByMap(query).size() == 0) {
            SafeSiWorkType safeSiWorkType = new SafeSiWorkType();
            safeSiWorkType.setId(IdUtil.randomUUID());
            safeSiWorkType.setWorkTypeName(params.get("work_type_name").toString());
            safeSiWorkType.setCreateUserId(params.get("create_user_id").toString());
            safeSiWorkType.setCreateUserName(params.get("create_user_name").toString());
            safeSiWorkType.setCeateTime(DateUtil.parseLocalDateTime(DateUtil.now()));
            if (save(safeSiWorkType)) {
                return ResponseUtil.toResult(params, "工种保存成功");
            } else {
                return ResponseUtil.toError(params, "工种保存失败");
            }
        }
        else {
            return ResponseUtil.toError(params, "工种名称已存在");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object updateSafeWorkType(Map<String, Object> params) {
        SafeSiWorkType safeSiWorkType = getById(params.get("id").toString());
        if (null != safeSiWorkType) {
            QueryWrapper<SafeSiWorkType> queryWrapper = new QueryWrapper<>();
            queryWrapper
                    .eq("work_type_name", params.get("work_type_name").toString())
                    .ne("id", params.get("id").toString());
            if (listMaps(queryWrapper).size() == 0) {
                safeSiWorkType.setWorkTypeName(params.get("work_type_name").toString());
                if (updateById(safeSiWorkType)) {
                    return ResponseUtil.toResult(params, "工种保存成功");
                } else {
                    return ResponseUtil.toError(params, "工种保存失败");
                }
            }
            else {
                return ResponseUtil.toError(params, "工种名称已存在");
            }
        }
        else {
            return ResponseUtil.toError(params, "工种不存在");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object deleteSafeWorkType(Map<String, Object> params) {
        SafeSiWorkType safeSiWorkType = getById(params.get("id").toString());
        if (null != safeSiWorkType) {
            if (removeById(safeSiWorkType.getId())) {
                return ResponseUtil.toResult(params, "工种删除成功");
            } else {
                return ResponseUtil.toError(params, "工种删除失败");
            }
        }
        else {
            return ResponseUtil.toError(params, "工种不存在");
        }
    }
}
