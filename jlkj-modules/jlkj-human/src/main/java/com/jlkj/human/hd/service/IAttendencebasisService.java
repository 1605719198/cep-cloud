package com.jlkj.human.hd.service;

import com.jlkj.human.hd.domain.Attendencebasis;
import com.jlkj.human.hp.domain.vo.TreeSelect;

import java.util.List;

/**
 * 员工出勤基本资料维护Service接口
 * 
 * @author 266861
 * @date 2023-03-20
 */
public interface IAttendencebasisService
{
    /**
     * 查询员工出勤基本资料维护
     * 
     * @param id 员工出勤基本资料维护主键
     * @return 员工出勤基本资料维护
     */
    public Attendencebasis selectAttendencebasisById(Long id);

    /**
     * 查询员工出勤基本资料维护列表
     * 
     * @param attendencebasis 员工出勤基本资料维护
     * @return 员工出勤基本资料维护集合
     */
    public List<Attendencebasis> selectAttendencebasisList(Attendencebasis attendencebasis);

    /**
     * 构建前端所需要树结构
     *
     * @param attendencebasiss 部门列表
     * @return 树结构列表
     */
    public List<Attendencebasis> buildAttendenceTree(List<Attendencebasis> attendencebasiss);

    /**
     * 构建前端所需要下拉树结构
     *
     * @param attendencebasiss 部门列表
     * @return 下拉树结构列表
     */
    public List<TreeSelect> buildAttendenceTreeSelect(List<Attendencebasis> attendencebasiss) ;

    /**
     * 新增员工出勤基本资料维护
     * 
     * @param attendencebasis 员工出勤基本资料维护
     * @return 结果
     */
    public int insertAttendencebasis(Attendencebasis attendencebasis) throws Exception;

    /**
     * 修改员工出勤基本资料维护
     * 
     * @param attendencebasis 员工出勤基本资料维护
     * @return 结果
     */
    public int updateAttendencebasis(Attendencebasis attendencebasis) throws Exception;

    /**
     * 批量删除员工出勤基本资料维护
     * 
     * @param ids 需要删除的员工出勤基本资料维护主键集合
     * @return 结果
     */
    public int deleteAttendencebasisByIds(Long[] ids);

    /**
     * 删除员工出勤基本资料维护信息
     * 
     * @param id 员工出勤基本资料维护主键
     * @return 结果
     */
    public int deleteAttendencebasisById(Long id) throws Exception;


    /**
     * 查询员工出勤基本资料维护选单
     *
     * @param code 员工出勤基本资料维护编码
     * @return 结果
     */
    public List<Attendencebasis> selectBasisOptions(String code) ;

}
