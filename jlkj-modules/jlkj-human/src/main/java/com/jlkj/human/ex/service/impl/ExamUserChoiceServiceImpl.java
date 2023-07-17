package com.jlkj.human.ex.service.impl;

import com.jlkj.human.ex.domain.ExamUserChoice;
import com.jlkj.human.ex.domain.dto.ExamUserCopy;
import com.jlkj.human.ex.domain.dto.ExamUserData;
import com.jlkj.human.ex.mapper.ExamUserChoiceMapper;
import com.jlkj.human.ex.service.IExamUserChoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 选择用户Service业务层处理
 * 
 * @author qnsdt
 * @date 2023-02-02
 */
@Service
public class ExamUserChoiceServiceImpl implements IExamUserChoiceService
{
    @Autowired
    private ExamUserChoiceMapper sysUserChoiceMapper;

    /**
     * 查询选择用户
     * 
     * @param choiceCode 选择用户ID
     * @return 选择用户
     */
    @Override
    public ExamUserChoice selectExamUserChoiceById(String choiceCode)
    {
        return sysUserChoiceMapper.selectExamUserChoiceById(choiceCode);
    }

    /**
     * 查询选择用户列表
     * 
     * @param sysUserChoice 选择用户
     * @return 选择用户
     */
    @Override
    public List<ExamUserChoice> selectExamUserChoiceList(ExamUserChoice sysUserChoice)
    {
        return sysUserChoiceMapper.selectExamUserChoiceList(sysUserChoice);
    }

    /**
     * 新增选择用户
     * 
     * @param sysUserChoice 选择用户
     * @return 结果
     */
    @Override
    public int insertExamUserChoice(ExamUserChoice sysUserChoice)
    {
        return sysUserChoiceMapper.insertExamUserChoice(sysUserChoice);
    }

    /**
     * 修改选择用户
     * 
     * @param sysUserChoice 选择用户
     * @return 结果
     */
    @Override
    public int updateExamUserChoice(ExamUserChoice sysUserChoice)
    {
        return sysUserChoiceMapper.updateExamUserChoice(sysUserChoice);
    }

    /**
     * 批量删除选择用户
     * 
     * @param choiceCodes 需要删除的选择用户ID
     * @return 结果
     */
    @Override
    public int deleteExamUserChoiceByIds(String[] choiceCodes)
    {
        return sysUserChoiceMapper.deleteExamUserChoiceByIds(choiceCodes);
    }

    /**
     * 删除选择用户信息
     * 
     * @param choiceCode 选择用户ID
     * @return 结果
     */
    @Override
    public int deleteExamUserChoiceById(String choiceCode)
    {
        return sysUserChoiceMapper.deleteExamUserChoiceById(choiceCode);
    }

	@Override
	public int copyExamUser(ExamUserCopy sysUserCopy) {
		// TODO Auto-generated method stub
		return sysUserChoiceMapper.copySysUser(sysUserCopy);
	}

	@Override
	public int copyTempUser(ExamUserCopy sysUserCopy) {
		// TODO Auto-generated method stub
		return sysUserChoiceMapper.copyTempUser(sysUserCopy);
	}

	@Override
	public List<ExamUserData> selectGroupUserList(ExamUserData sysUser) {
		// TODO Auto-generated method stub
		return sysUserChoiceMapper.selectGroupUserList(sysUser);
	}
}
