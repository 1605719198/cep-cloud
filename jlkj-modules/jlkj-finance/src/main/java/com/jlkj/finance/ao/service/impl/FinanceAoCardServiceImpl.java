package com.jlkj.finance.ao.service.impl;

import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.common.core.utils.uuid.IdUtils;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.finance.ao.domain.FinanceAoCard;
import com.jlkj.finance.ao.mapper.FinanceAoCardMapper;
import com.jlkj.finance.ao.service.IFinanceAoCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 个人信息设置Service业务层处理
 *
 * @author SunXuTong
 * @date 2023-05-08
 */
@Service
public class FinanceAoCardServiceImpl implements IFinanceAoCardService
{
    @Autowired
    private FinanceAoCardMapper financeAoCardMapper;

    /**
     * 查询个人信息设置
     *
     * @param uuid 个人信息设置主键
     * @return 个人信息设置
     */
    @Override
    public FinanceAoCard selectFinanceAoCardByUuid(String uuid)
    {
        return financeAoCardMapper.selectFinanceAoCardByUuid(uuid);
    }

    /**
     * 查询个人信息设置列表
     *
     * @param financeAoCard 个人信息设置
     * @return 个人信息设置
     */
    @Override
    public List<FinanceAoCard> selectFinanceAoCardList(FinanceAoCard financeAoCard)
    {
        return financeAoCardMapper.selectFinanceAoCardList(financeAoCard);
    }

    /**
     * 新增个人信息设置
     *
     * @param financeAoCard 个人信息设置
     * @return 结果
     */
    @Override
    public int insertFinanceAoCard(FinanceAoCard financeAoCard)
    {
        financeAoCard.setUuid(IdUtils.fastSimpleUUID());
        financeAoCard.setCreateTime(DateUtils.getNowDate());
        financeAoCard.setCreateBy(SecurityUtils.getUsername());
        financeAoCard.setCreateName(SecurityUtils.getNickName());
        return financeAoCardMapper.insertFinanceAoCard(financeAoCard);
    }

    /**
     * 修改个人信息设置
     *
     * @param financeAoCard 个人信息设置
     * @return 结果
     */
    @Override
    public int updateFinanceAoCard(FinanceAoCard financeAoCard)
    {
        financeAoCard.setUpdateTime(DateUtils.getNowDate());
        financeAoCard.setUpdateBy(SecurityUtils.getUsername());
        financeAoCard.setUpdateName(SecurityUtils.getNickName());
        return financeAoCardMapper.updateFinanceAoCard(financeAoCard);
    }

    /**
     * 批量删除个人信息设置
     *
     * @param uuids 需要删除的个人信息设置主键
     * @return 结果
     */
    @Override
    public int deleteFinanceAoCardByUuids(String[] uuids)
    {
        return financeAoCardMapper.deleteFinanceAoCardByUuids(uuids);
    }

    /**
     * 删除个人信息设置信息
     *
     * @param uuid 个人信息设置主键
     * @return 结果
     */
    @Override
    public int deleteFinanceAoCardByUuid(String uuid)
    {
        return financeAoCardMapper.deleteFinanceAoCardByUuid(uuid);
    }
}
