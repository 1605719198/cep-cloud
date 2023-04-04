package com.jlkj.product.oi.dto.coalconfigmanual;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author zyf
 * @Description
 * @create 2022-12-06 14:48
 */
@Data
public class SaveOrUpdateManualDTO {

    @Schema(title = "开始时间")
    private String start_time;

    @Schema(title = "结束时间")
    private String end_time;

    @Schema(title = "班次")
    private String shift_name;

    @Schema(title = "班别")
    private String class_name;

    @Schema(title = "上班时间")
    private String shift_start_time;

    @Schema(title = "下班时间")
    private String shift_end_time;

    @Schema(title = "配煤计划id")
    private String plan_id;

    @Schema(title = "焦炭等级料号")
    private String coal_level_materials_code;

    @Schema(title = "焦炭等级名称")
    private String coal_level_materials_name;

    @Schema(title = "储煤塔号")
    private Integer coal_tower_number;

    @Schema(title = "配煤重量")
    private BigDecimal coal_weight;

    @Schema(title = "配煤前重量")
    private BigDecimal coal_weight_befor;

    @Schema(title = "配煤后重量")
    private BigDecimal coal_weight_after;

    @Schema(title = "配煤前料位")
    private BigDecimal coal_material_level_befor;

    @Schema(title = "配煤后料位")
    private BigDecimal coal_material_level_after;

    @Schema(title = "配煤时长")
    private BigDecimal duration;

    @Schema(title = "配煤仓数组", implementation = CoalBlendingSiloDTO.class)
    List<CoalBlendingSiloDTO> coal_blendings;

    private String user_id;

    private String user_name;
}
