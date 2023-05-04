package com.jlkj.finance.aa.mapper;

import java.util.List;
import com.jlkj.finance.aa.domain.FinanceAaVoucherBackup;
import com.jlkj.finance.aa.domain.FinanceAaVoucherDetail;
import com.jlkj.finance.aa.domain.FinanceAaVoucherDetailBackup;

/**
 * 凭证维护-主备份Mapper接口
 * 
 * @author jlkj
 * @date 2023-04-28
 */
public interface FinanceAaVoucherBackupMapper 
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
     * 删除凭证维护-主备份
     * 
     * @param id 凭证维护-主备份主键
     * @return 结果
     */
    public int deleteFinanceAaVoucherBackupById(String id);

    /**
     * 批量删除凭证维护-主备份
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFinanceAaVoucherBackupByIds(String[] ids);

    /**
     * 批量删除凭证维护-明细备份
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFinanceAaVoucherDetailBackupByVoucherNos(String[] ids);
    
    /**
     * 批量新增凭证维护-明细备份
     * 
     * @param financeAaVoucherDetailBackupList 凭证维护-明细备份列表
     * @return 结果
     */
    public int batchFinanceAaVoucherDetailBackup(List<FinanceAaVoucherDetail> financeAaVoucherDetailBackupList);
    

    /**
     * 通过凭证维护-主备份主键删除凭证维护-明细备份信息
     * 
     * @param id 凭证维护-主备份ID
     * @return 结果
     */
    public int deleteFinanceAaVoucherDetailBackupByVoucherNo(String id);
}
