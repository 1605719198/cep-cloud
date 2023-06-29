package com.jlkj.finance.aa.service.impl;

import java.util.List;
import com.jlkj.common.core.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jlkj.finance.aa.mapper.FinanceAaProjectResultMapper;
import com.jlkj.finance.aa.domain.FinanceAaProjectResult;
import com.jlkj.finance.aa.service.IFinanceAaProjectResultService;

/**
 * 印项目计算结果Service业务层处理
 *
 * @author jlkj
 * @date 2023-06-28
 */
@Service
public class FinanceAaProjectResultServiceImpl implements IFinanceAaProjectResultService
{
    @Autowired
    private FinanceAaProjectResultMapper financeAaProjectResultMapper;

    /**
     * 查询印项目计算结果
     *
     * @param id 印项目计算结果主键
     * @return 印项目计算结果
     */
    @Override
    public FinanceAaProjectResult selectFinanceAaProjectResultById(Long id)
    {
        return financeAaProjectResultMapper.selectFinanceAaProjectResultById(id);
    }

    /**
     * 查询印项目计算结果列表
     *
     * @param financeAaProjectResult 印项目计算结果
     * @return 印项目计算结果
     */
    @Override
    public List<FinanceAaProjectResult> selectFinanceAaProjectResultList(FinanceAaProjectResult financeAaProjectResult)
    {
        return financeAaProjectResultMapper.selectFinanceAaProjectResultList(financeAaProjectResult);
    }

    /**
     * 新增印项目计算结果
     *
     * @param financeAaProjectResult 印项目计算结果
     * @return 结果
     */
    @Override
    public int insertFinanceAaProjectResult(FinanceAaProjectResult financeAaProjectResult)
    {
        financeAaProjectResult.setCreateTime(DateUtils.getNowDate());
        return financeAaProjectResultMapper.insertFinanceAaProjectResult(financeAaProjectResult);
    }

    /**
     * 修改印项目计算结果
     *
     * @param financeAaProjectResult 印项目计算结果
     * @return 结果
     */
    @Override
    public int updateFinanceAaProjectResult(FinanceAaProjectResult financeAaProjectResult)
    {
        return financeAaProjectResultMapper.updateFinanceAaProjectResult(financeAaProjectResult);
    }

    /**
     * 批量删除印项目计算结果
     *
     * @param ids 需要删除的印项目计算结果主键
     * @return 结果
     */
    @Override
    public int deleteFinanceAaProjectResultByIds(Long[] ids)
    {
        return financeAaProjectResultMapper.deleteFinanceAaProjectResultByIds(ids);
    }

    /**
     * 删除印项目计算结果信息
     *
     * @param id 印项目计算结果主键
     * @return 结果
     */
    @Override
    public int deleteFinanceAaProjectResultById(Long id)
    {
        return financeAaProjectResultMapper.deleteFinanceAaProjectResultById(id);
    }
}
