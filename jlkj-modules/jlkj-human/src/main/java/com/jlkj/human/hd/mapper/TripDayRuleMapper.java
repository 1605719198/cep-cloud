package com.jlkj.human.hd.mapper;

import com.jlkj.human.hd.domain.TripDayRule;

import java.util.List;

/**
 * 出差日标准维护Mapper接口
 * 
 * @author 266861
 * @date 2023-04-22
 */
public interface TripDayRuleMapper 
{
    /**
     * 查询出差日标准维护
     * 
     * @param id 出差日标准维护主键
     * @return 出差日标准维护
     */
    public TripDayRule selectTripDayRuleById(String id);

    /**
     * 查询出差日标准维护列表
     * 
     * @param tripDayRule 出差日标准维护
     * @return 出差日标准维护集合
     */
    public List<TripDayRule> selectTripDayRuleList(TripDayRule tripDayRule);

    /**
     * 查询相同出差日标准维护列表
     *
     * @param tripDayRule 出差日标准维护
     * @return 出差日标准维护
     */
    public TripDayRule querySameData(TripDayRule tripDayRule);

    /**
     * 查询出差日标准地区维护列表
     *
     * @param tripDayRule 出差日标准维护
     * @return 出差日标准维护集合
     */
    public List<TripDayRule> selectTripDayRuleArea(TripDayRule tripDayRule);

    /**
     * 新增出差日标准维护
     * 
     * @param tripDayRule 出差日标准维护
     * @return 结果
     */
    public int insertTripDayRule(TripDayRule tripDayRule);

    /**
     * 修改出差日标准维护
     * 
     * @param tripDayRule 出差日标准维护
     * @return 结果
     */
    public int updateTripDayRule(TripDayRule tripDayRule);

    /**
     * 删除出差日标准维护
     * 
     * @param id 出差日标准维护主键
     * @return 结果
     */
    public int deleteTripDayRuleById(String id);

    /**
     * 批量删除出差日标准维护
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTripDayRuleByIds(String[] ids);
}
