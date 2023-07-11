package com.jlkj.human.hs.mapper;

import com.jlkj.human.hs.domain.PayamtRules;

import java.util.List;

/**
 * 应付抛帐设定Mapper接口
 * 
 * @author 116519
 * @date 2023-06-20
 */
public interface PayamtRulesMapper 
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
     * 修改应付抛帐设定
     * 
     * @param payamtRules 应付抛帐设定
     * @return 结果
     */
    public int updatePayamtRules(PayamtRules payamtRules);

    /**
     * 删除应付抛帐设定
     * 
     * @param id 应付抛帐设定主键
     * @return 结果
     */
    public int deletePayamtRulesById(String id);

    /**
     * 批量删除应付抛帐设定
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePayamtRulesByIds(String[] ids);
    /**
     * 更具代码查询应付抛帐设定
     *
     * @param payamtRules 应付抛帐设定
     * @return 应付抛帐设定集合
     */
    public List<PayamtRules> selectPayamtRulesByCode(String code);
}
