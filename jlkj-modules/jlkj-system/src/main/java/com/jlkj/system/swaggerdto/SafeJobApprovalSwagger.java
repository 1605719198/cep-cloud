package com.jlkj.system.swaggerdto;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
* @author zyf
* @TableName 吊装安全作业证-审批记录
* @TableName safe_si_job_hoisting_approval
*/
@Data
public class SafeJobApprovalSwagger implements Serializable {

@Schema(title = "行标识")
@TableId
private String id;

@Schema(title = "作业证ID")
private String job_id;

@Schema(title = "序号")
private String sort;

@Schema(title = "审批环节")
private String approval_name;

@Schema(title = "审批人ID")
private String approval_person_id;

@Schema(title = "审批人姓名")
private String approval_person_name;

@Schema(title = "审批意见内容")
private String content;

@Schema(title = "选择时间")
private String pick_time;

}
