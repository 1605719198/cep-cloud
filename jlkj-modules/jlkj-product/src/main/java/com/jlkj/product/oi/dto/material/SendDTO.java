package com.jlkj.product.oi.dto.material;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author yzl
 * @Description
 * @create 2022年11月30日 15:02:13
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
