package com.jlkj.finance.ft.service;

import com.jlkj.finance.ft.domain.FinanceFtStatus;
import com.jlkj.finance.ft.dto.FinanceFtStatusDTO;

import java.util.List;

/**
 * 资产使用状态Service接口
 * 
 * @author SunXuTong
 * @date 2023-07-03
 */
public interface IFinanceFtStatusService 
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
     * @author SunXuTong
     * @date 2023-07-03
     * @param financeFtStatus 资产使用状态
     * @return 资产使用状态集合
     */
    public List<FinanceFtStatus> selectFinanceFtStatusList(FinanceFtStatus financeFtStatus);

    /**
     * 新增资产使用状态
     *
     * @author SunXuTong
     * @date 2023-07-03
     * @param financeFtStatusDTO 资产使用状态
     * @return 结果
     */
    public int insertFinanceFtStatus(FinanceFtStatusDTO financeFtStatusDTO);

    /**
     * 修改资产使用状态
     *
     * @author SunXuTong
     * @date 2023-07-03
     * @param financeFtStatus 资产使用状态
     * @return 结果
     */
    public int updateFinanceFtStatus(FinanceFtStatus financeFtStatus);

    /**
     * 批量删除资产使用状态
     *
     * @author SunXuTong
     * @date 2023-07-03
     * @param uuids 需要删除的资产使用状态主键集合
     * @return 结果
     */
    public int deleteFinanceFtStatusByUuids(String[] uuids);

    /**
     * 删除资产使用状态信息
     *
     * @author SunXuTong
     * @date 2023-07-03
     * @param uuid 资产使用状态主键
     * @return 结果
     */
    public int deleteFinanceFtStatusByUuid(String uuid);
}
