package com.jlkj.finance.aa.service.impl;

import java.util.List;
import com.jlkj.common.core.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jlkj.finance.aa.mapper.FinanceAaDictTypeMapper;
import com.jlkj.finance.aa.domain.FinanceAaDictType;
import com.jlkj.finance.aa.service.IFinanceAaDictTypeService;

/**
 * 系统管理-字典类型Service业务层处理
 *
 * @author 265799
 * @date 2023-04-03
 */
@Service
public class FinanceAaDictTypeServiceImpl implements IFinanceAaDictTypeService
{
    @Autowired
    private FinanceAaDictTypeMapper financeAaDictTypeMapper;

    /**
     * 查询系统管理-字典类型
     *
     * @param dictId 系统管理-字典类型主键
     * @return 系统管理-字典类型
     */
    @Override
    public FinanceAaDictType selectFinanceAaDictTypeByDictId(Long dictId)
    {
        return financeAaDictTypeMapper.selectFinanceAaDictTypeByDictId(dictId);
    }

    /**
     * 查询系统管理-字典类型列表
     *
     * @param financeAaDictType 系统管理-字典类型
     * @return 系统管理-字典类型
     */
    @Override
    public List<FinanceAaDictType> selectFinanceAaDictTypeList(FinanceAaDictType financeAaDictType)
    {
        return financeAaDictTypeMapper.selectFinanceAaDictTypeList(financeAaDictType);
    }

    /**
     * 新增系统管理-字典类型
     *
     * @param financeAaDictType 系统管理-字典类型
     * @return 结果
     */
    @Override
    public int insertFinanceAaDictType(FinanceAaDictType financeAaDictType)
    {
        financeAaDictType.setCreateTime(DateUtils.getNowDate());
        return financeAaDictTypeMapper.insertFinanceAaDictType(financeAaDictType);
    }

    /**
     * 修改系统管理-字典类型
     *
     * @param financeAaDictType 系统管理-字典类型
     * @return 结果
     */
    @Override
    public int updateFinanceAaDictType(FinanceAaDictType financeAaDictType)
    {
        financeAaDictType.setUpdateTime(DateUtils.getNowDate());
        return financeAaDictTypeMapper.updateFinanceAaDictType(financeAaDictType);
    }

    /**
     * 批量删除系统管理-字典类型
     *
     * @param dictIds 需要删除的系统管理-字典类型主键
     * @return 结果
     */
    @Override
    public int deleteFinanceAaDictTypeByDictIds(Long[] dictIds)
    {
        return financeAaDictTypeMapper.deleteFinanceAaDictTypeByDictIds(dictIds);
    }

    /**
     * 删除系统管理-字典类型信息
     *
     * @param dictId 系统管理-字典类型主键
     * @return 结果
     */
    @Override
    public int deleteFinanceAaDictTypeByDictId(Long dictId)
    {
        return financeAaDictTypeMapper.deleteFinanceAaDictTypeByDictId(dictId);
    }
}
