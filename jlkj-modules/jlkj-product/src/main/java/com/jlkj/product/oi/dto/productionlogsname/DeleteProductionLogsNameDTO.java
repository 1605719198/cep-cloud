package com.jlkj.product.oi.dto.productionlogsname;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author yzl
 * @create 2022-09-28 10:27:05.808763
 */
@Data
public class DeleteProductionLogsNameDTO {

	@NotNull(message = "主键标识为必须项")
	@NotEmpty(message = "主键标识不能为空")
	private String id;
}

