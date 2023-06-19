package com.jlkj.finance.ao.service.impl;

import java.util.List;
import com.jlkj.common.core.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jlkj.finance.ao.mapper.FinanceAoBudgetMapper;
import com.jlkj.finance.ao.domain.FinanceAoBudget;
import com.jlkj.finance.ao.service.IFinanceAoBudgetService;

/**
 * 预算设置Service业务层处理
 *
 * @author jlkj
 * @date 2023-06-16
 */
@Service
public class FinanceAoBudgetServiceImpl implements IFinanceAoBudgetService
{
    @Autowired
    private FinanceAoBudgetMapper financeAoBudgetMapper;

    /**
     * 查询预算设置
     *
     * @param id 预算设置主键
     * @return 预算设置
     */
    @Override
    public FinanceAoBudget selectFinanceAoBudgetById(String id)
    {
        return financeAoBudgetMapper.selectFinanceAoBudgetById(id);
    }

    /**
     * 查询预算设置列表
     *
     * @param financeAoBudget 预算设置
     * @return 预算设置
     */
    @Override
    public List<FinanceAoBudget> selectFinanceAoBudgetList(FinanceAoBudget financeAoBudget)
    {
        return financeAoBudgetMapper.selectFinanceAoBudgetList(financeAoBudget);
    }

    /**
     * 新增预算设置
     *
     * @param financeAoBudget 预算设置
     * @return 结果
     */
    @Override
    public int insertFinanceAoBudget(FinanceAoBudget financeAoBudget)
    {
        financeAoBudget.setCreateTime(DateUtils.getNowDate());
        return financeAoBudgetMapper.insertFinanceAoBudget(financeAoBudget);
    }

    /**
     * 修改预算设置
     *
     * @param financeAoBudget 预算设置
     * @return 结果
     */
    @Override
    public int updateFinanceAoBudget(FinanceAoBudget financeAoBudget)
    {
        financeAoBudget.setUpdateTime(DateUtils.getNowDate());
        return financeAoBudgetMapper.updateFinanceAoBudget(financeAoBudget);
    }

    /**
     * 批量删除预算设置
     *
     * @param ids 需要删除的预算设置主键
     * @return 结果
     */
    @Override
    public int deleteFinanceAoBudgetByIds(String[] ids)
    {
        return financeAoBudgetMapper.deleteFinanceAoBudgetByIds(ids);
    }

    /**
     * 删除预算设置信息
     *
     * @param id 预算设置主键
     * @return 结果
     */
    @Override
    public int deleteFinanceAoBudgetById(String id)
    {
        return financeAoBudgetMapper.deleteFinanceAoBudgetById(id);
    }
}
