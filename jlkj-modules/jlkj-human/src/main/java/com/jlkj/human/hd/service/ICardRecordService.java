package com.jlkj.human.hd.service;

import com.jlkj.human.hd.domain.CardRecord;

import java.util.List;

/**
 * 刷卡记录Service接口
 * 
 * @author jiangbingchen
 * @date 2023-04-03
 */
public interface ICardRecordService 
{
    /**
     * 查询刷卡记录
     * 
     * @param id 刷卡记录主键
     * @return 刷卡记录
     */
    public CardRecord selectCardRecordById(String id);

    /**
     * 查询刷卡记录列表
     * 
     * @param cardRecord 刷卡记录
     * @return 刷卡记录集合
     */
    public List<CardRecord> selectCardRecordList(CardRecord cardRecord);

    /**
     * 新增刷卡记录
     * 
     * @param cardRecord 刷卡记录
     * @return 结果
     */
    public int insertCardRecord(CardRecord cardRecord);

    /**
     * 修改刷卡记录
     * 
     * @param cardRecord 刷卡记录
     * @return 结果
     */
    public int updateCardRecord(CardRecord cardRecord);

    /**
     * 批量删除刷卡记录
     * 
     * @param ids 需要删除的刷卡记录主键集合
     * @return 结果
     */
    public int deleteCardRecordByIds(String[] ids);

    /**
     * 删除刷卡记录信息
     * 
     * @param id 刷卡记录主键
     * @return 结果
     */
    public int deleteCardRecordById(String id);
}
