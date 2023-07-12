package com.jlkj.finance.ft.service;

import java.util.List;
import com.jlkj.finance.ft.domain.FinanceFtChange;

/**
 * 资产变动单主档Service接口
 * 
 * @author jlkj
 * @date 2023-07-04
 */
public interface IFinanceFtChangeService 
{
    /**
     * 查询资产变动单主档
     * 
     * @param uuid 资产变动单主档主键
     * @return 资产变动单主档
     */

    public FinanceFtChange selectFinanceFtChangeByUuid(String uuid);

    /**
     * 查询资产变动单主档列表
     *
     * @author jlkj
     * @date 2023-07-04
     * @param financeFtChange 资产变动单主档
     * @return 资产变动单主档集合
     */
    public List<FinanceFtChange> selectFinanceFtChangeList(FinanceFtChange financeFtChange);

    /**
     * 新增资产变动单主档
     *
     * @author jlkj
     * @date 2023-07-04
     * @param financeFtChange 资产变动单主档
     * @return 结果
     */
    public int insertFinanceFtChange(FinanceFtChange financeFtChange);

    /**
     * 修改资产变动单主档
     *
     * @author jlkj
     * @date 2023-07-04
     * @param financeFtChange 资产变动单主档
     * @return 结果
     */
    public int updateFinanceFtChange(FinanceFtChange financeFtChange);

    /**
     * 批量删除资产变动单主档
     *
     * @author jlkj
     * @date 2023-07-04
     * @param uuids 需要删除的资产变动单主档主键集合
     * @return 结果
     */
    public int deleteFinanceFtChangeByUuids(String[] uuids);

    /**
     * 删除资产变动单主档信息
     *
     * @author jlkj
     * @date 2023-07-04
     * @param uuid 资产变动单主档主键
     * @return 结果
     */
    public int deleteFinanceFtChangeByUuid(String uuid);
}
