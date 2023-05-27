package com.jlkj.finance.aa.service.impl;

import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.common.core.utils.uuid.IdUtils;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.finance.aa.domain.IpmsDeBankAccount;
import com.jlkj.finance.aa.mapper.IpmsDeBankAccountMapper;
import com.jlkj.finance.aa.service.IIpmsDeBankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 往来银行Service业务层处理
 *
 * @author SunXuTong
 * @date 2023-05-22
 */
@Service
public class IpmsDeBankAccountServiceImpl implements IIpmsDeBankAccountService
{
    @Autowired
    private IpmsDeBankAccountMapper ipmsDeBankAccountMapper;

    /**
     * 查询往来银行
     *
     * @param id 往来银行主键
     * @return 往来银行
     */
    @Override
    public IpmsDeBankAccount selectIpmsDeBankAccountById(String id)
    {
        return ipmsDeBankAccountMapper.selectIpmsDeBankAccountById(id);
    }

    /**
     * 查询往来银行列表
     *
     * @param ipmsDeBankAccount 往来银行
     * @return 往来银行
     */
    @Override
    public List<IpmsDeBankAccount> selectIpmsDeBankAccountList(IpmsDeBankAccount ipmsDeBankAccount)
    {
        return ipmsDeBankAccountMapper.selectIpmsDeBankAccountList(ipmsDeBankAccount);
    }

    /**
     * 新增往来银行
     *
     * @param ipmsDeBankAccount 往来银行
     * @return 结果
     */
    @Override
    public int insertIpmsDeBankAccount(IpmsDeBankAccount ipmsDeBankAccount)
    {
        ipmsDeBankAccount.setId(IdUtils.fastSimpleUUID());
        ipmsDeBankAccount.setCreateTime(DateUtils.getNowDate());
        ipmsDeBankAccount.setCreateBy(SecurityUtils.getUsername());
        ipmsDeBankAccount.setCreateName(SecurityUtils.getNickName());
        return ipmsDeBankAccountMapper.insertIpmsDeBankAccount(ipmsDeBankAccount);
    }

    /**
     * 修改往来银行
     *
     * @param ipmsDeBankAccount 往来银行
     * @return 结果
     */
    @Override
    public int updateIpmsDeBankAccount(IpmsDeBankAccount ipmsDeBankAccount)
    {
        ipmsDeBankAccount.setUpdateTime(DateUtils.getNowDate());
        ipmsDeBankAccount.setUpdateBy(SecurityUtils.getUsername());
        ipmsDeBankAccount.setUpdateName(SecurityUtils.getNickName());
        return ipmsDeBankAccountMapper.updateIpmsDeBankAccount(ipmsDeBankAccount);
    }

    /**
     * 批量删除往来银行
     *
     * @param ids 需要删除的往来银行主键
     * @return 结果
     */
    @Override
    public int deleteIpmsDeBankAccountByIds(String[] ids)
    {
        return ipmsDeBankAccountMapper.deleteIpmsDeBankAccountByIds(ids);
    }

    /**
     * 删除往来银行信息
     *
     * @param id 往来银行主键
     * @return 结果
     */
    @Override
    public int deleteIpmsDeBankAccountById(String id)
    {
        return ipmsDeBankAccountMapper.deleteIpmsDeBankAccountById(id);
    }
}
