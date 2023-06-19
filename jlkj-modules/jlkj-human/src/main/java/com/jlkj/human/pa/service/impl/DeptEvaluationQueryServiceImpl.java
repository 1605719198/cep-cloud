package com.jlkj.human.pa.service.impl;

import com.jlkj.human.pa.domain.DeptEvaluationQuery;
import com.jlkj.human.pa.mapper.DeptEvaluationQueryMapper;
import com.jlkj.human.pa.service.IDeptEvaluationQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 个人绩效主档Service业务层处理
 *
 * @author 267383
 * @date 2023-06-15
 */
@Service
public class DeptEvaluationQueryServiceImpl implements IDeptEvaluationQueryService
{
    @Autowired
    private DeptEvaluationQueryMapper deptEvaluationQueryMapper;

    /**
     * 查询个人绩效主档
     *
     * @param id 个人绩效主档主键
     * @return 个人绩效主档
     */
    @Override
    public DeptEvaluationQuery selectDeptEvaluationQueryById(String id)
    {
        return deptEvaluationQueryMapper.selectDeptEvaluationQueryById(id);
    }

    /**
     * 查询个人绩效主档列表
     *
     * @param deptEvaluationQuery 个人绩效主档
     * @return 个人绩效主档
     */
    @Override
    public List<DeptEvaluationQuery> selectDeptEvaluationQueryList(DeptEvaluationQuery deptEvaluationQuery)
    {
        return deptEvaluationQueryMapper.selectDeptEvaluationQueryList(deptEvaluationQuery);
    }

    /**
     * 新增个人绩效主档
     *
     * @param deptEvaluationQuery 个人绩效主档
     * @return 结果
     */
    @Override
    public int insertDeptEvaluationQuery(DeptEvaluationQuery deptEvaluationQuery)
    {
        return deptEvaluationQueryMapper.insertDeptEvaluationQuery(deptEvaluationQuery);
    }

    /**
     * 修改个人绩效主档
     *
     * @param deptEvaluationQuery 个人绩效主档
     * @return 结果
     */
    @Override
    public int updateDeptEvaluationQuery(DeptEvaluationQuery deptEvaluationQuery)
    {
        return deptEvaluationQueryMapper.updateDeptEvaluationQuery(deptEvaluationQuery);
    }

    /**
     * 批量删除个人绩效主档
     *
     * @param ids 需要删除的个人绩效主档主键
     * @return 结果
     */
    @Override
    public int deleteDeptEvaluationQueryByIds(String[] ids)
    {
        return deptEvaluationQueryMapper.deleteDeptEvaluationQueryByIds(ids);
    }

    /**
     * 删除个人绩效主档信息
     *
     * @param id 个人绩效主档主键
     * @return 结果
     */
    @Override
    public int deleteDeptEvaluationQueryById(String id)
    {
        return deptEvaluationQueryMapper.deleteDeptEvaluationQueryById(id);
    }
}
