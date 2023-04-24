package com.jlkj.human.hd.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.ArrayList;
import java.util.Date;

/**
 * 人员卡钟对象 human_hd_person_colock
 *
 * @author 266861
 * @date 2023-03-29
 */
public class PersonColock extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 类别**/
    private String type;

    /** 公司别 */
    private String compId;

    /** 工号 */
    @Excel(name = "工号/机构ID号")
    private String empId;

    /** 组织机构记录ID*/
    private String orgColockId;

    /** 是否刷卡 */
    @Excel(name = "是否刷卡")
    private String checkcard;

    /** 一级单位名称 */
    private String firstDeptName;

    /** 一级单位ID */
    private String firstDeptId;

    /** 生效日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "生效日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date effectDate;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

    /** 输入人 */
    @Excel(name = "输入人")
    private String creator;

    /** 输入人ID */
    private String creatorId;

    /** 输入日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "输入日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createDate;

    /** 卡钟编号数组 */
    private ArrayList<String> colockList;

    public void setId(String id)
    {
        this.id = id;
    }

    public String getId()
    {
        return id;
    }
    public void setCompId(String compId)
    {
        this.compId = compId;
    }

    public String getCompId()
    {
        return compId;
    }
    public void setOrgColockId(String orgColockId)
    {
        this.orgColockId = orgColockId;
    }

    public String getOrgColockId()
    {
        return orgColockId;
    }
    public void setType(String type)
    {
        this.type = type;
    }

    public String getType()
    {
        return type;
    }
    public void setFirstDeptName(String firstDeptName)
    {
        this.firstDeptName = firstDeptName;
    }

    public String getFirstDeptName()
    {
        return firstDeptName;
    }
    public void setFirstDeptId(String firstDeptId)
    {
        this.firstDeptId = firstDeptId;
    }

    public String getFirstDeptId()
    {
        return firstDeptId;
    }
    public void setEmpId(String empId)
    {
        this.empId = empId;
    }

    public String getEmpId()
    {
        return empId;
    }
    public void setCheckcard(String checkcard)
    {
        this.checkcard = checkcard;
    }

    public String getCheckcard()
    {
        return checkcard;
    }
    public void setEffectDate(Date effectDate)
    {
        this.effectDate = effectDate;
    }

    public Date getEffectDate()
    {
        return effectDate;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
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
    public void setColockList(ArrayList<String> colockList)
    {
        this.colockList = colockList;
    }

    public ArrayList<String> getColockList()
    {
        return colockList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("type",getType())
                .append("compId", getCompId())
                .append("empId", getEmpId())
                .append("orgColockId",getOrgColockId())
                .append("firstDeptName",getFirstDeptName())
                .append("firstDeptId",getFirstDeptId())
                .append("checkcard", getCheckcard())
                .append("effectDate", getEffectDate())
                .append("status", getStatus())
                .append("creator", getCreator())
                .append("creatorId", getCreatorId())
                .append("createDate", getCreateDate())
                .toString();
    }
}
