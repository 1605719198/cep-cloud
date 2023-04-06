package com.jlkj.material.mr.dto.materialscodesmall;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author yzl
 * @create 2023-03-22 09:27:37.929375
 */
@Data
public class UpdateMaterialsCodeSmallDTO {

	@NotNull(message = "主键标识;UUID为必须项")
	@JsonProperty("id")
	private String id ;

	@NotNull(message = "物料小煤种简称为必须项")
	@JsonProperty("materials_small_name")
	private String materialsSmallName ;

	@NotNull(message = "修改人id为必须项")
	@JsonProperty("modify_user_id")
	private String modifyUserId ;

	@NotNull(message = "修改人为必须项")
	@JsonProperty("modify_user_name")
	private String modifyUserName ;

}

