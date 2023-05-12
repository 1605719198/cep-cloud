package com.jlkj.finance.aa.mapper;

import java.util.List;
import java.util.Map;

import com.jlkj.finance.aa.domain.FinanceCaltype;

/**
 * 核算项目-类别维护Mapper接口
 * 
 * @author 116514
 * @date 2023-04-06
 */
public interface FinanceCaltypeMapper 
{
    /**
     * 查询核算项目-类别维护
     * 
     * @param id 核算项目-类别维护主键
     * @return 核算项目-类别维护
     */
    public FinanceCaltype selectFinanceCaltypeById(String id);

    /**
     * 查询核算项目-类别维护列表
     * 
     * @param financeCaltype 核算项目-类别维护
     * @return 核算项目-类别维护集合
     */
    public List<FinanceCaltype> selectFinanceCaltypeList(FinanceCaltype financeCaltype);

    /**
     * 新增核算项目-类别维护
     * 
     * @param financeCaltype 核算项目-类别维护
     * @return 结果
     */
    public int insertFinanceCaltype(FinanceCaltype financeCaltype);

    /**
     * 修改核算项目-类别维护
     * 
     * @param financeCaltype 核算项目-类别维护
     * @return 结果
     */
    public int updateFinanceCaltype(FinanceCaltype financeCaltype);

    /**
     * 删除核算项目-类别维护
     * 
     * @param id 核算项目-类别维护主键
     * @return 结果
     */
    public int deleteFinanceCaltypeById(String id);

    /**
     * 批量删除核算项目-类别维护
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFinanceCaltypeByIds(String[] ids);

    /**
     * 查询核算项目类别(下拉选单用)
     * @return
     */
    public List<Map<String,String>> selectCalTypeList();
    /**
     * 查询核算项目类别(下拉选单用)
     * @param financeCaltype 核算项目-类别维护
     * @return
     */
    public FinanceCaltype selectCalTypeSystemList(FinanceCaltype financeCaltype);
}
