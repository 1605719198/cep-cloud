package com.jlkj.human.ex.mapper;


import com.jlkj.human.ex.domain.ExamTaskManager;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 创建考试Mapper接口
 * 
 * @author qnsdt
 * @date 2023-01-06
 */
@Repository
public interface ExamTaskManagerMapper 
{
    /**
     * 查询创建考试
     * 
     * @param examCode 创建考试ID
     * @return 创建考试
     */
    public ExamTaskManager selectExamTaskManagerById(String examCode);


    /**
     * 查询创建考试列表
     * 
     * @param examTaskManager 创建考试
     * @return 创建考试集合
     */
    public List<ExamTaskManager> selectExamTaskManagerList(ExamTaskManager examTaskManager);

    /**
     * 新增创建考试
     * 
     * @param examTaskManager 创建考试
     * @return 结果
     */
    public int insertExamTaskManager(ExamTaskManager examTaskManager);

    /**
     * 修改创建考试
     * 
     * @param examTaskManager 创建考试
     * @return 结果
     */
    public int updateExamTaskManager(ExamTaskManager examTaskManager);

    /**
     * 删除创建考试
     * 
     * @param examCode 创建考试ID
     * @return 结果
     */
    public int deleteExamTaskManagerById(String examCode);

    /**
     * 批量删除创建考试
     * 
     * @param examCodes 需要删除的数据ID
     * @return 结果
     */
    public int deleteExamTaskManagerByIds(String[] examCodes);
}
