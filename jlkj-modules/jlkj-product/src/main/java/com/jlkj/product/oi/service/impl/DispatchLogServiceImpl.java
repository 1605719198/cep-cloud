package com.jlkj.product.oi.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.product.oi.domain.DispatchLog;
import com.jlkj.product.oi.dto.dispatchlog.*;
import com.jlkj.product.oi.enums.DispatchLogStatus;
import com.jlkj.product.oi.mapper.DispatchLogMapper;
import com.jlkj.product.oi.service.DispatchLogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;

/**
 * 服务实现-调度日志
 * @author sudeyou
 */
@Service
public class DispatchLogServiceImpl extends ServiceImpl<DispatchLogMapper, DispatchLog>
    implements DispatchLogService {

    @Override
    @Transactional(readOnly = true)
    public IPage<Map<String, String>> getPageData(PageDispatchLogDTO pageDispatchLogDTO) {
        Page<Map<String, String>> page = new Page<>(pageDispatchLogDTO.getCurrent(), pageDispatchLogDTO.getSize());
        return getBaseMapper().getPageData(page, pageDispatchLogDTO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object addData(AddDispatchLogDTO addDispatchLogDTO) {
        DispatchLog dispatchLog = new DispatchLog();
        dispatchLog.setId(IdUtil.randomUUID());
        dispatchLog.setDispatcherId(addDispatchLogDTO.getDispatcherId());
        dispatchLog.setDispatcherName(addDispatchLogDTO.getDispatcherName());
        dispatchLog.setWorkerId(addDispatchLogDTO.getWorkerId());
        dispatchLog.setWorkerName(addDispatchLogDTO.getWorkerName());
        dispatchLog.setWorkContent(addDispatchLogDTO.getWorkContent());
        dispatchLog.setStateFlag(DispatchLogStatus.NOT_SEND.getStatus());
        dispatchLog.setCreateTime(new Date());
        dispatchLog.setDispathTime(DateUtil.parseDateTime(addDispatchLogDTO.getDispathTime()));
        save(dispatchLog);
        return AjaxResult.success("调度日志增加成功");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object updateData(UpdateDispatchLogDTO updateDispatchLogDTO) {
        DispatchLog dispatchLog = getById(updateDispatchLogDTO.getId());
        if (null != dispatchLog) {
            if (dispatchLog.getStateFlag() != DispatchLogStatus.NOT_SEND.getStatus()) {
                return AjaxResult.error("对不起，只能修改未下发的调度日志");
            }
            dispatchLog.setWorkerId(updateDispatchLogDTO.getWorkerId());
            dispatchLog.setWorkerName(updateDispatchLogDTO.getWorkerName());
            dispatchLog.setWorkContent(updateDispatchLogDTO.getWorkContent());
            dispatchLog.setDispathTime(DateUtil.parseDateTime(updateDispatchLogDTO.getDispathTime()));
            updateById(dispatchLog);
            return AjaxResult.success("调度日志修改成功");
        }
        else {
            return AjaxResult.error( "调度日志不存在");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object deleteData(DeleteDispatchLogDTO deleteDispatchLogDTO) {
        DispatchLog dispatchLog = getById(deleteDispatchLogDTO.getId());
        if (null != dispatchLog) {
            if (dispatchLog.getStateFlag() != DispatchLogStatus.NOT_SEND.getStatus()) {
                return AjaxResult.error( "对不起，只能删除未下发的调度日志");
            }
            removeById(dispatchLog);
            return AjaxResult.success("调度日志删除成功");
        }
        else {
            return AjaxResult.error("调度日志不存在或已被删除");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object updateSendData(SendDispatchLogDTO sendDispatchLogDTO) {
        DispatchLog dispatchLog = getById(sendDispatchLogDTO.getId());
        if (null != dispatchLog) {
            if (dispatchLog.getStateFlag() != DispatchLogStatus.NOT_SEND.getStatus()) {
                return AjaxResult.error("对不起，只能对未下发的调度日志进行下发操作");
            }
            dispatchLog.setStateFlag(DispatchLogStatus.NOT_FINISHED.getStatus());
            updateById(dispatchLog);
            return AjaxResult.success("调度日志指令下发成功");
        }
        else {
            return AjaxResult.error("调度日志不存在");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object updateFeedbackData(FeedbackDispatchLogDTO feedbackDispatchLogDTO) {
        DispatchLog dispatchLog = getById(feedbackDispatchLogDTO.getId());
        if (null != dispatchLog) {
            dispatchLog.setDisposeContent(feedbackDispatchLogDTO.getDisposeContent());
            dispatchLog.setDispose(feedbackDispatchLogDTO.getDispose());
            dispatchLog.setDisposeTime(new Date());
            updateById(dispatchLog);
            return AjaxResult.success("调度日志处理结果反馈成功");
        }
        else {
            return AjaxResult.error("调度日志不存在");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object updateCompleteData(CompleteDispatchLogDTO completeDispatchLogDTO) {
        DispatchLog dispatchLog = getById(completeDispatchLogDTO.getId());
        if (null != dispatchLog) {
            if (dispatchLog.getStateFlag() == DispatchLogStatus.FINISHED.getStatus()) {
                return AjaxResult.error("该调度日志已完成确认");
            }
            dispatchLog.setStateFlag(DispatchLogStatus.FINISHED.getStatus());
            updateById(dispatchLog);
            return AjaxResult.success("调度日志完成确认成功");
        }
        else {
            return AjaxResult.error("调度日志不存在");
        }
    }
}

