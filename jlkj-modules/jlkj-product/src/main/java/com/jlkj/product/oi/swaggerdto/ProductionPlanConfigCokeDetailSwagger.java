package com.jlkj.product.oi.swaggerdto;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
* @author zyf
* @TableName 计划管理--配煤计划配煤录
* @TableName product_oi_plan_config_coke_detail
*/
@Data
public class ProductionPlanConfigCokeDetailSwagger implements Serializable {

@Schema(title = "主键标识;UUID")
@TableId
private String id;

@Schema(title = "计划编号")
private String plan_id;

@Schema(title = "配煤仓号")
private String warehouse_number;

@Schema(title = "煤种编号")
private String coal_type_id;

@Schema(title = "煤种名称")
private String coal_type_name;

@Schema(title = "配置比例")
private String proportion;

@Schema(title = "创建人编号")
private String create_user_id;

@Schema(title = "创建人")
private String create_user_name;

@Schema(title = "创建时间")
private String create_time;

@Schema(title = "修改人编号")
private String modify_user_id;

@Schema(title = "修改人")
private String modify_user_name;

@Schema(title = "修改时间")
private String modify_time;

}
