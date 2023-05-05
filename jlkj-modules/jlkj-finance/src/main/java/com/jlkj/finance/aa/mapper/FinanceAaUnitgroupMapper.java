package com.jlkj.finance.aa.mapper;

import com.jlkj.finance.aa.domain.FinanceAaUnitgroup;

import java.util.List;

/**
 * 计量单位分组Mapper接口
 * 
 * @author SunXuTong
 * @date 2023-04-17
 */
public interface FinanceAaUnitgroupMapper 
{
    /**
     * 查询计量单位分组
     * 
     * @param id 计量单位分组主键
     * @return 计量单位分组
     */
    public FinanceAaUnitgroup selectFinanceAaUnitgroupById(String id);

    /**
     * 查询计量单位分组列表
     * 
     * @param financeAaUnitgroup 计量单位分组
     * @return 计量单位分组集合
     */
    public List<FinanceAaUnitgroup> selectFinanceAaUnitgroupList(FinanceAaUnitgroup financeAaUnitgroup);

    /**
     * 新增计量单位分组
     * 
     * @param financeAaUnitgroup 计量单位分组
     * @return 结果
     */
    public int insertFinanceAaUnitgroup(FinanceAaUnitgroup financeAaUnitgroup);

    /**
     * 修改计量单位分组
     * 
     * @param financeAaUnitgroup 计量单位分组
     * @return 结果
     */
    public int updateFinanceAaUnitgroup(FinanceAaUnitgroup financeAaUnitgroup);

    /**
     * 删除计量单位分组
     * 
     * @param id 计量单位分组主键
     * @return 结果
     */
    public int deleteFinanceAaUnitgroupById(String id);

    /**
     * 批量删除计量单位分组
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFinanceAaUnitgroupByIds(String[] ids);
}
