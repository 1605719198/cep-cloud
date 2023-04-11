package com.jlkj.finance.gp.mapper;

import java.util.List;
import com.jlkj.finance.gp.domain.FinanceGpAdd;

/**
 * 厂商增户申请Mapper接口
 * 
 * @author 265799
 * @date 2023-04-10
 */
public interface FinanceGpAddMapper 
{
    /**
     * 查询厂商增户申请
     * 
     * @param applyId 厂商增户申请主键
     * @return 厂商增户申请
     */
    public FinanceGpAdd selectFinanceGpAddByApplyId(String applyId);

    /**
     * 查询厂商增户申请列表
     * 
     * @param financeGpAdd 厂商增户申请
     * @return 厂商增户申请集合
     */
    public List<FinanceGpAdd> selectFinanceGpAddList(FinanceGpAdd financeGpAdd);
    /**
     * 查询厂商增户申请列表
     *
     * @param financeGpAdd 厂商增户申请
     * @return 厂商增户申请集合
     */
    public List<FinanceGpAdd> selectFinanceGpAddDateList(FinanceGpAdd financeGpAdd);

    /**
     * 新增厂商增户申请
     * 
     * @param financeGpAdd 厂商增户申请
     * @return 结果
     */
    public int insertFinanceGpAdd(FinanceGpAdd financeGpAdd);

    /**
     * 修改厂商增户申请
     * 
     * @param financeGpAdd 厂商增户申请
     * @return 结果
     */
    public int updateFinanceGpAdd(FinanceGpAdd financeGpAdd);

    /**
     * 删除厂商增户申请
     * 
     * @param applyId 厂商增户申请主键
     * @return 结果
     */
    public int deleteFinanceGpAddByApplyId(String applyId);

    /**
     * 批量删除厂商增户申请
     * 
     * @param applyIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFinanceGpAddByApplyIds(String[] applyIds);
}
