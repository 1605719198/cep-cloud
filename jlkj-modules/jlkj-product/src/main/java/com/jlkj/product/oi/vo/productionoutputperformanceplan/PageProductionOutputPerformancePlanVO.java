package com.jlkj.product.oi.vo.productionoutputperformanceplan;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * VO类-生产管理-炼焦实绩-推焦计划-数据同步-查询-分页
 *
 * @author sudeyou
 * @since 2023-04-10 10:11:01
 */
@Data
@Schema(description = "VO类-生产管理-炼焦实绩-推焦计划-数据同步-查询-分页")
public class PageProductionOutputPerformancePlanVO {
    /**
     * 主键标识;UUID
     */
    @Schema(description = "主键标识;UUID")
    @JsonProperty("id")
    private String id;

    /**
     * 炉区
     */
    @Schema(description = "炉区")
    @JsonProperty("furnace_area")
    private Integer furnaceArea;

    /**
     * 炉数
     */
    @Schema(description = "炉数")
    @JsonProperty("released_num")
    private Integer releasedNum;

    /**
     * 班组
     */
    @Schema(description = "班组")
    @JsonProperty("class_name")
    private String className;

    /**
     * 炉号
     */
    @Schema(description = "炉号")
    @JsonProperty("furnace_num")
    private Integer furnaceNum;

    /**
     * 上次装煤时间
     */
    @Schema(description = "上次装煤时间")
    @JsonProperty("last_coal_load_time")
    private String lastCoalLoadTime;

    /**
     * 计划结焦时间
     */
    @Schema(description = "计划结焦时间")
    @JsonProperty("plan_coking_time")
    private String planCokingTime;

    /**
     * 计划推焦时间
     */
    @Schema(description = "计划推焦时间")
    @JsonProperty("plan_focus_time")
    private String planFocusTime;

    /**
     * 状态
     */
    @Schema(description = "状态")
    @JsonProperty("status")
    private String status;

}
