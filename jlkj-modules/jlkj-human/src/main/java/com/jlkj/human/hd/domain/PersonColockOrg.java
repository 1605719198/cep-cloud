package com.jlkj.human.hd.domain;

import java.util.ArrayList;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;

/**
 * 人员卡钟组织机构对象 human_hd_org_colock
 *
 * @author 266861
 * @date 2023-03-31
 */
public class PersonColockOrg extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 公司别 */
    @Excel(name = "公司别")
    private String compId;

    /** 组织机构 */
    @Excel(name = "组织机构")
    private String deptId;

    /** 是否刷卡 */
    @Excel(name = "是否刷卡")
    private String checkcard;

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
    @Excel(name = "输入人ID")
    private String creatorId;

    /** 一级单位名称 */
    private String firstDeptName;

    /** 一级单位ID */
    private String firstDeptId;

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
    public void setDeptId(String deptId)
    {
        this.deptId = deptId;
    }

    public String getDeptId()
    {
        return deptId;
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
            .append("compId", getCompId())
            .append("deptId", getDeptId())
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
