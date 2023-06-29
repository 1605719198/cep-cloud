package com.jlkj.finance.ao.mapper;

import com.jlkj.finance.ao.dto.FinanceAoBudgetDto;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 预算设置Mapper接口
 * 
 * @author jlkj
 * @date 2023-06-16
 */
@Repository
public interface FinanceAoBudgetMapper 
{
    /**
     * 查询预算设置
     * 
     * @param id 预算设置主键
     * @return 预算设置
     */
    public FinanceAoBudgetDto selectFinanceAoBudgetById(String id);
    /**
     * 查询预算设置
     *
     * @param id 预算设置主键
     * @return 预算设置
     */
    public FinanceAoBudgetDto selectFinanceAoBudgetDtoById(String id);
    /**
     * 查询预算设置
     *
     * @param financeAoBudgetDto 预算设置主键
     * @return 预算设置
     */
    public  FinanceAoBudgetDto selectFinanceAoBudgetDtoMonth(FinanceAoBudgetDto financeAoBudgetDto);
    /**
     * 查询预算设置
     *
     * @param financeAoBudgetDto 预算设置主键
     * @return 预算设置
     */
    public  FinanceAoBudgetDto selectMonth(FinanceAoBudgetDto financeAoBudgetDto);

    /**
     * 查询预算设置列表
     * 
     * @param financeAoBudgetDto 预算设置
     * @return 预算设置集合
     */
    public List<FinanceAoBudgetDto> selectFinanceAoBudgetList(FinanceAoBudgetDto financeAoBudgetDto);
    /**
     * 查询预算设置列表
     *
     * @param financeAoBudgetDto 预算设置
     * @return 预算设置集合
     */
    public List<FinanceAoBudgetDto>selectDuplicateQuery(FinanceAoBudgetDto financeAoBudgetDto);

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
     * 删除预算设置
     *
     * @param list 预算设置
     * @return 结果
     */
    public int deleteFinanceAoBudgetList(@Param("list") List<FinanceAoBudgetDto> list);
    /**
     * 删除预算设置
     * 
     * @param id 预算设置主键
     * @return 结果
     */
    public int deleteFinanceAoBudgetById(String id);

    /**
     * 批量删除预算设置
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFinanceAoBudgetByIds(String[] ids);
}
