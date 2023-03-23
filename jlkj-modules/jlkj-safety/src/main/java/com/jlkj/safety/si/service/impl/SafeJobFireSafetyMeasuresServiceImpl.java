package com.jlkj.safety.si.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.safety.si.entity.SafeSiJobFireSafetyMeasures;
import com.jlkj.safety.si.mapper.SafeJobFireSafetyMeasuresMapper;
import com.jlkj.safety.si.service.SafeJobFireSafetyMeasuresService;
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
 * @since 2021-01-04
 */
@Service
public class SafeJobFireSafetyMeasuresServiceImpl extends ServiceImpl<SafeJobFireSafetyMeasuresMapper, SafeSiJobFireSafetyMeasures> implements SafeJobFireSafetyMeasuresService {

    @Resource
    private SafeJobFireSafetyMeasuresMapper safeJobFireSafetyMeasuresMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object confirmSafeJobFireSafetyMeasures(Map<String, Object> params) {
        SafeSiJobFireSafetyMeasures safeSiJobFireSafetyMeasures = getById(params.get("id").toString());
        safeSiJobFireSafetyMeasures.setConfirmPersonId(params.get("confirm_person_id").toString());
        safeSiJobFireSafetyMeasures.setConfirmPersonName(params.get("confirm_person_name").toString());
        safeSiJobFireSafetyMeasures.setSafetyMeasures(params.get("safety_measures").toString());
        safeSiJobFireSafetyMeasures.setIsConfirm(1);
        safeSiJobFireSafetyMeasures.setConfirmTime(DateUtil.parseLocalDateTime(DateUtil.now()));
        if (updateById(safeSiJobFireSafetyMeasures)) {
            return ResponseUtil.toResult(params, "安全措施确认成功");
        }
        else {
            return ResponseUtil.toError(params, "安全措施确认失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object cancelSafeJobFireSafetyMeasures(Map<String, Object> params) {
        SafeSiJobFireSafetyMeasures safeSiJobFireSafetyMeasures = getById(params.get("id").toString());
        if (null != safeSiJobFireSafetyMeasures) {
            Map<String, Object> queryParam = new HashMap<>(1);
            queryParam.put("sort", safeSiJobFireSafetyMeasures.getSort());
            Map<String, Object> oldData = safeJobFireSafetyMeasuresMapper.getOldSafeJobMeasures(queryParam);
            if (null != oldData) {
                safeSiJobFireSafetyMeasures.setConfirmPersonId("");
                safeSiJobFireSafetyMeasures.setConfirmPersonName("");
                safeSiJobFireSafetyMeasures.setSafetyMeasures(oldData.get("safety_measures").toString());
                safeSiJobFireSafetyMeasures.setIsConfirm(0);
                safeSiJobFireSafetyMeasures.setConfirmTime(null);
                if (updateById(safeSiJobFireSafetyMeasures)) {
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
