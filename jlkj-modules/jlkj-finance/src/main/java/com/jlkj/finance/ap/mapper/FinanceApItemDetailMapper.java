package com.jlkj.finance.ap.mapper;

import com.jlkj.finance.ap.domain.FinanceApItemDetail;

import java.util.List;

/**
 * 报支类别集团细项Mapper接口
 * 
 * @author SunXuTong
 * @date 2023-05-31
 */
public interface FinanceApItemDetailMapper 
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
     * 删除报支类别集团细项
     * 
     * @param id 报支类别集团细项主键
     * @return 结果
     */
    public int deleteFinanceApItemDetailById(String id);

    /**
     * 批量删除报支类别集团细项
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFinanceApItemDetailByIds(String[] ids);
}
