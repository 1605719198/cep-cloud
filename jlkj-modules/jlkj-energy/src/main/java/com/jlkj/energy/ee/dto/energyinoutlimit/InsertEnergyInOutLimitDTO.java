package com.jlkj.energy.ee.dto.energyinoutlimit;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 能源产耗标准配置表
 * @TableName t_energy_in_out_limit
 * @author liukuiyan
 */
@TableName(value ="t_energy_in_out_limit")
@Data
public class InsertEnergyInOutLimitDTO implements Serializable {
    /**
     * 主键ID
     */
    @TableId
    private String id;

    /**
     * 成本中心编码
     */
    @JsonProperty("cost_center_no")
    private String costCenterNo;

    /**
     * 成本中心名称
     */
    @JsonProperty("cost_center_name")
    private String costCenterName;

    /**
     * 卖方成本中心编码
     */
    @JsonProperty("charge_cost_center_no")
    private String chargeCostCenterNo;

    /**
     * 卖方成本中心名称
     */
    @JsonProperty("charge_cost_center_name")
    private String chargeCostCenterName;

    /**
     * 能源代码
     */
    @JsonProperty("engy_id")
    private String engyId;

    /**
     * 耗用产出类别代号
     */
    @JsonProperty("in_out_type_id")
    private String inOutTypeId;

    /**
     * 上限
     */
    @JsonProperty("up_limit")
    private BigDecimal upLimit;

    /**
     * 下限
     */
    @JsonProperty("down_limit")
    private BigDecimal downLimit;

    /**
     * 是否超百分比提醒
     */
    @JsonProperty("is_percent_tips")
    private String isPercentTips;

    /**
     * 上限超差百分比
     */
    @JsonProperty("up_limit_percent")
    private BigDecimal upLimitPercent;

    /**
     * 下限超差百分比
     */
    @JsonProperty("down_limit_percent")
    private BigDecimal downLimitPercent;

    /**
     * 警示色
     */
    @JsonProperty("tips_color")
    private String tipsColor;

    /**
     * 创建人
     */
    @JsonProperty("create_user")
    private String createUser;

    /**
     * 创建时间
     */
    @JsonProperty("create_time")
    private Date createTime;

    /**
     * 修改人
     */
    @JsonProperty("update_user")
    private String updateUser;

    /**
     * 修改时间
     */
    @JsonProperty("update_time")
    private Date updateTime;

    /**
     * 创建人姓名
     */
    @JsonProperty("create_user_name")
    private String createUserName;

    /**
     * 修改人姓名
     */
    @JsonProperty("update_user_name")
    private String updateUserName;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
