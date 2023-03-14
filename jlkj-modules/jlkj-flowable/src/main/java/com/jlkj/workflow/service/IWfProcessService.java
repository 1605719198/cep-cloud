package com.jlkj.workflow.service;

import com.jlkj.common.core.web.page.PageQuery;
import com.jlkj.common.core.web.page.PageQuery;
import com.jlkj.common.core.web.page.TableDataInfoPlus;
import com.jlkj.flowable.core.domain.ProcessQuery;
import com.jlkj.workflow.domain.vo.WfDefinitionVo;
import com.jlkj.workflow.domain.vo.WfDetailVo;
import com.jlkj.workflow.domain.vo.WfTaskVo;

import java.util.List;
import java.util.Map;

/**
 * @author xin
 * @createTime 2022/3/24 18:57
 */
public interface IWfProcessService {

    /**
     * 查询可发起流程列表
     * @param pageQuery 分页参数
     * @return
     */
    TableDataInfoPlus<WfDefinitionVo> selectPageStartProcessList(ProcessQuery processQuery, PageQuery pageQuery);

    /**
     * 查询可发起流程列表
     */
    List<WfDefinitionVo> selectStartProcessList(ProcessQuery processQuery);

    /**
     * 查询我的流程列表
     * @param pageQuery 分页参数
     */
    TableDataInfoPlus<WfTaskVo> selectPageOwnProcessList(ProcessQuery processQuery, PageQuery pageQuery);

    /**
     * 查询我的流程列表
     */
    List<WfTaskVo> selectOwnProcessList(ProcessQuery processQuery);

    /**
     * 查询代办任务列表
     * @param pageQuery 分页参数
     */
    TableDataInfoPlus<WfTaskVo> selectPageTodoProcessList(ProcessQuery processQuery, PageQuery pageQuery);

    /**
     * 查询代办任务列表
     */
    List<WfTaskVo> selectTodoProcessList(ProcessQuery processQuery);

    /**
     * 查询待签任务列表
     * @param pageQuery 分页参数
     */
    TableDataInfoPlus<WfTaskVo> selectPageClaimProcessList(ProcessQuery processQuery, PageQuery pageQuery);

    /**
     * 查询待签任务列表
     */
    List<WfTaskVo> selectClaimProcessList(ProcessQuery processQuery);

    /**
     * 查询已办任务列表
     * @param pageQuery 分页参数
     */
    TableDataInfoPlus<WfTaskVo> selectPageFinishedProcessList(ProcessQuery processQuery, PageQuery pageQuery);

    /**
     * 查询已办任务列表
     */
    List<WfTaskVo> selectFinishedProcessList(ProcessQuery processQuery);

    /**
     * 查询流程部署关联表单信息
     * @param definitionId 流程定义ID
     * @param deployId 部署ID
     */
    String selectFormContent(String definitionId, String deployId);

    /**
     * 启动流程实例
     * @param procDefId 流程定义ID
     * @param variables 扩展参数
     */
    void startProcessByDefId(String procDefId, Map<String, Object> variables);

    /**
     * 通过DefinitionKey启动流程
     * @param procDefKey 流程定义Key
     * @param variables 扩展参数
     */
    void startProcessByDefKey(String procDefKey, Map<String, Object> variables);


    /**
     * 读取xml文件
     * @param processDefId 流程定义ID
     */
    String queryBpmnXmlById(String processDefId);


    /**
     * 查询流程任务详情信息
     * @param procInsId 流程实例ID
     * @param taskId 任务ID
     */
    WfDetailVo queryProcessDetail(String procInsId, String taskId);
}
