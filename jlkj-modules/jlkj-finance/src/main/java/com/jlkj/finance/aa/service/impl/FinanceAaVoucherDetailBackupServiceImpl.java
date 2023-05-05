package com.jlkj.finance.aa.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jlkj.finance.aa.mapper.FinanceAaVoucherDetailBackupMapper;
import com.jlkj.finance.aa.domain.FinanceAaVoucherDetailBackup;
import com.jlkj.finance.aa.service.IFinanceAaVoucherDetailBackupService;

/**
 * 凭证维护-明细备份Service业务层处理
 *
 * @author jlkj
 * @date 2023-04-28
 */
@Service
public class FinanceAaVoucherDetailBackupServiceImpl
        implements IFinanceAaVoucherDetailBackupService
{
    @Autowired
    private FinanceAaVoucherDetailBackupMapper financeAaVoucherDetailBackupMapper;

    /**
     * 查询凭证维护-明细备份
     *
     * @param id 凭证维护-明细备份主键
     * @return 凭证维护-明细备份
     */
    @Override
    public FinanceAaVoucherDetailBackup selectFinanceAaVoucherDetailBackupById(String id)
    {
        return financeAaVoucherDetailBackupMapper.selectFinanceAaVoucherDetailBackupById(id);
    }

    /**
     * 查询凭证维护-明细备份列表
     *
     * @param financeAaVoucherDetailBackup 凭证维护-明细备份
     * @return 凭证维护-明细备份
     */
    @Override
    public List<FinanceAaVoucherDetailBackup> selectFinanceAaVoucherDetailBackupList(FinanceAaVoucherDetailBackup financeAaVoucherDetailBackup)
    {
        return financeAaVoucherDetailBackupMapper.selectFinanceAaVoucherDetailBackupList(financeAaVoucherDetailBackup);
    }

    /**
     * 新增凭证维护-明细备份
     *
     * @param financeAaVoucherDetailBackup 凭证维护-明细备份
     * @return 结果
     */
    @Override
    public int insertFinanceAaVoucherDetailBackup(FinanceAaVoucherDetailBackup financeAaVoucherDetailBackup)
    {
        return financeAaVoucherDetailBackupMapper.insertFinanceAaVoucherDetailBackup(financeAaVoucherDetailBackup);
    }

    /**
     * 修改凭证维护-明细备份
     *
     * @param financeAaVoucherDetailBackup 凭证维护-明细备份
     * @return 结果
     */
    @Override
    public int updateFinanceAaVoucherDetailBackup(FinanceAaVoucherDetailBackup financeAaVoucherDetailBackup)
    {
        return financeAaVoucherDetailBackupMapper.updateFinanceAaVoucherDetailBackup(financeAaVoucherDetailBackup);
    }

    /**
     * 批量删除凭证维护-明细备份
     *
     * @param ids 需要删除的凭证维护-明细备份主键
     * @return 结果
     */
    @Override
    public int deleteFinanceAaVoucherDetailBackupByIds(String[] ids)
    {
        return financeAaVoucherDetailBackupMapper.deleteFinanceAaVoucherDetailBackupByIds(ids);
    }

    /**
     * 删除凭证维护-明细备份信息
     *
     * @param id 凭证维护-明细备份主键
     * @return 结果
     */
    @Override
    public int deleteFinanceAaVoucherDetailBackupById(String id)
    {
        return financeAaVoucherDetailBackupMapper.deleteFinanceAaVoucherDetailBackupById(id);
    }
}
