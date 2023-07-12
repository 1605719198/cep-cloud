package com.jlkj.finance.ft.service.impl;

import java.util.List;
import com.jlkj.common.core.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jlkj.finance.ft.mapper.FinanceFtCardMapper;
import com.jlkj.finance.ft.domain.FinanceFtCard;
import com.jlkj.finance.ft.service.IFinanceFtCardService;

/**
 * 固定资产卡片主档Service业务层处理
 *
 * @author 116524
 * @date 2023-07-10
 * @version 1.0
 */
@Service
public class FinanceFtCardServiceImpl implements IFinanceFtCardService
{
    @Autowired
    private FinanceFtCardMapper financeFtCardMapper;

    /**
     * 查询固定资产卡片主档
     *
     * @author 116524
     * @date 2023-07-10
     * @param assetId 固定资产卡片主档主键
     * @return 固定资产卡片主档
     */
    @Override
    public FinanceFtCard selectFinanceFtCardByAssetId(String assetId)
    {
        return financeFtCardMapper.selectFinanceFtCardByAssetId(assetId);
    }

    /**
     * 查询固定资产卡片主档列表
     *
     * @author 116524
     * @date 2023-07-10
     * @param financeFtCard 固定资产卡片主档
     * @return 固定资产卡片主档
     */
    @Override
    public List<FinanceFtCard> selectFinanceFtCardList(FinanceFtCard financeFtCard)
    {
        return financeFtCardMapper.selectFinanceFtCardList(financeFtCard);
    }

    /**
     * 新增固定资产卡片主档
     *
     * @author 116524
     * @date 2023-07-10
     * @param financeFtCard 固定资产卡片主档
     * @return 结果
     */
    @Override
    public int insertFinanceFtCard(FinanceFtCard financeFtCard)
    {
        financeFtCard.setCreateTime(DateUtils.getNowDate());
        return financeFtCardMapper.insertFinanceFtCard(financeFtCard);
    }

    /**
     * 修改固定资产卡片主档
     *
     * @author 116524
     * @date 2023-07-10
     * @param financeFtCard 固定资产卡片主档
     * @return 结果
     */
    @Override
    public int updateFinanceFtCard(FinanceFtCard financeFtCard)
    {
        financeFtCard.setUpdateTime(DateUtils.getNowDate());
        return financeFtCardMapper.updateFinanceFtCard(financeFtCard);
    }

    /**
     * 批量删除固定资产卡片主档
     *
     * @author 116524
     * @date 2023-07-10
     * @param assetIds 需要删除的固定资产卡片主档主键
     * @return 结果
     */
    @Override
    public int deleteFinanceFtCardByAssetIds(String[] assetIds)
    {
        return financeFtCardMapper.deleteFinanceFtCardByAssetIds(assetIds);
    }

    /**
     * 删除固定资产卡片主档信息
     *
     * @author 116524
     * @date 2023-07-10
     * @param assetId 固定资产卡片主档主键
     * @return 结果
     */
    @Override
    public int deleteFinanceFtCardByAssetId(String assetId)
    {
        return financeFtCardMapper.deleteFinanceFtCardByAssetId(assetId);
    }
    /**
     * 查询固定资产卡片主档列表for弹窗
     *
     * @author 116524
     * @date 2023-07-10
     * @param financeFtCard 固定资产卡片主档
     * @return 固定资产卡片主档
     */
    @Override
    public List<FinanceFtCard>  selectCardListForPop(FinanceFtCard financeFtCard)
    {
        return financeFtCardMapper.selectCardListForPop(financeFtCard);
    }
}
