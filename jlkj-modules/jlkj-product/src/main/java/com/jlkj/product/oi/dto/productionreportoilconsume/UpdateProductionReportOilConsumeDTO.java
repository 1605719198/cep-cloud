package com.jlkj.product.oi.dto.productionreportoilconsume;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author yzl
 * @create 2023-04-13 13:27:01.939087
 */
@Data
public class UpdateProductionReportOilConsumeDTO {

    @NotNull(message = "id为必须项")
    @JsonProperty("id")
    private String id ;

    @NotNull(message = "消耗日期为必须项")
    @JsonProperty("consume_date")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date consumeDate ;

    @NotNull(message = "设备名称为必须项")
    @JsonProperty("device_name")
    private String deviceName ;

    @NotNull(message = "油品类型为必须项")
    @JsonProperty("oil_type")
    private String oilType ;

    @NotNull(message = "日耗用量为必须项")
    @JsonProperty("daily_consumption")
    @Min(value = 0, message = "日耗用量必须大于0")
    private String dailyConsumption ;

    @NotNull(message = "修改人id为必须项")
    @JsonProperty("modify_user_id")
    private String modifyUserId ;

    @NotNull(message = "修改人为必须项")
    @JsonProperty("modify_user_name")
    private String modifyUserName ;

}

