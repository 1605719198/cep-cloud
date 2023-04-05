package com.jlkj.product.oi.dto.sysdictdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * DTO类-字典数据表-删除
 *
 * @author sudeyou
 * @since 2022-11-02 09:46:29
 */
@Data
@Schema(description = "DTO类-字典数据表-删除")
public class DeleteSysDictDataDTO {
    /**
     * 字典编码
     */
    @NotNull(message = "字典编码为必填项")
    @Schema(description = "字典编码")
    @JsonProperty("dict_code")
    private Long dictCode;

}

