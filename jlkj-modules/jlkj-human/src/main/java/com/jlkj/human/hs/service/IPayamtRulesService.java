package com.jlkj.human.hs.service;

import com.jlkj.human.hs.domain.PayamtRules;

import java.util.List;

/**
 * 应付抛帐设定Service接口
 * 
 * @author 116519
 * @date 2023-06-20
 */
public interface IPayamtRulesService 
{
    /**
     * 查询应付抛帐设定
     * 
     * @param id 应付抛帐设定主键
     * @return 应付抛帐设定
     */
    public PayamtRules selectPayamtRulesById(String id);

    /**
     * 查询应付抛帐设定列表
     * 
     * @param payamtRules 应付抛帐设定
     * @return 应付抛帐设定集合
     */
    public List<PayamtRules> selectPayamtRulesList(PayamtRules payamtRules);
    /**
     * 查询应付抛帐设定列表
     *
     * @param payamtRules 应付抛帐设定
     * @return 应付抛帐设定集合
     */
    public List<PayamtRules> selectPayamtRules(PayamtRules payamtRules);
    /**
     * 新增应付抛帐设定
     * 
     * @param payamtRules 应付抛帐设定
     * @return 结果
     */
    public int insertPayamtRules(PayamtRules payamtRules);


    /**
     * 新增应付抛帐设定
     *
     * @param payamtRulesList 应付抛帐设定
     * @return 结果
     */
    public int insertPayamtRules(List<PayamtRules> payamtRulesList);
    /**
     * 修改应付抛帐设定
     * 
     * @param payamtRules 应付抛帐设定
     * @return 结果
     */
    public int updatePayamtRules(PayamtRules payamtRules);

    /**
     * 批量删除应付抛帐设定
     * 
     * @param ids 需要删除的应付抛帐设定主键集合
     * @return 结果
     */
    public int deletePayamtRulesByIds(String[] ids);

    /**
     * 删除应付抛帐设定信息
     * 
     * @param id 应付抛帐设定主键
     * @return 结果
     */
    public int deletePayamtRulesById(String id);
    /**
     * 查询抛帐规则
     *
     * @param code 应付抛帐设定
     * @return 应付抛帐设定集合
     */
    public List<PayamtRules> selectPayamtRulesByCode(String code);
}
