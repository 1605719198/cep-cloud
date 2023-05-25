package com.jlkj.finance.ap.service;

import com.jlkj.finance.ap.domain.FinanceApPara;

import java.util.List;

/**
 * 应付参数维护Service接口
 * 
 * @author SunXuTong
 * @date 2023-05-15
 */
public interface IFinanceApParaService 
{
    /**
     * 查询应付参数维护
     * 
     * @param id 应付参数维护主键
     * @return 应付参数维护
     */
    public FinanceApPara selectFinanceApParaById(String id);

    /**
     * 查询应付参数维护列表
     * 
     * @param financeApPara 应付参数维护
     * @return 应付参数维护集合
     */
    public List<FinanceApPara> selectFinanceApParaList(FinanceApPara financeApPara);

    /**
     * 新增应付参数维护
     * 
     * @param financeApPara 应付参数维护
     * @return 结果
     */
    public int insertFinanceApPara(FinanceApPara financeApPara);

    /**
     * 修改应付参数维护
     * 
     * @param financeApPara 应付参数维护
     * @return 结果
     */
    public int updateFinanceApPara(FinanceApPara financeApPara);

    /**
     * 批量删除应付参数维护
     * 
     * @param ids 需要删除的应付参数维护主键集合
     * @return 结果
     */
    public int deleteFinanceApParaByIds(String[] ids);

    /**
     * 删除应付参数维护信息
     * 
     * @param id 应付参数维护主键
     * @return 结果
     */
    public int deleteFinanceApParaById(String id);
}
