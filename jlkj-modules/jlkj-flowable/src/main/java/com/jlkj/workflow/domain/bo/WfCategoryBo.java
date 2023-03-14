package com.jlkj.workflow.domain.bo;

import com.jlkj.common.core.web.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 流程分类业务对象
 *
 * @author xin
 * @date 2022-01-15
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class WfCategoryBo extends BaseEntity {

    /**
     * 分类ID
     */
    @NotNull(message = "分类ID不能为空")
    private Long categoryId;

    /**
     * 分类名称
     */
    @NotBlank(message = "分类名称不能为空")
    private String categoryName;

    /**
     * 分类编码
     */
    @NotBlank(message = "分类编码不能为空")
    private String code;

    /**
     * 备注
     */
    @NotBlank(message = "备注不能为空")
    private String remark;


}
