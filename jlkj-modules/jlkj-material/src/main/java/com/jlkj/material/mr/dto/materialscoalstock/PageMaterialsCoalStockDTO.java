package com.jlkj.material.mr.dto.materialscoalstock;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author yzl
 * @Description 物料煤场库存 查询
 * @create 2022年6月10日 10:12:26
 */
@Data
public class PageMaterialsCoalStockDTO {

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

	private String categoryId;

	private String materialsId;

	private String storageSpacesId;

	private String createStartTime;

	private String createEndTime;

	private String clearStartTime;

	private String clearEndTime;

	private String materialsName;
}

