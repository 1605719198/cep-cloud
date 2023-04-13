package com.jlkj.system.dto.humanresourceschedule;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author yzl
 * @create 2023-04-07 09:44:19.096521
 */
@Data
public class GetHumanresourceScheduleDTO {

	@JsonProperty("start_time")
	private String startTime;

}

