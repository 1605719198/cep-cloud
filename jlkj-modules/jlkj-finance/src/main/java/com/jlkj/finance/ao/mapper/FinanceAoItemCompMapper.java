package com.jlkj.finance.ao.mapper;

import com.jlkj.finance.ao.domain.FinanceAoItemComp;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 报支类别-公司级-设定主档Mapper接口
 * 
 * @author 116514
 * @date 2023-05-23
 */
@Repository
public interface FinanceAoItemCompMapper 
{
    /**
     * 查询报支类别-公司级-设定主档
     * 
     * @param id 报支类别-公司级-设定主档主键
     * @return 报支类别-公司级-设定主档
     */
    public FinanceAoItemComp selectFinanceAoItemCompById(String id);

    /**
     * 查询报支类别-公司级-设定主档列表
     * 
     * @param financeAoItemComp 报支类别-公司级-设定主档
     * @return 报支类别-公司级-设定主档集合
     */
    public List<FinanceAoItemComp> selectFinanceAoItemCompList(FinanceAoItemComp financeAoItemComp);

    /**
     * 新增报支类别-公司级-设定主档
     * 
     * @param financeAoItemComp 报支类别-公司级-设定主档
     * @return 结果
     */
    public int insertFinanceAoItemComp(FinanceAoItemComp financeAoItemComp);

    /**
     * 修改报支类别-公司级-设定主档
     * 
     * @param financeAoItemComp 报支类别-公司级-设定主档
     * @return 结果
     */
    public int updateFinanceAoItemComp(FinanceAoItemComp financeAoItemComp);

    /**
     * 删除报支类别-公司级-设定主档
     * 
     * @param id 报支类别-公司级-设定主档主键
     * @return 结果
     */
    public int deleteFinanceAoItemCompById(String id);

    /**
     * 批量删除报支类别-公司级-设定主档
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFinanceAoItemCompByIds(String[] ids);

    /**
     * 批量新增公司级报支类别
     * @param financeAoItemComps
     * @return
     */
    public int insertBatch(List<FinanceAoItemComp> financeAoItemComps);

    /**
     * 通过id查询是否有细项数据
     * @param ids
     * @return
     */
    public List<String> selectDetailsByIds(String[] ids);


    /**
     * 查询报支类别下拉选单
     * @return
     */
    public List<Map<String,String>> selectItemNoList(String companyId);

}
