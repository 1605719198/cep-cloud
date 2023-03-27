package com.jlkj.human.hp.service;

import com.jlkj.human.hp.domain.SysDept;
import com.jlkj.human.hp.domain.vo.TreeSelect;
import com.jlkj.human.hp.dto.CopySysDept;
import com.jlkj.human.hp.dto.DeptUnionPost;

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
     * 查询部门岗位资料维护列表
     *
     * @param deptpost 部门岗位资料维护
     * @return 部门岗位资料集合
     */
    public List<DeptUnionPost> selectDeptPostList(DeptUnionPost deptpost);

    /**
     * 查询公司列表
     *
     * @return 公司列表资料集合
     */
    public List<SysDept> selectCompanyList();
    /**
     * 构建前端所需要树结构
     *
     * @param depts 部门列表
     * @return 树结构列表
     */
    public List<SysDept> buildDeptTree(List<SysDept> depts);

    /**
     * 构建前端所需要下拉树结构
     *
     * @param depts 部门列表
     * @return 下拉树结构列表
     */
    public List<TreeSelect> buildDeptTreeSelect(List<SysDept> depts);

    /**
     * 构建前端所需要树结构
     *
     * @param deptpostList 部门列表
     * @return 树结构列表
     */
    public List<DeptUnionPost> buildDeptPostTree(List<DeptUnionPost> deptpostList);

    /**
     * 构建前端所需要下拉树结构
     *
     * @param deptpostList 部门列表
     * @return 下拉树结构列表
     */
    public List<TreeSelect> buildDeptPostTreeSelect(List<DeptUnionPost> deptpostList);

    /**
     * 根据部门ID查询信息
     *
     * @param deptId 部门ID
     * @return 部门信息
     */
    public SysDept selectDeptById(Long deptId);


    /**
     * 新增部门资料维护
     * 
     * @param sysDept 部门资料维护
     * @return 结果
     */

    public int insertSysDept(SysDept sysDept)throws Exception;

    /**
     * 复制组织机构设定
     *
     * @param copySysDept 假别参数复制设定
     * @return 结果
     */
    public int copySysDept (CopySysDept copySysDept) throws Exception;

    /**
     * 修改部门资料维护
     * 
     * @param sysDept 部门资料维护
     * @return 结果
     */
    public int updateSysDept(SysDept sysDept) throws Exception;

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
