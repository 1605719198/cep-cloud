package com.jlkj.rabbit.dto.energy;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author yzl
 */
@Data
public class AddEnergyDayPlanConsumeDTO {

    private String uuid;

    private String energyName;

    private String types;

    private String factoryName;

    private String factoryArea;

    private String planDate;

    private BigDecimal planValue;

    private String unit;

    private String memo;

    private String memoa;

    private String memob;

    private String memoc;

    private BigDecimal advancedValue;

    private BigDecimal lawsValue;

    private String createDate;

    private String createEmpno;

    private String updateDate;

    private String updateEmpno;
}
