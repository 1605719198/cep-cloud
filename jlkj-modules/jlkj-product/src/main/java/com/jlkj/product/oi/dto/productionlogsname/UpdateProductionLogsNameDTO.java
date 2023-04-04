package com.jlkj.product.oi.dto.productionlogsname;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author yzl
 * @create 2022-09-28 10:27:05.808763
 */
@Data
public class UpdateProductionLogsNameDTO {

	@NotNull(message = "主键标识;UUID为必须项")
	@JsonProperty("id")
	private String id ;

	@NotNull(message = "日志项名称为必须项")
	@JsonProperty("logs_name")
	private String logsName ;

	@NotNull(message = "修改人id为必须项")
	@JsonProperty("modify_user_id")
	private String modifyUserId ;

	@NotNull(message = "修改人为必须项")
	@JsonProperty("modify_user_name")
	private String modifyUserName ;

}

