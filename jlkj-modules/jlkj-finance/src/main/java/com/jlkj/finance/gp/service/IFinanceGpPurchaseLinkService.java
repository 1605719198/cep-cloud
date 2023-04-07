package com.jlkj.finance.gp.service;

import java.util.List;
import com.jlkj.finance.gp.domain.FinanceGpPurchaseLink;

/**
 * 厂商采购关系联系人Service接口
 * 
 * @author 265799
 * @date 2023-04-07
 */
public interface IFinanceGpPurchaseLinkService 
{
    /**
     * 查询厂商采购关系联系人
     * 
     * @param uuid 厂商采购关系联系人主键
     * @return 厂商采购关系联系人
     */
    public FinanceGpPurchaseLink selectFinanceGpPurchaseLinkByUuid(String uuid);

    /**
     * 查询厂商采购关系联系人列表
     * 
     * @param financeGpPurchaseLink 厂商采购关系联系人
     * @return 厂商采购关系联系人集合
     */
    public List<FinanceGpPurchaseLink> selectFinanceGpPurchaseLinkList(FinanceGpPurchaseLink financeGpPurchaseLink);

    /**
     * 新增厂商采购关系联系人
     * 
     * @param financeGpPurchaseLink 厂商采购关系联系人
     * @return 结果
     */
    public int insertFinanceGpPurchaseLink(FinanceGpPurchaseLink financeGpPurchaseLink);

    /**
     * 修改厂商采购关系联系人
     * 
     * @param financeGpPurchaseLink 厂商采购关系联系人
     * @return 结果
     */
    public int updateFinanceGpPurchaseLink(FinanceGpPurchaseLink financeGpPurchaseLink);

    /**
     * 批量删除厂商采购关系联系人
     * 
     * @param uuids 需要删除的厂商采购关系联系人主键集合
     * @return 结果
     */
    public int deleteFinanceGpPurchaseLinkByUuids(String[] uuids);

    /**
     * 删除厂商采购关系联系人信息
     * 
     * @param uuid 厂商采购关系联系人主键
     * @return 结果
     */
    public int deleteFinanceGpPurchaseLinkByUuid(String uuid);
}
