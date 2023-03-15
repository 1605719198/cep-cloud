package com.jlkj.common.dto.human.hm;

import java.util.Date;

/**
 * @author Xin
 */
public class OrganizationDTO {

    /**
     * 主键
     */
    private String id;

    /**
     * 机构编码
     */
    private String organizationCode;
    /**
     * 机构名称
     */
    private String organizationName;
    /**
     * 板块ID
     */
    private String boardId;
    /**
     * 上级机构ID
     */
    private String parentId;
    /**
     * 上级机构名称
     */
    private String parentName;
    /**
     * 上级机构全称
     */
    private String parentFullName;
    /**
     * 机构层级ID
     */
    private String organizationTierId;
    /**
     * 机构类别ID
     */
    private String organizationTypeId;
    /**
     * 机构细类ID
     */
    private String organizationCategoryId;
    /**
     * 排序序号
     */
    private Integer serialNo;
    /**
     * 成本中心
     */
    private String costCenterId;
    /**
     * 批准文号
     */
    private String approvalNo;
    /**
     * 传真
     */
    private String fax;
    /**
     * 联系电话
     */
    private String telephone;
    /**
     * 变更原因
     */
    private String changeReason;
    /**
     * 生效日期
     */
    private String effectDate;
    /**
     * 版本号
     */
    private Integer versionNo;
    /**
     * 是否公司
     */
    private String ifCompany;
    /**
     * 公司别
     */
    private String companyId;
    /**
     * 是否显示
     */
    private String ifDisplay;
    /**
     * 输入人
     */
    private String creator;
    /**
     * 输入人ID
     */
    private String creatorId;
    /**
     * 输入日期
     */
    private String createDate;
    /**
     * 状态
     */
    private String status;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 创建人
     */
    private String createUser;
    /**
     * 更新人
     */
    private String updateUser;
    /**
     * 创建人姓名
     */
    private String createUserName;
    /**
     * 修改人姓名
     */
    private String updateUserName;

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getUpdateUserName() {
        return updateUserName;
    }

    public void setUpdateUserName(String updateUserName) {
        this.updateUserName = updateUserName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getOrganizationCode() {
        return organizationCode;
    }

    public void setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode;
    }
    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }
    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }
    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }
    public String getParentFullName() {
        return parentFullName;
    }

    public void setParentFullName(String parentFullName) {
        this.parentFullName = parentFullName;
    }
    public String getOrganizationTierId() {
        return organizationTierId;
    }

    public void setOrganizationTierId(String organizationTierId) {
        this.organizationTierId = organizationTierId;
    }
    public String getOrganizationTypeId() {
        return organizationTypeId;
    }

    public void setOrganizationTypeId(String organizationTypeId) {
        this.organizationTypeId = organizationTypeId;
    }
    public String getOrganizationCategoryId() {
        return organizationCategoryId;
    }

    public void setOrganizationCategoryId(String organizationCategoryId) {
        this.organizationCategoryId = organizationCategoryId;
    }
    public Integer getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(Integer serialNo) {
        this.serialNo = serialNo;
    }
    public String getCostCenterId() {
        return costCenterId;
    }

    public void setCostCenterId(String costCenterId) {
        this.costCenterId = costCenterId;
    }
    public String getApprovalNo() {
        return approvalNo;
    }

    public void setApprovalNo(String approvalNo) {
        this.approvalNo = approvalNo;
    }
    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }
    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    public String getChangeReason() {
        return changeReason;
    }

    public void setChangeReason(String changeReason) {
        this.changeReason = changeReason;
    }
    public String getEffectDate() {
        return effectDate;
    }

    public void setEffectDate(String effectDate) {
        this.effectDate = effectDate;
    }
    public Integer getVersionNo() {
        return versionNo;
    }

    public void setVersionNo(Integer versionNo) {
        this.versionNo = versionNo;
    }
    public String getIfCompany() {
        return ifCompany;
    }

    public void setIfCompany(String ifCompany) {
        this.ifCompany = ifCompany;
    }
    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }
    public String getIfDisplay() {
        return ifDisplay;
    }

    public void setIfDisplay(String ifDisplay) {
        this.ifDisplay = ifDisplay;
    }
    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }
    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }
    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }
    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    @Override
    public String toString() {
        return "HumanresourceOrganizationDTO{" +
                "id='" + id + '\'' +
                ", organizationCode='" + organizationCode + '\'' +
                ", organizationName='" + organizationName + '\'' +
                ", boardId='" + boardId + '\'' +
                ", parentId='" + parentId + '\'' +
                ", parentName='" + parentName + '\'' +
                ", parentFullName='" + parentFullName + '\'' +
                ", organizationTierId='" + organizationTierId + '\'' +
                ", organizationTypeId='" + organizationTypeId + '\'' +
                ", organizationCategoryId='" + organizationCategoryId + '\'' +
                ", serialNo=" + serialNo +
                ", costCenterId='" + costCenterId + '\'' +
                ", approvalNo='" + approvalNo + '\'' +
                ", fax='" + fax + '\'' +
                ", telephone='" + telephone + '\'' +
                ", changeReason='" + changeReason + '\'' +
                ", effectDate='" + effectDate + '\'' +
                ", versionNo=" + versionNo +
                ", ifCompany='" + ifCompany + '\'' +
                ", companyId='" + companyId + '\'' +
                ", ifDisplay='" + ifDisplay + '\'' +
                ", creator='" + creator + '\'' +
                ", creatorId='" + creatorId + '\'' +
                ", createDate='" + createDate + '\'' +
                ", status='" + status + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", createUser='" + createUser + '\'' +
                ", updateUser='" + updateUser + '\'' +
                ", createUserName='" + createUserName + '\'' +
                ", updateUserName='" + updateUserName + '\'' +
                '}';
    }
}
