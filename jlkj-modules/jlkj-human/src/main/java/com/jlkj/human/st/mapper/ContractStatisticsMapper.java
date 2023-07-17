package com.jlkj.human.st.mapper;

import java.util.List;
import com.jlkj.human.st.domain.ContractStatistics;

/**
 * 劳动合同统计分析Mapper接口
 * 
 * @author 267383
 * @date 2023-07-17
 */
public interface ContractStatisticsMapper 
{
    /**
     * 查询劳动合同统计分析
     * 
     * @param compId 劳动合同统计分析主键
     * @return 劳动合同统计分析
     */
    public ContractStatistics selectContractStatisticsByCompId(String compId);

    /**
     * 查询劳动合同统计分析列表
     * 
     * @param contractStatistics 劳动合同统计分析
     * @return 劳动合同统计分析集合
     */
    public List<ContractStatistics> selectContractStatisticsList(ContractStatistics contractStatistics);

    /**
     * 新增劳动合同统计分析
     * 
     * @param contractStatistics 劳动合同统计分析
     * @return 结果
     */
    public int insertContractStatistics(ContractStatistics contractStatistics);

    /**
     * 修改劳动合同统计分析
     * 
     * @param contractStatistics 劳动合同统计分析
     * @return 结果
     */
    public int updateContractStatistics(ContractStatistics contractStatistics);

    /**
     * 删除劳动合同统计分析
     * 
     * @param compId 劳动合同统计分析主键
     * @return 结果
     */
    public int deleteContractStatisticsByCompId(String compId);

    /**
     * 批量删除劳动合同统计分析
     * 
     * @param compIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteContractStatisticsByCompIds(String[] compIds);
}
