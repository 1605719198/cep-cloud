package com.jlkj.finance.ap.service;

import com.jlkj.finance.ap.domain.FinanceApConfirm;

import java.util.List;

/**
 * 会计确认主档Service接口
 * 
 * @author 265799
 * @date 2023-07-10
 */
public interface IFinanceApConfirmService 
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
     * @author 265799
     * @date 2023-07-10
     * @param financeApConfirm 会计确认主档
     * @return 会计确认主档集合
     */
    public List<FinanceApConfirm> selectFinanceApConfirmList(FinanceApConfirm financeApConfirm);

    /**
     * 新增会计确认主档
     *
     * @author 265799
     * @date 2023-07-10
     * @param financeApConfirm 会计确认主档
     * @return 结果
     */
    public int insertFinanceApConfirm(FinanceApConfirm financeApConfirm);

    /**
     * 修改会计确认主档
     *
     * @author 265799
     * @date 2023-07-10
     * @param financeApConfirm 会计确认主档
     * @return 结果
     */
    public int updateFinanceApConfirm(FinanceApConfirm financeApConfirm);

    /**
     * 批量删除会计确认主档
     *
     * @author 265799
     * @date 2023-07-10
     * @param ids 需要删除的会计确认主档主键集合
     * @return 结果
     */
    public int deleteFinanceApConfirmByIds(String[] ids);

    /**
     * 删除会计确认主档信息
     *
     * @author 265799
     * @date 2023-07-10
     * @param id 会计确认主档主键
     * @return 结果
     */
    public int deleteFinanceApConfirmById(String id);
}
