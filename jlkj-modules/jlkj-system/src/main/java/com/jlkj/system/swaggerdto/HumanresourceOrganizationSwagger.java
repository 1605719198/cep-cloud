package com.jlkj.system.swaggerdto;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
* @TableName 机构数据
* @TableName human_hm_organization
* @author zyf
*/
@Data
public class HumanresourceOrganizationSwagger implements Serializable {

@Schema(title = "主键")
@TableId
private String id;

@Schema(title = "机构编码")
private String organization_code;

@Schema(title = "机构名称")
private String organization_name;

@Schema(title = "板块ID")
private String board_id;

@Schema(title = "上级机构ID")
private String parent_id;

@Schema(title = "上级机构名称")
private String parent_name;

@Schema(title = "上级机构全称")
private String parent_full_name;

@Schema(title = "机构层级ID")
private String organization_tier_id;

@Schema(title = "机构类别ID")
private String organization_type_id;

@Schema(title = "机构细类ID")
private String organization_category_id;

@Schema(title = "排序序号")
private String serial_no;

@Schema(title = "成本中心")
private String cost_center_id;

@Schema(title = "批准文号")
private String approval_no;

@Schema(title = "传真")
private String fax;

@Schema(title = "联系电话")
private String telephone;

@Schema(title = "变更原因")
private String change_reason;

@Schema(title = "生效日期")
private String effect_date;

@Schema(title = "版本号")
private String version_no;

@Schema(title = "是否公司")
private String if_company;

@Schema(title = "公司别")
private String company_id;

@Schema(title = "是否显示")
private String if_display;

@Schema(title = "输入人")
private String creator;

@Schema(title = "输入人ID")
private String creator_id;

@Schema(title = "输入日期")
private String create_date;

@Schema(title = "状态")
private String status;

@Schema(title = "创建时间")
private String create_time;

@Schema(title = "更新时间")
private String update_time;

@Schema(title = "创建人")
private String create_user;

@Schema(title = "修改人")
private String update_user;

@Schema(title = "创建人姓名")
private String create_user_name;

@Schema(title = "修改人姓名")
private String update_user_name;

}
