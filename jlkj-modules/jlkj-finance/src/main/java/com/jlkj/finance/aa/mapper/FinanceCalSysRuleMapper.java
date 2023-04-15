package com.jlkj.finance.aa.mapper;

import java.util.List;
import com.jlkj.finance.aa.domain.FinanceCalSysRule;

/**
 * 核算项目-系统设定Mapper接口
 * 
 * @author 116514
 * @date 2023-04-12
 */
public interface FinanceCalSysRuleMapper 
{
    /**
     * 查询核算项目-系统设定
     * 
     * @param id 核算项目-系统设定主键
     * @return 核算项目-系统设定
     */
    public FinanceCalSysRule selectFinanceCalSysRuleById(String id);

    /**
     * 查询核算项目-系统设定列表
     * 
     * @param financeCalSysRule 核算项目-系统设定
     * @return 核算项目-系统设定集合
     */
    public List<FinanceCalSysRule> selectFinanceCalSysRuleList(FinanceCalSysRule financeCalSysRule);

    /**
     * 新增核算项目-系统设定
     * 
     * @param financeCalSysRule 核算项目-系统设定
     * @return 结果
     */
    public int insertFinanceCalSysRule(FinanceCalSysRule financeCalSysRule);

    /**
     * 修改核算项目-系统设定
     * 
     * @param financeCalSysRule 核算项目-系统设定
     * @return 结果
     */
    public int updateFinanceCalSysRule(FinanceCalSysRule financeCalSysRule);

    /**
     * 删除核算项目-系统设定
     * 
     * @param id 核算项目-系统设定主键
     * @return 结果
     */
    public int deleteFinanceCalSysRuleById(String id);

    /**
     * 批量删除核算项目-系统设定
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFinanceCalSysRuleByIds(String[] ids);
}
