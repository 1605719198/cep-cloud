package com.jlkj.human.hs.mapper;

import com.jlkj.human.hs.domain.ProjectPay;

import java.util.List;

/**
 * 薪酬项目Mapper接口
 * 
 * @author 266861
 * @date 2023-05-11
 */
public interface ProjectPayMapper 
{
    /**
     * 查询薪酬项目
     * 
     * @param id 薪酬项目主键
     * @return 薪酬项目
     */
    public ProjectPay selectProjectPayById(Long id);

    /**
     * 查询薪酬项目列表
     * 
     * @param projectPay 薪酬项目
     * @return 薪酬项目集合
     */
    public List<ProjectPay> selectProjectPayList(ProjectPay projectPay);

    /**
     * 新增薪酬项目
     * 
     * @param projectPay 薪酬项目
     * @return 结果
     */
    public int insertProjectPay(ProjectPay projectPay);

    /**
     * 修改薪酬项目
     * 
     * @param projectPay 薪酬项目
     * @return 结果
     */
    public int updateProjectPay(ProjectPay projectPay);

    /**
     * 删除薪酬项目
     * 
     * @param id 薪酬项目主键
     * @return 结果
     */
    public int deleteProjectPayById(Long id);

    /**
     * 批量删除薪酬项目
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteProjectPayByIds(Long[] ids);
}
