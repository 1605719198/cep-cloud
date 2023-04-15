package com.jlkj.finance.aa.service.impl;

import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.common.core.utils.uuid.IdUtils;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.finance.aa.domain.FinanceAaBranch;
import com.jlkj.finance.aa.mapper.FinanceAaBranchMapper;
import com.jlkj.finance.aa.service.IFinanceAaBranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 金融机构管理分行Service业务层处理
 *
 * @author SunXuTong
 * @date 2023-04-14
 */
@Service
public class FinanceAaBranchServiceImpl implements IFinanceAaBranchService
{
    @Autowired
    private FinanceAaBranchMapper financeAaBranchMapper;

    /**
     * 查询金融机构管理分行
     *
     * @param id 金融机构管理分行主键
     * @return 金融机构管理分行
     */
    @Override
    public FinanceAaBranch selectFinanceAaBranchById(String id)
    {
        return financeAaBranchMapper.selectFinanceAaBranchById(id);
    }

    /**
     * 查询金融机构管理分行列表
     *
     * @param financeAaBranch 金融机构管理分行
     * @return 金融机构管理分行
     */
    @Override
    public List<FinanceAaBranch> selectFinanceAaBranchList(FinanceAaBranch financeAaBranch)
    {
        return financeAaBranchMapper.selectFinanceAaBranchList(financeAaBranch);
    }

    /**
     * 新增金融机构管理分行
     *
     * @param financeAaBranch 金融机构管理分行
     * @return 结果
     */
    @Override
    public int insertFinanceAaBranch(FinanceAaBranch financeAaBranch)
    {
        financeAaBranch.setId(IdUtils.fastSimpleUUID());
        financeAaBranch.setCreateTime(DateUtils.getNowDate());
        financeAaBranch.setCreateBy(SecurityUtils.getUsername());
        financeAaBranch.setCreateName(SecurityUtils.getNickName());
        return financeAaBranchMapper.insertFinanceAaBranch(financeAaBranch);
    }

    /**
     * 修改金融机构管理分行
     *
     * @param financeAaBranch 金融机构管理分行
     * @return 结果
     */
    @Override
    public int updateFinanceAaBranch(FinanceAaBranch financeAaBranch)
    {
        financeAaBranch.setUpdateTime(DateUtils.getNowDate());
        financeAaBranch.setUpdateBy(SecurityUtils.getUsername());
        financeAaBranch.setUpdateName(SecurityUtils.getNickName());
        return financeAaBranchMapper.updateFinanceAaBranch(financeAaBranch);
    }

    /**
     * 批量删除金融机构管理分行
     *
     * @param ids 需要删除的金融机构管理分行主键
     * @return 结果
     */
    @Override
    public int deleteFinanceAaBranchByIds(String[] ids)
    {
        return financeAaBranchMapper.deleteFinanceAaBranchByIds(ids);
    }

    /**
     * 删除金融机构管理分行信息
     *
     * @param id 金融机构管理分行主键
     * @return 结果
     */
    @Override
    public int deleteFinanceAaBranchById(String id)
    {
        return financeAaBranchMapper.deleteFinanceAaBranchById(id);
    }
}
