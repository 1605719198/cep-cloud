package com.jlkj.human.hd.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 存班记录对象 human_hd_save_time
 *
 * @author 266861
 * @date 2023-07-10
 */
@Data
public class SaveTime extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 公司别 */
    @Excel(name = "公司别")
    private String compId;

    /** 年 */
    @Excel(name = "年")
    private Long year;

    /** 月（1-1月，2-1月，…… 12-12月，13-15季度，14-2季度，15-3季度，16-4季度，17-年） */
    @Excel(name = "月", readConverterExp = "1=-1月，2-1月，……,1=2-12月，13-15季度，14-2季度，15-3季度，16-4季度，17-年")
    private Long month;

    /** 员工id */
    @Excel(name = "员工id")
    private String empId;

    /** 姓名 */
    @Excel(name = "姓名")
    private String empName;

    /** 工号 */
    @Excel(name = "工号")
    private String empNo;

    /** 岗位ID */
    @Excel(name = "岗位ID")
    private String postId;

    /** 岗位名称 */
    @Excel(name = "岗位名称")
    private String postName;

    /** 加班天数（加班审批结束时，写入） */
    @Excel(name = "加班天数", readConverterExp = "加=班审批结束时，写入")
    private String overtimeDays;

    /** 加班小时数（加班审批结束时，写入） */
    @Excel(name = "加班小时数", readConverterExp = "加=班审批结束时，写入")
    private String overtimeHours;

    /** 存班天数（加班审批结束时，累加；补休审批结束时，减少） */
    @Excel(name = "存班天数", readConverterExp = "加=班审批结束时，累加；补休审批结束时，减少")
    private String saveDays;

    /** 存班小时数（加班审批结束时，累加；补休审批结束时，减少） */
    @Excel(name = "存班小时数", readConverterExp = "加=班审批结束时，累加；补休审批结束时，减少")
    private String saveHours;

    /** 补休天数（补休审批结束时，减少） */
    @Excel(name = "补休天数", readConverterExp = "补=休审批结束时，减少")
    private String compDays;

    /** 补休小时数（补休审批结束时，写入） */
    @Excel(name = "补休小时数", readConverterExp = "补=休审批结束时，写入")
    private String compHours;

    /** 输入人 */
    @Excel(name = "输入人")
    private String creator;

    /** 输入人ID */
    @Excel(name = "输入人ID")
    private String creatorId;

    /** 输入日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "输入日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createDate;

}
