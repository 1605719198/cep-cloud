package com.jlkj.finance.aa.mapper;

import com.jlkj.finance.aa.domain.FinanceAaCarryRecord;

import java.util.List;

/**
 * 自动结转规则-执行记录Mapper接口
 * 
 * @author 265799
 * @date 2023-05-19
 */
public interface FinanceAaCarryRecordMapper 
{
    /**
     * 查询自动结转规则-执行记录
     * 
     * @param id 自动结转规则-执行记录主键
     * @return 自动结转规则-执行记录
     */
    public FinanceAaCarryRecord selectFinanceAaCarryRecordById(String id);

    /**
     * 查询自动结转规则-执行记录列表(集团)
     * 
     * @param financeAaCarryRecord 自动结转规则-执行记录
     * @return 自动结转规则-执行记录集合
     */
    public List<FinanceAaCarryRecord> selectFinanceAaCarryRecordList(FinanceAaCarryRecord financeAaCarryRecord);
    /**
     * 查询自动结转规则-执行记录列表(公司)
     *
     * @param financeAaCarryRecord 自动结转规则-执行记录
     * @return 自动结转规则-执行记录集合
     */
    public List<FinanceAaCarryRecord> selectFinanceAaCarryRecordCompanyIdList(FinanceAaCarryRecord financeAaCarryRecord);


    /**
     * 新增自动结转规则-执行记录
     * 
     * @param financeAaCarryRecord 自动结转规则-执行记录
     * @return 结果
     */
    public int insertFinanceAaCarryRecord(FinanceAaCarryRecord financeAaCarryRecord);

    /**
     * 修改自动结转规则-执行记录
     * 
     * @param financeAaCarryRecord 自动结转规则-执行记录
     * @return 结果
     */
    public int updateFinanceAaCarryRecord(FinanceAaCarryRecord financeAaCarryRecord);

    /**
     * 删除自动结转规则-执行记录
     * 
     * @param id 自动结转规则-执行记录主键
     * @return 结果
     */
    public int deleteFinanceAaCarryRecordById(String id);

    /**
     * 批量删除自动结转规则-执行记录
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFinanceAaCarryRecordByIds(String[] ids);
}
