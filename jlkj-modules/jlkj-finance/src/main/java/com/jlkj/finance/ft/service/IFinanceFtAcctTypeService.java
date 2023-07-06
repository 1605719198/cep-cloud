package com.jlkj.finance.ft.service;

import com.jlkj.finance.ft.domain.FinanceFtAcctType;

import java.util.List;

/**
 * 账务代码设定Service接口
 * 
 * @author SunXuTong
 * @date 2023-06-27
 */
public interface IFinanceFtAcctTypeService 
{
    /**
     * 查询账务代码设定
     * 
     * @param uuid 账务代码设定主键
     * @return 账务代码设定
     */
    public FinanceFtAcctType selectFinanceFtAcctTypeByUuid(String uuid);

    /**
     * 查询账务代码设定列表
     * 
     * @param financeFtAcctType 账务代码设定
     * @return 账务代码设定集合
     */
    public List<FinanceFtAcctType> selectFinanceFtAcctTypeList(FinanceFtAcctType financeFtAcctType);

    /**
     * 新增账务代码设定
     * 
     * @param financeFtAcctType 账务代码设定
     * @return 结果
     */
    public int insertFinanceFtAcctType(FinanceFtAcctType financeFtAcctType);

    /**
     * 修改账务代码设定
     * 
     * @param financeFtAcctType 账务代码设定
     * @return 结果
     */
    public int updateFinanceFtAcctType(FinanceFtAcctType financeFtAcctType);

    /**
     * 批量删除账务代码设定
     * 
     * @param uuids 需要删除的账务代码设定主键集合
     * @return 结果
     */
    public int deleteFinanceFtAcctTypeByUuids(String[] uuids);

    /**
     * 删除账务代码设定信息
     * 
     * @param uuid 账务代码设定主键
     * @return 结果
     */
    public int deleteFinanceFtAcctTypeByUuid(String uuid);
}
