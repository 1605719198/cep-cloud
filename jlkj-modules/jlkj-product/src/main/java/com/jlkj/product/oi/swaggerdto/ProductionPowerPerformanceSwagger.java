package com.jlkj.product.oi.swaggerdto;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
* @author zyf
* @TableName 发电量实绩
* @TableName product_oi_power_performance
*/
@Data
public class ProductionPowerPerformanceSwagger implements Serializable {

@Schema(title = "班组结束时间")
private String class_end_time;

@Schema(title = "班别")
private String class_name;

@Schema(title = "班组开始时间")
private String class_start_time;

@Schema(title = "除盐水补水量")
private String desalination_water_volume;

@Schema(title = "端差")
private String end_difference;

@Schema(title = "发电机组汽耗率")
private String generator_consumption_rate;

@Schema(title = "主键标识;UUID")
@TableId
private String id;

@Schema(title = "降低发电单位成本")
private String lower_unit_electricity;

@Schema(title = "新水消耗")
private String new_water_volume;

@Schema(title = "发电量")
private String power_generation;

@Schema(title = "上网电量")
private String power_internet;

@Schema(title = "发电厂用电量")
private String power_plant_energy;

@Schema(title = "班次")
private String shift_name;

@Schema(title = "机组进气量")
private String unit_air;

@Schema(title = "真空")
private String vacuum;

}
