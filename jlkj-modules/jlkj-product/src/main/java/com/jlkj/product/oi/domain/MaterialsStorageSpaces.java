package com.jlkj.product.oi.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 实体类-物料管理-储位维护表
 *
 * @author sudeyou
 * @since 2022-11-09 19:41:36
 */
@TableName(value ="materials_mr_storage_spaces")
@Data
public class MaterialsStorageSpaces implements Serializable {
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
     * 物料代码(t_materials_code表的materials_code)
     */
    private String materialCategoryId;

    /**
     * 储位名称(物料名称)
     */
    private String storageSpacesName;

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
