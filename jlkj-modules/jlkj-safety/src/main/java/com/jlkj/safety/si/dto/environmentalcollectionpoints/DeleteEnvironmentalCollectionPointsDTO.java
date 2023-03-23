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
 * @create 2022-12-27 09:24:47.848421
 */
@Data
public class DeleteEnvironmentalCollectionPointsDTO {

	@NotNull(message = "主键标识为必须项")
	@NotEmpty(message = "主键标识不能为空")
	private String id;
}

