package com.jlkj.human.pa.service;

import com.jlkj.human.pa.domain.DeptEvaluationQuery;

import java.util.List;

/**
 * 个人绩效主档Service接口
 * 
 * @author 267383
 * @date 2023-06-15
 */
public interface IDeptEvaluationQueryService 
{
    /**
     * 查询个人绩效主档
     * 
     * @param id 个人绩效主档主键
     * @return 个人绩效主档
     */
    public DeptEvaluationQuery selectDeptEvaluationQueryById(String id);

    /**
     * 查询个人绩效主档列表
     * 
     * @param deptEvaluationQuery 个人绩效主档
     * @return 个人绩效主档集合
     */
    public List<DeptEvaluationQuery> selectDeptEvaluationQueryList(DeptEvaluationQuery deptEvaluationQuery);

    /**
     * 新增个人绩效主档
     * 
     * @param deptEvaluationQuery 个人绩效主档
     * @return 结果
     */
    public int insertDeptEvaluationQuery(DeptEvaluationQuery deptEvaluationQuery);

    /**
     * 修改个人绩效主档
     * 
     * @param deptEvaluationQuery 个人绩效主档
     * @return 结果
     */
    public int updateDeptEvaluationQuery(DeptEvaluationQuery deptEvaluationQuery);

    /**
     * 批量删除个人绩效主档
     * 
     * @param ids 需要删除的个人绩效主档主键集合
     * @return 结果
     */
    public int deleteDeptEvaluationQueryByIds(String[] ids);

    /**
     * 删除个人绩效主档信息
     * 
     * @param id 个人绩效主档主键
     * @return 结果
     */
    public int deleteDeptEvaluationQueryById(String id);
}
