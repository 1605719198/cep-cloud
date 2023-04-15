package com.jlkj.finance.aa.service.impl;

import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.common.core.utils.uuid.IdUtils;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.finance.aa.domain.FinanceAaCrcy;
import com.jlkj.finance.aa.mapper.FinanceAaCrcyMapper;
import com.jlkj.finance.aa.service.IFinanceAaCrcyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 币别管理Service业务层处理
 *
 * @author SunXuTong
 * @date 2023-04-07
 */
@Service
public class FinanceAaCrcyServiceImpl implements IFinanceAaCrcyService
{
    @Autowired
    private FinanceAaCrcyMapper financeAaCrcyMapper;

    /**
     * 查询币别管理
     *
     * @param id 币别管理主键
     * @return 币别管理
     */
    @Override
    public FinanceAaCrcy selectFinanceAaCrcyById(String id)
    {
        return financeAaCrcyMapper.selectFinanceAaCrcyById(id);
    }

    /**
     * 查询币别管理列表
     *
     * @param financeAaCrcy 币别管理
     * @return 币别管理
     */
    @Override
    public List<FinanceAaCrcy> selectFinanceAaCrcyList(FinanceAaCrcy financeAaCrcy)
    {
        return financeAaCrcyMapper.selectFinanceAaCrcyList(financeAaCrcy);
    }

    /**
     * 新增币别管理
     *
     * @param financeAaCrcy 币别管理
     * @return 结果
     */
    @Override
    public int insertFinanceAaCrcy(FinanceAaCrcy financeAaCrcy)
    {

        financeAaCrcy.setId(IdUtils.fastSimpleUUID());
        financeAaCrcy.setCreateTime(DateUtils.getNowDate());
        financeAaCrcy.setCreateBy(SecurityUtils.getUsername());
        financeAaCrcy.setCreateName(SecurityUtils.getNickName());
        return financeAaCrcyMapper.insertFinanceAaCrcy(financeAaCrcy);
    }

    /**
     * 修改币别管理
     *
     * @param financeAaCrcy 币别管理
     * @return 结果
     */
    @Override
    public int updateFinanceAaCrcy(FinanceAaCrcy financeAaCrcy)
    {
        financeAaCrcy.setUpdateTime(DateUtils.getNowDate());
        financeAaCrcy.setUpdateBy(SecurityUtils.getUsername());
        financeAaCrcy.setUpdateName(SecurityUtils.getNickName());
        return financeAaCrcyMapper.updateFinanceAaCrcy(financeAaCrcy);
    }

    /**
     * 批量删除币别管理
     *
     * @param ids 需要删除的币别管理主键
     * @return 结果
     */
    @Override
    public int deleteFinanceAaCrcyByIds(String[] ids)
    {
        return financeAaCrcyMapper.deleteFinanceAaCrcyByIds(ids);
    }

    /**
     * 删除币别管理信息
     *
     * @param id 币别管理主键
     * @return 结果
     */
    @Override
    public int deleteFinanceAaCrcyById(String id)
    {
        return financeAaCrcyMapper.deleteFinanceAaCrcyById(id);
    }
}
