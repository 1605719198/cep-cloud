package com.jlkj.finance.aa.service;

import java.util.List;
import com.jlkj.finance.aa.domain.FinanceAaCarryRulesCorp;

/**
 * 总账管理-自动结转规则-规则设置Service接口
 * 
 * @author 116514
 * @date 2023-04-25
 */
public interface IFinanceAaCarryRulesCorpService 
{
    /**
     * 查询总账管理-自动结转规则-规则设置
     * 
     * @param id 总账管理-自动结转规则-规则设置主键
     * @return 总账管理-自动结转规则-规则设置
     */
    public FinanceAaCarryRulesCorp selectFinanceAaCarryRulesCorpById(String id);

    /**
     * 查询总账管理-自动结转规则-规则设置列表
     * 
     * @param financeAaCarryRulesCorp 总账管理-自动结转规则-规则设置
     * @return 总账管理-自动结转规则-规则设置集合
     */
    public List<FinanceAaCarryRulesCorp> selectFinanceAaCarryRulesCorpList(FinanceAaCarryRulesCorp financeAaCarryRulesCorp);

    /**
     * 新增总账管理-自动结转规则-规则设置
     * 
     * @param financeAaCarryRulesCorp 总账管理-自动结转规则-规则设置
     * @return 结果
     */
    public int insertFinanceAaCarryRulesCorp(FinanceAaCarryRulesCorp financeAaCarryRulesCorp);

    /**
     * 修改总账管理-自动结转规则-规则设置
     * 
     * @param financeAaCarryRulesCorp 总账管理-自动结转规则-规则设置
     * @return 结果
     */
    public int updateFinanceAaCarryRulesCorp(FinanceAaCarryRulesCorp financeAaCarryRulesCorp);

    /**
     * 批量删除总账管理-自动结转规则-规则设置
     * 
     * @param ids 需要删除的总账管理-自动结转规则-规则设置主键集合
     * @return 结果
     */
    public int deleteFinanceAaCarryRulesCorpByIds(String[] ids);

    /**
     * 删除总账管理-自动结转规则-规则设置信息
     * 
     * @param id 总账管理-自动结转规则-规则设置主键
     * @return 结果
     */
    public int deleteFinanceAaCarryRulesCorpById(String id);
}
