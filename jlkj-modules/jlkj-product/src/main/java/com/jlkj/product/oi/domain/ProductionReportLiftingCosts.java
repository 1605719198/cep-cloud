package com.jlkj.product.oi.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.validation.constraints.Min;

/**
 * @author yzl
 * @create 2023-04-17 14:57:45.238032
 */
@TableName(value ="t_production_report_lifting_costs")
@Data
public class ProductionReportLiftingCosts  implements Serializable{
    /** 主键标识;UUID */
    @TableId
    private String id ;

    /** 统计日期 */
    private Date statisticsDate ;

    /** 作业区 */
    private String areaName ;

    /** 车型 */
    private String carType ;

    /** 吨数(t) */
    private BigDecimal tonnes ;

    /** 当日费用(元) */
    private BigDecimal dayCost ;

    /** 用车时长(h) */
    private Integer carTime ;

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

