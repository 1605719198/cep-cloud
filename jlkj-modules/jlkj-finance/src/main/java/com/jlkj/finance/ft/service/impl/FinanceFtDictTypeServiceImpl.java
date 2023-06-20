package com.jlkj.finance.ft.service.impl;

import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.common.core.utils.uuid.IdUtils;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.finance.ft.domain.FinanceFtDictType;
import com.jlkj.finance.ft.mapper.FinanceFtDictTypeMapper;
import com.jlkj.finance.ft.service.IFinanceFtDictTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 选单设定Service业务层处理
 *
 * @author SunXuTong
 * @date 2023-06-15
 */
@Service
public class FinanceFtDictTypeServiceImpl implements IFinanceFtDictTypeService
{
    @Autowired
    private FinanceFtDictTypeMapper financeFtDictTypeMapper;

    /**
     * 查询选单设定
     *
     * @param dictId 选单设定主键
     * @return 选单设定
     */
    @Override
    public FinanceFtDictType selectFinanceFtDictTypeByDictId(String dictId)
    {
        return financeFtDictTypeMapper.selectFinanceFtDictTypeByDictId(dictId);
    }

    /**
     * 查询选单设定列表
     *
     * @param financeFtDictType 选单设定
     * @return 选单设定
     */
    @Override
    public List<FinanceFtDictType> selectFinanceFtDictTypeList(FinanceFtDictType financeFtDictType)
    {
        return financeFtDictTypeMapper.selectFinanceFtDictTypeList(financeFtDictType);
    }

    /**
     * 新增选单设定
     *
     * @param financeFtDictType 选单设定
     * @return 结果
     */
    @Override
    public int insertFinanceFtDictType(FinanceFtDictType financeFtDictType)
    {
        financeFtDictType.setDictId(IdUtils.simpleUUID());
        financeFtDictType.setCreateBy(SecurityUtils.getUsername());
        financeFtDictType.setCreateName(SecurityUtils.getNickName());
        financeFtDictType.setCreateTime(DateUtils.getNowDate());
        return financeFtDictTypeMapper.insertFinanceFtDictType(financeFtDictType);
    }

    /**
     * 修改选单设定
     *
     * @param financeFtDictType 选单设定
     * @return 结果
     */
    @Override
    public int updateFinanceFtDictType(FinanceFtDictType financeFtDictType)
    {
        financeFtDictType.setUpdateBy(SecurityUtils.getUsername());
        financeFtDictType.setUpdateName(SecurityUtils.getNickName());
        financeFtDictType.setUpdateTime(DateUtils.getNowDate());
        return financeFtDictTypeMapper.updateFinanceFtDictType(financeFtDictType);
    }

    /**
     * 批量删除选单设定
     *
     * @param dictIds 需要删除的选单设定主键
     * @return 结果
     */
    @Override
    public int deleteFinanceFtDictTypeByDictIds(String[] dictIds)
    {
        return financeFtDictTypeMapper.deleteFinanceFtDictTypeByDictIds(dictIds);
    }

    /**
     * 删除选单设定信息
     *
     * @param dictId 选单设定主键
     * @return 结果
     */
    @Override
    public int deleteFinanceFtDictTypeByDictId(String dictId)
    {
        return financeFtDictTypeMapper.deleteFinanceFtDictTypeByDictId(dictId);
    }
}
