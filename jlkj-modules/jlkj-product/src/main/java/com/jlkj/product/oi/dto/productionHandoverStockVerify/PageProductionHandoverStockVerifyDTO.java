package com.jlkj.product.oi.dto.productionhandoverstockverify;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author yzl
 * @Description 交班仓存核验 查询
 * @create 2022年7月26日 08:48:35
 */
@Data
public class PageProductionHandoverStockVerifyDTO {

	@JsonProperty("start_time")
	private String startTime;

	@JsonProperty("end_time")
	private String endTime;

	@NotNull(message = "current为必须项")
	@Min(value = 1, message = "current不能小于1")
	private Long current;

	@NotNull(message = "size为必须项")
	@Min(value = 1, message = "size不能小于1")
	private Long size;

}
