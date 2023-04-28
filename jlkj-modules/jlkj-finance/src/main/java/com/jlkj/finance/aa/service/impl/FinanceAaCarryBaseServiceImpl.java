package com.jlkj.finance.aa.service.impl;

import java.util.List;
import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.common.core.utils.uuid.IdUtils;
import com.jlkj.common.security.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jlkj.finance.aa.mapper.FinanceAaCarryBaseMapper;
import com.jlkj.finance.aa.domain.FinanceAaCarryBase;
import com.jlkj.finance.aa.service.IFinanceAaCarryBaseService;

/**
 * 自动结转规则-基本资料-集团Service业务层处理
 *
 * @author 116514
 * @date 2023-04-24
 */
@Service
public class FinanceAaCarryBaseServiceImpl implements IFinanceAaCarryBaseService
{
    @Autowired
    private FinanceAaCarryBaseMapper financeAaCarryBaseMapper;

    /**
     * 查询自动结转规则-基本资料-集团
     *
     * @param id 自动结转规则-基本资料-集团主键
     * @return 自动结转规则-基本资料-集团
     */
    @Override
    public FinanceAaCarryBase selectFinanceAaCarryBaseById(String id)
    {
        return financeAaCarryBaseMapper.selectFinanceAaCarryBaseById(id);
    }

    /**
     * 查询自动结转规则-基本资料-集团列表
     *
     * @param financeAaCarryBase 自动结转规则-基本资料-集团
     * @return 自动结转规则-基本资料-集团
     */
    @Override
    public List<FinanceAaCarryBase> selectFinanceAaCarryBaseList(FinanceAaCarryBase financeAaCarryBase)
    {
        return financeAaCarryBaseMapper.selectFinanceAaCarryBaseList(financeAaCarryBase);
    }

    /**
     * 新增自动结转规则-基本资料-集团
     *
     * @param financeAaCarryBase 自动结转规则-基本资料-集团
     * @return 结果
     */
    @Override
    public int insertFinanceAaCarryBase(FinanceAaCarryBase financeAaCarryBase)
    {
        financeAaCarryBase.setId(IdUtils.simpleUUID());
        financeAaCarryBase.setCreateBy(SecurityUtils.getUsername());
        financeAaCarryBase.setCreateName(SecurityUtils.getNickName());
        financeAaCarryBase.setCreateTime(DateUtils.getNowDate());
        return financeAaCarryBaseMapper.insertFinanceAaCarryBase(financeAaCarryBase);
    }

    /**
     * 修改自动结转规则-基本资料-集团
     *
     * @param financeAaCarryBase 自动结转规则-基本资料-集团
     * @return 结果
     */
    @Override
    public int updateFinanceAaCarryBase(FinanceAaCarryBase financeAaCarryBase)
    {
        financeAaCarryBase.setUpdateBy(SecurityUtils.getUsername());
        financeAaCarryBase.setUpdateName(SecurityUtils.getNickName());
        financeAaCarryBase.setUpdateTime(DateUtils.getNowDate());
        return financeAaCarryBaseMapper.updateFinanceAaCarryBase(financeAaCarryBase);
    }

    /**
     * 批量删除自动结转规则-基本资料-集团
     *
     * @param ids 需要删除的自动结转规则-基本资料-集团主键
     * @return 结果
     */
    @Override
    public int deleteFinanceAaCarryBaseByIds(String[] ids)
    {
        return financeAaCarryBaseMapper.deleteFinanceAaCarryBaseByIds(ids);
    }

    /**
     * 删除自动结转规则-基本资料-集团信息
     *
     * @param id 自动结转规则-基本资料-集团主键
     * @return 结果
     */
    @Override
    public int deleteFinanceAaCarryBaseById(String id)
    {
        return financeAaCarryBaseMapper.deleteFinanceAaCarryBaseById(id);
    }
}
