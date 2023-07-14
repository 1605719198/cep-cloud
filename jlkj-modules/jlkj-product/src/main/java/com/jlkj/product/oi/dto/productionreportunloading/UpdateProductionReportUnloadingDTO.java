package com.jlkj.product.oi.dto.productionreportunloading;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author yzl
 * @create 2023-04-14 11:00:33.378526
 */
@Data
public class UpdateProductionReportUnloadingDTO {

    @NotNull(message = "id为必须项")
    @JsonProperty("id")
    private String id ;

    @NotNull(message = "统计日期为必须项")
    @JsonProperty("statistics_date")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date statisticsDate ;

    @NotNull(message = "来煤方式为必须项")
    @JsonProperty("coal_way")
    private String coalWay ;

    @NotNull(message = "来煤数量为必须项")
    @JsonProperty("coal_num")
    @Min(value = 0, message = "来煤数量必须大于0")
    private Integer coalNum ;

    @NotNull(message = "库存为必须项")
    @JsonProperty("stock")
    @Min(value = 0, message = "库存必须大于0")
    private BigDecimal stock ;

    @NotNull(message = "卸煤-螺旋卸为必须项")
    @JsonProperty("unloading_coal_screw")
    @Min(value = 0, message = "卸煤-螺旋卸必须大于0")
    private Integer unloadingCoalScrew ;

    @NotNull(message = "卸煤-分卸为必须项")
    @JsonProperty("unloading_coal_part")
    @Min(value = 0, message = "卸煤-分卸必须大于0")
    private Integer unloadingCoalPart ;

    @NotNull(message = "待卸-螺旋卸为必须项")
    @JsonProperty("unloaded_screw")
    @Min(value = 0, message = "待卸-螺旋卸必须大于0")
    private Integer unloadedScrew ;

    @NotNull(message = "待卸-分卸为必须项")
    @JsonProperty("unloaded_part")
    @Min(value = 0, message = "待卸-分卸必须大于0")
    private Integer unloadedPart ;

    @NotNull(message = "修改人id为必须项")
    @JsonProperty("modify_user_id")
    private String modifyUserId ;

    @NotNull(message = "修改人为必须项")
    @JsonProperty("modify_user_name")
    private String modifyUserName ;

}

