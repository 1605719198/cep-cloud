package com.jlkj.finance.gp.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 厂商关系表
 * @author wang'mai
 * @TableName finance_gp_manufacturer_relation
 */
@TableName(value ="finance_gp_manufacturer_relation")
@Data
public class ManufacturerRelation implements Serializable {
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

    /**
     * 逻辑删除标识
     */
    private Integer delFlag;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}