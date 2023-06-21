package com.jlkj.human.hs.mapper;

import com.jlkj.human.hs.domain.RestaurantDetail;

import java.util.List;

/**
 * 餐饮消费明细Mapper接口
 * 
 * @author 266861
 * @date 2023-06-19
 */
public interface RestaurantDetailMapper 
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
     * 删除餐饮消费明细
     * 
     * @param uuid 餐饮消费明细主键
     * @return 结果
     */
    public int deleteRestaurantDetailByUuid(String uuid);

    /**
     * 批量删除餐饮消费明细
     * 
     * @param uuids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteRestaurantDetailByUuids(String[] uuids);
}
