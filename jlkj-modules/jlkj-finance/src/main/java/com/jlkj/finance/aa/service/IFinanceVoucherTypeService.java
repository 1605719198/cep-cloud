package com.jlkj.finance.aa.service;

import java.util.List;
import java.util.Map;

import com.jlkj.finance.aa.domain.FinanceVoucherType;

/**
 * 凭证类别维护Service接口
 * 
 * @author 116514
 * @date 2023-04-13
 */
public interface IFinanceVoucherTypeService 
{
    /**
     * 查询凭证类别维护
     * 
     * @param id 凭证类别维护主键
     * @return 凭证类别维护
     */
    public FinanceVoucherType selectFinanceVoucherTypeById(String id);

    /**
     * 查询凭证类别维护列表
     * 
     * @param financeVoucherType 凭证类别维护
     * @return 凭证类别维护集合
     */
    public List<FinanceVoucherType> selectFinanceVoucherTypeList(FinanceVoucherType financeVoucherType);

    /**
     * 新增凭证类别维护
     * 
     * @param financeVoucherType 凭证类别维护
     * @return 结果
     */
    public int insertFinanceVoucherType(FinanceVoucherType financeVoucherType);

    /**
     * 修改凭证类别维护
     * 
     * @param financeVoucherType 凭证类别维护
     * @return 结果
     */
    public int updateFinanceVoucherType(FinanceVoucherType financeVoucherType);

    /**
     * 批量删除凭证类别维护
     * 
     * @param ids 需要删除的凭证类别维护主键集合
     * @return 结果
     */
    public int deleteFinanceVoucherTypeByIds(String[] ids);

    /**
     * 删除凭证类别维护信息
     * 
     * @param id 凭证类别维护主键
     * @return 结果
     */
    public int deleteFinanceVoucherTypeById(String id);

    /**
     * 查询凭证类别列表(下拉选单用)
     * @return
     */
    public List<Map<String,String>> selectVoucherTypeList();
}
