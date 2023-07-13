package com.jlkj.finance.ao.service.impl;

import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.common.core.utils.uuid.IdUtils;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.finance.ao.domain.FinanceAoExpenseBills;
import com.jlkj.finance.ao.mapper.FinanceAoExpenseBillsMapper;
import com.jlkj.finance.ao.service.IFinanceAoExpenseBillsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 我的发票Service业务层处理
 *
 * @author 116514
 * @date 2023-05-18
 * @version 1.0
 */
@Service
public class FinanceAoExpenseBillsServiceImpl implements IFinanceAoExpenseBillsService
{
    @Autowired
    private FinanceAoExpenseBillsMapper financeAoExpenseBillsMapper;

    /**
     * 查询我的发票
     *
     * @author 116514
     * @date 2023-05-18
     * @param id 我的发票主键
     * @return 我的发票
     */
    @Override
    public FinanceAoExpenseBills selectFinanceAoExpenseBillsById(String id)
    {
        return financeAoExpenseBillsMapper.selectFinanceAoExpenseBillsById(id);
    }

    /**
     * 查询我的发票列表
     *
     * @author 116514
     * @date 2023-05-18
     * @param financeAoExpenseBills 我的发票
     * @return 我的发票
     */
    @Override
    public List<FinanceAoExpenseBills> selectFinanceAoExpenseBillsList(FinanceAoExpenseBills financeAoExpenseBills)
    {
        return financeAoExpenseBillsMapper.selectFinanceAoExpenseBillsList(financeAoExpenseBills);
    }

    /**
     * 新增我的发票
     *
     * @author 116514
     * @date 2023-05-18
     * @param financeAoExpenseBills 我的发票
     * @return 结果
     */
    @Override
    public int insertFinanceAoExpenseBills(FinanceAoExpenseBills financeAoExpenseBills)
    {
        financeAoExpenseBills.setId(IdUtils.simpleUUID());
        // 0-未报销 1-已报销
        financeAoExpenseBills.setStatus("0");
        financeAoExpenseBills.setCreateBy(SecurityUtils.getUsername());
        financeAoExpenseBills.setCreateName(SecurityUtils.getNickName());
        financeAoExpenseBills.setCreateTime(DateUtils.getNowDate());
        return financeAoExpenseBillsMapper.insertFinanceAoExpenseBills(financeAoExpenseBills);
    }

    /**
     * 修改我的发票
     *
     * @author 116514
     * @date 2023-05-18
     * @param financeAoExpenseBills 我的发票
     * @return 结果
     */
    @Override
    public int updateFinanceAoExpenseBills(FinanceAoExpenseBills financeAoExpenseBills)
    {
        return financeAoExpenseBillsMapper.updateFinanceAoExpenseBills(financeAoExpenseBills);
    }

    /**
     * 批量删除我的发票
     *
     * @author 116514
     * @date 2023-05-18
     * @param ids 需要删除的我的发票主键
     * @return 结果
     */
    @Override
    public int deleteFinanceAoExpenseBillsByIds(String[] ids)
    {
        return financeAoExpenseBillsMapper.deleteFinanceAoExpenseBillsByIds(ids);
    }

    /**
     * 删除我的发票信息
     *
     * @author 116514
     * @date 2023-05-18
     * @param id 我的发票主键
     * @return 结果
     */
    @Override
    public int deleteFinanceAoExpenseBillsById(String id)
    {
        return financeAoExpenseBillsMapper.deleteFinanceAoExpenseBillsById(id);
    }
}
