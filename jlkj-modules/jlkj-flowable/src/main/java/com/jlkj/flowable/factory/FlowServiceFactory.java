package com.jlkj.flowable.factory;

import lombok.Getter;
import org.flowable.engine.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * flowable 引擎注入封装
 * @author xin
 * @date 2021-04-03
 */
@Component
@Getter
public class FlowServiceFactory {

    @Autowired
    protected RepositoryService repositoryService;

    @Autowired
    protected RuntimeService runtimeService;

    @Autowired
    protected IdentityService identityService;

    @Autowired
    protected TaskService taskService;

    @Autowired
    protected HistoryService historyService;

    @Autowired
    protected ManagementService managementService;

    @Qualifier("processEngine")
    @Autowired
    protected ProcessEngine processEngine;

}
