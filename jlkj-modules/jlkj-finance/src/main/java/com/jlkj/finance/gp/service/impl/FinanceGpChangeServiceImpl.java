package com.jlkj.finance.gp.service.impl;

import java.util.List;

import com.jlkj.common.core.exception.ServiceException;
import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.finance.gp.domain.FinanceGpAdd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jlkj.finance.gp.mapper.FinanceGpChangeMapper;
import com.jlkj.finance.gp.domain.FinanceGpChange;
import com.jlkj.finance.gp.service.IFinanceGpChangeService;

/**
 * 厂商异动申请Service业务层处理
 *
 * @author 265799
 * @date 2023-04-12
 */
@Service
public class FinanceGpChangeServiceImpl implements IFinanceGpChangeService
{
    @Autowired
    private FinanceGpChangeMapper financeGpChangeMapper;

    /**
     * 查询厂商异动申请
     *
     * @param applyId 厂商异动申请主键
     * @return 厂商异动申请
     */
    @Override
    public FinanceGpChange selectFinanceGpChangeByApplyId(String applyId)
    {
        return financeGpChangeMapper.selectFinanceGpChangeByApplyId(applyId);
    }

    /**
     * 查询厂商异动申请列表
     *
     * @param financeGpChange 厂商异动申请
     * @return 厂商异动申请
     */
    @Override
    public List<FinanceGpChange> selectFinanceGpChangeList(FinanceGpChange financeGpChange)
    {
        return financeGpChangeMapper.selectFinanceGpChangeList(financeGpChange);
    }

    /**
     * 新增厂商异动申请
     *
     * @param financeGpChange 厂商异动申请
     * @return 结果
     */
    @Override
    public int insertFinanceGpChange(FinanceGpChange financeGpChange)
    {
        FinanceGpChange financeGpChangeSelect = new FinanceGpChange();
        financeGpChange.setCreateTime(DateUtils.getNowDate());

        financeGpChangeSelect.setManufacturerName(financeGpChange.getManufacturerName());
        List<FinanceGpChange> financeGpChanges = financeGpChangeMapper.selectFinanceGpChangeList(financeGpChange);
        if (financeGpChanges.size()>0){
            throw new ServiceException("该公司已存在增户申请资料！");
        }

        List<FinanceGpChange> list = financeGpChangeMapper.selectFinanceGpChangeDateList(financeGpChange);
        String financeGpChangeList = null;
        if(list.size()>0){
            for (int i = 0 ;i<list.size();i++){
                financeGpChangeList = list.get(i).getApplyId();
            }
        }else {
            financeGpChangeList = DateUtils.dateTime()+"B"+"00";
        }
        String financeGpChangeId = String.valueOf(Integer.parseInt(financeGpChangeList.substring(9, 11)) + 1);
        int manufacturerIdInt = Integer.parseInt(financeGpChangeList.substring(9, 11)) + 1;
        int manufacturerIdInt10 = 10;
        int manufacturerIdInt100 = 100;
        String financeGp ="B0"+ financeGpChangeId ;
        if (manufacturerIdInt10 > manufacturerIdInt){
            financeGpChange.setApplyId(DateUtils.dateTime()+financeGp);
        }else if (manufacturerIdInt100 > manufacturerIdInt){
            financeGpChange.setApplyId(DateUtils.dateTime()+"B"+financeGpChangeId);
        }
        return financeGpChangeMapper.insertFinanceGpChange(financeGpChange);
    }

    /**
     * 修改厂商异动申请
     *
     * @param financeGpChange 厂商异动申请
     * @return 结果
     */
    @Override
    public int updateFinanceGpChange(FinanceGpChange financeGpChange)
    {
        financeGpChange.setUpdateTime(DateUtils.getNowDate());


        return financeGpChangeMapper.updateFinanceGpChange(financeGpChange);
    }

    /**
     * 批量删除厂商异动申请
     *
     * @param applyIds 需要删除的厂商异动申请主键
     * @return 结果
     */
    @Override
    public int deleteFinanceGpChangeByApplyIds(String[] applyIds)
    {
        return financeGpChangeMapper.deleteFinanceGpChangeByApplyIds(applyIds);
    }

    /**
     * 删除厂商异动申请信息
     *
     * @param applyId 厂商异动申请主键
     * @return 结果
     */
    @Override
    public int deleteFinanceGpChangeByApplyId(String applyId)
    {
        return financeGpChangeMapper.deleteFinanceGpChangeByApplyId(applyId);
    }
}
