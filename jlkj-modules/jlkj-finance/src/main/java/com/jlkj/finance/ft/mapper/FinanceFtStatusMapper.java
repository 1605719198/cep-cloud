package com.jlkj.finance.ft.mapper;

import com.jlkj.finance.ft.domain.FinanceFtStatus;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 资产使用状态Mapper接口
 * 
 * @author SunXuTong
 * @date 2023-07-03
 */
@Repository
public interface FinanceFtStatusMapper 
{
    /**
     * 查询资产使用状态
     * 
     * @param uuid 资产使用状态主键
     * @return 资产使用状态
     */
    public FinanceFtStatus selectFinanceFtStatusByUuid(String uuid);

    /**
     * 查询资产使用状态列表
     * 
     * @param financeFtStatus 资产使用状态
     * @return 资产使用状态集合
     */
    public List<FinanceFtStatus> selectFinanceFtStatusList(FinanceFtStatus financeFtStatus);

    /**
     * 新增资产使用状态
     * 
     * @param financeFtStatus 资产使用状态
     * @return 结果
     */
    public int insertFinanceFtStatus(FinanceFtStatus financeFtStatus);

    /**
     * 修改资产使用状态
     * 
     * @param financeFtStatus 资产使用状态
     * @return 结果
     */
    public int updateFinanceFtStatus(FinanceFtStatus financeFtStatus);

    /**
     * 删除资产使用状态
     * 
     * @param uuid 资产使用状态主键
     * @return 结果
     */
    public int deleteFinanceFtStatusByUuid(String uuid);

    /**
     * 批量删除资产使用状态
     * 
     * @param uuids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFinanceFtStatusByUuids(String[] uuids);
}
