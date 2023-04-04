package com.jlkj.product.oi.swaggerdto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 *
 * @author  generator
 */

@Data
public class ProductionConveyingCoalPerformanceSwagger implements Serializable {

@Schema(title = "主键标识;UUID")
private String id;

@Schema(title = "班次")
private String shift_name;

@Schema(title = "班别")
private String class_name;

@Schema(title = "上煤仓号")
private String conveying_coal_warehouse_number;

@Schema(title = "堆取料机号")
private String stacker_reclaimer_number;

@Schema(title = "料仓编号")
private String silo_number;

@Schema(title = "料仓名称")
private String silo_name;

@Schema(title = "储位编号")
private String slot_number;

@Schema(title = "储位名称")
private String slot_name;

@Schema(title = "上煤开始")
private String start_time;

@Schema(title = "上煤结束")
private String end_time;

@Schema(title = "上煤时长")
private String duration;

@Schema(title = "上煤重量")
private String material_weight;

@Schema(title = "物料编号")
private String material_number;

@Schema(title = "物料名称")
private String material_name;

@Schema(title = "创建时间")
private String create_time;

}
