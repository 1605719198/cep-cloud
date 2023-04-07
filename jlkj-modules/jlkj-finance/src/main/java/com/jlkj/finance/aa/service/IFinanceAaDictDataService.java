package com.jlkj.finance.aa.service;

import java.util.List;


import com.jlkj.finance.aa.domain.FinanceAaDictData;
import org.apache.ibatis.annotations.Param;

/**
 * 系统管理-字典数据Service接口
 * 
 * @author 265799
 * @date 2023-04-03
 */
public interface IFinanceAaDictDataService
{
    /**
     * 查询系统管理-字典数据
     *
     * @param dictType 数据名称
     * @return 系统管理-字典数据
     */


    List<FinanceAaDictData> getMainCompIdValue(@Param("dictType") String dictType);

    /**
     * 查询系统管理-字典数据
     *
     * @param dictType 数据名称
     * @param dictValue 数据值
     * @return 系统管理-字典数据
     */

    public  FinanceAaDictData getMainAreaIdValue(@Param("dictType")String dictType,
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
     * 批量删除系统管理-字典数据
     * 
     * @param dictCodes 需要删除的系统管理-字典数据主键集合
     * @return 结果
     */
    public int deleteFinanceAaDictDataByDictCodes(Long[] dictCodes);

    /**
     * 删除系统管理-字典数据信息
     * 
     * @param dictCode 系统管理-字典数据主键
     * @return 结果
     */
    public int deleteFinanceAaDictDataByDictCode(Long dictCode);
}
