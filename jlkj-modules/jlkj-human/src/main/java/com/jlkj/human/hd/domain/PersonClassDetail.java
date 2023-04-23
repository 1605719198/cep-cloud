package com.jlkj.human.hd.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 人员排班明细对象 human_hd_person_class_detail
 *
 * @author 266861
 * @date 2023-04-12
 */
@Data
public class PersonClassDetail extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 主档ID */
    @Excel(name = "主档ID")
    private String personClassMasterId;

    /** 公司别 */
    @Excel(name = "公司别")
    private String compId;

    /** 类别 */
    @Excel(name = "类别")
    private String classType;

    /** 工号 */
    @Excel(name = "工号")
    private String empId;

    /** 组织机构记录ID */
    @Excel(name = "组织机构记录ID")
    private String deptId;

    /** 排班日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "排班日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date arrShiDate;

    /** 排班开始日期 */
    private Date startDate;

    /** 排班结束日期 */
    private Date endDate;

    /** 班次一ID */
    @Excel(name = "班次一ID")
    private String firShiftId;

    /** 班次一编码 */
    @Excel(name = "班次一编码")
    private String firShiftCode;

    /** 班次二ID */
    @Excel(name = "班次二ID")
    private String secShiftId;

    /** 班次二编码 */
    @Excel(name = "班次二编码")
    private String secShiftCode;

    /** 轮班方式ID */
    @Excel(name = "轮班方式ID")
    private String shiftModeId;

    /** 班别ID */
    @Excel(name = "班别ID")
    private String classId;

    /** 待定 */
    @Excel(name = "待定")
    private String isJoinShift;

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

    public void setId(String id)
    {
        this.id = id;
    }

    public String getId()
    {
        return id;
    }
    public void setPersonClassMasterId(String personClassMasterId)
    {
        this.personClassMasterId = personClassMasterId;
    }

    public String getPersonClassMasterId()
    {
        return personClassMasterId;
    }
    public void setCompId(String compId)
    {
        this.compId = compId;
    }

    public String getCompId()
    {
        return compId;
    }
    public void setClassType(String classType)
    {
        this.classType = classType;
    }

    public String getClassType()
    {
        return classType;
    }
    public void setEmpId(String empId)
    {
        this.empId = empId;
    }

    public String getEmpId()
    {
        return empId;
    }
    public void setDeptId(String deptId)
    {
        this.deptId = deptId;
    }

    public String getDeptId()
    {
        return deptId;
    }
    public void setArrShiDate(Date arrShiDate)
    {
        this.arrShiDate = arrShiDate;
    }

    public Date getArrShiDate()
    {
        return arrShiDate;
    }
    public void setFirShiftId(String firShiftId)
    {
        this.firShiftId = firShiftId;
    }

    public String getFirShiftId()
    {
        return firShiftId;
    }
    public void setFirShiftCode(String firShiftCode)
    {
        this.firShiftCode = firShiftCode;
    }

    public String getFirShiftCode()
    {
        return firShiftCode;
    }
    public void setSecShiftId(String secShiftId)
    {
        this.secShiftId = secShiftId;
    }

    public String getSecShiftId()
    {
        return secShiftId;
    }
    public void setSecShiftCode(String secShiftCode)
    {
        this.secShiftCode = secShiftCode;
    }

    public String getSecShiftCode()
    {
        return secShiftCode;
    }
    public void setShiftModeId(String shiftModeId)
    {
        this.shiftModeId = shiftModeId;
    }

    public String getShiftModeId()
    {
        return shiftModeId;
    }
    public void setClassId(String classId)
    {
        this.classId = classId;
    }

    public String getClassId()
    {
        return classId;
    }
    public void setIsJoinShift(String isJoinShift)
    {
        this.isJoinShift = isJoinShift;
    }

    public String getIsJoinShift()
    {
        return isJoinShift;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("personClassMasterId", getPersonClassMasterId())
            .append("compId", getCompId())
            .append("classType", getClassType())
            .append("empId", getEmpId())
            .append("deptId", getDeptId())
            .append("arrShiDate", getArrShiDate())
            .append("firShiftId", getFirShiftId())
            .append("firShiftCode", getFirShiftCode())
            .append("secShiftId", getSecShiftId())
            .append("secShiftCode", getSecShiftCode())
            .append("shiftModeId", getShiftModeId())
            .append("classId", getClassId())
            .append("isJoinShift", getIsJoinShift())
            .append("creator", getCreator())
            .append("creatorId", getCreatorId())
            .append("createDate", getCreateDate())
            .toString();
    }
}
