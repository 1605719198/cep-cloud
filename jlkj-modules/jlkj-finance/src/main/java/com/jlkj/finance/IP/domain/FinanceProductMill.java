package com.jlkj.finance.IP.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 产线基础资料
 * @TableName t_finance_product_mill
 * @author 265675
 */
@TableName(value ="t_finance_product_mill")
@Data
public class FinanceProductMill implements Serializable {
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
     * 产线名称
     */
    private String millName;

    /**
     * 建立人
     */
    private String inputUser;

    /**
     * 建立日期
     */
    private String inputDate;

    /**
     * 异动人
     */
    private String updateUser;

    /**
     * 异动日期
     */
    private String updateDate;

    /**
     * 创建人
     */
    private String createUser;

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

    /**
     * 状态
     */
    private String status;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
