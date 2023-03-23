package com.jlkj.system.swaggerdto;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @author zyf
* @TableName 权限字典表
* @TableName t_sys_permissions
*/
@Data
public class SysPermissionsSwagger implements Serializable {

@Schema(title = "权限ID")
@TableId
private String id;

@Schema(title = "权限名称")
private String permission_name;

@Schema(title = "请求RUL")
private String url;

@Schema(title = "上级权限ID")
private String parent_id;

@Schema(title = "排序号")
private String sort;

}
