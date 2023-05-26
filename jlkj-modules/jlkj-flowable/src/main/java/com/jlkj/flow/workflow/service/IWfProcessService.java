package com.jlkj.flow.workflow.service;

import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.PageQuery;
import com.jlkj.common.core.web.page.TableDataInfoPlus;
import com.jlkj.flow.flowable.core.domain.ProcessQuery;
import com.jlkj.flow.workflow.domain.vo.WfDefinitionVo;
import com.jlkj.flow.workflow.domain.vo.WfDetailVo;
import com.jlkj.flow.workflow.domain.vo.WfTaskVo;

import java.util.List;
import java.util.Map;

/**
 * @author xin
 * @createTime 2022/3/24 18:57
 */
public interface IWfProcessService {

    /**
     * 查询可发起流程列表
     * @param processQuery
     * @param pageQuery
     * @return
     */
    TableDataInfoPlus<WfDefinitionVo> selectPageStartProcessList(ProcessQuery processQuery, PageQuery pageQuery);

    /**
     * 查询可发起流程列表
     * @param processQuery
     * @return
     */
    List<WfDefinitionVo> selectStartProcessList(ProcessQuery processQuery);

    /**
     * 查询我的流程列表
     * @param processQuery
     * @param pageQuery
     * @return
     */
    TableDataInfoPlus<WfTaskVo> selectPageOwnProcessList(ProcessQuery processQuery, PageQuery pageQuery);

    /**
     * 查询我的流程列表
     * @param processQuery
     * @return
     */
    List<WfTaskVo> selectOwnProcessList(ProcessQuery processQuery);

    /**
     *查询代办任务列表
     * @param processQuery
     * @param pageQuery
     * @return
     */
    TableDataInfoPlus<WfTaskVo> selectPageTodoProcessList(ProcessQuery processQuery, PageQuery pageQuery);

    /**
     * 查询代办任务列表
     * @param processQuery
     * @return
     */
    List<WfTaskVo> selectTodoProcessList(ProcessQuery processQuery);

    /**
     * 查询待签任务列表
     * @param processQuery
     * @param pageQuery
     * @return
     */
    TableDataInfoPlus<WfTaskVo> selectPageClaimProcessList(ProcessQuery processQuery, PageQuery pageQuery);

    /**
     * 查询待签任务列表
     * @param processQuery
     * @return
     */
    List<WfTaskVo> selectClaimProcessList(ProcessQuery processQuery);

    /**
     * 查询已办任务列表
     * @param processQuery
     * @param pageQuery
     * @return
     */
    TableDataInfoPlus<WfTaskVo> selectPageFinishedProcessList(ProcessQuery processQuery, PageQuery pageQuery);

    /**
     * 查询已办任务列表
     * @param processQuery
     * @return
     */
    List<WfTaskVo> selectFinishedProcessList(ProcessQuery processQuery);

    /**
     * 查询流程部署关联表单信息
     * @param definitionId
     * @param deployId
     * @return
     */
    String selectFormContent(String definitionId, String deployId);

    /**
     * 启动流程实例
     * @param procDefId
     * @param variables
     */
    void startProcessByDefId(String procDefId, Map<String, Object> variables);

    /**
     * 根据流程定义id启动流程实例（重写）
     * @param variables
     */
    AjaxResult startProcessOverride(Map<String, Object> variables);

    /**
     * 通过DefinitionKey启动流程
     * @param procDefKey
     * @param variables
     */
    void startProcessByDefKey(String procDefKey, Map<String, Object> variables);

    /**
     * 读取xml文件
     * @param processDefId
     * @return
     */
    String queryBpmnXmlById(String processDefId);

    /**
     * 查询流程任务详情信息
     * @param procInsId
     * @param taskId
     * @return
     */
    WfDetailVo queryProcessDetail(String procInsId, String taskId);

}
