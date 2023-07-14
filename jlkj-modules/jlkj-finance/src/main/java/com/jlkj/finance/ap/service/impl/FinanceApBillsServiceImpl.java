package com.jlkj.finance.ap.service.impl;

import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.common.core.utils.uuid.IdUtils;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.finance.ap.domain.FinanceApBills;
import com.jlkj.finance.ap.mapper.FinanceApBillsMapper;
import com.jlkj.finance.ap.service.IFinanceApBillsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 发票信息Service业务层处理
 *
 * @author 265799
 * @date 2023-07-13
 * @version 1.0
 */
@Service
public class FinanceApBillsServiceImpl implements IFinanceApBillsService
{
    @Autowired
    private FinanceApBillsMapper financeApBillsMapper;

    /**
     * 查询发票信息
     *
     * @author 265799
     * @date 2023-07-13
     * @param id 发票信息主键
     * @return 发票信息
     */
    @Override
    public FinanceApBills selectFinanceApBillsById(String id)
    {
        return financeApBillsMapper.selectFinanceApBillsById(id);
    }

    /**
     * 查询发票信息列表
     *
     * @author 265799
     * @date 2023-07-13
     * @param financeApBills 发票信息
     * @return 发票信息
     */
    @Override
    public List<FinanceApBills> selectFinanceApBillsList(FinanceApBills financeApBills)
    {
        return financeApBillsMapper.selectFinanceApBillsList(financeApBills);
    }

    /**
     * 新增发票信息
     *
     * @author 265799
     * @date 2023-07-13
     * @param financeApBills 发票信息
     * @return 结果
     */
    @Override
    public int insertFinanceApBills(FinanceApBills financeApBills)
    {
        financeApBills.setId(IdUtils.simpleUUID());
        // 0-未报销 1-已报销
        financeApBills.setStatus("0");
        financeApBills.setCreateBy(SecurityUtils.getUsername());
        financeApBills.setCreateName(SecurityUtils.getNickName());
        financeApBills.setCreateTime(DateUtils.getNowDate());
        return financeApBillsMapper.insertFinanceApBills(financeApBills);
    }

    /**
     * 修改发票信息
     *
     * @author 265799
     * @date 2023-07-13
     * @param financeApBills 发票信息
     * @return 结果
     */
    @Override
    public int updateFinanceApBills(FinanceApBills financeApBills)
    {
        return financeApBillsMapper.updateFinanceApBills(financeApBills);
    }

    /**
     * 批量删除发票信息
     *
     * @author 265799
     * @date 2023-07-13
     * @param ids 需要删除的发票信息主键
     * @return 结果
     */
    @Override
    public int deleteFinanceApBillsByIds(String[] ids)
    {
        return financeApBillsMapper.deleteFinanceApBillsByIds(ids);
    }

    /**
     * 删除发票信息信息
     *
     * @author 265799
     * @date 2023-07-13
     * @param id 发票信息主键
     * @return 结果
     */
    @Override
    public int deleteFinanceApBillsById(String id)
    {
        return financeApBillsMapper.deleteFinanceApBillsById(id);
    }
}
