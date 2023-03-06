package com.jlkj.material.mr.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 实体类-物料焦场每日库存表
 * @author sudeyou
 * @since 2022-09-02 10:29:24
 */
@TableName(value ="materials_mr_coke_day_stock")
@Data
public class MaterialsCokeDayStock implements Serializable {
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
     * 库存量
     */
    private BigDecimal inventory;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 清零时间
     */
    private Date clearName;

    /**
     * 日期
     */
    private Date statDate;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}

