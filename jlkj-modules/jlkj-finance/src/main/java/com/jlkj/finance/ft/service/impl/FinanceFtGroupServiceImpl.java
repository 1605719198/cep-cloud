package com.jlkj.finance.ft.service.impl;

import com.jlkj.common.core.exception.ServiceException;
import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.common.core.utils.uuid.IdUtils;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.finance.ft.domain.FinanceFtGroup;
import com.jlkj.finance.ft.mapper.FinanceFtGroupMapper;
import com.jlkj.finance.ft.service.IFinanceFtGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 资产大类基本资料Service业务层处理
 *
 * @author SunXuTong
 * @date 2023-06-21
 */
@Service
public class FinanceFtGroupServiceImpl implements IFinanceFtGroupService
{
    @Autowired
    private FinanceFtGroupMapper financeFtGroupMapper;

    /**
     * 查询资产大类基本资料
     *
     * @param id 资产大类基本资料主键
     * @return 资产大类基本资料
     */
    @Override
    public FinanceFtGroup selectFinanceFtGroupById(String id)
    {
        return financeFtGroupMapper.selectFinanceFtGroupById(id);
    }

    /**
     * 查询资产大类基本资料列表
     *
     * @param financeFtGroup 资产大类基本资料
     * @return 资产大类基本资料
     */
    @Override
    public List<FinanceFtGroup> selectFinanceFtGroupList(FinanceFtGroup financeFtGroup)
    {
        return financeFtGroupMapper.selectFinanceFtGroupList(financeFtGroup);
    }

    /**
     * 新增资产大类基本资料
     *
     * @param financeFtGroup 资产大类基本资料
     * @return 结果
     */
    @Override
    public int insertFinanceFtGroup(FinanceFtGroup financeFtGroup)
    {
        List<FinanceFtGroup> financeFtGroups = financeFtGroupMapper.selectFinanceFtGroupByCompIdAndAssetGroupNo(financeFtGroup);
        if (financeFtGroups.isEmpty()){
            financeFtGroup.setId(IdUtils.fastSimpleUUID());
            financeFtGroup.setCreateTime(DateUtils.getNowDate());
            financeFtGroup.setCreateBy(SecurityUtils.getUsername());
            financeFtGroup.setCreateName(SecurityUtils.getNickName());
            return financeFtGroupMapper.insertFinanceFtGroup(financeFtGroup);
        } else {
            throw new ServiceException("资产大类："+financeFtGroup.getAssetGroupNo()+"已经存在，不可重复新增！");
        }
    }

    /**
     * 修改资产大类基本资料
     *
     * @param financeFtGroup 资产大类基本资料
     * @return 结果
     */
    @Override
    public int updateFinanceFtGroup(FinanceFtGroup financeFtGroup)
    {
        financeFtGroup.setUpdateTime(DateUtils.getNowDate());
        financeFtGroup.setUpdateBy(SecurityUtils.getUsername());
        financeFtGroup.setUpdateName(SecurityUtils.getNickName());
        return financeFtGroupMapper.updateFinanceFtGroup(financeFtGroup);
    }

    /**
     * 批量删除资产大类基本资料
     *
     * @param ids 需要删除的资产大类基本资料主键
     * @return 结果
     */
    @Override
    public int deleteFinanceFtGroupByIds(String[] ids)
    {
        return financeFtGroupMapper.deleteFinanceFtGroupByIds(ids);
    }

    /**
     * 删除资产大类基本资料信息
     *
     * @param id 资产大类基本资料主键
     * @return 结果
     */
    @Override
    public int deleteFinanceFtGroupById(String id)
    {
        return financeFtGroupMapper.deleteFinanceFtGroupById(id);
    }
}
