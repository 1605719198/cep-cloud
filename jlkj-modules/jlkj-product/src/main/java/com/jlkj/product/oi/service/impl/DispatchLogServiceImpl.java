package com.jlkj.product.oi.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.common.core.exception.ServiceException;
import com.jlkj.product.oi.domain.DispatchLog;
import com.jlkj.product.oi.dto.dispatchlog.*;
import com.jlkj.product.oi.enums.DispatchLogStatus;
import com.jlkj.product.oi.mapper.DispatchLogMapper;
import com.jlkj.product.oi.service.DispatchLogService;
import org.springframework.beans.BeanUtils;
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

    /**
     * AjaxResult
     * @param pageDispatchLogDTO 查询条件dto
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public IPage<Map<String, String>> getPageData(PageDispatchLogDTO pageDispatchLogDTO) {
        Page<Map<String, String>> page = new Page<>(pageDispatchLogDTO.getCurrent(), pageDispatchLogDTO.getSize());
        return getBaseMapper().getPageData(page, pageDispatchLogDTO);
    }

    /**
     * 新增-调度日志
     * @param addDispatchLogDTO 新增dto
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addData(AddDispatchLogDTO addDispatchLogDTO) {
        DispatchLog dispatchLog = new DispatchLog();
        BeanUtils.copyProperties(addDispatchLogDTO,dispatchLog);
        dispatchLog.setId(IdUtil.randomUUID());
        dispatchLog.setStateFlag(DispatchLogStatus.NOT_SEND.getStatus());
        dispatchLog.setCreateTime(new Date());
        dispatchLog.setDispathTime(DateUtil.parseDateTime(addDispatchLogDTO.getDispathTime()));
        save(dispatchLog);
    }

    /**
     * 修改-调度日志
     * @param updateDispatchLogDTO 修改dto
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateData(UpdateDispatchLogDTO updateDispatchLogDTO) {
        DispatchLog dispatchLog = getById(updateDispatchLogDTO.getId());
        if (null != dispatchLog) {
            if (dispatchLog.getStateFlag() != DispatchLogStatus.NOT_SEND.getStatus()) {
                throw new ServiceException("对不起，只能修改未下发的调度日志");
            }
            dispatchLog.setWorkerId(updateDispatchLogDTO.getWorkerId());
            dispatchLog.setWorkerName(updateDispatchLogDTO.getWorkerName());
            dispatchLog.setWorkContent(updateDispatchLogDTO.getWorkContent());
            dispatchLog.setDispathTime(DateUtil.parseDateTime(updateDispatchLogDTO.getDispathTime()));
            updateById(dispatchLog);
        }
        else {
            throw new ServiceException("调度日志不存在");
        }
    }

    /**
     * 删除-调度日志
     * @param deleteDispatchLogDTO 删除dto
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteData(DeleteDispatchLogDTO deleteDispatchLogDTO) {
        DispatchLog dispatchLog = getById(deleteDispatchLogDTO.getId());
        if (null != dispatchLog) {
            if (dispatchLog.getStateFlag() != DispatchLogStatus.NOT_SEND.getStatus()) {
                throw new ServiceException("对不起，只能删除未下发的调度日志");
            }
            removeById(dispatchLog);
        }
        else {
            throw new ServiceException("调度日志不存在或已被删除");
        }
    }

    /**
     * 指令下发-调度日志
     * @param sendDispatchLogDTO 修改dto
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateSendData(SendDispatchLogDTO sendDispatchLogDTO) {
        DispatchLog dispatchLog = getById(sendDispatchLogDTO.getId());
        if (null != dispatchLog) {
            if (dispatchLog.getStateFlag() != DispatchLogStatus.NOT_SEND.getStatus()) {
                throw new ServiceException("对不起，只能对未下发的调度日志进行下发操作");
            }
            dispatchLog.setStateFlag(DispatchLogStatus.NOT_FINISHED.getStatus());
            updateById(dispatchLog);
        }
        else {
            throw new ServiceException("调度日志不存在");
        }
    }

    /**
     * 处理结果反馈-调度日志
     * @param feedbackDispatchLogDTO 修改dto
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateFeedbackData(FeedbackDispatchLogDTO feedbackDispatchLogDTO) {
        DispatchLog dispatchLog = getById(feedbackDispatchLogDTO.getId());
        if (null != dispatchLog) {
            dispatchLog.setDisposeContent(feedbackDispatchLogDTO.getDisposeContent());
            dispatchLog.setDispose(feedbackDispatchLogDTO.getDispose());
            dispatchLog.setDisposeTime(new Date());
            updateById(dispatchLog);
        }
        else {
            throw new ServiceException("调度日志不存在");
        }
    }

    /**
     * 完成确认-调度日志
     * @param completeDispatchLogDTO 修改dto
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateCompleteData(CompleteDispatchLogDTO completeDispatchLogDTO) {
        DispatchLog dispatchLog = getById(completeDispatchLogDTO.getId());
        if (null != dispatchLog) {
            if (dispatchLog.getStateFlag() == DispatchLogStatus.FINISHED.getStatus()) {
                throw new ServiceException("该调度日志已完成确认");
            }
            dispatchLog.setStateFlag(DispatchLogStatus.FINISHED.getStatus());
            updateById(dispatchLog);
        }
        else {
            throw new ServiceException("调度日志不存在");
        }
    }
}

