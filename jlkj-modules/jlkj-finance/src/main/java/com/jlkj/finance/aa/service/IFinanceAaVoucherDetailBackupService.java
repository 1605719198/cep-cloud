package com.jlkj.finance.aa.service;

import java.util.List;
import com.jlkj.finance.aa.domain.FinanceAaVoucherDetailBackup;

/**
 * 凭证维护-明细备份Service接口
 * 
 * @author jlkj
 * @date 2023-04-28
 */
public interface IFinanceAaVoucherDetailBackupService 
{
    /**
     * 查询凭证维护-明细备份
     * 
     * @param id 凭证维护-明细备份主键
     * @return 凭证维护-明细备份
     */
    public FinanceAaVoucherDetailBackup selectFinanceAaVoucherDetailBackupById(String id);

    /**
     * 查询凭证维护-明细备份列表
     * 
     * @param financeAaVoucherDetailBackup 凭证维护-明细备份
     * @return 凭证维护-明细备份集合
     */
    public List<FinanceAaVoucherDetailBackup> selectFinanceAaVoucherDetailBackupList(FinanceAaVoucherDetailBackup financeAaVoucherDetailBackup);

    /**
     * 新增凭证维护-明细备份
     * 
     * @param financeAaVoucherDetailBackup 凭证维护-明细备份
     * @return 结果
     */
    public int insertFinanceAaVoucherDetailBackup(FinanceAaVoucherDetailBackup financeAaVoucherDetailBackup);

    /**
     * 修改凭证维护-明细备份
     * 
     * @param financeAaVoucherDetailBackup 凭证维护-明细备份
     * @return 结果
     */
    public int updateFinanceAaVoucherDetailBackup(FinanceAaVoucherDetailBackup financeAaVoucherDetailBackup);

    /**
     * 批量删除凭证维护-明细备份
     * 
     * @param ids 需要删除的凭证维护-明细备份主键集合
     * @return 结果
     */
    public int deleteFinanceAaVoucherDetailBackupByIds(String[] ids);

    /**
     * 删除凭证维护-明细备份信息
     * 
     * @param id 凭证维护-明细备份主键
     * @return 结果
     */
    public int deleteFinanceAaVoucherDetailBackupById(String id);
}
