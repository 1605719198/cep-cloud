package com.jlkj.finance.aa.service.impl;

import java.util.List;
import com.jlkj.common.core.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jlkj.finance.aa.mapper.FinanceAaReportInfoMapper;
import com.jlkj.finance.aa.domain.FinanceAaReportInfo;
import com.jlkj.finance.aa.service.IFinanceAaReportInfoService;

/**
 * 印项目计算记录Service业务层处理
 *
 * @author jlkj
 * @date 2023-06-28
 */
@Service
public class FinanceAaReportInfoServiceImpl implements IFinanceAaReportInfoService
{
    @Autowired
    private FinanceAaReportInfoMapper financeAaReportInfoMapper;

    /**
     * 查询印项目计算记录
     *
     * @param id 印项目计算记录主键
     * @return 印项目计算记录
     */
    @Override
    public FinanceAaReportInfo selectFinanceAaReportInfoById(String id)
    {
        return financeAaReportInfoMapper.selectFinanceAaReportInfoById(id);
    }

    /**
     * 查询印项目计算记录列表
     *
     * @param financeAaReportInfo 印项目计算记录
     * @return 印项目计算记录
     */
    @Override
    public List<FinanceAaReportInfo> selectFinanceAaReportInfoList(FinanceAaReportInfo financeAaReportInfo)
    {
        return financeAaReportInfoMapper.selectFinanceAaReportInfoList(financeAaReportInfo);
    }

    /**
     * 新增印项目计算记录
     *
     * @param financeAaReportInfo 印项目计算记录
     * @return 结果
     */
    @Override
    public int insertFinanceAaReportInfo(FinanceAaReportInfo financeAaReportInfo)
    {
        financeAaReportInfo.setCreateTime(DateUtils.getNowDate());
        return financeAaReportInfoMapper.insertFinanceAaReportInfo(financeAaReportInfo);
    }

    /**
     * 修改印项目计算记录
     *
     * @param financeAaReportInfo 印项目计算记录
     * @return 结果
     */
    @Override
    public int updateFinanceAaReportInfo(FinanceAaReportInfo financeAaReportInfo)
    {
        return financeAaReportInfoMapper.updateFinanceAaReportInfo(financeAaReportInfo);
    }

    /**
     * 批量删除印项目计算记录
     *
     * @param ids 需要删除的印项目计算记录主键
     * @return 结果
     */
    @Override
    public int deleteFinanceAaReportInfoByIds(String[] ids)
    {
        return financeAaReportInfoMapper.deleteFinanceAaReportInfoByIds(ids);
    }

    /**
     * 删除印项目计算记录信息
     *
     * @param id 印项目计算记录主键
     * @return 结果
     */
    @Override
    public int deleteFinanceAaReportInfoById(String id)
    {
        return financeAaReportInfoMapper.deleteFinanceAaReportInfoById(id);
    }
}
