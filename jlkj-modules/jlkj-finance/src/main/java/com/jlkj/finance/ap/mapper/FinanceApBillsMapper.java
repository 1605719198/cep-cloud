package com.jlkj.finance.ap.mapper;

import com.jlkj.finance.ap.domain.FinanceApBills;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 发票信息Mapper接口
 * 
 * @author 265799
 * @date 2023-07-13
 */
@Repository
public interface FinanceApBillsMapper 
{
    /**
     * 查询发票信息
     * 
     * @param id 发票信息主键
     * @return 发票信息
     */
    public FinanceApBills selectFinanceApBillsById(String id);

    /**
     * 查询发票信息列表
     * 
     * @param financeApBills 发票信息
     * @return 发票信息集合
     */
    public List<FinanceApBills> selectFinanceApBillsList(FinanceApBills financeApBills);

    /**
     * 新增发票信息
     * 
     * @param financeApBills 发票信息
     * @return 结果
     */
    public int insertFinanceApBills(FinanceApBills financeApBills);

    /**
     * 修改发票信息
     * 
     * @param financeApBills 发票信息
     * @return 结果
     */
    public int updateFinanceApBills(FinanceApBills financeApBills);

    /**
     * 删除发票信息
     * 
     * @param id 发票信息主键
     * @return 结果
     */
    public int deleteFinanceApBillsById(String id);

    /**
     * 批量删除发票信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFinanceApBillsByIds(String[] ids);
}
