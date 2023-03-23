package com.jlkj.system.swaggerdto;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
* @author zyf
* @TableName 调度日志
* @TableName t_dispatch_log
*/
@Data
public class DispatchLogSwagger implements Serializable {

@Schema(title = "调度记录编号")
@TableId
private String id;

@Schema(title = "调度人员ID")
private String dispatcher_id;

@Schema(title = "调度人员姓名")
private String dispatcher_name;

@Schema(title = "处理人员ID")
private String worker_id;

@Schema(title = "处理人员姓名")
private String worker_name;

@Schema(title = "工作内容")
private String work_content;

@Schema(title = "处理说明")
private String dispose_content;

@Schema(title = "处理结果")
private String dispose;

@Schema(title = "完成标识")
private String state_flag;

@Schema(title = "填报时间")
private String create_time;

@Schema(title = "调度时间")
private String dispath_time;

@Schema(title = "处理反馈时间")
private String dispose_time;

}
