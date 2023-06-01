package com.jlkj.human.hs.service;

import com.jlkj.human.hs.domain.SalaryEmployedMonth;

import java.util.List;

/**
 * 员工受雇月维护Service接口
 * 
 * @author 267383
 * @date 2023-05-30
 */
public interface ISalaryEmployedMonthService 
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
     * @param salaryEmployedMonthList 员工受雇月维护
     * @return 结果
     */
    public int insertSalaryEmployedMonth(List<SalaryEmployedMonth> salaryEmployedMonthList);

    /**
     * 修改员工受雇月维护
     * 
     * @param salaryEmployedMonth 员工受雇月维护
     * @return 结果
     */
    public int updateSalaryEmployedMonth(SalaryEmployedMonth salaryEmployedMonth);

    /**
     * 批量删除员工受雇月维护
     * 
     * @param ids 需要删除的员工受雇月维护主键集合
     * @return 结果
     */
    public int deleteSalaryEmployedMonthByIds(String[] ids);

    /**
     * 删除员工受雇月维护信息
     * 
     * @param id 员工受雇月维护主键
     * @return 结果
     */
    public int deleteSalaryEmployedMonthById(String id);

    /**
     * 导入员工受雇月维护资料数据
     *
     * @param salaryEmployedMonthList 员工受雇月维护数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    public String importUser(List<SalaryEmployedMonth> salaryEmployedMonthList, Boolean isUpdateSupport, String operName);
}
