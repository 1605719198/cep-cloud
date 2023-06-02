package com.jlkj.human.hs.mapper;

import com.jlkj.human.hs.domain.SalaryExplain;

import java.util.List;

/**
 * 各公司薪资发放说明Mapper接口
 * 
 * @author 266861
 * @date 2023-05-25
 */
public interface SalaryExplainMapper 
{
    /**
     * 查询各公司薪资发放说明
     * 
     * @param uuid 各公司薪资发放说明主键
     * @return 各公司薪资发放说明
     */
    public SalaryExplain selectSalaryExplainByUuid(String uuid);

    /**
     * 查询各公司薪资发放说明列表
     * 
     * @param salaryExplain 各公司薪资发放说明
     * @return 各公司薪资发放说明集合
     */
    public List<SalaryExplain> selectSalaryExplainList(SalaryExplain salaryExplain);

    /**
     * 新增各公司薪资发放说明
     * 
     * @param salaryExplain 各公司薪资发放说明
     * @return 结果
     */
    public int insertSalaryExplain(SalaryExplain salaryExplain);

    /**
     * 修改各公司薪资发放说明
     * 
     * @param salaryExplain 各公司薪资发放说明
     * @return 结果
     */
    public int updateSalaryExplain(SalaryExplain salaryExplain);

    /**
     * 删除各公司薪资发放说明
     * 
     * @param uuid 各公司薪资发放说明主键
     * @return 结果
     */
    public int deleteSalaryExplainByUuid(String uuid);

    /**
     * 批量删除各公司薪资发放说明
     * 
     * @param uuids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSalaryExplainByUuids(String[] uuids);
}
