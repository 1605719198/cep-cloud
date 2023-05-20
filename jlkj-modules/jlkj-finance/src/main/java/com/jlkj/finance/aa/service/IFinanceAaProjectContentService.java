package com.jlkj.finance.aa.service;

import com.jlkj.finance.aa.domain.FinanceAaProjectContent;

import java.util.List;

/**
 * 集团管理-报表内容资料Service接口
 * 
 * @author 116514
 * @date 2023-05-16
 */
public interface IFinanceAaProjectContentService 
{
    /**
     * 查询集团管理-报表内容资料
     * 
     * @param id 集团管理-报表内容资料主键
     * @return 集团管理-报表内容资料
     */
    public FinanceAaProjectContent selectFinanceAaProjectContentById(String id);

    /**
     * 查询集团管理-报表内容资料列表
     * 
     * @param financeAaProjectContent 集团管理-报表内容资料
     * @return 集团管理-报表内容资料集合
     */
    public List<FinanceAaProjectContent> selectFinanceAaProjectContentList(FinanceAaProjectContent financeAaProjectContent);

    /**
     * 新增集团管理-报表内容资料
     * 
     * @param financeAaProjectContent 集团管理-报表内容资料
     * @return 结果
     */
    public int insertFinanceAaProjectContent(FinanceAaProjectContent financeAaProjectContent);

    /**
     *  新增集团管理-报表内容资料（多笔）
     * @param dataList
     * @return
     */
    public int insertFinanceAaProjectContentItem( List<FinanceAaProjectContent> dataList);

    /**
     * 修改集团管理-报表内容资料
     * 
     * @param financeAaProjectContent 集团管理-报表内容资料
     * @return 结果
     */
    public int updateFinanceAaProjectContent(FinanceAaProjectContent financeAaProjectContent);

    /**
     * 批量删除集团管理-报表内容资料
     * 
     * @param ids 需要删除的集团管理-报表内容资料主键集合
     * @return 结果
     */
    public int deleteFinanceAaProjectContentByIds(String[] ids);

    /**
     * 删除集团管理-报表内容资料信息
     * 
     * @param id 集团管理-报表内容资料主键
     * @return 结果
     */
    public int deleteFinanceAaProjectContentById(String id);
}
