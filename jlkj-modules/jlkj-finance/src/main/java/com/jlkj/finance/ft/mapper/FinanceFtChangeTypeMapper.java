package com.jlkj.finance.ft.mapper;

import com.jlkj.finance.ft.domain.FinanceFtChangeType;

import java.util.List;

/**
 * 固定资产变动方式Mapper接口
 * 
 * @author SunXuTong
 * @date 2023-06-26
 */
public interface FinanceFtChangeTypeMapper 
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
     * 查询固定资产变动方式列表 查重
     *
     * @param financeFtChangeType 固定资产变动方式
     * @return 固定资产变动方式集合
     */
    public List<FinanceFtChangeType> selectFinanceFtChangeTypeByCompIdAndChangeWay(FinanceFtChangeType financeFtChangeType);

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
     * 删除固定资产变动方式
     * 
     * @param uuid 固定资产变动方式主键
     * @return 结果
     */
    public int deleteFinanceFtChangeTypeByUuid(String uuid);

    /**
     * 批量删除固定资产变动方式
     * 
     * @param uuids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFinanceFtChangeTypeByUuids(String[] uuids);
}
