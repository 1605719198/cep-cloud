package com.jlkj.safety.si.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author : yzl
 * @date : 2022-6-6
 * @desc : 安全重点工作
 */
@TableName(value ="safe_si_important_work")
@Data
public class SafeImportantWork implements Serializable {
    /** 主键标识;UUID */
    @TableId
    private String id ;

    /** 单位 */
    private String departmentId ;

    /** 主体 */
    private String subject ;

    /** 完成目标 */
    private String completeTarget ;

    /** 完成情况 */
    private Integer completeState ;

    /** 计划完成时间 */
    private Date planCompleteTime ;

    /** 备注 */
    private String remarks ;

    /** 创建人 */
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
