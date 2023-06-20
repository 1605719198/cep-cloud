package com.jlkj.finance.ft.mapper;

import com.jlkj.finance.ft.domain.FinanceFtDictData;

import java.util.List;

/**
 * 选单内容设定Mapper接口
 * 
 * @author SunXuTong
 * @date 2023-06-15
 */
public interface FinanceFtDictDataMapper 
{
    /**
     * 查询选单内容设定
     * 
     * @param uuid 选单内容设定主键
     * @return 选单内容设定
     */
    public FinanceFtDictData selectFinanceFtDictDataByUuid(String uuid);

    /**
     * 查询选单内容设定列表
     * 
     * @param financeFtDictData 选单内容设定
     * @return 选单内容设定集合
     */
    public List<FinanceFtDictData> selectFinanceFtDictDataList(FinanceFtDictData financeFtDictData);

    /**
     * 新增选单内容设定
     * 
     * @param financeFtDictData 选单内容设定
     * @return 结果
     */
    public int insertFinanceFtDictData(FinanceFtDictData financeFtDictData);

    /**
     * 修改选单内容设定
     * 
     * @param financeFtDictData 选单内容设定
     * @return 结果
     */
    public int updateFinanceFtDictData(FinanceFtDictData financeFtDictData);

    /**
     * 删除选单内容设定
     * 
     * @param uuid 选单内容设定主键
     * @return 结果
     */
    public int deleteFinanceFtDictDataByUuid(String uuid);

    /**
     * 批量删除选单内容设定
     * 
     * @param uuids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFinanceFtDictDataByUuids(String[] uuids);
}
