package com.jlkj.finance.aa.service.impl;

import com.jlkj.common.core.exception.ServiceException;
import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.common.core.utils.uuid.IdUtils;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.finance.aa.domain.FinanceAaRate;
import com.jlkj.finance.aa.mapper.FinanceAaRateMapper;
import com.jlkj.finance.aa.service.IFinanceAaRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 利率汇率管理Service业务层处理
 *
 * @author SunXuTong
 * @date 2023-04-17
 */
@Service
public class FinanceAaRateServiceImpl implements IFinanceAaRateService
{
    @Autowired
    private FinanceAaRateMapper financeAaRateMapper;

    /**
     * 查询利率汇率管理
     *
     * @param id 利率汇率管理主键
     * @return 利率汇率管理
     */
    @Override
    public FinanceAaRate selectFinanceAaRateById(String id)
    {
        return financeAaRateMapper.selectFinanceAaRateById(id);
    }

    /**
     * 查询利率汇率管理列表
     *
     * @param financeAaRate 利率汇率管理
     * @return 利率汇率管理
     */
    @Override
    public List<FinanceAaRate> selectFinanceAaRateList(FinanceAaRate financeAaRate)
    {
        return financeAaRateMapper.selectFinanceAaRateList(financeAaRate);
    }

    /**
     * 新增利率汇率管理
     *
     * @param financeAaRate 利率汇率管理
     * @return 结果
     */
    @Override
    public int insertFinanceAaRate(FinanceAaRate financeAaRate)
    {
        List<FinanceAaRate> financeAaRates = financeAaRateMapper.selectFinanceAoParaByRateCate(financeAaRate);
        if (financeAaRates.isEmpty()){
            financeAaRate.setId(IdUtils.fastSimpleUUID());
            financeAaRate.setCreateTime(DateUtils.getNowDate());
            financeAaRate.setCreateBy(SecurityUtils.getUsername());
            financeAaRate.setCreateName(SecurityUtils.getNickName());
            return financeAaRateMapper.insertFinanceAaRate(financeAaRate);
        } else {
            throw new ServiceException("此日期、汇率类别、交易市场、币别已经存在，不可重复新增！");
        }
    }

    /**
     * 修改利率汇率管理
     *
     * @param financeAaRate 利率汇率管理
     * @return 结果
     */
    @Override
    public int updateFinanceAaRate(FinanceAaRate financeAaRate)
    {
        financeAaRate.setUpdateTime(DateUtils.getNowDate());
        financeAaRate.setUpdateBy(SecurityUtils.getUsername());
        financeAaRate.setUpdateName(SecurityUtils.getNickName());
        return financeAaRateMapper.updateFinanceAaRate(financeAaRate);
    }

    /**
     * 批量删除利率汇率管理
     *
     * @param ids 需要删除的利率汇率管理主键
     * @return 结果
     */
    @Override
    public int deleteFinanceAaRateByIds(String[] ids)
    {
        return financeAaRateMapper.deleteFinanceAaRateByIds(ids);
    }

    /**
     * 删除利率汇率管理信息
     *
     * @param id 利率汇率管理主键
     * @return 结果
     */
    @Override
    public int deleteFinanceAaRateById(String id)
    {
        return financeAaRateMapper.deleteFinanceAaRateById(id);
    }
}
