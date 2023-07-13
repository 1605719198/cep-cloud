package com.jlkj.finance.ap.mapper;

import com.jlkj.finance.ap.domain.FinanceApConfirm;
import com.jlkj.finance.ap.domain.FinanceApConfirmDetail;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 会计确认主档Mapper接口
 * 
 * @author 265799
 * @date 2023-07-10
 */
@Repository
public interface FinanceApConfirmMapper 
{
    /**
     * 查询会计确认主档
     * 
     * @param id 会计确认主档主键
     * @return 会计确认主档
     */
    public FinanceApConfirm selectFinanceApConfirmById(String id);

    /**
     * 查询会计确认主档列表
     * 
     * @param financeApConfirm 会计确认主档
     * @return 会计确认主档集合
     */
    public List<FinanceApConfirm> selectFinanceApConfirmList(FinanceApConfirm financeApConfirm);

    /**
     * 新增会计确认主档
     * 
     * @param financeApConfirm 会计确认主档
     * @return 结果
     */
    public int insertFinanceApConfirm(FinanceApConfirm financeApConfirm);

    /**
     * 修改会计确认主档
     * 
     * @param financeApConfirm 会计确认主档
     * @return 结果
     */
    public int updateFinanceApConfirm(FinanceApConfirm financeApConfirm);

    /**
     * 删除会计确认主档
     * 
     * @param id 会计确认主档主键
     * @return 结果
     */
    public int deleteFinanceApConfirmById(String id);

    /**
     * 批量删除会计确认主档
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFinanceApConfirmByIds(String[] ids);

    /**
     * 批量删除会计确认明细
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFinanceApConfirmDetailByBillIds(String[] ids);
    
    /**
     * 批量新增会计确认明细
     * 
     * @param financeApConfirmDetailList 会计确认明细列表
     * @return 结果
     */
    public int batchFinanceApConfirmDetail(List<FinanceApConfirmDetail> financeApConfirmDetailList);
    

    /**
     * 通过会计确认主档主键删除会计确认明细信息
     * 
     * @param id 会计确认主档ID
     * @return 结果
     */
    public int deleteFinanceApConfirmDetailByBillId(String id);
}
