package com.jlkj.finance.ao.service;

import java.util.List;
import com.jlkj.finance.ao.domain.FinanceAoItemCompDetail;

/**
 * 报支类别公司设定明细档Service接口
 * 
 * @author 116514
 * @date 2023-05-30
 */
public interface IFinanceAoItemCompDetailService 
{
    /**
     * 查询报支类别公司设定明细档
     * 
     * @param id 报支类别公司设定明细档主键
     * @return 报支类别公司设定明细档
     */
    public FinanceAoItemCompDetail selectFinanceAoItemCompDetailById(String id);

    /**
     * 查询报支类别公司设定明细档列表
     * 
     * @param financeAoItemCompDetail 报支类别公司设定明细档
     * @return 报支类别公司设定明细档集合
     */
    public List<FinanceAoItemCompDetail> selectFinanceAoItemCompDetailList(FinanceAoItemCompDetail financeAoItemCompDetail);

    /**
     * 新增报支类别公司设定明细档
     * 
     * @param financeAoItemCompDetail 报支类别公司设定明细档
     * @return 结果
     */
    public int insertFinanceAoItemCompDetail(FinanceAoItemCompDetail financeAoItemCompDetail);

    /**
     * 修改报支类别公司设定明细档
     * 
     * @param financeAoItemCompDetail 报支类别公司设定明细档
     * @return 结果
     */
    public int updateFinanceAoItemCompDetail(FinanceAoItemCompDetail financeAoItemCompDetail);

    /**
     * 批量删除报支类别公司设定明细档
     * 
     * @param ids 需要删除的报支类别公司设定明细档主键集合
     * @return 结果
     */
    public int deleteFinanceAoItemCompDetailByIds(String[] ids);

    /**
     * 删除报支类别公司设定明细档信息
     * 
     * @param id 报支类别公司设定明细档主键
     * @return 结果
     */
    public int deleteFinanceAoItemCompDetailById(String id);
}
