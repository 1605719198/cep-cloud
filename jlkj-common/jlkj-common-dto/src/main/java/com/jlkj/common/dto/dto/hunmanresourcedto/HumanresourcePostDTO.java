package com.jlkj.common.dto.dto.hunmanresourcedto;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 岗位数据
 * </p>
 *
 * @author Xin
 * @since 2022-04-29
 */
@TableName("t_humanresource_post")
public class HumanresourcePostDTO {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 岗位编码
     */
    private String postCode;

    /**
     * 岗位名称
     */
    private String postName;

    /**
     * 所属公司/机构
     */
    private String organizationId;

    /**
     * 专长类别id
     */
    private String specialityTypeId;

    /**
     * 专长id
     */
    private String specialityId;

    /**
     * 岗位序列id
     */
    private String postSequenceId;

    /**
     * 岗位类别id
     */
    private String postTypeId;

    /**
     * 职位名称id
     */
    private String jobTitleId;

    /**
     * 流水号
     */
    private String serialNumber;

    /**
     * 倒班方式
     */
    private String shiftWorkId;

    /**
     * 用工性质
     */
    private String employmentNatureId;

    /**
     * 工作地点
     */
    private String workAddressId;

    /**
     * 公司（具体工作地点）
     */
    private String workAddress;

    /**
     * 驻外地点（具体工作地点）
     */
    private String overseasLocations;

    /**
     * 是否配置专人
     */
    private String ifSpeciallyAssignedPerson;

    /**
     * 定员
     */
    private Integer planCapacity;

    /**
     * 现员
     */
    private Integer nowCapacity;

    /**
     * 排序序号
     */
    private Integer serialNo;

    /**
     * 定员依据
     */
    private String capacityBasis;

    /**
     * 成本中心
     */
    private String costCenterId;

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
     * 上级岗位ID
     */
    private String parentId;

    /**
     * 上级岗位名称
     */
    private String parentName;

    /**
     * 职级ID
     */
    private String positionsClassId;

    /**
     * 所属公司/机构名称
     */
    private String parentHumanresourceOrganizationName;

    /**
     * 所属公司/机构全称
     */
    private String parentHumanresourceOrganizationFullName;

    /**
     * 职位费用类别
     */
    private String positionExpenseCategory;

    /**
     * 成本中心名称
     */
    private String costCenterName;

    /**
     * 变更原因
     */
    private String changeReason;

    /**
     * 生效日期
     */
    private String effectDate;

    /**
     * 版本
     */
    private String versionNo;

    /**
     * 是否新增
     */
    private String ifNew;

