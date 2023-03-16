package com.jlkj.flow.workflow.domain.vo;

import com.jlkj.common.core.annotation.Excel;
import lombok.Data;

import java.util.Date;


/**
 * 流程部署视图对象
 *
 * @author xin
 * @date 2022-06-30
 */
@Data
public class WfDeployVo {

    private static final long serialVersionUID = 1L;

    /**
     * 流程定义ID
     */
    @Excel(name = "流程定义ID")
    private String definitionId;

    /**
     * 流程名称
     */
    @Excel(name = "流程名称")
    private String processName;

    /**
     * 流程Key
     */
    @Excel(name = "流程Key")
    private String processKey;

    /**
     * 分类编码
     */
    @Excel(name = "分类编码")
    private String category;

    /**
     * 版本
     */
    private Integer version;

    /**
     * 表单ID
     */
    @Excel(name = "表单ID")
    private Long formId;

    /**
     * 表单名称
     */
    @Excel(name = "表单名称")
    private String formName;

    /**
     * 部署ID
     */
    @Excel(name = "部署ID")
    private String deploymentId;

    /**
     * 流程定义状态: 1:激活 , 2:中止
     */
    @Excel(name = "流程定义状态: 1:激活 , 2:中止")
    private Boolean suspended;

    /**
     * 部署时间
     */
    @Excel(name = "部署时间")
    private Date deploymentTime;
}
