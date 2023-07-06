package com.jlkj.human.hd.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 班次数据对象 human_hd_shift_code
 *
 * @author 266861
 * @date 2023-04-06
 */
@Data
public class ShiftCode extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 公司别 */
    private String compId;

    /** 轮班方式ID */
    private String shiftmodeId;

    /** 班次编码 */
    @Excel(name = "班次编码")
    private String shiftCode;

    /** 班次开始时间_时 */
    @Excel(name = "班次开始时间_时")
    private String startHour;

    /** 班次开始时间_分 */
    private String startMin;

    /** 班次工作时间_时 */
    @Excel(name = "班次工作时间_时")
    private String conHour;

    /** 班次工作时间_分 */
    private String conMin;

    /** 班次结束时间_时 */
    private String endHour;

    /** 班次结束时间_分 */
    private String endMin;

    /** 是否中午打卡 */
    @Excel(name = "是否中午打卡")
    private String isNoon;

    /** 中间休息次数 */
    @Excel(name = "中间休息次数")
    private Long restCount;

    /** 大小夜 */
    @Excel(name = "大小夜")
    private String bigSmaNight;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

    /** 说明 */
    @Excel(name = "说明")
    private String description;

    /** 是否使用 */
    private String isUsed;

    /** 一次休息开始_时 */
    private String restStartHour;

    /** 一次休息开始_分 */
    private String restStartMin;

    /** 一次休息时长 */
    @Excel(name = "一次休息时长")
    private String restConMin;

    /** 一次休息结束_时 */
    private String restEndHour;

    /** 一次休息结束_分 */
    private String restEndMin;

    /** 二次休息开始_时 */
    private String restStartHour2;

    /** 二次休息开始_分 */
    private String restStartMin2;

    /** 二次休息时长 */
    private String restConMin2;

    /** 二次休息结束_时 */
    private String restEndHour2;

    /** 二次休息结束_分 */
    private String restEndMin2;

    /** 三次休息开始_时 */
    private String restStartHour3;

    /** 三次休息开始_分 */
    private String restStartMin3;

    /** 三次休息时长 */
    private String restConMin3;

    /** 三次休息结束_时 */
    private String restEndHour3;

    /** 三次休息结束_分 */
    private String restEndMin3;

    /** 输入人 */
    @Excel(name = "输入人")
    private String creator;

    /** 输入人ID */
    private String creatorId;

    /** 输入日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "输入日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createDate;

}
