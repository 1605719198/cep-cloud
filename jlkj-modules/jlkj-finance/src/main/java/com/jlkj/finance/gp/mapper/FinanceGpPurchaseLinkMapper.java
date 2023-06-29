package com.jlkj.finance.gp.mapper;

import java.util.List;
import com.jlkj.finance.gp.domain.FinanceGpPurchaseLink;
import org.springframework.stereotype.Repository;

/**
 * 厂商采购关系联系人Mapper接口
 * 
 * @author 265799
 * @date 2023-04-07
 */
@Repository
public interface FinanceGpPurchaseLinkMapper 
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
     * 删除厂商采购关系联系人
     * 
     * @param uuid 厂商采购关系联系人主键
     * @return 结果
     */
    public int deleteFinanceGpPurchaseLinkByUuid(String uuid);

    /**
     * 批量删除厂商采购关系联系人
     * 
     * @param uuids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFinanceGpPurchaseLinkByUuids(String[] uuids);
    /**
     * 批量新增厂商采购关系联系人
     *
     * @param tCapitalDetailList 需要新增的数据集合
     * @return 结果
     */
    public int batchFinanceGpPurchaseLink(List<FinanceGpPurchaseLink> tCapitalDetailList);
    /**
     * 删除厂商采购关系联系人
     *
     * @param manufacturerId 厂商采购关系联系人厂商编码
     * @return 结果
     */
    public int deleteFinanceGpPurchaseLink(String manufacturerId);
}
