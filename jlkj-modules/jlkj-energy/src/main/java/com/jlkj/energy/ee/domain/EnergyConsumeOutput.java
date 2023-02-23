package com.jlkj.energy.ee.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 能源项目基本资料
 * @TableName energy_ee_consume_output
 * @author 265675
 */
@TableName(value ="energy_ee_consume_output")
@Data
public class EnergyConsumeOutput implements Serializable {
    /**
     * 时间序号
     */
    @TableId
    private String timeSeqNo;

    /**
     * 公司别
     */
    @TableField(fill = FieldFill.INSERT)
    private String compId;

    /**
     * 成本中心代号
     */
    private String costCenter;

    /**
     * 能源计量日期
     */
    private String eCountDate;

    /**
     * 能源代码
     */
    private String engyId;

    /**
     * 耗用产出类别代号
     */
    private String ioTypeId;

    /**
     * 执行作业代码
     */
    private String execJobFunc;

    /**
     * 能源类别
     */
    private String engyType;

    /**
     * 工厂代号
     */
    private String factoryId;

    /**
     * 产线别代号
     */
    private String millId;

    /**
     * 抛帐对象
     */
    private String acctSys;

    /**
     * 卖方成本中心
     */
    private String chargeCostCenter;

    /**
     * 卖方工厂代号
     */
    private String chargeFactoryId;

    /**
     * 能源产耗量
     */
    private BigDecimal proConQty;

    /**
     * 财务用能源计量单位
     */
    private String engyUnitF;

    /**
     * 能源部用能源计量单位
     */
    private String engyUnit;

    /**
     * 能源热值
     */
    private BigDecimal calValue;

    /**
     * 热值单位
     */
    private String calUnit;

    /**
     * 能量值
     */
    private BigDecimal engyValue;

    /**
     * 建立日期
     */
    private String createDate;

    /**
     * 是否抛AM
     */
    private String throwAm;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 修改人
     */
    private String updateUser;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
