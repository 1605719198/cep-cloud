package com.jlkj.finance.ao.mapper;

import com.jlkj.finance.ao.domain.FinanceAoItemCompDetail;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 报支类别公司设定明细档Mapper接口
 * 
 * @author 116514
 * @date 2023-05-30
 */
@Repository
public interface FinanceAoItemCompDetailMapper 
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
     * 删除报支类别公司设定明细档
     * 
     * @param id 报支类别公司设定明细档主键
     * @return 结果
     */
    public int deleteFinanceAoItemCompDetailById(String id);

    /**
     * 批量删除报支类别公司设定明细档
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFinanceAoItemCompDetailByIds(String[] ids);


    /**
     * 批量新增明细档
     * @param financeAoItemCompDetails
     * @return
     */
    public int insertBatch(List<FinanceAoItemCompDetail> financeAoItemCompDetails);
}
