package com.jlkj.human.hd.service.impl;

import com.jlkj.common.core.utils.uuid.UUID;
import com.jlkj.human.hd.domain.TripDayRule;
import com.jlkj.human.hd.mapper.TripDayRuleMapper;
import com.jlkj.human.hd.service.ITripDayRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 出差日标准维护Service业务层处理
 *
 * @author 266861
 * @date 2023-04-22
 */
@Service
public class TripDayRuleServiceImpl implements ITripDayRuleService
{
    @Autowired
    private TripDayRuleMapper tripDayRuleMapper;

    /**
     * 查询出差日标准维护
     *
     * @param id 出差日标准维护主键
     * @return 出差日标准维护
     */
    @Override
    public TripDayRule selectTripDayRuleById(String id)
    {
        return tripDayRuleMapper.selectTripDayRuleById(id);
    }

    /**
     * 查询出差日标准维护列表
     *
     * @param tripDayRule 出差日标准维护
     * @return 出差日标准维护
     */
    @Override
    public List<TripDayRule> selectTripDayRuleList(TripDayRule tripDayRule)
    {
        return tripDayRuleMapper.selectTripDayRuleList(tripDayRule);
    }

    /**
     * 新增出差日标准维护
     *
     * @param tripDayRule 出差日标准维护
     * @return 结果
     */
    @Override
    public int insertTripDayRule(TripDayRule tripDayRule)
    {
        tripDayRule.setId(UUID.randomUUID().toString().substring(0,32));
        return tripDayRuleMapper.insertTripDayRule(tripDayRule);
    }

    /**
     * 修改出差日标准维护
     *
     * @param tripDayRule 出差日标准维护
     * @return 结果
     */
    @Override
    public int updateTripDayRule(TripDayRule tripDayRule)
    {
        return tripDayRuleMapper.updateTripDayRule(tripDayRule);
    }

    /**
     * 批量删除出差日标准维护
     *
     * @param ids 需要删除的出差日标准维护主键
     * @return 结果
     */
    @Override
    public int deleteTripDayRuleByIds(String[] ids)
    {
        return tripDayRuleMapper.deleteTripDayRuleByIds(ids);
    }

    /**
     * 删除出差日标准维护信息
     *
     * @param id 出差日标准维护主键
     * @return 结果
     */
    @Override
    public int deleteTripDayRuleById(String id)
    {
        return tripDayRuleMapper.deleteTripDayRuleById(id);
    }
}
