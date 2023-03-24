package com.jlkj.human.hm.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 人员异动信息明细表
 * @author huangbing
 * @TableName human_hm_change_detail
 */
@TableName(value ="human_hm_change_detail")
@Data
public class ChangeDetail implements Serializable {
    /**
     * 主键id
     */
    @TableId
    private String uuid;

    /**
     * 异动主表ID（人员异动信息主表uuid）
     */
    private String parentId;

    /**
     * 岗位类别
     */
    private String postTypeId;

    /**
     * 异动前岗位ID
     */
    private String oldPostId;

    /**
     * 异动前岗位名称
     */
    private String oldPostName;

    /**
     * 异动后岗位ID
     */
    private String newPostId;

    /**
     * 异动后岗位名称
     */
    private String newPostName;

    /**
     * 更新人员
     */
    private String creator;

    /**
     * 更新人员ID
     */
    private String creatorId;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;

    /**
     * 备用字段1
     */
    private String alternateField1;

    /**
     * 备用字段2
     */
    private String alternateField2;

    /**
     * 备用字段3
     */
    private String alternateField3;

    /**
     * 备用字段4
     */
    private String alternateField4;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}