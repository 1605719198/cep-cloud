package com.jlkj.human.hs.service.impl;

import com.jlkj.common.core.utils.uuid.IdUtils;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.human.hs.domain.AccountRules;
import com.jlkj.human.hs.domain.SalaryProjectBasis;
import com.jlkj.human.hs.mapper.AccountRulesMapper;
import com.jlkj.human.hs.service.IAccountRulesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 抛帐规则维护Service业务层处理
 *
 * @author 116519
 * @date 2023-05-22
 */
@Service
public class AccountRulesServiceImpl implements IAccountRulesService
{
    @Autowired
    private AccountRulesMapper accountRulesMapper;

    /**
     * 查询抛帐规则维护
     *
     * @param id 抛帐规则维护主键
     * @return 抛帐规则维护
     */
    @Override
    public AccountRules selectAccountRulesById(String id)
    {
        return accountRulesMapper.selectAccountRulesById(id);
    }

    /**
     * 查询抛帐规则维护列表
     *
     * @param accountRules 抛帐规则维护
     * @return 抛帐规则维护
     */
    @Override
    public List<AccountRules> selectAccountRulesList(AccountRules accountRules)
    {
        return accountRulesMapper.selectAccountRulesList(accountRules);
    }

    /**
     * 新增抛帐规则维护
     *
     * @param accountRules 抛帐规则维护
     * @return 结果
     */
    @Override
    public int insertAccountRules(AccountRules accountRules)
    {
        return accountRulesMapper.insertAccountRules(accountRules);
    }

    /**
     * 修改抛帐规则维护
     *
     * @param accountRules 抛帐规则维护
     * @return 结果
     */
    @Override
    public int updateAccountRules(AccountRules accountRules)
    {
        return accountRulesMapper.updateAccountRules(accountRules);
    }

    /**
     * 批量删除抛帐规则维护
     *
     * @param ids 需要删除的抛帐规则维护主键
     * @return 结果
     */
    @Override
    public int deleteAccountRulesByIds(String[] ids)
    {
        return accountRulesMapper.deleteAccountRulesByIds(ids);
    }

    /**
     * 删除抛帐规则维护信息
     *
     * @param id 抛帐规则维护主键
     * @return 结果
     */
    @Override
    public int deleteAccountRulesById(String id)
    {
        return accountRulesMapper.deleteAccountRulesById(id);
    }


    /**
     * 新增抛帐设定
     *
     * @param accountRulesList 抛帐设定列表
     * @return 结果
     */
    @Override
    public int insertAccountRules(List<AccountRules> accountRulesList)
    {
        int count = 0;
        for(AccountRules accountRules :accountRulesList){
            if(accountRules.getId()!=null){
                accountRules.setCreatorId(SecurityUtils.getUserId().toString());
                accountRules.setCreatorNo(SecurityUtils.getUsername());
                accountRules.setCreator(SecurityUtils.getNickName());
                accountRules.setCreateDate(new Date());
                count= accountRulesMapper.updateAccountRules(accountRules);

            }else{
                accountRules.setId(IdUtils.simpleUUID());
                count= accountRulesMapper.insertAccountRules(accountRules);
            }
        }
        return count;
    }

    /**
     * 查询公司薪酬项目
     *
     * @param compId 抛帐规则维护
     * @return 抛帐规则维护
     */
    @Override
    public List<SalaryProjectBasis> selectSalaryProjectTableList(String  compId)
    {
        return accountRulesMapper.getPayTable(compId);
    }
}
