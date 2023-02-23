package com.jlkj.equip.me.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 设备缺陷统计
 * </p>
 *
 * @author xin
 * @since 2022-05-13
 */
@TableName("equip_me_defect")
public class EquipmentDefect implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 缺陷项目
     */
    private String defectTitle;

    /**
     * 所属专业
     */
    private String defectSpecialtyNo;

    /**
     * 所属专业名称
     */
    private String defectSpecialtyName;

    /**
     * 重要程度
     */
    private String importanceNo;

    /**
     * 重要程度名称
     */
    private String importanceName;

    /**
     * 处理条件
     */
    private String processConditionNo;

    /**
     * 处理条件名称
     */
    private String processConditionName;

    /**
     * 完成情况
     */
    private String completionRemark;

    /**
     * 备注
     */
    private String remark;

    /**
     * 点检责任人
     */
    private String pointCheckPerson;

    /**
     * 点检责任人姓名
     */
    private String pointCheckPersonName;

    /**
     * 作业区责任人
     */
    private String workingZonePerson;

    /**
     * 作业区责任人姓名
     */
    private String workingZonePersonName;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 修改人
     */
    private String updateUser;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    /**
     * 创建人姓名
     */
    private String createUserName;

    /**
     * 修改人姓名
     */
    private String updateUserName;
    /**
     * 逻辑删除标识
     */
    private Integer delFlag;

    /**
     * 更新日期
     */
    private String defectUpdateDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getDefectTitle() {
        return defectTitle;
    }

    public void setDefectTitle(String defectTitle) {
        this.defectTitle = defectTitle;
    }
    public String getDefectSpecialtyNo() {
        return defectSpecialtyNo;
    }

    public void setDefectSpecialtyNo(String defectSpecialtyNo) {
        this.defectSpecialtyNo = defectSpecialtyNo;
    }
    public String getDefectSpecialtyName() {
        return defectSpecialtyName;
    }

    public void setDefectSpecialtyName(String defectSpecialtyName) {
        this.defectSpecialtyName = defectSpecialtyName;
    }
    public String getImportanceNo() {
        return importanceNo;
    }

    public void setImportanceNo(String importanceNo) {
        this.importanceNo = importanceNo;
    }
    public String getImportanceName() {
        return importanceName;
    }

    public void setImportanceName(String importanceName) {
        this.importanceName = importanceName;
    }
    public String getProcessConditionNo() {
        return processConditionNo;
    }

    public void setProcessConditionNo(String processConditionNo) {
        this.processConditionNo = processConditionNo;
    }
    public String getProcessConditionName() {
        return processConditionName;
    }

    public void setProcessConditionName(String processConditionName) {
        this.processConditionName = processConditionName;
    }
    public String getCompletionRemark() {
        return completionRemark;
    }

    public void setCompletionRemark(String completionRemark) {
        this.completionRemark = completionRemark;
    }
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    public String getPointCheckPerson() {
        return pointCheckPerson;
    }

    public void setPointCheckPerson(String pointCheckPerson) {
        this.pointCheckPerson = pointCheckPerson;
    }
    public String getPointCheckPersonName() {
        return pointCheckPersonName;
    }

    public void setPointCheckPersonName(String pointCheckPersonName) {
        this.pointCheckPersonName = pointCheckPersonName;
    }
    public String getWorkingZonePerson() {
        return workingZonePerson;
    }

    public void setWorkingZonePerson(String workingZonePerson) {
        this.workingZonePerson = workingZonePerson;
    }
    public String getWorkingZonePersonName() {
        return workingZonePersonName;
    }

    public void setWorkingZonePersonName(String workingZonePersonName) {
        this.workingZonePersonName = workingZonePersonName;
    }
    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
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

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public String getDefectUpdateDate() {
        return defectUpdateDate;
    }

    public void setDefectUpdateDate(String defectUpdateDate) {
        this.defectUpdateDate = defectUpdateDate;
    }

    @Override
    public String toString() {
        return "EquipmentDefect{" +
                "id='" + id + '\'' +
                ", defectTitle='" + defectTitle + '\'' +
                ", defectSpecialtyNo='" + defectSpecialtyNo + '\'' +
                ", defectSpecialtyName='" + defectSpecialtyName + '\'' +
                ", importanceNo='" + importanceNo + '\'' +
                ", importanceName='" + importanceName + '\'' +
                ", processConditionNo='" + processConditionNo + '\'' +
                ", processConditionName='" + processConditionName + '\'' +
                ", completionRemark='" + completionRemark + '\'' +
                ", remark='" + remark + '\'' +
                ", pointCheckPerson='" + pointCheckPerson + '\'' +
                ", pointCheckPersonName='" + pointCheckPersonName + '\'' +
                ", workingZonePerson='" + workingZonePerson + '\'' +
                ", workingZonePersonName='" + workingZonePersonName + '\'' +
                ", createUser='" + createUser + '\'' +
                ", createTime=" + createTime +
                ", updateUser='" + updateUser + '\'' +
                ", updateTime=" + updateTime +
                ", createUserName='" + createUserName + '\'' +
                ", updateUserName='" + updateUserName + '\'' +
                ", delFlag=" + delFlag +
                ", defectUpdateDate='" + defectUpdateDate + '\'' +
                '}';
    }
}
