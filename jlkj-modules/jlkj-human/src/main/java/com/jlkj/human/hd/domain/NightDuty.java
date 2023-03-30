package com.jlkj.human.hd.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.common.core.annotation.Excel;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 夜班查询表
 * @TableName human_hd_night_duty
 */
@TableName(value ="human_hd_night_duty")
@Data
public class NightDuty implements Serializable {
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
     * 员工Id
     */
    private String empId;

    /**
     * 工号
     */
    @Excel(name = "工号", cellType = Excel.ColumnType.NUMERIC, type = Excel.Type.IMPORT)
    private String empNo;

    /**
     * 姓名
     */
    @Excel(name = "姓名", type = Excel.Type.IMPORT)
    private String empName;

    /**
     * 岗位全称
     */
    @Excel(name = "岗位", type = Excel.Type.IMPORT)
    private String postFullname;

    /**
     * 岗位ID
     */
    private String postId;

    /**
     * 班次ID
     */
    private String shiftId;

    /**
     * 排班日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @Excel(name = "班次日期", width = 30, dateFormat = "yyyy-MM-dd", type = Excel.Type.IMPORT)
    private Date shiftDate;

    /**
     * 大夜数
     */
    @Excel(name = "大夜数", type = Excel.Type.IMPORT)
    private BigDecimal bigNight;

    /**
     * 小夜数
     */
    @Excel(name = "小夜数", type = Excel.Type.IMPORT)
    private BigDecimal smallNight;

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

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}