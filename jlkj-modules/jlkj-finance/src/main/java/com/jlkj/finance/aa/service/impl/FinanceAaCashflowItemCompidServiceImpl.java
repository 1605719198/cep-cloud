package com.jlkj.finance.aa.service.impl;

import java.util.List;
import com.jlkj.common.core.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jlkj.finance.aa.mapper.FinanceAaCashflowItemCompidMapper;
import com.jlkj.finance.aa.domain.FinanceAaCashflowItemCompid;
import com.jlkj.finance.aa.service.IFinanceAaCashflowItemCompidService;

/**
 * 现金流量代码公司级-对应会计科目Service业务层处理
 *
 * @author 265799
 * @date 2023-05-04
 */
@Service
public class FinanceAaCashflowItemCompidServiceImpl implements IFinanceAaCashflowItemCompidService
{
    @Autowired
    private FinanceAaCashflowItemCompidMapper financeAaCashflowItemCompidMapper;

    /**
     * 查询现金流量代码公司级-对应会计科目
     *
     * @param id 现金流量代码公司级-对应会计科目主键
     * @return 现金流量代码公司级-对应会计科目
     */
    @Override
    public FinanceAaCashflowItemCompid selectFinanceAaCashflowItemCompidById(String id)
    {
        return financeAaCashflowItemCompidMapper.selectFinanceAaCashflowItemCompidById(id);
    }

    /**
     * 查询现金流量代码公司级-对应会计科目列表
     *
     * @param financeAaCashflowItemCompid 现金流量代码公司级-对应会计科目
     * @return 现金流量代码公司级-对应会计科目
     */
    @Override
    public List<FinanceAaCashflowItemCompid> selectFinanceAaCashflowItemCompidList(FinanceAaCashflowItemCompid financeAaCashflowItemCompid)
    {
        return financeAaCashflowItemCompidMapper.selectFinanceAaCashflowItemCompidList(financeAaCashflowItemCompid);
    }

    /**
     * 新增现金流量代码公司级-对应会计科目
     *
     * @param financeAaCashflowItemCompid 现金流量代码公司级-对应会计科目
     * @return 结果
     */
    @Override
    public int insertFinanceAaCashflowItemCompid(FinanceAaCashflowItemCompid financeAaCashflowItemCompid)
    {
        financeAaCashflowItemCompid.setCreateTime(DateUtils.getNowDate());
        return financeAaCashflowItemCompidMapper.insertFinanceAaCashflowItemCompid(financeAaCashflowItemCompid);
    }

    /**
     * 修改现金流量代码公司级-对应会计科目
     *
     * @param financeAaCashflowItemCompid 现金流量代码公司级-对应会计科目
     * @return 结果
     */
    @Override
    public int updateFinanceAaCashflowItemCompid(FinanceAaCashflowItemCompid financeAaCashflowItemCompid)
    {
        financeAaCashflowItemCompid.setUpdateTime(DateUtils.getNowDate());
        return financeAaCashflowItemCompidMapper.updateFinanceAaCashflowItemCompid(financeAaCashflowItemCompid);
    }

    /**
     * 批量删除现金流量代码公司级-对应会计科目
     *
     * @param ids 需要删除的现金流量代码公司级-对应会计科目主键
     * @return 结果
     */
    @Override
    public int deleteFinanceAaCashflowItemCompidByIds(String[] ids)
    {
        return financeAaCashflowItemCompidMapper.deleteFinanceAaCashflowItemCompidByIds(ids);
    }

    /**
     * 删除现金流量代码公司级-对应会计科目信息
     *
     * @param id 现金流量代码公司级-对应会计科目主键
     * @return 结果
     */
    @Override
    public int deleteFinanceAaCashflowItemCompidById(String id)
    {
        return financeAaCashflowItemCompidMapper.deleteFinanceAaCashflowItemCompidById(id);
    }
}
