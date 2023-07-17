package com.jlkj.human.ex.service.impl;

import com.jlkj.human.ex.domain.dto.ExamUserData;
import com.jlkj.human.ex.mapper.ExamUserDataMapper;
import com.jlkj.human.ex.service.IExamUserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ExamUserDataServiceImpl implements IExamUserDataService {
	@Autowired
	ExamUserDataMapper sysUserDataMapper;

	@Override
	public List<ExamUserData> selectUserList(ExamUserData sysUser) {
		// TODO Auto-generated method stub
		return sysUserDataMapper.selectUserList(sysUser);
	}

	@Override
	public List<ExamUserData> selectChoiceUserList(ExamUserData sysUser) {
		// TODO Auto-generated method stub
		return sysUserDataMapper.selectChoiceUserList(sysUser);
	}

}
