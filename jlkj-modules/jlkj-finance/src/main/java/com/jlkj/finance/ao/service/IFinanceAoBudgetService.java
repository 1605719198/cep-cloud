package com.jlkj.finance.ao.service;

import com.jlkj.finance.ao.dto.FinanceAoBudgetDto;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;

/**
 * 预算设置Service接口
 * 
 * @author jlkj
 * @date 2023-06-16
 */
public interface IFinanceAoBudgetService 
{
    /**
     * 查询预算设置
     * 
     * @param id 预算设置主键
     * @return 预算设置
     */
    public FinanceAoBudgetDto selectFinanceAoBudgetById(String id);

    /**
     * 查询预算设置列表
     * 
     * @param financeAoBudgetDto 预算设置
     * @return 预算设置集合
     */
    public List<FinanceAoBudgetDto> selectFinanceAoBudgetList(FinanceAoBudgetDto financeAoBudgetDto);

    /**
     * 新增预算设置
     * 
     * @param financeAoBudgetDto 预算设置
     * @return 结果
     */
    public int insertFinanceAoBudget(FinanceAoBudgetDto financeAoBudgetDto);

    /**
     * 修改预算设置
     * 
     * @param financeAoBudgetDto 预算设置
     * @return 结果
     */
    public int updateFinanceAoBudget(FinanceAoBudgetDto financeAoBudgetDto);

    /**
     * 批量删除预算设置
     * 
     * @param financeAoBudgetDto 需要删除的预算设置集合
     * @return 结果
     */
    public int deleteFinanceAoBudgetByIds (@Param("financeAoBudgetDto")List<FinanceAoBudgetDto> financeAoBudgetDto);

    /**
     * 删除预算设置信息
     * 
     * @param id 预算设置主键
     * @return 结果
     */
    public int deleteFinanceAoBudgetById(String id);

    /**
     * 导入预算设置
     *
     * @param financeAoBudgetDtos 预算设置列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    public String importFinanceAaVoucher(List<FinanceAoBudgetDto> financeAoBudgetDtos, Boolean isUpdateSupport, String operName);
}


