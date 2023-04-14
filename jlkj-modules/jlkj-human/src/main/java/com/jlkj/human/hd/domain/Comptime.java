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
 * 补休记录表
 * @author HuangBing
 * @TableName human_hd_comptime
 */
@TableName(value ="human_hd_comptime")
@Data
public class Comptime implements Serializable {
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
     * 姓名
     */
    @Excel(name = "申请人姓名", type = Excel.Type.IMPORT)
    private String empName;

    /**
     * 工号
     */
    @Excel(name = "申请人工号", cellType = Excel.ColumnType.NUMERIC, type = Excel.Type.IMPORT)
    private String empNo;

    /**
     * 岗位ID
     */
    private String postId;

    /**
     * 岗位名称
     */
    private String postName;

    /**
     * 补休开始日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Excel(name = "补休开始时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss", type = Excel.Type.IMPORT)
    private Date startDate;

    /**
     * 补休结束日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Excel(name = "补休开始时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss", type = Excel.Type.IMPORT)
    private Date endDate;

    /**
     * 存班小时数
     */
    private String saveHours;

    /**
     * 补休小时数
     */
    private String compHours;

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

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}