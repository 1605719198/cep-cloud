package com.jlkj.human.hs.mapper;

import com.jlkj.human.hs.domain.AccountRules;
import com.jlkj.human.hs.domain.SalaryProjectBasis;

import java.util.List;

/**
 * 抛帐规则维护Mapper接口
 * 
 * @author 116519
 * @date 2023-05-22
 */
public interface AccountRulesMapper 
{
    /**
     * 查询抛帐规则维护
     * 
     * @param id 抛帐规则维护主键
     * @return 抛帐规则维护
     */
    public AccountRules selectAccountRulesById(String id);

    /**
     * 查询抛帐规则维护列表
     * 
     * @param accountRules 抛帐规则维护
     * @return 抛帐规则维护集合
     */
    public List<AccountRules> selectAccountRulesList(AccountRules accountRules);

    /**
     * 新增抛帐规则维护
     * 
     * @param accountRules 抛帐规则维护
     * @return 结果
     */
    public int insertAccountRules(AccountRules accountRules);

    /**
     * 修改抛帐规则维护
     * 
     * @param accountRules 抛帐规则维护
     * @return 结果
     */
    public int updateAccountRules(AccountRules accountRules);

    /**
     * 删除抛帐规则维护
     * 
     * @param id 抛帐规则维护主键
     * @return 结果
     */
    public int deleteAccountRulesById(String id);

    /**
     * 批量删除抛帐规则维护
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteAccountRulesByIds(String[] ids);

    /**
     * 查询公司薪酬项目表树
     *
     * @param compId 公司
     * @return 抛帐规则维护集合
     */
    public  List<SalaryProjectBasis> getPayTable(String  compId);
}
