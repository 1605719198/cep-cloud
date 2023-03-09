package com.jlkj.flowable.domain.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 动态人员、组
 * @author xin
 * @date 2021/4/17 22:59
 */
@Data
public class FlowNextDto implements Serializable {

    private String type;
    private String dataType;
    private String vars;

}
