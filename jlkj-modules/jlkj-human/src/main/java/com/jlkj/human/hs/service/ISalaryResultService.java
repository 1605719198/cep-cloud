package com.jlkj.human.hs.service;

import com.jlkj.human.hs.domain.SalaryResult;
import com.jlkj.human.hs.dto.SalaryResultDTO;

import java.util.List;

/**
 * 薪资计算结果Service接口
 * 
 * @author 266861
 * @date 2023-06-20
 */
public interface ISalaryResultService 
{
    /**
     * 查询薪资计算结果
     * 
     * @param id 薪资计算结果主键
     * @return 薪果
     */
    public SalaryResultDTO selectSalaryResultById(String id);

    /**
     * 查询薪资计算结果列表
     * 
     * @param salaryResultDTO 薪资计算结果
     * @return 薪资计算结果集合
     */
    public List<SalaryResultDTO> selectSalaryResultList(SalaryResultDTO salaryResultDTO);

    /**
     * 新增薪资计算结果
     * 
     * @param salaryResult 薪资计算结果
     * @return 结果
     */
    public int insertSalaryResult(SalaryResult salaryResult);

    /**
     * 修改薪资计算结果
     * 
     * @param salaryResult 薪资计算结果
     * @return 结果
     */
    public int updateSalaryResult(SalaryResult salaryResult);

    /**
     * 批量删除薪资计算结果
     * 
     * @param ids 需要删除的薪资计算结果主键集合
     * @return 结果
     */
    public int deleteSalaryResultByIds(String[] ids);

    /**
     * 删除薪资计算结果信息
     * 
     * @param id 薪资计算结果主键
     * @return 结果
     */
    public int deleteSalaryResultById(String id);
}
