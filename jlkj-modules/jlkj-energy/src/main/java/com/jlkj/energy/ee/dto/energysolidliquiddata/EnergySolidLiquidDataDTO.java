package com.jlkj.energy.ee.dto.energysolidliquiddata;

import lombok.Data;

/**
 * @author 智能研发室 黄兵
 * @date 2022/4/29 - 10:53
 */
@Data
public class EnergySolidLiquidDataDTO {
    /**
     * 能源代码开始值
     */
    private String dateYm;

    /**
     * 能源代码开始值
     */
    private String engyIdStart;

    /**
     * 能源代码结束值
     */
    private String engyIdEnd;

    /**
     * 能源计量日期开始值
     */
    private String engyDateStart;

    /**
     * 能源计量日期结束值
     */
    private String engyDateEnd;

    /**
     * 成本中心代号开始值
     */
    private String costCenterStart;

    /**
     * 成本中心代号结束值
     */
    private String costCenterEnd;

    /**
     * 当前页
     */
    private Long pageNum;

    /**
     * 每页记录数
     */
    private Long pageSize;
}
