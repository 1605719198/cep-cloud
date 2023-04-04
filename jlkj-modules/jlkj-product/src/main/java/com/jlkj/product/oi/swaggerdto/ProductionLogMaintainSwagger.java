package com.jlkj.product.oi.swaggerdto;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
* @author zyf
* @TableName 生产管理-日志维护
* @TableName product_oi_log_maintain
*/
@Data
public class ProductionLogMaintainSwagger implements Serializable {

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

@Schema(title = "日志项id")
private String item_id;

@Schema(title = "日志项名称")
private String item_name;

@Schema(title = "修改时间")
private String modify_time;

@Schema(title = "修改人id")
private String modify_user_id;

@Schema(title = "修改人")
private String modify_user_name;

}
