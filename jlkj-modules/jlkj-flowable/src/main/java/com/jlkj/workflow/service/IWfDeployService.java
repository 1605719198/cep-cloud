package com.jlkj.workflow.service;


import com.jlkj.common.core.web.page.PageQuery;
import com.jlkj.common.core.web.page.TableDataInfoPlus;
import com.jlkj.flowable.core.domain.ProcessQuery;
import com.jlkj.workflow.domain.vo.WfDeployVo;

import java.util.List;

/**
 * @author xin
 * @createTime 2022/6/30 9:03
 */
public interface IWfDeployService {

    TableDataInfoPlus<WfDeployVo> queryPageList(ProcessQuery processQuery, PageQuery pageQuery);

    TableDataInfoPlus<WfDeployVo> queryPublishList(String processKey, PageQuery pageQuery);

    void updateState(String definitionId, String stateCode);

    String queryBpmnXmlById(String definitionId);

    void deleteByIds(List<String> deployIds);
}
