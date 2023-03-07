package com.jlkj.material.mr.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 实体类-料仓区域维护表
 * @author sudeyou
 */
@TableName(value ="materials_mr_silo_area")
@Data
public class MaterialsSiloArea implements Serializable {
    /**
     * 主键标识;UUID
     */
    @TableId
    private String id;

    /**
     * 料仓编号
     */
    private String siloId;

    /**
     * 储位编号
     */
    private String storageSpacesId;

    /**
     * 起始位置
     */
    private Integer startLocation;

    /**
     * 结束位置
     */
    private Integer endLocation;

    /**
     * 颜色值
     */
    private String colorValue;

    /**
     * 物料编号
     */
    private String materialsId;

    /**
     * 物料名称
     */
    private String materialsName;

    /**
     * 储量
     */
    private BigDecimal storageQuantity;

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

