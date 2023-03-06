package com.jlkj.material.mr.dto.sysdictdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * DTO类-获取字典数据列表
 *
 * @author sudeyou
 * @since 2022-11-02 09:46:29
 */
@Data
@Schema(description = "DTO类-获取字典数据列表")
public class ListSysDictDataDTO {
    /**
     * 字典类型
     */
    @NotNull(message = "字典类型为必填项")
    @NotEmpty(message = "字典类型不能为空")
    @Size(max = 100, message = "字典类型长度不能超过100")
    @Schema(description = "字典类型")
    @JsonProperty("dict_type")
    private String dictType;

    /**
     * 字典标签
     */
    @NotNull(message = "字典类型为必填项")
    @Schema(description = "字典标签")
    @JsonProperty("dict_label")
    private String dictLabel;

}

