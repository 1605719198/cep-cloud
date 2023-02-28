package com.jlkj.common.dto.finance.ip;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;
/**
 * @author wang'mai
 */
@Data
public class FinanceperDTO {
    @Override
    public String toString() {
        return "FinanceperDTO{" +
                "id='" + id + '\'' +
                ", parentid='" + parentid + '\'' +
                ", compId='" + compId + '\'' +
                ", nodeNo='" + nodeNo + '\'' +
                ", nodeName='" + nodeName + '\'' +
                ", createUser='" + createUser + '\'' +
                ", createTime=" + createTime +
                ", updateUser='" + updateUser + '\'' +
                ", updateTime=" + updateTime +
                ", createUserName='" + createUserName + '\'' +
                ", updateUserName='" + updateUserName + '\'' +
                ", delFlag=" + delFlag +
                '}';
    }

    /**
     * 主键
     */
    @TableId
    private String id;

    /**
     * 父键
     */
    private String parentid;

    /**
     * 公司别
     */
    private String compId;

    /**
     * 节点编码
     */
    private String nodeNo;

    /**
     * 节点名称
     */
    private String nodeName;

    /**
     * 建立人员
     */
    private String createUser;

    /**
     * 建立时间
     */
    private Date createTime;

    /**
     * 修改人员
     */
    private String updateUser;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 创建人员姓名
     */
    private String createUserName;

    /**
     * 修改人员姓名
     */
    private String updateUserName;

    /**
     * 逻辑删除标识
     */
    private Integer delFlag;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public String getCompId() {
        return compId;
    }

    public void setCompId(String compId) {
        this.compId = compId;
    }

    public String getNodeNo() {
        return nodeNo;
    }

    public void setNodeNo(String nodeNo) {
        this.nodeNo = nodeNo;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
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

    public FinanceperDTO(String id, String parentid, String compId, String nodeNo, String nodeName, String createUser, Date createTime, String updateUser, Date updateTime, String createUserName, String updateUserName, Integer delFlag) {
        this.id = id;
        this.parentid = parentid;
        this.compId = compId;
        this.nodeNo = nodeNo;
        this.nodeName = nodeName;
        this.createUser = createUser;
        this.createTime = createTime;
        this.updateUser = updateUser;
        this.updateTime = updateTime;
        this.createUserName = createUserName;
        this.updateUserName = updateUserName;
        this.delFlag = delFlag;
    }
}
