package com.jlkj.finance.aa.service;

import java.util.List;
import com.jlkj.finance.aa.domain.FinanceCalcode;

/**
 * 核算项目-内容维护Service接口
 * 
 * @author 116514
 * @date 2023-04-10
 */
public interface IFinanceCalcodeService 
{
    /**
     * 查询核算项目-内容维护
     * 
     * @param id 核算项目-内容维护主键
     * @return 核算项目-内容维护
     */
    public FinanceCalcode selectFinanceCalcodeById(String id);

    /**
     * 查询核算项目-内容维护列表
     * 
     * @param financeCalcode 核算项目-内容维护
     * @return 核算项目-内容维护集合
     */
    public List<FinanceCalcode> selectFinanceCalcodeList(FinanceCalcode financeCalcode);

    /**
     * 新增核算项目-内容维护
     * 
     * @param financeCalcode 核算项目-内容维护
     * @return 结果
     */
    public int insertFinanceCalcode(FinanceCalcode financeCalcode);

    /**
     * 修改核算项目-内容维护
     * 
     * @param financeCalcode 核算项目-内容维护
     * @return 结果
     */
    public int updateFinanceCalcode(FinanceCalcode financeCalcode);

    /**
     * 批量删除核算项目-内容维护
     * 
     * @param ids 需要删除的核算项目-内容维护主键集合
     * @return 结果
     */
    public int deleteFinanceCalcodeByIds(String[] ids);

    /**
     * 删除核算项目-内容维护信息
     * 
     * @param id 核算项目-内容维护主键
     * @return 结果
     */
    public int deleteFinanceCalcodeById(String id);
}
