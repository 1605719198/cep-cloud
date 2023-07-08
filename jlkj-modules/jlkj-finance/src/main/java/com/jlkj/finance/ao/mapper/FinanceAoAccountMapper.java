package com.jlkj.finance.ao.mapper;

import com.jlkj.finance.ao.domain.FinanceAoAccount;
import com.jlkj.finance.ao.domain.FinanceAoAccountDetail;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 会计确认主档Mapper接口
 * 
 * @author 265799
 * @date 2023-07-04
 */
@Repository
public interface FinanceAoAccountMapper 
{
    /**
     * 查询会计确认主档
     * 
     * @param id 会计确认主档主键
     * @return 会计确认主档
     */
    public FinanceAoAccount selectFinanceAoAccountById(String id);

    /**
     * 查询会计确认主档列表
     * 
     * @param financeAoAccount 会计确认主档
     * @return 会计确认主档集合
     */
    public List<FinanceAoAccount> selectFinanceAoAccountList(FinanceAoAccount financeAoAccount);

    /**
     * 查询会计明细确认主档列表
     *
     * @param financeAoAccount 会计确认主档
     * @return 会计确认主档集合
     */
    public List<FinanceAoAccount> selectDetailList(FinanceAoAccount financeAoAccount);

    /**
     * 新增会计确认主档
     * 
     * @param financeAoAccount 会计确认主档
     * @return 结果
     */
    public int insertFinanceAoAccount(FinanceAoAccount financeAoAccount);

    /**
     * 修改会计确认主档
     * 
     * @param financeAoAccount 会计确认主档
     * @return 结果
     */
    public int updateFinanceAoAccount(FinanceAoAccount financeAoAccount);

    /**
     * 删除会计确认主档
     * 
     * @param id 会计确认主档主键
     * @return 结果
     */
    public int deleteFinanceAoAccountById(String id);

    /**
     * 批量删除会计确认主档
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFinanceAoAccountByIds(String[] ids);

    /**
     * 批量删除会计处理明细
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFinanceAoAccountDetailByAccountIds(String[] ids);
    
    /**
     * 批量新增会计处理明细
     * 
     * @param financeAoAccountDetailList 会计处理明细列表
     * @return 结果
     */
    public int batchFinanceAoAccountDetail(List<FinanceAoAccountDetail> financeAoAccountDetailList);
    

    /**
     * 通过会计确认主档主键删除会计处理明细信息
     * 
     * @param id 会计确认主档ID
     * @return 结果
     */
    public int deleteFinanceAoAccountDetailByAccountId(String id);
}
