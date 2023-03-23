package com.jlkj.safety.si.dto.safesiteenvironmentalgovernance;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @description: dto 奖励比例信息
 * @author: yzl
 * @time: 2022年6月8日
 */
@Data
public class ProportionGovernanceDTO {

    private String id;

    @JsonProperty(value = "job_id")
    private String jobId ;

    @JsonProperty(value = "responsible_user_id")
    private String responsibleUserId ;

    @JsonProperty(value = "responsible_user_name")
    private String responsibleUserName ;

    @JsonProperty(value = "responsible_user_no")
    private String responsibleUserNo ;

    @JsonProperty(value = "reward_proportion")
    private BigDecimal rewardProportion ;
}
