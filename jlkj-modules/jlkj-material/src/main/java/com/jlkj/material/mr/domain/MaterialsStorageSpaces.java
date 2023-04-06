package com.jlkj.material.mr.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 实体类-储位维护表
 * @author sudeyou
 * @since 2022-09-09 09:13:21
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
     * 物料类别id
     */
    private String materialCategoryId;

    /**
     * 储位名称
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

    /**
     * 物料小煤种代码
     */
    private String materialsSmallCode;

    /**
     * 煤排
     */
    private Integer coalSort;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}

