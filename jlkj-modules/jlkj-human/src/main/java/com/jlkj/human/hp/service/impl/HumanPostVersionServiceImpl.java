package com.jlkj.human.hp.service.impl;

import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.human.hp.domain.HumanPostVersion;
import com.jlkj.human.hp.mapper.HumanPostVersionMapper;
import com.jlkj.human.hp.service.IHumanPostVersionService;
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
public class HumanPostVersionServiceImpl implements IHumanPostVersionService
{
    @Autowired
    private HumanPostVersionMapper humanPostVersionMapper;

    /**
     * 查询岗位信息版本数据维护
     *
     * @param id 岗位信息版本数据维护主键
     * @return 岗位信息版本数据维护
     */
    @Override
    public HumanPostVersion selectSysPostVersionById(Long id)
    {
        return humanPostVersionMapper.selectSysPostVersionById(id);
    }

    /**
     * 查询岗位信息版本数据维护列表
     *
     * @param humanPostVersion 岗位信息版本数据维护
     * @return 岗位信息版本数据维护
     */
    @Override
    public List<HumanPostVersion> selectSysPostVersionList(HumanPostVersion humanPostVersion)
    {
        return humanPostVersionMapper.selectSysPostVersionList(humanPostVersion);
    }

    /**
     * 新增岗位信息版本数据维护
     *
     * @param humanPostVersion 岗位信息版本数据维护
     * @return 结果
     */
    @Override
    public int insertSysPostVersion(HumanPostVersion humanPostVersion)
    {
        humanPostVersion.setCreateTime(DateUtils.getNowDate());
        return humanPostVersionMapper.insertSysPostVersion(humanPostVersion);
    }

    /**
     * 修改岗位信息版本数据维护
     *
     * @param humanPostVersion 岗位信息版本数据维护
     * @return 结果
     */
    @Override
    public int updateSysPostVersion(HumanPostVersion humanPostVersion)
    {
        humanPostVersion.setUpdateTime(DateUtils.getNowDate());
        return humanPostVersionMapper.updateSysPostVersion(humanPostVersion);
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
        return humanPostVersionMapper.deleteSysPostVersionById(id);
    }
}
