package com.jlkj.human.hs.mapper;

import com.jlkj.human.hs.domain.SalaryEmployedMonth;

import java.util.List;

/**
 * 员工受雇月维护Mapper接口
 * 
 * @author 267383
 * @date 2023-05-30
 */
public interface SalaryEmployedMonthMapper 
{
    /**
     * 查询员工受雇月维护
     * 
     * @param id 员工受雇月维护主键
     * @return 员工受雇月维护
     */
    public SalaryEmployedMonth selectSalaryEmployedMonthById(String id);

    /**
     * 查询员工受雇月维护列表
     * 
     * @param salaryEmployedMonth 员工受雇月维护
     * @return 员工受雇月维护集合
     */
    public List<SalaryEmployedMonth> selectSalaryEmployedMonthList(SalaryEmployedMonth salaryEmployedMonth);

    /**
     * 新增员工受雇月维护
     * 
     * @param salaryEmployedMonth 员工受雇月维护
     * @return 结果
     */
    public int insertSalaryEmployedMonth(SalaryEmployedMonth salaryEmployedMonth);

    /**
     * 修改员工受雇月维护
     * 
     * @param salaryEmployedMonth 员工受雇月维护
     * @return 结果
     */
    public int updateSalaryEmployedMonth(SalaryEmployedMonth salaryEmployedMonth);

    /**
     * 删除员工受雇月维护
     * 
     * @param id 员工受雇月维护主键
     * @return 结果
     */
    public int deleteSalaryEmployedMonthById(String id);

    /**
     * 批量删除员工受雇月维护
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSalaryEmployedMonthByIds(String[] ids);
}
