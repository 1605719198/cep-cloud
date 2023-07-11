package com.jlkj.human.hs.service;

import com.jlkj.human.hs.domain.AccountRules;
import com.jlkj.human.hs.domain.SalaryProjectBasis;

import java.util.List;

/**
 * 抛帐规则维护Service接口
 * 
 * @author 116519
 * @date 2023-05-22
 */
public interface IAccountRulesService 
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
     * 批量删除抛帐规则维护
     * 
     * @param ids 需要删除的抛帐规则维护主键集合
     * @return 结果
     */
    public int deleteAccountRulesByIds(String[] ids);

    /**
     * 删除抛帐规则维护信息
     * 
     * @param id 抛帐规则维护主键
     * @return 结果
     */
    public int deleteAccountRulesById(String id);

    /**
     * 抛帐规则新增
     * @param accountRulesList
     * @return
     */
    public int insertAccountRules(List<AccountRules> accountRulesList);
    /**
    * @Description 查询公司薪酬表项目
    * @Param No such property: code for class: Script1
    * @return
    * @Author 116519
    * @Date 2023-07-10 16:29
    **/
    public  List<SalaryProjectBasis> selectSalaryProjectTableList(String  compId);
}
