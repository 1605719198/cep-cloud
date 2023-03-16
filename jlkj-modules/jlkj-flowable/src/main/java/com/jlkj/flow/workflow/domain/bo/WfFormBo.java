package com.jlkj.flow.workflow.domain.bo;

import com.jlkj.common.core.web.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 流程表单业务对象
 *
 * @author xin
 * @createTime 2022/3/7 22:07
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class WfFormBo extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 表单主键
     */
    @NotNull(message = "表单ID不能为空")
    private Long formId;

    /**
     * 表单名称
     */
    @NotBlank(message = "表单名称不能为空")
    private String formName;

    /**
     * 表单内容
     */
    @NotBlank(message = "表单内容不能为空")
    private String content;

    /**
     * 备注
     */
    private String remark;
}
