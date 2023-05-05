package com.jlkj.finance.aa.service;

import java.util.List;
import com.jlkj.finance.aa.domain.FinanceAaVoucherBackup;

/**
 * 凭证维护-主备份Service接口
 * 
 * @author jlkj
 * @date 2023-05-04
 */
public interface IFinanceAaVoucherBackupService 
{
    /**
     * 查询凭证维护-主备份
     * 
     * @param id 凭证维护-主备份主键
     * @return 凭证维护-主备份
     */
    public FinanceAaVoucherBackup selectFinanceAaVoucherBackupById(String id);

    /**
     * 查询凭证维护-主备份列表
     * 
     * @param financeAaVoucherBackup 凭证维护-主备份
     * @return 凭证维护-主备份集合
     */
    public List<FinanceAaVoucherBackup> selectFinanceAaVoucherBackupList(FinanceAaVoucherBackup financeAaVoucherBackup);

    /**
     * 新增凭证维护-主备份
     * 
     * @param financeAaVoucherBackup 凭证维护-主备份
     * @return 结果
     */
    public int insertFinanceAaVoucherBackup(FinanceAaVoucherBackup financeAaVoucherBackup);

    /**
     * 修改凭证维护-主备份
     * 
     * @param financeAaVoucherBackup 凭证维护-主备份
     * @return 结果
     */
    public int updateFinanceAaVoucherBackup(FinanceAaVoucherBackup financeAaVoucherBackup);

    /**
     * 批量删除凭证维护-主备份
     * 
     * @param ids 需要删除的凭证维护-主备份主键集合
     * @return 结果
     */
    public int deleteFinanceAaVoucherBackupByIds(String[] ids);

    /**
     * 删除凭证维护-主备份信息
     * 
     * @param id 凭证维护-主备份主键
     * @return 结果
     */
    public int deleteFinanceAaVoucherBackupById(String id);
}
