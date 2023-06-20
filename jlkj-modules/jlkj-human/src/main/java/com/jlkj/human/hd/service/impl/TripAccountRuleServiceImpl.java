package com.jlkj.human.hd.service.impl;

import com.jlkj.common.core.exception.ServiceException;
import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.core.utils.uuid.IdUtils;
import com.jlkj.human.hd.domain.TripAccountRule;
import com.jlkj.human.hd.mapper.TripAccountRuleMapper;
import com.jlkj.human.hd.service.ITripAccountRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        if(StringUtils.isNull(tripAccountRuleMapper.querySameData(tripAccountRule))){
            tripAccountRule.setId(IdUtils.simpleUUID());
            return tripAccountRuleMapper.insertTripAccountRule(tripAccountRule);
        }else{
            throw new ServiceException("已有相同类型数据");
        }
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
        if(StringUtils.isNull(tripAccountRuleMapper.querySameData(tripAccountRule))){
            return tripAccountRuleMapper.updateTripAccountRule(tripAccountRule);
        }else{
            throw new ServiceException("已有相同类型数据");
        }
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
