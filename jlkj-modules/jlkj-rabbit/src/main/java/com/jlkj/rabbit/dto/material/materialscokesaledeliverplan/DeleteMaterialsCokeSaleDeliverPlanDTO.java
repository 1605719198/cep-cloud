package com.jlkj.rabbit.dto.material.materialscokesaledeliverplan;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * DTO类-焦炭外售发货计划-删除
 * @author sudeyou
 * @since 2022-08-19 14:00:15
 */
@Data
@Schema(description = "DTO类-焦炭外售发货计划-删除")
public class DeleteMaterialsCokeSaleDeliverPlanDTO {
    /**
     * 发货单号
     */
    @NotNull(message = "发货单号为必填项")
    @NotEmpty(message = "发货单号不能为空")
    @Size(max = 20, message = "发货单号长度不能超过20")
    @Schema(description = "发货单号")
    @JsonProperty("plan_list_no")
    private String planListNo;

}

