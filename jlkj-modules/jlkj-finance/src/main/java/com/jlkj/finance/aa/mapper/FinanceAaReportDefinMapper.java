package com.jlkj.finance.aa.mapper;

import com.jlkj.finance.aa.domain.FinanceAaReportDefin;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 集团管理-报定义Mapper接口
 * 
 * @author 116514
 * @date 2023-05-04
 */
@Repository
public interface FinanceAaReportDefinMapper 
{
    /**
     * 查询集团管理-报定义
     * 
     * @param reportId 集团管理-报定义主键
     * @return 集团管理-报定义
     */
    public FinanceAaReportDefin selectFinanceAaReportDefinByReportId(String reportId);

    /**
     * 查询集团管理-报定义列表
     * 
     * @param financeAaReportDefin 集团管理-报定义
     * @return 集团管理-报定义集合
     */
    public List<FinanceAaReportDefin> selectFinanceAaReportDefinList(FinanceAaReportDefin financeAaReportDefin);

    /**
     * 新增集团管理-报定义
     * 
     * @param financeAaReportDefin 集团管理-报定义
     * @return 结果
     */
    public int insertFinanceAaReportDefin(FinanceAaReportDefin financeAaReportDefin);

    /**
     * 修改集团管理-报定义
     * 
     * @param financeAaReportDefin 集团管理-报定义
     * @return 结果
     */
    public int updateFinanceAaReportDefin(FinanceAaReportDefin financeAaReportDefin);

    /**
     * 删除集团管理-报定义
     * 
     * @param reportId 集团管理-报定义主键
     * @return 结果
     */
    public int deleteFinanceAaReportDefinByReportId(String reportId);

    /**
     * 批量删除集团管理-报定义
     * 
     * @param reportIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFinanceAaReportDefinByReportIds(String[] reportIds);

    /**
     * 报表代号选单查询
     * @param compId
     * @return
     */
    public List<Map<String,String>> selectReportNoList(String compId);


    /**
     * 捡核数据是否重复
     * @param financeAaReportDefin
     * @return
     */
    public int updateCheckReportDefin(FinanceAaReportDefin financeAaReportDefin);

}
