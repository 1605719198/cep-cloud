package com.jlkj.finance.ft.service.impl;

import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.common.core.utils.uuid.IdUtils;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.finance.ft.domain.FinanceFtPara;
import com.jlkj.finance.ft.mapper.FinanceFtParaMapper;
import com.jlkj.finance.ft.service.IFinanceFtParaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 固定资产参数维护Service业务层处理
 *
 * @author SunXuTong
 * @date 2023-06-13
 */
@Service
public class FinanceFtParaServiceImpl implements IFinanceFtParaService
{
    @Autowired
    private FinanceFtParaMapper financeFtParaMapper;

    /**
     * 查询固定资产参数维护
     *
     * @param id 固定资产参数维护主键
     * @return 固定资产参数维护
     */
    @Override
    public FinanceFtPara selectFinanceFtParaById(String id)
    {
        return financeFtParaMapper.selectFinanceFtParaById(id);
    }

    /**
     * 查询固定资产参数维护列表
     *
     * @param financeFtPara 固定资产参数维护
     * @return 固定资产参数维护
     */
    @Override
    public List<FinanceFtPara> selectFinanceFtParaList(FinanceFtPara financeFtPara)
    {
        return financeFtParaMapper.selectFinanceFtParaList(financeFtPara);
    }

    /**
     * 新增固定资产参数维护
     *
     * @param financeFtPara 固定资产参数维护
     * @return 结果
     */
    @Override
    public int insertFinanceFtPara(FinanceFtPara financeFtPara)
    {
        financeFtPara.setId(IdUtils.fastSimpleUUID());
        financeFtPara.setCreateTime(DateUtils.getNowDate());
        financeFtPara.setCreateBy(SecurityUtils.getUsername());
        financeFtPara.setCreateName(SecurityUtils.getNickName());
        return financeFtParaMapper.insertFinanceFtPara(financeFtPara);
    }

    /**
     * 修改固定资产参数维护
     *
     * @param financeFtPara 固定资产参数维护
     * @return 结果
     */
    @Override
    public int updateFinanceFtPara(FinanceFtPara financeFtPara)
    {
        financeFtPara.setUpdateTime(DateUtils.getNowDate());
        financeFtPara.setUpdateBy(SecurityUtils.getUsername());
        financeFtPara.setUpdateName(SecurityUtils.getNickName());
        return financeFtParaMapper.updateFinanceFtPara(financeFtPara);
    }

    /**
     * 批量删除固定资产参数维护
     *
     * @param ids 需要删除的固定资产参数维护主键
     * @return 结果
     */
    @Override
    public int deleteFinanceFtParaByIds(String[] ids)
    {
        return financeFtParaMapper.deleteFinanceFtParaByIds(ids);
    }

    /**
     * 删除固定资产参数维护信息
     *
     * @param id 固定资产参数维护主键
     * @return 结果
     */
    @Override
    public int deleteFinanceFtParaById(String id)
    {
        return financeFtParaMapper.deleteFinanceFtParaById(id);
    }
}
