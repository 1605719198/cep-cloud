package com.jlkj.human.ex.service;


import com.alibaba.fastjson.JSONObject;
import com.jlkj.human.ex.domain.ExamTaskManager;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 创建考试Service接口
 * 
 * @author qnsdt
 * @date 2023-01-06
 */
public interface IExamTaskManagerService 
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
     * 新增题库图片
     *
     * @param examCodes 试题代码
     * @param file      试题图片
     * @return JSONObject 结果（图片URL）
     * @Author: 111191
     * @Date: 2023年7月14日, 0014 上午 11:07:07
     */
    JSONObject insertExamBankPicture(String examCodes, MultipartFile file);

    /**
     * 修改创建考试
     *
     * @param examTaskManager 创建考试
     * @return 结果
     */
    public int updateExamTaskManager(ExamTaskManager examTaskManager);

    /**
     * 批量删除创建考试
     * 
     * @param examCodes 需要删除的创建考试ID
     * @return 结果
     */
    public int deleteExamTaskManagerByIds(String[] examCodes);

    /**
     * 删除创建考试信息
     * 
     * @param examCode 创建考试ID
     * @return 结果
     */
    public int deleteExamTaskManagerById(String examCode);
}
