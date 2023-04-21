package com.jlkj.human.hp.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 岗位信息数据维护对象 sys_post
 *
 * @author 266861
 * @date 2023-03-15
 */
public class SysPost extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long postId;

    /** 岗位编码 */
    private String postCode;

    /** 岗位名称 */
    @Excel(name = "岗位名称")
    private String postName;

    /** 所属公司/机构 */
    private Long deptId;

    /** 专长类别id */
    private String specialityTypeId;

    /** 专长id */
    private String specialityId;

    /** 岗位序列id */
    private String postSequenceId;

    /** 岗位类别id */
    private String postTypeId;

    /** 职位类别 */
    private String deptType;

    /** 职位名称id */
    private String jobTitleId;

    /** 流水号 */
    private String serialNumber;

    /** 倒班方式 */
    private String shiftWorkId;

    /** 用工性质 */
    private String employmentNatureId;

    /** 工作地点 */
    private String workAddressId;

    /** 公司（具体工作地点） */
    private String workAddress;

    /** 驻外地点（具体工作地点） */
    private String overseasLocations;

    /** 是否配置专人 */
    private String ifSpeciallyAssignedPerson;

    /** 定员 */
    @Excel(name = "定员")
    private Long planCapacity;

    /** 现员 */
    @Excel(name = "现员")
    private Long nowCapacity;

    /** 显示顺序 */
    private Long postSort;

    /** 定员依据 */
    private String capacityBasis;

    /** 成本中心 */
    private String costCenterId;

    /** 状态 */
    private String status;

    /** 上级岗位ID */
    private Long parentPostId;

    /** 该岗位上级岗位名称 */
    @Excel(name = "该岗位上级岗位名称")
    private String parentPostName;

    /** 职级ID */
    private String positionsClassId;

    /** 所属公司/机构id */
    private Long orgId;

    /** 所属公司/机构名称 */
    private String orgName;

    /** 所属公司/机构全称 */
    private String orgFullName;

    /** 职位费用类别 */
    private String positionExpenseCategory;

    /** 成本中心名称 */
    private String costCenterName;

    /** 变更原因 */
    private String changeReason;

    /** 生效日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date effectDate;

    /** 版本 */
    private String versionNo;

    /** 是否新增 */
    private String ifNew;

    /** 公司别 */
    private String compId;

    /** 岗位工作概述 */
    private String workDesc;

    /** 岗位职责内容 */
    private String workCon;

    /** 考核指标 */
    private String apprKpi;

    /** 检讨指标 */
    private String reviewKpi;

    /** 工作标准 */
    private String workSta;

    /** 工作权限 */
    private String workAut;

    /** 工作环境 */
    private String workEnv;

    /** 使用工具 */
    private String opeEquHp;

    /** 使用工具 */
    private String toolUsed;

    /** 办公用品 */
    private String officeEqu;

    /** 劳保标准 */
    private String labourSta;

    /** 最小年龄 */
    private Long minAge;

    /** 最大年龄 */
    private Long maxAge;

    /** 性别 */
    private String gender;

    /** 最低学历 */
    private String recordId;

    /** 专业要求 */
    private String speClaim;

    /** 职业资格 */
    private String proQua;

    /** 工作经验 */
    private String workExp;

    /** 其他要求 */
    private String otherClaim;

    /** 知识与技能 */
    private String knowTec;

    /** 职业化行为 */
    private String proAct;

    public void setPostId(Long postId)
    {
        this.postId = postId;
    }

    public Long getPostId()
    {
        return postId;
    }
    public void setPostCode(String postCode)
    {
        this.postCode = postCode;
    }

    public String getPostCode()
    {
        return postCode;
    }
    public void setPostName(String postName)
    {
        this.postName = postName;
    }

    public String getPostName()
    {
        return postName;
    }
    public void setDeptId(Long deptId)
    {
        this.deptId = deptId;
    }

    public Long getDeptId()
    {
        return deptId;
    }
    public void setSpecialityTypeId(String specialityTypeId)
    {
        this.specialityTypeId = specialityTypeId;
    }

    public String getSpecialityTypeId()
    {
        return specialityTypeId;
    }
    public void setSpecialityId(String specialityId)
    {
        this.specialityId = specialityId;
    }

    public String getSpecialityId()
    {
        return specialityId;
    }
    public void setPostSequenceId(String postSequenceId)
    {
        this.postSequenceId = postSequenceId;
    }

    public String getPostSequenceId()
    {
        return postSequenceId;
    }
    public void setPostTypeId(String postTypeId)
    {
        this.postTypeId = postTypeId;
    }

    public String getPostTypeId()
    {
        return postTypeId;
    }
    public void setJobTitleId(String jobTitleId)
    {
        this.jobTitleId = jobTitleId;
    }

    public String getJobTitleId()
    {
        return jobTitleId;
    }
    public void setSerialNumber(String serialNumber)
    {
        this.serialNumber = serialNumber;
    }

    public String getSerialNumber()
    {
        return serialNumber;
    }
    public void setShiftWorkId(String shiftWorkId)
    {
        this.shiftWorkId = shiftWorkId;
    }

    public String getShiftWorkId()
    {
        return shiftWorkId;
    }
    public void setDeptType(String deptType)
    {
        this.deptType = deptType;
    }

    public String getDeptType()
    {
        return deptType;
    }
    public void setEmploymentNatureId(String employmentNatureId)
    {
        this.employmentNatureId = employmentNatureId;
    }

    public String getEmploymentNatureId()
    {
        return employmentNatureId;
    }
    public void setWorkAddressId(String workAddressId)
    {
        this.workAddressId = workAddressId;
    }

    public String getWorkAddressId()
    {
        return workAddressId;
    }
    public void setWorkAddress(String workAddress)
    {
        this.workAddress = workAddress;
    }

    public String getWorkAddress()
    {
        return workAddress;
    }
    public void setOverseasLocations(String overseasLocations)
    {
        this.overseasLocations = overseasLocations;
    }

    public String getOverseasLocations()
    {
        return overseasLocations;
    }
    public void setIfSpeciallyAssignedPerson(String ifSpeciallyAssignedPerson)
    {
        this.ifSpeciallyAssignedPerson = ifSpeciallyAssignedPerson;
    }

    public String getIfSpeciallyAssignedPerson()
    {
        return ifSpeciallyAssignedPerson;
    }
    public void setPlanCapacity(Long planCapacity)
    {
        this.planCapacity = planCapacity;
    }

    public Long getPlanCapacity()
    {
        return planCapacity;
    }
    public void setNowCapacity(Long nowCapacity)
    {
        this.nowCapacity = nowCapacity;
    }

    public Long getNowCapacity()
    {
        return nowCapacity;
    }
    public void setPostSort(Long postSort)
    {
        this.postSort = postSort;
    }

    public Long getPostSort()
    {
        return postSort;
    }
    public void setCapacityBasis(String capacityBasis)
    {
        this.capacityBasis = capacityBasis;
    }

    public String getCapacityBasis()
    {
        return capacityBasis;
    }
    public void setCostCenterId(String costCenterId)
    {
        this.costCenterId = costCenterId;
    }

    public String getCostCenterId()
    {
        return costCenterId;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }
    public void setParentPostId(Long parentPostId)
    {
        this.parentPostId = parentPostId;
    }

    public Long getParentPostId()
    {
        return parentPostId;
    }
    public void setParentPostName(String parentPostName)
    {
        this.parentPostName = parentPostName;
    }

    public String getParentPostName()
    {
        return parentPostName;
    }
    public void setPositionsClassId(String positionsClassId)
    {
        this.positionsClassId = positionsClassId;
    }

    public String getPositionsClassId()
    {
        return positionsClassId;
    }
    public void setOrgId(Long orgId)
    {
        this.orgId = orgId;
    }

    public Long getOrgId()
    {
        return orgId;
    }
    public void setOrgName(String orgName)
    {
        this.orgName = orgName;
    }

    public String getOrgName()
    {
        return orgName;
    }
    public void setOrgFullName(String orgFullName)
    {
        this.orgFullName = orgFullName;
    }

    public String getOrgFullName()
    {
        return orgFullName;
    }
    public void setPositionExpenseCategory(String positionExpenseCategory)
    {
        this.positionExpenseCategory = positionExpenseCategory;
    }

    public String getPositionExpenseCategory()
    {
        return positionExpenseCategory;
    }
    public void setCostCenterName(String costCenterName)
    {
        this.costCenterName = costCenterName;
    }

    public String getCostCenterName()
    {
        return costCenterName;
    }
    public void setChangeReason(String changeReason)
    {
        this.changeReason = changeReason;
    }

    public String getChangeReason()
    {
        return changeReason;
    }
    public void setEffectDate(Date effectDate)
    {
        this.effectDate = effectDate;
    }

    public Date getEffectDate()
    {
        return effectDate;
    }
    public void setVersionNo(String versionNo)
    {
        this.versionNo = versionNo;
    }

    public String getVersionNo()
    {
        return versionNo;
    }
    public void setIfNew(String ifNew)
    {
        this.ifNew = ifNew;
    }

    public String getIfNew()
    {
        return ifNew;
    }
    public void setCompId(String compId)
    {
        this.compId = compId;
    }

    public String getCompId()
    {
        return compId;
    }
    public void setWorkDesc(String workDesc)
    {
        this.workDesc = workDesc;
    }

    public String getWorkDesc()
    {
        return workDesc;
    }
    public void setWorkCon(String workCon)
    {
        this.workCon = workCon;
    }

    public String getWorkCon()
    {
        return workCon;
    }
    public void setApprKpi(String apprKpi)
    {
        this.apprKpi = apprKpi;
    }

    public String getApprKpi()
    {
        return apprKpi;
    }
    public void setReviewKpi(String reviewKpi)
    {
        this.reviewKpi = reviewKpi;
    }

    public String getReviewKpi()
    {
        return reviewKpi;
    }
    public void setWorkSta(String workSta)
    {
        this.workSta = workSta;
    }

    public String getWorkSta()
    {
        return workSta;
    }
    public void setWorkAut(String workAut)
    {
        this.workAut = workAut;
    }

    public String getWorkAut()
    {
        return workAut;
    }
    public void setWorkEnv(String workEnv)
    {
        this.workEnv = workEnv;
    }

    public String getWorkEnv()
    {
        return workEnv;
    }
    public void setOpeEquHp(String opeEquHp)
    {
        this.opeEquHp = opeEquHp;
    }

    public String getOpeEquHp()
    {
        return opeEquHp;
    }
    public void setToolUsed(String toolUsed)
    {
        this.toolUsed = toolUsed;
    }

    public String getToolUsed()
    {
        return toolUsed;
    }
    public void setOfficeEqu(String officeEqu)
    {
        this.officeEqu = officeEqu;
    }

    public String getOfficeEqu()
    {
        return officeEqu;
    }
    public void setLabourSta(String labourSta)
    {
        this.labourSta = labourSta;
    }

    public String getLabourSta()
    {
        return labourSta;
    }
    public void setMinAge(Long minAge)
    {
        this.minAge = minAge;
    }

    public Long getMinAge()
    {
        return minAge;
    }
    public void setMaxAge(Long maxAge)
    {
        this.maxAge = maxAge;
    }

    public Long getMaxAge()
    {
        return maxAge;
    }
    public void setGender(String gender)
    {
        this.gender = gender;
    }

    public String getGender()
    {
        return gender;
    }
    public void setRecordId(String recordId)
    {
        this.recordId = recordId;
    }

    public String getRecordId()
    {
        return recordId;
    }
    public void setSpeClaim(String speClaim)
    {
        this.speClaim = speClaim;
    }

    public String getSpeClaim()
    {
        return speClaim;
    }
    public void setProQua(String proQua)
    {
        this.proQua = proQua;
    }

    public String getProQua()
    {
        return proQua;
    }
    public void setWorkExp(String workExp)
    {
        this.workExp = workExp;
    }

    public String getWorkExp()
    {
        return workExp;
    }
    public void setOtherClaim(String otherClaim)
    {
        this.otherClaim = otherClaim;
    }

    public String getOtherClaim()
    {
        return otherClaim;
    }
    public void setKnowTec(String knowTec)
    {
        this.knowTec = knowTec;
    }

    public String getKnowTec()
    {
        return knowTec;
    }
    public void setProAct(String proAct)
    {
        this.proAct = proAct;
    }

    public String getProAct()
    {
        return proAct;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("postId", getPostId())
            .append("postCode", getPostCode())
            .append("postName", getPostName())
            .append("deptId", getDeptId())
            .append("specialityTypeId", getSpecialityTypeId())
            .append("deptType", getDeptType())
            .append("specialityId", getSpecialityId())
            .append("postSequenceId", getPostSequenceId())
            .append("postTypeId", getPostTypeId())
            .append("jobTitleId", getJobTitleId())
            .append("serialNumber", getSerialNumber())
            .append("shiftWorkId", getShiftWorkId())
            .append("employmentNatureId", getEmploymentNatureId())
            .append("workAddressId", getWorkAddressId())
            .append("workAddress", getWorkAddress())
            .append("overseasLocations", getOverseasLocations())
            .append("ifSpeciallyAssignedPerson", getIfSpeciallyAssignedPerson())
            .append("planCapacity", getPlanCapacity())
            .append("nowCapacity", getNowCapacity())
            .append("postSort", getPostSort())
            .append("capacityBasis", getCapacityBasis())
            .append("costCenterId", getCostCenterId())
            .append("status", getStatus())
            .append("parentPostId", getParentPostId())
            .append("parentPostName", getParentPostName())
            .append("positionsClassId", getPositionsClassId())
            .append("orgId", getOrgId())
            .append("orgName", getOrgName())
            .append("orgFullName", getOrgFullName())
            .append("positionExpenseCategory", getPositionExpenseCategory())
            .append("costCenterName", getCostCenterName())
            .append("changeReason", getChangeReason())
            .append("effectDate", getEffectDate())
            .append("versionNo", getVersionNo())
            .append("ifNew", getIfNew())
            .append("compId", getCompId())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("workDesc", getWorkDesc())
            .append("workCon", getWorkCon())
            .append("apprKpi", getApprKpi())
            .append("reviewKpi", getReviewKpi())
            .append("workSta", getWorkSta())
            .append("workAut", getWorkAut())
            .append("workEnv", getWorkEnv())
            .append("opeEquHp", getOpeEquHp())
            .append("toolUsed", getToolUsed())
            .append("officeEqu", getOfficeEqu())
            .append("labourSta", getLabourSta())
            .append("minAge", getMinAge())
            .append("maxAge", getMaxAge())
            .append("gender", getGender())
            .append("recordId", getRecordId())
            .append("speClaim", getSpeClaim())
            .append("proQua", getProQua())
            .append("workExp", getWorkExp())
            .append("otherClaim", getOtherClaim())
            .append("knowTec", getKnowTec())
            .append("proAct", getProAct())
            .toString();
    }
}
