package com.jlkj.finance.aa.service.impl;

import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.common.core.utils.uuid.IdUtils;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.finance.aa.domain.FinanceAaAssetcategory;
import com.jlkj.finance.aa.mapper.FinanceAaAssetcategoryMapper;
import com.jlkj.finance.aa.service.IFinanceAaAssetcategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 资产大类维护Service业务层处理
 *
 * @author SunXuTong
 * @date 2023-05-09
 */
@Service
public class FinanceAaAssetcategoryServiceImpl implements IFinanceAaAssetcategoryService
{
    @Autowired
    private FinanceAaAssetcategoryMapper financeAaAssetcategoryMapper;

    /**
     * 查询资产大类维护
     *
     * @param id 资产大类维护主键
     * @return 资产大类维护
     */
    @Override
    public FinanceAaAssetcategory selectFinanceAaAssetcategoryById(String id)
    {
        return financeAaAssetcategoryMapper.selectFinanceAaAssetcategoryById(id);
    }

    /**
     * 查询资产大类维护列表
     *
     * @param financeAaAssetcategory 资产大类维护
     * @return 资产大类维护
     */
    @Override
    public List<FinanceAaAssetcategory> selectFinanceAaAssetcategoryList(FinanceAaAssetcategory financeAaAssetcategory)
    {
        return financeAaAssetcategoryMapper.selectFinanceAaAssetcategoryList(financeAaAssetcategory);
    }

    /**
     * 新增资产大类维护
     *
     * @param financeAaAssetcategory 资产大类维护
     * @return 结果
     */
    @Override
    public int insertFinanceAaAssetcategory(FinanceAaAssetcategory financeAaAssetcategory)
    {
        financeAaAssetcategory.setId(IdUtils.fastSimpleUUID());
        financeAaAssetcategory.setCreateTime(DateUtils.getNowDate());
        financeAaAssetcategory.setCreateBy(SecurityUtils.getUsername());
        financeAaAssetcategory.setCreateName(SecurityUtils.getNickName());
        return financeAaAssetcategoryMapper.insertFinanceAaAssetcategory(financeAaAssetcategory);
    }

    /**
     * 修改资产大类维护
     *
     * @param financeAaAssetcategory 资产大类维护
     * @return 结果
     */
    @Override
    public int updateFinanceAaAssetcategory(FinanceAaAssetcategory financeAaAssetcategory)
    {
        financeAaAssetcategory.setUpdateTime(DateUtils.getNowDate());
        financeAaAssetcategory.setUpdateBy(SecurityUtils.getUsername());
        financeAaAssetcategory.setUpdateName(SecurityUtils.getNickName());
        return financeAaAssetcategoryMapper.updateFinanceAaAssetcategory(financeAaAssetcategory);
    }

    /**
     * 批量删除资产大类维护
     *
     * @param ids 需要删除的资产大类维护主键
     * @return 结果
     */
    @Override
    public int deleteFinanceAaAssetcategoryByIds(String[] ids)
    {
        return financeAaAssetcategoryMapper.deleteFinanceAaAssetcategoryByIds(ids);
    }

    /**
     * 删除资产大类维护信息
     *
     * @param id 资产大类维护主键
     * @return 结果
     */
    @Override
    public int deleteFinanceAaAssetcategoryById(String id)
    {
        return financeAaAssetcategoryMapper.deleteFinanceAaAssetcategoryById(id);
    }

    /**
     * 查询资产大类（下拉选单用）
     * @return
     */
    @Override
    public List<Map<String,String>> selectAssetGroupList(){
        return financeAaAssetcategoryMapper.selectAssetGroupList();
    }
}
