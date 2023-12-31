package com.jlkj.flow.workflow.service;

import com.jlkj.common.core.web.page.PageQuery;
import com.jlkj.common.core.web.page.TableDataInfoPlus;
import com.jlkj.flow.workflow.domain.bo.WfModelBo;
import com.jlkj.flow.workflow.domain.vo.WfModelVo;


import java.util.Collection;
import java.util.List;

/**
 * @author xin
 * @createTime 2022/6/21 9:11
 */
public interface IWfModelService {

    /**
     * 查询流程模型列表
     * @param modelBo
     * @param pageQuery
     * @return
     */
    TableDataInfoPlus<WfModelVo> list(WfModelBo modelBo, PageQuery pageQuery);

    /**
     * 查询流程模型列表
     * @param modelBo
     * @return
     */
    List<WfModelVo> list(WfModelBo modelBo);

    /**
     * 查询流程模型列表
     * @param modelBo
     * @param pageQuery
     * @return
     */
    TableDataInfoPlus<WfModelVo> historyList(WfModelBo modelBo, PageQuery pageQuery);

    /**
     * 查询流程模型详情信息
     * @param modelId
     * @return
     */
    WfModelVo getModel(String modelId);

    /**
     * 查询流程表单详细信息
     * @param modelId
     * @return
     */
    String queryBpmnXmlById(String modelId);

    /**
     * 新增模型信息
     * @param modelBo
     */
    void insertModel(WfModelBo modelBo);

    /**
     * 修改模型信息
     * @param modelBo
     */
    void updateModel(WfModelBo modelBo);

    /**
     * 保存流程模型信息
     * @param modelBo
     */
    void saveModel(WfModelBo modelBo);

    /**
     * 设为最新流程模型
     * @param modelId
     */
    void latestModel(String modelId);

    /**
     * 删除流程模型
     * @param ids
     */
    void deleteByIds(Collection<String> ids);

    /**
     * 部署流程模型
     * @param modelId
     * @return
     */
    boolean deployModel(String modelId);
}
