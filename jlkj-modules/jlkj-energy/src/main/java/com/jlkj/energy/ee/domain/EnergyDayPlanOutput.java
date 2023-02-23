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
 * @create 2022-09-02 13:33:26.756129
 */
@TableName(value ="energy_ee_day_plan_output")
@Data
public class EnergyDayPlanOutput  implements Serializable{
    /** 主键标识;UUID */
    @TableId
    private String id ;

    /** 序号 */
	private String serialNo ;

    /** 工序名称 */
	private String factoryName ;

    /** 作业区 */
	private String factoryArea ;

    /** 日计划产量 */
	private BigDecimal planValue ;

    /** 年月日(YYYY-MM-DD) */
	private String planyearMonthDay ;

    /** 单位 */
	private String unit ;

    /** 新增时间(YYYY-MM-DD HH:mm:ss) */
	private String createDate ;

    /** 新增人员 */
	private String createEmpNo ;

    /** 修改时间(YYYY-MM-DD HH:mm:ss) */
	private String updateDate ;

    /** 修改人员 */
    private String updateEmpNo ;

    /** 创建人 */
	private String createUser ;

    /** 修改人 */
	private String updateUser ;

    /** 创建时间 */
	private Date createTime ;

    /** 修改时间 */
	private Date updateTime ;

    /** 创建人姓名 */
	private String createUserName ;

    /** 修改人姓名 */
	private String updateUserName ;
    /** 备注 */
    private String memo ;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}

