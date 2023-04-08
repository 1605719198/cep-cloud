package com.jlkj.human.hd.service.impl;

import com.jlkj.human.hd.domain.CardRecord;
import com.jlkj.human.hd.mapper.CardRecordMapper;
import com.jlkj.human.hd.service.ICardRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 刷卡记录Service业务层处理
 *
 * @author jiangbingchen
 * @date 2023-04-03
 */
@Service
public class CardRecordServiceImpl implements ICardRecordService
{
    @Autowired
    private CardRecordMapper cardRecordMapper;

    /**
     * 查询刷卡记录
     *
     * @param id 刷卡记录主键
     * @return 刷卡记录
     */
    @Override
    public CardRecord selectCardRecordById(String id)
    {
        return cardRecordMapper.selectCardRecordById(id);
    }

    /**
     * 查询刷卡记录列表
     *
     * @param cardRecord 刷卡记录
     * @return 刷卡记录
     */
    @Override
    public List<CardRecord> selectCardRecordList(CardRecord cardRecord)
    {
        return cardRecordMapper.selectCardRecordList(cardRecord);
    }

    /**
     * 新增刷卡记录
     *
     * @param cardRecord 刷卡记录
     * @return 结果
     */
    @Override
    public int insertCardRecord(CardRecord cardRecord)
    {
        return cardRecordMapper.insertCardRecord(cardRecord);
    }

    /**
     * 修改刷卡记录
     *
     * @param cardRecord 刷卡记录
     * @return 结果
     */
    @Override
    public int updateCardRecord(CardRecord cardRecord)
    {
        return cardRecordMapper.updateCardRecord(cardRecord);
    }

    /**
     * 批量删除刷卡记录
     *
     * @param ids 需要删除的刷卡记录主键
     * @return 结果
     */
    @Override
    public int deleteCardRecordByIds(String[] ids)
    {
        return cardRecordMapper.deleteCardRecordByIds(ids);
    }

    /**
     * 删除刷卡记录信息
     *
     * @param id 刷卡记录主键
     * @return 结果
     */
    @Override
    public int deleteCardRecordById(String id)
    {
        return cardRecordMapper.deleteCardRecordById(id);
    }
}
