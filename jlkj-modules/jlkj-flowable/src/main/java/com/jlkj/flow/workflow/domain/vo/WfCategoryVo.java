package com.jlkj.flow.workflow.domain.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.jlkj.common.core.annotation.Excel;
import lombok.Data;


/**
 * 流程分类视图对象 flow_category
 *
 * @author xin
 * @date 2022-01-15
 */
@Data
public class WfCategoryVo {

    private static final long serialVersionUID = 1L;

    /**
     * 分类ID
     */
    @Excel(name = "分类ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long categoryId;

    /**
     * 分类名称
     */
    @Excel(name = "分类名称")
    private String categoryName;

    /**
     * 分类编码
     */
    @Excel(name = "分类编码")
    private String code;

    /**
     * 备注
     */
    @Excel(name = "备注")
    private String remark;


}
