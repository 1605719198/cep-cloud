package com.jlkj.product.oi.dto.productionconfigurecoalspeciesperformancedetail;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author zyf
 * @Description
 * @create 2022-11-09 16:29
 */
@Data
public class SaveOrUpdateDTO {

    @Schema(title = "主键标识;UUID")
    private String id;

    @Schema(title = "主记录编号")
    private String main_id;

    @Schema(title = "班次")
    private String shift_name;

    @Schema(title = "班别")
    private String class_name;

    @Schema(title = "计划编号")
    private String plan_id;

    @Schema(title = "上煤仓号")
    private Integer bunker_number;

    @Schema(title = "储煤塔号")
    private Integer coal_tower_number;

    @Schema(title = "配煤前料位")
    private BigDecimal before_material_level;

    @Schema(title = "配煤后料位")
    private BigDecimal after_material_level;

    @Schema(title = "配煤前存量")
    private BigDecimal before_stock;

    @Schema(title = "配煤后存量")
    private BigDecimal after_stock;

    @Schema(title = "配煤日期")
    private Date coal_delivery_date;

    @Schema(title = "开始时间")
    private Date start_time;

    @Schema(title = "结束时间")
    private Date end_time;

    @Schema(title = "配煤时长")
    private BigDecimal duration;

    @Schema(title = "配煤重量")
    private BigDecimal material_weight;

    @Schema(title = "物料编号")
    private String material_number;

    @Schema(title = "物料名称")
    private String material_name;

    @Schema(title = "物料代码")
    private String material_code;

    @Schema(title = "用户id")
    private String user_id;

    @Schema(title = "用户名称")
    private String user_name;
}
