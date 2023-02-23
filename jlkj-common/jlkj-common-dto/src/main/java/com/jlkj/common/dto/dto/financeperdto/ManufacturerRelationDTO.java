package com.jlkj.common.dto.dto.financeperdto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;

/**
 * 厂商关系表
 * @author wang'mai
 * @TableName t_manufacturer_relation
 */
@Data
public class ManufacturerRelationDTO {
    /**
     * 主键
     */
    @TableId
    private String id;

    /**
     * 公司别
     */
    private String compId;

    /**
     * 厂商编码
     */
    private String manufacturerId;

    /**
     * 关系
     */
    private String relation;

    /**
     * 关系说明
     */
    private String relationDesc;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}