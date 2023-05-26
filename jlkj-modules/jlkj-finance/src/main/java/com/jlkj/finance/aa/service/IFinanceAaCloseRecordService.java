package com.jlkj.finance.aa.service;

import com.jlkj.finance.aa.domain.FinanceAaCloseRecord;

import java.util.List;

/**
 * 关账执行记录Service接口
 * 
 * @author 265799
 * @date 2023-05-23
 */
public interface IFinanceAaCloseRecordService 
{
    /**
     * 查询关账执行记录
     * 
     * @param id 关账执行记录主键
     * @return 关账执行记录
     */
    public FinanceAaCloseRecord selectFinanceAaCloseRecordById(String id);

    /**
     * 查询关账执行记录列表
     * 
     * @param financeAaCloseRecord 关账执行记录
     * @return 关账执行记录集合
     */
    public List<FinanceAaCloseRecord> selectFinanceAaCloseRecordList(FinanceAaCloseRecord financeAaCloseRecord);

    /**
     * 执行关账执行记录
     * 
     * @param financeAaCloseRecord 关账执行记录
     * @return 结果
     */
    public List<FinanceAaCloseRecord> insertFinanceAaCloseRecord(FinanceAaCloseRecord financeAaCloseRecord);


    /**
     * 修改关账执行记录
     * 
     * @param financeAaCloseRecord 关账执行记录
     * @return 结果
     */
    public int updateFinanceAaCloseRecord(FinanceAaCloseRecord financeAaCloseRecord);

    /**
     * 批量删除关账执行记录
     * 
     * @param ids 需要删除的关账执行记录主键集合
     * @return 结果
     */
    public int deleteFinanceAaCloseRecordByIds(String[] ids);

    /**
     * 删除关账执行记录信息
     * 
     * @param id 关账执行记录主键
     * @return 结果
     */
    public int deleteFinanceAaCloseRecordById(String id);
}
