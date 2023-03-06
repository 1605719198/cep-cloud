package com.jlkj.material.mr.dto.materialscode;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * DTO类-物料代码下拉列表查询
 * @author sudeyou
 * @since 2022-09-09 10:43:18
 */
@Data
@Schema(description = "DTO类-物料代码下拉列表查询")
public class ListMaterialsCodeDTO {
    /**
     * 类别编号
     */
    @NotNull(message = "类别编号为必填项")
    @NotEmpty(message = "类别编号不能为空")
    @Size(max = 36, message = "类别编号长度不能超过36")
    @Schema(description = "类别编号")
    @JsonProperty("category_id")
    private String categoryId;

}

