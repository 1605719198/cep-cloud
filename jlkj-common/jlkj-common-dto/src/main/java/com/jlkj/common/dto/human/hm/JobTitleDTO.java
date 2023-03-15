package com.jlkj.common.dto.human.hm;

import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

/**
 * <p>
 * 职位名称数据
 * </p>
 *
 * @author xin
 * @since 2022-05-20
 */
@TableName("t_humanresource_job_title")
public class JobTitleDTO {

    /**
     * 主键
     */
    private String id;

    /**
     * 职位名称代号
     */
    private String jobTitleCode;

    /**
     * 职位名称
     */
    private String jobTitleName;

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
    public String getJobTitleCode() {
        return jobTitleCode;
    }

    public void setJobTitleCode(String jobTitleCode) {
        this.jobTitleCode = jobTitleCode;
    }
    public String getJobTitleName() {
        return jobTitleName;
    }

    public void setJobTitleName(String jobTitleName) {
        this.jobTitleName = jobTitleName;
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
        return "HumanresourceJobTitle{" +
            "id=" + id +
            ", jobTitleCode=" + jobTitleCode +
            ", jobTitleName=" + jobTitleName +
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
