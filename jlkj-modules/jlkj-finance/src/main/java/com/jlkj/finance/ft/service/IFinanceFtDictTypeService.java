package com.jlkj.finance.ft.service;

import com.jlkj.finance.ft.domain.FinanceFtDictType;

import java.util.List;

/**
 * 选单设定Service接口
 * 
 * @author SunXuTong
 * @date 2023-06-15
 */
public interface IFinanceFtDictTypeService 
{
    /**
     * 查询选单设定
     * 
     * @param dictId 选单设定主键
     * @return 选单设定
     */
    public FinanceFtDictType selectFinanceFtDictTypeByDictId(String dictId);

    /**
     * 查询选单设定列表
     * 
     * @param financeFtDictType 选单设定
     * @return 选单设定集合
     */
    public List<FinanceFtDictType> selectFinanceFtDictTypeList(FinanceFtDictType financeFtDictType);

    /**
     * 新增选单设定
     * 
     * @param financeFtDictType 选单设定
     * @return 结果
     */
    public int insertFinanceFtDictType(FinanceFtDictType financeFtDictType);

    /**
     * 修改选单设定
     * 
     * @param financeFtDictType 选单设定
     * @return 结果
     */
    public int updateFinanceFtDictType(FinanceFtDictType financeFtDictType);

    /**
     * 批量删除选单设定
     * 
     * @param dictIds 需要删除的选单设定主键集合
     * @return 结果
     */
    public int deleteFinanceFtDictTypeByDictIds(String[] dictIds);

    /**
     * 删除选单设定信息
     * 
     * @param dictId 选单设定主键
     * @return 结果
     */
    public int deleteFinanceFtDictTypeByDictId(String dictId);
}
