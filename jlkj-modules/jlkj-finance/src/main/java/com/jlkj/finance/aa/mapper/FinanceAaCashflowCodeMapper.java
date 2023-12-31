package com.jlkj.finance.aa.mapper;

import com.jlkj.finance.aa.domain.FinanceAaCashflowCode;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 现金流量代码Mapper接口
 * 
 * @author 265799
 * @date 2023-04-17
 */
@Repository
public interface FinanceAaCashflowCodeMapper 
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
     * 查询现金流量代码列表
     *
     * @param financeAaCashflowCode 现金流量代码
     * @return 现金流量代码集合
     */
    public List<FinanceAaCashflowCode> selectFinanceAaCashflowCodeListPop(FinanceAaCashflowCode financeAaCashflowCode);
    /**
     * 查询现金流量代码列表
     *
     * @param financeAaCashflowCode 现金流量代码
     * @return 现金流量代码集合
     */
    public FinanceAaCashflowCode selectListPop(FinanceAaCashflowCode financeAaCashflowCode);

    /**
     * 新增现金流量代码
     * 
     * @param financeAaCashflowCode 现金流量代码
     * @return 结果
     */
    public int insertFinanceAaCashflowCode(FinanceAaCashflowCode financeAaCashflowCode);

    /**
     * 修改现金流量代码
     * 
     * @param financeAaCashflowCode 现金流量代码
     * @return 结果
     */
    public int updateFinanceAaCashflowCode(FinanceAaCashflowCode financeAaCashflowCode);

    /**
     * 删除现金流量代码
     * 
     * @param id 现金流量代码主键
     * @return 结果
     */
    public int deleteFinanceAaCashflowCodeById(String id);

    /**
     * 批量删除现金流量代码
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFinanceAaCashflowCodeByIds(String[] ids);
}
