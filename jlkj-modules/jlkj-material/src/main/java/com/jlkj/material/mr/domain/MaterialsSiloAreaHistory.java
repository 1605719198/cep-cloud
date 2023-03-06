package com.jlkj.material.mr.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 实体类-料仓区域历史表
 * @author sudeyou
 */
@TableName(value ="materials_mr_silo_area_history")
@Data
public class MaterialsSiloAreaHistory implements Serializable {
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
     * 物料编号
     */
    private String materialsId;

    /**
     * 物料名称
     */
    private String materialsName;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}

