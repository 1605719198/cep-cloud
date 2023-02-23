package com.jlkj.energy.ee.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * EMS上抛异常资讯
 * @TableName energy_ee_trans_data
 * @author 265675
 */
@TableName(value ="energy_ee_trans_data")
@Data
public class EnergyTransData implements Serializable {
    /**
     * ID
     */
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 公司别
     */
    private String compId;

    /**
     * 交易日期
     */
    private String transDate;

    /**
     * 交易时间
     */
    private String transTime;

    /**
     * 资料序号
     */
    private Integer seqNo;

    /**
     * 异常讯息类别
     */
    private String errType;

    /**
     * 资料表代号
     */
    private String formId;

    /**
     * 上抛资料
     */
    private String transData;

    /**
     * 错误讯息
     */
    private String errMsg;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 修改人
     */
    private String updateUser;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
