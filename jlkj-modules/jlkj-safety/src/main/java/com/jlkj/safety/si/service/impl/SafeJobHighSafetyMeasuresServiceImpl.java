package com.jlkj.safety.si.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.safety.si.entity.SafeSiJobHighSafetyMeasures;
import com.jlkj.safety.si.mapper.SafeJobHighSafetyMeasuresMapper;
import com.jlkj.safety.si.service.SafeJobHighSafetyMeasuresService;
import com.jlkj.safety.si.utils.ResponseUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *
    服务实现类
 * </p>
 *
 * @author su
 * @since 2021-01-13
 */
@Service
public class SafeJobHighSafetyMeasuresServiceImpl extends ServiceImpl<SafeJobHighSafetyMeasuresMapper, SafeSiJobHighSafetyMeasures> implements SafeJobHighSafetyMeasuresService {

    @Resource
    private SafeJobHighSafetyMeasuresMapper safeJobHighSafetyMeasuresMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object confirmSafeJobHighSafetyMeasures(Map<String, Object> params) {
        SafeSiJobHighSafetyMeasures safeSiJobHighSafetyMeasures = getById(params.get("id").toString());
        safeSiJobHighSafetyMeasures.setConfirmPersonId(params.get("confirm_person_id").toString());
        safeSiJobHighSafetyMeasures.setConfirmPersonName(params.get("confirm_person_name").toString());
        safeSiJobHighSafetyMeasures.setSafetyMeasures(params.get("safety_measures").toString());
        safeSiJobHighSafetyMeasures.setIsConfirm(1);
        safeSiJobHighSafetyMeasures.setConfirmTime(DateUtil.parseLocalDateTime(DateUtil.now()));
        if (updateById(safeSiJobHighSafetyMeasures)) {
            return ResponseUtil.toResult(params, "安全措施确认成功");
        }
        else {
            return ResponseUtil.toError(params, "安全措施确认失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object cancelSafeJobHighSafetyMeasures(Map<String, Object> params) {
        SafeSiJobHighSafetyMeasures safeSiJobHighSafetyMeasures = getById(params.get("id").toString());
        if (null != safeSiJobHighSafetyMeasures) {
            Map<String, Object> queryParam = new HashMap<>(1);
            queryParam.put("sort", safeSiJobHighSafetyMeasures.getSort());
            Map<String, Object> oldData = safeJobHighSafetyMeasuresMapper.getOldSafeJobMeasures(queryParam);
            if (null != oldData) {
                safeSiJobHighSafetyMeasures.setConfirmPersonId("");
                safeSiJobHighSafetyMeasures.setConfirmPersonName("");
                safeSiJobHighSafetyMeasures.setSafetyMeasures(oldData.get("safety_measures").toString());
                safeSiJobHighSafetyMeasures.setIsConfirm(0);
                safeSiJobHighSafetyMeasures.setConfirmTime(null);
                if (updateById(safeSiJobHighSafetyMeasures)) {
                    return ResponseUtil.toResult(params, "安全措施取消成功");
                } else {
                    return ResponseUtil.toError(params, "安全措施取消失败");
                }
            }
            else {
                return ResponseUtil.toError(params, "获取原始安全措施失败");
            }
        }
        else {
            return ResponseUtil.toError(params, "安全措施不存在");
        }
    }
}
