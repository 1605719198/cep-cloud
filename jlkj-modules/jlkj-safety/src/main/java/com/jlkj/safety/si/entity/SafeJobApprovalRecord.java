package com.jlkj.safety.si.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author yzl
 * @create 2023-02-01 13:41:04.066746
 */
@TableName(value ="safe_si_job_approval_record")
@Data
public class SafeJobApprovalRecord implements Serializable{
    /** 主键标识;UUID */
    @TableId
    private String id ;

    /** 作业证id */
	private String jobId ;

    /** 作业票类型(1:吊装;2:动火;3:高处;4:有限空间;5:动土;6:抽堵;7:断路;8:临时) */
	private Integer jobType ;

    /** 序号 */
	private Integer sort ;

    /** 审批环节 */
	private String approvalName ;

    /** 审批人ID */
	private String approvalPersonId ;

    /** 审批人姓名 */
	private String approvalPersonName ;

    /** 审批时间 */
	private LocalDateTime approvalTime ;

    /** 是否通过(0:否;1:是) */
	private Integer isPass ;

    /** 意见内容 */
	private String content ;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}

