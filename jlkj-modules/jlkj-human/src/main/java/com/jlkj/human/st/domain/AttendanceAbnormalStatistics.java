package com.jlkj.human.st.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 出勤异常统计对象 human_hd_attendance_abnormal
 *
 * @author 267383
 * @date 2023-06-18
 */
public class AttendanceAbnormalStatistics extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    @Excel(name = "序号")
    private String id;

    /** 公司别 */
    private String compId;

    /** 员工id */
    private String empId;

    /** 姓名 */
    @Excel(name = "姓名")
    private String empName;

    /** 工号 */
    @Excel(name = "工号")
    private String empNo;

    /** 岗位ID */
    private String postId;

    /** 岗位名称 */
    @Excel(name = "岗位名称")
    private String postName;

    /** 轮班方式ID */
    private String turnTypeId;

    /** 轮班方式名称 */
    private String turnTypeName;

    /** 班别ID */
    private String classId;

    /** 班别名称 */
    private String className;

    /** 异常原因ID */
    @Excel(name = "异常原因")
    private String excReaId;

    /** 出勤证明原因ID */
    private String proveReaId;

    /** 出勤证明原因 */
    @Excel(name = "出勤证明原因细分")
    private String proveReason;

    /** 审批状态 */
    private String status;

    /** 缺勤类别 */
    private String undutyType;

    /** 正常出勤时段开始 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "正常出勤时段开始", width = 30, dateFormat = "yyyy-MM-dd")
    private Date norOndutyBegin;

    /** 正常出勤时段结束 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "正常出勤时段结束", width = 30, dateFormat = "yyyy-MM-dd")
    private Date norOndutyEnd;

    /** 实际出勤时段开始 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "实际出勤时段开始", width = 30, dateFormat = "yyyy-MM-dd")
    private Date slotCardOnduty;

    /** 实际出勤时段结束 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "实际出勤时段结束", width = 30, dateFormat = "yyyy-MM-dd")
    private Date slotCardOffduty;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date1;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date2;

    /** 辅助说明 */
    private String description;

    /** 呈核主管员工ID */
    private String chiefempnoId;

    /** 呈核主管工号 */
    private String chiefempnoNo;

    /** 呈核主管姓名 */
    private String chiefempnoName;

    /** 呈核主管岗位ID */
    private String chiefempnoPostId;

    /** 审核日期 */
    private String appDate;

    /** 审核人 */
    private String appName;

    /** 呈核主管岗位名称 */
    private String chiefempnoPostname;

    /** 处理情况 */
    @Excel(name = "处理情况")
    private String disposeId;

    /** 序号 */
    @Excel(name = "序号")
    private String num;

    /** 审核类型 */
    private String auditType;

    /** 输入人 */
    private String creator;

    /** 输入人ID */
    private String creatorId;

    /** 输入日期 */
    private Date createDate;

    public void setId(String id)
    {
        this.id = id;
    }

    public String getId()
    {
        return id;
    }
    public void setUndutyType(String undutyType)
    {
        this.undutyType = undutyType;
    }

    public String getUndutyType()
    {
        return undutyType;
    }
    public void setCompId(String compId)
    {
        this.compId = compId;
    }

    public String getCompId()
    {
        return compId;
    }
    public void setEmpId(String empId)
    {
        this.empId = empId;
    }

    public String getEmpId()
    {
        return empId;
    }
    public void setEmpName(String empName)
    {
        this.empName = empName;
    }

    public String getEmpName()
    {
        return empName;
    }
    public void setEmpNo(String empNo)
    {
        this.empNo = empNo;
    }

    public String getEmpNo()
    {
        return empNo;
    }
    public void setPostId(String postId)
    {
        this.postId = postId;
    }

    public String getPostId()
    {
        return postId;
    }
    public void setPostName(String postName)
    {
        this.postName = postName;
    }

    public String getPostName()
    {
        return postName;
    }
    public void setTurnTypeId(String turnTypeId)
    {
        this.turnTypeId = turnTypeId;
    }

    public void setDate1(Date date1)
    {
        this.date1 = date1;
    }
    public Date getDate1()
    {
        return date1;
    }

    public void setDate2(Date date2)
    {
        this.date2 = date2;
    }
    public Date getDate2()
    {
        return date2;
    }

    public String getTurnTypeId()
    {
        return turnTypeId;
    }
    public void setTurnTypeName(String turnTypeName)
    {
        this.turnTypeName = turnTypeName;
    }

    public String getTurnTypeName()
    {
        return turnTypeName;
    }
    public void setClassId(String classId)
    {
        this.classId = classId;
    }

    public String getClassId()
    {
        return classId;
    }
    public void setClassName(String className)
    {
        this.className = className;
    }

    public String getClassName()
    {
        return className;
    }
    public void setExcReaId(String excReaId)
    {
        this.excReaId = excReaId;
    }

    public String getExcReaId()
    {
        return excReaId;
    }
    public void setProveReaId(String proveReaId)
    {
        this.proveReaId = proveReaId;
    }

    public String getProveReaId()
    {
        return proveReaId;
    }
    public void setProveReason(String proveReason)
    {
        this.proveReason = proveReason;
    }

    public String getProveReason()
    {
        return proveReason;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }
    public void setNorOndutyBegin(Date norOndutyBegin)
    {
        this.norOndutyBegin = norOndutyBegin;
    }

    public Date getNorOndutyBegin()
    {
        return norOndutyBegin;
    }
    public void setNorOndutyEnd(Date norOndutyEnd)
    {
        this.norOndutyEnd = norOndutyEnd;
    }

    public Date getNorOndutyEnd()
    {
        return norOndutyEnd;
    }
    public void setSlotCardOnduty(Date slotCardOnduty)
    {
        this.slotCardOnduty = slotCardOnduty;
    }

    public Date getSlotCardOnduty()
    {
        return slotCardOnduty;
    }
    public void setSlotCardOffduty(Date slotCardOffduty)
    {
        this.slotCardOffduty = slotCardOffduty;
    }

    public Date getSlotCardOffduty()
    {
        return slotCardOffduty;
    }
    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getDescription()
    {
        return description;
    }
    public void setChiefempnoId(String chiefempnoId)
    {
        this.chiefempnoId = chiefempnoId;
    }

    public String getChiefempnoId()
    {
        return chiefempnoId;
    }
    public void setChiefempnoNo(String chiefempnoNo)
    {
        this.chiefempnoNo = chiefempnoNo;
    }

    public String getChiefempnoNo()
    {
        return chiefempnoNo;
    }
    public void setChiefempnoName(String chiefempnoName)
    {
        this.chiefempnoName = chiefempnoName;
    }

    public String getChiefempnoName()
    {
        return chiefempnoName;
    }
    public void setChiefempnoPostId(String chiefempnoPostId)
    {
        this.chiefempnoPostId = chiefempnoPostId;
    }

    public String getChiefempnoPostId()
    {
        return chiefempnoPostId;
    }
    public void setAppDate(String appDate)
    {
        this.appDate = appDate;
    }

    public String getAppDate()
    {
        return appDate;
    }
    public void setAppName(String appName)
    {
        this.appName = appName;
    }

    public String getAppName()
    {
        return appName;
    }
    public void setChiefempnoPostname(String chiefempnoPostname)
    {
        this.chiefempnoPostname = chiefempnoPostname;
    }

    public String getChiefempnoPostname()
    {
        return chiefempnoPostname;
    }
    public void setDisposeId(String disposeId)
    {
        this.disposeId = disposeId;
    }

    public String getDisposeId()
    {
        return disposeId;
    }
    public void setAuditType(String auditType)
    {
        this.auditType = auditType;
    }

    public String getAuditType()
    {
        return auditType;
    }
    public void setCreator(String creator)
    {
        this.creator = creator;
    }

    public String getCreator()
    {
        return creator;
    }
    public void setCreatorId(String creatorId)
    {
        this.creatorId = creatorId;
    }

    public String getCreatorId()
    {
        return creatorId;
    }
    public void setCreateDate(Date createDate)
    {
        this.createDate = createDate;
    }

    public Date getCreateDate()
    {
        return createDate;
    }
    public void setNum(String num)
    {
        this.num = num;
    }

    public String getNum()
    {
        return num;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
                .append("num", getNum())
                .append("undutyType", getUndutyType())
            .append("compId", getCompId())
            .append("empId", getEmpId())
            .append("empName", getEmpName())
            .append("empNo", getEmpNo())
            .append("postId", getPostId())
            .append("postName", getPostName())
            .append("turnTypeId", getTurnTypeId())
            .append("turnTypeName", getTurnTypeName())
            .append("classId", getClassId())
            .append("className", getClassName())
            .append("excReaId", getExcReaId())
            .append("proveReaId", getProveReaId())
            .append("proveReason", getProveReason())
            .append("status", getStatus())
            .append("norOndutyBegin", getNorOndutyBegin())
            .append("norOndutyEnd", getNorOndutyEnd())
            .append("slotCardOnduty", getSlotCardOnduty())
            .append("slotCardOffduty", getSlotCardOffduty())
            .append("description", getDescription())
            .append("chiefempnoId", getChiefempnoId())
            .append("chiefempnoNo", getChiefempnoNo())
            .append("chiefempnoName", getChiefempnoName())
            .append("chiefempnoPostId", getChiefempnoPostId())
            .append("chiefempnoPostname", getChiefempnoPostname())
                .append("appDate", getAppDate())
                .append("appName", getAppName())
            .append("disposeId", getDisposeId())
            .append("auditType", getAuditType())
            .append("creator", getCreator())
            .append("creatorId", getCreatorId())
            .append("createDate", getCreateDate())
                .append("date1", getDate1())
                .append("date2", getDate2())
            .toString();
    }
}
