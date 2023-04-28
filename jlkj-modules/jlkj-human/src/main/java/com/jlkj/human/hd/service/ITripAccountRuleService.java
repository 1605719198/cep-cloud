package com.jlkj.human.hd.service;

import java.util.List;
import com.jlkj.human.hd.domain.TripAccountRule;

/**
 * 出差会计科目Service接口
 * 
 * @author 266861
 * @date 2023-04-23
 */
public interface ITripAccountRuleService 
{
    /**
     * 查询出差会计科目
     * 
     * @param id 出差会计科目主键
     * @return 出差会计科目
     */
    public TripAccountRule selectTripAccountRuleById(String id);

    /**
     * 查询出差会计科目列表
     * 
     * @param tripAccountRule 出差会计科目
     * @return 出差会计科目集合
     */
    public List<TripAccountRule> selectTripAccountRuleList(TripAccountRule tripAccountRule);

    /**
     * 新增出差会计科目
     * 
     * @param tripAccountRule 出差会计科目
     * @return 结果
     */
    public int insertTripAccountRule(TripAccountRule tripAccountRule);

    /**
     * 修改出差会计科目
     * 
     * @param tripAccountRule 出差会计科目
     * @return 结果
     */
    public int updateTripAccountRule(TripAccountRule tripAccountRule);

    /**
     * 批量删除出差会计科目
     * 
     * @param ids 需要删除的出差会计科目主键集合
     * @return 结果
     */
    public int deleteTripAccountRuleByIds(String[] ids);

    /**
     * 删除出差会计科目信息
     * 
     * @param id 出差会计科目主键
     * @return 结果
     */
    public int deleteTripAccountRuleById(String id);
}
