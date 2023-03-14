package com.jlkj.workflow.domain.vo;

import com.jlkj.common.core.annotation.Excel;
import lombok.Data;

import java.util.Date;


/**
 * 流程定义视图对象 workflow_definition
 *
 * @author xin
 * @date 2022-01-17
 */
@Data
public class WfDefinitionVo {

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
    @Excel(name = "版本")
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
     * 流程是否暂停（true:挂起 false:激活 ）
     */
    @Excel(name = "流程是否挂起", readConverterExp = "true=挂起,false=激活")
    private Boolean suspended;

    /**
     * 部署时间
     */
    @Excel(name = "部署时间")
    private Date deploymentTime;
}
