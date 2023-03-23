package com.jlkj.safety.si.dto.safesiteenvironmentalgovernance;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @description: dto 修改
 * @author: yzl
 * @time: 2022年6月8日
 */
@Data
public class UpdateSafeSiteEnvironmentalGovernanceDTO {

	@NotNull(message = "主键标识;UUID为必须项")
	@JsonProperty("id")
	private String id ;

	@NotNull(message = "单位为必须项")
	@JsonProperty("department_id")
	private String departmentId ;

	@NotNull(message = "工作事项为必须项")
	@JsonProperty("work_items")
	private String workItems ;

	@NotNull(message = "完成情况为必须项")
	@JsonProperty("complete_state")
	private Integer completeState ;

	@NotNull(message = "工作目标为必须项")
	@JsonProperty("work_target")
	private String workTarget ;

	@NotNull(message = "整改前照片为必须项")
	@JsonProperty("rectification_before_url")
	private String rectificationBeforeUrl ;

	@NotNull(message = "整改后照片为必须项")
	@JsonProperty("rectification_after_url")
	private String rectificationAfterUrl ;

	@NotNull(message = "修改人id为必须项")
	@JsonProperty("modify_user_id")
	private String modifyUserId ;

	@NotNull(message = "修改人为必须项")
	@JsonProperty("modify_user_name")
	private String modifyUserName ;

	@NotNull(message = "奖励比例为必须项")
	private List<ProportionGovernanceDTO> proportions;

}

