package com.jlkj.energy.ee.dto.energymillofcost;

import lombok.Data;

/**
 * @author 智能研发室 黄兵
 * @date 2022/4/30 - 10:09
 */
@Data
public class EnergyMillOfCostDTO {

    /**
     * 工场产线代码开始值
     */
    private String millIdCodeStart;

    /**
     * 工场产线代码结束值
     */
    private String millIdCodeEnd;

    /**
     * 产线名称
     */
    private String millIdName;
}
