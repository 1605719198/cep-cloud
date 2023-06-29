package com.jlkj.finance.aa.mapper;

import com.jlkj.finance.aa.domain.FinanceAaCarryRulesCorp;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 集团管理-自动结转规则-规则设置Mapper接口
 * 
 * @author 116514
 * @date 2023-04-25
 */
@Repository
public interface FinanceAaCarryRulesCorpMapper 
{
    /**
     * 查询集团管理-自动结转规则-规则设置
     * 
     * @param id 集团管理-自动结转规则-规则设置主键
     * @return 集团管理-自动结转规则-规则设置
     */
    public FinanceAaCarryRulesCorp selectFinanceAaCarryRulesCorpById(String id);
    /**
     * 接口查询
     *
     * @param rulesNo 集团管理-自动结转规则-规则设置
     * @param companyId 公司別
     * @return 集团管理-自动结转规则-规则设置集合
     */
    public FinanceAaCarryRulesCorp selectFinanceAaCarryRulesExecuteList(@Param("rulesNo")String rulesNo, @Param("companyId")String companyId);
    /**
     * 查询集团管理-自动结转规则-规则设置列表
     * 
     * @param financeAaCarryRulesCorp 集团管理-自动结转规则-规则设置
     * @return 集团管理-自动结转规则-规则设置集合
     */
    public List<FinanceAaCarryRulesCorp> selectFinanceAaCarryRulesCorpList(FinanceAaCarryRulesCorp financeAaCarryRulesCorp);

    /**
     * 新增集团管理-自动结转规则-规则设置
     * 
     * @param financeAaCarryRulesCorp 集团管理-自动结转规则-规则设置
     * @return 结果
     */
    public int insertFinanceAaCarryRulesCorp(FinanceAaCarryRulesCorp financeAaCarryRulesCorp);

    /**
     * 修改集团管理-自动结转规则-规则设置
     * 
     * @param financeAaCarryRulesCorp 集团管理-自动结转规则-规则设置
     * @return 结果
     */
    public int updateFinanceAaCarryRulesCorp(FinanceAaCarryRulesCorp financeAaCarryRulesCorp);

    /**
     * 删除集团管理-自动结转规则-规则设置
     * 
     * @param id 集团管理-自动结转规则-规则设置主键
     * @return 结果
     */
    public int deleteFinanceAaCarryRulesCorpById(String id);

    /**
     * 批量删除集团管理-自动结转规则-规则设置
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFinanceAaCarryRulesCorpByIds(String[] ids);
}
