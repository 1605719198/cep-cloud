package com.jlkj.finance.ao.service;

import com.jlkj.finance.ao.domain.FinanceAoItemDetail;

import java.util.List;

/**
 * 报支管理-报支类别集团设定明细档Service接口
 * 
 * @author 116514
 * @date 2023-05-21
 */
public interface IFinanceAoItemDetailService 
{
    /**
     * 查询报支管理-报支类别集团设定明细档
     * 
     * @param id 报支管理-报支类别集团设定明细档主键
     * @return 报支管理-报支类别集团设定明细档
     */
    public FinanceAoItemDetail selectFinanceAoItemDetailById(String id);

    /**
     * 查询报支管理-报支类别集团设定明细档列表
     * 
     * @param financeAoItemDetail 报支管理-报支类别集团设定明细档
     * @return 报支管理-报支类别集团设定明细档集合
     */
    public List<FinanceAoItemDetail> selectFinanceAoItemDetailList(FinanceAoItemDetail financeAoItemDetail);

    /**
     * 新增报支管理-报支类别集团设定明细档
     * 
     * @param financeAoItemDetail 报支管理-报支类别集团设定明细档
     * @return 结果
     */
    public int insertFinanceAoItemDetail(FinanceAoItemDetail financeAoItemDetail);

    /**
     * 修改报支管理-报支类别集团设定明细档
     * 
     * @param financeAoItemDetail 报支管理-报支类别集团设定明细档
     * @return 结果
     */
    public int updateFinanceAoItemDetail(FinanceAoItemDetail financeAoItemDetail);

    /**
     * 批量删除报支管理-报支类别集团设定明细档
     * 
     * @param ids 需要删除的报支管理-报支类别集团设定明细档主键集合
     * @return 结果
     */
    public int deleteFinanceAoItemDetailByIds(String[] ids);

    /**
     * 删除报支管理-报支类别集团设定明细档信息
     * 
     * @param id 报支管理-报支类别集团设定明细档主键
     * @return 结果
     */
    public int deleteFinanceAoItemDetailById(String id);

}
