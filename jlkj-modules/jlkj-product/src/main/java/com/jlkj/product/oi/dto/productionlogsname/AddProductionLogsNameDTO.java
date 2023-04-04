package com.jlkj.product.oi.dto.productionlogsname;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author yzl
 * @create 2022-09-28 10:27:05.808763
 */
@Data
public class AddProductionLogsNameDTO {

	@NotNull(message = "日志项名称为必须项")
	@JsonProperty("logs_name")
	private String logsName ;

	@NotNull(message = "创建人为必须项")
	@JsonProperty("create_user_id")
	private String createUserId ;

	@NotNull(message = "创建人为必须项")
	@JsonProperty("create_user_name")
	private String createUserName ;

}

