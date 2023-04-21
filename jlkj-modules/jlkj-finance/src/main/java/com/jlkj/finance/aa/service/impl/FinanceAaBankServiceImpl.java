package com.jlkj.finance.aa.service.impl;

import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.common.core.utils.uuid.IdUtils;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.finance.aa.domain.FinanceAaBank;
import com.jlkj.finance.aa.mapper.FinanceAaBankMapper;
import com.jlkj.finance.aa.service.IFinanceAaBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 金融机构管理Service业务层处理
 *
 * @author SunXuTong
 * @date 2023-04-12
 */
@Service
public class FinanceAaBankServiceImpl implements IFinanceAaBankService
{
    @Autowired
    private FinanceAaBankMapper financeAaBankMapper;

    /**
     * 查询金融机构管理
     *
     * @param id 金融机构管理主键
     * @return 金融机构管理
     */
    @Override
    public FinanceAaBank selectFinanceAaBankById(String id)
    {
        return financeAaBankMapper.selectFinanceAaBankById(id);
    }

    /**
     * 查询金融机构管理列表
     *
     * @param financeAaBank 金融机构管理
     * @return 金融机构管理
     */
    @Override
    public List<FinanceAaBank> selectFinanceAaBankList(FinanceAaBank financeAaBank)
    {
        return financeAaBankMapper.selectFinanceAaBankList(financeAaBank);
    }

    /**
     * 新增金融机构管理
     *
     * @param financeAaBank 金融机构管理
     * @return 结果
     */
    @Override
    public int insertFinanceAaBank(FinanceAaBank financeAaBank)
    {
        financeAaBank.setId(IdUtils.fastSimpleUUID());
        financeAaBank.setCreateTime(DateUtils.getNowDate());
        financeAaBank.setCreateBy(SecurityUtils.getUsername());
        financeAaBank.setCreateName(SecurityUtils.getNickName());
        return financeAaBankMapper.insertFinanceAaBank(financeAaBank);
    }

    /**
     * 修改金融机构管理
     *
     * @param financeAaBank 金融机构管理
     * @return 结果
     */
    @Override
    public int updateFinanceAaBank(FinanceAaBank financeAaBank)
    {
        financeAaBank.setUpdateTime(DateUtils.getNowDate());
        financeAaBank.setUpdateBy(SecurityUtils.getUsername());
        financeAaBank.setUpdateName(SecurityUtils.getNickName());
        return financeAaBankMapper.updateFinanceAaBank(financeAaBank);
    }

    /**
     * 批量删除金融机构管理
     *
     * @param ids 需要删除的金融机构管理主键
     * @return 结果
     */
    @Override
    public int deleteFinanceAaBankByIds(String[] ids)
    {
        return financeAaBankMapper.deleteFinanceAaBankByIds(ids);
    }

    /**
     * 删除金融机构管理信息
     *
     * @param id 金融机构管理主键
     * @return 结果
     */
    @Override
    public int deleteFinanceAaBankById(String id)
    {
        return financeAaBankMapper.deleteFinanceAaBankById(id);
    }
}
