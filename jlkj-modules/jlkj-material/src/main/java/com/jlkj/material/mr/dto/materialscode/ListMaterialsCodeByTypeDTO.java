package com.jlkj.material.mr.dto.materialscode;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * DTO类-物料代码下拉列表-类型(煤/焦)
 *
 * @author sudeyou
 * @since 2022-11-03 17:44:09
 */
@Data
@Schema(description = "DTO类-物料代码下拉列表-类型(煤/焦)")
public class ListMaterialsCodeByTypeDTO {
    /**
     * 00：煤   01：焦炭
     */
    @NotNull(message = "00：煤   01：焦炭为必填项")
    @Size(max = 2, message = "00：煤   01：焦炭长度不能超过2")
    @Schema(description = "00：煤   01：焦炭")
    @JsonProperty("mtrl_type")
    private String mtrlType;

}

