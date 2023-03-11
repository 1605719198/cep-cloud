package com.jlkj.human.hp.service.impl;

import java.util.List;
import com.jlkj.common.core.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jlkj.human.hp.mapper.SysDeptVersionMapper;
import com.jlkj.human.hp.domain.SysDeptVersion;
import com.jlkj.human.hp.service.ISysDeptVersionService;

/**
 * 部门资料变更版本Service业务层处理
 *
 * @author 266861
 * @date 2023-03-09
 */
@Service
public class SysDeptVersionServiceImpl implements ISysDeptVersionService
{
    @Autowired
    private SysDeptVersionMapper sysDeptVersionMapper;

    /**
     * 查询部门资料变更版本
     *
     * @param id 部门资料变更版本主键
     * @return 部门资料变更版本
     */
    @Override
    public SysDeptVersion selectSysDeptVersionById(Long id)
    {
        return sysDeptVersionMapper.selectSysDeptVersionById(id);
    }

    /**
     * 查询部门资料变更版本列表
     *
     * @param sysDeptVersion 部门资料变更版本
     * @return 部门资料变更版本
     */
    @Override
    public List<SysDeptVersion> selectSysDeptVersionList(SysDeptVersion sysDeptVersion)
    {
        return sysDeptVersionMapper.selectSysDeptVersionList(sysDeptVersion);
    }

    /**
     * 新增部门资料变更版本
     *
     * @param sysDeptVersion 部门资料变更版本
     * @return 结果
     */
    @Override
    public int insertSysDeptVersion(SysDeptVersion sysDeptVersion)
    {
        sysDeptVersion.setCreateTime(DateUtils.getNowDate());
        return sysDeptVersionMapper.insertSysDeptVersion(sysDeptVersion);
    }

    /**
     * 修改部门资料变更版本
     *
     * @param sysDeptVersion 部门资料变更版本
     * @return 结果
     */
    @Override
    public int updateSysDeptVersion(SysDeptVersion sysDeptVersion)
    {
        sysDeptVersion.setUpdateTime(DateUtils.getNowDate());
        return sysDeptVersionMapper.updateSysDeptVersion(sysDeptVersion);
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
        return sysDeptVersionMapper.deleteSysDeptVersionByIds(ids);
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
        return sysDeptVersionMapper.deleteSysDeptVersionById(id);
    }
}
