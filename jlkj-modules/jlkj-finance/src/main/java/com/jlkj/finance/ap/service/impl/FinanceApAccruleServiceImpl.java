package com.jlkj.finance.ap.service.impl;

import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.common.core.utils.uuid.IdUtils;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.finance.ap.domain.FinanceApAccrule;
import com.jlkj.finance.ap.mapper.FinanceApAccruleMapper;
import com.jlkj.finance.ap.service.IFinanceApAccruleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 付款抛账规则维护Service业务层处理
 *
 * @author SunXuTong
 * @date 2023-05-17
 */
@Service
public class FinanceApAccruleServiceImpl implements IFinanceApAccruleService
{
    @Autowired
    private FinanceApAccruleMapper financeApAccruleMapper;

    /**
     * 查询付款抛账规则维护
     *
     * @param id 付款抛账规则维护主键
     * @return 付款抛账规则维护
     */
    @Override
    public FinanceApAccrule selectFinanceApAccruleById(String id)
    {
        return financeApAccruleMapper.selectFinanceApAccruleById(id);
    }

    /**
     * 查询付款抛账规则维护列表
     *
     * @param financeApAccrule 付款抛账规则维护
     * @return 付款抛账规则维护
     */
    @Override
    public List<FinanceApAccrule> selectFinanceApAccruleList(FinanceApAccrule financeApAccrule)
    {
        return financeApAccruleMapper.selectFinanceApAccruleList(financeApAccrule);
    }

    /**
     * 新增付款抛账规则维护
     *
     * @param financeApAccrule 付款抛账规则维护
     * @return 结果
     */
    @Override
    public int insertFinanceApAccrule(FinanceApAccrule financeApAccrule)
    {
        financeApAccrule.setId(IdUtils.fastSimpleUUID());
        financeApAccrule.setCreateTime(DateUtils.getNowDate());
        financeApAccrule.setCreateBy(SecurityUtils.getUsername());
        financeApAccrule.setCreateName(SecurityUtils.getNickName());
        return financeApAccruleMapper.insertFinanceApAccrule(financeApAccrule);
    }

    /**
     * 修改付款抛账规则维护
     *
     * @param financeApAccrule 付款抛账规则维护
     * @return 结果
     */
    @Override
    public int updateFinanceApAccrule(FinanceApAccrule financeApAccrule)
    {
        financeApAccrule.setUpdateTime(DateUtils.getNowDate());
        financeApAccrule.setUpdateBy(SecurityUtils.getUsername());
        financeApAccrule.setUpdateName(SecurityUtils.getNickName());
        return financeApAccruleMapper.updateFinanceApAccrule(financeApAccrule);
    }

    /**
     * 批量删除付款抛账规则维护
     *
     * @param ids 需要删除的付款抛账规则维护主键
     * @return 结果
     */
    @Override
    public int deleteFinanceApAccruleByIds(String[] ids)
    {
        return financeApAccruleMapper.deleteFinanceApAccruleByIds(ids);
    }

    /**
     * 删除付款抛账规则维护信息
     *
     * @param id 付款抛账规则维护主键
     * @return 结果
     */
    @Override
    public int deleteFinanceApAccruleById(String id)
    {
        return financeApAccruleMapper.deleteFinanceApAccruleById(id);
    }
}
