package com.jlkj.system.swaggerdto;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
* @author zyf
* @TableName 系统日志
* @TableName sys_logs
*/
@Data
public class SysLogsSwagger implements Serializable {

@Schema(title = "日志内容")
private String content;

@Schema(title = "创建时间")
private String create_time;

@Schema(title = "创建人")
private String create_user_id;

@Schema(title = "创建人")
private String create_user_name;

@Schema(title = "主键标识;UUID")
@TableId
private String id;

@Schema(title = "事件类型")
private String type;

@Schema(title = "请求路径")
private String uri;

}
