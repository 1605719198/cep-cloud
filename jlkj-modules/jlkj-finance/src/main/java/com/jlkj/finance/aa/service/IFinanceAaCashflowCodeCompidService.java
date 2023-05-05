package com.jlkj.finance.aa.service;

import java.util.List;
import com.jlkj.finance.aa.domain.FinanceAaCashflowCodeCompid;
import com.jlkj.finance.aa.dto.FinanceAaCashflowCodeDTO;

/**
 * 现金流量代码公司级Service接口
 * 
 * @author 265799
 * @date 2023-05-04
 */
public interface IFinanceAaCashflowCodeCompidService 
{
    /**
     * 查询现金流量代码公司级
     * 
     * @param id 现金流量代码公司级主键
     * @return 现金流量代码公司级
     */
    public FinanceAaCashflowCodeCompid selectFinanceAaCashflowCodeCompidById(String id);

    /**
     * 查询现金流量代码公司级列表
     * 
     * @param financeAaCashflowCodeCompid 现金流量代码公司级
     * @return 现金流量代码公司级集合
     */
    public List<FinanceAaCashflowCodeCompid> selectFinanceAaCashflowCodeCompidList(FinanceAaCashflowCodeCompid financeAaCashflowCodeCompid);

    /**
     * 新增现金流量代码公司级
     *
     * @param projectIds 批量现金流量代码
     * @return 结果
     */
    public int insertFinanceAaCashflowCodeCompid( List<FinanceAaCashflowCodeDTO> projectIds);

    /**
     * 修改现金流量代码公司级
     * 
     * @param financeAaCashflowCodeCompid 现金流量代码公司级
     * @return 结果
     */
    public int updateFinanceAaCashflowCodeCompid(FinanceAaCashflowCodeCompid financeAaCashflowCodeCompid);

    /**
     * 批量删除现金流量代码公司级
     * 
     * @param ids 需要删除的现金流量代码公司级主键集合
     * @return 结果
     */
    public int deleteFinanceAaCashflowCodeCompidByIds(String[] ids);

    /**
     * 删除现金流量代码公司级信息
     * 
     * @param id 现金流量代码公司级主键
     * @return 结果
     */
    public int deleteFinanceAaCashflowCodeCompidById(String id);
}
