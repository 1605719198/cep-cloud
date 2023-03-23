package com.jlkj.safety.si.dto.safeimportantwork;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @description: dto 奖励比例列表
 * @author: yzl
 * @time: 2022年6月6日
 */
@Data
public class ListProportionsDTO {

    @NotNull(message = "现场环境治理id为必须项")
    @NotEmpty(message = "现场环境治理id不能为空")
    private String id;
}
