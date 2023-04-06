package com.jlkj.material.mr.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author yzl
 * @create 2023-03-22 09:27:37.931375
 */
@TableName(value ="materials_mr_code_small")
@Data
public class MaterialsCodeSmall implements Serializable{
    /** 主键标识;UUID */
    @TableId
    private String id ;

    /** 物料类别(eg:	00512) */
	private String categoryCode ;

    /** 物料代码(eg:	0051201) */
	private String materialsCode ;

    /** 物料小煤种代码(eg:	005120101) */
	private String materialsSmallCode ;

    /** 物料小煤种简称(煤管手动维护) */
	private String materialsSmallName ;

    /** 供应商编码 */
	private String vendorNo ;

    /** 供应商名称 */
	private String vendorName ;

    /** 发货地编码 */
	private String sendVendorNo ;

    /** 发货地名称 */
	private String sendVendorName ;

    /** 计量单位 */
	private String measurementUnit ;

    /** 00：煤   01：焦炭 */
	private String mtrlType ;

    /** 删除标识 */
	private Integer deleteFlag ;

    /** 创建人 */
	private String createUserId ;

    /** 创建人 */
	private String createUserName ;

    /** 创建时间 */
	private Date createTime ;

    /** 修改人id */
	private String modifyUserId ;

    /** 修改人 */
	private String modifyUserName ;

    /** 修改时间 */
	private Date modifyTime ;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}

