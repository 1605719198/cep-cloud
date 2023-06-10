package com.jlkj.human.hs.service.impl;

import com.jlkj.common.core.exception.ServiceException;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.human.hs.domain.ProjectPay;
import com.jlkj.human.hs.mapper.ProjectPayMapper;
import com.jlkj.human.hs.service.IProjectPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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
     * 通过编码类型查询薪酬项目
     *
     * @param projectPay 薪酬项目
     * @return 薪酬项目
     */
    @Override
    public ProjectPay selectProjectPayByCode(ProjectPay projectPay){
        return projectPayMapper.selectProjectPayByCode(projectPay);
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
        //状态启动停用
        String status1 = "0",status2 = "1";
        for(ProjectPay projectPay :projectPayList){
            if(status1.equals(projectPay.getStatus())){
                projectPay.setCreatorId(SecurityUtils.getUserId().toString());
                projectPay.setCreatorNo(SecurityUtils.getUsername());
                projectPay.setCreator(SecurityUtils.getNickName());
                projectPay.setCreateDate(new Date());
                ProjectPay parent = projectPayMapper.selectProjectPayById(projectPay.getParentid());
                if("1".equals(parent.getIfEnd())){
                    parent.setIfEnd("0");
                    projectPayMapper.updateProjectPay(parent);
                }
                if(projectPay.getId()!=null){
                    projectPayMapper.updateProjectPay(projectPay);
                }else{
                    String parents = parent.getParents()+","+parent.getId();
                    projectPay.setParents(parents);
                    projectPayMapper.insertProjectPay(projectPay);
                }
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
        //状态启动停用
        String status1 = "0",status2 = "1";
        ProjectPay preData = projectPayMapper.selectProjectPayById(projectPay.getId());
        if(!projectPay.getStatus().equals(preData.getStatus())){
            // 子节点有正常数据不能关闭BasisOptionsDTO(id=78, dicNo=null, dicName=null, status=0, compId=null)
            if(status2.equals(projectPay.getStatus())){
                List<ProjectPay> childData = projectPayMapper.selectProjectPayByParentid(projectPay.getId());
                for (ProjectPay oldDatum : childData) {
                    if (status1.equals(oldDatum.getStatus())) {
                        throw new ServiceException("该资料下存在数据启用，不可关闭");
                    }
                }
            }
            // 当子节点启动时，父节点直接改成正常
            if(status1.equals(projectPay.getStatus())){
                ProjectPay parentData = projectPayMapper.selectProjectPayById(projectPay.getParentid());
                parentData.setStatus("0");
                projectPayMapper.updateProjectPay(parentData);
            }
        }
        projectPay.setCreatorId(SecurityUtils.getUserId().toString());
        projectPay.setCreatorNo(SecurityUtils.getUsername());
        projectPay.setCreator(SecurityUtils.getNickName());
        projectPay.setCreateDate(new Date());
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
