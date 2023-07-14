package com.jlkj.product.oi.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.product.oi.domain.DispatchLog;
import com.jlkj.product.oi.dto.dispatchlog.*;

import java.util.Map;

/**
 * 服务接口-调度日志
 * @author sudeyou
 */
public interface DispatchLogService extends IService<DispatchLog> {

    /**
     * 查询-分页-调度日志
     * @param pageDispatchLogDTO 查询条件dto
     * @return 分页数据
     */
    IPage<Map<String, String>> getPageData(PageDispatchLogDTO pageDispatchLogDTO);

    /**
     * 新增-调度日志
     * @param addDispatchLogDTO 新增dto
     * @return 执行结果
     */
    void addData(AddDispatchLogDTO addDispatchLogDTO);

    /**
     * 修改-调度日志
     * @param updateDispatchLogDTO 修改dto
     * @return 执行结果
     */
    void updateData(UpdateDispatchLogDTO updateDispatchLogDTO);

    /**
     * 删除-调度日志
     * @param deleteDispatchLogDTO 删除dto
     * @return 执行结果
     */
    void deleteData(DeleteDispatchLogDTO deleteDispatchLogDTO);

    /**
     * 指令下发-调度日志
     * @param sendDispatchLogDTO 修改dto
     * @return 执行结果
     */
    void updateSendData(SendDispatchLogDTO sendDispatchLogDTO);

    /**
     * 处理结果反馈-调度日志
     * @param feedbackDispatchLogDTO 修改dto
     * @return 执行结果
     */
    void updateFeedbackData(FeedbackDispatchLogDTO feedbackDispatchLogDTO);

    /**
     * 完成确认-调度日志
     * @param completeDispatchLogDTO 修改dto
     * @return 执行结果
     */
    void updateCompleteData(CompleteDispatchLogDTO completeDispatchLogDTO);
}

