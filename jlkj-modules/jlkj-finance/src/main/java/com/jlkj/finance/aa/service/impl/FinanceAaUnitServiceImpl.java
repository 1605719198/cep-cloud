package com.jlkj.finance.aa.service.impl;

import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.common.core.utils.uuid.IdUtils;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.finance.aa.domain.FinanceAaUnit;
import com.jlkj.finance.aa.mapper.FinanceAaUnitMapper;
import com.jlkj.finance.aa.service.IFinanceAaUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 计量单位维护Service业务层处理
 *
 * @author SunXuTong
 * @date 2023-04-27
 */
@Service
public class FinanceAaUnitServiceImpl implements IFinanceAaUnitService
{
    @Autowired
    private FinanceAaUnitMapper financeAaUnitMapper;

    /**
     * 查询计量单位维护
     *
     * @param id 计量单位维护主键
     * @return 计量单位维护
     */
    @Override
    public FinanceAaUnit selectFinanceAaUnitById(String id)
    {
        return financeAaUnitMapper.selectFinanceAaUnitById(id);
    }

    /**
     * 查询计量单位维护列表
     *
     * @param financeAaUnit 计量单位维护
     * @return 计量单位维护
     */
    @Override
    public List<FinanceAaUnit> selectFinanceAaUnitList(FinanceAaUnit financeAaUnit)
    {
        return financeAaUnitMapper.selectFinanceAaUnitList(financeAaUnit);
    }

    /**
     * 新增计量单位维护
     *
     * @param financeAaUnit 计量单位维护
     * @return 结果
     */
    @Override
    public int insertFinanceAaUnit(FinanceAaUnit financeAaUnit)
    {
        List<FinanceAaUnit> financeAaUnits = financeAaUnitMapper.selectFinanceAaUnitByUnitNo(financeAaUnit);
        if (financeAaUnits.isEmpty()){
            financeAaUnit.setId(IdUtils.fastSimpleUUID());
            financeAaUnit.setCreateTime(DateUtils.getNowDate());
            financeAaUnit.setCreateBy(SecurityUtils.getUsername());
            financeAaUnit.setCreateName(SecurityUtils.getNickName());
            return financeAaUnitMapper.insertFinanceAaUnit(financeAaUnit);
        } else {
            return 0;
        }
    }

    /**
     * 修改计量单位维护
     *
     * @param financeAaUnit 计量单位维护
     * @return 结果
     */
    @Override
    public int updateFinanceAaUnit(FinanceAaUnit financeAaUnit)
    {
        financeAaUnit.setUpdateTime(DateUtils.getNowDate());
        financeAaUnit.setUpdateBy(SecurityUtils.getUsername());
        financeAaUnit.setUpdateName(SecurityUtils.getNickName());
        return financeAaUnitMapper.updateFinanceAaUnit(financeAaUnit);
    }

    /**
     * 批量删除计量单位维护
     *
     * @param ids 需要删除的计量单位维护主键
     * @return 结果
     */
    @Override
    public int deleteFinanceAaUnitByIds(String[] ids)
    {
        return financeAaUnitMapper.deleteFinanceAaUnitByIds(ids);
    }

    /**
     * 删除计量单位维护信息
     *
     * @param id 计量单位维护主键
     * @return 结果
     */
    @Override
    public int deleteFinanceAaUnitById(String id)
    {
        return financeAaUnitMapper.deleteFinanceAaUnitById(id);
    }
}
