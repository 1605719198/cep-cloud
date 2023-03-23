package com.jlkj.safety.si.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.safety.si.entity.SafeSiJobBlindPlatePluggingSafetyMeasures;
import com.jlkj.safety.si.mapper.SafeJobBlindPlatePluggingSafetyMeasuresMapper;
import com.jlkj.safety.si.service.SafeJobBlindPlatePluggingSafetyMeasuresService;
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
 * @since 2021-02-07
 */
@Service
public class SafeJobBlindPlatePluggingSafetyMeasuresServiceImpl extends ServiceImpl<SafeJobBlindPlatePluggingSafetyMeasuresMapper, SafeSiJobBlindPlatePluggingSafetyMeasures> implements SafeJobBlindPlatePluggingSafetyMeasuresService {

    @Resource
    private SafeJobBlindPlatePluggingSafetyMeasuresMapper safeJobBlindPlatePluggingSafetyMeasuresMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object confirmSafeJobBlindPlatePluggingSafetyMeasures(Map<String, Object> params) {
        SafeSiJobBlindPlatePluggingSafetyMeasures safeSiJobBlindPlatePluggingSafetyMeasures = getById(params.get("id").toString());
        safeSiJobBlindPlatePluggingSafetyMeasures.setConfirmPersonId(params.get("confirm_person_id").toString());
        safeSiJobBlindPlatePluggingSafetyMeasures.setConfirmPersonName(params.get("confirm_person_name").toString());
        safeSiJobBlindPlatePluggingSafetyMeasures.setSafetyMeasures(params.get("safety_measures").toString());
        safeSiJobBlindPlatePluggingSafetyMeasures.setIsConfirm(1);
        safeSiJobBlindPlatePluggingSafetyMeasures.setConfirmTime(DateUtil.parseLocalDateTime(DateUtil.now()));
        if (updateById(safeSiJobBlindPlatePluggingSafetyMeasures)) {
            return ResponseUtil.toResult(params, "安全措施确认成功");
        }
        else {
            return ResponseUtil.toError(params, "安全措施确认失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object cancelSafeJobBlindPlatePluggingSafetyMeasures(Map<String, Object> params) {
        SafeSiJobBlindPlatePluggingSafetyMeasures safeSiJobBlindPlatePluggingSafetyMeasures = getById(params.get("id").toString());
        if (null != safeSiJobBlindPlatePluggingSafetyMeasures) {
            Map<String, Object> queryParam = new HashMap<>(1);
            queryParam.put("sort", safeSiJobBlindPlatePluggingSafetyMeasures.getSort());
            Map<String, Object> oldData = safeJobBlindPlatePluggingSafetyMeasuresMapper.getOldSafeJobMeasures(queryParam);
            if (null != oldData) {
                safeSiJobBlindPlatePluggingSafetyMeasures.setConfirmPersonId("");
                safeSiJobBlindPlatePluggingSafetyMeasures.setConfirmPersonName("");
                safeSiJobBlindPlatePluggingSafetyMeasures.setSafetyMeasures(oldData.get("safety_measures").toString());
                safeSiJobBlindPlatePluggingSafetyMeasures.setIsConfirm(0);
                safeSiJobBlindPlatePluggingSafetyMeasures.setConfirmTime(null);
                if (updateById(safeSiJobBlindPlatePluggingSafetyMeasures)) {
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
