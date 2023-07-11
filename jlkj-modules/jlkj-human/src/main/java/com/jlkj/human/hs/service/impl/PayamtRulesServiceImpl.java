package com.jlkj.human.hs.service.impl;

import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.common.core.utils.uuid.IdUtils;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.human.hs.domain.PayamtRules;
import com.jlkj.human.hs.mapper.PayamtRulesMapper;
import com.jlkj.human.hs.service.IPayamtRulesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 应付抛帐设定Service业务层处理
 *
 * @author 116519
 * @date 2023-06-20
 */
@Service
public class PayamtRulesServiceImpl implements IPayamtRulesService
{
    @Autowired
    private PayamtRulesMapper payamtRulesMapper;

    /**
     * 查询应付抛帐设定
     *
     * @param id 应付抛帐设定主键
     * @return 应付抛帐设定
     */
    @Override
    public PayamtRules selectPayamtRulesById(String id)
    {
        return payamtRulesMapper.selectPayamtRulesById(id);
    }

    /**
     * 查询应付抛帐设定列表
     *
     * @param payamtRules 应付抛帐设定
     * @return 应付抛帐设定
     */
    @Override
    public List<PayamtRules> selectPayamtRulesList(PayamtRules payamtRules)
    {
        return payamtRulesMapper.selectPayamtRulesList(payamtRules);
    }
    /**
     * 查询应付抛帐设定列表
     *
     * @param payamtRules 应付抛帐设定
     * @return 应付抛帐设定
     */
    @Override
    public List<PayamtRules> selectPayamtRules(PayamtRules payamtRules)
    {
        return payamtRulesMapper.selectPayamtRules(payamtRules);
    }
    /**
     * 新增应付抛帐设定
     *
     * @param payamtRules 应付抛帐设定
     * @return 结果
     */
    @Override
    public int insertPayamtRules(PayamtRules payamtRules)
    {
        payamtRules.setCreateTime(DateUtils.getNowDate());
        return payamtRulesMapper.insertPayamtRules(payamtRules);
    }
    /**
     * 新增抛帐设定
     *
     * @param payamtRulesList 抛帐设定列表
     * @return 结果
     */
    @Override
    public int insertPayamtRules(List<PayamtRules> payamtRulesList)
    {
        int count = 0;
        for(PayamtRules payamtRules :payamtRulesList){
            if(payamtRules.getId()!=null){
                payamtRules.setCreatId(SecurityUtils.getUserId().toString());
                payamtRules.setCreatNo(SecurityUtils.getUsername());
                payamtRules.setCreatBy(SecurityUtils.getNickName());
                payamtRules.setCreateTime(new Date());
                count= payamtRulesMapper.updatePayamtRules(payamtRules);

            }else{
                payamtRules.setId(IdUtils.simpleUUID());
                count= payamtRulesMapper.insertPayamtRules(payamtRules);
            }
        }
        return count;
    }
    /**
     * 修改应付抛帐设定
     *
     * @param payamtRules 应付抛帐设定
     * @return 结果
     */
    @Override
    public int updatePayamtRules(PayamtRules payamtRules)
    {
        return payamtRulesMapper.updatePayamtRules(payamtRules);
    }

    /**
     * 批量删除应付抛帐设定
     *
     * @param ids 需要删除的应付抛帐设定主键
     * @return 结果
     */
    @Override
    public int deletePayamtRulesByIds(String[] ids)
    {
        return payamtRulesMapper.deletePayamtRulesByIds(ids);
    }

    /**
     * 删除应付抛帐设定信息
     *
     * @param id 应付抛帐设定主键
     * @return 结果
     */
    @Override
    public int deletePayamtRulesById(String id)
    {
        return payamtRulesMapper.deletePayamtRulesById(id);
    }

    /**
     * 查询抛帐规则
     *
     * @param code 应付抛帐设定
     * @return 应付抛帐设定
     */
    @Override
    public List<PayamtRules> selectPayamtRulesByCode(String code)
    {
        return payamtRulesMapper.selectPayamtRulesByCode(code);
    }
}
