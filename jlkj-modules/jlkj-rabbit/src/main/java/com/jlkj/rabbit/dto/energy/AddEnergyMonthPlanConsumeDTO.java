package com.jlkj.rabbit.dto.energy;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author yzl
 */
@Data
public class AddEnergyMonthPlanConsumeDTO {

    private String uuid;

    private String energyName;

    private String costCenterCode;

    private String types;

    private String factoryName;

    private String factoryArea;

    private String planyearMonth;

    private BigDecimal planValue;

    private String unit;

    private String memo;

    private String memoa;

    private BigDecimal advancedValue;

    private BigDecimal lawsValue;

    private String createDate;

    private String createEmpno;

    private String updateEmpno;

    private String updateDate;
}
