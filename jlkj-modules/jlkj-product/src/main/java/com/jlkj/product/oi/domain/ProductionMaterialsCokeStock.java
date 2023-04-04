package com.jlkj.product.oi.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author zzh
 * @Description 物料焦场库存
 * @create 2022年6月10日 10:12:26
 */
@TableName(value ="t_materials_coke_stock")
@Data
public class ProductionMaterialsCokeStock implements Serializable{
    /** 主键标识;UUID */
    @TableId
    private String id ;

    /** 物料类别id */
	private String categoryId ;

    /** 物资类别名称 */
	private String categoryName ;

    /** 物料id */
	private String materialsId ;

    /** 物料名称 */
	private String materialsName ;

    /** 库存量 */
	private BigDecimal inventory ;

    /** 创建时间 */
	private Date createTime ;

    /** 清零时间 */
	private Date clearName ;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}

