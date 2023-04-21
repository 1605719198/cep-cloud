package com.jlkj.finance.aa.service;

import java.util.List;
import com.jlkj.finance.aa.domain.FinanceAaCashflowCode;
import com.jlkj.finance.aa.dto.FinanceAaCashflowCodeDTO;
import com.jlkj.finance.ip.dto.FinanceCostDownTypeTreeDTO;

/**
 * 现金流量代码Service接口
 * 
 * @author 265799
 * @date 2023-04-17
 */
public interface IFinanceAaCashflowCodeService 
{

    /**
     * 查询现金流量代码
     * 
     * @param id 现金流量代码主键
     * @return 现金流量代码
     */
    public FinanceAaCashflowCode selectFinanceAaCashflowCodeById(String id);

    /**
     * 查询现金流量代码列表
     * 
     * @param financeAaCashflowCode 现金流量代码
     * @return 现金流量代码集合
     */
    public List<FinanceAaCashflowCode> selectFinanceAaCashflowCodeList(FinanceAaCashflowCode financeAaCashflowCode);

    /**
     * 新增现金流量代码
     * 
     * @param projectIds 批量现金流量代码
     * @return 结果
     */
    public int insertFinanceAaCashflowCode( List<FinanceAaCashflowCodeDTO> projectIds);

    /**
     * 修改现金流量代码
     * 
     * @param financeAaCashflowCode 现金流量代码
     * @return 结果
     */
    public int updateFinanceAaCashflowCode(FinanceAaCashflowCode financeAaCashflowCode);

    /**
     * 批量删除现金流量代码
     * 
     * @param ids 需要删除的现金流量代码主键集合
     * @return 结果
     */
    public int deleteFinanceAaCashflowCodeByIds(String[] ids);

    /**
     * 删除现金流量代码信息
     * 
     * @param id 现金流量代码主键
     * @return 结果
     */
    public int deleteFinanceAaCashflowCodeById(String id);
}
