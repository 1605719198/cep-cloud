package com.jlkj.material.mr.dto.materialscokestock;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author yzl
 * @Description 物料焦场库存 调整
 * @create 2022年6月10日 10:12:26
 */
@Data
public class AdjustMaterialsCokeStockDTO {
    @NotNull(message = "主键标识;UUID为必须项")
    @JsonProperty("id")
    private String id;

    @NotNull(message = "库存量为必须项")
    @JsonProperty("inventory")
    private BigDecimal inventory;

    @NotNull(message = "调整人编号为必须项")
    @JsonProperty("modify_user_id")
    private String modifyUserId;

    @NotNull(message = "调整人姓名为必须项")
    @JsonProperty("modify_user_name")
    private String modifyUserName;

    @JsonProperty("remark")
    private String remark;
}
