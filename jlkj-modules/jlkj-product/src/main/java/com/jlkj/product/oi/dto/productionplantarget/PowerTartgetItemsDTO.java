package com.jlkj.product.oi.dto.productionplantarget;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author zyf
 * @Description
 * @create 2022-04-29 15:48
 */
@Data
public class PowerTartgetItemsDTO {
    private String id;

    private BigDecimal value;

    private Integer calcUnitId;

    @NotNull(message = "措施及完成目标为必须项")
    @JsonProperty("measures_and_goals")
    private String measuresAndGoals;

    @NotNull(message = "责任人为必须项")
    @JsonProperty("responsible_person")
    private String responsiblePerson;
}
