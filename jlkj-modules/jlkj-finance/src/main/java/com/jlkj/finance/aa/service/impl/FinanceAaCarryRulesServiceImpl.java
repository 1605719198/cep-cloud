package com.jlkj.finance.aa.service.impl;

import java.util.List;
import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.common.core.utils.uuid.IdUtils;
import com.jlkj.common.security.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jlkj.finance.aa.mapper.FinanceAaCarryRulesMapper;
import com.jlkj.finance.aa.domain.FinanceAaCarryRules;
import com.jlkj.finance.aa.service.IFinanceAaCarryRulesService;

/**
 * 集团管理-自动结转规则-规则设置Service业务层处理
 *
 * @author 116514
 * @date 2023-04-25
 */
@Service
public class FinanceAaCarryRulesServiceImpl implements IFinanceAaCarryRulesService
{
    @Autowired
    private FinanceAaCarryRulesMapper financeAaCarryRulesMapper;

    /**
     * 查询集团管理-自动结转规则-规则设置
     *
     * @param id 集团管理-自动结转规则-规则设置主键
     * @return 集团管理-自动结转规则-规则设置
     */
    @Override
    public FinanceAaCarryRules selectFinanceAaCarryRulesById(String id)
    {
        return financeAaCarryRulesMapper.selectFinanceAaCarryRulesById(id);
    }

    /**
     * 查询集团管理-自动结转规则-规则设置列表
     *
     * @param financeAaCarryRules 集团管理-自动结转规则-规则设置
     * @return 集团管理-自动结转规则-规则设置
     */
    @Override
    public List<FinanceAaCarryRules> selectFinanceAaCarryRulesList(FinanceAaCarryRules financeAaCarryRules)
    {
        return financeAaCarryRulesMapper.selectFinanceAaCarryRulesList(financeAaCarryRules);
    }

    /**
     * 新增集团管理-自动结转规则-规则设置
     *
     * @param financeAaCarryRules 集团管理-自动结转规则-规则设置
     * @return 结果
     */
    @Override
    public int insertFinanceAaCarryRules(FinanceAaCarryRules financeAaCarryRules)
    {
        financeAaCarryRules.setId(IdUtils.simpleUUID());
        financeAaCarryRules.setCreateBy(SecurityUtils.getUsername());
        financeAaCarryRules.setCreateName(SecurityUtils.getNickName());
        financeAaCarryRules.setCreateTime(DateUtils.getNowDate());
        return financeAaCarryRulesMapper.insertFinanceAaCarryRules(financeAaCarryRules);
    }

    /**
     * 修改集团管理-自动结转规则-规则设置
     *
     * @param financeAaCarryRules 集团管理-自动结转规则-规则设置
     * @return 结果
     */
    @Override
    public int updateFinanceAaCarryRules(FinanceAaCarryRules financeAaCarryRules)
    {
        financeAaCarryRules.setUpdateBy(SecurityUtils.getUsername());
        financeAaCarryRules.setUpdateName(SecurityUtils.getNickName());
        financeAaCarryRules.setUpdateTime(DateUtils.getNowDate());
        return financeAaCarryRulesMapper.updateFinanceAaCarryRules(financeAaCarryRules);
    }

    /**
     * 批量删除集团管理-自动结转规则-规则设置
     *
     * @param ids 需要删除的集团管理-自动结转规则-规则设置主键
     * @return 结果
     */
    @Override
    public int deleteFinanceAaCarryRulesByIds(String[] ids)
    {
        return financeAaCarryRulesMapper.deleteFinanceAaCarryRulesByIds(ids);
    }

    /**
     * 删除集团管理-自动结转规则-规则设置信息
     *
     * @param id 集团管理-自动结转规则-规则设置主键
     * @return 结果
     */
    @Override
    public int deleteFinanceAaCarryRulesById(String id)
    {
        return financeAaCarryRulesMapper.deleteFinanceAaCarryRulesById(id);
    }
}
