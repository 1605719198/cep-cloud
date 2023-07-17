package com.jlkj.human.ex.service.impl;

import com.jlkj.human.ex.domain.ExamGroupPerson;
import com.jlkj.human.ex.mapper.ExamGroupPersonMapper;
import com.jlkj.human.ex.service.IExamGroupPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 分组用户Service业务层处理
 * 
 * @author qnsdt
 * @date 2023-02-06
 */
@Service
public class ExamGroupPersonServiceImpl implements IExamGroupPersonService
{
    @Autowired
    private ExamGroupPersonMapper sysGroupPersonMapper;

    /**
     * 查询分组用户
     * 
     * @param groupCode 分组用户ID
     * @return 分组用户
     */
    @Override
    public ExamGroupPerson selectSysGroupPersonById(String groupCode)
    {
        return sysGroupPersonMapper.selecExamGroupPersonById(groupCode);
    }

    /**
     * 查询分组用户列表
     * 
     * @param sysGroupPerson 分组用户
     * @return 分组用户
     */
    @Override
    public List<ExamGroupPerson> selectSysGroupPersonList(ExamGroupPerson sysGroupPerson)
    {
        return sysGroupPersonMapper.selectExamGroupPersonList(sysGroupPerson);
    }

    /**
     * 新增分组用户
     * 
     * @param sysGroupPersonList 分组用户
     * @return 结果
     */
    @Override
    public int insertSysGroupPerson(List<ExamGroupPerson> sysGroupPersonList)
    {    	
    	sysGroupPersonMapper.deleteExamGroupPersonById(sysGroupPersonList.get(0).getGroupCode());
    	int count = 0;
    	
    	for (ExamGroupPerson sysGroupPerson: sysGroupPersonList) {
    		count += sysGroupPersonMapper.insertExamGroupPerson(sysGroupPerson);
    	}
    	
        return count;
    }

    /**
     * 修改分组用户
     * 
     * @param sysGroupPerson 分组用户
     * @return 结果
     */
    @Override
    public int updateSysGroupPerson(ExamGroupPerson sysGroupPerson)
    {
        return sysGroupPersonMapper.updateExamGroupPerson(sysGroupPerson);
    }

    /**
     * 批量删除分组用户
     * 
     * @param groupCodes 需要删除的分组用户ID
     * @return 结果
     */
    @Override
    public int deleteSysGroupPersonByIds(String[] groupCodes)
    {
        return sysGroupPersonMapper.deleteExamGroupPersonByIds(groupCodes);
    }

    /**
     * 删除分组用户信息
     * 
     * @param groupCode 分组用户ID
     * @return 结果
     */
    @Override
    public int deleteSysGroupPersonById(String groupCode)
    {
        return sysGroupPersonMapper.deleteExamGroupPersonById(groupCode);
    }
}
