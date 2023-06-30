package com.jlkj.finance.ao.mapper;

import com.jlkj.finance.ao.domain.FinanceAoAccount;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 会计确认主档Mapper接口
 * 
 * @author 265799
 * @date 2023-06-25
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
}
