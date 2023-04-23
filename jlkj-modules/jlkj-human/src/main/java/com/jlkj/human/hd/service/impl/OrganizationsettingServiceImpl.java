package com.jlkj.human.hd.service.impl;

import com.jlkj.common.core.utils.uuid.UUID;
import com.jlkj.human.hd.domain.Organizationsetting;
import com.jlkj.human.hd.mapper.OrganizationsettingMapper;
import com.jlkj.human.hd.service.IOrganizationsettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 机构参数Service业务层处理
 *
 * @author 266861
 * @date 2023-03-28
 */
@Service
public class OrganizationsettingServiceImpl implements IOrganizationsettingService
{
    @Autowired
    private OrganizationsettingMapper organizationsettingMapper;

    /**
     * 查询机构参数
     *
     * @param id 机构参数主键
     * @return 机构参数
     */
    @Override
    public Organizationsetting selectOrganizationsettingById(String id)
    {
        return organizationsettingMapper.selectOrganizationsettingById(id);
    }

    /**
     * 查询机构参数列表
     *
     * @param organizationsetting 机构参数
     * @return 机构参数
     */
    @Override
    public List<Organizationsetting> selectOrganizationsettingList(Organizationsetting organizationsetting)
    {
        return organizationsettingMapper.selectOrganizationsettingList(organizationsetting);
    }

    /**
     * 新增机构参数
     *
     * @param organizationsetting 机构参数
     * @return 结果
     */
    @Override
    public int insertOrganizationsetting(Organizationsetting organizationsetting)
    {
        organizationsetting.setId(UUID.randomUUID().toString().substring(0, 32));
        return organizationsettingMapper.insertOrganizationsetting(organizationsetting);
    }

    /**
     * 修改机构参数
     *
     * @param organizationsetting 机构参数
     * @return 结果
     */
    @Override
    public int updateOrganizationsetting(Organizationsetting organizationsetting)
    {
        return organizationsettingMapper.updateOrganizationsetting(organizationsetting);
    }

    /**
     * 批量删除机构参数
     *
     * @param ids 需要删除的机构参数主键
     * @return 结果
     */
    @Override
    public int deleteOrganizationsettingByIds(String[] ids)
    {
        return organizationsettingMapper.deleteOrganizationsettingByIds(ids);
    }

    /**
     * 删除机构参数信息
     *
     * @param id 机构参数主键
     * @return 结果
     */
    @Override
    public int deleteOrganizationsettingById(String id)
    {
        return organizationsettingMapper.deleteOrganizationsettingById(id);
    }
}
