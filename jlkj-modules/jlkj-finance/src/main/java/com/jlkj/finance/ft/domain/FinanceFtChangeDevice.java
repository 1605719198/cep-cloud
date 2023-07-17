package com.jlkj.finance.ft.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 变动单附属设备档
 * @TableName finance_ft_change_device
 */
@TableName(value ="finance_ft_change_device")
@Data
public class FinanceFtChangeDevice implements Serializable {
    /**
     * 主键
     */
    @TableId
    private String uuid;

    /**
     * 公司别
     */
    private String companyId;

    /**
     * 单据id
     */
    private String billId;

    /**
     * 单据编号
     */
    private String billNo;

    /**
     * 附属设备编码
     */
    private String deviceNo;

    /**
     * 附属设备名称
     */
    private String deviceName;

    /**
     * 备注
     */
    private String deviceRemark;

    /**
     * 新增人
     */
    private String createBy;

    /**
     * 新增人名称
     */
    private String createName;

    /**
     * 新增日期
     */
    private Date createTime;

    /**
     * 异动人
     */
    private String updateBy;

    /**
     * 异动人名称
     */
    private String updateName;

    /**
     * 异动日期
     */
    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}