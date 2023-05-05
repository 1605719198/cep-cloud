package com.jlkj.finance.aa.mapper;

import com.jlkj.finance.aa.domain.FinanceAaUnitChg;

import java.util.List;

/**
 * 计量单位转换Mapper接口
 * 
 * @author SunXuTong
 * @date 2023-05-04
 */
public interface FinanceAaUnitChgMapper 
{
    /**
     * 查询计量单位转换
     * 
     * @param id 计量单位转换主键
     * @return 计量单位转换
     */
    public FinanceAaUnitChg selectFinanceAaUnitChgById(String id);

    /**
     * 查询计量单位转换列表
     * 
     * @param financeAaUnitChg 计量单位转换
     * @return 计量单位转换集合
     */
    public List<FinanceAaUnitChg> selectFinanceAaUnitChgList(FinanceAaUnitChg financeAaUnitChg);

    /**
     * 新增计量单位转换
     * 
     * @param financeAaUnitChg 计量单位转换
     * @return 结果
     */
    public int insertFinanceAaUnitChg(FinanceAaUnitChg financeAaUnitChg);

    /**
     * 修改计量单位转换
     * 
     * @param financeAaUnitChg 计量单位转换
     * @return 结果
     */
    public int updateFinanceAaUnitChg(FinanceAaUnitChg financeAaUnitChg);

    /**
     * 删除计量单位转换
     * 
     * @param id 计量单位转换主键
     * @return 结果
     */
    public int deleteFinanceAaUnitChgById(String id);

    /**
     * 批量删除计量单位转换
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFinanceAaUnitChgByIds(String[] ids);
}
