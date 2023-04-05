package com.jlkj.product.oi.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author zzh
 * 焦炭分项产量实体类
 * @time 2022年9月9日10:57:04
 */
@Data
@TableName(value = "product_oi_coke_sub_records")
public class ProductionCokeItemYieldEntity implements Serializable {

    @TableId
    private String id ;

    private String createTime ;

    private String materialsName ;

    private String materialsCode ;

    private BigDecimal yield ;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}
