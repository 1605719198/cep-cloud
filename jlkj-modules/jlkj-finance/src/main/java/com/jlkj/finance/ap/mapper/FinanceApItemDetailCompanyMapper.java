package com.jlkj.finance.ap.mapper;

import com.jlkj.finance.ap.domain.FinanceApItemDetailCompany;
import com.jlkj.finance.ap.dto.FinanceApItemDetailDTO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 报支类别公司细项Mapper接口
 * 
 * @author SunXuTong
 * @date 2023-06-08
 */
@Repository
public interface FinanceApItemDetailCompanyMapper 
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
     * 删除报支类别公司细项
     * 
     * @param id 报支类别公司细项主键
     * @return 结果
     */
    public int deleteFinanceApItemDetailCompanyById(String id);

    /**
     * 批量删除报支类别公司细项
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFinanceApItemDetailCompanyByIds(String[] ids);
    /**
    * 新增该报支类别的同时新增该类别下所有细项资料
    *@author 265799
    *@date 2023/7/7  10:57
     * @param financeApItemDetails :
     * @return : int
    */
    public int insertBatch(List<FinanceApItemDetailDTO> financeApItemDetails);

    /**
    *报支类别公司设定明细档
    *@author 265799
    *@date 2023/7/12  11:13
     * @param financeApItemDetailCompany :
     * @return : java.util.List<java.util.Map<java.lang.String,java.lang.String>>
    */
    public List<Map<String,String>> selectItemNoList(FinanceApItemDetailCompany financeApItemDetailCompany);

}

