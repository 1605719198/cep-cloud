package com.jlkj.finance.aa.service;

import java.util.List;
import com.jlkj.finance.aa.domain.FinanceAaReportInfo;

/**
 * 印项目计算记录Service接口
 * 
 * @author jlkj
 * @date 2023-06-28
 */
public interface IFinanceAaReportInfoService 
{
    /**
     * 查询印项目计算记录
     * 
     * @param id 印项目计算记录主键
     * @return 印项目计算记录
     */
    public FinanceAaReportInfo selectFinanceAaReportInfoById(String id);

    /**
     * 查询印项目计算记录列表
     * 
     * @param financeAaReportInfo 印项目计算记录
     * @return 印项目计算记录集合
     */
    public List<FinanceAaReportInfo> selectFinanceAaReportInfoList(FinanceAaReportInfo financeAaReportInfo);

    /**
     * 新增印项目计算记录
     * 
     * @param financeAaReportInfo 印项目计算记录
     * @return 结果
     */
    public int insertFinanceAaReportInfo(FinanceAaReportInfo financeAaReportInfo);


    /**
     * 修改印项目计算记录
     * 
     * @param financeAaReportInfo 印项目计算记录
     * @return 结果
     */
    public int updateFinanceAaReportInfo(FinanceAaReportInfo financeAaReportInfo);

    /**
     * 批量删除印项目计算记录
     * 
     * @param ids 需要删除的印项目计算记录主键集合
     * @return 结果
     */
    public int deleteFinanceAaReportInfoByIds(String[] ids);

    /**
     * 删除印项目计算记录信息
     * 
     * @param id 印项目计算记录主键
     * @return 结果
     */
    public int deleteFinanceAaReportInfoById(String id);
}
