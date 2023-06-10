package com.jlkj.finance.ap.service.impl;

import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.common.core.utils.uuid.IdUtils;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.finance.ap.domain.FinanceApItemComp;
import com.jlkj.finance.ap.mapper.FinanceApItemCompMapper;
import com.jlkj.finance.ap.service.IFinanceApItemCompService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 报支类别公司设置Service业务层处理
 *
 * @author SunXuTong
 * @date 2023-06-06
 */
@Service
public class FinanceApItemCompServiceImpl implements IFinanceApItemCompService
{
    @Autowired
    private FinanceApItemCompMapper financeApItemCompMapper;

    /**
     * 查询报支类别公司设置
     *
     * @param id 报支类别公司设置主键
     * @return 报支类别公司设置
     */
    @Override
    public FinanceApItemComp selectFinanceApItemCompById(String id)
    {
        return financeApItemCompMapper.selectFinanceApItemCompById(id);
    }

    /**
     * 查询报支类别公司设置列表
     *
     * @param financeApItemComp 报支类别公司设置
     * @return 报支类别公司设置
     */
    @Override
    public List<FinanceApItemComp> selectFinanceApItemCompList(FinanceApItemComp financeApItemComp)
    {
        return financeApItemCompMapper.selectFinanceApItemCompList(financeApItemComp);
    }

    /**
     * 新增报支类别公司设置
     *
     * @param financeApItemComp 报支类别公司设置
     * @return 结果
     */
    @Override
    public int insertFinanceApItemComp(FinanceApItemComp financeApItemComp)
    {
        financeApItemComp.setId(IdUtils.fastSimpleUUID());
        financeApItemComp.setCreateTime(DateUtils.getNowDate());
        financeApItemComp.setCreateBy(SecurityUtils.getUsername());
        financeApItemComp.setCreateName(SecurityUtils.getNickName());
        return financeApItemCompMapper.insertFinanceApItemComp(financeApItemComp);
    }

    /**
     * 修改报支类别公司设置
     *
     * @param financeApItemComp 报支类别公司设置
     * @return 结果
     */
    @Override
    public int updateFinanceApItemComp(FinanceApItemComp financeApItemComp)
    {
        financeApItemComp.setUpdateTime(DateUtils.getNowDate());
        financeApItemComp.setUpdateBy(SecurityUtils.getUsername());
        financeApItemComp.setUpdateName(SecurityUtils.getNickName());
        return financeApItemCompMapper.updateFinanceApItemComp(financeApItemComp);
    }

    /**
     * 批量删除报支类别公司设置
     *
     * @param ids 需要删除的报支类别公司设置主键
     * @return 结果
     */
    @Override
    public int deleteFinanceApItemCompByIds(String[] ids)
    {
        return financeApItemCompMapper.deleteFinanceApItemCompByIds(ids);
    }

    /**
     * 删除报支类别公司设置信息
     *
     * @param id 报支类别公司设置主键
     * @return 结果
     */
    @Override
    public int deleteFinanceApItemCompById(String id)
    {
        return financeApItemCompMapper.deleteFinanceApItemCompById(id);
    }

    /**
     * 批量新增公司级报支类别
     * @param financeApItemComps
     * @return
     */
    @Override
    public int insertBatch(List<FinanceApItemComp> financeApItemComps){
        // 新增主档
        return financeApItemCompMapper.insertBatch(financeApItemComps);
    }

}
