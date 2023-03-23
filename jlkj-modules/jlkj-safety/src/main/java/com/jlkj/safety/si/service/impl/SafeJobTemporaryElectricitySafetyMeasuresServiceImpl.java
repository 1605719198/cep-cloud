package com.jlkj.safety.si.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.safety.si.entity.SafeSiJobTemporaryElectricitySafetyMeasures;
import com.jlkj.safety.si.mapper.SafeJobTemporaryElectricitySafetyMeasuresMapper;
import com.jlkj.safety.si.service.SafeJobTemporaryElectricitySafetyMeasuresService;
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
public class SafeJobTemporaryElectricitySafetyMeasuresServiceImpl extends ServiceImpl<SafeJobTemporaryElectricitySafetyMeasuresMapper, SafeSiJobTemporaryElectricitySafetyMeasures> implements SafeJobTemporaryElectricitySafetyMeasuresService {

    @Resource
    private SafeJobTemporaryElectricitySafetyMeasuresMapper safeJobTemporaryElectricitySafetyMeasuresMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object confirmSafeJobTemporaryElectricitySafetyMeasures(Map<String, Object> params) {
        SafeSiJobTemporaryElectricitySafetyMeasures safeSiJobTemporaryElectricitySafetyMeasures = getById(params.get("id").toString());
        safeSiJobTemporaryElectricitySafetyMeasures.setConfirmPersonId(params.get("confirm_person_id").toString());
        safeSiJobTemporaryElectricitySafetyMeasures.setConfirmPersonName(params.get("confirm_person_name").toString());
        safeSiJobTemporaryElectricitySafetyMeasures.setSafetyMeasures(params.get("safety_measures").toString());
        safeSiJobTemporaryElectricitySafetyMeasures.setIsConfirm(1);
        safeSiJobTemporaryElectricitySafetyMeasures.setConfirmTime(DateUtil.parseLocalDateTime(DateUtil.now()));
        if (updateById(safeSiJobTemporaryElectricitySafetyMeasures)) {
            return ResponseUtil.toResult(params, "安全措施确认成功");
        }
        else {
            return ResponseUtil.toError(params, "安全措施确认失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object cancelSafeJobTemporaryElectricitySafetyMeasures(Map<String, Object> params) {
        SafeSiJobTemporaryElectricitySafetyMeasures safeSiJobTemporaryElectricitySafetyMeasures = getById(params.get("id").toString());
        if (null != safeSiJobTemporaryElectricitySafetyMeasures) {
            Map<String, Object> queryParam = new HashMap<>(1);
            queryParam.put("job_id", safeSiJobTemporaryElectricitySafetyMeasures.getJobId());
            queryParam.put("sort", safeSiJobTemporaryElectricitySafetyMeasures.getSort());
            Map<String, Object> oldData = safeJobTemporaryElectricitySafetyMeasuresMapper.getOldSafeJobMeasures(queryParam);
            if (null != oldData) {
                safeSiJobTemporaryElectricitySafetyMeasures.setConfirmPersonId("");
                safeSiJobTemporaryElectricitySafetyMeasures.setConfirmPersonName("");
                safeSiJobTemporaryElectricitySafetyMeasures.setSafetyMeasures(oldData.get("safety_measures").toString());
                safeSiJobTemporaryElectricitySafetyMeasures.setIsConfirm(0);
                safeSiJobTemporaryElectricitySafetyMeasures.setConfirmTime(null);
                if (updateById(safeSiJobTemporaryElectricitySafetyMeasures)) {
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
