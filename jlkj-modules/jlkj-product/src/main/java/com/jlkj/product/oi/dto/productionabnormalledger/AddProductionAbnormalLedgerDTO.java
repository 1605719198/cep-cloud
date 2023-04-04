package com.jlkj.product.oi.dto.productionabnormalledger;

import lombok.Data;

/**
 * @author zyf
 * @Description
 * @create 2022-05-12 8:32
 */
@Data
public class AddProductionAbnormalLedgerDTO {

    private String departmentId;
    private String departmentName;
    private String happenTime;
    private String accidentName;
    private String preventiveMeasures;
    private String measuresCompletionTime;
    private String measuresImplementation;
    private String professionalResponsible;
    private String trackResponsiblePerson;
    private String accidentReportFile;
    private String createUserId;
    private String createUserNname;
}
