package com.jlkj.finance.aa.service;

import com.jlkj.finance.aa.domain.FinanceAaUnit;

import java.util.List;

/**
 * 计量单位维护Service接口
 * 
 * @author SunXuTong
 * @date 2023-04-27
 */
public interface IFinanceAaUnitService 
{
    /**
     * 查询计量单位维护
     * 
     * @param id 计量单位维护主键
     * @return 计量单位维护
     */
    public FinanceAaUnit selectFinanceAaUnitById(String id);

    /**
     * 查询计量单位维护列表
     * 
     * @param financeAaUnit 计量单位维护
     * @return 计量单位维护集合
     */
    public List<FinanceAaUnit> selectFinanceAaUnitList(FinanceAaUnit financeAaUnit);

    /**
     * 新增计量单位维护
     * 
     * @param financeAaUnit 计量单位维护
     * @return 结果
     */
    public int insertFinanceAaUnit(FinanceAaUnit financeAaUnit);

    /**
     * 修改计量单位维护
     * 
     * @param financeAaUnit 计量单位维护
     * @return 结果
     */
    public int updateFinanceAaUnit(FinanceAaUnit financeAaUnit);

    /**
     * 批量删除计量单位维护
     * 
     * @param ids 需要删除的计量单位维护主键集合
     * @return 结果
     */
    public int deleteFinanceAaUnitByIds(String[] ids);

    /**
     * 删除计量单位维护信息
     * 
     * @param id 计量单位维护主键
     * @return 结果
     */
    public int deleteFinanceAaUnitById(String id);
}
