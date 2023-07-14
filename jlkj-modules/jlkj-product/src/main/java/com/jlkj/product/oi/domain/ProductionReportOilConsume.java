package com.jlkj.product.oi.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author yzl
 * @create 2023-04-13 13:27:01.941071
 */
@TableName(value ="t_production_report_oil_consume")
@Data
public class ProductionReportOilConsume  implements Serializable{
    /** 主键标识;UUID */
    @TableId
    private String id ;

    /** 消耗日期 */
    private Date consumeDate ;

    /** 设备名称 */
    private String deviceName ;

    /** 油品类型 */
    private String oilType ;

    /** 日耗用量(L) */
    private String dailyConsumption ;

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

