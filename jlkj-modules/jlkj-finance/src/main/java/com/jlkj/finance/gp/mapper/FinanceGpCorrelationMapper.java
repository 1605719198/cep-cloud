package com.jlkj.finance.gp.mapper;

import java.util.List;
import com.jlkj.finance.gp.domain.FinanceGpCorrelation;
import org.springframework.stereotype.Repository;

/**
 * 是否关联方更新记录Mapper接口
 * 
 * @author 265799
 * @date 2023-04-06
 */
@Repository
public interface FinanceGpCorrelationMapper 
{
    /**
     * 查询是否关联方更新记录
     * 
     * @param uuid 是否关联方更新记录主键
     * @return 是否关联方更新记录
     */
    public FinanceGpCorrelation selectFinanceGpCorrelationByUuid(String uuid);

    /**
     * 查询是否关联方更新记录列表
     * 
     * @param financeGpCorrelation 是否关联方更新记录
     * @return 是否关联方更新记录集合
     */
    public List<FinanceGpCorrelation> selectFinanceGpCorrelationList(FinanceGpCorrelation financeGpCorrelation);

    /**
     * 新增是否关联方更新记录
     * 
     * @param financeGpCorrelation 是否关联方更新记录
     * @return 结果
     */
    public int insertFinanceGpCorrelation(FinanceGpCorrelation financeGpCorrelation);

    /**
     * 修改是否关联方更新记录
     * 
     * @param financeGpCorrelation 是否关联方更新记录
     * @return 结果
     */
    public int updateFinanceGpCorrelation(FinanceGpCorrelation financeGpCorrelation);

    /**
     * 删除是否关联方更新记录
     * 
     * @param uuid 是否关联方更新记录主键
     * @return 结果
     */
    public int deleteFinanceGpCorrelationByUuid(String uuid);

    /**
     * 批量删除是否关联方更新记录
     * 
     * @param uuids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFinanceGpCorrelationByUuids(String[] uuids);
}
