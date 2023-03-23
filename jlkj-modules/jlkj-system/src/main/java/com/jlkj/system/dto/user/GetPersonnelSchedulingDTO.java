package com.jlkj.system.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author yzl
 * @create 2023年1月4日 09:49:34
 */
@Data
public class GetPersonnelSchedulingDTO {

    @JsonProperty("job_number")
    private String jobNumber;

    @JsonProperty("full_name")
    private String fullName;

    @JsonProperty("post_name")
    private String postName;

    @JsonProperty("scheduling_date")
    private String schedulingDate;

    private String shift;

    @JsonProperty("class_type")
    private String classType;

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
