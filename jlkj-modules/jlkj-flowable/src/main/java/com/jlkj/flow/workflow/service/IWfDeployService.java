package com.jlkj.flow.workflow.service;


import com.jlkj.common.core.web.page.PageQuery;
import com.jlkj.common.core.web.page.TableDataInfoPlus;
import com.jlkj.flow.flowable.core.domain.ProcessQuery;
import com.jlkj.flow.workflow.domain.vo.WfDeployVo;

import java.util.List;

/**
 * @author xin
 * @createTime 2022/6/30 9:03
 */
public interface IWfDeployService {

    /**
     * 分页查询
     * @param processQuery
     * @param pageQuery
     * @return
     */
    TableDataInfoPlus<WfDeployVo> queryPageList(ProcessQuery processQuery, PageQuery pageQuery);

    /**
     * 查询发布列表
     * @param processKey
     * @param pageQuery
     * @return
     */
    TableDataInfoPlus<WfDeployVo> queryPublishList(String processKey, PageQuery pageQuery);

    /**
     * 更新挂起、激活
     * @param definitionId
     * @param stateCode
     */
    void updateState(String definitionId, String stateCode);

    /**
     * 根据id查询xml
     * @param definitionId
     * @return
     */
    String queryBpmnXmlById(String definitionId);

    /**
     * 根据id删除
     * @param deployIds
     */
    void deleteByIds(List<String> deployIds);

    /**
     * 查询流程部署版本列表--升级版
     * @param processQuery
     * @param pageQuery
     * @return
     */
    TableDataInfoPlus<WfDeployVo> getDeployListPlus(ProcessQuery processQuery, PageQuery pageQuery);
}
