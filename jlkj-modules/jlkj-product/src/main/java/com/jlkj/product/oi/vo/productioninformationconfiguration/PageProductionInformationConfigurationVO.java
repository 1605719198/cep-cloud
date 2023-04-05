package com.jlkj.product.oi.vo.productioninformationconfiguration;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * VO类-生产信息配置-查询-分页
 *
 * @author sudeyou
 * @since 2022-10-18 14:47:44
 */
@Data
@Schema(description = "VO类-生产信息配置-查询-分页")
public class PageProductionInformationConfigurationVO {
    /**
     * 主键标识;UUID
     */
    @Schema(description = "主键标识;UUID")
    @JsonProperty("id")
    private String id;

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
     * 熄焦方式(1:干熄;2:湿熄)
     */
    @Schema(description = "熄焦方式(1:干熄;2:湿熄)")
    @JsonProperty("quenching_method")
    private String quenchingMethod;

    /**
     * 单孔理论重量
     */
    @Schema(description = "单孔理论重量")
    @JsonProperty("single_hole_weight")
    private BigDecimal singleHoleWeight;

    /**
     * 理论单耗
     */
    @Schema(description = "理论单耗")
    @JsonProperty("single_consumption")
    private BigDecimal singleConsumption;

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

