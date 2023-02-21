package com.jlkj.finance.IP.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 产线产副品资料
 * @TableName t_finance_product_mill_product
 * @author 265675
 */
@TableName(value ="t_finance_product_mill_product")
@Data
public class FinanceProductMillProduct implements Serializable {
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
     * 产线代码
     */
    private String millId;

    /**
     * 产副品代码
     */
    private String productCode;

    /**
     * 建立人
     */
    private String createUser;

    /**
     * 建立日期
     */
    private String createDate;

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

    /**
     * 修改人
     */
    private String updateUser;

    /**
     * 创建时间
     */
    private Date createTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
