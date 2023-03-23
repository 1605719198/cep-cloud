package com.jlkj.safety.si.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.safety.si.entity.SafeSiConstructionUnit;
import com.jlkj.safety.si.mapper.SafeConstructionUnitMapper;
import com.jlkj.safety.si.service.SafeConstructionUnitPersonsService;
import com.jlkj.safety.si.service.SafeConstructionUnitService;
import com.jlkj.safety.si.utils.ResponseUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 外委施工单位 服务实现类
 * </p>
 *
 * @author su
 * @since 2020-12-28
 */
@Service
public class SafeConstructionUnitServiceImpl extends ServiceImpl<SafeConstructionUnitMapper, SafeSiConstructionUnit> implements SafeConstructionUnitService {

    @Resource
    private SafeConstructionUnitMapper safeConstructionUnitMapper;

    @Resource
    private SafeConstructionUnitPersonsService safeConstructionUnitPersonsService;

    @Override
    @Transactional(readOnly = true)
    public Object getSafeConstructionUnitPageList(Map<String, Object> params) {
        return ResponseUtil.toResult(params, safeConstructionUnitMapper.getSafeConstructionUnitPageList(ResponseUtil.initPage(params), params));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object insertSafeConstructionUnit(Map<String, Object> params) {
        Map<String, Object> query = new HashMap<>(1);
        query.put("construction_unit_name", params.get("construction_unit_name").toString());
        if (listByMap(query).size() == 0) {
            SafeSiConstructionUnit safeSiConstructionUnit = new SafeSiConstructionUnit();
            safeSiConstructionUnit.setId(IdUtil.randomUUID());
            safeSiConstructionUnit.setConstructionUnitName(params.get("construction_unit_name").toString());
            safeSiConstructionUnit.setContent(params.get("content").toString());
            safeSiConstructionUnit.setCreateUserId(params.get("create_user_id").toString());
            safeSiConstructionUnit.setCreateUserName(params.get("create_user_name").toString());
            safeSiConstructionUnit.setCeateTime(DateUtil.parseLocalDateTime(DateUtil.now()));
            if (save(safeSiConstructionUnit)) {
                return ResponseUtil.toResult(params, "施工单位保存成功");
            } else {
                return ResponseUtil.toError(params, "施工单位保存失败");
            }
        }
        else {
            return ResponseUtil.toError(params, "施工单位名称已存在");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object updateSafeConstructionUnit(Map<String, Object> params) {
        SafeSiConstructionUnit safeSiConstructionUnit = getById(params.get("id").toString());
        if (null != safeSiConstructionUnit) {
            QueryWrapper<SafeSiConstructionUnit> queryWrapper = new QueryWrapper<>();
            queryWrapper
                    .eq("construction_unit_name", params.get("construction_unit_name").toString())
                    .ne("id", params.get("id").toString());
            if (listMaps(queryWrapper).size() == 0) {
                safeSiConstructionUnit.setConstructionUnitName(params.get("construction_unit_name").toString());
                safeSiConstructionUnit.setContent(params.get("content").toString());
                if (updateById(safeSiConstructionUnit)) {
                    safeConstructionUnitPersonsService.updateSafeConstructionUnitPersonsConstructionUnit(params);
                    return ResponseUtil.toResult(params, "施工单位保存成功");
                } else {
                    return ResponseUtil.toError(params, "施工单位保存失败");
                }
            }
            else {
                return ResponseUtil.toError(params, "施工单位名称已存在");
            }
        }
        else {
            return ResponseUtil.toError(params, "施工单位不存在");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object deleteSafeConstructionUnit(Map<String, Object> params) {
        SafeSiConstructionUnit safeSiConstructionUnit = getById(params.get("id").toString());
        if (null != safeSiConstructionUnit) {
            if (!safeConstructionUnitPersonsService.existsConstructionUnitPersonsById(params)) {
                if (removeById(safeSiConstructionUnit.getId())) {
                    return ResponseUtil.toResult(params, "施工单位删除成功");
                } else {
                    return ResponseUtil.toError(params, "施工单位删除失败");
                }
            }
            else {
                return ResponseUtil.toError(params, "施工单位已有人员信息，请先删除人员");
            }
        }
        else {
            return ResponseUtil.toError(params, "施工单位不存在");
        }
    }
}
