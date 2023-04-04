package com.jlkj.product.oi.swaggerdto;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @author zyf
 * @TableName 计划管理--配煤计划主表
 * @TableName product_oi_plan_config_coke
 */
@Data
public class ProductionPlanConfigCokeSwagger implements Serializable {

    @Schema(title = "主键标识;UUID")
    @TableId
    private String id;

    @Schema(title = "计划编号")
    private String plan_number;

    @Schema(title = "开始时间")
    private String plan_start_time;

    @Schema(title = "结束时间")
    private String plan_end_time;

    @Schema(title = "计划状态(1:未确认;2:已确认;3:执行中;4:已完成)")
    private String plan_state;

    @Schema(title = "接收人编号")
    private String receive_user_id;

    @Schema(title = "接收人姓名")
    private String receive_user_name;

    @Schema(title = "接收确认时间")
    private String receive_time;

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

    @Schema(title = "物料id(焦炭等级id)")
    private String material_id;

    @Schema(title = "物料名称(焦炭等级名称)")
    private String material_name;

    @Schema(title = "物料代码(焦炭等级code)")
    private String material_code;

    @Schema(title = "配煤详细列表")
    private ProductionPlanConfigCokeDetailSwagger detail_list;

}
