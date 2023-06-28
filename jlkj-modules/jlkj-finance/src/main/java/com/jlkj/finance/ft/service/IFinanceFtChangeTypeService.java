package com.jlkj.finance.ft.service;

import com.jlkj.finance.ft.domain.FinanceFtChangeType;

import java.util.List;

/**
 * 固定资产变动方式Service接口
 * 
 * @author SunXuTong
 * @date 2023-06-26
 */
public interface IFinanceFtChangeTypeService 
{
    /**
     * 查询固定资产变动方式
     * 
     * @param uuid 固定资产变动方式主键
     * @return 固定资产变动方式
     */
    public FinanceFtChangeType selectFinanceFtChangeTypeByUuid(String uuid);

    /**
     * 查询固定资产变动方式列表
     * 
     * @param financeFtChangeType 固定资产变动方式
     * @return 固定资产变动方式集合
     */
    public List<FinanceFtChangeType> selectFinanceFtChangeTypeList(FinanceFtChangeType financeFtChangeType);

    /**
     * 新增固定资产变动方式
     * 
     * @param financeFtChangeType 固定资产变动方式
     * @return 结果
     */
    public int insertFinanceFtChangeType(FinanceFtChangeType financeFtChangeType);

    /**
     * 修改固定资产变动方式
     * 
     * @param financeFtChangeType 固定资产变动方式
     * @return 结果
     */
    public int updateFinanceFtChangeType(FinanceFtChangeType financeFtChangeType);

    /**
     * 批量删除固定资产变动方式
     * 
     * @param uuids 需要删除的固定资产变动方式主键集合
     * @return 结果
     */
    public int deleteFinanceFtChangeTypeByUuids(String[] uuids);

    /**
     * 删除固定资产变动方式信息
     * 
     * @param uuid 固定资产变动方式主键
     * @return 结果
     */
    public int deleteFinanceFtChangeTypeByUuid(String uuid);
}
