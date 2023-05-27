package com.jlkj.finance.ao.service;

import com.jlkj.finance.ao.domain.FinanceAoCard;

import java.util.List;

/**
 * 个人信息设置Service接口
 * 
 * @author SunXuTong
 * @date 2023-05-08
 */
public interface IFinanceAoCardService 
{
    /**
     * 查询个人信息设置
     * 
     * @param userNo 个人信息工号
     * @return 个人信息设置
     */
    public FinanceAoCard selectFinanceAoCardByUserNo(String userNo);

    /**
     * 查询个人信息设置列表
     * 
     * @param financeAoCard 个人信息设置
     * @return 个人信息设置集合
     */
    public List<FinanceAoCard> selectFinanceAoCardList(FinanceAoCard financeAoCard);

    /**
     * 新增个人信息设置
     * 
     * @param financeAoCard 个人信息设置
     * @return 结果
     */
    public int insertFinanceAoCard(FinanceAoCard financeAoCard);

    /**
     * 修改个人信息设置
     * 
     * @param financeAoCard 个人信息设置
     * @return 结果
     */
    public int updateFinanceAoCard(FinanceAoCard financeAoCard);

    /**
     * 批量删除个人信息设置
     * 
     * @param uuids 需要删除的个人信息设置主键集合
     * @return 结果
     */
    public int deleteFinanceAoCardByUuids(String[] uuids);

    /**
     * 删除个人信息设置信息
     * 
     * @param uuid 个人信息设置主键
     * @return 结果
     */
    public int deleteFinanceAoCardByUuid(String uuid);
}
