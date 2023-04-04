package com.jlkj.product.oi.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 实体类-物料管理-物料类别维护表
 *
 * @author sudeyou
 * @since 2022-12-07 15:10:09
 */
@TableName(value ="materials_mr_category")
@Data
public class MaterialsCategory implements Serializable {
    /**
     * 主键标识;UUID
     */
    @TableId
    private String id;

    /**
     * 类别名称
     */
    private String categoryName;

    /**
     * 类别代码
     */
    private String categoryCode;

    /**
     * 焦炭或煤
     */
    private String mtrlType;

    /**
     * 删除标识
     */
    private Short deleteFlag;

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
