package com.jlkj.human.pa.service;

import com.jlkj.human.hd.dto.BasisOptionsDTO;
import com.jlkj.human.hp.domain.vo.TreeSelect;
import com.jlkj.human.pa.domain.PerformanceBasis;

import java.util.List;

/**
 * 绩效管理基本资料维护Service接口
 * 
 * @author 116519
 * @date 2023-05-17
 */
public interface IPerformanceBasisService 
{
    /**
     * 查询绩效管理基本资料维护
     * 
     * @param id 绩效管理基本资料维护主键
     * @return 绩效管理基本资料维护
     */
    public PerformanceBasis selectPerformanceBasisById(Long id);

    /**
     * 查询绩效管理基本资料维护列表
     * 
     * @param performanceBasis 绩效管理基本资料维护
     * @return 绩效管理基本资料维护集合
     */
    public List<PerformanceBasis> selectPerformanceBasisList(PerformanceBasis performanceBasis);

    /**
     * 新增绩效管理基本资料维护
     * 
     * @param performanceBasis 绩效管理基本资料维护
     * @return 结果
     */
    public int insertPerformanceBasis(PerformanceBasis performanceBasis);

    /**
     * 修改绩效管理基本资料维护
     * 
     * @param performanceBasis 绩效管理基本资料维护
     * @return 结果
     */
    public int updatePerformanceBasis(PerformanceBasis performanceBasis) throws Exception;;

    /**
     * 批量删除绩效管理基本资料维护
     * 
     * @param ids 需要删除的绩效管理基本资料维护主键集合
     * @return 结果
     */
    public int deletePerformanceBasisByIds(Long[] ids);

    /**
     * 删除绩效管理基本资料维护信息
     * 
     * @param id 绩效管理基本资料维护主键
     * @return 结果
     */
    public int deletePerformanceBasisById(Long id) throws Exception;

    /**
     * 构建前端所需要下拉树结构
     *
     * @param performanceBasiss 部门列表
     * @return 下拉树结构列表
     */
    public List<TreeSelect> buildAttendenceTreeSelect(List<PerformanceBasis> performanceBasiss) ;

    /**
     * 构建前端所需要树结构
     *
     * @param performanceBasiss 部门列表
     * @return 树结构列表
     */
    public List<PerformanceBasis> buildAttendenceTree(List<PerformanceBasis> performanceBasiss);

    /**
     * 查询员工绩效基本资料维护选单
     *
     * @param code 员工绩效基本资料维护编码
     * @return 结果
     */
    public List<BasisOptionsDTO> selectBasisOptions(String code) ;
}
