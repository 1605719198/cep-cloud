package com.jlkj.product.oi.vo.productioncokeshedoutput;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * VO类-焦棚产量维护-查询-分页
 *
 * @author sudeyou
 * @since 2022-10-18 14:57:19
 */
@Data
@Schema(description = "VO类-焦棚产量维护-查询-分页")
public class PageProductionCokeShedOutputVO {
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
     * 产量(t)
     */
    @Schema(description = "产量(t)")
    @JsonProperty("yield")
    private BigDecimal yield;

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

