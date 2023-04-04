package com.jlkj.product.oi.swaggerdto;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
* @author zyf
* @TableName 生产实绩-配煤仓配煤记录
* @TableName product_oi_coal_warehouse_blended_coal_record
*/
@Data
public class ProductionCoalWarehouseBlendedCoalRecordSwagger implements Serializable {

@Schema(title = "主键标识;UUID")
@TableId
private String id;

@Schema(title = "班次代码")
private String shift_name;

@Schema(title = "班别名称")
private String class_name;

@Schema(title = "储煤塔配煤记录编号")
private String coal_tower_record_id;

@Schema(title = "配煤实绩编号")
private String blended_coal_actual_performance_id;

@Schema(title = "计划编号")
private String plan_id;

@Schema(title = "焦炭等级料号")
private String coal_level_materials_code;

@Schema(title = "焦炭等级名称")
private String coal_level_materials_name;

@Schema(title = "配煤仓号")
private String bunker_number;

@Schema(title = "储煤塔号")
private String coal_tower_number;

@Schema(title = "配煤前料位")
private String before_material_level;

@Schema(title = "配煤后料位")
private String after_material_level;

@Schema(title = "配煤前存量")
private String before_stock;

@Schema(title = "配煤后存量")
private String after_stock;

@Schema(title = "开始时间")
private String start_time;

@Schema(title = "结束时间")
private String end_time;

@Schema(title = "配煤时长")
private String duration;

@Schema(title = "配煤重量")
private String material_weight;

@Schema(title = "物料编号")
private String material_number;

@Schema(title = "物料名称")
private String material_name;

@Schema(title = "物料代码")
private String material_code;

@Schema(title = "数据来源（0:手工填报；1:数据采集）")
private String data_sources;

@Schema(title = "创建人")
private String create_user_id;

@Schema(title = "创建人")
private String create_user_name;

@Schema(title = "创建时间")
private String create_time;

@Schema(title = "修改人id")
private String modify_user_id;

@Schema(title = "修改人")
private String modify_user_name;

@Schema(title = "修改时间")
private String modify_time;

@Schema(title = "上班时间")
private String shift_start_time;

@Schema(title = "下班时间")
private String shift_end_time;

}
