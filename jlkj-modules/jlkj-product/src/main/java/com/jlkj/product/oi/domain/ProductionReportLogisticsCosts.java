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
 * @create 2023-04-17 15:00:13.651133
 */
@TableName(value ="t_production_report_logistics_costs")
@Data
public class ProductionReportLogisticsCosts  implements Serializable{
    /** 主键标识;UUID */
    @TableId
    private String id ;

    /** 统计日期 */
    private Date statisticsDate ;

    /** 卸车费(元) */
    private BigDecimal unloadingFee ;

    /** 倒运费(元) */
    private BigDecimal reverseFee ;

    /** 临时用(元) */
    private BigDecimal temporaryFee ;

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

