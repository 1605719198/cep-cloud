package com.jlkj.flowable.mapper;

import com.jlkj.flowable.domain.dto.FlowProcDefDto;

import java.util.List;

/**
 * 流程定义查询
 *
 * @author xin
 * @email
 * @date 2022/1/29 5:44 下午
 **/
public interface FlowDeployMapper {

    /**
     * 流程定义列表
     * @param name
     * @return
     */
    List<FlowProcDefDto> selectDeployList(String name);
}
