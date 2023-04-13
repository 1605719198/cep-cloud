package com.jlkj.finance.gp.service;

import java.util.List;
import com.jlkj.finance.gp.domain.FinanceGpAddfile;

/**
 * 厂商增户证照资料Service接口
 * 
 * @author 265799
 * @date 2023-04-10
 */
public interface IFinanceGpAddfileService 
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
     * 查询厂商异动证照资料列表
     *
     * @param financeGpAddfile 查询厂商异动证照资料列表
     * @return 查询厂商异动证照资料列表
     */
    public List<FinanceGpAddfile> selectFinanceGpChangeFileList(FinanceGpAddfile financeGpAddfile);

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
     * 批量删除厂商增户证照资料
     * 
     * @param uuids 需要删除的厂商增户证照资料主键集合
     * @return 结果
     */
    public int deleteFinanceGpAddfileByUuids(String[] uuids);

    /**
     * 删除厂商增户证照资料信息
     * 
     * @param uuid 厂商增户证照资料主键
     * @return 结果
     */
    public int deleteFinanceGpAddfileByUuid(String uuid);
}