    /**
     * 公司别
     */
    private String companyId;

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
     * 修改人
     */
    private String updateUser;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }
    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }
    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }
    public String getSpecialityTypeId() {
        return specialityTypeId;
    }

    public void setSpecialityTypeId(String specialityTypeId) {
        this.specialityTypeId = specialityTypeId;
    }
    public String getSpecialityId() {
        return specialityId;
    }

    public void setSpecialityId(String specialityId) {
        this.specialityId = specialityId;
    }
    public String getPostSequenceId() {
        return postSequenceId;
    }

    public void setPostSequenceId(String postSequenceId) {
        this.postSequenceId = postSequenceId;
    }
    public String getPostTypeId() {
        return postTypeId;
    }

    public void setPostTypeId(String postTypeId) {
        this.postTypeId = postTypeId;
    }
    public String getJobTitleId() {
        return jobTitleId;
    }

    public void setJobTitleId(String jobTitleId) {
        this.jobTitleId = jobTitleId;
    }
    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }
    public String getShiftWorkId() {
        return shiftWorkId;
    }

    public void setShiftWorkId(String shiftWorkId) {
        this.shiftWorkId = shiftWorkId;
    }
    public String getEmploymentNatureId() {
        return employmentNatureId;
    }

    public void setEmploymentNatureId(String employmentNatureId) {
        this.employmentNatureId = employmentNatureId;
    }
    public String getWorkAddressId() {
        return workAddressId;
    }

    public void setWorkAddressId(String workAddressId) {
        this.workAddressId = workAddressId;
    }
    public String getWorkAddress() {
        return workAddress;
    }

    public void setWorkAddress(String workAddress) {
        this.workAddress = workAddress;
    }
    public String getOverseasLocations() {
        return overseasLocations;
    }

    public void setOverseasLocations(String overseasLocations) {
        this.overseasLocations = overseasLocations;
    }
    public String getIfSpeciallyAssignedPerson() {
        return ifSpeciallyAssignedPerson;
    }

    public void setIfSpeciallyAssignedPerson(String ifSpeciallyAssignedPerson) {
        this.ifSpeciallyAssignedPerson = ifSpeciallyAssignedPerson;
    }
    public Integer getPlanCapacity() {
        return planCapacity;
    }

    public void setPlanCapacity(Integer planCapacity) {
        this.planCapacity = planCapacity;
    }
    public Integer getNowCapacity() {
        return nowCapacity;
    }

    public void setNowCapacity(Integer nowCapacity) {
        this.nowCapacity = nowCapacity;
    }
    public Integer getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(Integer serialNo) {
        this.serialNo = serialNo;
    }
    public String getCapacityBasis() {
        return capacityBasis;
    }

    public void setCapacityBasis(String capacityBasis) {
        this.capacityBasis = capacityBasis;
    }
    public String getCostCenterId() {
        return costCenterId;
    }

    public void setCostCenterId(String costCenterId) {
        this.costCenterId = costCenterId;
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
    public String getPositionsClassId() {
        return positionsClassId;
    }

    public void setPositionsClassId(String positionsClassId) {
        this.positionsClassId = positionsClassId;
    }
    public String getParentHumanresourceOrganizationName() {
        return parentHumanresourceOrganizationName;
    }

    public void setParentHumanresourceOrganizationName(String parentHumanresourceOrganizationName) {
        this.parentHumanresourceOrganizationName = parentHumanresourceOrganizationName;
    }
    public String getParentHumanresourceOrganizationFullName() {
        return parentHumanresourceOrganizationFullName;
    }

    public void setParentHumanresourceOrganizationFullName(String parentHumanresourceOrganizationFullName) {
        this.parentHumanresourceOrganizationFullName = parentHumanresourceOrganizationFullName;
    }
    public String getPositionExpenseCategory() {
        return positionExpenseCategory;
    }

    public void setPositionExpenseCategory(String positionExpenseCategory) {
        this.positionExpenseCategory = positionExpenseCategory;
    }
    public String getCostCenterName() {
        return costCenterName;
    }

    public void setCostCenterName(String costCenterName) {
        this.costCenterName = costCenterName;
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
    public String getVersionNo() {
        return versionNo;
    }

    public void setVersionNo(String versionNo) {
        this.versionNo = versionNo;
    }
    public String getIfNew() {
        return ifNew;
    }

    public void setIfNew(String ifNew) {
        this.ifNew = ifNew;
    }
    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
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
        return "HumanresourcePost{" +
            "id=" + id +
            ", postCode=" + postCode +
            ", postName=" + postName +
            ", organizationId=" + organizationId +
            ", specialityTypeId=" + specialityTypeId +
            ", specialityId=" + specialityId +
            ", postSequenceId=" + postSequenceId +
            ", postTypeId=" + postTypeId +
            ", jobTitleId=" + jobTitleId +
            ", serialNumber=" + serialNumber +
            ", shiftWorkId=" + shiftWorkId +
            ", employmentNatureId=" + employmentNatureId +
            ", workAddressId=" + workAddressId +
            ", workAddress=" + workAddress +
            ", overseasLocations=" + overseasLocations +
            ", ifSpeciallyAssignedPerson=" + ifSpeciallyAssignedPerson +
            ", planCapacity=" + planCapacity +
            ", nowCapacity=" + nowCapacity +
            ", serialNo=" + serialNo +
            ", capacityBasis=" + capacityBasis +
            ", costCenterId=" + costCenterId +
            ", creator=" + creator +
            ", creatorId=" + creatorId +
            ", createDate=" + createDate +
            ", status=" + status +
            ", parentId=" + parentId +
            ", parentName=" + parentName +
            ", positionsClassId=" + positionsClassId +
            ", parentHumanresourceOrganizationName=" + parentHumanresourceOrganizationName +
            ", parentHumanresourceOrganizationFullName=" + parentHumanresourceOrganizationFullName +
            ", positionExpenseCategory=" + positionExpenseCategory +
            ", costCenterName=" + costCenterName +
            ", changeReason=" + changeReason +
            ", effectDate=" + effectDate +
            ", versionNo=" + versionNo +
            ", ifNew=" + ifNew +
            ", companyId=" + companyId +
            ", createTime=" + createTime +
            ", updateTime=" + updateTime +
            ", createUser=" + createUser +
            ", updateUser=" + updateUser +
        "}";
    }
}
