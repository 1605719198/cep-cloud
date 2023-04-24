package com.jlkj.finance.aa.service.impl;

import java.security.Security;
import java.util.List;
import java.util.Map;

import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.common.core.utils.uuid.IdUtils;
import com.jlkj.common.security.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jlkj.finance.aa.mapper.FinanceVoucherTypeMapper;
import com.jlkj.finance.aa.domain.FinanceVoucherType;
import com.jlkj.finance.aa.service.IFinanceVoucherTypeService;

/**
 * 凭证类别维护Service业务层处理
 *
 * @author 116514
 * @date 2023-04-13
 */
@Service
public class FinanceVoucherTypeServiceImpl implements IFinanceVoucherTypeService
{
    @Autowired
    private FinanceVoucherTypeMapper financeVoucherTypeMapper;

    /**
     * 查询凭证类别维护
     *
     * @param id 凭证类别维护主键
     * @return 凭证类别维护
     */
    @Override
    public FinanceVoucherType selectFinanceVoucherTypeById(String id)
    {
        return financeVoucherTypeMapper.selectFinanceVoucherTypeById(id);
    }

    /**
     * 查询凭证类别维护列表
     *
     * @param financeVoucherType 凭证类别维护
     * @return 凭证类别维护
     */
    @Override
    public List<FinanceVoucherType> selectFinanceVoucherTypeList(FinanceVoucherType financeVoucherType)
    {
        return financeVoucherTypeMapper.selectFinanceVoucherTypeList(financeVoucherType);
    }

    /**
     * 新增凭证类别维护
     *
     * @param financeVoucherType 凭证类别维护
     * @return 结果
     */
    @Override
    public int insertFinanceVoucherType(FinanceVoucherType financeVoucherType)
    {
        financeVoucherType.setId(IdUtils.simpleUUID());
        financeVoucherType.setCreateBy(SecurityUtils.getUsername());
        financeVoucherType.setCreateName(SecurityUtils.getNickName());
        financeVoucherType.setCreateTime(DateUtils.getNowDate());
        return financeVoucherTypeMapper.insertFinanceVoucherType(financeVoucherType);
    }

    /**
     * 修改凭证类别维护
     *
     * @param financeVoucherType 凭证类别维护
     * @return 结果
     */
    @Override
    public int updateFinanceVoucherType(FinanceVoucherType financeVoucherType)
    {
        financeVoucherType.setUpdateBy(SecurityUtils.getUsername());
        financeVoucherType.setUpdateName(SecurityUtils.getNickName());
        financeVoucherType.setUpdateTime(DateUtils.getNowDate());
        return financeVoucherTypeMapper.updateFinanceVoucherType(financeVoucherType);
    }

    /**
     * 批量删除凭证类别维护
     *
     * @param ids 需要删除的凭证类别维护主键
     * @return 结果
     */
    @Override
    public int deleteFinanceVoucherTypeByIds(String[] ids)
    {
        return financeVoucherTypeMapper.deleteFinanceVoucherTypeByIds(ids);
    }

    /**
     * 删除凭证类别维护信息
     *
     * @param id 凭证类别维护主键
     * @return 结果
     */
    @Override
    public int deleteFinanceVoucherTypeById(String id)
    {
        return financeVoucherTypeMapper.deleteFinanceVoucherTypeById(id);
    }

    /**
     * 查询凭证类别列表(下拉选单用)
     * @return
     */
    @Override
    public List<Map<String,String>> selectVoucherTypeList(){
        return financeVoucherTypeMapper.selectVoucherTypeList();
    }
}
