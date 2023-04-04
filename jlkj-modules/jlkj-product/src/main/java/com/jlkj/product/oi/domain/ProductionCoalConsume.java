package com.jlkj.product.oi.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 实体类-炼焦煤消耗维护
 *
 * @author sudeyou
 * @since 2022-11-03 17:10:54
 */
@TableName(value ="product_oi_coal_consume")
@Data
public class ProductionCoalConsume implements Serializable {
    /**
     * 主键标识;UUID
     */
    @TableId
    private String id;

    /**
     * 账务日期
     */
    private Date accountDate;

    /**
     * 物料代码
     */
    private String materialsCode;

    /**
     * 物料名称
     */
    private String materialsName;

    /**
     * 煤消耗量(t)
     */
    private Integer consumption;

    /**
     * 抛送状态(0:未抛送；1:已抛送)
     */
    private String throwState;

    /**
     * 创建人
     */
    private String createUserId;

    /**
     * 创建人
     */
    private String createUserName;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改人id
     */
    private String modifyUserId;

    /**
     * 修改人
     */
    private String modifyUserName;

    /**
     * 修改时间
     */
    private Date modifyTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}

