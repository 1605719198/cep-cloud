package com.jlkj.human.hp.service.impl;

import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.human.hp.domain.HumanDeptVersion;
import com.jlkj.human.hp.mapper.HumanDeptVersionMapper;
import com.jlkj.human.hp.service.IHumanDeptVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 部门资料变更版本Service业务层处理
 *
 * @author 266861
 * @date 2023-03-09
 */
@Service
public class HumanDeptVersionServiceImpl implements IHumanDeptVersionService
{
    @Autowired
    private HumanDeptVersionMapper humanDeptVersionMapper;

    /**
     * 查询部门资料变更版本
     *
     * @param id 部门资料变更版本主键
     * @return 部门资料变更版本
     */
    @Override
    public HumanDeptVersion selectSysDeptVersionById(Long id)
    {
        return humanDeptVersionMapper.selectSysDeptVersionById(id);
    }

    /**
     * 查询部门资料变更版本列表
     *
     * @param humanDeptVersion 部门资料变更版本
     * @return 部门资料变更版本
     */
    @Override
    public List<HumanDeptVersion> selectSysDeptVersionList(HumanDeptVersion humanDeptVersion)
    {
        return humanDeptVersionMapper.selectSysDeptVersionList(humanDeptVersion);
    }

    /**
     * 新增部门资料变更版本
     *
     * @param humanDeptVersion 部门资料变更版本
     * @return 结果
     */
    @Override
    public int insertSysDeptVersion(HumanDeptVersion humanDeptVersion)
    {
        humanDeptVersion.setCreateTime(DateUtils.getNowDate());
        return humanDeptVersionMapper.insertSysDeptVersion(humanDeptVersion);
    }

    /**
     * 修改部门资料变更版本
     *
     * @param humanDeptVersion 部门资料变更版本
     * @return 结果
     */
    @Override
    public int updateSysDeptVersion(HumanDeptVersion humanDeptVersion)
    {
        humanDeptVersion.setUpdateTime(DateUtils.getNowDate());
        return humanDeptVersionMapper.updateSysDeptVersion(humanDeptVersion);
    }

    /**
     * 批量删除部门资料变更版本
     *
     * @param ids 需要删除的部门资料变更版本主键
     * @return 结果
     */
    @Override
    public int deleteSysDeptVersionByIds(Long[] ids)
    {
        return humanDeptVersionMapper.deleteSysDeptVersionByIds(ids);
    }

    /**
     * 删除部门资料变更版本信息
     *
     * @param id 部门资料变更版本主键
     * @return 结果
     */
    @Override
    public int deleteSysDeptVersionById(Long id)
    {
        return humanDeptVersionMapper.deleteSysDeptVersionById(id);
    }
}
