package com.jlkj.product.oi.swaggerdto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 *
 * @author  generator
 */
@Data
public class ProductionMaterialUnloadingPerformanceSwagger implements Serializable {

@Schema(title = "主键标识;UUID")
private String id;

@Schema(title = "检斤计划编号")
private String plan_id;

@Schema(title = "检斤计划日期")
private String plan_date;

@Schema(title = "供货单位编号")
private String supplier_number;

@Schema(title = "供货单位名称")
private String supplier_name;

@Schema(title = "运输方式")
private String shipping_method;

@Schema(title = "车号")
private String vehicle_number;

@Schema(title = "erp储位编号")
private String storage_spaces_id;

@Schema(title = "物料编号")
private String material_number;

@Schema(title = "物料名称")
private String material_name;

@Schema(title = "物料代码")
private String material_code;

@Schema(title = "物料重量")
private String material_weight;

@Schema(title = "班次")
private String shift_name;

@Schema(title = "班别")
private String class_name;

@Schema(title = "卸车时间")
private String unloading_time;

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

@Schema(title = "平台储位编号")
private String storage_maintain_id;

@Schema(title = "平台储位名称")
private String storage_maintain_space_name;

@Schema(title = "erp储位说明")
private String storage_spaces_name;

@Schema(title = "组批号")
private String chk_no;

@Schema(title = "到货日期")
private String recv_date;

}
