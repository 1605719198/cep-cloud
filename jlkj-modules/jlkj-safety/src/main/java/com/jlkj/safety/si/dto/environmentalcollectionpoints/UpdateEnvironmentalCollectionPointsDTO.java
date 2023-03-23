package com.jlkj.safety.si.dto.environmentalcollectionpoints;
		
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author yzl
 * @create 2022-12-27 09:24:47.847448
 */
@Data
public class UpdateEnvironmentalCollectionPointsDTO {

	@NotNull(message = "主键标识;UUID为必须项")
	@JsonProperty("id")
	private String id ;

	@NotNull(message = "名称为必须项")
	@JsonProperty("device_name")
	private String deviceName ;

	@NotNull(message = "污染物名称为必须项")
	@JsonProperty("pollutant_name")
	private String pollutantName ;

	@NotNull(message = "采集点位为必须项")
	@JsonProperty("collection_point")
	private String collectionPoint ;

	@NotNull(message = "排放限值为必须项")
	@JsonProperty("emission_limit")
	private String emissionLimit ;

	@NotNull(message = "序号为必须项")
	@JsonProperty("sort")
	private Integer sort ;

	@NotNull(message = "单位为必须项")
	@JsonProperty("unit")
	private String unit ;

}

