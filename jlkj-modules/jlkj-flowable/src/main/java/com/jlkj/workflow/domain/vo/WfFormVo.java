package com.jlkj.workflow.domain.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.jlkj.common.core.annotation.Excel;
import lombok.Data;

/**
 * 流程分类视图对象
 *
 * @author xin
 * @createTime 2022/3/7 22:07
 */
@Data
public class WfFormVo {

    private static final long serialVersionUID = 1L;

    /**
     * 表单主键
     */
    @Excel(name = "表单ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long formId;

    /**
     * 表单名称
     */
    @Excel(name = "表单名称")
    private String formName;

    /**
     * 表单内容
     */
    @Excel(name = "表单内容")
    private String content;

    /**
     * 备注
     */
    @Excel(name = "备注")
    private String remark;
}
