package com.jlkj.finance.ao.service;

import com.jlkj.finance.ao.domain.FinanceAoPara;

import java.util.List;

/**
 * 报支参数维护Service接口
 * 
 * @author SunXuTong
 * @date 2023-05-06
 */
public interface IFinanceAoParaService 
{
    /**
     * 查询报支参数维护
     * 
     * @param id 报支参数维护主键
     * @return 报支参数维护
     */
    public FinanceAoPara selectFinanceAoParaById(String id);

    /**
     * 查询报支参数维护列表
     * 
     * @param financeAoPara 报支参数维护
     * @return 报支参数维护集合
     */
    public List<FinanceAoPara> selectFinanceAoParaList(FinanceAoPara financeAoPara);

    /**
     * 新增报支参数维护
     * 
     * @param financeAoPara 报支参数维护
     * @return 结果
     */
    public int insertFinanceAoPara(FinanceAoPara financeAoPara);

    /**
     * 修改报支参数维护
     * 
     * @param financeAoPara 报支参数维护
     * @return 结果
     */
    public int updateFinanceAoPara(FinanceAoPara financeAoPara);

    /**
     * 批量删除报支参数维护
     * 
     * @param ids 需要删除的报支参数维护主键集合
     * @return 结果
     */
    public int deleteFinanceAoParaByIds(String[] ids);

    /**
     * 删除报支参数维护信息
     * 
     * @param id 报支参数维护主键
     * @return 结果
     */
    public int deleteFinanceAoParaById(String id);
}
