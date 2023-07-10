package com.jlkj.human.hd.service;

import com.jlkj.human.hd.domain.TripAddress;
import com.jlkj.human.hd.dto.TripAddressDTO;

import java.util.List;

/**
 * 出差地点记录Service接口
 * 
 * @author 266861
 * @date 2023-04-24
 */
public interface ITripAddressService 
{
    /**
     * 查询出差地点记录
     * 
     * @param id 出差地点记录主键
     * @return 出差地点记录
     */
    public TripAddress selectTripAddressById(String id);

    /**
     * 查询出差地点记录列表
     * 
     * @param tripAddress 出差地点记录
     * @return 出差地点记录集合
     */
    public List<TripAddress> selectTripAddressList(TripAddress tripAddress);

    /**
     * 查询出差地点记录列表
     *
     * @param address 出差地点查询条件
     * @return 出差地点记录集合
     */
    public List<TripAddressDTO> selectTripAddress(TripAddress address);

    /**
     * 新增出差地点记录
     * 
     * @param tripAddress 出差地点记录
     * @return 结果
     */
    public int insertTripAddress(TripAddress tripAddress);

    /**
     * 修改出差地点记录
     * 
     * @param tripAddress 出差地点记录
     * @return 结果
     */
    public int updateTripAddress(TripAddress tripAddress);

    /**
     * 批量删除出差地点记录
     * 
     * @param ids 需要删除的出差地点记录主键集合
     * @return 结果
     */
    public int deleteTripAddressByIds(String[] ids);

    /**
     * 删除出差地点记录信息
     * 
     * @param id 出差地点记录主键
     * @return 结果
     */
    public int deleteTripAddressById(String id);
}
