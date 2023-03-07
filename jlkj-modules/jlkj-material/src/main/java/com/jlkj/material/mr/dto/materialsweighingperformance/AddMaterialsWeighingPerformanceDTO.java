package com.jlkj.material.mr.dto.materialsweighingperformance;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author zyf
 * @Description
 * @create 2022-05-23 15:01
 */
@Data
public class AddMaterialsWeighingPerformanceDTO {
    private String planListNo;
    private String transType;
    private String carrierType;
    private String lotNo;
    private String carrierNo;
    private String seqNo;
    private String mtrlNo;
    private String mtrlName;
    private String fromCode;
    private String fromDesc;
    private String toCode;
    private String toDesc;
    private String pchrNo;
    private String isLoad;
    private String loadStation;
    private String issueDept;
    private String issueEmpNo;
    private String issueDate;
    private String isBack;
    private String wgtListNo;
    private BigDecimal grossWgt;
    private BigDecimal tartWgt;
    private BigDecimal netWgt;
}
