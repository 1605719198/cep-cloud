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
 * @date : 2022-6-8
 * @desc : 现场环境治理
 */
@TableName(value ="safe_si_site_environmental_governance")
@Data
public class SafeSiteEnvironmentalGovernance implements Serializable{
    /** 主键标识;UUID */
    @TableId
    private String id ;

    /** 单位 */
    private String departmentId ;

    /** 工作事项 */
    private String workItems ;

    /** 完成情况 */
    private Integer completeState ;

    /** 工作目标 */
    private String workTarget ;

    /** 整改前照片 */
    private String rectificationBeforeUrl ;

    /** 整改后照片 */
    private String rectificationAfterUrl ;

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

