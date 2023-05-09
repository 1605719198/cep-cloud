package com.jlkj.human.hs.mapper;

import com.jlkj.human.hs.domain.SalaryBasis;

import java.util.List;

/**
 * 薪资资料Mapper接口
 * 
 * @author 266861
 * @date 2023-05-09
 */
public interface SalaryBasisMapper 
{
    /**
     * 查询薪资资料
     * 
     * @param id 薪资资料主键
     * @return 薪资资料
     */
    public SalaryBasis selectSalaryBasisById(Long id);

    /**
     * 查询薪资资料列表
     * 
     * @param salaryBasis 薪资资料
     * @return 薪资资料集合
     */
    public List<SalaryBasis> selectSalaryBasisList(SalaryBasis salaryBasis);

    /**
     * 查询是否有重复编码数据
     *
     * @param salaryBasis 员工薪资基本资料
     * @return 员工出勤基本资料维护
     */
    public int queryRepetitivedata(SalaryBasis salaryBasis);

    /**
     * 通过父节点id查询员工薪资基本资料维护
     *
     * @param parentid 员工薪资基本资料维护编码
     * @return 员工薪资基本资料维护
     */
    public List<SalaryBasis> selectSalaryBasisByParentid(Long  parentid);

    /**
     * 新增薪资资料
     * 
     * @param salaryBasis 薪资资料
     * @return 结果
     */
    public int insertSalaryBasis(SalaryBasis salaryBasis);

    /**
     * 修改薪资资料
     * 
     * @param salaryBasis 薪资资料
     * @return 结果
     */
    public int updateSalaryBasis(SalaryBasis salaryBasis);

    /**
     * 删除薪资资料
     * 
     * @param id 薪资资料主键
     * @return 结果
     */
    public int deleteSalaryBasisById(Long id);

    /**
     * 批量删除薪资资料
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSalaryBasisByIds(Long[] ids);
}
