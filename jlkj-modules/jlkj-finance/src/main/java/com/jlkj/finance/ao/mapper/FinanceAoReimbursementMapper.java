package com.jlkj.finance.ao.mapper;

import java.util.List;
import com.jlkj.finance.ao.domain.FinanceAoReimbursement;
import org.springframework.stereotype.Repository;

/**
 * 费用报销主档Mapper接口
 * 
 * @author 265799
 * @date 2023-06-27
 */
@Repository
public interface FinanceAoReimbursementMapper 
{
    /**
     * 查询费用报销主档
     * 
     * @param id 费用报销主档主键
     * @return 费用报销主档
     */
    public FinanceAoReimbursement selectFinanceAoReimbursementById(String id);

    /**
     * 查询费用报销主档列表
     * 
     * @param financeAoReimbursement 费用报销主档
     * @return 费用报销主档集合
     */
    public List<FinanceAoReimbursement> selectFinanceAoReimbursementList(FinanceAoReimbursement financeAoReimbursement);

    /**
     * 新增费用报销主档
     * 
     * @param financeAoReimbursement 费用报销主档
     * @return 结果
     */
    public int insertFinanceAoReimbursement(FinanceAoReimbursement financeAoReimbursement);

    /**
     * 修改费用报销主档
     * 
     * @param financeAoReimbursement 费用报销主档
     * @return 结果
     */
    public int updateFinanceAoReimbursement(FinanceAoReimbursement financeAoReimbursement);

    /**
     * 删除费用报销主档
     * 
     * @param id 费用报销主档主键
     * @return 结果
     */
    public int deleteFinanceAoReimbursementById(String id);

    /**
     * 批量删除费用报销主档
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFinanceAoReimbursementByIds(String[] ids);
}
