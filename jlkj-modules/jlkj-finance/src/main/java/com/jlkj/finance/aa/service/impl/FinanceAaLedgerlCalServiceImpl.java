package com.jlkj.finance.aa.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jlkj.finance.aa.mapper.FinanceAaLedgerlCalMapper;
import com.jlkj.finance.aa.domain.FinanceAaLedgerlCal;
import com.jlkj.finance.aa.service.IFinanceAaLedgerlCalService;

/**
 * 账薄-核算项目余额Service业务层处理
 *
 * @author jlkj
 * @date 2023-04-29
 */
@Service
public class FinanceAaLedgerlCalServiceImpl implements IFinanceAaLedgerlCalService
{
    @Autowired
    private FinanceAaLedgerlCalMapper financeAaLedgerlCalMapper;

    /**
     * 查询账薄-核算项目余额
     *
     * @param id 账薄-核算项目余额主键
     * @return 账薄-核算项目余额
     */
    @Override
    public FinanceAaLedgerlCal selectFinanceAaLedgerlCalById(String id)
    {
        return financeAaLedgerlCalMapper.selectFinanceAaLedgerlCalById(id);
    }

    /**
     * 查询账薄-核算项目余额列表
     *
     * @param financeAaLedgerlCal 账薄-核算项目余额
     * @return 账薄-核算项目余额
     */
    @Override
    public List<FinanceAaLedgerlCal> selectFinanceAaLedgerlCalList(FinanceAaLedgerlCal financeAaLedgerlCal)
    {
        return financeAaLedgerlCalMapper.selectFinanceAaLedgerlCalList(financeAaLedgerlCal);
    }
    /**
     * 查询账薄-核算项目余额列表
     *
     * @param financeAaLedgerlCal 账薄-核算项目余额
     * @return 账薄-核算项目余额
     */
    @Override
    public FinanceAaLedgerlCal selectFinanceAaLedgerlCal(FinanceAaLedgerlCal financeAaLedgerlCal)
    {
        return financeAaLedgerlCalMapper.selectFinanceAaLedgerlCal(financeAaLedgerlCal);
    }
    /**
     * 新增账薄-核算项目余额
     *
     * @param financeAaLedgerlCal 账薄-核算项目余额
     * @return 结果
     */
    @Override
    public int insertFinanceAaLedgerlCal(FinanceAaLedgerlCal financeAaLedgerlCal)
    {
        return financeAaLedgerlCalMapper.insertFinanceAaLedgerlCal(financeAaLedgerlCal);
    }

    /**
     * 修改账薄-核算项目余额
     *
     * @param financeAaLedgerlCal 账薄-核算项目余额
     * @return 结果
     */
    @Override
    public int updateFinanceAaLedgerlCal(FinanceAaLedgerlCal financeAaLedgerlCal)
    {
        return financeAaLedgerlCalMapper.updateFinanceAaLedgerlCal(financeAaLedgerlCal);
    }

    /**
     * 批量删除账薄-核算项目余额
     *
     * @param ids 需要删除的账薄-核算项目余额主键
     * @return 结果
     */
    @Override
    public int deleteFinanceAaLedgerlCalByIds(String[] ids)
    {
        return financeAaLedgerlCalMapper.deleteFinanceAaLedgerlCalByIds(ids);
    }

    /**
     * 删除账薄-核算项目余额信息
     *
     * @param id 账薄-核算项目余额主键
     * @return 结果
     */
    @Override
    public int deleteFinanceAaLedgerlCalById(String id)
    {
        return financeAaLedgerlCalMapper.deleteFinanceAaLedgerlCalById(id);
    }
}
