package com.jlkj.finance.aa.service;

import java.util.List;

import com.alibaba.fastjson2.JSONObject;
import com.jlkj.finance.aa.domain.FinanceAaBase;

/**
 * 系统选单-基本设定Service接口
 * 
 * @author jlkj
 * @date 2023-04-19
 */
public interface IFinanceAaBaseService 
{
    /**
     * 查询系统选单-基本设定
     * 
     * @param id 系统选单-基本设定主键
     * @return 系统选单-基本设定
     */
    public FinanceAaBase selectFinanceAaBaseById(String id);

    /**
     * 查询系统选单-基本设定列表
     * 
     * @param financeAaBase 系统选单-基本设定
     * @return 系统选单-基本设定集合
     */
    public List<FinanceAaBase> selectFinanceAaBaseList(FinanceAaBase financeAaBase);
    /**
     * 查询系统选单-基本设定列表
     *
     * @param financeAaBase 系统选单-基本设定
     * @return 系统选单-基本设定集合
     */
    public   List<JSONObject> selectFinanceAaBaseListTab(FinanceAaBase financeAaBase);

    /**
     * 新增系统选单-基本设定
     * 
     * @param financeAaBase 系统选单-基本设定
     * @return 结果
     */
    public int insertFinanceAaBase(FinanceAaBase financeAaBase);

    /**
     * 修改系统选单-基本设定
     * 
     * @param financeAaBase 系统选单-基本设定
     * @return 结果
     */
    public int updateFinanceAaBase(FinanceAaBase financeAaBase);

    /**
     * 批量删除系统选单-基本设定
     * 
     * @param leafId 需要删除的系统选单-基本设定主键集合
     * @return 结果
     */
    public int deleteFinanceAaBaseByIds(String[] leafId);

    /**
     * 删除系统选单-基本设定信息
     * 
     * @param id 系统选单-基本设定主键
     * @return 结果
     */
    public int deleteFinanceAaBaseById(String id);
}
