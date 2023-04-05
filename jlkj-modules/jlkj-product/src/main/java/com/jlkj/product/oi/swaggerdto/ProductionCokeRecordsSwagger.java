package com.jlkj.product.oi.swaggerdto;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
* @author zyf
* @TableName 焦炭产量记录
* @TableName product_oi_coke_records
*/
@Data
public class ProductionCokeRecordsSwagger implements Serializable {

@Schema(title = "班组结束时间")
private String class_end_time;

@Schema(title = "班别")
private String class_name;

@Schema(title = "班组开始时间")
private String class_start_time;

@Schema(title = "主键标识;UUID")
@TableId
private String id;

@Schema(title = "物料代码")
private String material_code;

@Schema(title = "物料名称(产品)")
private String material_name;

@Schema(title = "产出量")
private String material_weight;

@Schema(title = "班次")
private String shift_name;

}
