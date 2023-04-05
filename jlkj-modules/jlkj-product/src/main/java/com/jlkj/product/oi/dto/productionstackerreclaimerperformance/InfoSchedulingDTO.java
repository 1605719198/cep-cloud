package com.jlkj.product.oi.dto.productionstackerreclaimerperformance;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * DTO类-获取排班信息
 *
 * @author sudeyou
 * @since 2022-11-10 09:11:08
 */
@Data
@Schema(description = "DTO类-获取排班信息")
public class InfoSchedulingDTO {

    /**
     * 工号
     */
    @NotNull(message = "工号为必填项")
    @NotEmpty(message = "工号不能为空")
    @Size(max = 36, message = "工号长度不能超过36")
    @JsonProperty("job_number")
    private String jobNumber;

    /**
     * 应出勤时间
     */
    @NotNull(message = "应出勤时间为必填项")
    @NotEmpty(message = "应出勤时间不能为空")
    @Schema(description = "应出勤时间")
    @JsonProperty("due_attendance_time_work")
    private String dueAttendanceTimeWork;

}
