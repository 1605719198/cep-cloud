package com.jlkj.human.st.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jlkj.human.st.mapper.ContractStatisticsMapper;
import com.jlkj.human.st.domain.ContractStatistics;
import com.jlkj.human.st.service.IContractStatisticsService;

/**
 * 劳动合同统计分析Service业务层处理
 *
 * @author 267383
 * @date 2023-07-17
 * @version 1.0
 */
@Service
public class ContractStatisticsServiceImpl implements IContractStatisticsService
{
    @Autowired
    private ContractStatisticsMapper contractStatisticsMapper;

    /**
     * 查询劳动合同统计分析
     *
     * @author 267383
     * @date 2023-07-17
     * @param compId 劳动合同统计分析主键
     * @return 劳动合同统计分析
     */
    @Override
    public ContractStatistics selectContractStatisticsByCompId(String compId)
    {
        return contractStatisticsMapper.selectContractStatisticsByCompId(compId);
    }

    /**
     * 查询劳动合同统计分析列表
     *
     * @author 267383
     * @date 2023-07-17
     * @param contractStatistics 劳动合同统计分析
     * @return 劳动合同统计分析
     */
    @Override
    public List<ContractStatistics> selectContractStatisticsList(ContractStatistics contractStatistics)
    {
        return contractStatisticsMapper.selectContractStatisticsList(contractStatistics);
    }

    /**
     * 新增劳动合同统计分析
     *
     * @author 267383
     * @date 2023-07-17
     * @param contractStatistics 劳动合同统计分析
     * @return 结果
     */
    @Override
    public int insertContractStatistics(ContractStatistics contractStatistics)
    {
        return contractStatisticsMapper.insertContractStatistics(contractStatistics);
    }

    /**
     * 修改劳动合同统计分析
     *
     * @author 267383
     * @date 2023-07-17
     * @param contractStatistics 劳动合同统计分析
     * @return 结果
     */
    @Override
    public int updateContractStatistics(ContractStatistics contractStatistics)
    {
        return contractStatisticsMapper.updateContractStatistics(contractStatistics);
    }

    /**
     * 批量删除劳动合同统计分析
     *
     * @author 267383
     * @date 2023-07-17
     * @param compIds 需要删除的劳动合同统计分析主键
     * @return 结果
     */
    @Override
    public int deleteContractStatisticsByCompIds(String[] compIds)
    {
        return contractStatisticsMapper.deleteContractStatisticsByCompIds(compIds);
    }

    /**
     * 删除劳动合同统计分析信息
     *
     * @author 267383
     * @date 2023-07-17
     * @param compId 劳动合同统计分析主键
     * @return 结果
     */
    @Override
    public int deleteContractStatisticsByCompId(String compId)
    {
        return contractStatisticsMapper.deleteContractStatisticsByCompId(compId);
    }
}
