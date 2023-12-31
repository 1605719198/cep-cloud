package com.jlkj.finance.ao.mapper;

import com.jlkj.finance.ao.domain.FinanceAoItem;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 报支管理-报支类别集团设定主档Mapper接口
 * 
 * @author 116514
 * @date 2023-05-20
 */
@Repository
public interface FinanceAoItemMapper 
{
    /**
     * 查询报支管理-报支类别集团设定主档
     * 
     * @param id 报支管理-报支类别集团设定主档主键
     * @return 报支管理-报支类别集团设定主档
     */
    public FinanceAoItem selectFinanceAoItemById(String id);

    /**
     * 查询报支管理-报支类别集团设定主档列表
     * 
     * @param financeAoItem 报支管理-报支类别集团设定主档
     * @return 报支管理-报支类别集团设定主档集合
     */
    public List<FinanceAoItem> selectFinanceAoItemList(FinanceAoItem financeAoItem);

    /**
     * 新增报支管理-报支类别集团设定主档
     * 
     * @param financeAoItem 报支管理-报支类别集团设定主档
     * @return 结果
     */
    public int insertFinanceAoItem(FinanceAoItem financeAoItem);

    /**
     * 修改报支管理-报支类别集团设定主档
     * 
     * @param financeAoItem 报支管理-报支类别集团设定主档
     * @return 结果
     */
    public int updateFinanceAoItem(FinanceAoItem financeAoItem);

    /**
     * 删除报支管理-报支类别集团设定主档
     * 
     * @param id 报支管理-报支类别集团设定主档主键
     * @return 结果
     */
    public int deleteFinanceAoItemById(String id);

    /**
     * 批量删除报支管理-报支类别集团设定主档
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFinanceAoItemByIds(String[] ids);

    /**
     * 检核数据唯一性
     * @param itemNo
     * @return
     */
    public int updateCheckUnique(String itemNo);

    /**
     * 通过id查询是否有细项数据
     * @param ids
     * @return
     */
    public List<Map<String,String>> selectItemNosByIds(String[] ids);

    /**
     *  选取集团报支类别(公司级报支类别用)
     * @param companyId
     * @return
     */
    public List<Map<String,Object>> selectItemAndDetailList(String companyId);





}
