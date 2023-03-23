package com.jlkj.safety.si.dto.safesiteenvironmentalgovernance;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
/**
 * @description: dto 删除
 * @author: yzl
 * @time: 2022年6月8日
 */
@Data
public class DeleteSafeSiteEnvironmentalGovernanceDTO {

	@NotNull(message = "主键标识为必须项")
	@NotEmpty(message = "主键标识不能为空")
	private String id;
}

