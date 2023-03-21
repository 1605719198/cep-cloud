package com.jlkj.human.hp.service.impl;

import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.human.hp.domain.SysPostVersion;
import com.jlkj.human.hp.mapper.SysPostVersionMapper;
import com.jlkj.human.hp.service.ISysPostVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 岗位信息版本数据维护Service业务层处理
 *
 * @author 266861
 * @date 2023-03-15
 */
@Service
public class SysPostVersionServiceImpl implements ISysPostVersionService
{
    @Autowired
    private SysPostVersionMapper SysPostVersionMapper;

    /**
     * 查询岗位信息版本数据维护
     *
     * @param Id 岗位信息版本数据维护主键
     * @return 岗位信息版本数据维护
     */
    @Override
    public SysPostVersion selectSysPostVersionById(Long Id)
    {
        return SysPostVersionMapper.selectSysPostVersionById(Id);
    }

    /**
     * 查询岗位信息版本数据维护列表
     *
     * @param SysPostVersion 岗位信息版本数据维护
     * @return 岗位信息版本数据维护
     */
    @Override
    public List<SysPostVersion> selectSysPostVersionList(SysPostVersion SysPostVersion)
    {
        return SysPostVersionMapper.selectSysPostVersionList(SysPostVersion);
    }

    /**
     * 新增岗位信息版本数据维护
     *
     * @param SysPostVersion 岗位信息版本数据维护
     * @return 结果
     */
    @Override
    public int insertSysPostVersion(SysPostVersion SysPostVersion)
    {
        SysPostVersion.setCreateTime(DateUtils.getNowDate());
        return SysPostVersionMapper.insertSysPostVersion(SysPostVersion);
    }

    /**
     * 修改岗位信息版本数据维护
     *
     * @param SysPostVersion 岗位信息版本数据维护
     * @return 结果
     */
    @Override
    public int updateSysPostVersion(SysPostVersion SysPostVersion)
    {
        SysPostVersion.setUpdateTime(DateUtils.getNowDate());
        return SysPostVersionMapper.updateSysPostVersion(SysPostVersion);
    }

    /**
     * 批量删除岗位信息版本数据维护
     *
     * @param Ids 需要删除的岗位信息版本数据维护主键
     * @return 结果
     */
    @Override
    public int deleteSysPostVersionByIds(Long[] Ids)
    {
        return SysPostVersionMapper.deleteSysPostVersionByIds(Ids);
    }

    /**
     * 删除岗位信息版本数据维护信息
     *
     * @param Id 岗位信息版本数据维护主键
     * @return 结果
     */
    @Override
    public int deleteSysPostVersionById(Long Id)
    {
        return SysPostVersionMapper.deleteSysPostVersionById(Id);
    }
}
