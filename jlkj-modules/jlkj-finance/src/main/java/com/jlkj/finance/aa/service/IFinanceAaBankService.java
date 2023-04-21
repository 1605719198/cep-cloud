package com.jlkj.finance.aa.service;

import com.jlkj.finance.aa.domain.FinanceAaBank;

import java.util.List;

/**
 * 金融机构管理Service接口
 * 
 * @author SunXuTong
 * @date 2023-04-12
 */
public interface IFinanceAaBankService 
{
    /**
     * 查询金融机构管理
     * 
     * @param id 金融机构管理主键
     * @return 金融机构管理
     */
    public FinanceAaBank selectFinanceAaBankById(String id);

    /**
     * 查询金融机构管理列表
     * 
     * @param financeAaBank 金融机构管理
     * @return 金融机构管理集合
     */
    public List<FinanceAaBank> selectFinanceAaBankList(FinanceAaBank financeAaBank);

    /**
     * 新增金融机构管理
     * 
     * @param financeAaBank 金融机构管理
     * @return 结果
     */
    public int insertFinanceAaBank(FinanceAaBank financeAaBank);

    /**
     * 修改金融机构管理
     * 
     * @param financeAaBank 金融机构管理
     * @return 结果
     */
    public int updateFinanceAaBank(FinanceAaBank financeAaBank);

    /**
     * 批量删除金融机构管理
     * 
     * @param ids 需要删除的金融机构管理主键集合
     * @return 结果
     */
    public int deleteFinanceAaBankByIds(String[] ids);

    /**
     * 删除金融机构管理信息
     * 
     * @param id 金融机构管理主键
     * @return 结果
     */
    public int deleteFinanceAaBankById(String id);
}
