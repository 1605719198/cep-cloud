package com.jlkj.finance.aa.service.impl;

import java.util.List;
import java.util.UUID;

import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.system.api.domain.SysUser;
import com.jlkj.system.api.model.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jlkj.finance.aa.mapper.FinanceCalcodeMapper;
import com.jlkj.finance.aa.domain.FinanceCalcode;
import com.jlkj.finance.aa.service.IFinanceCalcodeService;

/**
 * 核算项目-内容维护Service业务层处理
 *
 * @author 116514
 * @date 2023-04-10
 */
@Service
public class FinanceCalcodeServiceImpl implements IFinanceCalcodeService
{
    @Autowired
    private FinanceCalcodeMapper financeCalcodeMapper;

    /**
     * 查询核算项目-内容维护
     *
     * @param id 核算项目-内容维护主键
     * @return 核算项目-内容维护
     */
    @Override
    public FinanceCalcode selectFinanceCalcodeById(String id)
    {
        return financeCalcodeMapper.selectFinanceCalcodeById(id);
    }

    /**
     * 查询核算项目-内容维护列表
     *
     * @param financeCalcode 核算项目-内容维护
     * @return 核算项目-内容维护
     */
    @Override
    public List<FinanceCalcode> selectFinanceCalcodeList(FinanceCalcode financeCalcode)
    {
        return financeCalcodeMapper.selectFinanceCalcodeList(financeCalcode);
    }

    /**
     * 新增核算项目-内容维护
     *
     * @param financeCalcode 核算项目-内容维护
     * @return 结果
     */
    @Override
    public int insertFinanceCalcode(FinanceCalcode financeCalcode)
    {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        SysUser user = loginUser.getSysUser();
        financeCalcode.setId(UUID.randomUUID().toString());
        financeCalcode.setCreateBy(user.getUserName());
        financeCalcode.setCreateName(user.getNickName());
        financeCalcode.setCreateTime(DateUtils.getNowDate());
        return financeCalcodeMapper.insertFinanceCalcode(financeCalcode);
    }

    /**
     * 修改核算项目-内容维护
     *
     * @param financeCalcode 核算项目-内容维护
     * @return 结果
     */
    @Override
    public int updateFinanceCalcode(FinanceCalcode financeCalcode)
    {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        SysUser user = loginUser.getSysUser();
        financeCalcode.setUpdateBy(user.getUserName());
        financeCalcode.setUpdateName(user.getNickName());
        financeCalcode.setUpdateTime(DateUtils.getNowDate());
        return financeCalcodeMapper.updateFinanceCalcode(financeCalcode);
    }

    /**
     * 批量删除核算项目-内容维护
     *
     * @param ids 需要删除的核算项目-内容维护主键
     * @return 结果
     */
    @Override
    public int deleteFinanceCalcodeByIds(String[] ids)
    {
        return financeCalcodeMapper.deleteFinanceCalcodeByIds(ids);
    }

    /**
     * 删除核算项目-内容维护信息
     *
     * @param id 核算项目-内容维护主键
     * @return 结果
     */
    @Override
    public int deleteFinanceCalcodeById(String id)
    {
        return financeCalcodeMapper.deleteFinanceCalcodeById(id);
    }
}
