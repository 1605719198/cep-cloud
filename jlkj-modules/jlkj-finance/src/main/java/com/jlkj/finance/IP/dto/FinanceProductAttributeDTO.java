package com.jlkj.finance.IP.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author wang'mai
 */
@Data
public class FinanceProductAttributeDTO {
    /**
     * 属性说明
     */
    private String label;
    /**
     * 属性名称
     */
    private String value;

    private List<FinanceProductAttributeDTO> propertydata;

    private String attriValue;
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
     * 存货特性
     */
    private String productInventoryNature;

    /**
     * 产副品编码
     */
    private String productCode;

    /**
     * 属性名称
     */
    private String attriName;


    /**
     * 建立人
     */
    private String createUser;

    /**
     * 建立日期
     */
    private String createDate;

    /**
     * 异动人
     */
    private String updateUser;

    /**
     * 异动日期
     */
    private String updateDate;

    /**
     * 创建人姓名
     */
    private String createUserName;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 修改人姓名
     */
    private String updateUserName;

    /**
     * 逻辑删除标识
     */
    private Integer delFlag;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}
