package com.jlkj.human.ex.service;

import com.jlkj.human.ex.domain.dto.ExamUserData;

import java.util.List;


public interface IExamUserDataService {
	public List<ExamUserData> selectUserList(ExamUserData sysUser);
	
	public List<ExamUserData> selectChoiceUserList(ExamUserData sysUser);


}
