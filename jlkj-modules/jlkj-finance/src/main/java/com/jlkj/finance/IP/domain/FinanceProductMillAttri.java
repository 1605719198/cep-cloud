package com.jlkj.finance.IP.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 产线属性资料
 * @TableName t_finance_product_mill_attri
 * @author 265675
 */
@TableName(value ="t_finance_product_mill_attri")
@Data
public class FinanceProductMillAttri implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 公司别
     */
    private String compId;

    /**
     * 产线代码
     */
    private String millId;

    /**
     * 属性名称
     */
    private String attriName;

    /**
     * 属性值
     */
    private String attriValue;

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
     * 创建人姓名
     */
    private String createUserName;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
