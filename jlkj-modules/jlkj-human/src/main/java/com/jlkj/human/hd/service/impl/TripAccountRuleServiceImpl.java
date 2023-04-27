package com.jlkj.human.hd.service.impl;

import java.util.List;

import com.jlkj.common.core.utils.uuid.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jlkj.human.hd.mapper.TripAccountRuleMapper;
import com.jlkj.human.hd.domain.TripAccountRule;
import com.jlkj.human.hd.service.ITripAccountRuleService;

/**
 * 出差会计科目Service业务层处理
 *
 * @author 266861
 * @date 2023-04-23
 */
@Service
public class TripAccountRuleServiceImpl implements ITripAccountRuleService
{
    @Autowired
    private TripAccountRuleMapper tripAccountRuleMapper;

    /**
     * 查询出差会计科目
     *
     * @param id 出差会计科目主键
     * @return 出差会计科目
     */
    @Override
    public TripAccountRule selectTripAccountRuleById(String id)
    {
        return tripAccountRuleMapper.selectTripAccountRuleById(id);
    }

    /**
     * 查询出差会计科目列表
     *
     * @param tripAccountRule 出差会计科目
     * @return 出差会计科目
     */
    @Override
    public List<TripAccountRule> selectTripAccountRuleList(TripAccountRule tripAccountRule)
    {
        return tripAccountRuleMapper.selectTripAccountRuleList(tripAccountRule);
    }

    /**
     * 新增出差会计科目
     *
     * @param tripAccountRule 出差会计科目
     * @return 结果
     */
    @Override
    public int insertTripAccountRule(TripAccountRule tripAccountRule)
    {
        tripAccountRule.setId(UUID.randomUUID().toString().substring(0,32));
        return tripAccountRuleMapper.insertTripAccountRule(tripAccountRule);
    }

    /**
     * 修改出差会计科目
     *
     * @param tripAccountRule 出差会计科目
     * @return 结果
     */
    @Override
    public int updateTripAccountRule(TripAccountRule tripAccountRule)
    {
        return tripAccountRuleMapper.updateTripAccountRule(tripAccountRule);
    }

    /**
     * 批量删除出差会计科目
     *
     * @param ids 需要删除的出差会计科目主键
     * @return 结果
     */
    @Override
    public int deleteTripAccountRuleByIds(String[] ids)
    {
        return tripAccountRuleMapper.deleteTripAccountRuleByIds(ids);
    }

    /**
     * 删除出差会计科目信息
     *
     * @param id 出差会计科目主键
     * @return 结果
     */
    @Override
    public int deleteTripAccountRuleById(String id)
    {
        return tripAccountRuleMapper.deleteTripAccountRuleById(id);
    }
}