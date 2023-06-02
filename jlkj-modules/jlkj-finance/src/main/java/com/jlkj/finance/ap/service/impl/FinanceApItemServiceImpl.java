package com.jlkj.finance.ap.service.impl;

import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.common.core.utils.uuid.IdUtils;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.finance.ap.domain.FinanceApItem;
import com.jlkj.finance.ap.mapper.FinanceApItemMapper;
import com.jlkj.finance.ap.service.IFinanceApItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 报支类别集团设置Service业务层处理
 *
 * @author SunXuTong
 * @date 2023-05-31
 */
@Service
public class FinanceApItemServiceImpl implements IFinanceApItemService
{
    @Autowired
    private FinanceApItemMapper financeApItemMapper;

    /**
     * 查询报支类别集团设置
     *
     * @param id 报支类别集团设置主键
     * @return 报支类别集团设置
     */
    @Override
    public FinanceApItem selectFinanceApItemById(String id)
    {
        return financeApItemMapper.selectFinanceApItemById(id);
    }

    /**
     * 查询报支类别集团设置列表
     *
     * @param financeApItem 报支类别集团设置
     * @return 报支类别集团设置
     */
    @Override
    public List<FinanceApItem> selectFinanceApItemList(FinanceApItem financeApItem)
    {
        return financeApItemMapper.selectFinanceApItemList(financeApItem);
    }

    /**
     * 新增报支类别集团设置
     *
     * @param financeApItem 报支类别集团设置
     * @return 结果
     */
    @Override
    public int insertFinanceApItem(FinanceApItem financeApItem)
    {
        financeApItem.setId(IdUtils.fastSimpleUUID());
        financeApItem.setCreateTime(DateUtils.getNowDate());
        financeApItem.setCreateBy(SecurityUtils.getUsername());
        financeApItem.setCreateName(SecurityUtils.getNickName());
        return financeApItemMapper.insertFinanceApItem(financeApItem);
    }

    /**
     * 修改报支类别集团设置
     *
     * @param financeApItem 报支类别集团设置
     * @return 结果
     */
    @Override
    public int updateFinanceApItem(FinanceApItem financeApItem)
    {
        financeApItem.setUpdateTime(DateUtils.getNowDate());
        financeApItem.setUpdateBy(SecurityUtils.getUsername());
        financeApItem.setUpdateName(SecurityUtils.getNickName());
        return financeApItemMapper.updateFinanceApItem(financeApItem);
    }

    /**
     * 批量删除报支类别集团设置
     *
     * @param ids 需要删除的报支类别集团设置主键
     * @return 结果
     */
    @Override
    public int deleteFinanceApItemByIds(String[] ids)
    {
        return financeApItemMapper.deleteFinanceApItemByIds(ids);
    }

    /**
     * 删除报支类别集团设置信息
     *
     * @param id 报支类别集团设置主键
     * @return 结果
     */
    @Override
    public int deleteFinanceApItemById(String id)
    {
        return financeApItemMapper.deleteFinanceApItemById(id);
    }
}
