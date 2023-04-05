package com.jlkj.product.oi.dto.changelog;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * DTO类-变更日志-新增
 * @author sudeyou
 * @since 2022-09-21 10:57:59
 */
@Data
@Schema(description = "DTO类-变更日志-新增")
public class InsertChangeLogDTO {
    /**
     * 模块名称
     */
    @NotNull(message = "模块名称为必填项")
    @NotEmpty(message = "模块名称不能为空")
    @Size(max = 50, message = "模块名称长度不能超过50")
    @Schema(description = "模块名称")
    @JsonProperty("module_name")
    private String moduleName;

    /**
     * 功能名称
     */
    @NotNull(message = "功能名称为必填项")
    @NotEmpty(message = "功能名称不能为空")
    @Size(max = 50, message = "功能名称长度不能超过50")
    @Schema(description = "功能名称")
    @JsonProperty("function_name")
    private String functionName;

    /**
     * 变更内容
     */
    @NotNull(message = "变更内容为必填项")
    @NotEmpty(message = "变更内容不能为空")
    @Size(max = 65535, message = "变更内容长度不能超过65535")
    @Schema(description = "变更内容")
    @JsonProperty("content")
    private String content;

    /**
     * 创建人id
     */
    @NotNull(message = "创建人id为必填项")
    @NotEmpty(message = "创建人id不能为空")
    @Size(max = 36, message = "创建人id长度不能超过36")
    @Schema(description = "创建人id")
    @JsonProperty("create_user_id")
    private String createUserId;

    /**
     * 创建人
     */
    @NotNull(message = "创建人为必填项")
    @NotEmpty(message = "创建人不能为空")
    @Size(max = 50, message = "创建人长度不能超过50")
    @Schema(description = "创建人")
    @JsonProperty("create_user_name")
    private String createUserName;

}

