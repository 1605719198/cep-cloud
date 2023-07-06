package com.jlkj.finance.ft.service.impl;

import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.common.core.utils.uuid.IdUtils;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.finance.ft.domain.FinanceFtAcctType;
import com.jlkj.finance.ft.mapper.FinanceFtAcctTypeMapper;
import com.jlkj.finance.ft.service.IFinanceFtAcctTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 账务代码设定Service业务层处理
 *
 * @author SunXuTong
 * @date 2023-06-27
 */
@Service
public class FinanceFtAcctTypeServiceImpl implements IFinanceFtAcctTypeService
{
    @Autowired
    private FinanceFtAcctTypeMapper financeFtAcctTypeMapper;

    /**
     * 查询账务代码设定
     *
     * @param uuid 账务代码设定主键
     * @return 账务代码设定
     */
    @Override
    public FinanceFtAcctType selectFinanceFtAcctTypeByUuid(String uuid)
    {
        return financeFtAcctTypeMapper.selectFinanceFtAcctTypeByUuid(uuid);
    }

    /**
     * 查询账务代码设定列表
     *
     * @param financeFtAcctType 账务代码设定
     * @return 账务代码设定x
     */
    @Override
    public List<FinanceFtAcctType> selectFinanceFtAcctTypeList(FinanceFtAcctType financeFtAcctType)
    {
        return financeFtAcctTypeMapper.selectFinanceFtAcctTypeList(financeFtAcctType);
    }

    /**
     * 新增账务代码设定
     *
     * @param financeFtAcctType 账务代码设定
     * @return 结果
     */
    @Override
    public int insertFinanceFtAcctType(FinanceFtAcctType financeFtAcctType)
    {
        financeFtAcctType.setUuid(IdUtils.fastSimpleUUID());
        financeFtAcctType.setCreateTime(DateUtils.getNowDate());
        financeFtAcctType.setCreateBy(SecurityUtils.getUsername());
        financeFtAcctType.setCreateName(SecurityUtils.getNickName());
        return financeFtAcctTypeMapper.insertFinanceFtAcctType(financeFtAcctType);
    }

    /**
     * 修改账务代码设定
     *
     * @param financeFtAcctType 账务代码设定
     * @return 结果
     */
    @Override
    public int updateFinanceFtAcctType(FinanceFtAcctType financeFtAcctType)
    {
        financeFtAcctType.setUpdateTime(DateUtils.getNowDate());
        financeFtAcctType.setUpdateBy(SecurityUtils.getUsername());
        financeFtAcctType.setUpdateName(SecurityUtils.getNickName());
        return financeFtAcctTypeMapper.updateFinanceFtAcctType(financeFtAcctType);
    }

    /**
     * 批量删除账务代码设定
     *
     * @param uuids 需要删除的账务代码设定主键
     * @return 结果
     */
    @Override
    public int deleteFinanceFtAcctTypeByUuids(String[] uuids)
    {
        return financeFtAcctTypeMapper.deleteFinanceFtAcctTypeByUuids(uuids);
    }

    /**
     * 删除账务代码设定信息
     *
     * @param uuid 账务代码设定主键
     * @return 结果
     */
    @Override
    public int deleteFinanceFtAcctTypeByUuid(String uuid)
    {
        return financeFtAcctTypeMapper.deleteFinanceFtAcctTypeByUuid(uuid);
    }
}
