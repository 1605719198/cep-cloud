package com.jlkj.human.hs.service;

import com.jlkj.human.hs.domain.UnfixedSalary;
import com.jlkj.human.hs.dto.UnfixedSalaryDTO;

import java.util.List;

/**
 * 未定薪人员挑选作业Service接口
 * 
 * @author 266861
 * @date 2023-06-15
 */
public interface IUnfixedSalaryService 
{
    /**
     * 查询未定薪人员挑选作业
     * 
     * @param id 未定薪人员挑选作业主键
     * @return 未定薪人员挑选作业
     */
    public UnfixedSalary selectUnfixedSalaryById(String id);

    /**
     * 查询未定薪人员挑选作业列表
     * 
     * @param dto 未定薪人员挑选作业
     * @return 未定薪人员挑选作业集合
     */
    public List<UnfixedSalary> selectUnfixedSalaryList(UnfixedSalaryDTO dto);

    /**
     * 查询未定薪人员挑选作业列表(连表)
     *
     * @param dto 未定薪人员挑选作业
     * @return 未定薪集合
     */
    public List<UnfixedSalaryDTO> selectUncheckedList(UnfixedSalaryDTO dto);

    /**
     * 保存未定薪人员挑选作业
     *
     * @param unfixedSalaryDTO 未定薪人员挑选作业DTO
     * @return 结果
     */
    public int saveUnfixedSalary(UnfixedSalaryDTO unfixedSalaryDTO);

    /**
     * 新增未定薪人员挑选作业
     * 
     * @param unfixedSalary 未定薪人员挑选作业
     * @return 结果
     */
    public int insertUnfixedSalary(UnfixedSalary unfixedSalary);

    /**
     * 修改未定薪人员挑选作业
     * 
     * @param unfixedSalary 未定薪人员挑选作业
     * @return 结果
     */
    public int updateUnfixedSalary(UnfixedSalary unfixedSalary);

    /**
     * 批量删除未定薪人员挑选作业
     * 
     * @param ids 需要删除的未定薪人员挑选作业主键集合
     * @return 结果
     */
    public int deleteUnfixedSalaryByIds(String[] ids);

    /**
     * 删除未定薪人员挑选作业信息
     * 
     * @param id 未定薪人员挑选作业主键
     * @return 结果
     */
    public int deleteUnfixedSalaryById(String id);
}
