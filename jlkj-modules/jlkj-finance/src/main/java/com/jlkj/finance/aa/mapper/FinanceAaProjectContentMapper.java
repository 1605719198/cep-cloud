package com.jlkj.finance.aa.mapper;

import com.jlkj.finance.aa.domain.FinanceAaProjectContent;

import java.util.List;

/**
 * 集团管理-报表内容资料Mapper接口
 * 
 * @author 116514
 * @date 2023-05-16
 */
public interface FinanceAaProjectContentMapper 
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
     * 修改集团管理-报表内容资料
     * 
     * @param financeAaProjectContent 集团管理-报表内容资料
     * @return 结果
     */
    public int updateFinanceAaProjectContent(FinanceAaProjectContent financeAaProjectContent);

    /**
     * 删除集团管理-报表内容资料
     * 
     * @param id 集团管理-报表内容资料主键
     * @return 结果
     */
    public int deleteFinanceAaProjectContentById(String id);

    /**
     * 批量删除集团管理-报表内容资料
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFinanceAaProjectContentByIds(String[] ids);

    /**
     * 捡核资料是否重复
     * @param financeAaProjectContent
     * @return
     */
    public int updateCheckProjectContent(FinanceAaProjectContent financeAaProjectContent);

}
