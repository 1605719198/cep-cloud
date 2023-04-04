package com.jlkj.product.oi.vo.sysdictdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * VO类-获取字典数据列表
 *
 * @author sudeyou
 * @since 2022-11-02 09:46:29
 */
@Data
@Schema(description = "VO类-获取字典数据列表")
public class ListSysDictDataVO {
    /**
     * 字典标签
     */
    @Schema(description = "字典标签")
    @JsonProperty("dict_label")
    private String dictLabel;

    /**
     * 字典键值
     */
    @Schema(description = "字典键值")
    @JsonProperty("dict_value")
    private String dictValue;

}

