package com.jlkj.material.mr.dto.materialscokestock;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author yzl
 * @Description 物料焦场库存 调整
 * @create 2022年11月21日 15:25:40
 */
@Data
public class AdjustInventoryDTO {

    @JsonProperty("materials_code")
    private String materialCode;

    @JsonProperty("inventory")
    private BigDecimal inventory;

    @JsonProperty("remark")
    private String remark;

}
