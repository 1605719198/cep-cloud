package com.jlkj.product.oi.swaggerdto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 *
 * @author  generator
 */
@Data
public class ProductionAbnormalLedgerSwagger implements Serializable {

@Schema(title = "主键标识;UUID")
private String id;

@Schema(title = "主体单位id")
private String department_id;

@Schema(title = "主体单位")
private String department_name;

@Schema(title = "发生时间")
private String happen_time;

@Schema(title = "事故名称")
private String accident_name;

@Schema(title = "防范措施")
private String preventive_measures;

@Schema(title = "计划完成时间")
private String measures_completion_time;

@Schema(title = "措施落实情况")
private String measures_Implementation;

@Schema(title = "专业责任人")
private String professional_responsible_person;

@Schema(title = "跟踪责任人")
private String track_responsible_person;

@Schema(title = "事故报告")
private String accident_report_file;

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

}
