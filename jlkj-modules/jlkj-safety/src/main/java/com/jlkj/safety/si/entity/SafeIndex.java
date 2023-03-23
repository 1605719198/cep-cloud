package com.jlkj.safety.si.entity;
		
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.validation.constraints.Min;

/**
 * @author : yzl
 * @date : 2022-7-26
 * @desc : 环保指标项
 */
@TableName(value ="safe_si_index")
@Data
public class SafeIndex implements Serializable{
    /** 主键标识;UUID */
    @TableId
    private String id ;

    /** 规则编号 */
	private String ruleNumber ;

    /** 规则描述 */
	private String ruleDescribe ;

    /** 规则等级 */
	private String ruleLevel ;

    /** 数值上限 */
	private String ruleUp ;

    /** 数值下限 */
	private String ruleDown ;

    /** 单位 */
	private String unit ;

    /** 状态 */
	private String status ;

    /** 创建人id */
	private String createUserId ;

    /** 创建人 */
	private String createUserName ;

    /** 创建时间 */
	private Date createTime ;

    /** 修改人id */
	private String modifyUserId ;

    /** 修改人 */
	private String modifyUserName ;

    /** 修改时间 */
	private Date modifyTime ;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}

