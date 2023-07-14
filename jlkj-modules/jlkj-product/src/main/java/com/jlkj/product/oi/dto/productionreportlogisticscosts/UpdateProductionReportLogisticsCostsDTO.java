package com.jlkj.product.oi.dto.productionreportlogisticscosts;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author yzl
 * @create 2023-04-17 15:00:13.650137
 */
@Data
public class UpdateProductionReportLogisticsCostsDTO {

    @NotNull(message = "id为必须项")
    @JsonProperty("id")
    private String id ;

    @NotNull(message = "统计日期为必须项")
    @JsonProperty("statistics_date")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date statisticsDate ;

    @NotNull(message = "卸车费(元)为必须项")
    @JsonProperty("unloading_fee")
    private BigDecimal unloadingFee ;

    @NotNull(message = "倒运费(元)为必须项")
    @JsonProperty("reverse_fee")
    private BigDecimal reverseFee ;

    @NotNull(message = "临时用(元)为必须项")
    @JsonProperty("temporary_fee")
    private BigDecimal temporaryFee ;

    @NotNull(message = "修改人id为必须项")
    @JsonProperty("modify_user_id")
    private String modifyUserId ;

    @NotNull(message = "修改人为必须项")
    @JsonProperty("modify_user_name")
    private String modifyUserName ;

}

