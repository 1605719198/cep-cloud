package com.jlkj.finance.ip.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 系统配置明细资料
 * @TableName t_finance_product_setting_detail
  * @author wang'mai
 */

@TableName(value ="finance_ip_product_setting_detail")
@Data
public class FinanceProductSettingDetail implements Serializable {
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
     * 节点指示码
     */
    private String leafId;

    /**
     * 项次一
     */
    private String itemA;

    /**
     * 项次二
     */
    private String itemB;

    /**
     * 项次三
     */
    private String itemC;

    /**
     * 项次四
     */
    private String itemD;

    /**
     * 项次五
     */
    private String itemE;

    /**
     * 资料一
     */
    private String dataA;

    /**
     * 资料二
     */
    private String dataB;

    /**
     * 资料三
     */
    private String dataC;

    /**
     * 资料四
     */
    private String dataD;

    /**
     * 资料五
     */
    private String dataE;

    /**
     * 资料六
     */
    private String dataF;

    /**
     * 资料七
     */
    private BigDecimal dataG;

    /**
     * 资料排列指示
     */
    private String dataSeq;

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
     * 建立时间
     */
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createTime;
    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
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
     * 逻辑删除标识
     */
    private Integer delFlag;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}