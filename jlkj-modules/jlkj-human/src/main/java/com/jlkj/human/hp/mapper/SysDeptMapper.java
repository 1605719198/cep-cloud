package com.jlkj.human.hp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jlkj.human.hp.domain.SysDept;
import com.jlkj.human.hp.dto.CopySysDeptDTO;
import com.jlkj.human.hp.dto.DeptUnionPostDTO;
import com.jlkj.human.hp.dto.FirstDeptDTO;
import org.apache.ibatis.annotations.MapKey;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 部门资料维护Mapper接口
 * 
 * @author 266861
 * @date 2023-03-08
 */
public interface SysDeptMapper extends BaseMapper<SysDept>
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
     * 根据公司别查询信息
     *
     * @param compId 公司别
     * @return 公司信息
     */
    public SysDept selectSysDeptByCompId(String compId);

    /**
     * 查询子部门个数
     *
     * @param deptId 部门id
     * @return 子部门个数
     */
    public Integer queryChildNumber(Long deptId);

    /**
     * 查询是否有重复编码数据
     *
     * @param sysDept 部门信息
     * @return 重复编码个数
     */
    public Integer queryRepetitivedata(SysDept sysDept);

    /**
     * 查询父id为此部门编码的数据
     *
     * @param deptId 部门id
     * @return 部门信息
     */
    public List<SysDept> selectParentIdByDeptCode(Long deptId);

    /**
     * 查询部门资料维护列表
     * 
     * @param sysDept 部门资料维护
     * @return 部门资料维护集合
     */
    public List<SysDept> selectSysDeptList(SysDept sysDept);

    /**
     * 通过部门Id查一级部门
     *
     * @param empId 部门ID
     * @return 一级部门信息
     */
    public FirstDeptDTO getFirstDeptByDept(String empId);

    /**
     * 通过员工工号查一级部门
     *
     * @param empId 员工工号
     * @return 一级部门信息
     */
    public  FirstDeptDTO getFirstDeptByPerson(String empId);

    /**
     * 通过部门差员工工号
     *
     * @param deptId 员工工号
     * @return 一级部门信息
     */
    public ArrayList<FirstDeptDTO> getPersonByDept(String deptId);

    /**
     * 查询公司资料列表
     *
     * @return 部门公司资料集合
     */
    public List<SysDept> selectCompanyList();

    /**
     * 查询部门岗位资料维护列表
     *
     * @param deptpost 部门岗位资料维护
     * @return 部门岗位资料维护集合
     */
    public List<DeptUnionPostDTO> selectDeptUnionPost(DeptUnionPostDTO deptpost);

    /**
     * 新增部门资料维护
     * 
     * @param sysDept 部门资料维护
     * @return 结果
     */
    public int insertSysDept(SysDept sysDept);

    /**
     * 复制组织机构
     *
     * @param copySysDeptDTO 组织机构复制设定
     * @return 结果
     */
    public int copySysDept (CopySysDeptDTO copySysDeptDTO);

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
     * 查询组织结构设定复制来源公司
     *
     * @param oldCompId 来源公司别
     * @return 结果
     */
    public int querycopybyOldCompId(String oldCompId);

    /**
     * 查询组织机构设定复制目标公司
     *
     * @param newCompId 目标公司别
     * @return 结果
     */
    public int querycopybyNewCompId(String  newCompId);

    /**
     * 批量删除部门资料维护
     * 
     * @param deptIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysDeptByDeptIds(Long[] deptIds);

    /**
     * 查询部门名称（id/编码 转名称）
     * @param compId 公司别
     * @return 结果
     */
    @MapKey("id")
    public List<Map<String,Object>> selectDeptName(String compId);
}
