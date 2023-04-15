package com.jlkj.finance.aa.service.impl;

import java.util.List;
import java.util.UUID;

import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.system.api.domain.SysUser;
import com.jlkj.system.api.model.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jlkj.finance.aa.mapper.FinanceCalSysRuleMapper;
import com.jlkj.finance.aa.domain.FinanceCalSysRule;
import com.jlkj.finance.aa.service.IFinanceCalSysRuleService;

/**
 * 核算项目-系统设定Service业务层处理
 *
 * @author 116514
 * @date 2023-04-12
 */
@Service
public class FinanceCalSysRuleServiceImpl implements IFinanceCalSysRuleService
{
    @Autowired
    private FinanceCalSysRuleMapper financeCalSysRuleMapper;

    /**
     * 查询核算项目-系统设定
     *
     * @param id 核算项目-系统设定主键
     * @return 核算项目-系统设定
     */
    @Override
    public FinanceCalSysRule selectFinanceCalSysRuleById(String id)
    {
        return financeCalSysRuleMapper.selectFinanceCalSysRuleById(id);
    }

    /**
     * 查询核算项目-系统设定列表
     *
     * @param financeCalSysRule 核算项目-系统设定
     * @return 核算项目-系统设定
     */
    @Override
    public List<FinanceCalSysRule> selectFinanceCalSysRuleList(FinanceCalSysRule financeCalSysRule)
    {
        return financeCalSysRuleMapper.selectFinanceCalSysRuleList(financeCalSysRule);
    }

    /**
     * 新增核算项目-系统设定
     *
     * @param financeCalSysRule 核算项目-系统设定
     * @return 结果
     */
    @Override
    public int insertFinanceCalSysRule(FinanceCalSysRule financeCalSysRule)
    {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        SysUser user = loginUser.getSysUser();
        financeCalSysRule.setId(UUID.randomUUID().toString());
        financeCalSysRule.setCreateBy(user.getUserName());
        financeCalSysRule.setCreateName(user.getNickName());
        financeCalSysRule.setCreateTime(DateUtils.getNowDate());
        return financeCalSysRuleMapper.insertFinanceCalSysRule(financeCalSysRule);
    }

    /**
     * 修改核算项目-系统设定
     *
     * @param financeCalSysRule 核算项目-系统设定
     * @return 结果
     */
    @Override
    public int updateFinanceCalSysRule(FinanceCalSysRule financeCalSysRule)
    {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        SysUser user = loginUser.getSysUser();
        financeCalSysRule.setUpdateBy(user.getUserName());
        financeCalSysRule.setUpdateName(user.getNickName());
        financeCalSysRule.setUpdateTime(DateUtils.getNowDate());
        return financeCalSysRuleMapper.updateFinanceCalSysRule(financeCalSysRule);
    }

    /**
     * 批量删除核算项目-系统设定
     *
     * @param ids 需要删除的核算项目-系统设定主键
     * @return 结果
     */
    @Override
    public int deleteFinanceCalSysRuleByIds(String[] ids)
    {
        return financeCalSysRuleMapper.deleteFinanceCalSysRuleByIds(ids);
    }

    /**
     * 删除核算项目-系统设定信息
     *
     * @param id 核算项目-系统设定主键
     * @return 结果
     */
    @Override
    public int deleteFinanceCalSysRuleById(String id)
    {
        return financeCalSysRuleMapper.deleteFinanceCalSysRuleById(id);
    }
}
