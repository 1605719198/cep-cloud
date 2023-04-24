package com.jlkj.finance.aa.service.impl;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.system.api.domain.SysUser;
import com.jlkj.system.api.model.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jlkj.finance.aa.mapper.FinanceCaltypeMapper;
import com.jlkj.finance.aa.domain.FinanceCaltype;
import com.jlkj.finance.aa.service.IFinanceCaltypeService;

/**
 * 核算项目-类别维护Service业务层处理
 *
 * @author 116514
 * @date 2023-04-06
 */
@Service
public class FinanceCaltypeServiceImpl implements IFinanceCaltypeService
{
    @Autowired
    private FinanceCaltypeMapper financeCaltypeMapper;

    /**
     * 查询核算项目-类别维护
     *
     * @param id 核算项目-类别维护主键
     * @return 核算项目-类别维护
     */
    @Override
    public FinanceCaltype selectFinanceCaltypeById(String id)
    {
        return financeCaltypeMapper.selectFinanceCaltypeById(id);
    }

    /**
     * 查询核算项目-类别维护列表
     *
     * @param financeCaltype 核算项目-类别维护
     * @return 核算项目-类别维护
     */
    @Override
    public List<FinanceCaltype> selectFinanceCaltypeList(FinanceCaltype financeCaltype)
    {
        return financeCaltypeMapper.selectFinanceCaltypeList(financeCaltype);
    }

    /**
     * 新增核算项目-类别维护
     *
     * @param financeCaltype 核算项目-类别维护
     * @return 结果
     */
    @Override
    public int insertFinanceCaltype(FinanceCaltype financeCaltype)
    {
        financeCaltype.setId(UUID.randomUUID().toString());
        LoginUser loginUser = SecurityUtils.getLoginUser();
        SysUser user = loginUser.getSysUser();
        financeCaltype.setCreateBy(user.getUserName());
        financeCaltype.setCreateName(user.getNickName());
        financeCaltype.setCreateTime(DateUtils.getNowDate());
        return financeCaltypeMapper.insertFinanceCaltype(financeCaltype);
    }

    /**
     * 修改核算项目-类别维护
     *
     * @param financeCaltype 核算项目-类别维护
     * @return 结果
     */
    @Override
    public int updateFinanceCaltype(FinanceCaltype financeCaltype)
    {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        SysUser user = loginUser.getSysUser();
        financeCaltype.setUpdateBy(user.getUserName());
        financeCaltype.setUpdateName(user.getNickName());
        financeCaltype.setUpdateTime(DateUtils.getNowDate());
        return financeCaltypeMapper.updateFinanceCaltype(financeCaltype);
    }

    /**
     * 批量删除核算项目-类别维护
     *
     * @param ids 需要删除的核算项目-类别维护主键
     * @return 结果
     */
    @Override
    public int deleteFinanceCaltypeByIds(String[] ids)
    {
        return financeCaltypeMapper.deleteFinanceCaltypeByIds(ids);
    }

    /**
     * 删除核算项目-类别维护信息
     *
     * @param id 核算项目-类别维护主键
     * @return 结果
     */
    @Override
    public int deleteFinanceCaltypeById(String id)
    {
        return financeCaltypeMapper.deleteFinanceCaltypeById(id);
    }

    /**
     * 查询核算项目类别(下拉选单用)
     * @return
     */
    @Override
    public List<Map<String,String>> selectCalTypeList() { return financeCaltypeMapper.selectCalTypeList(); }
}
