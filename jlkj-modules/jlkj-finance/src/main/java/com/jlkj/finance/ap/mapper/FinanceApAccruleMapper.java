package com.jlkj.finance.ap.mapper;

import com.jlkj.finance.ap.domain.FinanceApAccrule;

import java.util.List;

/**
 * 付款抛账规则维护Mapper接口
 * 
 * @author SunXuTong
 * @date 2023-05-17
 */
public interface FinanceApAccruleMapper 
{
    /**
     * 查询付款抛账规则维护
     * 
     * @param id 付款抛账规则维护主键
     * @return 付款抛账规则维护
     */
    public FinanceApAccrule selectFinanceApAccruleById(String id);

    /**
     * 查询付款抛账规则维护列表
     * 
     * @param financeApAccrule 付款抛账规则维护
     * @return 付款抛账规则维护集合
     */
    public List<FinanceApAccrule> selectFinanceApAccruleList(FinanceApAccrule financeApAccrule);

    /**
     * 新增付款抛账规则维护
     * 
     * @param financeApAccrule 付款抛账规则维护
     * @return 结果
     */
    public int insertFinanceApAccrule(FinanceApAccrule financeApAccrule);

    /**
     * 查询付款抛账规则维护列表  查重
     *
     * @param financeApAccrule 付款抛账规则维护
     * @return 付款抛账规则维护集合
     */
    public List<FinanceApAccrule> selectFinanceAoParaByCompIdAndPayMethod(FinanceApAccrule financeApAccrule);

    /**
     * 修改付款抛账规则维护
     * 
     * @param financeApAccrule 付款抛账规则维护
     * @return 结果
     */
    public int updateFinanceApAccrule(FinanceApAccrule financeApAccrule);

    /**
     * 删除付款抛账规则维护
     * 
     * @param id 付款抛账规则维护主键
     * @return 结果
     */
    public int deleteFinanceApAccruleById(String id);

    /**
     * 批量删除付款抛账规则维护
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFinanceApAccruleByIds(String[] ids);
}
