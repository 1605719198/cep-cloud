package com.jlkj.finance.ft.mapper;

import com.jlkj.finance.ft.domain.FinanceFtGroup;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 资产大类基本资料Mapper接口
 * 
 * @author SunXuTong
 * @date 2023-06-21
 */
@Repository
public interface FinanceFtGroupMapper 
{
    /**
     * 查询资产大类基本资料
     * 
     * @param id 资产大类基本资料主键
     * @return 资产大类基本资料
     */
    public FinanceFtGroup selectFinanceFtGroupById(String id);

    /**
     * 查询资产大类基本资料列表
     * 
     * @param financeFtGroup 资产大类基本资料
     * @return 资产大类基本资料集合
     */
    public List<FinanceFtGroup> selectFinanceFtGroupList(FinanceFtGroup financeFtGroup);

    /**
     * 查询资产大类基本资料列表 查重
     *
     * @param financeFtGroup 资产大类基本资料
     * @return 资产大类基本资料集合
     */
    public List<FinanceFtGroup> selectFinanceFtGroupByCompIdAndAssetGroupNo(FinanceFtGroup financeFtGroup);

    /**
     * 新增资产大类基本资料
     * 
     * @param financeFtGroup 资产大类基本资料
     * @return 结果
     */
    public int insertFinanceFtGroup(FinanceFtGroup financeFtGroup);

    /**
     * 修改资产大类基本资料
     * 
     * @param financeFtGroup 资产大类基本资料
     * @return 结果
     */
    public int updateFinanceFtGroup(FinanceFtGroup financeFtGroup);

    /**
     * 删除资产大类基本资料
     * 
     * @param id 资产大类基本资料主键
     * @return 结果
     */
    public int deleteFinanceFtGroupById(String id);

    /**
     * 批量删除资产大类基本资料
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFinanceFtGroupByIds(String[] ids);
}
