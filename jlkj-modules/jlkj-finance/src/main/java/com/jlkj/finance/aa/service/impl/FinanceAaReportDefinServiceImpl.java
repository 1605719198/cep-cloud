package com.jlkj.finance.aa.service.impl;

import com.jlkj.common.core.exception.ServiceException;
import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.common.core.utils.uuid.IdUtils;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.finance.aa.domain.FinanceAaReportDefin;
import com.jlkj.finance.aa.mapper.FinanceAaReportDefinMapper;
import com.jlkj.finance.aa.service.IFinanceAaReportDefinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 集团管理-报表定义Service业务层处理
 *
 * @author 116514
 * @date 2023-05-04
 */
@Service
public class FinanceAaReportDefinServiceImpl implements IFinanceAaReportDefinService
{
    @Autowired
    private FinanceAaReportDefinMapper financeAaReportDefinMapper;

    /**
     * 查询集团管理-报表定义
     *
     * @param reportId 集团管理-报表定义主键
     * @return 集团管理-报表定义
     */
    @Override
    public FinanceAaReportDefin selectFinanceAaReportDefinByReportId(String reportId)
    {
        return financeAaReportDefinMapper.selectFinanceAaReportDefinByReportId(reportId);
    }

    /**
     * 查询集团管理-报表定义列表
     *
     * @param financeAaReportDefin 集团管理-报表定义
     * @return 集团管理-报表定义
     */
    @Override
    public List<FinanceAaReportDefin> selectFinanceAaReportDefinList(FinanceAaReportDefin financeAaReportDefin)
    {
        return financeAaReportDefinMapper.selectFinanceAaReportDefinList(financeAaReportDefin);
    }

    /**
     * 新增集团管理-报表定义
     *
     * @param financeAaReportDefin 集团管理-报表定义
     * @return 结果
     */
    @Override
    public int insertFinanceAaReportDefin(FinanceAaReportDefin financeAaReportDefin)
    {
        // 捡核资料是否重复【报表代号+公司别】
        int count = financeAaReportDefinMapper.updateCheckReportDefin(financeAaReportDefin);
        if(count>0){
            throw new ServiceException("资料重复，不可新增！");
        }
        financeAaReportDefin.setReportId(IdUtils.simpleUUID());
        financeAaReportDefin.setCreateBy(SecurityUtils.getUsername());
        financeAaReportDefin.setCreateName(SecurityUtils.getNickName());
        financeAaReportDefin.setCreateTime(DateUtils.getNowDate());
        return financeAaReportDefinMapper.insertFinanceAaReportDefin(financeAaReportDefin);
    }

    /**
     * 修改集团管理-报表定义
     *
     * @param financeAaReportDefin 集团管理-报表定义
     * @return 结果
     */
    @Override
    public int updateFinanceAaReportDefin(FinanceAaReportDefin financeAaReportDefin)
    {
        financeAaReportDefin.setUpdateBy(SecurityUtils.getUsername());
        financeAaReportDefin.setUpdateName(SecurityUtils.getNickName());
        financeAaReportDefin.setUpdateTime(DateUtils.getNowDate());
        return financeAaReportDefinMapper.updateFinanceAaReportDefin(financeAaReportDefin);
    }

    /**
     * 批量删除集团管理-报表定义
     *
     * @param reportIds 需要删除的集团管理-报表定义主键
     * @return 结果
     */
    @Override
    public int deleteFinanceAaReportDefinByReportIds(String[] reportIds)
    {
        return financeAaReportDefinMapper.deleteFinanceAaReportDefinByReportIds(reportIds);
    }

    /**
     * 删除集团管理-报表定义信息
     *
     * @param reportId 集团管理-报表定义主键
     * @return 结果
     */
    @Override
    public int deleteFinanceAaReportDefinByReportId(String reportId)
    {
        return financeAaReportDefinMapper.deleteFinanceAaReportDefinByReportId(reportId);
    }

    /**
     * 报表代号选单查询
     * @param compId
     * @return
     */
    @Override
    public List<Map<String,String>> selectReportNoList(String compId){
        return financeAaReportDefinMapper.selectReportNoList(compId);
    }
}
