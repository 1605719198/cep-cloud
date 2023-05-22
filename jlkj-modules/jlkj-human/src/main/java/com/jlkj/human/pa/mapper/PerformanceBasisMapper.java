package com.jlkj.human.pa.mapper;

import com.jlkj.human.hd.dto.BasisOptionsDTO;
import com.jlkj.human.pa.domain.PerformanceBasis;

import java.util.List;

/**
 * 绩效管理基本资料维护Mapper接口
 * 
 * @author 116519
 * @date 2023-05-17
 */
public interface PerformanceBasisMapper 
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
    public int updatePerformanceBasis(PerformanceBasis performanceBasis);

    /**
     * 删除绩效管理基本资料维护
     * 
     * @param id 绩效管理基本资料维护主键
     * @return 结果
     */
    public int deletePerformanceBasisById(Long id);

    /**
     * 批量删除绩效管理基本资料维护
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePerformanceBasisByIds(Long[] ids);

    /**
     * 通过父节点id查询绩效基本资料维护
     *
     * @param parentid 绩效基本资料维护编码
     * @return 绩效基本资料维护
     */
    public List<BasisOptionsDTO> selectAttendencebasisByParentid(Long  parentid);

    /**
     * 通过编码查询绩效基本资料维护
     *
     * @param code 绩效基本资料维护编码
     * @return 绩效基本资料维护
     */
    public PerformanceBasis selectAttendencebasisParentByCode(String  code);

    /**
     * 查询是否有重复编码数据
     *
     * @param performanceBasis 绩效基本资料
     * @return 绩效基本资料维护
     */
    public int queryRepetitivedata(PerformanceBasis performanceBasis);

}