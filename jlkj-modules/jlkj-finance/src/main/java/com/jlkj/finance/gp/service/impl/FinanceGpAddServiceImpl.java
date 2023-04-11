package com.jlkj.finance.gp.service.impl;

import java.util.List;

import com.jlkj.common.core.exception.ServiceException;
import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.finance.gp.domain.ManufacturerBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jlkj.finance.gp.mapper.FinanceGpAddMapper;
import com.jlkj.finance.gp.domain.FinanceGpAdd;
import com.jlkj.finance.gp.service.IFinanceGpAddService;

/**
 * 厂商增户申请Service业务层处理
 *
 * @author 265799
 * @date 2023-04-10
 */
@Service
public class FinanceGpAddServiceImpl implements IFinanceGpAddService
{
    @Autowired
    private FinanceGpAddMapper financeGpAddMapper;

    /**
     * 查询厂商增户申请
     *
     * @param applyId 厂商增户申请主键
     * @return 厂商增户申请
     */
    @Override
    public FinanceGpAdd selectFinanceGpAddByApplyId(String applyId)
    {
        return financeGpAddMapper.selectFinanceGpAddByApplyId(applyId);
    }

    /**
     * 查询厂商增户申请列表
     *
     * @param financeGpAdd 厂商增户申请
     * @return 厂商增户申请
     */
    @Override
    public List<FinanceGpAdd> selectFinanceGpAddList(FinanceGpAdd financeGpAdd)
    {
        return financeGpAddMapper.selectFinanceGpAddList(financeGpAdd);
    }

    /**
     * 新增厂商增户申请
     *
     * @param financeGpAdd 厂商增户申请
     * @return 结果
     */
    @Override
    public int insertFinanceGpAdd(FinanceGpAdd financeGpAdd)
    {
        FinanceGpAdd financeGpAddSelect = new FinanceGpAdd();
        financeGpAdd.setCreateTime(DateUtils.getNowDate());
        financeGpAdd.setApplyId(DateUtils.dateTime());
        financeGpAddSelect.setManufacturerName(financeGpAdd.getManufacturerName());
        financeGpAddSelect.setOwner(financeGpAdd.getOwner());
        List<FinanceGpAdd> financeGpAdds = financeGpAddMapper.selectFinanceGpAddList(financeGpAddSelect);
        if (financeGpAdds.size()>0){
            throw new ServiceException("该公司已存在增户申请资料！");
        }

        List<FinanceGpAdd> list = financeGpAddMapper.selectFinanceGpAddDateList(financeGpAdd);
        String financeGpAddList =null;
        if(list.size()>0){
            for (int i = 0 ;i<list.size();i++){
                financeGpAddList = list.get(i).getApplyId();
            }
        }else {
            financeGpAddList = DateUtils.dateTime()+"A"+"00";
        }
        String financeGpAddId = String.valueOf(Integer.parseInt(financeGpAddList.substring(9, 11)) + 1);
        int manufacturerIdInt = Integer.parseInt(financeGpAddList.substring(9, 11)) + 1;
        int manufacturerIdInt10 = 10;
        int manufacturerIdInt100 = 100;
        String financeGp ="A0"+ financeGpAddId ;
        if (manufacturerIdInt10 > manufacturerIdInt){
            financeGpAdd.setApplyId(DateUtils.dateTime()+financeGp);
        }else if (manufacturerIdInt100 > manufacturerIdInt){
            financeGpAdd.setApplyId(DateUtils.dateTime()+"A"+financeGpAddId);
        }

        return financeGpAddMapper.insertFinanceGpAdd(financeGpAdd);
    }

    /**
     * 修改厂商增户申请
     *
     * @param financeGpAdd 厂商增户申请
     * @return 结果
     */
    @Override
    public int updateFinanceGpAdd(FinanceGpAdd financeGpAdd)
    {
        financeGpAdd.setUpdateTime(DateUtils.getNowDate());
        return financeGpAddMapper.updateFinanceGpAdd(financeGpAdd);
    }

    /**
     * 批量删除厂商增户申请
     *
     * @param applyIds 需要删除的厂商增户申请主键
     * @return 结果
     */
    @Override
    public int deleteFinanceGpAddByApplyIds(String[] applyIds)
    {
        return financeGpAddMapper.deleteFinanceGpAddByApplyIds(applyIds);
    }

    /**
     * 删除厂商增户申请信息
     *
     * @param applyId 厂商增户申请主键
     * @return 结果
     */
    @Override
    public int deleteFinanceGpAddByApplyId(String applyId)
    {
        return financeGpAddMapper.deleteFinanceGpAddByApplyId(applyId);
    }
}
