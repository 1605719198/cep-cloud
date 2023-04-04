package com.jlkj.product.oi.dto.productionplanconfigcoke;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * DTO类-修改-计划管理--配煤计划主表
 * @author sudeyou
 */
@Data
public class ConfirmProductionPlanConfigCokeDTO {
    /**
     * 主键标识;UUID
     */
    @NotNull(message = "主键标识;UUID为必填项")
    @NotEmpty(message = "主键标识;UUID不能为空")
    @Size(max = 36, message = "主键标识;UUID长度不能超过36")
    @JsonProperty("id")
    private String id;

    /**
     * 接收人编号
     */
    @NotNull(message = "接收人编号为必填项")
    @NotEmpty(message = "接收人编号不能为空")
    @Size(max = 36, message = "接收人编号长度不能超过36")
    @JsonProperty("receive_user_id")
    private String receiveUserId;

    /**
     * 接收人姓名
     */
    @NotNull(message = "接收人姓名为必填项")
    @NotEmpty(message = "接收人姓名不能为空")
    @Size(max = 50, message = "接收人姓名长度不能超过50")
    @JsonProperty("receive_user_name")
    private String receiveUserName;

}
