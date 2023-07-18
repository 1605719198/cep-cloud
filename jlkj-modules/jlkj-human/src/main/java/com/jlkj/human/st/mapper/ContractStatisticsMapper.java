package com.jlkj.human.st.mapper;

import com.jlkj.human.st.domain.ContractStatistics;

import java.util.List;

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
     * @param id 劳动合同统计分析主键
     * @return 劳动合同统计分析
     */
    public ContractStatistics selectContractStatisticsById(String id);

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

}
