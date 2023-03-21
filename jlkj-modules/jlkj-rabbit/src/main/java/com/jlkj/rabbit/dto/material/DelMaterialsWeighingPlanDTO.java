package com.jlkj.rabbit.dto.material;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author zyf
 * @Description
 * @create 2022-05-16 15:50
 */
@Data
public class DelMaterialsWeighingPlanDTO {
    @NotNull
    @NotEmpty
    private String id;
}
