package com.jlkj.finance.ap.service;

import com.jlkj.finance.ap.domain.FinanceApItemDetail;
import com.jlkj.finance.ap.dto.FinanceApItemDetailDTO;

import java.util.List;

/**
 * 报支类别集团细项Service接口
 * 
 * @author SunXuTong
 * @date 2023-05-31
 */
public interface IFinanceApItemDetailService 
{
    /**
     * 查询报支类别集团细项
     * 
     * @param id 报支类别集团细项主键
     * @return 报支类别集团细项
     */
    public FinanceApItemDetail selectFinanceApItemDetailById(String id);

    /**
     * 查询报支类别集团细项列表
     * 
     * @param financeApItemDetail 报支类别集团细项
     * @return 报支类别集团细项集合
     */
    public List<FinanceApItemDetail> selectFinanceApItemDetailList(FinanceApItemDetail financeApItemDetail);


    /**
     * 查询报支类别集团细项列表
     *
     * @param financeApItemDetail 报支类别集团细项
     * @return 报支类别集团细项集合
     */
    public List<FinanceApItemDetailDTO> selectFinanceApItemDetailList1(FinanceApItemDetail financeApItemDetail);

    /**
     * 新增报支类别集团细项
     * 
     * @param financeApItemDetail 报支类别集团细项
     * @return 结果
     */
    public int insertFinanceApItemDetail(FinanceApItemDetail financeApItemDetail);

    /**
     * 修改报支类别集团细项
     * 
     * @param financeApItemDetail 报支类别集团细项
     * @return 结果
     */
    public int updateFinanceApItemDetail(FinanceApItemDetail financeApItemDetail);

    /**
     * 批量删除报支类别集团细项
     * 
     * @param ids 需要删除的报支类别集团细项主键集合
     * @return 结果
     */
    public int deleteFinanceApItemDetailByIds(String[] ids);

    /**
     * 删除报支类别集团细项信息
     * 
     * @param id 报支类别集团细项主键
     * @return 结果
     */
    public int deleteFinanceApItemDetailById(String id);
}
