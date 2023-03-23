package com.jlkj.safety.si.entity;
		
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.validation.constraints.Min;

/**
 * @author : yzl
 * @date : 2022-6-6
 * @desc : 安全重点工作-奖励比例
 */
@TableName(value ="safe_si_important_work_proportion")
@Data
public class SafeImportantWorkProportion implements Serializable{
    /** 主键标识;UUID */
    @TableId
    private String id ;

    /** 专业 */
	private String major ;

    /** 安全重点工作id */
    @JsonProperty("job_id")
	private String jobId ;

    /** 负责人id */
    @JsonProperty("responsible_user_id")
    private String responsibleUserId ;

    /** 负责人姓名 */
    @JsonProperty("responsible_user_name")
    private String responsibleUserName ;

    /** 工号 */
    @JsonProperty("job_number")
	private String jobNumber ;

    /** 奖励比例 */
    @JsonProperty("reward_proportion")
	private BigDecimal rewardProportion ;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}

