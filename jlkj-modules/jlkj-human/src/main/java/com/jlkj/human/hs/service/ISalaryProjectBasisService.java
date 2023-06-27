package com.jlkj.human.hs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.human.hd.dto.BasisOptionsDTO;
import com.jlkj.human.hp.domain.vo.TreeSelect;
import com.jlkj.human.hs.domain.SalaryProjectBasis;

import java.util.List;

/**
 * 集团级薪资项目输入维护Service接口
 * 
 * @author jiangbingchen
 * @date 2023-05-08
 */
public interface ISalaryProjectBasisService extends IService<SalaryProjectBasis>
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
     * 构建前端所需要树结构
     *
     * @param salaryProjectBasiss 列表
     * @return 树结构列表
     */
    public List<SalaryProjectBasis> buildSalaryProjectTree(List<SalaryProjectBasis> salaryProjectBasiss);

    /**
     * 构建前端所需要下拉树结构
     *
     * @param salaryProjectBasiss 列表
     * @return 下拉树结构列表
     */
    public List<TreeSelect> buildSalaryProjectTreeSelect(List<SalaryProjectBasis> salaryProjectBasiss);

    /**
     * 新增集团级薪资项目输入维护
     * 
     * @param salaryProjectBasisList 集团级薪资项目输入维护
     * @return 结果
     */
    public Object insertSalaryProjectBasis(List<SalaryProjectBasis> salaryProjectBasisList);

    /**
     * 修改集团级薪资项目输入维护
     * 
     * @param salaryProjectBasis 集团级薪资项目输入维护
     * @return 结果
     */
    public int updateSalaryProjectBasis(SalaryProjectBasis salaryProjectBasis);

    /**
     * 通过父节点id查询员工薪资基本资料维护
     *
     * @param parentid 员工薪资基本资料维护编码
     * @return 员工薪资基本资料维护
     */
    public List<BasisOptionsDTO> selectSalaryProjectBasisByParentid(Long  parentid);

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
