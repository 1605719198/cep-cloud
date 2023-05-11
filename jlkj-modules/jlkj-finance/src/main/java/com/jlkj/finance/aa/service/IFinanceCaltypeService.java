package com.jlkj.finance.aa.service;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson2.JSONObject;
import com.jlkj.finance.aa.domain.FinanceCaltype;

/**
 * 核算项目-类别维护Service接口
 * 
 * @author 116514
 * @date 2023-04-06
 */
public interface IFinanceCaltypeService 
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
     * 批量删除核算项目-类别维护
     * 
     * @param ids 需要删除的核算项目-类别维护主键集合
     * @return 结果
     */
    public int deleteFinanceCaltypeByIds(String[] ids);

    /**
     * 删除核算项目-类别维护信息
     * 
     * @param id 核算项目-类别维护主键
     * @return 结果
     */
    public int deleteFinanceCaltypeById(String id);

    /**
     * 查询核算项目类别(下拉选单用)
     * @return
     */
    public List<Map<String,String>> selectCalTypeList();
    /**
     * 查询核算项目系统别(下拉选单用)
     * @param financeCaltype 核算项目-类别维护
     * @return
     */
    public List<Map<String,String>> selectCalTypeSystemList(FinanceCaltype financeCaltype);
    /**
     * 查询核算项目系统别(下拉选单用)
     * @param financeCaltype 核算项目-类别维护
     * @return
     */
    public  List<JSONObject> selectFinanceAaBaseListTab(FinanceCaltype financeCaltype);
    /**
     * 查询核算项目系统别(下拉选单用)
     * @param financeCaltype 核算项目-类别维护
     * @return
     */
    public    List<Map<String, String>> selectFinanceAaBaseListTable(FinanceCaltype financeCaltype);

    /**
     * 查询模糊核算项目系统别(下拉选单用)
     * @param financeCaltype 核算项目-类别维护
     * @return
     */
    public    List<Map<String, String>> selectFinanceAaBaseListLikeTable(JSONObject financeCaltype);

    /**
     * 查询模糊核算项目系统别(下拉选单用)备份
     * @param financeCaltype 核算项目-类别维护
     * @return
     */
    public    List<Map<String, String>> selectFinanceAaBaseListLikeTable1(JSONObject financeCaltype);
}
