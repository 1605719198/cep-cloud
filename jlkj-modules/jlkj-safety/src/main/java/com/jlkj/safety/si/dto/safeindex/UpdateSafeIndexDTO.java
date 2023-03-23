package com.jlkj.safety.si.dto.safeindex;
		
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @description: dto 修改
 * @author: yzl
 * @time: 2022年7月19日
 */
@Data
public class UpdateSafeIndexDTO {

	@NotNull(message = "主键标识;UUID为必须项")
	@JsonProperty("id")
	private String id ;

	@NotNull(message = "规则编号为必须项")
	@JsonProperty("rule_number")
	private String ruleNumber ;

	@NotNull(message = "规则描述为必须项")
	@JsonProperty("rule_describe")
	private String ruleDescribe ;

	@NotNull(message = "规则等级为必须项")
	@JsonProperty("rule_level")
	private String ruleLevel ;

	@NotNull(message = "数值上限为必须项")
	@JsonProperty("rule_up")
	private String ruleUp ;

	@NotNull(message = "数值下限为必须项")
	@JsonProperty("rule_down")
	private String ruleDown ;

	@NotNull(message = "单位为必须项")
	@JsonProperty("unit")
	private String unit ;

	@NotNull(message = "状态为必须项")
	@JsonProperty("status")
	private String status ;

	@NotNull(message = "修改人id为必须项")
	@JsonProperty("modify_user_id")
	private String modifyUserId ;

	@NotNull(message = "修改人为必须项")
	@JsonProperty("modify_user_name")
	private String modifyUserName ;

}

