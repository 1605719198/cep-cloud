package com.jlkj.product.oi.vo.productioncokeovens;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * VO类-每日推焦炉数维护-查询-分页
 *
 * @author sudeyou
 * @since 2022-11-01 10:10:47
 */
@Data
@Schema(description = "VO类-每日推焦炉数维护-查询-分页")
public class PageProductionCokeOvensVO {
    /**
     * 主键标识;UUID
     */
    @Schema(description = "主键标识;UUID")
    @JsonProperty("id")
    private String id;

    /**
     * 账务日期
     */
    @Schema(description = "账务日期")
    @JsonProperty("account_date")
    private String accountDate;

    /**
     * 物料代码
     */
    @Schema(description = "物料代码")
    @JsonProperty("materials_code")
    private String materialsCode;

    /**
     * 物料名称
     */
    @Schema(description = "物料名称")
    @JsonProperty("materials_name")
    private String materialsName;

    /**
     * 日推焦总炉数
     */
    @Schema(description = "日推焦总炉数")
    @JsonProperty("out_num")
    private Integer outNum;

    /**
     * 日理论总产量(t)(全焦量)
     */
    @Schema(description = "日理论总产量(t)(全焦量)")
    @JsonProperty("theoretical_yield")
    private BigDecimal theoreticalYield;

    /**
     * 日推焦干熄炉数
     */
    @Schema(description = "日推焦干熄炉数")
    @JsonProperty("dry_out_num")
    private Integer dryOutNum;

    /**
     * 日理论干熄产量(t)(全焦量)
     */
    @Schema(description = "日理论干熄产量(t)(全焦量)")
    @JsonProperty("dry_theoretical_yield")
    private BigDecimal dryTheoreticalYield;

    /**
     * 日推焦湿熄炉数
     */
    @Schema(description = "日推焦湿熄炉数")
    @JsonProperty("wet_out_num")
    private Integer wetOutNum;

    /**
     * 日理论湿熄产量(t)(全焦量)
     */
    @Schema(description = "日理论湿熄产量(t)(全焦量)")
    @JsonProperty("wet_theoretical_yield")
    private BigDecimal wetTheoreticalYield;

    /**
     * 抛送状态(0:未抛送；1:已抛送)
     */
    @Schema(description = "抛送状态(0:未抛送；1:已抛送)")
    @JsonProperty("throw_state")
    private String throwState;

    /**
     * 创建人
     */
    @Schema(description = "创建人")
    @JsonProperty("create_user_id")
    private String createUserId;

    /**
     * 创建人
     */
    @Schema(description = "创建人")
    @JsonProperty("create_user_name")
    private String createUserName;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @JsonProperty("create_time")
    private String createTime;

    /**
     * 修改人id
     */
    @Schema(description = "修改人id")
    @JsonProperty("modify_user_id")
    private String modifyUserId;

    /**
     * 修改人
     */
    @Schema(description = "修改人")
    @JsonProperty("modify_user_name")
    private String modifyUserName;

    /**
     * 修改时间
     */
    @Schema(description = "修改时间")
    @JsonProperty("modify_time")
    private String modifyTime;

}

