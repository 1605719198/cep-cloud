package com.jlkj.system.dto.humanresourcepersonnel;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * DTO类-获取人员签章
 *
 * @author sudeyou
 * @since 2022-10-19 10:38:36
 */
@Data
@Schema(description = "DTO类-获取人员签章")
public class InfoHumanresourcePersonnelSignatureDTO {
    /**
     * 主键
     */
    @NotNull(message = "主键为必填项")
    @NotEmpty(message = "主键不能为空")
    @Size(max = 36, message = "主键长度不能超过36")
    @Schema(description = "主键")
    @JsonProperty("id")
    private String id;
}

