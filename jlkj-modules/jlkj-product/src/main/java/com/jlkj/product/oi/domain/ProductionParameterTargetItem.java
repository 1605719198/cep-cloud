package com.jlkj.product.oi.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author auto
 * 生产参数--指标项维护
 * @TableName product_oi_parameter_target_item
 */
@TableName(value ="product_oi_parameter_target_item")
@Data
public class ProductionParameterTargetItem implements Serializable {
    /**
     * 主键标识;UUID
     */
    @TableId
    private String id;

    /**
     * 指标类型id;1:焦化；2：发电
     */
    private Integer targetItemTypeId;

    /**
     * 指标类型名称
     */
    private String targetItemTypeName;

    /**
     * 指标名称
     */
    private String targetItemName;

    /**
     * 计算单位id;1：百分比，2：量值
     */
    private Integer calcUnitId;

    /**
     * 计算单位名称
     */
    private String calcUnitName;

    /**
     * 计量单位
     */
    private String measuringUnit;

    /**
     * 创建人编号
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
     * 修改人编号
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

    /**
     * 物料代码id
     */
    private String materialId;

    /**
     * 物料名称
     */
    private String materialName;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
