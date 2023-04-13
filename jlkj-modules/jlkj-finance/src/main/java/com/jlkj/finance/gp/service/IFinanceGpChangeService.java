package com.jlkj.finance.gp.service;

import java.util.List;
import com.jlkj.finance.gp.domain.FinanceGpChange;

/**
 * 厂商异动申请Service接口
 * 
 * @author 265799
 * @date 2023-04-12
 */
public interface IFinanceGpChangeService 
{
    /**
     * 查询厂商异动申请
     * 
     * @param applyId 厂商异动申请主键
     * @return 厂商异动申请
     */
    public FinanceGpChange selectFinanceGpChangeByApplyId(String applyId);

    /**
     * 查询厂商异动申请列表
     * 
     * @param financeGpChange 厂商异动申请
     * @return 厂商异动申请集合
     */
    public List<FinanceGpChange> selectFinanceGpChangeList(FinanceGpChange financeGpChange);

    /**
     * 新增厂商异动申请
     * 
     * @param financeGpChange 厂商异动申请
     * @return 结果
     */
    public int insertFinanceGpChange(FinanceGpChange financeGpChange);

    /**
     * 修改厂商异动申请
     * 
     * @param financeGpChange 厂商异动申请
     * @return 结果
     */
    public int updateFinanceGpChange(FinanceGpChange financeGpChange);

    /**
     * 批量删除厂商异动申请
     * 
     * @param applyIds 需要删除的厂商异动申请主键集合
     * @return 结果
     */
    public int deleteFinanceGpChangeByApplyIds(String[] applyIds);

    /**
     * 删除厂商异动申请信息
     * 
     * @param applyId 厂商异动申请主键
     * @return 结果
     */
    public int deleteFinanceGpChangeByApplyId(String applyId);
}
