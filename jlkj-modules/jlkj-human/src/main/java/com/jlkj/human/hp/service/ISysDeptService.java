package com.jlkj.human.hp.service;

import com.jlkj.human.hp.domain.SysDept;
import com.jlkj.human.hp.domain.vo.TreeSelect;

import java.util.List;

/**
 * 部门资料维护Service接口
 * 
 * @author jlkj
 * @date 2023-03-08
 */
public interface ISysDeptService 
{
    /**
     * 查询部门资料维护
     * 
     * @param deptId 部门资料维护主键
     * @return 部门资料维护
     */
    public SysDept selectSysDeptByDeptId(Long deptId);

    /**
     * 查询部门资料维护列表
     * 
     * @param sysDept 部门资料维护
     * @return 部门资料维护集合
     */
    public List<SysDept> selectSysDeptList(SysDept sysDept);
    /**
     * 构建前端所需要树结构
     *
     * @param depts 部门列表
     * @return 树结构列表
     */
    public List<SysDept> buildDeptTree(List<SysDept> depts);
    /**
     * 根据部门ID查询信息
     *
     * @param deptId 部门ID
     * @return 部门信息
     */
    public SysDept selectDeptById(Long deptId);

    /**
     * 构建前端所需要下拉树结构
     *
     * @param depts 部门列表
     * @return 下拉树结构列表
     */
    public List<TreeSelect> buildDeptTreeSelect(List<SysDept> depts);

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
     * 批量删除部门资料维护
     * 
     * @param deptIds 需要删除的部门资料维护主键集合
     * @return 结果
     */
    public int deleteSysDeptByDeptIds(Long[] deptIds);

    /**
     * 删除部门资料维护信息
     * 
     * @param deptId 部门资料维护主键
     * @return 结果
     */
    public int deleteSysDeptByDeptId(Long deptId);
}
