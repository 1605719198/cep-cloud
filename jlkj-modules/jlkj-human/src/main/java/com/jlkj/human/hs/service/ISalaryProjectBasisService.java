package com.jlkj.human.hs.service;

import com.jlkj.human.hs.domain.SalaryProjectBasis;

import java.util.List;

/**
 * 集团级薪资项目输入维护Service接口
 * 
 * @author jiangbingchen
 * @date 2023-05-08
 */
public interface ISalaryProjectBasisService 
{
    /**
     * 查询集团级薪资项目输入维护
     * 
     * @param id 集团级薪资项目输入维护主键
     * @return 集团级薪资项目输入维护
     */
    public SalaryProjectBasis selectSalaryProjectBasisById(Long id);

    /**
     * 查询集团级薪资项目输入维护列表
     * 
     * @param salaryProjectBasis 集团级薪资项目输入维护
     * @return 集团级薪资项目输入维护集合
     */
    public List<SalaryProjectBasis> selectSalaryProjectBasisList(SalaryProjectBasis salaryProjectBasis);

    /**
     * 新增集团级薪资项目输入维护
     * 
     * @param salaryProjectBasis 集团级薪资项目输入维护
     * @return 结果
     */
    public int insertSalaryProjectBasis(SalaryProjectBasis salaryProjectBasis);

    /**
     * 修改集团级薪资项目输入维护
     * 
     * @param salaryProjectBasis 集团级薪资项目输入维护
     * @return 结果
     */
    public int updateSalaryProjectBasis(SalaryProjectBasis salaryProjectBasis);

    /**
     * 批量删除集团级薪资项目输入维护
     * 
     * @param ids 需要删除的集团级薪资项目输入维护主键集合
     * @return 结果
     */
    public int deleteSalaryProjectBasisByIds(Long[] ids);

    /**
     * 删除集团级薪资项目输入维护信息
     * 
     * @param id 集团级薪资项目输入维护主键
     * @return 结果
     */
    public int deleteSalaryProjectBasisById(Long id);
}
