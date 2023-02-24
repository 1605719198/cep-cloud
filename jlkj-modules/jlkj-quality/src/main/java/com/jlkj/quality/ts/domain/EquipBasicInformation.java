package com.jlkj.quality.ts.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 试验元素表格维护表
 * @TableName t_ts_equip_basic_information
 * @author 265675
 */
@TableName(value ="t_ts_equip_basic_information")
@Data
public class EquipBasicInformation implements Serializable {
    /**
     * 唯一标识
     */
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 仪器代号
     */
    private String equipNo;

    /**
     * 仪器说明
     */
    private String equipDesc;

    /**
     * 操作类型
     */
    private String workType;

    /**
     * ID接收状态
     */
    private String receiveStatus;

    /**
     * 工作档名
     */
    private String workFileName;

    /**
     * 传出IP
     */
    private String sendIp;

    /**
     * 传出PORT
     */
    private String sendPort;

    /**
     * 接收PORT
     */
    private String receivePort;

    /**
     * 检验站号
     */
    private String inspectionStationNo;

    /**
     * 建档人员
     */
    private String createUserId;

    /**
     * 异动人员
     */
    private String modifyUserId;

    /**
     * 异动日期
     */
    private String modifyTime;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 修改人
     */
    private String updateUser;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    /**
     * 创建人姓名
     */
    private String createUserName;

    /**
     * 修改人姓名
     */
    private String updateUserName;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
