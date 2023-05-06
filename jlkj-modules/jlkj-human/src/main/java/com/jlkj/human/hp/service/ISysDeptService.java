package com.jlkj.human.hp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.human.hp.domain.SysDept;
import com.jlkj.human.hp.domain.vo.TreeSelect;
import com.jlkj.human.hp.dto.CopySysDeptDTO;
import com.jlkj.human.hp.dto.DeptUnionPostDTO;
import com.jlkj.human.hp.dto.FirstDeptDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * 部门资料维护Service接口
 * 
 * @author jlkj
 * @date 2023-03-08
 */
public interface ISysDeptService extends IService<SysDept>
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
    public List<DeptUnionPostDTO> selectDeptPostList(DeptUnionPostDTO deptpost);

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
    public List<DeptUnionPostDTO> buildDeptPostTree(List<DeptUnionPostDTO> deptpostList);

    /**
     * 构建前端所需要下拉树结构
     *
     * @param deptpostList 部门列表
     * @return 下拉树结构列表
     */
    public List<TreeSelect> buildDeptPostTreeSelect(List<DeptUnionPostDTO> deptpostList);

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
     * @throws Exception 抛出异常停止执行
     */

    public int insertSysDept(SysDept sysDept)throws Exception;

    /**
     * 复制组织机构设定
     *
     * @param copySysDeptDTO 假别参数复制设定
     * @return 结果
     * @throws Exception 抛出异常停止执行
     */
    public int copySysDept (CopySysDeptDTO copySysDeptDTO) throws Exception;

    /**
     * 修改部门资料维护
     * 
     * @param sysDept 部门资料维护
     * @return 结果
     * @throws Exception 抛出异常停止执行
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

    /**
     * 通过部门id查一级部门
     * @param empId 部门id
     * @return 结果
     */
    public FirstDeptDTO getFirstDeptByDept(String empId);

    /**
     * 根据部门编码查询信息
     *
     * @param deptCode 部门编码
     * @return 部门信息
     */
    public SysDept selectSysDeptByDeptCode(String deptCode);

    /**
     * 查询父id为此部门编码的数据
     *
     * @param deptId 部门id
     * @return 部门信息
     */
    public List<SysDept> selectParentIdByDeptCode(Long deptId);

    /**
     * 通过员工工号查一级部门
     * @param empId 员工工号
     * @return 结果
     */
    public FirstDeptDTO getFirstDeptByPerson(String empId);


    /**
     * 通过部门查员工
     * @param deptId 员工工号
     * @return 结果
     */
    public ArrayList<FirstDeptDTO> getPersonByDept(String deptId);
}
