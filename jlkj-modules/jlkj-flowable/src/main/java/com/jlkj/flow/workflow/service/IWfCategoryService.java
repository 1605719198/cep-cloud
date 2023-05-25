package com.jlkj.flow.workflow.service;


import com.jlkj.common.core.web.page.PageQuery;
import com.jlkj.common.core.web.page.TableDataInfoPlus;
import com.jlkj.flow.workflow.domain.bo.WfCategoryBo;
import com.jlkj.flow.workflow.domain.vo.WfCategoryVo;

import java.util.Collection;
import java.util.List;

/**
 * 流程分类Service接口
 *
 * @author xin
 * @date 2022-01-15
 */
public interface IWfCategoryService {

    /**
     * 查询单个
     * @param categoryId
     * @return
     */
    WfCategoryVo queryById(Long categoryId);

    /**
     * 查询列表
     * @param bo
     * @param pageQuery
     * @return
     */
    TableDataInfoPlus<WfCategoryVo> queryPageList(WfCategoryBo bo, PageQuery pageQuery);

    /**
     * 查询列表
     * @param categoryBo
     * @return
     */
    List<WfCategoryVo> queryList(WfCategoryBo categoryBo);

    /**
     * 新增流程分类
     * @param categoryBo
     * @return
     */
    int insertCategory(WfCategoryBo categoryBo);

    /**
     * 编辑流程分类
     * @param categoryBo
     * @return
     */
    int updateCategory(WfCategoryBo categoryBo);

    /**
     * 校验并删除数据
     * @param ids
     * @param isValid
     * @return
     */
    int deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    /**
     * 校验分类编码是否唯一
     * @param code
     * @param categoryId
     * @return
     */
    String checkCategoryCodeUnique(String code,Long categoryId);
}
