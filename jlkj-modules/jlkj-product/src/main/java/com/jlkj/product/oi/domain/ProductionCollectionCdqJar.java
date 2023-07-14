package com.jlkj.product.oi.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jlkj.common.core.annotation.Excel;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author yzl
 * @create 2023-05-30 10:01:57.794328
 */
@TableName(value ="t_production_collection_cdq_jar")
@Data
public class ProductionCollectionCdqJar  implements Serializable{
    /** 主键标识;UUID */
    @TableId
    private String id ;

    /** 采集时间 */
    @Excel(name = "采集时间",dateFormat = "yyyy-MM-dd HH:mm:ss",type = Excel.Type.EXPORT,width = 30)
    private Date collectionTime ;

    /** 过程值重量 */
    @Excel(name = "过程值重量")
    private BigDecimal processWeight ;

    /** 罐车重量 */
    @Excel(name = "罐车重量")
    private BigDecimal tankWeight ;

    /** 焦重量 */
    @Excel(name = "焦重量")
    private BigDecimal cokeWeight ;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}

