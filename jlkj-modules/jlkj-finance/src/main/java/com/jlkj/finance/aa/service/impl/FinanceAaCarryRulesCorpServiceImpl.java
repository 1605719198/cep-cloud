package com.jlkj.finance.aa.service.impl;

import java.util.List;
import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.common.core.utils.uuid.IdUtils;
import com.jlkj.common.security.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jlkj.finance.aa.mapper.FinanceAaCarryRulesCorpMapper;
import com.jlkj.finance.aa.domain.FinanceAaCarryRulesCorp;
import com.jlkj.finance.aa.service.IFinanceAaCarryRulesCorpService;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 集团管理-自动结转规则-规则设置Service业务层处理
 *
 * @author 116514
 * @date 2023-04-25
 */
@Service
public class FinanceAaCarryRulesCorpServiceImpl implements IFinanceAaCarryRulesCorpService
{
    @Autowired
    private FinanceAaCarryRulesCorpMapper financeAaCarryRulesCorpMapper;

    /**
     * 查询集团管理-自动结转规则-规则设置
     *
     * @param id 集团管理-自动结转规则-规则设置主键
     * @return 集团管理-自动结转规则-规则设置
     */
    @Override
    public FinanceAaCarryRulesCorp selectFinanceAaCarryRulesCorpById(String id)
    {
        return financeAaCarryRulesCorpMapper.selectFinanceAaCarryRulesCorpById(id);
    }

    /**
     * 查询集团管理-自动结转规则-规则设置列表
     *
     * @param financeAaCarryRulesCorp 集团管理-自动结转规则-规则设置
     * @return 集团管理-自动结转规则-规则设置
     */
    @Override
    public List<FinanceAaCarryRulesCorp> selectFinanceAaCarryRulesCorpList(FinanceAaCarryRulesCorp financeAaCarryRulesCorp)
    {
        return financeAaCarryRulesCorpMapper.selectFinanceAaCarryRulesCorpList(financeAaCarryRulesCorp);
    }

    /**
     * 新增集团管理-自动结转规则-规则设置
     *
     * @param financeAaCarryRulesCorp 集团管理-自动结转规则-规则设置
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.REPEATABLE_READ)
    public int insertFinanceAaCarryRulesCorp(FinanceAaCarryRulesCorp financeAaCarryRulesCorp)
    {
        financeAaCarryRulesCorp.setId(IdUtils.simpleUUID());
        financeAaCarryRulesCorp.setCreateBy(SecurityUtils.getUsername());
        financeAaCarryRulesCorp.setCreateName(SecurityUtils.getNickName());
        financeAaCarryRulesCorp.setCreateTime(DateUtils.getNowDate());
        return financeAaCarryRulesCorpMapper.insertFinanceAaCarryRulesCorp(financeAaCarryRulesCorp);
    }

    /**
     * 修改集团管理-自动结转规则-规则设置
     *
     * @param financeAaCarryRulesCorp 集团管理-自动结转规则-规则设置
     * @return 结果
     */
    @Override
    public int updateFinanceAaCarryRulesCorp(FinanceAaCarryRulesCorp financeAaCarryRulesCorp)
    {
        financeAaCarryRulesCorp.setUpdateBy(SecurityUtils.getUsername());
        financeAaCarryRulesCorp.setUpdateName(SecurityUtils.getNickName());
        financeAaCarryRulesCorp.setUpdateTime(DateUtils.getNowDate());
        return financeAaCarryRulesCorpMapper.updateFinanceAaCarryRulesCorp(financeAaCarryRulesCorp);
    }

    /**
     * 批量删除集团管理-自动结转规则-规则设置
     *
     * @param ids 需要删除的集团管理-自动结转规则-规则设置主键
     * @return 结果
     */
    @Override
    public int deleteFinanceAaCarryRulesCorpByIds(String[] ids)
    {
        return financeAaCarryRulesCorpMapper.deleteFinanceAaCarryRulesCorpByIds(ids);
    }

    /**
     * 删除集团管理-自动结转规则-规则设置信息
     *
     * @param id 集团管理-自动结转规则-规则设置主键
     * @return 结果
     */
    @Override
    public int deleteFinanceAaCarryRulesCorpById(String id)
    {
        return financeAaCarryRulesCorpMapper.deleteFinanceAaCarryRulesCorpById(id);
    }
}
