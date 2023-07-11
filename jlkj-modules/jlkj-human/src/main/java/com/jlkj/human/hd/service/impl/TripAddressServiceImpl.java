package com.jlkj.human.hd.service.impl;

import com.jlkj.common.core.utils.uuid.IdUtils;
import com.jlkj.human.hd.domain.TripAddress;
import com.jlkj.human.hd.dto.TripAddressDTO;
import com.jlkj.human.hd.mapper.TripAddressMapper;
import com.jlkj.human.hd.service.ITripAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 出差地点记录Service业务层处理
 *
 * @author 266861
 * @date 2023-04-24
 */
@Service
public class TripAddressServiceImpl implements ITripAddressService
{
    @Autowired
    private TripAddressMapper tripAddressMapper;

    /**
     * 查询出差地点记录
     *
     * @param id 出差地点记录主键
     * @return 出差地点记录
     */
    @Override
    public TripAddress selectTripAddressById(String id)
    {
        return tripAddressMapper.selectTripAddressById(id);
    }

    /**
     * 查询出差地点记录列表
     *
     * @param tripAddress 出差地点记录
     * @return 出差地点记录
     */
    @Override
    public List<TripAddress> selectTripAddressList(TripAddress tripAddress)
    {
        return tripAddressMapper.selectTripAddressList(tripAddress);
    }

    /**
     * 查询出差地点记录列表
     *
     * @param address 出差地点记录
     * @return 出差地点记录
     */
    @Override
    public List<TripAddressDTO> selectTripAddress(TripAddress address)
    {
        return tripAddressMapper.selectTripAddress(address);
    }

    /**
     * 新增出差地点记录
     *
     * @param tripAddress 出差地点记录
     * @return 结果
     */
    @Override
    public int insertTripAddress(TripAddress tripAddress)
    {
        tripAddress.setId(IdUtils.simpleUUID());
        return tripAddressMapper.insertTripAddress(tripAddress);
    }



    /**
     * 修改出差地点记录
     *
     * @param tripAddress 出差地点记录
     * @return 结果
     */
    @Override
    public int updateTripAddress(TripAddress tripAddress)
    {
        return tripAddressMapper.updateTripAddress(tripAddress);
    }

    /**
     * 批量删除出差地点记录
     *
     * @param ids 需要删除的出差地点记录主键
     * @return 结果
     */
    @Override
    public int deleteTripAddressByIds(String[] ids)
    {
        return tripAddressMapper.deleteTripAddressByIds(ids);
    }

    /**
     * 删除出差地点记录信息
     *
     * @param id 出差地点记录主键
     * @return 结果
     */
    @Override
    public int deleteTripAddressById(String id)
    {
        return tripAddressMapper.deleteTripAddressById(id);
    }
}
