package com.jlkj.system.swaggerdto;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
* @author zyf
* @TableName 隐患处理
* @TableName safe_si_danger_record
*/
@Data
public class SafeDangerRecordSwagger implements Serializable {

@Schema(title = "安全隐患ID;UUID")
@TableId
private String id;

@Schema(title = "隐患等级id")
private String danger_type_id;

@Schema(title = "隐患等级")
private String danger_type_name;

@Schema(title = "责任单位id")
private String duty_depart_id;

@Schema(title = "责任单位")
private String duty_depart_name;

@Schema(title = "责任人id")
private String duty_person_id;

@Schema(title = "责任人")
private String duty_person_name;

@Schema(title = "提交单位id")
private String submit_depart_id;

@Schema(title = "提交单位")
private String submit_depart_name;

@Schema(title = "提交人id")
private String submit_person_id;

@Schema(title = "提交人")
private String submit_person_name;

@Schema(title = "区域ID")
private String safe_area_id;

@Schema(title = "区域")
private String safe_area_name;

@Schema(title = "隐患提交时间")
private String danger_time;

@Schema(title = "限期完成时间")
private String danger_deadline_time;

@Schema(title = "隐患处理编号")
private String danger_number;

@Schema(title = "验收单位id")
private String acceptance_depart_id;

@Schema(title = "验收单位")
private String acceptance_depart_name;

@Schema(title = "验收人id")
private String acceptance_person_id;

@Schema(title = "验收人")
private String acceptance_person_name;

@Schema(title = "提交人签名图片")
private String submit_sign_image;

@Schema(title = "提交图片1")
private String submit_image1;

@Schema(title = "提交图片2")
private String submit_image2;

@Schema(title = "提交图片3")
private String submit_image3;

@Schema(title = "提交图片4")
private String submit_image4;

@Schema(title = "验收图片1")
private String acceptance_image1;

@Schema(title = "验收图片2")
private String acceptance_image2;

@Schema(title = "验收图片3")
private String acceptance_image3;

@Schema(title = "验收图片4")
private String acceptance_image4;

@Schema(title = "验收时间")
private String acceptance_time;

@Schema(title = "整改措施")
private String dispose_measure;

@Schema(title = "整改内容")
private String danger_content;

@Schema(title = "状态：1待整改，2已验收，3超时")
private String danger_status;

@Schema(title = "验收评价")
private String acceptance_appraise;

@Schema(title = "验收人签名图片")
private String acceptance_sign_image;

@Schema(title = "整改内容")
private String dispose_content;

@Schema(title = "处理人ID")
private String disposer_id;

@Schema(title = "处理人名称")
private String disposer_name;

@Schema(title = "处理反馈时间")
private String dispose_time;

}
