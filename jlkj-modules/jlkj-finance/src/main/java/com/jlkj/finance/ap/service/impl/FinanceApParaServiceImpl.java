package com.jlkj.finance.ap.service.impl;

import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.common.core.utils.uuid.IdUtils;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.finance.ap.domain.FinanceApPara;
import com.jlkj.finance.ap.mapper.FinanceApParaMapper;
import com.jlkj.finance.ap.service.IFinanceApParaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 应付参数维护Service业务层处理
 *
 * @author SunXuTong
 * @date 2023-05-15
 */
@Service
public class FinanceApParaServiceImpl implements IFinanceApParaService
{
    @Autowired
    private FinanceApParaMapper financeApParaMapper;

    /**
     * 查询应付参数维护
     *
     * @param id 应付参数维护主键
     * @return 应付参数维护
     */
    @Override
    public FinanceApPara selectFinanceApParaById(String id)
    {
        return financeApParaMapper.selectFinanceApParaById(id);
    }

    /**
     * 查询应付参数维护列表
     *
     * @param financeApPara 应付参数维护
     * @return 应付参数维护
     */
    @Override
    public List<FinanceApPara> selectFinanceApParaList(FinanceApPara financeApPara)
    {
        return financeApParaMapper.selectFinanceApParaList(financeApPara);
    }

    /**
     * 新增应付参数维护
     *
     * @param financeApPara 应付参数维护
     * @return 结果
     */
    @Override
    public int insertFinanceApPara(FinanceApPara financeApPara)
    {
        List<FinanceApPara> financeApParas = financeApParaMapper.selectFinanceApParaByCompIdAndParameterValue(financeApPara);
        if (financeApParas.isEmpty()){
            financeApPara.setId(IdUtils.fastSimpleUUID());
            financeApPara.setCreateTime(DateUtils.getNowDate());
            financeApPara.setCreateBy(SecurityUtils.getUsername());
            financeApPara.setCreateName(SecurityUtils.getNickName());
            return financeApParaMapper.insertFinanceApPara(financeApPara);
        } else {
            return 0;
        }
    }

    /**
     * 修改应付参数维护
     *
     * @param financeApPara 应付参数维护
     * @return 结果
     */
    @Override
    public int updateFinanceApPara(FinanceApPara financeApPara)
    {
        financeApPara.setUpdateTime(DateUtils.getNowDate());
        financeApPara.setUpdateBy(SecurityUtils.getUsername());
        financeApPara.setUpdateName(SecurityUtils.getNickName());
        return financeApParaMapper.updateFinanceApPara(financeApPara);
    }

    /**
     * 批量删除应付参数维护
     *
     * @param ids 需要删除的应付参数维护主键
     * @return 结果
     */
    @Override
    public int deleteFinanceApParaByIds(String[] ids)
    {
        return financeApParaMapper.deleteFinanceApParaByIds(ids);
    }

    /**
     * 删除应付参数维护信息
     *
     * @param id 应付参数维护主键
     * @return 结果
     */
    @Override
    public int deleteFinanceApParaById(String id)
    {
        return financeApParaMapper.deleteFinanceApParaById(id);
    }
}
