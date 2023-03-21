package com.jlkj.rabbit.dto.energy;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author yzl
 */
@Data
public class AddEnergyDayPlanOutputDTO {

    private String uuid ;

    private String serialNo ;

    private String factoryName ;

    private String factoryArea ;

    private BigDecimal planValue ;

    private String planDate ;

    private String unit ;

    private String createDate ;

    private String createEmpno ;

    private String updateDate ;

    private String updateEmpno ;

    private String memo ;
}
