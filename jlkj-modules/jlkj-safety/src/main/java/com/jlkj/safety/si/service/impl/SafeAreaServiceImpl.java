package com.jlkj.safety.si.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.safety.si.entity.SafeSiArea;
import com.jlkj.safety.si.mapper.SafeAreaMapper;
import com.jlkj.safety.si.service.SafeAreaService;
import com.jlkj.safety.si.utils.ResponseUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 安全区域 服务实现类
 * </p>
 *
 * @author su
 * @since 2020-12-28
 */
@Service
public class SafeAreaServiceImpl extends ServiceImpl<SafeAreaMapper, SafeSiArea> implements SafeAreaService {

    @Resource
    private SafeAreaMapper safeAreaMapper;

    @Override
    @Transactional(readOnly = true)
    public Object getSafeAreaPageList(Map<String, Object> params) {
        return ResponseUtil.toResult(params, safeAreaMapper.getSafeAreaPageList(ResponseUtil.initPage(params), params));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object insertSafeArea(Map<String, Object> params) {
        Map<String, Object> query = new HashMap<>(1);
        query.put("safe_area_name", params.get("safe_area_name").toString());
        if (listByMap(query).size() == 0) {
            SafeSiArea safeSiArea = new SafeSiArea();
            safeSiArea.setId(IdUtil.randomUUID());
            safeSiArea.setSafeAreaName(params.get("safe_area_name").toString());
            safeSiArea.setLng(params.get("lng").toString());
            safeSiArea.setLat(params.get("lat").toString());
            safeSiArea.setCreateUserId(params.get("create_user_id").toString());
            safeSiArea.setCreateUserName(params.get("create_user_name").toString());
            safeSiArea.setCeateTime(DateUtil.parseLocalDateTime(DateUtil.now()));
            if (save(safeSiArea)) {
                return ResponseUtil.toResult(params, "安全区域保存成功");
            } else {
                return ResponseUtil.toError(params, "安全区域保存失败");
            }
        }
        else {
            return ResponseUtil.toError(params, "安全区域名称已存在");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object updateSafeArea(Map<String, Object> params) {
        SafeSiArea safeSiArea = getById(params.get("id").toString());
        if (null != safeSiArea) {
            QueryWrapper<SafeSiArea> queryWrapper = new QueryWrapper<>();
            queryWrapper
                    .eq("safe_area_name", params.get("safe_area_name").toString())
                    .ne("id", params.get("id").toString());
            if (listMaps(queryWrapper).size() == 0) {
                safeSiArea.setSafeAreaName(params.get("safe_area_name").toString());
                safeSiArea.setLng(params.get("lng").toString());
                safeSiArea.setLat(params.get("lat").toString());
                if (updateById(safeSiArea)) {
                    return ResponseUtil.toResult(params, "安全区域保存成功");
                } else {
                    return ResponseUtil.toError(params, "安全区域保存失败");
                }
            }
            else {
                return ResponseUtil.toError(params, "安全区域名称已存在");
            }
        }
        else {
            return ResponseUtil.toError(params, "安全区域不存在");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object deleteSafeArea(Map<String, Object> params) {
        SafeSiArea safeSiArea = getById(params.get("id").toString());
        if (null != safeSiArea) {
            if (removeById(safeSiArea.getId())) {
                return ResponseUtil.toResult(params, "安全区域删除成功");
            } else {
                return ResponseUtil.toError(params, "安全区域删除失败");
            }
        }
        else {
            return ResponseUtil.toError(params, "安全区域不存在");
        }
    }
}
