package com.jlkj.safety.si.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.safety.si.entity.SafeSiDangerType;
import com.jlkj.safety.si.mapper.SafeDangerTypeMapper;
import com.jlkj.safety.si.service.SafeDangerTypeService;
import com.jlkj.safety.si.utils.ResponseUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 隐患等级 服务实现类
 * </p>
 *
 * @author su
 * @since 2021-01-07
 */
@Service
public class SafeDangerTypeServiceImpl extends ServiceImpl<SafeDangerTypeMapper, SafeSiDangerType> implements SafeDangerTypeService {

    @Resource
    private SafeDangerTypeMapper safeDangerTypeMapper;

    @Override
    @Transactional(readOnly = true)
    public Object getSafeDangerTypePageList(Map<String, Object> params) {
        return ResponseUtil.toResult(params, safeDangerTypeMapper.getSafeDangerTypePageList(ResponseUtil.initPage(params), params));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object insertSafeDangerType(Map<String, Object> params) {
        Map<String, Object> query = new HashMap<>(1);
        query.put("danger_type_name", params.get("danger_type_name").toString());
        if (listByMap(query).size() == 0) {
            SafeSiDangerType safeSiDangerType = new SafeSiDangerType();
            safeSiDangerType.setId(IdUtil.randomUUID());
            safeSiDangerType.setDangerTypeName(params.get("danger_type_name").toString());
            safeSiDangerType.setCreateUserId(params.get("create_user_id").toString());
            safeSiDangerType.setCreateUserName(params.get("create_user_name").toString());
            safeSiDangerType.setCeateTime(DateUtil.parseLocalDateTime(DateUtil.now()));
            if (save(safeSiDangerType)) {
                return ResponseUtil.toResult(params, "隐患等级保存成功");
            } else {
                return ResponseUtil.toError(params, "隐患等级保存失败");
            }
        }
        else {
            return ResponseUtil.toError(params, "隐患等级名称已存在");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object updateSafeDangerType(Map<String, Object> params) {
        SafeSiDangerType safeSiDangerType = getById(params.get("id").toString());
        if (null != safeSiDangerType) {
            QueryWrapper<SafeSiDangerType> queryWrapper = new QueryWrapper<>();
            queryWrapper
                    .eq("danger_type_name", params.get("danger_type_name").toString())
                    .ne("id", params.get("id").toString());
            if (listMaps(queryWrapper).size() == 0) {
                safeSiDangerType.setDangerTypeName(params.get("danger_type_name").toString());
                if (updateById(safeSiDangerType)) {
                    return ResponseUtil.toResult(params, "隐患等级保存成功");
                } else {
                    return ResponseUtil.toError(params, "隐患等级保存失败");
                }
            }
            else {
                return ResponseUtil.toError(params, "隐患等级名称已存在");
            }
        }
        else {
            return ResponseUtil.toError(params, "隐患等级不存在");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object deleteSafeDangerType(Map<String, Object> params) {
        SafeSiDangerType safeSiDangerType = getById(params.get("id").toString());
        if (null != safeSiDangerType) {
            if (removeById(safeSiDangerType.getId())) {
                return ResponseUtil.toResult(params, "隐患等级删除成功");
            } else {
                return ResponseUtil.toError(params, "隐患等级删除失败");
            }
        }
        else {
            return ResponseUtil.toError(params, "隐患等级不存在");
        }
    }
}
