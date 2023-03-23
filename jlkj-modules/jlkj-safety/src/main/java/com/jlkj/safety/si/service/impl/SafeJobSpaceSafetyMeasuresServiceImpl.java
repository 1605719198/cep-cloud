package com.jlkj.safety.si.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.safety.si.entity.SafeSiJobSpaceSafetyMeasures;
import com.jlkj.safety.si.mapper.SafeJobSpaceSafetyMeasuresMapper;
import com.jlkj.safety.si.service.SafeJobSpaceSafetyMeasuresService;
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
public class SafeJobSpaceSafetyMeasuresServiceImpl extends ServiceImpl<SafeJobSpaceSafetyMeasuresMapper, SafeSiJobSpaceSafetyMeasures> implements SafeJobSpaceSafetyMeasuresService {

    @Resource
    private SafeJobSpaceSafetyMeasuresMapper safeJobSpaceSafetyMeasuresMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object confirmSafeJobSpaceSafetyMeasures(Map<String, Object> params) {
        SafeSiJobSpaceSafetyMeasures safeSiJobSpaceSafetyMeasures = getById(params.get("id").toString());
        safeSiJobSpaceSafetyMeasures.setConfirmPersonId(params.get("confirm_person_id").toString());
        safeSiJobSpaceSafetyMeasures.setConfirmPersonName(params.get("confirm_person_name").toString());
        safeSiJobSpaceSafetyMeasures.setSafetyMeasures(params.get("safety_measures").toString());
        safeSiJobSpaceSafetyMeasures.setIsConfirm(1);
        safeSiJobSpaceSafetyMeasures.setConfirmTime(DateUtil.parseLocalDateTime(DateUtil.now()));
        if (updateById(safeSiJobSpaceSafetyMeasures)) {
            return ResponseUtil.toResult(params, "安全措施确认成功");
        }
        else {
            return ResponseUtil.toError(params, "安全措施确认失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object cancelSafeJobSpaceSafetyMeasures(Map<String, Object> params) {
        SafeSiJobSpaceSafetyMeasures safeSiJobSpaceSafetyMeasures = getById(params.get("id").toString());
        if (null != safeSiJobSpaceSafetyMeasures) {
            Map<String, Object> queryParam = new HashMap<>(1);
            queryParam.put("job_id", safeSiJobSpaceSafetyMeasures.getJobId());
            queryParam.put("sort", safeSiJobSpaceSafetyMeasures.getSort());
            Map<String, Object> oldData = safeJobSpaceSafetyMeasuresMapper.getOldSafeJobMeasures(queryParam);
            if (null != oldData) {
                safeSiJobSpaceSafetyMeasures.setConfirmPersonId("");
                safeSiJobSpaceSafetyMeasures.setConfirmPersonName("");
                safeSiJobSpaceSafetyMeasures.setSafetyMeasures(oldData.get("safety_measures").toString());
                safeSiJobSpaceSafetyMeasures.setIsConfirm(0);
                safeSiJobSpaceSafetyMeasures.setConfirmTime(null);
                if (updateById(safeSiJobSpaceSafetyMeasures)) {
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
