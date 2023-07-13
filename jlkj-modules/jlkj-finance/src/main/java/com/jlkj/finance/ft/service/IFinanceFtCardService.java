package com.jlkj.finance.ft.service;

import java.util.List;
import com.jlkj.finance.ft.domain.FinanceFtCard;

/**
 * 固定资产卡片主档Service接口
 * 
 * @author 116524
 * @date 2023-07-10
 */
public interface IFinanceFtCardService 
{
    /**
     * 查询固定资产卡片主档
     * 
     * @param assetId 固定资产卡片主档主键
     * @return 固定资产卡片主档
     */
    public FinanceFtCard selectFinanceFtCardByAssetId(String assetId);

    /**
     * 查询固定资产卡片主档列表
     *
     * @author 116524
     * @date 2023-07-10
     * @param financeFtCard 固定资产卡片主档
     * @return 固定资产卡片主档集合
     */
    public List<FinanceFtCard> selectFinanceFtCardList(FinanceFtCard financeFtCard);

    /**
     * 新增固定资产卡片主档
     *
     * @author 116524
     * @date 2023-07-10
     * @param financeFtCard 固定资产卡片主档
     * @return 结果
     */
    public int insertFinanceFtCard(FinanceFtCard financeFtCard);

    /**
     * 修改固定资产卡片主档
     *
     * @author 116524
     * @date 2023-07-10
     * @param financeFtCard 固定资产卡片主档
     * @return 结果
     */
    public int updateFinanceFtCard(FinanceFtCard financeFtCard);

    /**
     * 批量删除固定资产卡片主档
     *
     * @author 116524
     * @date 2023-07-10
     * @param assetIds 需要删除的固定资产卡片主档主键集合
     * @return 结果
     */
    public int deleteFinanceFtCardByAssetIds(String[] assetIds);

    /**
     * 删除固定资产卡片主档信息
     *
     * @author 116524
     * @date 2023-07-10
     * @param assetId 固定资产卡片主档主键
     * @return 结果
     */
    public int deleteFinanceFtCardByAssetId(String assetId);

    /**
     * 查询固定资产卡片主档列表for弹窗
     *
     * @author 116524
     * @date 2023-07-10
     * @return 固定资产卡片主档集合
     */
    public List<FinanceFtCard> selectCardListForPop(FinanceFtCard financeFtCard);
}
