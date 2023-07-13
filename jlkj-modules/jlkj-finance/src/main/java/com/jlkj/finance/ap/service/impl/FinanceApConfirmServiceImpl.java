package com.jlkj.finance.ap.service.impl;

import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.finance.ap.domain.FinanceApConfirm;
import com.jlkj.finance.ap.domain.FinanceApConfirmDetail;
import com.jlkj.finance.ap.mapper.FinanceApConfirmMapper;
import com.jlkj.finance.ap.service.IFinanceApConfirmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 会计确认主档Service业务层处理
 *
 * @author 265799
 * @date 2023-07-10
 * @version 1.0
 */
@Service
public class FinanceApConfirmServiceImpl implements IFinanceApConfirmService
{
    @Autowired
    private FinanceApConfirmMapper financeApConfirmMapper;

    /**
     * 查询会计确认主档
     *
     * @author 265799
     * @date 2023-07-10
     * @param id 会计确认主档主键
     * @return 会计确认主档
     */
    @Override
    public FinanceApConfirm selectFinanceApConfirmById(String id)
    {
        return financeApConfirmMapper.selectFinanceApConfirmById(id);
    }

    /**
     * 查询会计确认主档列表
     *
     * @author 265799
     * @date 2023-07-10
     * @param financeApConfirm 会计确认主档
     * @return 会计确认主档
     */
    @Override
    public List<FinanceApConfirm> selectFinanceApConfirmList(FinanceApConfirm financeApConfirm)
    {
        return financeApConfirmMapper.selectFinanceApConfirmList(financeApConfirm);
    }

    /**
     * 新增会计确认主档
     *
     * @author 265799
     * @date 2023-07-10
     * @param financeApConfirm 会计确认主档
     * @return 结果
     */

    @Override
    public int insertFinanceApConfirm(FinanceApConfirm financeApConfirm)
    {
        financeApConfirm.setCreateTime(DateUtils.getNowDate());
        int rows = financeApConfirmMapper.insertFinanceApConfirm(financeApConfirm);
        insertFinanceApConfirmDetail(financeApConfirm);
        return rows;
    }

    /**
     * 修改会计确认主档
     *
     * @author 265799
     * @date 2023-07-10
     * @param financeApConfirm 会计确认主档
     * @return 结果
     */

    @Override
    public int updateFinanceApConfirm(FinanceApConfirm financeApConfirm)
    {
        financeApConfirm.setUpdateTime(DateUtils.getNowDate());
        financeApConfirmMapper.deleteFinanceApConfirmDetailByBillId(financeApConfirm.getId());
        insertFinanceApConfirmDetail(financeApConfirm);
        return financeApConfirmMapper.updateFinanceApConfirm(financeApConfirm);
    }

    /**
     * 批量删除会计确认主档
     *
     * @author 265799
     * @date 2023-07-10
     * @param ids 需要删除的会计确认主档主键
     * @return 结果
     */

    @Override
    public int deleteFinanceApConfirmByIds(String[] ids)
    {
        financeApConfirmMapper.deleteFinanceApConfirmDetailByBillIds(ids);
        return financeApConfirmMapper.deleteFinanceApConfirmByIds(ids);
    }

    /**
     * 删除会计确认主档信息
     *
     * @author 265799
     * @date 2023-07-10
     * @param id 会计确认主档主键
     * @return 结果
     */

    @Override
    public int deleteFinanceApConfirmById(String id)
    {
        financeApConfirmMapper.deleteFinanceApConfirmDetailByBillId(id);
        return financeApConfirmMapper.deleteFinanceApConfirmById(id);
    }

    /**
     * 新增会计确认明细信息
     *
     * @author 265799
     * @date 2023-07-10
     * @param financeApConfirm 会计确认主档对象
     */
    public void insertFinanceApConfirmDetail(FinanceApConfirm financeApConfirm)
    {
        List<FinanceApConfirmDetail> financeApConfirmDetailList = financeApConfirm.getFinanceApConfirmDetailList();
        String id = financeApConfirm.getId();
        if (StringUtils.isNotNull(financeApConfirmDetailList))
        {
            List<FinanceApConfirmDetail> list = new ArrayList<FinanceApConfirmDetail>();
            for (FinanceApConfirmDetail financeApConfirmDetail : financeApConfirmDetailList)
            {
                financeApConfirmDetail.setBillId(id);
                list.add(financeApConfirmDetail);
            }
            if (list.size() > 0)
            {
                financeApConfirmMapper.batchFinanceApConfirmDetail(list);
            }
        }
    }
}
