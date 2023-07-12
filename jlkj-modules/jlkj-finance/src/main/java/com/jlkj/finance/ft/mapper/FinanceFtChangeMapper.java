package com.jlkj.finance.ft.mapper;

import java.util.List;
import com.jlkj.finance.ft.domain.FinanceFtChange;
import com.jlkj.finance.ft.domain.FinanceFtChangeDetail;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 资产变动单主档Mapper接口
 * 
 * @author jlkj
 * @date 2023-07-04
 */
@Repository
public interface FinanceFtChangeMapper 
{
    
    /**
    * @Description: 
    * @Param:[uuid]
    * @return:com.jlkj.finance.ft.domain.FinanceFtChange
    * @Author: 116524_wxj
    * @Date: 2023/7/6 16:29
    */


    /**
    * @Description:
    * @param:[uuid]
    * @return:com.jlkj.finance.ft.domain.FinanceFtChange
    * @Author: 116524_wxj
    * @Date: 2023/7/7 10:30
    */
    public FinanceFtChange selectFinanceFtChangeByUuid(String uuid);

    /**
     * 查询资产变动单主档列表
     * 
     * @param financeFtChange 资产变动单主档
     * @return 资产变动单主档集合
     */
    public List<FinanceFtChange> selectFinanceFtChangeList(FinanceFtChange financeFtChange);

    /**
     * 新增资产变动单主档
     * 
     * @param financeFtChange 资产变动单主档
     * @return 结果
     */
    public int insertFinanceFtChange(FinanceFtChange financeFtChange);

    /**
     * 修改资产变动单主档
     * 
     * @param financeFtChange 资产变动单主档
     * @return 结果
     */
    public int updateFinanceFtChange(FinanceFtChange financeFtChange);

    /**
     * 删除资产变动单主档
     * 
     * @param uuid 资产变动单主档主键
     * @return 结果
     */
    public int deleteFinanceFtChangeByUuid(String uuid);

    /**
     * 批量删除资产变动单主档
     * 
     * @param uuids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFinanceFtChangeByUuids(String[] uuids);

    /**
     * 批量删除资产变动单主档
     * 
     * @param uuids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFinanceFtChangeDetailByChangeIds(String[] uuids);
    
    /**
     * 批量新增资产变动单主档
     * 
     * @param financeFtChangeDetailList 资产变动单主档列表
     * @return 结果
     */
    public int batchFinanceFtChangeDetail(List<FinanceFtChangeDetail> financeFtChangeDetailList);
    

    /**
     * 通过资产变动单主档主键删除资产变动单主档信息
     * 
     * @param uuid 资产变动单主档ID
     * @return 结果
     */
    public int deleteFinanceFtChangeDetailByChangeId(String uuid);

    /**
     * 查询最大流水号
     * @param companyId
     * @param changeNo
     * @return
     */
    public String selectMaxChangeNo(@Param("companyId") String companyId, @Param("changeNo")String changeNo);
}
