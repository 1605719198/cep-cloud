package com.jlkj.rabbit.dto.production;

import lombok.Data;

/**
 * @author zyf
 * @Description
 * @create 2022-05-10 13:50
 */
@Data
public class AddProductionMaterialUnloadingPerformanceErpDTO {

    private String planListNo;
    private String planDate;
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
    private String grossWgt;
    private String tartWgt;
    private String netWgt;


}
