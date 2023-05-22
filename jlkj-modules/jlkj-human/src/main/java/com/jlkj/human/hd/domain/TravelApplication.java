package com.jlkj.human.hd.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 出差申请对象 human_hd_travel_application
 *
 * @author jlkj
 * @date 2023-04-27
 */
public class TravelApplication extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 出差单号 */
    @Excel(name = "出差单号")
    private String travelNo;

    /** 出差类别 */
    @Excel(name = "出差类别")
    private String travelTpye;

    /** 出差事由类别 */
    @Excel(name = "出差事由类别")
    private String travelReason;

    /** 公司别 */
    @Excel(name = "公司别")
    private String compId;

    /** 员工id */
    @Excel(name = "员工id")
    private String empId;

    /** 工号 */
    @Excel(name = "工号")
    private String empNo;

    /** 姓名 */
    @Excel(name = "姓名")
    private String empName;

    /** 部门名称 */
    @Excel(name = "部门名称")
    private String deptName;

    /** 申请单状态 */
    @Excel(name = "申请单状态")
    private String status;

    /** 代理人ID */
    @Excel(name = "代理人ID")
    private String agentId;

    /** 代理人NO */
    @Excel(name = "代理人NO")
    private String agentNo;

    /** 代理人 */
    @Excel(name = "代理人")
    private String agentName;

    /** 成本中心 */
    @Excel(name = "成本中心")
    private String costCenter;

    /** 预算编号 */
    @Excel(name = "预算编号")
    private String contract;

    /** 出差开始日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "出差开始日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startDate;

    /** 出差开始时分 */
    @Excel(name = "出差开始时分")
    private String startTime;

    /** 出差结束日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "出差结束日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endDate;

    /** 出差结束时分 */
    @Excel(name = "出差结束时分")
    private String endTime;

    /** 实际出差开始日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "实际出差开始日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date realStartDate;

    /** 实际出差开始时分 */
    @Excel(name = "实际出差开始时分")
    private String realStartTime;

    /** 实际出差结束日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "实际出差结束日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date realEndDate;

    /** 实际出差结束时分 */
    @Excel(name = "实际出差结束时分")
    private String realEndTime;

    /** 出差事由 */
    @Excel(name = "出差事由")
    private String reasonDesc;

    /** 报支单号 */
    @Excel(name = "报支单号")
    private String billNo;

    /** 是否工程费用 */
    @Excel(name = "是否工程费用")
    private String isEngineer;

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

    /** 预留属性1 */
    @Excel(name = "预留属性1")
    private String resvAttr1;

    /** 预留属性2 */
    @Excel(name = "预留属性2")
    private String resvAttr2;

    /** 预留属性3 */
    @Excel(name = "预留属性3")
    private String resvAttr3;

    /** 预留属性4 */
    @Excel(name = "预留属性4")
    private String resvAttr4;

    public void setId(String id)
    {
        this.id = id;
    }

    public String getId()
    {
        return id;
    }
    public void setTravelNo(String travelNo)
    {
        this.travelNo = travelNo;
    }

    public String getTravelNo()
    {
        return travelNo;
    }
    public void setTravelTpye(String travelTpye)
    {
        this.travelTpye = travelTpye;
    }

    public String getTravelTpye()
    {
        return travelTpye;
    }
    public void setTravelReason(String travelReason)
    {
        this.travelReason = travelReason;
    }

    public String getTravelReason()
    {
        return travelReason;
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
    public void setEmpNo(String empNo)
    {
        this.empNo = empNo;
    }

    public String getEmpNo()
    {
        return empNo;
    }
    public void setEmpName(String empName)
    {
        this.empName = empName;
    }

    public String getEmpName()
    {
        return empName;
    }
    public void setDeptName(String deptName)
    {
        this.deptName = deptName;
    }

    public String getDeptName()
    {
        return deptName;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }
    public void setAgentId(String agentId)
    {
        this.agentId = agentId;
    }

    public String getAgentId()
    {
        return agentId;
    }
    public void setAgentNo(String agentNo)
    {
        this.agentNo = agentNo;
    }

    public String getAgentNo()
    {
        return agentNo;
    }
    public void setAgentName(String agentName)
    {
        this.agentName = agentName;
    }

    public String getAgentName()
    {
        return agentName;
    }
    public void setCostCenter(String costCenter)
    {
        this.costCenter = costCenter;
    }

    public String getCostCenter()
    {
        return costCenter;
    }
    public void setContract(String contract)
    {
        this.contract = contract;
    }

    public String getContract()
    {
        return contract;
    }
    public void setStartDate(Date startDate)
    {
        this.startDate = startDate;
    }

    public Date getStartDate()
    {
        return startDate;
    }
    public void setStartTime(String startTime)
    {
        this.startTime = startTime;
    }

    public String getStartTime()
    {
        return startTime;
    }
    public void setEndDate(Date endDate)
    {
        this.endDate = endDate;
    }

    public Date getEndDate()
    {
        return endDate;
    }
    public void setEndTime(String endTime)
    {
        this.endTime = endTime;
    }

    public String getEndTime()
    {
        return endTime;
    }
    public void setRealStartDate(Date realStartDate)
    {
        this.realStartDate = realStartDate;
    }

    public Date getRealStartDate()
    {
        return realStartDate;
    }
    public void setRealStartTime(String realStartTime)
    {
        this.realStartTime = realStartTime;
    }

    public String getRealStartTime()
    {
        return realStartTime;
    }
    public void setRealEndDate(Date realEndDate)
    {
        this.realEndDate = realEndDate;
    }

    public Date getRealEndDate()
    {
        return realEndDate;
    }
    public void setRealEndTime(String realEndTime)
    {
        this.realEndTime = realEndTime;
    }

    public String getRealEndTime()
    {
        return realEndTime;
    }
    public void setReasonDesc(String reasonDesc)
    {
        this.reasonDesc = reasonDesc;
    }

    public String getReasonDesc()
    {
        return reasonDesc;
    }
    public void setBillNo(String billNo)
    {
        this.billNo = billNo;
    }

    public String getBillNo()
    {
        return billNo;
    }
    public void setIsEngineer(String isEngineer)
    {
        this.isEngineer = isEngineer;
    }

    public String getIsEngineer()
    {
        return isEngineer;
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
    public void setResvAttr1(String resvAttr1)
    {
        this.resvAttr1 = resvAttr1;
    }

    public String getResvAttr1()
    {
        return resvAttr1;
    }
    public void setResvAttr2(String resvAttr2)
    {
        this.resvAttr2 = resvAttr2;
    }

    public String getResvAttr2()
    {
        return resvAttr2;
    }
    public void setResvAttr3(String resvAttr3)
    {
        this.resvAttr3 = resvAttr3;
    }

    public String getResvAttr3()
    {
        return resvAttr3;
    }
    public void setResvAttr4(String resvAttr4)
    {
        this.resvAttr4 = resvAttr4;
    }

    public String getResvAttr4()
    {
        return resvAttr4;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("travelNo", getTravelNo())
            .append("travelTpye", getTravelTpye())
            .append("travelReason", getTravelReason())
            .append("compId", getCompId())
            .append("empId", getEmpId())
            .append("empNo", getEmpNo())
            .append("empName", getEmpName())
            .append("deptName", getDeptName())
            .append("status", getStatus())
            .append("agentId", getAgentId())
            .append("agentNo", getAgentNo())
            .append("agentName", getAgentName())
            .append("costCenter", getCostCenter())
            .append("contract", getContract())
            .append("startDate", getStartDate())
            .append("startTime", getStartTime())
            .append("endDate", getEndDate())
            .append("endTime", getEndTime())
            .append("realStartDate", getRealStartDate())
            .append("realStartTime", getRealStartTime())
            .append("realEndDate", getRealEndDate())
            .append("realEndTime", getRealEndTime())
            .append("reasonDesc", getReasonDesc())
            .append("billNo", getBillNo())
            .append("isEngineer", getIsEngineer())
            .append("creator", getCreator())
            .append("creatorId", getCreatorId())
            .append("createDate", getCreateDate())
            .append("resvAttr1", getResvAttr1())
            .append("resvAttr2", getResvAttr2())
            .append("resvAttr3", getResvAttr3())
            .append("resvAttr4", getResvAttr4())
            .toString();
    }
}
