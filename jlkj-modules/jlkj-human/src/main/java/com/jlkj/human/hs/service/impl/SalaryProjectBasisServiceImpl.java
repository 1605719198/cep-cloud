package com.jlkj.human.hs.service.impl;

import com.jlkj.human.hs.domain.SalaryProjectBasis;
import com.jlkj.human.hs.mapper.SalaryProjectBasisMapper;
import com.jlkj.human.hs.service.ISalaryProjectBasisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 集团级薪资项目输入维护Service业务层处理
 *
 * @author jiangbingchen
 * @date 2023-05-08
 */
@Service
public class SalaryProjectBasisServiceImpl implements ISalaryProjectBasisService
{
    @Autowired
    private SalaryProjectBasisMapper salaryProjectBasisMapper;

    /**
     * 查询集团级薪资项目输入维护
     *
     * @param id 集团级薪资项目输入维护主键
     * @return 集团级薪资项目输入维护
     */
    @Override
    public SalaryProjectBasis selectSalaryProjectBasisById(Long id)
    {
        return salaryProjectBasisMapper.selectSalaryProjectBasisById(id);
    }

    /**
     * 查询集团级薪资项目输入维护列表
     *
     * @param salaryProjectBasis 集团级薪资项目输入维护
     * @return 集团级薪资项目输入维护
     */
    @Override
    public List<SalaryProjectBasis> selectSalaryProjectBasisList(SalaryProjectBasis salaryProjectBasis)
    {
        return salaryProjectBasisMapper.selectSalaryProjectBasisList(salaryProjectBasis);
    }

    /**
     * 新增集团级薪资项目输入维护
     *
     * @param salaryProjectBasis 集团级薪资项目输入维护
     * @return 结果
     */
    @Override
    public int insertSalaryProjectBasis(SalaryProjectBasis salaryProjectBasis)
    {
        return salaryProjectBasisMapper.insertSalaryProjectBasis(salaryProjectBasis);
    }

    /**
     * 修改集团级薪资项目输入维护
     *
     * @param salaryProjectBasis 集团级薪资项目输入维护
     * @return 结果
     */
    @Override
    public int updateSalaryProjectBasis(SalaryProjectBasis salaryProjectBasis)
    {
        return salaryProjectBasisMapper.updateSalaryProjectBasis(salaryProjectBasis);
    }

    /**
     * 批量删除集团级薪资项目输入维护
     *
     * @param ids 需要删除的集团级薪资项目输入维护主键
     * @return 结果
     */
    @Override
    public int deleteSalaryProjectBasisByIds(Long[] ids)
    {
        return salaryProjectBasisMapper.deleteSalaryProjectBasisByIds(ids);
    }

    /**
     * 删除集团级薪资项目输入维护信息
     *
     * @param id 集团级薪资项目输入维护主键
     * @return 结果
     */
    @Override
    public int deleteSalaryProjectBasisById(Long id)
    {
        return salaryProjectBasisMapper.deleteSalaryProjectBasisById(id);
    }
}
