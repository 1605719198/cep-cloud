package com.jlkj.human.hs.mapper;

import com.jlkj.human.hs.domain.UnfixedSalary;
import com.jlkj.human.hs.dto.UnfixedSalaryDTO;

import java.util.List;

/**
 * 未定薪人员挑选作业Mapper接口
 * 
 * @author 266861
 * @date 2023-06-15
 */
public interface UnfixedSalaryMapper 
{
    /**
     * 查询未定薪人员挑选作业
     * 
     * @param id 未定薪人员挑选作业主键
     * @return 未定薪人员挑选作业
     */
    public UnfixedSalary selectUnfixedSalaryById(String id);

    /**
     * 查询未定薪人员挑选作业列表(连表)
     * 
     * @param unfixedSalaryDTO 未定薪人员挑选作业
     * @return 未定薪集合
     */
    public List<UnfixedSalaryDTO> selectUncheckedList(UnfixedSalaryDTO unfixedSalaryDTO);

    /**
     * 查询未定薪人员挑选作业列表
     *
     * @param dto 未定薪人员挑选作业
     * @return 未定薪人员挑选作业集合
     */
    public List<UnfixedSalary> selectUnfixedSalaryList(UnfixedSalaryDTO dto);

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
     * 删除未定薪人员挑选作业
     * 
     * @param id 未定薪人员挑选作业主键
     * @return 结果
     */
    public int deleteUnfixedSalaryById(String id);

    /**
     * 删除查询条件旧数据
     *
     * @param dto 删除参数
     * @return 结果
     */
    public int deletePreDataByParam(UnfixedSalaryDTO dto);

    /**
     * 批量删除未定薪人员挑选作业
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteUnfixedSalaryByIds(String[] ids);
}
