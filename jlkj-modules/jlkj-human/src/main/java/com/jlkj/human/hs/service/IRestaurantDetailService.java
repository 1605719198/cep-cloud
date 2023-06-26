package com.jlkj.human.hs.service;

import com.jlkj.human.hs.domain.RestaurantDetail;

import java.util.List;

/**
 * 餐饮消费明细Service接口
 * 
 * @author 266861
 * @date 2023-06-19
 */
public interface IRestaurantDetailService 
{
    /**
     * 查询餐饮消费明细
     * 
     * @param uuid 餐饮消费明细主键
     * @return 餐饮消费明细
     */
    public RestaurantDetail selectRestaurantDetailByUuid(String uuid);

    /**
     * 查询餐饮消费明细列表
     * 
     * @param restaurantDetail 餐饮消费明细
     * @return 餐饮消费明细集合
     */
    public List<RestaurantDetail> selectRestaurantDetailList(RestaurantDetail restaurantDetail);

    /**
     * 新增餐饮消费明细
     * 
     * @param restaurantDetail 餐饮消费明细
     * @return 结果
     */
    public int insertRestaurantDetail(RestaurantDetail restaurantDetail);

    /**
     * 修改餐饮消费明细
     * 
     * @param restaurantDetail 餐饮消费明细
     * @return 结果
     */
    public int updateRestaurantDetail(RestaurantDetail restaurantDetail);

    /**
     * 批量删除餐饮消费明细
     * 
     * @param uuids 需要删除的餐饮消费明细主键集合
     * @return 结果
     */
    public int deleteRestaurantDetailByUuids(String[] uuids);

    /**
     * 删除餐饮消费明细信息
     * 
     * @param uuid 餐饮消费明细主键
     * @return 结果
     */
    public int deleteRestaurantDetailByUuid(String uuid);
}
