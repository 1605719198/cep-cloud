package com.jlkj.finance.aa.mapper;

import java.util.List;
import com.jlkj.finance.aa.domain.FinanceAaDictData;
import org.apache.ibatis.annotations.Param;


/**
 * 系统管理-字典数据Mapper接口
 * 
 * @author 265799
 * @date 2023-04-03
 */
public interface FinanceAaDictDataMapper 
{
    /**
     * 查询系统管理-字典数据列表
     *
     * @param dictType 系统管理-字典类型
     * @return 系统管理-字典数据集合
     */
    public List<FinanceAaDictData> selectMainCompIdValue(@Param("dictType")String dictType);
    /**
     * 查询系统管理-字典数据列表
     *
     * @param dictType 系统管理-字典类型
     * @param dictValue 系统管理-字典值
     * @return 系统管理-字典数据集合
     */
    public FinanceAaDictData selectMainAreaIdValue(@Param("dictType")String dictType,
                                                   @Param("dictValue")String dictValue);

    /**
     * 查询系统管理-字典数据
     * 
     * @param dictCode 系统管理-字典数据主键
     * @return 系统管理-字典数据
     */
    public FinanceAaDictData selectFinanceAaDictDataByDictCode(Long dictCode);

    /**
     * 查询系统管理-字典数据列表
     * 
     * @param financeAaDictData 系统管理-字典数据
     * @return 系统管理-字典数据集合
     */
    public List<FinanceAaDictData> selectFinanceAaDictDataList(FinanceAaDictData financeAaDictData);

    /**
     * 新增系统管理-字典数据
     * 
     * @param financeAaDictData 系统管理-字典数据
     * @return 结果
     */
    public int insertFinanceAaDictData(FinanceAaDictData financeAaDictData);

    /**
     * 修改系统管理-字典数据
     * 
     * @param financeAaDictData 系统管理-字典数据
     * @return 结果
     */
    public int updateFinanceAaDictData(FinanceAaDictData financeAaDictData);

    /**
     * 删除系统管理-字典数据
     * 
     * @param dictCode 系统管理-字典数据主键
     * @return 结果
     */
    public int deleteFinanceAaDictDataByDictCode(Long dictCode);

    /**
     * 批量删除系统管理-字典数据
     * 
     * @param dictCodes 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFinanceAaDictDataByDictCodes(Long[] dictCodes);
}
