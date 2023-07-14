package com.jlkj.product.oi.vo.productionoutputperformancemanualbake;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;


/**
 * VO类-当前焦炭等级数据
 *
 * @author sudeyou
 * @since 2022-12-28 13:35:48
 */
@Data
public class CurrentCokeLevelCodeVO {

    /**
     * 焦炭等级料号
     */
    @JsonProperty("coke_level_materials_code")
    private String cokeLevelMaterialsCode;

    /**
     * 焦炭等级名称
     */
    @JsonProperty("coke_level_materials_name")
    private String cokeLevelMaterialsName;

    /**
     * 配煤计划编号
     */
    @JsonProperty("plan_number")
    private String planNumber;
    /**
     * 配煤实绩id
     */
    @JsonProperty("id")
    private String id;
    /**
     * 配煤实绩明细开始时间
     */
    @JsonProperty("coal_detail_start_time")
    private Date coalDetailStartTime;
}
