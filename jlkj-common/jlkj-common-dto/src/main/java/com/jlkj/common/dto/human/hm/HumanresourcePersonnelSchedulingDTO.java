package com.jlkj.common.dto.human.hm;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * <p>
 * 人员排班数据
 * </p>
 *
 * @author Xin
 * @since 2022-04-29
 */
@TableName("t_humanresource_personnel_scheduling")
public class HumanresourcePersonnelSchedulingDTO {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
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


    @Override
    public String toString() {
        return "HumanresourcePersonnelScheduling{" +
            "id=" + id +
            ", jobNumber=" + jobNumber +
            ", fullName=" + fullName +
            ", department=" + department +
            ", post=" + post +
            ", schedulingDate=" + schedulingDate +
            ", shift=" + shift +
            ", classType=" + classType +
            ", ifContinuousShift=" + ifContinuousShift +
            ", dueAttendanceTimeWork=" + dueAttendanceTimeWork +
            ", dueAttendanceTimeOffduty=" + dueAttendanceTimeOffduty +
        "}";
    }
}
