package com.jlkj.human.ex.mapper;

import com.jlkj.human.ex.domain.dto.ExamUserData;

import java.util.List;


public interface ExamUserDataMapper {
	public List<ExamUserData> selectUserList(ExamUserData sysUser);
	
	public List<ExamUserData> selectChoiceUserList(ExamUserData sysUser);

}
