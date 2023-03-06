package com.jlkj.material.mr.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author yzl
 * @Description 物料煤场库存
 * @create 2022年6月10日 10:12:26
 */
@TableName(value ="materials_mr_coal_stock")
@Data
public class MaterialsCoalStock implements Serializable{
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

    /** 储位编号 */
	private String storageSpacesId ;

    /** 储位名称 */
	private String storageSpacesName ;

    /** 库存量 */
	private BigDecimal inventory ;

    /** 今日卸煤净重 */
	private BigDecimal todayUnloadedWeight ;

    /** 今日煤仓上煤 */
	private BigDecimal todayLoadingWeight ;

    /** 创建时间 */
	private Date createTime ;

    /** 清零时间 */
	private Date clearName ;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}

