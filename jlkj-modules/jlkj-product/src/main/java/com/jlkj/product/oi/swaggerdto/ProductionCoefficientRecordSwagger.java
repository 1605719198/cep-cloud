package com.jlkj.product.oi.swaggerdto;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
* @author zyf
* @TableName 焦炉系数记录
* @TableName product_oi_coefficient_record
*/
@Data
public class ProductionCoefficientRecordSwagger implements Serializable {

@Schema(title = "班别")
private String class_name;

@Schema(title = "炉号")
private String coke_oven_number;

@Schema(title = "主键标识;UUID")
@TableId
private String id;

@Schema(title = "K1")
private String k1;

@Schema(title = "K2")
private String k2;

@Schema(title = "K3")
private String k3;

@Schema(title = "K安")
private String k_an;

@Schema(title = "K均")
private String k_jun;

@Schema(title = "测温日期")
private String measure_time;

@Schema(title = "班次")
private String shift_name;

}
