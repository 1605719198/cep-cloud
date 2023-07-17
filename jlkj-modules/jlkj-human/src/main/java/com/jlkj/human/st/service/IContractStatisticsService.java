package com.jlkj.human.st.service;

import java.util.List;
import com.jlkj.human.st.domain.ContractStatistics;

/**
 * 劳动合同统计分析Service接口
 * 
 * @author 267383
 * @date 2023-07-17
 */
public interface IContractStatisticsService 
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
     * @author 267383
     * @date 2023-07-17
     * @param contractStatistics 劳动合同统计分析
     * @return 劳动合同统计分析集合
     */
    public List<ContractStatistics> selectContractStatisticsList(ContractStatistics contractStatistics);

    /**
     * 新增劳动合同统计分析
     *
     * @author 267383
     * @date 2023-07-17
     * @param contractStatistics 劳动合同统计分析
     * @return 结果
     */
    public int insertContractStatistics(ContractStatistics contractStatistics);

    /**
     * 修改劳动合同统计分析
     *
     * @author 267383
     * @date 2023-07-17
     * @param contractStatistics 劳动合同统计分析
     * @return 结果
     */
    public int updateContractStatistics(ContractStatistics contractStatistics);

    /**
     * 批量删除劳动合同统计分析
     *
     * @author 267383
     * @date 2023-07-17
     * @param compIds 需要删除的劳动合同统计分析主键集合
     * @return 结果
     */
    public int deleteContractStatisticsByCompIds(String[] compIds);

    /**
     * 删除劳动合同统计分析信息
     *
     * @author 267383
     * @date 2023-07-17
     * @param compId 劳动合同统计分析主键
     * @return 结果
     */
    public int deleteContractStatisticsByCompId(String compId);
}
