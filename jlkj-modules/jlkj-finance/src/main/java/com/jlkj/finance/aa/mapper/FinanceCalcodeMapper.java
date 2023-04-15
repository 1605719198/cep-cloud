package com.jlkj.finance.aa.mapper;

import java.util.List;
import com.jlkj.finance.aa.domain.FinanceCalcode;

/**
 * 核算项目-内容维护Mapper接口
 * 
 * @author 116514
 * @date 2023-04-10
 */
public interface FinanceCalcodeMapper 
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
     * 删除核算项目-内容维护
     * 
     * @param id 核算项目-内容维护主键
     * @return 结果
     */
    public int deleteFinanceCalcodeById(String id);

    /**
     * 批量删除核算项目-内容维护
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFinanceCalcodeByIds(String[] ids);
}
