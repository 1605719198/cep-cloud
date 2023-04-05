package com.jlkj.product.oi.dto.sysdictdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

/**
 * DTO类-字典数据表-修改
 *
 * @author sudeyou
 * @since 2022-11-02 09:46:29
 */
@Data
@Schema(description = "DTO类-字典数据表-修改")
public class UpdateSysDictDataDTO {
    /**
     * 字典编码
     */
    @NotNull(message = "字典编码为必填项")
    @Schema(description = "字典编码")
    @JsonProperty("dict_code")
    private Long dictCode;

    /**
     * 字典排序
     */
    @NotNull(message = "字典排序为必填项")
    @Schema(description = "字典排序")
    @JsonProperty("dict_sort")
    private Integer dictSort;

    /**
     * 字典标签
     */
    @NotNull(message = "字典标签为必填项")
    @NotEmpty(message = "字典标签不能为空")
    @Size(max = 100, message = "字典标签长度不能超过100")
    @Schema(description = "字典标签")
    @JsonProperty("dict_label")
    private String dictLabel;

    /**
     * 字典键值
     */
    @NotNull(message = "字典键值为必填项")
    @NotEmpty(message = "字典键值不能为空")
    @Size(max = 100, message = "字典键值长度不能超过100")
    @Schema(description = "字典键值")
    @JsonProperty("dict_value")
    private String dictValue;

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
     * 样式属性（其他样式扩展）
     */
    @NotNull(message = "样式属性（其他样式扩展）为必填项")
    @NotEmpty(message = "样式属性（其他样式扩展）不能为空")
    @Size(max = 100, message = "样式属性（其他样式扩展）长度不能超过100")
    @Schema(description = "样式属性（其他样式扩展）")
    @JsonProperty("css_class")
    private String cssClass;

    /**
     * 表格回显样式
     */
    @NotNull(message = "表格回显样式为必填项")
    @NotEmpty(message = "表格回显样式不能为空")
    @Size(max = 100, message = "表格回显样式长度不能超过100")
    @Schema(description = "表格回显样式")
    @JsonProperty("list_class")
    private String listClass;

    /**
     * 是否默认（Y是 N否）
     */
    @NotNull(message = "是否默认（Y是 N否）为必填项")
    @NotEmpty(message = "是否默认（Y是 N否）不能为空")
    @Size(max = 1, message = "是否默认（Y是 N否）长度不能超过1")
    @Schema(description = "是否默认（Y是 N否）")
    @JsonProperty("is_default")
    private String isDefault;

    /**
     * 状态（0正常 1停用）
     */
    @NotNull(message = "状态（0正常 1停用）为必填项")
    @NotEmpty(message = "状态（0正常 1停用）不能为空")
    @Size(max = 1, message = "状态（0正常 1停用）长度不能超过1")
    @Schema(description = "状态（0正常 1停用）")
    @JsonProperty("status")
    private String status;

    /**
     * 创建者
     */
    @NotNull(message = "创建者为必填项")
    @NotEmpty(message = "创建者不能为空")
    @Size(max = 64, message = "创建者长度不能超过64")
    @Schema(description = "创建者")
    @JsonProperty("create_by")
    private String createBy;

    /**
     * 更新者
     */
    @NotNull(message = "更新者为必填项")
    @NotEmpty(message = "更新者不能为空")
    @Size(max = 64, message = "更新者长度不能超过64")
    @Schema(description = "更新者")
    @JsonProperty("update_by")
    private String updateBy;

    /**
     * 更新时间
     */
    @NotNull(message = "更新时间为必填项")
    @NotEmpty(message = "更新时间不能为空")
    @Schema(description = "更新时间")
    @JsonProperty("update_time")
    private String updateTime;

    /**
     * 备注
     */
    @NotNull(message = "备注为必填项")
    @NotEmpty(message = "备注不能为空")
    @Size(max = 500, message = "备注长度不能超过500")
    @Schema(description = "备注")
    @JsonProperty("remark")
    private String remark;

    /**
     * 字符型字段
     */
    @NotNull(message = "字符型字段为必填项")
    @NotEmpty(message = "字符型字段不能为空")
    @Size(max = 40, message = "字符型字段长度不能超过40")
    @Schema(description = "字符型字段")
    @JsonProperty("field1")
    private String field1;

    /**
     * 字符型字段
     */
    @NotNull(message = "字符型字段为必填项")
    @NotEmpty(message = "字符型字段不能为空")
    @Size(max = 40, message = "字符型字段长度不能超过40")
    @Schema(description = "字符型字段")
    @JsonProperty("field2")
    private String field2;

    /**
     * 字符型字段
     */
    @NotNull(message = "字符型字段为必填项")
    @NotEmpty(message = "字符型字段不能为空")
    @Size(max = 40, message = "字符型字段长度不能超过40")
    @Schema(description = "字符型字段")
    @JsonProperty("field3")
    private String field3;

    /**
     * 数值型字段
     */
    @NotNull(message = "数值型字段为必填项")
    @Schema(description = "数值型字段")
    @JsonProperty("field4")
    private BigDecimal field4;

    /**
     * 数值型字段
     */
    @NotNull(message = "数值型字段为必填项")
    @Schema(description = "数值型字段")
    @JsonProperty("field5")
    private BigDecimal field5;

}

