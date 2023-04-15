package com.jlkj.finance.aa.mapper;

import com.jlkj.finance.aa.domain.FinanceAaBranch;

import java.util.List;

/**
 * 金融机构管理分行Mapper接口
 * 
 * @author SunXuTong
 * @date 2023-04-14
 */
public interface FinanceAaBranchMapper 
{
    /**
     * 查询金融机构管理分行
     * 
     * @param id 金融机构管理分行主键
     * @return 金融机构管理分行
     */
    public FinanceAaBranch selectFinanceAaBranchById(String id);

    /**
     * 查询金融机构管理分行列表
     * 
     * @param financeAaBranch 金融机构管理分行
     * @return 金融机构管理分行集合
     */
    public List<FinanceAaBranch> selectFinanceAaBranchList(FinanceAaBranch financeAaBranch);

    /**
     * 新增金融机构管理分行
     * 
     * @param financeAaBranch 金融机构管理分行
     * @return 结果
     */
    public int insertFinanceAaBranch(FinanceAaBranch financeAaBranch);

    /**
     * 修改金融机构管理分行
     * 
     * @param financeAaBranch 金融机构管理分行
     * @return 结果
     */
    public int updateFinanceAaBranch(FinanceAaBranch financeAaBranch);

    /**
     * 删除金融机构管理分行
     * 
     * @param id 金融机构管理分行主键
     * @return 结果
     */
    public int deleteFinanceAaBranchById(String id);

    /**
     * 批量删除金融机构管理分行
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFinanceAaBranchByIds(String[] ids);
}
