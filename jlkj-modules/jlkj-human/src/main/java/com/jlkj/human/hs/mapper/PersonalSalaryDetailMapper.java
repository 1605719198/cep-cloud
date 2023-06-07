package com.jlkj.human.hs.mapper;

import com.jlkj.human.hs.domain.PersonalSalaryDetail;

import java.util.List;

/**
 * 个人薪资核定明细Mapper接口
 * 
 * @author 266861
 * @date 2023-06-01
 */
public interface PersonalSalaryDetailMapper 
{
    /**
     * 查询个人薪资核定明细
     * 
     * @param id 个人薪资核定明细主键
     * @return 个人薪资核定明细
     */
    public PersonalSalaryDetail selectPersonalSalaryDetailById(String id);

    /**
     * 查询个人薪资核定明细列表
     * 
     * @param personalSalaryDetail 个人薪资核定明细
     * @return 个人薪资核定明细集合
     */
    public List<PersonalSalaryDetail> selectPersonalSalaryDetailList(PersonalSalaryDetail personalSalaryDetail);

    /**
     * 新增个人薪资核定明细
     * 
     * @param personalSalaryDetail 个人薪资核定明细
     * @return 结果
     */
    public int insertPersonalSalaryDetail(PersonalSalaryDetail personalSalaryDetail);

    /**
     * 修改个人薪资核定明细
     * 
     * @param personalSalaryDetail 个人薪资核定明细
     * @return 结果
     */
    public int updatePersonalSalaryDetail(PersonalSalaryDetail personalSalaryDetail);

    /**
     * 删除个人薪资核定明细
     * 
     * @param id 个人薪资核定明细主键
     * @return 结果
     */
    public int deletePersonalSalaryDetailById(String id);

    /**
     * 批量删除个人薪资核定明细
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePersonalSalaryDetailByIds(String[] ids);
}
