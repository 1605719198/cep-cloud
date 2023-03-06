package com.jlkj.material.mr.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 实体类-物料煤场每日库存表
 * @author sudeyou
 * @since 2022-09-02 10:55:24
 */
@TableName(value ="materials_mr_coal_day_stock")
@Data
public class MaterialsCoalDayStock implements Serializable {
    /**
     * 主键标识;UUID
     */
    @TableId
    private String id;

    /**
     * 物料类别id
     */
    private String categoryId;

    /**
     * 物资类别名称
     */
    private String categoryName;

    /**
     * 物料id
     */
    private String materialsId;

    /**
     * 物料名称
     */
    private String materialsName;

    /**
     * 储位编号
     */
    private String storageSpacesId;

    /**
     * 储位名称
     */
    private String storageSpacesName;

    /**
     * 库存量
     */
    private BigDecimal inventory;

    /**
     * 今日卸煤净重
     */
    private BigDecimal todayUnloadedWeight;

    /**
     * 今日煤仓上煤
     */
    private BigDecimal todayLoadingWeight;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 日期
     */
    private Date statDate;

    /**
     * 清零时间
     */
    private Date clearName;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}

