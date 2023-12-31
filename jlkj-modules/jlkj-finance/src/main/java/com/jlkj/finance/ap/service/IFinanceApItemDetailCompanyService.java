package com.jlkj.finance.ap.service;

import com.jlkj.finance.ap.domain.FinanceApItemDetailCompany;

import java.util.List;
import java.util.Map;

/**
 * 报支类别公司细项Service接口
 * 
 * @author SunXuTong
 * @date 2023-06-08
 */
public interface IFinanceApItemDetailCompanyService 
{
    /**
     * 查询报支类别公司细项
     * 
     * @param id 报支类别公司细项主键
     * @return 报支类别公司细项
     */
    public FinanceApItemDetailCompany selectFinanceApItemDetailCompanyById(String id);

    /**
     * 查询报支类别公司细项列表
     * 
     * @param financeApItemDetailCompany 报支类别公司细项
     * @return 报支类别公司细项集合
     */
    public List<FinanceApItemDetailCompany> selectFinanceApItemDetailCompanyList(FinanceApItemDetailCompany financeApItemDetailCompany);

    /**
     * 新增报支类别公司细项
     * 
     * @param financeApItemDetailCompany 报支类别公司细项
     * @return 结果
     */
    public int insertFinanceApItemDetailCompany(FinanceApItemDetailCompany financeApItemDetailCompany);

    /**
     * 修改报支类别公司细项
     * 
     * @param financeApItemDetailCompany 报支类别公司细项
     * @return 结果
     */
    public int updateFinanceApItemDetailCompany(FinanceApItemDetailCompany financeApItemDetailCompany);

    /**
     * 批量删除报支类别公司细项
     * 
     * @param ids 需要删除的报支类别公司细项主键集合
     * @return 结果
     */
    public int deleteFinanceApItemDetailCompanyByIds(String[] ids);

    /**
     * 删除报支类别公司细项信息
     * 
     * @param id 报支类别公司细项主键
     * @return 结果
     */
    public int deleteFinanceApItemDetailCompanyById(String id);
   /**
   *获取报支类别公司设定明细档费用细项
   *@author 265799
   *@date 2023/7/12  11:11
    * @param financeApItemDetailCompany :
    * @return : java.util.List<java.util.Map<java.lang.String,java.lang.String>>
   */
    public List<Map<String,String>> selectItemNoList(FinanceApItemDetailCompany financeApItemDetailCompany);
}
