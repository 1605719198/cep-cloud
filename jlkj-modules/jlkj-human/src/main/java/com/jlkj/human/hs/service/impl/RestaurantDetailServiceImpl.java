package com.jlkj.human.hs.service.impl;

import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.human.hs.domain.RestaurantDetail;
import com.jlkj.human.hs.mapper.RestaurantDetailMapper;
import com.jlkj.human.hs.service.IRestaurantDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 餐饮消费明细Service业务层处理
 *
 * @author 266861
 * @date 2023-06-19
 */
@Service
public class RestaurantDetailServiceImpl implements IRestaurantDetailService
{
    @Autowired
    private RestaurantDetailMapper restaurantDetailMapper;

    /**
     * 查询餐饮消费明细
     *
     * @param uuid 餐饮消费明细主键
     * @return 餐饮消费明细
     */
    @Override
    public RestaurantDetail selectRestaurantDetailByUuid(String uuid)
    {
        return restaurantDetailMapper.selectRestaurantDetailByUuid(uuid);
    }

    /**
     * 查询餐饮消费明细列表
     *
     * @param restaurantDetail 餐饮消费明细
     * @return 餐饮消费明细
     */
    @Override
    public List<RestaurantDetail> selectRestaurantDetailList(RestaurantDetail restaurantDetail)
    {
        return restaurantDetailMapper.selectRestaurantDetailList(restaurantDetail);
    }

    /**
     * 新增餐饮消费明细
     *
     * @param restaurantDetail 餐饮消费明细
     * @return 结果
     */
    @Override
    public int insertRestaurantDetail(RestaurantDetail restaurantDetail)
    {
        restaurantDetail.setCreateTime(DateUtils.getNowDate());
        return restaurantDetailMapper.insertRestaurantDetail(restaurantDetail);
    }

    /**
     * 修改餐饮消费明细
     *
     * @param restaurantDetail 餐饮消费明细
     * @return 结果
     */
    @Override
    public int updateRestaurantDetail(RestaurantDetail restaurantDetail)
    {
        return restaurantDetailMapper.updateRestaurantDetail(restaurantDetail);
    }

    /**
     * 批量删除餐饮消费明细
     *
     * @param uuids 需要删除的餐饮消费明细主键
     * @return 结果
     */
    @Override
    public int deleteRestaurantDetailByUuids(String[] uuids)
    {
        return restaurantDetailMapper.deleteRestaurantDetailByUuids(uuids);
    }

    /**
     * 删除餐饮消费明细信息
     *
     * @param uuid 餐饮消费明细主键
     * @return 结果
     */
    @Override
    public int deleteRestaurantDetailByUuid(String uuid)
    {
        return restaurantDetailMapper.deleteRestaurantDetailByUuid(uuid);
    }
}
