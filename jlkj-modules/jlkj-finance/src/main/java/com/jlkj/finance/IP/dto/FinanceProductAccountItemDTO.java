package com.jlkj.finance.IP.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author wang'mai
 */
@Data
public class FinanceProductAccountItemDTO {

    /**
     * 当前页
     */
    private Long pageNum;

    /**
     * 每页记录数
     */
    private Long pageSize;
    /**
     * 系统码uuid
     */
    @TableId
    private String id;

    /**
     * 公司别
     */
    private String compId;

    /**
     * 账务代码
     */
    private String accountItemCode;

    /**
     * 账务名称
     */
    private String accountItemName;

    /**
     * 用途说明
     */
    private String accountItemCodeDesc;

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
     * 逻辑删除标识
     */
    private Integer delFlag;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
