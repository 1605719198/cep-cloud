package com.jlkj.human.hm.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 人员排班数据
 * </p>
 *
 * @author Xin
 * @since 2022-04-29
 */
@TableName("human_hm_personnel_scheduling")
public class HumanresourcePersonnelScheduling implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 工号
     */
    private String jobNumber;

    /**
     * 姓名
     */
    private String fullName;

    /**
     * 所属单位
     */
    private String department;

    /**
     * 岗位
     */
    private String post;

    /**
     * 排班日期
     */
    private String schedulingDate;

    /**
     * 班次
     */
    private String shift;

    /**
     * 班别
     */
    private String classType;
    /**
     * 班别代号
     */
    private String classTypeNo;

    /**
     * 是否当天连班
     */
    private String ifContinuousShift;

    /**
     * 应出勤日期（上班）
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date dueAttendanceTimeWork;

    /**
     * 应出勤日期（下班）
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date dueAttendanceTimeOffduty;

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
     * 修改人编号
     */
    private String updateUserId;

    /**
     * 新增人编号
     */
    private String createUserId;

    /**
     * 修改人姓名
     */
    private String updateUserName;

    /**
     * 新增人姓名
     */
    private String createUserName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }
    public String getSchedulingDate() {
        return schedulingDate;
    }

    public void setSchedulingDate(String schedulingDate) {
        this.schedulingDate = schedulingDate;
    }
    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }
    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }
    public String getIfContinuousShift() {
        return ifContinuousShift;
    }

    public void setIfContinuousShift(String ifContinuousShift) {
        this.ifContinuousShift = ifContinuousShift;
    }
    public Date getDueAttendanceTimeWork() {
        return dueAttendanceTimeWork;
    }

    public void setDueAttendanceTimeWork(Date dueAttendanceTimeWork) {
        this.dueAttendanceTimeWork = dueAttendanceTimeWork;
    }
    public Date getDueAttendanceTimeOffduty() {
        return dueAttendanceTimeOffduty;
    }

    public void setDueAttendanceTimeOffduty(Date dueAttendanceTimeOffduty) {
        this.dueAttendanceTimeOffduty = dueAttendanceTimeOffduty;
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
    public String getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }
    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }
    public String getUpdateUserName() {
        return updateUserName;
    }

    public void setUpdateUserName(String updateUserName) {
        this.updateUserName = updateUserName;
    }
    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getClassTypeNo() {
        return classTypeNo;
    }

    public void setClassTypeNo(String classTypeNo) {
        this.classTypeNo = classTypeNo;
    }

    @Override
    public String toString() {
        return "HumanresourcePersonnelScheduling{" +
                "id='" + id + '\'' +
                ", jobNumber='" + jobNumber + '\'' +
                ", fullName='" + fullName + '\'' +
                ", department='" + department + '\'' +
                ", post='" + post + '\'' +
                ", schedulingDate='" + schedulingDate + '\'' +
                ", shift='" + shift + '\'' +
                ", classType='" + classType + '\'' +
                ", classTypeNo='" + classTypeNo + '\'' +
                ", ifContinuousShift='" + ifContinuousShift + '\'' +
                ", dueAttendanceTimeWork=" + dueAttendanceTimeWork +
                ", dueAttendanceTimeOffduty=" + dueAttendanceTimeOffduty +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", updateUserId='" + updateUserId + '\'' +
                ", createUserId='" + createUserId + '\'' +
                ", updateUserName='" + updateUserName + '\'' +
                ", createUserName='" + createUserName + '\'' +
                '}';
    }
}
