package com.jlkj.material.mr.vo.materialscode;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * VO类-物料代码下拉列表-类型(煤/焦)
 *
 * @author sudeyou
 * @since 2022-11-03 17:44:09
 */
@Data
@Schema(description = "VO类-物料代码下拉列表-类型(煤/焦)")
public class ListMaterialsCodeByTypeVO {
    /**
     * 主键标识;UUID
     */
    @Schema(description = "主键标识;UUID")
    @JsonProperty("id")
    private String id;

    /**
     * 物料名称
     */
    @Schema(description = "物料名称")
    @JsonProperty("materials_name")
    private String materialsName;

    /**
     * 物料代码
     */
    @Schema(description = "物料代码")
    @JsonProperty("materials_code")
    private String materialsCode;

}

