package com.jlkj.human.hp.mapper;

import com.jlkj.human.hp.domain.SysDept;

import java.util.List;

/**
 * 部门资料维护Mapper接口
 * 
 * @author jlkj
 * @date 2023-03-08
 */
public interface SysDeptMapper 
{
    /**
     * 查询部门资料维护
     * 
     * @param deptId 部门资料维护主键
     * @return 部门资料维护
     */
    public SysDept selectSysDeptByDeptId(Long deptId);
    /**
     * 根据部门ID查询信息
     *
     * @param deptId 部门ID
     * @return 部门信息
     */
    public SysDept selectDeptById(Long deptId);
    /**
     * 根据部门编码查询信息
     *
     * @param deptCode 部门编码
     * @return 部门信息
     */
    public SysDept selectSysDeptByDeptCode(String deptCode);

    /**
     * 查询部门资料维护列表
     * 
     * @param sysDept 部门资料维护
     * @return 部门资料维护集合
     */
    public List<SysDept> selectSysDeptList(SysDept sysDept);

    /**
     * 新增部门资料维护
     * 
     * @param sysDept 部门资料维护
     * @return 结果
     */
    public int insertSysDept(SysDept sysDept);

    /**
     * 修改部门资料维护
     * 
     * @param sysDept 部门资料维护
     * @return 结果
     */
    public int updateSysDept(SysDept sysDept);

    /**
     * 删除部门资料维护
     * 
     * @param deptId 部门资料维护主键
     * @return 结果
     */
    public int deleteSysDeptByDeptId(Long deptId);

    /**
     * 批量删除部门资料维护
     * 
     * @param deptIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysDeptByDeptIds(Long[] deptIds);
}
