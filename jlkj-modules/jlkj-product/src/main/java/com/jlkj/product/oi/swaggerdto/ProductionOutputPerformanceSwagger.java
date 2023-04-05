package com.jlkj.product.oi.swaggerdto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 *
 * @author  generator
 */
@Data
public class ProductionOutputPerformanceSwagger implements Serializable {

@Schema(title = "主键标识;UUID")
private String id;

@Schema(title = "班次")
private String shift_name;

@Schema(title = "班别")
private String class_name;

@Schema(title = "炉号")
private String coke_oven_number;

@Schema(title = "炭化室号")
private String carbonization_chamber_number;

@Schema(title = "生产日期")
private String production_date;

@Schema(title = "计划推焦时间")
private String planning_production_time;

@Schema(title = "实际推焦时间")
private String actual_production_time;

@Schema(title = "推焦电流")
private String coke_current;

@Schema(title = "计划装煤时间")
private String planning_load_coal_time;

@Schema(title = "实际装煤时间")
private String actual_load_coal_time;

@Schema(title = "装煤电流")
private String coal_current;

@Schema(title = "计划运转周期")
private String planning_run_cycle;

@Schema(title = "实际运转周期")
private String actual_run_cycle;

@Schema(title = "单孔产量")
private String single_hole_production;

@Schema(title = "装煤量")
private String Loading_coal;

@Schema(title = "创建时间")
private String create_time;

}
