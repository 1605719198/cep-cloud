package com.jlkj.rabbit.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zyf
 * 物料代码维护表
 * @TableName t_materials_code
 */
@TableName(value ="materials_mr_code")
@Data
public class MaterialsCode implements Serializable {
    /**
     * 主键标识;UUID
     */
    @TableId
    private String id;

    /**
     * 类别编号
     */
    private String categoryId;

    /**
     * 物料名称
     */
    private String materialsName;

    /**
     * 物料代码
     */
    private String materialsCode;

    /**
     * 计量单位
     */
    private String measurementUnit;

    /**
     * 00：煤   01：焦炭
     */
    private String mtrltype;

    /**
     * 删除标识
     */
    private Integer deleteFlag;

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
