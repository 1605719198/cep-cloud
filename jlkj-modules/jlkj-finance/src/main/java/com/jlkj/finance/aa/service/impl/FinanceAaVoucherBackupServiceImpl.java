package com.jlkj.finance.aa.service.impl;

import java.util.List;
import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.common.core.utils.uuid.UUID;
import com.jlkj.finance.aa.domain.FinanceAaVoucherDetail;
import com.jlkj.finance.aa.service.IFinanceAaVoucherBackupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import com.jlkj.common.core.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import com.jlkj.finance.aa.domain.FinanceAaVoucherDetailBackup;
import com.jlkj.finance.aa.mapper.FinanceAaVoucherBackupMapper;
import com.jlkj.finance.aa.domain.FinanceAaVoucherBackup;


/**
 * 凭证维护-主备份Service业务层处理
 *
 * @author jlkj
 * @date 2023-04-28
 */
@Service
public class FinanceAaVoucherBackupServiceImpl
        implements IFinanceAaVoucherBackupService
{
    @Autowired
    private FinanceAaVoucherBackupMapper financeAaVoucherBackupMapper;

    /**
     * 查询凭证维护-主备份
     *
     * @param id 凭证维护-主备份主键
     * @return 凭证维护-主备份
     */
    @Override
    public FinanceAaVoucherBackup selectFinanceAaVoucherBackupById(String id)
    {
        return financeAaVoucherBackupMapper.selectFinanceAaVoucherBackupById(id);
    }

    /**
     * 查询凭证维护-主备份列表
     *
     * @param financeAaVoucherBackup 凭证维护-主备份
     * @return 凭证维护-主备份
     */
    @Override
    public List<FinanceAaVoucherBackup> selectFinanceAaVoucherBackupList(FinanceAaVoucherBackup financeAaVoucherBackup)
    {
        return financeAaVoucherBackupMapper.selectFinanceAaVoucherBackupList(financeAaVoucherBackup);
    }

    /**
     * 新增凭证维护-主备份
     *
     * @param financeAaVoucherBackup 凭证维护-主备份
     * @return 结果
     */
    @Override
    public int insertFinanceAaVoucherBackup(FinanceAaVoucherBackup financeAaVoucherBackup)
    {
        int rows = 0;
        try{
            //增删改方法
            financeAaVoucherBackup.setCreateTime(DateUtils.getNowDate());
             rows = financeAaVoucherBackupMapper.insertFinanceAaVoucherBackup(financeAaVoucherBackup);
            insertFinanceAaVoucherDetailBackup(financeAaVoucherBackup);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return rows;
    }

    /**
     * 修改凭证维护-主备份
     *
     * @param financeAaVoucherBackup 凭证维护-主备份
     * @return 结果
     */

    @Override
    public int updateFinanceAaVoucherBackup(FinanceAaVoucherBackup financeAaVoucherBackup)
    {
        financeAaVoucherBackupMapper.deleteFinanceAaVoucherDetailBackupByVoucherNo(financeAaVoucherBackup.getId());
        insertFinanceAaVoucherDetailBackup(financeAaVoucherBackup);
        return financeAaVoucherBackupMapper.updateFinanceAaVoucherBackup(financeAaVoucherBackup);
    }

    /**
     * 批量删除凭证维护-主备份
     *
     * @param ids 需要删除的凭证维护-主备份主键
     * @return 结果
     */

    @Override
    public int deleteFinanceAaVoucherBackupByIds(String[] ids)
    {
        financeAaVoucherBackupMapper.deleteFinanceAaVoucherDetailBackupByVoucherNos(ids);
        return financeAaVoucherBackupMapper.deleteFinanceAaVoucherBackupByIds(ids);
    }

    /**
     * 删除凭证维护-主备份信息
     *
     * @param id 凭证维护-主备份主键
     * @return 结果
     */

    @Override
    public int deleteFinanceAaVoucherBackupById(String id)
    {
        financeAaVoucherBackupMapper.deleteFinanceAaVoucherDetailBackupByVoucherNo(id);
        return financeAaVoucherBackupMapper.deleteFinanceAaVoucherBackupById(id);
    }

    /**
     * 新增凭证维护-明细备份信息
     *
     * @param financeAaVoucherBackup 凭证维护-主备份对象
     */
    public void insertFinanceAaVoucherDetailBackup(FinanceAaVoucherBackup financeAaVoucherBackup)
    {
        List<FinanceAaVoucherDetail> financeAaVoucherDetailBackupList = financeAaVoucherBackup.getDetailList();
        String id = financeAaVoucherBackup.getVoucherNo();
        if (StringUtils.isNotNull(financeAaVoucherDetailBackupList))
        {
            List<FinanceAaVoucherDetail> list = new ArrayList<FinanceAaVoucherDetail>();
            for (FinanceAaVoucherDetail financeAaVoucherDetailBackup : financeAaVoucherDetailBackupList)
            {
                financeAaVoucherDetailBackup.setVoucherNo(id);
                financeAaVoucherDetailBackup.setId(UUID.fastUUID().toString());
                list.add(financeAaVoucherDetailBackup);
            }
            if (list.size() > 0)
            {
                financeAaVoucherBackupMapper.batchFinanceAaVoucherDetailBackup(list);
            }
        }
    }
}
