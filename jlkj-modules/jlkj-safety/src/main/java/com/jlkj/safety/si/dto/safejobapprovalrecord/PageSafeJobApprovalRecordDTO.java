package com.jlkj.safety.si.dto.safejobapprovalrecord;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author yzl
 * @create 2023-02-01 13:41:04.066746
 */
@Data
public class PageSafeJobApprovalRecordDTO {

	/**  工号 */
	@NotNull(message = "作业票类型为必填项")
	@NotEmpty(message = "作业票类型不能为空")
	@JsonProperty("job_type")
	private String jobType;

	/** 作业票id */
	@NotNull(message = "作业票id为必填项")
	@NotEmpty(message = "作业票id不能为空")
	@JsonProperty("job_id")
	private String jobId;
}

