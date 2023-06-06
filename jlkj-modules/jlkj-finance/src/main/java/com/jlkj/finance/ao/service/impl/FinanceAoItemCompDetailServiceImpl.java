package com.jlkj.finance.ao.service.impl;

import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.finance.ao.domain.FinanceAoItemCompDetail;
import com.jlkj.finance.ao.mapper.FinanceAoItemCompDetailMapper;
import com.jlkj.finance.ao.service.IFinanceAoItemCompDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 报支类别公司设定明细档Service业务层处理
 *
 * @author 116514
 * @date 2023-05-30
 */
@Service
public class FinanceAoItemCompDetailServiceImpl implements IFinanceAoItemCompDetailService
{
    @Autowired
    private FinanceAoItemCompDetailMapper financeAoItemCompDetailMapper;

    /**
     * 查询报支类别公司设定明细档
     *
     * @param id 报支类别公司设定明细档主键
     * @return 报支类别公司设定明细档
     */
    @Override
    public FinanceAoItemCompDetail selectFinanceAoItemCompDetailById(String id)
    {
        return financeAoItemCompDetailMapper.selectFinanceAoItemCompDetailById(id);
    }

    /**
     * 查询报支类别公司设定明细档列表
     *
     * @param financeAoItemCompDetail 报支类别公司设定明细档
     * @return 报支类别公司设定明细档
     */
    @Override
    public List<FinanceAoItemCompDetail> selectFinanceAoItemCompDetailList(FinanceAoItemCompDetail financeAoItemCompDetail)
    {
        return financeAoItemCompDetailMapper.selectFinanceAoItemCompDetailList(financeAoItemCompDetail);
    }

    /**
     * 新增报支类别公司设定明细档
     *
     * @param financeAoItemCompDetail 报支类别公司设定明细档
     * @return 结果
     */
    @Override
    public int insertFinanceAoItemCompDetail(FinanceAoItemCompDetail financeAoItemCompDetail)
    {
        financeAoItemCompDetail.setCreateTime(DateUtils.getNowDate());
        return financeAoItemCompDetailMapper.insertFinanceAoItemCompDetail(financeAoItemCompDetail);
    }

    /**
     * 修改报支类别公司设定明细档
     *
     * @param financeAoItemCompDetail 报支类别公司设定明细档
     * @return 结果
     */
    @Override
    public int updateFinanceAoItemCompDetail(FinanceAoItemCompDetail financeAoItemCompDetail)
    {
        financeAoItemCompDetail.setUpdateBy(SecurityUtils.getUsername());
        financeAoItemCompDetail.setUpdateName(SecurityUtils.getNickName());
        financeAoItemCompDetail.setUpdateTime(DateUtils.getNowDate());
        return financeAoItemCompDetailMapper.updateFinanceAoItemCompDetail(financeAoItemCompDetail);
    }

    /**
     * 批量删除报支类别公司设定明细档
     *
     * @param ids 需要删除的报支类别公司设定明细档主键
     * @return 结果
     */
    @Override
    public int deleteFinanceAoItemCompDetailByIds(String[] ids)
    {
        return financeAoItemCompDetailMapper.deleteFinanceAoItemCompDetailByIds(ids);
    }

    /**
     * 删除报支类别公司设定明细档信息
     *
     * @param id 报支类别公司设定明细档主键
     * @return 结果
     */
    @Override
    public int deleteFinanceAoItemCompDetailById(String id)
    {
        return financeAoItemCompDetailMapper.deleteFinanceAoItemCompDetailById(id);
    }
}
