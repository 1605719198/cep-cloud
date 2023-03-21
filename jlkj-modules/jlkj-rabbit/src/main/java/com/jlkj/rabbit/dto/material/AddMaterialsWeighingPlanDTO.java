package com.jlkj.rabbit.dto.material;

import lombok.Data;

/**
 * @author zyf
 * @Description
 * @create 2022-05-16 15:57
 */
@Data
public class AddMaterialsWeighingPlanDTO {
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
}
