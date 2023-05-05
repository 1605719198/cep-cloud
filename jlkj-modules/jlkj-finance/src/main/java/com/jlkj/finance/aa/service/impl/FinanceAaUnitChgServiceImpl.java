package com.jlkj.finance.aa.service.impl;

import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.common.core.utils.uuid.IdUtils;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.finance.aa.domain.FinanceAaUnitChg;
import com.jlkj.finance.aa.mapper.FinanceAaUnitChgMapper;
import com.jlkj.finance.aa.service.IFinanceAaUnitChgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 计量单位转换Service业务层处理
 *
 * @author SunXuTong
 * @date 2023-05-04
 */
@Service
public class FinanceAaUnitChgServiceImpl implements IFinanceAaUnitChgService
{
    @Autowired
    private FinanceAaUnitChgMapper financeAaUnitChgMapper;

    /**
     * 查询计量单位转换
     *
     * @param id 计量单位转换主键
     * @return 计量单位转换
     */
    @Override
    public FinanceAaUnitChg selectFinanceAaUnitChgById(String id)
    {
        return financeAaUnitChgMapper.selectFinanceAaUnitChgById(id);
    }

    /**
     * 查询计量单位转换列表
     *
     * @param financeAaUnitChg 计量单位转换
     * @return 计量单位转换
     */
    @Override
    public List<FinanceAaUnitChg> selectFinanceAaUnitChgList(FinanceAaUnitChg financeAaUnitChg)
    {
        return financeAaUnitChgMapper.selectFinanceAaUnitChgList(financeAaUnitChg);
    }

    /**
     * 新增计量单位转换
     *
     * @param financeAaUnitChg 计量单位转换
     * @return 结果
     */
    @Override
    public int insertFinanceAaUnitChg(FinanceAaUnitChg financeAaUnitChg)
    {
        financeAaUnitChg.setId(IdUtils.fastSimpleUUID());
        financeAaUnitChg.setCreateTime(DateUtils.getNowDate());
        financeAaUnitChg.setCreateBy(SecurityUtils.getUsername());
        financeAaUnitChg.setCreateName(SecurityUtils.getNickName());
        return financeAaUnitChgMapper.insertFinanceAaUnitChg(financeAaUnitChg);
    }

    /**
     * 修改计量单位转换
     *
     * @param financeAaUnitChg 计量单位转换
     * @return 结果
     */
    @Override
    public int updateFinanceAaUnitChg(FinanceAaUnitChg financeAaUnitChg)
    {
        financeAaUnitChg.setUpdateTime(DateUtils.getNowDate());
        financeAaUnitChg.setUpdateBy(SecurityUtils.getUsername());
        financeAaUnitChg.setUpdateName(SecurityUtils.getNickName());
        return financeAaUnitChgMapper.updateFinanceAaUnitChg(financeAaUnitChg);
    }

    /**
     * 批量删除计量单位转换
     *
     * @param ids 需要删除的计量单位转换主键
     * @return 结果
     */
    @Override
    public int deleteFinanceAaUnitChgByIds(String[] ids)
    {
        return financeAaUnitChgMapper.deleteFinanceAaUnitChgByIds(ids);
    }

    /**
     * 删除计量单位转换信息
     *
     * @param id 计量单位转换主键
     * @return 结果
     */
    @Override
    public int deleteFinanceAaUnitChgById(String id)
    {
        return financeAaUnitChgMapper.deleteFinanceAaUnitChgById(id);
    }
}
