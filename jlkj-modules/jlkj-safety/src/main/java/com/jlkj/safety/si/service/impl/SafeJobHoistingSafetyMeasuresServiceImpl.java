package com.jlkj.safety.si.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.safety.si.entity.SafeSiJobHoistingSafetyMeasures;
import com.jlkj.safety.si.mapper.SafeJobHoistingSafetyMeasuresMapper;
import com.jlkj.safety.si.service.SafeJobHoistingSafetyMeasuresService;
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
public class SafeJobHoistingSafetyMeasuresServiceImpl extends ServiceImpl<SafeJobHoistingSafetyMeasuresMapper, SafeSiJobHoistingSafetyMeasures> implements SafeJobHoistingSafetyMeasuresService {

    @Resource
    private SafeJobHoistingSafetyMeasuresMapper safeJobHoistingSafetyMeasuresMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object confirmSafeJobHoistingSafetyMeasures(Map<String, Object> params) {
        SafeSiJobHoistingSafetyMeasures safeSiJobHoistingSafetyMeasures = getById(params.get("id").toString());
        safeSiJobHoistingSafetyMeasures.setConfirmPersonId(params.get("confirm_person_id").toString());
        safeSiJobHoistingSafetyMeasures.setConfirmPersonName(params.get("confirm_person_name").toString());
        safeSiJobHoistingSafetyMeasures.setSafetyMeasures(params.get("safety_measures").toString());
        safeSiJobHoistingSafetyMeasures.setIsConfirm(1);
        safeSiJobHoistingSafetyMeasures.setConfirmTime(DateUtil.parseLocalDateTime(DateUtil.now()));
        if (updateById(safeSiJobHoistingSafetyMeasures)) {
            return ResponseUtil.toResult(params, "安全措施确认成功");
        }
        else {
            return ResponseUtil.toError(params, "安全措施确认失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object cancelSafeJobHoistingSafetyMeasures(Map<String, Object> params) {
        SafeSiJobHoistingSafetyMeasures safeSiJobHoistingSafetyMeasures = getById(params.get("id").toString());
        if (null != safeSiJobHoistingSafetyMeasures) {
            Map<String, Object> queryParam = new HashMap<>(1);
            queryParam.put("sort", safeSiJobHoistingSafetyMeasures.getSort());
            Map<String, Object> oldData = safeJobHoistingSafetyMeasuresMapper.getOldSafeJobMeasures(queryParam);
            if (null != oldData) {
                safeSiJobHoistingSafetyMeasures.setConfirmPersonId("");
                safeSiJobHoistingSafetyMeasures.setConfirmPersonName("");
                safeSiJobHoistingSafetyMeasures.setSafetyMeasures(oldData.get("safety_measures").toString());
                safeSiJobHoistingSafetyMeasures.setIsConfirm(0);
                safeSiJobHoistingSafetyMeasures.setConfirmTime(null);
                if (updateById(safeSiJobHoistingSafetyMeasures)) {
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
