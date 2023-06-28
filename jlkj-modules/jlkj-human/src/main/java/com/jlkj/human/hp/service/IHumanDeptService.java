package com.jlkj.human.hp.service;

import com.jlkj.human.hp.domain.HumanDept;
import com.jlkj.human.hp.domain.vo.TreeSelect;
import com.jlkj.human.hp.dto.CopySysDeptDTO;
import com.jlkj.human.hp.dto.DeptUnionPostDTO;
import com.jlkj.human.hp.dto.FirstDeptDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 部门资料维护Service接口
 * 
 * @author 266861
 * @date 2023-03-08
 */
public interface IHumanDeptService
{
    /**
     * 查询部门资料维护
     * 
     * @param deptId 部门资料维护主键
     * @return 部门资料维护
     */
    public HumanDept selectSysDeptByDeptId(Long deptId);

    /**
     * 查询部门资料维护列表
     * 
     * @param humanDept 部门资料维护
     * @return 部门资料维护集合
     */
    public List<HumanDept> selectSysDeptList(HumanDept humanDept);

    /**
     * 查询部门岗位资料维护列表
     *
     * @param deptpost 部门岗位资料维护
     * @return 部门岗位资料集合
     */
    public List<DeptUnionPostDTO> selectDeptPostList(DeptUnionPostDTO deptpost);

    /**
     * 查询子部门个数
     *
     * @param deptId 部门id
     * @return 子部门个数
     */
    public Integer queryChildNumber(Long deptId);

    /**
     * 查询公司列表
     *
     * @return 公司列表资料集合
     */
    public List<HumanDept> selectCompanyList();
    /**
     * 构建前端所需要树结构
     *
     * @param depts 部门列表
     * @return 树结构列表
     */
    public List<HumanDept> buildDeptTree(List<HumanDept> depts);

    /**
     * 构建前端所需要下拉树结构
     *
     * @param depts 部门列表
     * @return 下拉树结构列表
     */
    public List<TreeSelect> buildDeptTreeSelect(List<HumanDept> depts);

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
    public HumanDept selectDeptById(Long deptId);


    /**
     * 新增部门资料维护
     * 
     * @param humanDept 部门资料维护
     * @return 结果
     * @throws Exception 抛出异常停止执行
     */

    public int insertSysDept(HumanDept humanDept)throws Exception;

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
     * @param humanDept 部门资料维护
     * @return 结果
     * @throws Exception 抛出异常停止执行
     */
    public int updateSysDept(HumanDept humanDept) throws Exception;

    /**
     * 批量删除部门资料维护
     * 
     * @param deptIds 需要删除的部门资料维护主键集合
     * @return 结果
     * @throws Exception 抛出异常停止执行
     */
    public int deleteSysDeptByDeptIds(Long[] deptIds)throws Exception;

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
    public HumanDept selectSysDeptByDeptCode(String deptCode);

    /**
     * 查询父id为此部门编码的数据
     *
     * @param deptId 部门id
     * @return 部门信息
     */
    public List<HumanDept> selectParentIdByDeptCode(Long deptId);

    /**
     * 通过员工工号查一级部门
     * @param empId 员工工号
     * @return 结果
     */
    public FirstDeptDTO getFirstDeptByPerson(String empId);

    /**
     * 根据公司别查询信息
     *
     * @param compId 公司别
     * @return 公司信息
     */
    public HumanDept queryCompById(String compId);


    /**
     * 通过部门查员工
     * @param deptId 员工工号
     * @return 结果
     */
    public ArrayList<FirstDeptDTO> getPersonByDept(String deptId);

    /**
     * 导入部门资料数据
     *
     * @param humanDeptList 部门数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    public String importUser(List<HumanDept> humanDeptList, Boolean isUpdateSupport, String operName);

    /**
     * 查询部门名称（id/编码 转名称）
     * @param compId 公司别
     * @return 结果
     */
    public List<Map<String,Object>> selectDeptName(String compId);
}
