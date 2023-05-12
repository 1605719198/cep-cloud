package com.jlkj.finance.aa.service.impl;

import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.common.core.utils.uuid.IdUtils;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.finance.aa.domain.financeAaDeprmethod;
import com.jlkj.finance.aa.service.IfinanceAaDeprmethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 折旧方法维护Service业务层处理
 *
 * @author SunXuTong
 * @date 2023-05-10
 */
@Service
public class financeAaDeprmethodServiceImpl implements IfinanceAaDeprmethodService
{
    @Autowired
    private com.jlkj.finance.aa.mapper.financeAaDeprmethodMapper financeAaDeprmethodMapper;

    /**
     * 查询折旧方法维护
     *
     * @param id 折旧方法维护主键
     * @return 折旧方法维护
     */
    @Override
    public financeAaDeprmethod selectfinanceAaDeprmethodById(String id)
    {
        return financeAaDeprmethodMapper.selectfinanceAaDeprmethodById(id);
    }

    /**
     * 查询折旧方法维护列表
     *
     * @param financeAaDeprmethod 折旧方法维护
     * @return 折旧方法维护
     */
    @Override
    public List<financeAaDeprmethod> selectfinanceAaDeprmethodList(financeAaDeprmethod financeAaDeprmethod)
    {
        return financeAaDeprmethodMapper.selectfinanceAaDeprmethodList(financeAaDeprmethod);
    }

    /**
     * 新增折旧方法维护
     *
     * @param financeAaDeprmethod 折旧方法维护
     * @return 结果
     */
    @Override
    public int insertfinanceAaDeprmethod(financeAaDeprmethod financeAaDeprmethod)
    {
        financeAaDeprmethod.setId(IdUtils.fastSimpleUUID());
        financeAaDeprmethod.setCreateTime(DateUtils.getNowDate());
        financeAaDeprmethod.setCreateBy(SecurityUtils.getUsername());
        financeAaDeprmethod.setCreateName(SecurityUtils.getNickName());
        return financeAaDeprmethodMapper.insertfinanceAaDeprmethod(financeAaDeprmethod);
    }

    /**
     * 修改折旧方法维护
     *
     * @param financeAaDeprmethod 折旧方法维护
     * @return 结果
     */
    @Override
    public int updatefinanceAaDeprmethod(financeAaDeprmethod financeAaDeprmethod)
    {
        financeAaDeprmethod.setUpdateTime(DateUtils.getNowDate());
        financeAaDeprmethod.setUpdateBy(SecurityUtils.getUsername());
        financeAaDeprmethod.setUpdateName(SecurityUtils.getNickName());
        return financeAaDeprmethodMapper.updatefinanceAaDeprmethod(financeAaDeprmethod);
    }

    /**
     * 批量删除折旧方法维护
     *
     * @param ids 需要删除的折旧方法维护主键
     * @return 结果
     */
    @Override
    public int deletefinanceAaDeprmethodByIds(String[] ids)
    {
        return financeAaDeprmethodMapper.deletefinanceAaDeprmethodByIds(ids);
    }

    /**
     * 删除折旧方法维护信息
     *
     * @param id 折旧方法维护主键
     * @return 结果
     */
    @Override
    public int deletefinanceAaDeprmethodById(String id)
    {
        return financeAaDeprmethodMapper.deletefinanceAaDeprmethodById(id);
    }
}
