package com.jlkj.finance.ap.mapper;

import com.jlkj.finance.ap.domain.FinanceApItemComp;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 报支类别公司设置Mapper接口
 * 
 * @author SunXuTong
 * @date 2023-06-06
 */
@Repository
public interface FinanceApItemCompMapper 
{
    /**
     * 查询报支类别公司设置
     * 
     * @param id 报支类别公司设置主键
     * @return 报支类别公司设置
     */
    public FinanceApItemComp selectFinanceApItemCompById(String id);

    /**
     * 查询报支类别公司设置列表
     * 
     * @param financeApItemComp 报支类别公司设置
     * @return 报支类别公司设置集合
     */
    public List<FinanceApItemComp> selectFinanceApItemCompList(FinanceApItemComp financeApItemComp);
    /**
     * 查询报支类别公司设置列表
     *
     * @param financeApItemComp 报支类别公司设置
     * @return 报支类别公司设置集合
     */
    public FinanceApItemComp selectFinanceApItemComp(FinanceApItemComp financeApItemComp);


    /**
     * 新增报支类别公司设置
     * 
     * @param financeApItemComp 报支类别公司设置
     * @return 结果
     */
    public int insertFinanceApItemComp(FinanceApItemComp financeApItemComp);

    /**
     * 修改报支类别公司设置
     * 
     * @param financeApItemComp 报支类别公司设置
     * @return 结果
     */
    public int updateFinanceApItemComp(FinanceApItemComp financeApItemComp);

    /**
     * 删除报支类别公司设置
     * 
     * @param id 报支类别公司设置主键
     * @return 结果
     */
    public int deleteFinanceApItemCompById(String id);

    /**
     * 批量删除报支类别公司设置
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFinanceApItemCompByIds(String[] ids);

    /**
     * 批量新增公司级报支类别
     * @param financeApItemComps
     * @return
     */
    public int insertBatch(List<FinanceApItemComp> financeApItemComps);

    /**
     * 通过id查询是否有细项数据
     * @param ids
     * @return
     */
    public List<Map<String,String>> selectItemNosByIds(String[] ids);
    /**
     *查询下拉选单
     *@author 265799
     *@date 2023/7/12  10:53
     * @param companyId :
     * @return : java.util.List<java.util.Map<java.lang.String,java.lang.String>>
     */
    public List<Map<String,String>> selectItemNoList(String companyId);
}


