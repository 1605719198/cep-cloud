package com.jlkj.product.oi.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 计划管理--配煤计划主表
 * @TableName product_oi_plan_config_coke
 * @author Liukuiyan
 */
@TableName(value ="product_oi_plan_config_coke")
@Data
public class ProductionPlanConfigCoke implements Serializable {
    /**
     * 主键标识;UUID
     */
    @TableId
    private String id;

    /**
     * 计划编号
     */
    private String planNumber;

    /**
     * 开始时间
     */
    private Date planStartTime;

    /**
     * 结束时间
     */
    private Date planEndTime;

    /**
     * 计划状态(1:未确认;2:已确认;3:执行中;4:已完成)
     */
    private Integer planState;

    /**
     * 接收人编号
     */
    private String receiveUserId;

    /**
     * 接收人姓名
     */
    private String receiveUserName;

    /**
     * 接收确认时间
     */
    private Date receiveTime;

    /**
     * 创建人编号
     */
    private String createUserId;

    /**
     * 创建人
     */
    private String createUserName;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改人编号
     */
    private String modifyUserId;

    /**
     * 修改人
     */
    private String modifyUserName;

    /**
     * 修改时间
     */
    private Date modifyTime;

    /**
     * 物料id(焦炭等级id)
     */
    private String materialId;

    /**
     * 物料名称(焦炭等级名称)
     */
    private String materialName;

    /**
     * 物料代码(焦炭等级code)
     */
    private String materialCode;

    /**
     * 储煤塔号
     */
    private Integer towerNumber;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
