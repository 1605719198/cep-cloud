package com.jlkj.finance.aa.service.impl;

import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.common.core.utils.uuid.IdUtils;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.finance.aa.domain.FinanceAaDeprmethod;
import com.jlkj.finance.aa.service.IFinanceAaDeprmethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 折旧方法维护Service业务层处理
 *
 * @author SunXuTong
 * @date 2023-05-10
 */
@Service
public class FinanceAaDeprmethodServiceImpl implements IFinanceAaDeprmethodService
{
    @Autowired
    private com.jlkj.finance.aa.mapper.FinanceAaDeprmethodMapper financeAaDeprmethodMapper;

    /**
     * 查询折旧方法维护
     *
     * @param id 折旧方法维护主键
     * @return 折旧方法维护
     */
    @Override
    public FinanceAaDeprmethod selectFinanceAaDeprmethodById(String id)
    {
        return financeAaDeprmethodMapper.selectFinanceAaDeprmethodById(id);
    }

    /**
     * 查询折旧方法维护列表
     *
     * @param financeAaDeprmethod 折旧方法维护
     * @return 折旧方法维护
     */
    @Override
    public List<FinanceAaDeprmethod> selectFinanceAaDeprmethodList(FinanceAaDeprmethod financeAaDeprmethod)
    {
        return financeAaDeprmethodMapper.selectFinanceAaDeprmethodList(financeAaDeprmethod);
    }

    /**
     * 新增折旧方法维护
     *
     * @param financeAaDeprmethod 折旧方法维护
     * @return 结果
     */
    @Override
    public int insertFinanceAaDeprmethod(FinanceAaDeprmethod financeAaDeprmethod)
    {
        financeAaDeprmethod.setId(IdUtils.fastSimpleUUID());
        financeAaDeprmethod.setCreateTime(DateUtils.getNowDate());
        financeAaDeprmethod.setCreateBy(SecurityUtils.getUsername());
        financeAaDeprmethod.setCreateName(SecurityUtils.getNickName());
        return financeAaDeprmethodMapper.insertFinanceAaDeprmethod(financeAaDeprmethod);
    }

    /**
     * 修改折旧方法维护
     *
     * @param financeAaDeprmethod 折旧方法维护
     * @return 结果
     */
    @Override
    public int updateFinanceAaDeprmethod(FinanceAaDeprmethod financeAaDeprmethod)
    {
        financeAaDeprmethod.setUpdateTime(DateUtils.getNowDate());
        financeAaDeprmethod.setUpdateBy(SecurityUtils.getUsername());
        financeAaDeprmethod.setUpdateName(SecurityUtils.getNickName());
        return financeAaDeprmethodMapper.updateFinanceAaDeprmethod(financeAaDeprmethod);
    }

    /**
     * 批量删除折旧方法维护
     *
     * @param ids 需要删除的折旧方法维护主键
     * @return 结果
     */
    @Override
    public int deleteFinanceAaDeprmethodByIds(String[] ids)
    {
        return financeAaDeprmethodMapper.deleteFinanceAaDeprmethodByIds(ids);
    }

    /**
     * 删除折旧方法维护信息
     *
     * @param id 折旧方法维护主键
     * @return 结果
     */
    @Override
    public int deleteFinanceAaDeprmethodById(String id)
    {
        return financeAaDeprmethodMapper.deleteFinanceAaDeprmethodById(id);
    }

    /**
     * 查询折旧方法（下拉选单用）
     * @return
     */
    @Override
    public List<Map<String,String>> selectDeprMethodList(){
        return financeAaDeprmethodMapper.selectDeprMethodList();
    }
}
