package com.jlkj.finance.aa.service.impl;

import java.util.List;
import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.common.core.utils.uuid.IdUtils;
import com.jlkj.common.security.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jlkj.finance.aa.mapper.FinanceAaCarryBaseCorpMapper;
import com.jlkj.finance.aa.domain.FinanceAaCarryBaseCorp;
import com.jlkj.finance.aa.service.IFinanceAaCarryBaseCorpService;

/**
 * 自动结转规则-基本资料-集团Service业务层处理
 *
 * @author 116514
 * @date 2023-04-24
 */
@Service
public class FinanceAaCarryBaseCorpServiceImpl implements IFinanceAaCarryBaseCorpService
{
    @Autowired
    private FinanceAaCarryBaseCorpMapper financeAaCarryBaseCorpMapper;

    /**
     * 查询自动结转规则-基本资料-集团
     *
     * @param id 自动结转规则-基本资料-集团主键
     * @return 自动结转规则-基本资料-集团
     */
    @Override
    public FinanceAaCarryBaseCorp selectFinanceAaCarryBaseCorpById(String id)
    {
        return financeAaCarryBaseCorpMapper.selectFinanceAaCarryBaseCorpById(id);
    }

    /**
     * 查询自动结转规则-基本资料-集团列表
     *
     * @param financeAaCarryBaseCorp 自动结转规则-基本资料-集团
     * @return 自动结转规则-基本资料-集团
     */
    @Override
    public List<FinanceAaCarryBaseCorp> selectFinanceAaCarryBaseCorpList(FinanceAaCarryBaseCorp financeAaCarryBaseCorp)
    {
        return financeAaCarryBaseCorpMapper.selectFinanceAaCarryBaseCorpList(financeAaCarryBaseCorp);
    }

    /**
     * 新增自动结转规则-基本资料-集团
     *
     * @param financeAaCarryBaseCorp 自动结转规则-基本资料-集团
     * @return 结果
     */
    @Override
    public int insertFinanceAaCarryBaseCorp(FinanceAaCarryBaseCorp financeAaCarryBaseCorp)
    {
        financeAaCarryBaseCorp.setId(IdUtils.simpleUUID());
        financeAaCarryBaseCorp.setCreateBy(SecurityUtils.getUsername());
        financeAaCarryBaseCorp.setCreateName(SecurityUtils.getNickName());
        financeAaCarryBaseCorp.setCreateTime(DateUtils.getNowDate());
        return financeAaCarryBaseCorpMapper.insertFinanceAaCarryBaseCorp(financeAaCarryBaseCorp);
    }

    /**
     * 修改自动结转规则-基本资料-集团
     *
     * @param financeAaCarryBaseCorp 自动结转规则-基本资料-集团
     * @return 结果
     */
    @Override
    public int updateFinanceAaCarryBaseCorp(FinanceAaCarryBaseCorp financeAaCarryBaseCorp)
    {
        financeAaCarryBaseCorp.setUpdateBy(SecurityUtils.getUsername());
        financeAaCarryBaseCorp.setUpdateName(SecurityUtils.getNickName());
        financeAaCarryBaseCorp.setUpdateTime(DateUtils.getNowDate());
        return financeAaCarryBaseCorpMapper.updateFinanceAaCarryBaseCorp(financeAaCarryBaseCorp);
    }

    /**
     * 批量删除自动结转规则-基本资料-集团
     *
     * @param ids 需要删除的自动结转规则-基本资料-集团主键
     * @return 结果
     */
    @Override
    public int deleteFinanceAaCarryBaseCorpByIds(String[] ids)
    {
        return financeAaCarryBaseCorpMapper.deleteFinanceAaCarryBaseCorpByIds(ids);
    }

    /**
     * 删除自动结转规则-基本资料-集团信息
     *
     * @param id 自动结转规则-基本资料-集团主键
     * @return 结果
     */
    @Override
    public int deleteFinanceAaCarryBaseCorpById(String id)
    {
        return financeAaCarryBaseCorpMapper.deleteFinanceAaCarryBaseCorpById(id);
    }
}
