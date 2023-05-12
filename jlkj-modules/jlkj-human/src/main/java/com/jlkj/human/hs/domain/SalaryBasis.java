package com.jlkj.human.hs.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 薪资资料对象 human_hs_salary_basis
 *
 * @author 266861
 * @date 2023-05-09
 */
public class SalaryBasis extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 资料编码
     */
    @Excel(name = "资料编码")
    private String infoCode;

    /**
     * 资料名称
     */
    @Excel(name = "资料名称")
    private String infoName;

    /**
     * 公司别
     */
    @Excel(name = "公司别")
    private String compId;

    /**
     * 是否显示编码
     */
    @Excel(name = "是否显示编码")
    private String isShowno;

    /**
     * 默认值
     */
    @Excel(name = "默认值")
    private String ischecked;

    /**
     * 显示序号
     */
    @Excel(name = "显示序号")
    private Long orderNum;

    /**
     * 父节点ID
     */
    @Excel(name = "父节点ID")
    private Long parentid;

    /**
     * 祖籍关系
     */
    @Excel(name = "祖籍关系")
    private String parents;

    /**
     * 状态
     */
    @Excel(name = "状态")
    private String status;

    /**
     * 输入人
     */
    @Excel(name = "输入人")
    private String creator;

    /**
     * 输入人工号
     */
    @Excel(name = "输入人工号")
    private String creatorNo;

    /**
     * 输入人ID
     */
    @Excel(name = "输入人ID")
    private String creatorId;

    /**
     * 输入日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "输入日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createDate;

    /**
     * 子节点
     */
    private List<SalaryBasis> children = new ArrayList<SalaryBasis>();

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setInfoCode(String infoCode) {
        this.infoCode = infoCode;
    }

    public String getInfoCode() {
        return infoCode;
    }

    public void setInfoName(String infoName) {
        this.infoName = infoName;
    }

    public String getInfoName() {
        return infoName;
    }

    public void setCompId(String compId) {
        this.compId = compId;
    }

    public String getCompId() {
        return compId;
    }

    public void setIsShowno(String isShowno) {
        this.isShowno = isShowno;
    }

    public String getIsShowno() {
        return isShowno;
    }

    public void setIschecked(String ischecked) {
        this.ischecked = ischecked;
    }

    public String getIschecked() {
        return ischecked;
    }

    public void setOrderNum(Long orderNum) {
        this.orderNum = orderNum;
    }

    public Long getOrderNum() {
        return orderNum;
    }

    public void setParentid(Long parentid) {
        this.parentid = parentid;
    }

    public Long getParentid() {
        return parentid;
    }

    public void setParents(String parents) {
        this.parents = parents;
    }

    public String getParents() {
        return parents;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorNo(String creatorNo) {
        this.creatorNo = creatorNo;
    }

    public String getCreatorNo() {
        return creatorNo;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public List<SalaryBasis> getChildren() {
        return children;
    }

    public void setChildren(List<SalaryBasis> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("infoCode", getInfoCode())
                .append("infoName", getInfoName())
                .append("compId", getCompId())
                .append("isShowno", getIsShowno())
                .append("ischecked", getIschecked())
                .append("orderNum", getOrderNum())
                .append("parentid", getParentid())
                .append("parents", getParents())
                .append("status", getStatus())
                .append("creator", getCreator())
                .append("creatorNo", getCreatorNo())
                .append("creatorId", getCreatorId())
                .append("createDate", getCreateDate())
                .toString();
    }
}
