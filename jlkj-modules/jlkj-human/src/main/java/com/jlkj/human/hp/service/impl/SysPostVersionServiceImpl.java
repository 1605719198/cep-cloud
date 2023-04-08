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
    private SysPostVersionMapper sysPostVersionMapper;

    /**
     * 查询岗位信息版本数据维护
     *
     * @param id 岗位信息版本数据维护主键
     * @return 岗位信息版本数据维护
     */
    @Override
    public SysPostVersion selectSysPostVersionById(Long id)
    {
        return sysPostVersionMapper.selectSysPostVersionById(id);
    }

    /**
     * 查询岗位信息版本数据维护列表
     *
     * @param sysPostVersion 岗位信息版本数据维护
     * @return 岗位信息版本数据维护
     */
    @Override
    public List<SysPostVersion> selectSysPostVersionList(SysPostVersion sysPostVersion)
    {
        return sysPostVersionMapper.selectSysPostVersionList(sysPostVersion);
    }

    /**
     * 新增岗位信息版本数据维护
     *
     * @param sysPostVersion 岗位信息版本数据维护
     * @return 结果
     */
    @Override
    public int insertSysPostVersion(SysPostVersion sysPostVersion)
    {
        sysPostVersion.setCreateTime(DateUtils.getNowDate());
        return sysPostVersionMapper.insertSysPostVersion(sysPostVersion);
    }

    /**
     * 修改岗位信息版本数据维护
     *
     * @param sysPostVersion 岗位信息版本数据维护
     * @return 结果
     */
    @Override
    public int updateSysPostVersion(SysPostVersion sysPostVersion)
    {
        sysPostVersion.setUpdateTime(DateUtils.getNowDate());
        return sysPostVersionMapper.updateSysPostVersion(sysPostVersion);
    }

    /**
     * 删除岗位信息版本数据维护信息
     *
     * @param id 岗位信息版本数据维护主键
     * @return 结果
     */
    @Override
    public int deleteSysPostVersionById(Long id)
    {
        return sysPostVersionMapper.deleteSysPostVersionById(id);
    }
}
