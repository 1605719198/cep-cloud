package com.jlkj.common.dto.human.hm;

import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

/**
 * <p>
 * 职级数据
 * </p>
 *
 * @author xin
 * @since 2022-05-19
 */
@TableName("t_humanresource_positions_class")
public class PositionsClassDTO {

    /**
     * 主键
     */
    private String id;

    /**
     * 职级代号
     */
    private String positionsClassCode;

    /**
     * 职级名称
     */
    private String positionsClassName;

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
    public String getPositionsClassCode() {
        return positionsClassCode;
    }

    public void setPositionsClassCode(String positionsClassCode) {
        this.positionsClassCode = positionsClassCode;
    }
    public String getPositionsClassName() {
        return positionsClassName;
    }

    public void setPositionsClassName(String positionsClassName) {
        this.positionsClassName = positionsClassName;
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
        return "HumanresourcePositionsClass{" +
            "id=" + id +
            ", positionsClassCode=" + positionsClassCode +
            ", positionsClassName=" + positionsClassName +
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
