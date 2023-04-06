package com.jlkj.material.mr.dto.materialscodesmall;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author yzl
 * @create 2023-03-22 09:27:37.930375
 */
@Data
public class PageMaterialsCodeSmallDTO {


	@JsonProperty("materialsCode")
	private String materialsCode;

	@JsonProperty("materialsName")
	private String materialsName;

	@JsonProperty("vendorName")
	private String vendorName;

	@JsonProperty("sendVendorName")
	private String sendVendorName;


	@NotNull(message = "current为必须项")
	@Min(value = 1, message = "current不能小于1")
	private Long current;

	@NotNull(message = "size为必须项")
	@Min(value = 1, message = "size不能小于1")
	private Long size;

	@NotNull(message = "order为必须项")
	@NotEmpty(message = "order不能为空")
	private String order;

	@NotNull(message = "orderby为必须项")
	private String orderby;
}

