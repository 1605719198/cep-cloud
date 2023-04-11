package com.jlkj.finance.gp.mapper;

import java.util.List;
import com.jlkj.finance.gp.domain.FinanceGpAddfile;

/**
 * 厂商增户证照资料Mapper接口
 * 
 * @author 265799
 * @date 2023-04-10
 */
public interface FinanceGpAddfileMapper 
{
    /**
     * 查询厂商增户证照资料
     * 
     * @param uuid 厂商增户证照资料主键
     * @return 厂商增户证照资料
     */
    public FinanceGpAddfile selectFinanceGpAddfileByUuid(String uuid);

    /**
     * 查询厂商增户证照资料列表
     * 
     * @param financeGpAddfile 厂商增户证照资料
     * @return 厂商增户证照资料集合
     */
    public List<FinanceGpAddfile> selectFinanceGpAddfileList(FinanceGpAddfile financeGpAddfile);

    /**
     * 新增厂商增户证照资料
     * 
     * @param financeGpAddfile 厂商增户证照资料
     * @return 结果
     */
    public int insertFinanceGpAddfile(FinanceGpAddfile financeGpAddfile);

    /**
     * 修改厂商增户证照资料
     * 
     * @param financeGpAddfile 厂商增户证照资料
     * @return 结果
     */
    public int updateFinanceGpAddfile(FinanceGpAddfile financeGpAddfile);

    /**
     * 删除厂商增户证照资料
     * 
     * @param uuid 厂商增户证照资料主键
     * @return 结果
     */
    public int deleteFinanceGpAddfileByUuid(String uuid);

    /**
     * 批量删除厂商增户证照资料
     * 
     * @param uuids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFinanceGpAddfileByUuids(String[] uuids);
}
