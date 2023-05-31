package com.jlkj.human.hs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jlkj.human.hd.dto.BasisOptionsDTO;
import com.jlkj.human.hs.domain.SalaryProjectBasis;

import java.util.List;

/**
 * 集团级薪资项目输入维护Mapper接口
 * 
 * @author jiangbingchen
 * @date 2023-05-08
 */
public interface SalaryProjectBasisMapper extends BaseMapper<SalaryProjectBasis>
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
     * @param salaryProjectBasisList 集团级薪资项目输入维护
     * @return 结果
     */
    public int insertSalaryProjectBasis(List<SalaryProjectBasis> salaryProjectBasisList);

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
     * 删除集团级薪资项目输入维护
     * 
     * @param id 集团级薪资项目输入维护主键
     * @return 结果
     */
    public int deleteSalaryProjectBasisById(Long id);

    /**
     * 批量删除集团级薪资项目输入维护
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSalaryProjectBasisByIds(Long[] ids);

    /**
     * 通过父节点id查询员工出勤基本资料维护
     *
     * @param parentid 员工出勤基本资料维护编码
     * @return 员工出勤基本资料维护
     */
    public List<BasisOptionsDTO> selectSalaryByParentid(Long  parentid);
}
