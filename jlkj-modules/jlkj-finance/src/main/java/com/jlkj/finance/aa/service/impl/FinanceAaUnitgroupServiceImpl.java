package com.jlkj.finance.aa.service.impl;

import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.common.core.utils.uuid.IdUtils;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.finance.aa.domain.FinanceAaUnitgroup;
import com.jlkj.finance.aa.mapper.FinanceAaUnitgroupMapper;
import com.jlkj.finance.aa.service.IFinanceAaUnitgroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 计量单位分组Service业务层处理
 *
 * @author SunXuTong
 * @date 2023-04-17
 */
@Service
public class FinanceAaUnitgroupServiceImpl implements IFinanceAaUnitgroupService
{
    @Autowired
    private FinanceAaUnitgroupMapper financeAaUnitgroupMapper;

    /**
     * 查询计量单位分组
     *
     * @param id 计量单位分组主键
     * @return 计量单位分组
     */
    @Override
    public FinanceAaUnitgroup selectFinanceAaUnitgroupById(String id)
    {
        return financeAaUnitgroupMapper.selectFinanceAaUnitgroupById(id);
    }

    /**
     * 查询计量单位分组列表
     *
     * @param financeAaUnitgroup 计量单位分组
     * @return 计量单位分组
     */
    @Override
    public List<FinanceAaUnitgroup> selectFinanceAaUnitgroupList(FinanceAaUnitgroup financeAaUnitgroup)
    {
        return financeAaUnitgroupMapper.selectFinanceAaUnitgroupList(financeAaUnitgroup);
    }

    /**
     * 新增计量单位分组
     *
     * @param financeAaUnitgroup 计量单位分组
     * @return 结果
     */
    @Override
    public int insertFinanceAaUnitgroup(FinanceAaUnitgroup financeAaUnitgroup)
    {
        financeAaUnitgroup.setId(IdUtils.fastSimpleUUID());
        financeAaUnitgroup.setCreateTime(DateUtils.getNowDate());
        financeAaUnitgroup.setCreateBy(SecurityUtils.getUsername());
        financeAaUnitgroup.setCreateName(SecurityUtils.getNickName());
        return financeAaUnitgroupMapper.insertFinanceAaUnitgroup(financeAaUnitgroup);
    }

    /**
     * 修改计量单位分组
     *
     * @param financeAaUnitgroup 计量单位分组
     * @return 结果
     */
    @Override
    public int updateFinanceAaUnitgroup(FinanceAaUnitgroup financeAaUnitgroup)
    {
        financeAaUnitgroup.setUpdateTime(DateUtils.getNowDate());
        financeAaUnitgroup.setUpdateBy(SecurityUtils.getUsername());
        financeAaUnitgroup.setUpdateName(SecurityUtils.getNickName());
        return financeAaUnitgroupMapper.updateFinanceAaUnitgroup(financeAaUnitgroup);
    }

    /**
     * 批量删除计量单位分组
     *
     * @param ids 需要删除的计量单位分组主键
     * @return 结果
     */
    @Override
    public int deleteFinanceAaUnitgroupByIds(String[] ids)
    {
        return financeAaUnitgroupMapper.deleteFinanceAaUnitgroupByIds(ids);
    }

    /**
     * 删除计量单位分组信息
     *
     * @param id 计量单位分组主键
     * @return 结果
     */
    @Override
    public int deleteFinanceAaUnitgroupById(String id)
    {
        return financeAaUnitgroupMapper.deleteFinanceAaUnitgroupById(id);
    }
}