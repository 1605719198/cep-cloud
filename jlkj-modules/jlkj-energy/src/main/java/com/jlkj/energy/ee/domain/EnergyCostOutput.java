package com.jlkj.energy.ee.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 能源产出资料档
 * @author sudeyou
 * @TableName energy_ee_cost_output
 */
@TableName(value ="energy_ee_cost_output")
@Data
public class EnergyCostOutput implements Serializable {
    /**
     * 公司别
     */
    @TableId
    private String compId;

    /**
     * 成本中心代号
     */
    private String costCenter;

    /**
     * 卖方成本中心
     */
    private String chargeCostCenter;

    /**
     * 活动日期
     */
    private String activityDate;

    /**
     * 能源代码
     */
    private String engyId;

    /**
     * 生产活动(C/P)
     */
    private String activityId;

    /**
     * 执行作业(N/D)
     */
    private String execJobFunc;

    /**
     * 新增日期
     */
    private String createDate;

    /**
     * 新增时间
     */
    private String createTime;

    /**
     * 财务用能源单位
     */
    private String engyUnit;

    /**
     * 交易资料指示码(N/O)
     */
    private String dataTypeIdx;

    /**
     * 当前产线别
     */
    private String crntMill;

    /**
     * 账务日期
     */
    private String acctDate;

    /**
     * 工厂代号
     */
    private String factoryId;

    /**
     * 帐务代码
     */
    private String prodCode;

    /**
     * 能量值
     */
    private BigDecimal proConQty;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}