package com.jlkj.finance.ao.mapper;

import com.jlkj.finance.ao.domain.FinanceAoCard;

import java.util.List;

/**
 * 个人信息设置Mapper接口
 * 
 * @author SunXuTong
 * @date 2023-05-08
 */
public interface FinanceAoCardMapper 
{
    /**
     * 查询个人信息设置
     * 
     * @param uuid 个人信息设置主键
     * @return 个人信息设置
     */
    public FinanceAoCard selectFinanceAoCardByUuid(String uuid);

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
     * 删除个人信息设置
     * 
     * @param uuid 个人信息设置主键
     * @return 结果
     */
    public int deleteFinanceAoCardByUuid(String uuid);

    /**
     * 批量删除个人信息设置
     * 
     * @param uuids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFinanceAoCardByUuids(String[] uuids);
}
