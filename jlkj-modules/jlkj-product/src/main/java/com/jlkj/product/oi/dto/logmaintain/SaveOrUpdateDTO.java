package com.jlkj.product.oi.dto.logmaintain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author zyf
 * @Description
 * @create 2022-10-10 9:21
 */
@Data
public class SaveOrUpdateDTO {

    @JsonProperty("id")
    private String id;

    @JsonProperty("item_id")
    private String itemId;

    @JsonProperty("item_name")
    private String itemName;

    @JsonProperty("content")
    private String content;

    @JsonProperty("time")
    private String time;

    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("user_name")
    private String userName;
}
