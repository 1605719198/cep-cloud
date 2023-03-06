package com.jlkj.material.mr.dto.materialscoalstockhistory;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author zyf
 * @Description
 * @create 2022-10-24 9:46
 */
@Data
public class SendDTO {

    @JsonProperty("organization_code")
    private String organizationCode;

    @JsonProperty("matrlnoA")
    private String matrlnoA;

    @JsonProperty("stgNoA")
    private String stgNoA;

    @JsonProperty("qty")
    private String qty;

    @JsonProperty("waterRate")
    private String waterRate;
}
