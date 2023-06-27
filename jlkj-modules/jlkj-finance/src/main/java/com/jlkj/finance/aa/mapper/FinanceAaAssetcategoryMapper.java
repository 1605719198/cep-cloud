package com.jlkj.finance.aa.mapper;

import com.jlkj.finance.aa.domain.FinanceAaAssetcategory;

import java.util.List;
import java.util.Map;

/**
 * 资产大类维护Mapper接口
 * 
 * @author SunXuTong
 * @date 2023-05-09
 */
public interface FinanceAaAssetcategoryMapper 
{
    /**
     * 查询资产大类维护
     * 
     * @param id 资产大类维护主键
     * @return 资产大类维护
     */
    public FinanceAaAssetcategory selectFinanceAaAssetcategoryById(String id);

    /**
     * 查询资产大类维护列表
     * 
     * @param financeAaAssetcategory 资产大类维护
     * @return 资产大类维护集合
     */
    public List<FinanceAaAssetcategory> selectFinanceAaAssetcategoryList(FinanceAaAssetcategory financeAaAssetcategory);

    /**
     * 新增资产大类维护
     * 
     * @param financeAaAssetcategory 资产大类维护
     * @return 结果
     */
    public int insertFinanceAaAssetcategory(FinanceAaAssetcategory financeAaAssetcategory);

    /**
     * 修改资产大类维护
     * 
     * @param financeAaAssetcategory 资产大类维护
     * @return 结果
     */
    public int updateFinanceAaAssetcategory(FinanceAaAssetcategory financeAaAssetcategory);

    /**
     * 删除资产大类维护
     * 
     * @param id 资产大类维护主键
     * @return 结果
     */
    public int deleteFinanceAaAssetcategoryById(String id);

    /**
     * 批量删除资产大类维护
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFinanceAaAssetcategoryByIds(String[] ids);

    /**
     * 查询资产大类（下拉选单用）
     * @return
     */
    public List<Map<String,String>> selectAssetGroupList();
}
