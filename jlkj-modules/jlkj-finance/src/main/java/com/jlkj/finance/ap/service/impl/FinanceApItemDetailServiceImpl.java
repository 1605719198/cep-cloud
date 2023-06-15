package com.jlkj.finance.ap.service.impl;

import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.common.core.utils.uuid.IdUtils;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.finance.ap.domain.FinanceApItemDetail;
import com.jlkj.finance.ap.dto.FinanceApItemDetailDTO;
import com.jlkj.finance.ap.mapper.FinanceApItemDetailMapper;
import com.jlkj.finance.ap.service.IFinanceApItemDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 报支类别集团细项Service业务层处理
 *
 * @author SunXuTong
 * @date 2023-05-31
 */
@Service
public class FinanceApItemDetailServiceImpl implements IFinanceApItemDetailService
{
    @Autowired
    private FinanceApItemDetailMapper financeApItemDetailMapper;

    /**
     * 查询报支类别集团细项
     *
     * @param id 报支类别集团细项主键
     * @return 报支类别集团细项
     */
    @Override
    public FinanceApItemDetail selectFinanceApItemDetailById(String id)
    {
        return financeApItemDetailMapper.selectFinanceApItemDetailById(id);
    }

    /**
     * 查询报支类别集团细项列表
     *
     * @param financeApItemDetail 报支类别集团细项
     * @return 报支类别集团细项
     */
    @Override
    public List<FinanceApItemDetail> selectFinanceApItemDetailList(FinanceApItemDetail financeApItemDetail)
    {
        return financeApItemDetailMapper.selectFinanceApItemDetailList(financeApItemDetail);
    }

    /**
     * 查询报支类别集团细项列表
     *
     * @param financeApItemDetail 报支类别集团细项
     * @return 报支类别集团细项
     */
    @Override
    public List<FinanceApItemDetailDTO> selectFinanceApItemDetailList1(FinanceApItemDetail financeApItemDetail)
    {
        return financeApItemDetailMapper.selectFinanceApItemDetailList1(financeApItemDetail);
    }
    /**
     * 新增报支类别集团细项
     *
     * @param financeApItemDetail 报支类别集团细项
     * @return 结果
     */
    @Override
    public int insertFinanceApItemDetail(FinanceApItemDetail financeApItemDetail)
    {
        financeApItemDetail.setId(IdUtils.fastSimpleUUID());
        financeApItemDetail.setCreateTime(DateUtils.getNowDate());
        financeApItemDetail.setCreateBy(SecurityUtils.getUsername());
        financeApItemDetail.setCreateName(SecurityUtils.getNickName());
        return financeApItemDetailMapper.insertFinanceApItemDetail(financeApItemDetail);
    }

    /**
     * 修改报支类别集团细项
     *
     * @param financeApItemDetail 报支类别集团细项
     * @return 结果
     */
    @Override
    public int updateFinanceApItemDetail(FinanceApItemDetail financeApItemDetail)
    {
        financeApItemDetail.setUpdateTime(DateUtils.getNowDate());
        financeApItemDetail.setUpdateBy(SecurityUtils.getUsername());
        financeApItemDetail.setUpdateName(SecurityUtils.getNickName());
        return financeApItemDetailMapper.updateFinanceApItemDetail(financeApItemDetail);
    }

    /**
     * 批量删除报支类别集团细项
     *
     * @param ids 需要删除的报支类别集团细项主键
     * @return 结果
     */
    @Override
    public int deleteFinanceApItemDetailByIds(String[] ids)
    {
        return financeApItemDetailMapper.deleteFinanceApItemDetailByIds(ids);
    }

    /**
     * 删除报支类别集团细项信息
     *
     * @param id 报支类别集团细项主键
     * @return 结果
     */
    @Override
    public int deleteFinanceApItemDetailById(String id)
    {
        return financeApItemDetailMapper.deleteFinanceApItemDetailById(id);
    }
}
