package com.jlkj.finance.gp.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.common.core.utils.uuid.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jlkj.finance.gp.mapper.FinanceGpPurchaseLinkMapper;
import com.jlkj.finance.gp.domain.FinanceGpPurchaseLink;
import com.jlkj.finance.gp.service.IFinanceGpPurchaseLinkService;

/**
 * 厂商采购关系联系人Service业务层处理
 *
 * @author 265799
 * @date 2023-04-07
 */
@Service
public class FinanceGpPurchaseLinkServiceImpl implements IFinanceGpPurchaseLinkService
{
    @Autowired
    private FinanceGpPurchaseLinkMapper financeGpPurchaseLinkMapper;

    /**
     * 查询厂商采购关系联系人
     *
     * @param uuid 厂商采购关系联系人主键
     * @return 厂商采购关系联系人
     */
    @Override
    public FinanceGpPurchaseLink selectFinanceGpPurchaseLinkByUuid(String uuid)
    {
        return financeGpPurchaseLinkMapper.selectFinanceGpPurchaseLinkByUuid(uuid);
    }

    /**
     * 查询厂商采购关系联系人列表
     *
     * @param financeGpPurchaseLink 厂商采购关系联系人
     * @return 厂商采购关系联系人
     */
    @Override
    public List<FinanceGpPurchaseLink> selectFinanceGpPurchaseLinkList(FinanceGpPurchaseLink financeGpPurchaseLink)
    {
        return financeGpPurchaseLinkMapper.selectFinanceGpPurchaseLinkList(financeGpPurchaseLink);
    }

    /**
     * 新增厂商采购关系联系人
     *
     * @param financeGpPurchaseLink 厂商采购关系联系人
     * @return 结果
     */
    @Override
    public int insertFinanceGpPurchaseLink(FinanceGpPurchaseLink financeGpPurchaseLink)
    {
        financeGpPurchaseLink.setCreateTime(DateUtils.getNowDate());
        return financeGpPurchaseLinkMapper.insertFinanceGpPurchaseLink(financeGpPurchaseLink);
    }

    /**
     * 修改厂商采购关系联系人
     *
     * @param financeGpPurchaseLink 厂商采购关系联系人
     * @return 结果
     */
    @Override
    public int updateFinanceGpPurchaseLink(FinanceGpPurchaseLink financeGpPurchaseLink)
    {
        financeGpPurchaseLink.setUpdateTime(DateUtils.getNowDate());
        return financeGpPurchaseLinkMapper.updateFinanceGpPurchaseLink(financeGpPurchaseLink);
    }

    /**
     * 批量删除厂商采购关系联系人
     *
     * @param uuids 需要删除的厂商采购关系联系人主键
     * @return 结果
     */
    @Override
    public int deleteFinanceGpPurchaseLinkByUuids(String[] uuids)
    {
        return financeGpPurchaseLinkMapper.deleteFinanceGpPurchaseLinkByUuids(uuids);
    }

    /**
     * 删除厂商采购关系联系人信息
     *
     * @param uuid 厂商采购关系联系人主键
     * @return 结果
     */
    @Override
    public int deleteFinanceGpPurchaseLinkByUuid(String uuid)
    {
        return financeGpPurchaseLinkMapper.deleteFinanceGpPurchaseLinkByUuid(uuid);
    }


    /**
     * 批量新增新增厂商采购关系联系人
     *
     * @param tCapitalDetailList 厂商采购关系联系人
     */
    public void insertCapitalDetail(List<FinanceGpPurchaseLink> tCapitalDetailList,String manufacturerId) {
        financeGpPurchaseLinkMapper.deleteFinanceGpPurchaseLink(manufacturerId);
        List<FinanceGpPurchaseLink> list = new ArrayList<FinanceGpPurchaseLink>();
        for (FinanceGpPurchaseLink financeGpPurchaseLink:tCapitalDetailList){
            financeGpPurchaseLink.setUuid(UUID.randomUUID().toString());
            list.add(financeGpPurchaseLink);
        }
        financeGpPurchaseLinkMapper.batchFinanceGpPurchaseLink(list);

    }
}
