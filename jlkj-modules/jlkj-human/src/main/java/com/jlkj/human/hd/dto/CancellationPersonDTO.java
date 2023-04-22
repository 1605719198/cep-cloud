package com.jlkj.human.hd.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.human.hd.domain.CancellationPerson;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 人事注销记录表
 * @author HuangBing
 * @TableName human_hd_cancellation_person
 */
@TableName(value ="human_hd_cancellation_person")
@Data
public class CancellationPersonDTO implements Serializable {
    /**
     * 主键
     */
    @TableId
    private String id;

    /**
     * 公司别
     */
    private String companyId;

    /**
     * 工号
     */
    private String empNo;

    /**
     * 工号ID
     */
    private String empId;

    /**
     * 姓名
     */
    private String empName;

    /**
     * 岗位名称
     */
    private String postName;

    /**
     * 岗位ID
     */
    private String postId;

    /**
     * 组织机构名称
     */
    private String orgName;

    /**
     * 组织机构ID
     */
    private String orgId;

    /**
     * 卡钟ID
     */
    private String clockWorkId;

    /**
     * 注销开始时间
     */
    private Date checkStartDate;

    /**
     * 注销结束时间
     */
    private Date checkEndDate;

    /**
     * 注销原因
     */
    private String reason;

    /**
     * 类别
     */
    private String type;

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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;


    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;

    /**
     * 展示时间
     */
    private List<String> workOvertimeDate;

    /**
     * 部门
     */
    private String departmentId;

    /**
     * 批量注销人员信息
     */
    private List<CancellationPerson> userInfo;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}