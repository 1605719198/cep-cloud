package com.jlkj.finance.aa.mapper;

import com.jlkj.finance.aa.domain.IpmsDeBankAccount;

import java.util.List;

/**
 * 往来银行Mapper接口
 * 
 * @author SunXuTong
 * @date 2023-05-22
 */
public interface IpmsDeBankAccountMapper 
{
    /**
     * 查询往来银行
     * 
     * @param id 往来银行主键
     * @return 往来银行
     */
    public IpmsDeBankAccount selectIpmsDeBankAccountById(String id);

    /**
     * 查询往来银行列表
     * 
     * @param ipmsDeBankAccount 往来银行
     * @return 往来银行集合
     */
    public List<IpmsDeBankAccount> selectIpmsDeBankAccountList(IpmsDeBankAccount ipmsDeBankAccount);

    /**
     * 新增往来银行
     * 
     * @param ipmsDeBankAccount 往来银行
     * @return 结果
     */
    public int insertIpmsDeBankAccount(IpmsDeBankAccount ipmsDeBankAccount);

    /**
     * 修改往来银行
     * 
     * @param ipmsDeBankAccount 往来银行
     * @return 结果
     */
    public int updateIpmsDeBankAccount(IpmsDeBankAccount ipmsDeBankAccount);

    /**
     * 删除往来银行
     * 
     * @param id 往来银行主键
     * @return 结果
     */
    public int deleteIpmsDeBankAccountById(String id);

    /**
     * 批量删除往来银行
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteIpmsDeBankAccountByIds(String[] ids);

    /**
     * 修改银行信息
     *
     * @param ipmsDeBankAccount 银行信息
     * @return 结果
     */
    public int updateStatus(IpmsDeBankAccount ipmsDeBankAccount);
}
