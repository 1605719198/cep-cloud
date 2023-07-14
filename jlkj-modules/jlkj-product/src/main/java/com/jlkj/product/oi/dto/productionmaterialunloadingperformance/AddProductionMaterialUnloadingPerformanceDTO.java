package com.jlkj.product.oi.dto.productionmaterialunloadingperformance;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author zyf
 * @Description
 * @create 2022-05-10 13:50
 */
@Data
public class AddProductionMaterialUnloadingPerformanceDTO {

    private String id;

    private String planId;

    private String planDate;

    private String supplierNumber;

    private String supplierName;

    private String shippingMethod;

    private String vehicleNumber;

    private String storageSpacesId;

    private String materialNumber;

    private String materialName;

    private String materialCode;

    private BigDecimal materialWeight;

    private String shiftName;

    private String className;

    private String unloadingTime;
    // 0609新增 来源地代码
    private String fromCode;

    // 0609新增 来源地名称
    private String fromDecs;

}
