package com.jlkj.human.ex.service;

import com.jlkj.human.ex.domain.ExamUserChoice;
import com.jlkj.human.ex.domain.dto.ExamUserCopy;
import com.jlkj.human.ex.domain.dto.ExamUserData;

import java.util.List;


/**
 * 选择用户Service接口
 * 
 * @author qnsdt
 * @date 2023-02-02
 */
public interface IExamUserChoiceService
{
    /**
     * 查询选择用户
     * 
     * @param choiceCode 选择用户ID
     * @return 选择用户
     */
    public ExamUserChoice selectExamUserChoiceById(String choiceCode);

    /**
     * 查询选择用户列表
     * 
     * @param sysUserChoice 选择用户
     * @return 选择用户集合
     */
    public List<ExamUserChoice> selectExamUserChoiceList(ExamUserChoice sysUserChoice);

    /**
     * 查询选择用户列表
     * 
     * @param sysUser 选择用户
     * @return 选择用户集合
     */    
	public List<ExamUserData> selectGroupUserList(ExamUserData sysUser);

    /**
     * 新增选择用户
     * 
     * @param sysUserChoice 选择用户
     * @return 结果
     */
    public int insertExamUserChoice(ExamUserChoice sysUserChoice);

    public int copyExamUser(ExamUserCopy sysUserCopy);
    
    public int copyTempUser(ExamUserCopy sysUserCopy);

    /**
     * 修改选择用户
     * 
     * @param sysUserChoice 选择用户
     * @return 结果
     */
    public int updateExamUserChoice(ExamUserChoice sysUserChoice);

    /**
     * 批量删除选择用户
     * 
     * @param choiceCodes 需要删除的选择用户ID
     * @return 结果
     */
    public int deleteExamUserChoiceByIds(String[] choiceCodes);

    /**
     * 删除选择用户信息
     * 
     * @param choiceCode 选择用户ID
     * @return 结果
     */
    public int deleteExamUserChoiceById(String choiceCode);
}
