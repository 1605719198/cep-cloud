package com.jlkj.energy.ee.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author yzl
 * @create 2022-09-02 13:32:21.116575
 */
@TableName(value ="energy_ee_day_plan_consume")
@Data
public class EnergyDayPlanConsume  implements Serializable{
    /** 主键标识;UUID */
    @TableId
    private String id ;

    /** 能源介质名称 */
	private String energyName ;

    /** 能源介质代码 */
    private String energyCode ;

    /** 类别 */
	private String types ;

    /** 工序名称 */
	private String factoryName ;

    /** 作业区 */
	private String factoryArea ;

    /** 年月日(YYYY-MM-DD) */
	private String planyearMonthDay ;

    /** 日计划值 */
	private BigDecimal planValue ;

    /** 单位 */
	private String unit ;

    /** 备注 */
	private String memo ;

    /** 日计划公式 */
	private String memoa ;

    /** 行业先进值 */
	private BigDecimal advancedValue ;

    /** 内控指标值 */
	private BigDecimal lawsValue ;

    /** 新增时间(YYYY-MM-DD HH:mm:ss) */
	private String createDate ;

    /** 新增人员(XXXXX NNNNN) */
	private String createEmpNo ;

    /** 修改时间(YYYY-MM-DD HH:mm:ss) */
    private String updateDate ;

    /** 修改人员(XXXXX NNNNN) */
	private String updateEmpNo ;

    /** 创建人 */
	private String createUser ;

    /** 修改人 */
	private String updateUser ;

    /** 创建时间 */
	private Date createTime ;

    /** 修改时间 */
	private Date updateTime ;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}

