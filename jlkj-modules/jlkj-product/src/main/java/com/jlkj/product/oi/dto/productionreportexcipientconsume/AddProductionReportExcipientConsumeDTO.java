package com.jlkj.product.oi.dto.productionreportexcipientconsume;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author yzl
 * @create 2023-04-13 15:00:09.780147
 */
@Data
public class AddProductionReportExcipientConsumeDTO {

    @NotNull(message = "消耗日期为必须项")
    @JsonProperty("consume_date")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date consumeDate ;

    @NotNull(message = "辅料名称为必须项")
    @JsonProperty("excipient_name")
    private String excipientName ;


    @NotNull(message = "日耗用量为必须项")
    @JsonProperty("daily_consumption")
    @Min(value = 0, message = "日耗用量必须大于0")
    private BigDecimal dailyConsumption ;

    @NotNull(message = "库存为必须项")
    @Min(value = 0, message = "库存必须大于0")
    private BigDecimal stock ;

    @NotNull(message = "创建人为必须项")
    @JsonProperty("create_user_id")
    private String createUserId ;

    @NotNull(message = "创建人为必须项")
    @JsonProperty("create_user_name")
    private String createUserName ;

}

