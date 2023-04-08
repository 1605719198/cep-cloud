package com.jlkj.finance.aa.service.impl;

import java.util.List;


import com.jlkj.common.core.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jlkj.finance.aa.mapper.FinanceAaDictDataMapper;
import com.jlkj.finance.aa.domain.FinanceAaDictData;
import com.jlkj.finance.aa.service.IFinanceAaDictDataService;

/**
 * 系统管理-字典数据Service业务层处理
 *
 * @author 265799
 * @date 2023-04-03
 */
@Service
public class FinanceAaDictDataServiceImpl implements IFinanceAaDictDataService
{
    @Autowired
    private FinanceAaDictDataMapper financeAaDictDataMapper;

    @Override
    public List<FinanceAaDictData> getMainCompIdValue(String dictType) {
        return financeAaDictDataMapper.selectMainCompIdValue(dictType);
    }

    @Override
    public FinanceAaDictData getMainAreaIdValue(String dictType, String dictValue) {
        return financeAaDictDataMapper.selectMainAreaIdValue(dictType,dictValue);
    }

    /**
     * 查询系统管理-字典数据
     *
     * @param dictCode 系统管理-字典数据主键
     * @return 系统管理-字典数据
     */
    @Override
    public FinanceAaDictData selectFinanceAaDictDataByDictCode(Long dictCode)
    {
        return financeAaDictDataMapper.selectFinanceAaDictDataByDictCode(dictCode);
    }

    /**
     * 查询系统管理-字典数据列表
     *
     * @param financeAaDictData 系统管理-字典数据
     * @return 系统管理-字典数据
     */
    @Override
    public List<FinanceAaDictData> selectFinanceAaDictDataList(FinanceAaDictData financeAaDictData)
    {
        return financeAaDictDataMapper.selectFinanceAaDictDataList(financeAaDictData);
    }

    /**
     * 新增系统管理-字典数据
     *
     * @param financeAaDictData 系统管理-字典数据
     * @return 结果
     */
    @Override
    public int insertFinanceAaDictData(FinanceAaDictData financeAaDictData)
    {
        financeAaDictData.setCreateTime(DateUtils.getNowDate());
        return financeAaDictDataMapper.insertFinanceAaDictData(financeAaDictData);
    }

    /**
     * 修改系统管理-字典数据
     *
     * @param financeAaDictData 系统管理-字典数据
     * @return 结果
     */
    @Override
    public int updateFinanceAaDictData(FinanceAaDictData financeAaDictData)
    {
        financeAaDictData.setUpdateTime(DateUtils.getNowDate());
        return financeAaDictDataMapper.updateFinanceAaDictData(financeAaDictData);
    }

    /**
     * 批量删除系统管理-字典数据
     *
     * @param dictCodes 需要删除的系统管理-字典数据主键
     * @return 结果
     */
    @Override
    public int deleteFinanceAaDictDataByDictCodes(Long[] dictCodes)
    {
        return financeAaDictDataMapper.deleteFinanceAaDictDataByDictCodes(dictCodes);
    }

    /**
     * 删除系统管理-字典数据信息
     *
     * @param dictCode 系统管理-字典数据主键
     * @return 结果
     */
    @Override
    public int deleteFinanceAaDictDataByDictCode(Long dictCode)
    {
        return financeAaDictDataMapper.deleteFinanceAaDictDataByDictCode(dictCode);
    }
}
