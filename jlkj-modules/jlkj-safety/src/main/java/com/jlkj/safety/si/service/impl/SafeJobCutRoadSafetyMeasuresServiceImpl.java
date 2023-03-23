package com.jlkj.safety.si.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.safety.si.entity.SafeSiJobCutRoadSafetyMeasures;
import com.jlkj.safety.si.mapper.SafeJobCutRoadSafetyMeasuresMapper;
import com.jlkj.safety.si.service.SafeJobCutRoadSafetyMeasuresService;
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
public class SafeJobCutRoadSafetyMeasuresServiceImpl extends ServiceImpl<SafeJobCutRoadSafetyMeasuresMapper, SafeSiJobCutRoadSafetyMeasures> implements SafeJobCutRoadSafetyMeasuresService {

    @Resource
    private SafeJobCutRoadSafetyMeasuresMapper safeJobCutRoadSafetyMeasuresMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object confirmSafeJobCutRoadSafetyMeasures(Map<String, Object> params) {
        SafeSiJobCutRoadSafetyMeasures safeSiJobCutRoadSafetyMeasures = getById(params.get("id").toString());
        safeSiJobCutRoadSafetyMeasures.setConfirmPersonId(params.get("confirm_person_id").toString());
        safeSiJobCutRoadSafetyMeasures.setConfirmPersonName(params.get("confirm_person_name").toString());
        safeSiJobCutRoadSafetyMeasures.setSafetyMeasures(params.get("safety_measures").toString());
        safeSiJobCutRoadSafetyMeasures.setIsConfirm(1);
        safeSiJobCutRoadSafetyMeasures.setConfirmTime(DateUtil.parseLocalDateTime(DateUtil.now()));
        if (updateById(safeSiJobCutRoadSafetyMeasures)) {
            return ResponseUtil.toResult(params, "安全措施确认成功");
        }
        else {
            return ResponseUtil.toError(params, "安全措施确认失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object cancelSafeJobCutRoadSafetyMeasures(Map<String, Object> params) {
        SafeSiJobCutRoadSafetyMeasures safeSiJobCutRoadSafetyMeasures = getById(params.get("id").toString());
        if (null != safeSiJobCutRoadSafetyMeasures) {
            Map<String, Object> queryParam = new HashMap<>(1);
            queryParam.put("sort", safeSiJobCutRoadSafetyMeasures.getSort());
            Map<String, Object> oldData = safeJobCutRoadSafetyMeasuresMapper.getOldSafeJobMeasures(queryParam);
            if (null != oldData) {
                safeSiJobCutRoadSafetyMeasures.setConfirmPersonId("");
                safeSiJobCutRoadSafetyMeasures.setConfirmPersonName("");
                safeSiJobCutRoadSafetyMeasures.setSafetyMeasures(oldData.get("safety_measures").toString());
                safeSiJobCutRoadSafetyMeasures.setIsConfirm(0);
                safeSiJobCutRoadSafetyMeasures.setConfirmTime(null);
                if (updateById(safeSiJobCutRoadSafetyMeasures)) {
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
