package com.jlkj.finance.ap.mapper;

import com.jlkj.finance.ap.domain.FinanceApItem;

import java.util.List;
import java.util.Map;

/**
 * 报支类别集团设置Mapper接口
 * 
 * @author SunXuTong
 * @date 2023-05-31
 */
public interface FinanceApItemMapper 
{
    /**
     * 查询报支类别集团设置
     * 
     * @param id 报支类别集团设置主键
     * @return 报支类别集团设置
     */
    public FinanceApItem selectFinanceApItemById(String id);
    /**
     * 查询报支类别集团设置
     *
     * @param id 报支类别集团设置主键
     * @return 报支类别集团设置
     */
    public FinanceApItem selectFinanceApItemByIdcal(String id);
    /**
     * 查询报支类别集团设置列表
     * 
     * @param financeApItem 报支类别集团设置
     * @return 报支类别集团设置集合
     */
    public List<FinanceApItem> selectFinanceApItemList(FinanceApItem financeApItem);

    /**
     * 新增报支类别集团设置
     * 
     * @param financeApItem 报支类别集团设置
     * @return 结果
     */
    public int insertFinanceApItem(FinanceApItem financeApItem);

    /**
     * 修改报支类别集团设置
     * 
     * @param financeApItem 报支类别集团设置
     * @return 结果
     */
    public int updateFinanceApItem(FinanceApItem financeApItem);

    /**
     * 删除报支类别集团设置
     * 
     * @param id 报支类别集团设置主键
     * @return 结果
     */
    public int deleteFinanceApItemById(String id);

    /**
     * 批量删除报支类别集团设置
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFinanceApItemByIds(String[] ids);

    /**
     * 通过id查询是否有细项数据
     * @param ids
     * @return
     */
    public List<Map<String,String>> selectItemNosByIds(String[] ids);

    /**
     * 检核数据唯一性
     * @param itemNo
     * @return
     */
    public int updateCheckUnique(String itemNo);

    /**
     *  选取集团报支类别(公司级报支类别用)
     * @param companyId
     * @return
     */
    public List<Map<String,Object>> selectItemAndDetailList(String companyId);
}
