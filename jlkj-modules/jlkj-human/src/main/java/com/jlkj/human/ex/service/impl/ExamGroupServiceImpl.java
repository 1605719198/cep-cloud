package com.jlkj.human.ex.service.impl;

import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.common.core.utils.uuid.IdUtils;
import com.jlkj.common.security.service.TokenService;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.human.ex.domain.ExamGroup;
import com.jlkj.human.ex.mapper.ExamGroupMapper;
import com.jlkj.human.ex.service.IExamGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 人员分组Service业务层处理
 *
 * @author qnsdt
 * @date 2023-01-28
 */
@Service
public class ExamGroupServiceImpl implements IExamGroupService {
    @Autowired
    private ExamGroupMapper sysGroupMapper;
    @Autowired
    private TokenService tokenService;

    /**
     * 查询人员分组
     *
     * @param groupId 人员分组ID
     * @return 人员分组
     */
    @Override
    public ExamGroup selectSysGroupById(Long groupId) {
        return sysGroupMapper.selectExamGroupById(groupId);
    }

    /**
     * 查询人员分组列表
     * 
     * @param sysGroup 人员分组
     * @return 人员分组
     */
    @Override
    public List<ExamGroup> selectSysGroupList(ExamGroup sysGroup)
    {
        return sysGroupMapper.selectExamGroupList(sysGroup);
    }

    /**
     * 新增人员分组
     * 
     * @param sysGroup 人员分组
     * @return 结果
     */
    @Override
    public int insertSysGroup(ExamGroup sysGroup)
    {
        sysGroup.setGroupCode(IdUtils.simpleUUID());
        sysGroup.setCreateBy(SecurityUtils.getLoginUser().getUserName());
        sysGroup.setCreateTime(DateUtils.getNowDate());
        return sysGroupMapper.insertExamGroup(sysGroup);
    }

    /**
     * 修改人员分组
     * 
     * @param sysGroup 人员分组
     * @return 结果
     */
    @Override
    public int updateSysGroup(ExamGroup sysGroup)
    {
        return sysGroupMapper.updateExamGroup(sysGroup);
    }

    /**
     * 批量删除人员分组
     * 
     * @param groupIds 需要删除的人员分组ID
     * @return 结果
     */
    @Override
    public int deleteSysGroupByIds(Long[] groupIds)
    {
        return sysGroupMapper.deleteExamGroupByIds(groupIds);
    }

    /**
     * 删除人员分组信息
     * 
     * @param groupId 人员分组ID
     * @return 结果
     */
    @Override
    public int deleteSysGroupById(Long groupId)
    {
        return sysGroupMapper.deleteExamGroupById(groupId);
    }

	@Override
	public List<ExamGroup> selectChoiceSysGroupList(String examCode) {
		// TODO Auto-generated method stub
		return sysGroupMapper.selectChoiceExamGroupList(examCode);
	}
}
