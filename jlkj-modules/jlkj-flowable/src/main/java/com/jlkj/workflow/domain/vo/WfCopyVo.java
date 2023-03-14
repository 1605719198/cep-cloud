package com.jlkj.workflow.domain.vo;

import com.jlkj.common.core.annotation.Excel;
import lombok.Data;

import java.util.Date;


/**
 * 流程抄送视图对象 wf_copy
 *
 * @author ruoyi
 * @date 2022-05-19
 */
@Data
public class WfCopyVo {

    private static final long serialVersionUID = 1L;

    /**
     * 抄送主键
     */
    @Excel(name = "抄送主键")
    private Long copyId;

    /**
     * 抄送标题
     */
    @Excel(name = "抄送标题")
    private String title;

    /**
     * 流程主键
     */
    @Excel(name = "流程主键")
    private String processId;

    /**
     * 流程名称
     */
    @Excel(name = "流程名称")
    private String processName;

    /**
     * 流程分类主键
     */
    @Excel(name = "流程分类主键")
    private String categoryId;

    /**
     * 部署主键
     */
    @Excel(name = "部署主键")
    private String deploymentId;

    /**
     * 流程实例主键
     */
    @Excel(name = "流程实例主键")
    private String instanceId;

    /**
     * 任务主键
     */
    @Excel(name = "任务主键")
    private String taskId;

    /**
     * 用户主键
     */
    @Excel(name = "用户主键")
    private Long userId;

    /**
     * 发起人Id
     */
    @Excel(name = "发起人主键")
    private Long originatorId;

    /**
     * 发起人名称
     */
    @Excel(name = "发起人名称")
    private String originatorName;

    /**
     * 抄送时间（创建时间）
     */
    @Excel(name = "抄送时间")
    private Date createTime;
}
