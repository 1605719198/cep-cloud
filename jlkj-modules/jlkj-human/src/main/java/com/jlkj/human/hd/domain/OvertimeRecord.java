package com.jlkj.human.hd.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.common.core.annotation.Excel;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 加班记录表
 * @author HuangBing
 * @TableName human_hd_overtime_record
 */
@TableName(value ="human_hd_overtime_record")
@Data
public class OvertimeRecord implements Serializable {
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
     * 员工id
     */
    private String empId;

    /**
     * 工号
     */
    @Excel(name = "申请人工号", cellType = Excel.ColumnType.NUMERIC, type = Excel.Type.IMPORT)
    private String empNo;

    /**
     * 姓名
     */
    @Excel(name = "申请人姓名", type = Excel.Type.IMPORT)
    private String empName;

    /**
     * 岗位ID
     */
    private String postId;

    /**
     * 岗位名称
     */
    private String postName;

    /**
     * 加班开始日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Excel(name = "加班开始时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss", type = Excel.Type.IMPORT)
    private Date startDate;

    /**
     * 加班结束日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Excel(name = "加班结束时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss", type = Excel.Type.IMPORT)
    private Date endDate;

    /**
     * 加班小时数
     */
    @Excel(name = "加班小时数", cellType = Excel.ColumnType.NUMERIC, type = Excel.Type.IMPORT)
    private String overtimeHours;

    /**
     * 加班原因ID
     */
    @Excel(name = "加班原因编码", cellType = Excel.ColumnType.STRING, type = Excel.Type.IMPORT)
    private String overtimeReaId;

    /**
     * 加班类别ID
     */
    @Excel(name = "加班类别编码", cellType = Excel.ColumnType.STRING, type = Excel.Type.IMPORT)
    private String overtimeTypeId;

    /**
     * 加班天数
     */
    private String overtimeDays;

    /**
     * 辅助说明
     */
    @Excel(name = "辅助说明", type = Excel.Type.IMPORT)
    private String description;

    /**
     * 审核状态
     */
    private String status;

    /**
     * 是否有加班费
     */
    private String resultlt;

    /**
     * 刷卡时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date cardTime;

    /**
     * 输入人
     */
    private String creator;

    /**
     * 输入人ID
     */
    private String creatorId;

    /**
     * 输入日期
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;

    /**
     * 处理情况
     */
    private String disposeId;

    /**
     * 预留属性1
     */
    private String resvAttr1;

    /**
     * 预留属性2
     */
    private String resvAttr2;

    /**
     * 预留属性3
     */
    private String resvAttr3;

    /**
     * 预留属性4
     */
    private String resvAttr4;

    /**
     * 预留属性5
     */
    private String resvAttr5;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}