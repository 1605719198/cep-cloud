package com.jlkj.human.hs.service;

import com.jlkj.human.hs.domain.SalaryBasis;

import java.util.List;

/**
 * 薪资资料Service接口
 * 
 * @author 266861
 * @date 2023-05-09
 */
public interface ISalaryBasisService 
{
    /**
     * 查询薪资资料
     * 
     * @param id 薪资资料主键
     * @return 薪资资料
     */
    public SalaryBasis selectSalaryBasisById(Long id);

    /**
     * 查询是否有重复编码数据
     *
     * @param salaryBasis 员工薪资基本资料
     * @return 员工出勤基本资料维护
     */
    public int queryRepetitivedata(SalaryBasis salaryBasis);

    /**
     * 查询薪资资料列表
     * 
     * @param salaryBasis 薪资资料
     * @return 薪资资料集合
     */
    public List<SalaryBasis> selectSalaryBasisList(SalaryBasis salaryBasis);

    /**
     * 新增薪资资料
     * 
     * @param salaryBasis 薪资资料
     * @return 结果
     * @throws Exception 抛出异常停止执行
     */
    public int insertSalaryBasis(SalaryBasis salaryBasis)throws Exception;

    /**
     * 修改薪资资料
     * 
     * @param salaryBasis 薪资资料
     * @return 结果
     * @throws Exception 抛出异常停止执行
     */
    public int updateSalaryBasis(SalaryBasis salaryBasis)throws Exception;

    /**
     * 批量删除薪资资料
     * 
     * @param ids 需要删除的薪资资料主键集合
     * @return 结果
     */
    public int deleteSalaryBasisByIds(Long[] ids);

    /**
     * 删除薪资资料信息
     * 
     * @param id 薪资资料主键
     * @return 结果
     * @throws Exception 抛出异常停止执行
     */
    public int deleteSalaryBasisById(Long id)throws Exception;

    /**
     * 通过父节点id查询员工薪资基本资料维护
     *
     * @param parentid 员工薪资基本资料维护编码
     * @return 员工薪资基本资料维护
     */
    public List<SalaryBasis> selectSalaryBasisByParentid(Long  parentid);
}
