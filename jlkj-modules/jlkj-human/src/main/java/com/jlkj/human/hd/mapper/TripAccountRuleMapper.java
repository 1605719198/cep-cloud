package com.jlkj.human.hd.mapper;

import com.jlkj.human.hd.domain.TripAccountRule;

import java.util.List;

/**
 * 出差会计科目Mapper接口
 * 
 * @author 266861
 * @date 2023-04-23
 */
public interface TripAccountRuleMapper 
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
     * 查询相同出差会计科目
     *
     * @param tripAccountRule 出差会计科目
     * @return 出差会计科目
     */
    public TripAccountRule querySameData(TripAccountRule tripAccountRule);

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
     * 删除出差会计科目
     * 
     * @param id 出差会计科目主键
     * @return 结果
     */
    public int deleteTripAccountRuleById(String id);

    /**
     * 批量删除出差会计科目
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTripAccountRuleByIds(String[] ids);
}
