package com.jlkj.finance.gp.service.impl;

import java.util.List;
import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.common.core.utils.uuid.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jlkj.finance.gp.mapper.FinanceGpCorrelationMapper;
import com.jlkj.finance.gp.domain.FinanceGpCorrelation;
import com.jlkj.finance.gp.service.IFinanceGpCorrelationService;

/**
 * 是否关联方更新记录Service业务层处理
 *
 * @author 265799
 * @date 2023-04-06
 */
@Service
public class FinanceGpCorrelationServiceImpl implements IFinanceGpCorrelationService
{
    @Autowired
    private FinanceGpCorrelationMapper financeGpCorrelationMapper;

    /**
     * 查询是否关联方更新记录
     *
     * @param uuid 是否关联方更新记录主键
     * @return 是否关联方更新记录
     */
    @Override
    public FinanceGpCorrelation selectFinanceGpCorrelationByUuid(String uuid)
    {
        return financeGpCorrelationMapper.selectFinanceGpCorrelationByUuid(uuid);
    }

    /**
     * 查询是否关联方更新记录列表
     *
     * @param financeGpCorrelation 是否关联方更新记录
     * @return 是否关联方更新记录
     */
    @Override
    public List<FinanceGpCorrelation> selectFinanceGpCorrelationList(FinanceGpCorrelation financeGpCorrelation)
    {
        return financeGpCorrelationMapper.selectFinanceGpCorrelationList(financeGpCorrelation);
    }

    /**
     * 新增是否关联方更新记录
     *
     * @param financeGpCorrelation 是否关联方更新记录
     * @return 结果
     */
    @Override
    public int insertFinanceGpCorrelation(FinanceGpCorrelation financeGpCorrelation)
    {
        financeGpCorrelation.setUuid(UUID.fastUUID().toString());
        financeGpCorrelation.setCreateTime(DateUtils.getNowDate());
        return financeGpCorrelationMapper.insertFinanceGpCorrelation(financeGpCorrelation);
    }

    /**
     * 修改是否关联方更新记录
     *
     * @param financeGpCorrelation 是否关联方更新记录
     * @return 结果
     */
    @Override
    public int updateFinanceGpCorrelation(FinanceGpCorrelation financeGpCorrelation)
    {
        financeGpCorrelation.setUpdateTime(DateUtils.getNowDate());
        return financeGpCorrelationMapper.updateFinanceGpCorrelation(financeGpCorrelation);
    }

    /**
     * 批量删除是否关联方更新记录
     *
     * @param uuids 需要删除的是否关联方更新记录主键
     * @return 结果
     */
    @Override
    public int deleteFinanceGpCorrelationByUuids(String[] uuids)
    {
        return financeGpCorrelationMapper.deleteFinanceGpCorrelationByUuids(uuids);
    }

    /**
     * 删除是否关联方更新记录信息
     *
     * @param uuid 是否关联方更新记录主键
     * @return 结果
     */
    @Override
    public int deleteFinanceGpCorrelationByUuid(String uuid)
    {
        return financeGpCorrelationMapper.deleteFinanceGpCorrelationByUuid(uuid);
    }
}
