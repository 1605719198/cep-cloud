package com.jlkj.human.pa.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 员工出勤基本资料维护对象 human_hd_performance_basis
 *
 * @author 266861
 * @date 2023-03-20
 */
public class PerformanceBasis extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 资料编码 */
    @Excel(name = "资料编码")
    private String code;

    /** 资料名称 */
    @Excel(name = "资料名称")
    private String name;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

    /** 公司别 */
    private String compId;

    /** 是否显示编码 */
    private String isShowno;

    /** 默认值 */
    private String isChecked;

    /** 排序序号 */
    private Long orderNum;

    /** 父节点ID */
    private Long parentid;

    /** 祖籍关系 */
    private String parents;

    /** 输入人 */
    @Excel(name = "输入人")
    private String creator;

    /** 输入人id */
    private String creatorId;

    /** 输入日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "输入日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createDate;

    /** 子节点 */
    private List<PerformanceBasis> children = new ArrayList<PerformanceBasis>();

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setCode(String code)
    {
        this.code = code;
    }

    public String getCode()
    {
        return code;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }
    public void setCompId(String compId)
    {
        this.compId = compId;
    }

    public String getCompId()
    {
        return compId;
    }
    public void setIsShowno(String isShowno)
    {
        this.isShowno = isShowno;
    }

    public String getIsShowno()
    {
        return isShowno;
    }
    public void setIsChecked(String isChecked)
    {
        this.isChecked = isChecked;
    }

    public String getIsChecked()
    {
        return isChecked;
    }
    public void setOrderNum(Long orderNum)
    {
        this.orderNum = orderNum;
    }

    public Long getOrderNum()
    {
        return orderNum;
    }
    public void setParentid(Long parentid)
    {
        this.parentid = parentid;
    }

    public Long getParentid()
    {
        return parentid;
    }
    public void setParents(String parents)
    {
        this.parents = parents;
    }

    public String getParents()
    {
        return parents;
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

    public List<PerformanceBasis> getChildren()
    {
        return children;
    }

    public void setChildren(List<PerformanceBasis> children)
    {
        this.children = children;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("code", getCode())
                .append("name", getName())
                .append("status", getStatus())
                .append("compId", getCompId())
                .append("isShowno", getIsShowno())
                .append("isChecked", getIsChecked())
                .append("orderNum", getOrderNum())
                .append("parentid", getParentid())
                .append("parents", getParents())
                .append("creator", getCreator())
                .append("creatorId", getCreatorId())
                .append("createDate", getCreateDate())
                .toString();
    }
}
