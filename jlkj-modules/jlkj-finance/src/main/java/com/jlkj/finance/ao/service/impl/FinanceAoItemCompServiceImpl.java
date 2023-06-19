package com.jlkj.finance.ao.service.impl;

import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.common.core.utils.uuid.IdUtils;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.finance.ao.domain.FinanceAoItemComp;
import com.jlkj.finance.ao.domain.FinanceAoItemCompDetail;
import com.jlkj.finance.ao.domain.FinanceAoItemDetail;
import com.jlkj.finance.ao.mapper.FinanceAoItemCompDetailMapper;
import com.jlkj.finance.ao.mapper.FinanceAoItemCompMapper;
import com.jlkj.finance.ao.mapper.FinanceAoItemDetailMapper;
import com.jlkj.finance.ao.service.IFinanceAoItemCompService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

/**
 * 报支类别-公司级-设定主档Service业务层处理
 *
 * @author 116514
 * @date 2023-05-23
 */
@Service
public class FinanceAoItemCompServiceImpl implements IFinanceAoItemCompService
{
    @Autowired
    private FinanceAoItemCompMapper financeAoItemCompMapper;

    @Autowired
    private FinanceAoItemDetailMapper financeAoItemDetailMapper;

    @Autowired
    private FinanceAoItemCompDetailMapper financeAoItemCompDetailMapper;

    /**
     * 查询报支类别-公司级-设定主档
     *
     * @param id 报支类别-公司级-设定主档主键
     * @return 报支类别-公司级-设定主档
     */
    @Override
    public FinanceAoItemComp selectFinanceAoItemCompById(String id)
    {
        return financeAoItemCompMapper.selectFinanceAoItemCompById(id);
    }

    /**
     * 查询报支类别-公司级-设定主档列表
     *
     * @param financeAoItemComp 报支类别-公司级-设定主档
     * @return 报支类别-公司级-设定主档
     */
    @Override
    public List<FinanceAoItemComp> selectFinanceAoItemCompList(FinanceAoItemComp financeAoItemComp)
    {
        return financeAoItemCompMapper.selectFinanceAoItemCompList(financeAoItemComp);
    }

    /**
     * 新增报支类别-公司级-设定主档
     *
     * @param financeAoItemComp 报支类别-公司级-设定主档
     * @return 结果
     */
    @Override
    public int insertFinanceAoItemComp(FinanceAoItemComp financeAoItemComp)
    {
        financeAoItemComp.setCreateTime(DateUtils.getNowDate());
        return financeAoItemCompMapper.insertFinanceAoItemComp(financeAoItemComp);
    }

    /**
     * 修改报支类别-公司级-设定主档
     *
     * @param financeAoItemComp 报支类别-公司级-设定主档
     * @return 结果
     */
    @Override
    public int updateFinanceAoItemComp(FinanceAoItemComp financeAoItemComp)
    {
        financeAoItemComp.setUpdateBy(SecurityUtils.getUsername());
        financeAoItemComp.setUpdateName(SecurityUtils.getNickName());
        financeAoItemComp.setUpdateTime(DateUtils.getNowDate());
        return financeAoItemCompMapper.updateFinanceAoItemComp(financeAoItemComp);
    }

    /**
     * 批量删除报支类别-公司级-设定主档
     *
     * @param ids 需要删除的报支类别-公司级-设定主档主键
     * @return 结果
     */
    @Override
    public int deleteFinanceAoItemCompByIds(String[] ids)
    {
        List<String> detailIds = financeAoItemCompMapper.selectDetailsByIds(ids);
        if(detailIds.size()>0){
            financeAoItemCompDetailMapper.deleteFinanceAoItemCompDetailByIds(detailIds.stream().toArray(String[]::new));
        }
        return financeAoItemCompMapper.deleteFinanceAoItemCompByIds(ids);
    }

    /**
     * 删除报支类别-公司级-设定主档信息
     *
     * @param id 报支类别-公司级-设定主档主键
     * @return 结果
     */
    @Override
    public int deleteFinanceAoItemCompById(String id)
    {
        return financeAoItemCompMapper.deleteFinanceAoItemCompById(id);
    }

    /**
     * 批量新增公司级报支类别
     * @param financeAoItemComps
     * @return
     */
    @Override
    public int insertBatch(List<FinanceAoItemComp> financeAoItemComps){
        String companyId = financeAoItemComps.get(0).getCompanyId();
        // 提取itemNos
        StringJoiner sj = new StringJoiner("-");
        for (FinanceAoItemComp financeAoItemComp : financeAoItemComps) {
            financeAoItemComp.setId(IdUtils.simpleUUID());
            financeAoItemComp.setCreateBy(SecurityUtils.getUsername());
            financeAoItemComp.setCreateName(SecurityUtils.getNickName());
            financeAoItemComp.setCreateTime(DateUtils.getNowDate());
            sj.add(financeAoItemComp.getItemNo());
        }
        List<FinanceAoItemDetail> financeAoItemDetails = financeAoItemDetailMapper.selectItemDetailsByNos(sj.toString().split("-"));
        List<FinanceAoItemCompDetail> FinanceAoItemCompDetails = new ArrayList<>();
        for (FinanceAoItemDetail financeAoItemDetail : financeAoItemDetails) {
            FinanceAoItemCompDetail itemCompDetail = new FinanceAoItemCompDetail();
            itemCompDetail.setId(IdUtils.simpleUUID());
            itemCompDetail.setCompanyId(companyId);
            itemCompDetail.setItemNo(financeAoItemDetail.getItemNo());
            itemCompDetail.setDetailNo(financeAoItemDetail.getDetailNo());
            itemCompDetail.setDetailName(financeAoItemDetail.getDetailName());
            itemCompDetail.setExpenseType(financeAoItemDetail.getExpenseType());
            itemCompDetail.setDrAcctCode("");
            itemCompDetail.setCrAcctCode("");
            itemCompDetail.setBudgetType(financeAoItemDetail.getBudgetType());
            itemCompDetail.setCostCode(financeAoItemDetail.getCostCode());
            itemCompDetail.setStatus(financeAoItemDetail.getStatus());
            itemCompDetail.setOtherType(financeAoItemDetail.getOtherType());
            itemCompDetail.setOtherDesc("");
            itemCompDetail.setCreateBy(SecurityUtils.getUsername());
            itemCompDetail.setCreateName(SecurityUtils.getNickName());
            itemCompDetail.setCreateTime(DateUtils.getNowDate());
            FinanceAoItemCompDetails.add(itemCompDetail);
        }
        // 新增明细档
        if(FinanceAoItemCompDetails.size()>0){
            financeAoItemCompDetailMapper.insertBatch(FinanceAoItemCompDetails);
        }
        // 新增主档
        return financeAoItemCompMapper.insertBatch(financeAoItemComps);
    }


    /**
     * 查询报支类别下拉选单
     * @return
     */
    @Override
    public List<Map<String,String>> selectItemNoList(String companyId){
        return financeAoItemCompMapper.selectItemNoList(companyId);
    }
}
