package com.jlkj.finance.aa.mapper;

import java.util.List;
import com.jlkj.finance.aa.domain.FinanceAaDictType;

/**
 * 系统管理-字典类型Mapper接口
 * 
 * @author 265799
 * @date 2023-04-03
 */
public interface FinanceAaDictTypeMapper 
{
    /**
     * 查询系统管理-字典类型
     * 
     * @param dictId 系统管理-字典类型主键
     * @return 系统管理-字典类型
     */
    public FinanceAaDictType selectFinanceAaDictTypeByDictId(Long dictId);

    /**
     * 查询系统管理-字典类型列表
     * 
     * @param financeAaDictType 系统管理-字典类型
     * @return 系统管理-字典类型集合
     */
    public List<FinanceAaDictType> selectFinanceAaDictTypeList(FinanceAaDictType financeAaDictType);

    /**
     * 新增系统管理-字典类型
     * 
     * @param financeAaDictType 系统管理-字典类型
     * @return 结果
     */
    public int insertFinanceAaDictType(FinanceAaDictType financeAaDictType);

    /**
     * 修改系统管理-字典类型
     * 
     * @param financeAaDictType 系统管理-字典类型
     * @return 结果
     */
    public int updateFinanceAaDictType(FinanceAaDictType financeAaDictType);

    /**
     * 删除系统管理-字典类型
     * 
     * @param dictId 系统管理-字典类型主键
     * @return 结果
     */
    public int deleteFinanceAaDictTypeByDictId(Long dictId);

    /**
     * 批量删除系统管理-字典类型
     * 
     * @param dictIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFinanceAaDictTypeByDictIds(Long[] dictIds);
}
