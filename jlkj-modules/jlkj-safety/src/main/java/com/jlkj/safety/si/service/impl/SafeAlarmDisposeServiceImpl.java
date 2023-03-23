package com.jlkj.safety.si.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.safety.si.entity.Empty;
import com.jlkj.safety.si.mapper.SafeAlarmDisposeMapper;
import com.jlkj.safety.si.service.SafeAlarmDisposeService;
import com.jlkj.safety.si.utils.ResponseUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Map;

/**
 * <p>
 * 安全报警处理 服务实现类
 * </p>
 *
 * @author cy
 * @since 2021-04-26
 */
@Service
public class SafeAlarmDisposeServiceImpl extends ServiceImpl<SafeAlarmDisposeMapper, Empty> implements SafeAlarmDisposeService {

    @Resource
    private SafeAlarmDisposeMapper safeAlarmDisposeMapper;

    @Override
    @Transactional(readOnly = true)
    public Object getSafeAlarmDisposePageList(Map<String, Object> params) {
        return ResponseUtil.toResult(params, safeAlarmDisposeMapper.getSafeAlarmDisposePageList(ResponseUtil.initPage(params), params));
    }

    @Override
    @Transactional(readOnly = true)
    public Object getSafeAlarmDisposeById(Map<String, Object> params) {
        Map<String, Object> data = safeAlarmDisposeMapper.getSafeAlarmDisposeById(params);
        if (null == data || data.isEmpty()) {
            return ResponseUtil.toError(params, "安全报警记录不存在，或已停止报警");
        }
        else {
            return ResponseUtil.toResult(params, data);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object addNewSafeAlarmDispose(Map<String, Object> params) {
        if(safeAlarmDisposeMapper.addNewSafeAlarmDispose(params) > 0) {
            return ResponseUtil.toResult(params, "安全报警上报成功");
        }
        else {
            return ResponseUtil.toError(params, "安全报警上报失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object addAutoSafeAlarmDispose(Map<String, Object> params) {
        if(safeAlarmDisposeMapper.addAutoSafeAlarmDispose(params) > 0) {
            return ResponseUtil.toResult(params, "安全报警上报成功");
        }
        else {
            return ResponseUtil.toError(params, "安全报警上报失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object doDisposeSafeAlarmDispose(Map<String, Object> params) {
        if(safeAlarmDisposeMapper.doDisposeSafeAlarmDispose(params) > 0) {
            return ResponseUtil.toResult(params, "安全报警处理成功");
        }
        else {
            return ResponseUtil.toError(params, "安全报警处理失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object doConfirmSafeAlarmDispose(Map<String, Object> params) {
        if(safeAlarmDisposeMapper.doConfirmSafeAlarmDispose(params) > 0) {
            return ResponseUtil.toResult(params, "安全报警确认成功");
        }
        else {
            return ResponseUtil.toError(params, "安全报警确认失败");
        }
    }
}
