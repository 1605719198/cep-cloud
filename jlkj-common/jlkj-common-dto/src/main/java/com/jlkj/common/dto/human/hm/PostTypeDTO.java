package com.jlkj.common.dto.human.hm;

import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

/**
 * <p>
 * 岗位类别数据
 * </p>
 *
 * @author xin
 * @since 2022-05-19
 */
@TableName("t_humanresource_post_type")
public class PostTypeDTO {

    /**
     * 主键
     */
    private String id;

    /**
     * 岗位类别代号
     */
    private String postTypeCode;

    /**
     * 岗位类别名称
     */
    private String postTypeName;

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
     * 所属岗位序列
     */
    private String postSequenceId;

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
    public String getPostTypeCode() {
        return postTypeCode;
    }

    public void setPostTypeCode(String postTypeCode) {
        this.postTypeCode = postTypeCode;
    }
    public String getPostTypeName() {
        return postTypeName;
    }

    public void setPostTypeName(String postTypeName) {
        this.postTypeName = postTypeName;
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
    public String getPostSequenceId() {
        return postSequenceId;
    }

    public void setPostSequenceId(String postSequenceId) {
        this.postSequenceId = postSequenceId;
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
        return "HumanresourcePostType{" +
            "id=" + id +
            ", postTypeCode=" + postTypeCode +
            ", postTypeName=" + postTypeName +
            ", status=" + status +
            ", creator=" + creator +
            ", creatorId=" + creatorId +
            ", createDate=" + createDate +
            ", usedTimes=" + usedTimes +
            ", postSequenceId=" + postSequenceId +
            ", createTime=" + createTime +
            ", updateTime=" + updateTime +
            ", createUser=" + createUser +
            ", updateUser=" + updateUser +
        "}";
    }
}
