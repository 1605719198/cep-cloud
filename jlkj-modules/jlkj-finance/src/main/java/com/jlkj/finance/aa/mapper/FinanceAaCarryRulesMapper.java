package com.jlkj.finance.aa.mapper;

import java.util.List;
import com.jlkj.finance.aa.domain.FinanceAaCarryRules;

/**
 * 集团管理-自动结转规则-规则设置Mapper接口
 * 
 * @author 116514
 * @date 2023-04-25
 */
public interface FinanceAaCarryRulesMapper 
{
    /**
     * 查询集团管理-自动结转规则-规则设置
     * 
     * @param id 集团管理-自动结转规则-规则设置主键
     * @return 集团管理-自动结转规则-规则设置
     */
    public FinanceAaCarryRules selectFinanceAaCarryRulesById(String id);

    /**
     * 查询集团管理-自动结转规则-规则设置列表
     * 
     * @param financeAaCarryRules 集团管理-自动结转规则-规则设置
     * @return 集团管理-自动结转规则-规则设置集合
     */
    public List<FinanceAaCarryRules> selectFinanceAaCarryRulesList(FinanceAaCarryRules financeAaCarryRules);

    /**
     * 新增集团管理-自动结转规则-规则设置
     * 
     * @param financeAaCarryRules 集团管理-自动结转规则-规则设置
     * @return 结果
     */
    public int insertFinanceAaCarryRules(FinanceAaCarryRules financeAaCarryRules);

    /**
     * 修改集团管理-自动结转规则-规则设置
     * 
     * @param financeAaCarryRules 集团管理-自动结转规则-规则设置
     * @return 结果
     */
    public int updateFinanceAaCarryRules(FinanceAaCarryRules financeAaCarryRules);

    /**
     * 删除集团管理-自动结转规则-规则设置
     * 
     * @param id 集团管理-自动结转规则-规则设置主键
     * @return 结果
     */
    public int deleteFinanceAaCarryRulesById(String id);

    /**
     * 批量删除集团管理-自动结转规则-规则设置
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFinanceAaCarryRulesByIds(String[] ids);
}
