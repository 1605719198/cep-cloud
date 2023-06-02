package com.jlkj.finance.ao.service.impl;

import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.common.core.utils.uuid.IdUtils;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.finance.ao.domain.FinanceAoItemDetail;
import com.jlkj.finance.ao.mapper.FinanceAoItemDetailMapper;
import com.jlkj.finance.ao.service.IFinanceAoItemDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 报支管理-报支类别集团设定明细档Service业务层处理
 *
 * @author 116514
 * @date 2023-05-21
 */
@Service
public class FinanceAoItemDetailServiceImpl implements IFinanceAoItemDetailService
{
    @Autowired
    private FinanceAoItemDetailMapper financeAoItemDetailMapper;

    /**
     * 查询报支管理-报支类别集团设定明细档
     *
     * @param id 报支管理-报支类别集团设定明细档主键
     * @return 报支管理-报支类别集团设定明细档
     */
    @Override
    public FinanceAoItemDetail selectFinanceAoItemDetailById(String id)
    {
        return financeAoItemDetailMapper.selectFinanceAoItemDetailById(id);
    }

    /**
     * 查询报支管理-报支类别集团设定明细档列表
     *
     * @param financeAoItemDetail 报支管理-报支类别集团设定明细档
     * @return 报支管理-报支类别集团设定明细档
     */
    @Override
    public List<FinanceAoItemDetail> selectFinanceAoItemDetailList(FinanceAoItemDetail financeAoItemDetail)
    {
        return financeAoItemDetailMapper.selectFinanceAoItemDetailList(financeAoItemDetail);
    }

    /**
     * 新增报支管理-报支类别集团设定明细档
     *
     * @param financeAoItemDetail 报支管理-报支类别集团设定明细档
     * @return 结果
     */
    @Override
    public int insertFinanceAoItemDetail(FinanceAoItemDetail financeAoItemDetail)
    {
        financeAoItemDetail.setId(IdUtils.simpleUUID());
        financeAoItemDetail.setCreateBy(SecurityUtils.getUsername());
        financeAoItemDetail.setCreateName(SecurityUtils.getNickName());
        financeAoItemDetail.setCreateTime(DateUtils.getNowDate());
        return financeAoItemDetailMapper.insertFinanceAoItemDetail(financeAoItemDetail);
    }

    /**
     * 修改报支管理-报支类别集团设定明细档
     *
     * @param financeAoItemDetail 报支管理-报支类别集团设定明细档
     * @return 结果
     */
    @Override
    public int updateFinanceAoItemDetail(FinanceAoItemDetail financeAoItemDetail)
    {
        financeAoItemDetail.setUpdateBy(SecurityUtils.getUsername());
        financeAoItemDetail.setUpdateName(SecurityUtils.getNickName());
        financeAoItemDetail.setUpdateTime(DateUtils.getNowDate());
        return financeAoItemDetailMapper.updateFinanceAoItemDetail(financeAoItemDetail);
    }

    /**
     * 批量删除报支管理-报支类别集团设定明细档
     *
     * @param ids 需要删除的报支管理-报支类别集团设定明细档主键
     * @return 结果
     */
    @Override
    public int deleteFinanceAoItemDetailByIds(String[] ids)
    {
        return financeAoItemDetailMapper.deleteFinanceAoItemDetailByIds(ids);
    }

    /**
     * 删除报支管理-报支类别集团设定明细档信息
     *
     * @param id 报支管理-报支类别集团设定明细档主键
     * @return 结果
     */
    @Override
    public int deleteFinanceAoItemDetailById(String id)
    {
        return financeAoItemDetailMapper.deleteFinanceAoItemDetailById(id);
    }
}
