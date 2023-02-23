package com.jlkj.energy.ee.dto.dashboardenergy;

import lombok.Data;

/**
 * @author liukuiyan
 * @Description
 * @create 2023-02-16 14:31
 */
@Data
public class GetDashBoardEnergyDTO {

    private String startTime;

    private String endTime;

    private String energyTypeName;
}
