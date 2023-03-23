package com.jlkj.safety.si.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.safety.si.entity.SafeSiJobSoilSafetyMeasures;
import com.jlkj.safety.si.mapper.SafeJobSoilSafetyMeasuresMapper;
import com.jlkj.safety.si.service.SafeJobSoilSafetyMeasuresService;
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
public class SafeJobSoilSafetyMeasuresServiceImpl extends ServiceImpl<SafeJobSoilSafetyMeasuresMapper, SafeSiJobSoilSafetyMeasures> implements SafeJobSoilSafetyMeasuresService {

    @Resource
    private SafeJobSoilSafetyMeasuresMapper safeJobSoilSafetyMeasuresMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object confirmSafeJobSoilSafetyMeasures(Map<String, Object> params) {
        SafeSiJobSoilSafetyMeasures safeSiJobSoilSafetyMeasures = getById(params.get("id").toString());
        safeSiJobSoilSafetyMeasures.setConfirmPersonId(params.get("confirm_person_id").toString());
        safeSiJobSoilSafetyMeasures.setConfirmPersonName(params.get("confirm_person_name").toString());
        safeSiJobSoilSafetyMeasures.setSafetyMeasures(params.get("safety_measures").toString());
        safeSiJobSoilSafetyMeasures.setIsConfirm(1);
        safeSiJobSoilSafetyMeasures.setConfirmTime(DateUtil.parseLocalDateTime(DateUtil.now()));
        if (updateById(safeSiJobSoilSafetyMeasures)) {
            return ResponseUtil.toResult(params, "安全措施确认成功");
        }
        else {
            return ResponseUtil.toError(params, "安全措施确认失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object cancelSafeJobSoilSafetyMeasures(Map<String, Object> params) {
        SafeSiJobSoilSafetyMeasures safeSiJobSoilSafetyMeasures = getById(params.get("id").toString());
        if (null != safeSiJobSoilSafetyMeasures) {
            Map<String, Object> queryParam = new HashMap<>(1);
            queryParam.put("sort", safeSiJobSoilSafetyMeasures.getSort());
            Map<String, Object> oldData = safeJobSoilSafetyMeasuresMapper.getOldSafeJobMeasures(queryParam);
            if (null != oldData) {
                safeSiJobSoilSafetyMeasures.setConfirmPersonId("");
                safeSiJobSoilSafetyMeasures.setConfirmPersonName("");
                safeSiJobSoilSafetyMeasures.setSafetyMeasures(oldData.get("safety_measures").toString());
                safeSiJobSoilSafetyMeasures.setIsConfirm(0);
                safeSiJobSoilSafetyMeasures.setConfirmTime(null);
                if (updateById(safeSiJobSoilSafetyMeasures)) {
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
