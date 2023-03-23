package com.jlkj.safety.si.dto.safeimportantwork;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * @description: dto 新增
 * @author: yzl
 * @time: 2022年6月6日
 */
@Data
public class AddSafeImportantWorkDTO {
    /** 单位 */
    @NotNull(message = "单位必须项")
    @JsonProperty("department_id")
    private String departmentId ;
    /** 主体 */
    @NotNull(message = "主体必须项")
    @JsonProperty("subject")
    private String subject ;
    /** 完成目标 */
    @NotNull(message = "完成目标必须项")
    @JsonProperty("complete_target")
    private String completeTarget ;
    /** 完成情况 */
    @NotNull(message = "完成情况必须项")
    @JsonProperty("complete_state")
    private Integer completeState ;
    /** 计划完成时间 */
    @NotNull(message = "计划完成时间必须项")
    @JsonProperty("plan_complete_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date planCompleteTime ;
    /** 备注 */
    @JsonProperty("remarks")
    private String remarks ;

    @NotNull(message = "创建人编号为必须项")
    @NotEmpty(message = "创建人编号不能为空")
    @JsonProperty("create_user_id")
    private String createUserId;

    @NotNull(message = "创建人为必须项")
    @NotEmpty(message = "创建人不能为空")
    @JsonProperty("create_user_name")
    private String createUserName;

    @NotNull(message = "奖励比例为必须项")
    private List<ProportionDTO> proportions;
}
