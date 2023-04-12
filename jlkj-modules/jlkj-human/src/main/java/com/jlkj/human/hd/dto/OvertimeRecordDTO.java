package com.jlkj.human.hd.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 加班记录表
 * @author HuangBing
 * @TableName human_hd_overtime_record
 */
@TableName(value ="human_hd_overtime_record")
@Data
public class OvertimeRecordDTO implements Serializable {
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
    private String empName;

    /**
     * 工号
     */
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
     * 加班开始日期
     */
    private List startDate;

    /**
     * 加班结束日期
     */
    private List endDate;

    /**
     * 加班类别ID
     */
    private String overtimeTypeId;

    /**
     * 加班原因ID
     */
    private String overtimeReaId;

    /**
     * 加班天数
     */
    private String overtimeDays;

    /**
     * 加班小时数
     */
    private String overtimeHours;

    /**
     * 辅助说明
     */
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

    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}