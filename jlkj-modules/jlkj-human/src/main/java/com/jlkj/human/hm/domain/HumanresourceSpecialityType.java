package com.jlkj.human.hm.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 专长类别数据
 * </p>
 *
 * @author xin
 * @since 2022-05-20
 */
@TableName("human_hm_speciality_type")
public class HumanresourceSpecialityType implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 专长类别代号
     */
    private String specialityTypeCode;

    /**
     * 专长类别名称
     */
    private String specialityTypeName;

    /**
     * 状态
     */
    private String status;

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
     * 使用次数
     */
    private String usedTimes;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
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
    public String getSpecialityTypeCode() {
        return specialityTypeCode;
    }

    public void setSpecialityTypeCode(String specialityTypeCode) {
        this.specialityTypeCode = specialityTypeCode;
    }
    public String getSpecialityTypeName() {
        return specialityTypeName;
    }

    public void setSpecialityTypeName(String specialityTypeName) {
        this.specialityTypeName = specialityTypeName;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
    public String getUsedTimes() {
        return usedTimes;
    }

    public void setUsedTimes(String usedTimes) {
        this.usedTimes = usedTimes;
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
        return "HumanresourceSpecialityType{" +
            "id=" + id +
            ", specialityTypeCode=" + specialityTypeCode +
            ", specialityTypeName=" + specialityTypeName +
            ", status=" + status +
            ", creator=" + creator +
            ", creatorId=" + creatorId +
            ", createDate=" + createDate +
            ", usedTimes=" + usedTimes +
            ", createTime=" + createTime +
            ", updateTime=" + updateTime +
            ", createUser=" + createUser +
            ", updateUser=" + updateUser +
        "}";
    }
}
