package com.jlkj.safety.si.dto.safeimportantwork;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @description: dto 奖励比例信息
 * @author: yzl
 * @time: 2022年6月8日
 */
@Data
public class ProportionDTO {

    private String id;

    private String major ;

    @JsonProperty(value = "job_id")
    private String jobId ;

    @JsonProperty(value = "responsible_user_id")
    private String responsibleUserId ;

    @JsonProperty(value = "responsible_user_name")
    private String responsibleUserName ;

    @JsonProperty(value = "job_number")
    private String jobNumber ;

    @JsonProperty(value = "reward_proportion")
    private BigDecimal rewardProportion ;
}
