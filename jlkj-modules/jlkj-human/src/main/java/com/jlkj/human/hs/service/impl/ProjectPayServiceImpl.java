package com.jlkj.human.hs.service.impl;

import java.security.Security;
import java.util.Date;
import java.util.List;

import com.jlkj.common.security.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jlkj.human.hs.mapper.ProjectPayMapper;
import com.jlkj.human.hs.domain.ProjectPay;
import com.jlkj.human.hs.service.IProjectPayService;

/**
 * 薪酬项目Service业务层处理
 *
 * @author 266861
 * @date 2023-05-11
 */
@Service
public class ProjectPayServiceImpl implements IProjectPayService
{
    @Autowired
    private ProjectPayMapper projectPayMapper;

    /**
     * 查询薪酬项目
     *
     * @param id 薪酬项目主键
     * @return 薪酬项目
     */
    @Override
    public ProjectPay selectProjectPayById(Long id)
    {
        return projectPayMapper.selectProjectPayById(id);
    }

    /**
     * 查询薪酬项目列表
     *
     * @param projectPay 薪酬项目
     * @return 薪酬项目
     */
    @Override
    public List<ProjectPay> selectProjectPayList(ProjectPay projectPay)
    {
        return projectPayMapper.selectProjectPayList(projectPay);
    }

    /**
     * 新增薪酬项目
     *
     * @param projectPayList 薪酬项目列表
     * @return 结果
     */
    @Override
    public int insertProjectPay(List<ProjectPay> projectPayList)
    {
        for(ProjectPay projectPay :projectPayList){
            if(projectPay.getId()!=null){
                projectPay.setCreatorId(SecurityUtils.getUserId().toString());
                projectPay.setCreatorNo(SecurityUtils.getUsername());
                projectPay.setCreator(SecurityUtils.getNickName());
                projectPay.setCreateDate(new Date());
                projectPayMapper.updateProjectPay(projectPay);

            }else{
                projectPayMapper.insertProjectPay(projectPay);
            }

        }
        return 1;
    }

    /**
     * 修改薪酬项目
     *
     * @param projectPay 薪酬项目
     * @return 结果
     */
    @Override
    public int updateProjectPay(ProjectPay projectPay)
    {
        return projectPayMapper.updateProjectPay(projectPay);
    }

    /**
     * 批量删除薪酬项目
     *
     * @param ids 需要删除的薪酬项目主键
     * @return 结果
     */
    @Override
    public int deleteProjectPayByIds(Long[] ids)
    {
        return projectPayMapper.deleteProjectPayByIds(ids);
    }

    /**
     * 删除薪酬项目信息
     *
     * @param id 薪酬项目主键
     * @return 结果
     */
    @Override
    public int deleteProjectPayById(Long id)
    {
        return projectPayMapper.deleteProjectPayById(id);
    }
}
