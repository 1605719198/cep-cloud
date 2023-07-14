package com.jlkj.product.oi.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author yzl
 * @create 2023-04-13 15:00:09.783150
 */
@TableName(value ="t_production_report_excipient_consume")
@Data
public class ProductionReportExcipientConsume  implements Serializable{
    /** 主键标识;UUID */
    @TableId
    private String id ;

    /** 消耗日期 */
    private Date consumeDate ;

    /** 辅料名称 */
    private String excipientName ;

    /** 日耗用量(KG) */
    private BigDecimal dailyConsumption ;

    /** 库存(KG) */
    private BigDecimal stock ;

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

