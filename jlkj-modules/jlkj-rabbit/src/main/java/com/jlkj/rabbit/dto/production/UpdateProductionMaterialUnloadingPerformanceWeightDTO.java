package com.jlkj.rabbit.dto.production;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author zyf
 * @Description
 * @create 2022-05-17 14:14
 */
@Data
public class UpdateProductionMaterialUnloadingPerformanceWeightDTO {

    @NotEmpty
    @NotNull
    private String planListNo;

    @NotNull
    private String stgNo;

    @NotNull
    private String stgName;

    @NotNull
    private String chkNo;

    @NotNull
    private String recvDate;
}
