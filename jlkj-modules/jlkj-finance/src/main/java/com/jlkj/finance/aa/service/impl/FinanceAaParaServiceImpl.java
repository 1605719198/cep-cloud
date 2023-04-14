package com.jlkj.finance.aa.service.impl;

import java.util.List;

import com.jlkj.common.core.exception.ServiceException;
import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.common.core.utils.uuid.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jlkj.finance.aa.mapper.FinanceAaParaMapper;
import com.jlkj.finance.aa.domain.FinanceAaPara;
import com.jlkj.finance.aa.service.IFinanceAaParaService;

/**
 * 会计系统参数维护Service业务层处理
 *
 * @author 265799
 * @date 2023-04-13
 */
@Service
public class FinanceAaParaServiceImpl implements IFinanceAaParaService
{
    @Autowired
    private FinanceAaParaMapper financeAaParaMapper;

    /**
     * 查询会计系统参数维护
     *
     * @param id 会计系统参数维护主键
     * @return 会计系统参数维护
     */
    @Override
    public FinanceAaPara selectFinanceAaParaById(String id)
    {
        return financeAaParaMapper.selectFinanceAaParaById(id);
    }

    /**
     * 查询会计系统参数维护列表
     *
     * @param financeAaPara 会计系统参数维护
     * @return 会计系统参数维护
     */
    @Override
    public List<FinanceAaPara> selectFinanceAaParaList(FinanceAaPara financeAaPara)
    {
        return financeAaParaMapper.selectFinanceAaParaList(financeAaPara);
    }

    /**
     * 新增会计系统参数维护
     *
     * @param financeAaPara 会计系统参数维护
     * @return 结果
     */
    @Override
    public int insertFinanceAaPara(FinanceAaPara financeAaPara)
    {
        financeAaPara.setCreateTime(DateUtils.getNowDate());
        financeAaPara.setId(UUID.fastUUID().toString());
        FinanceAaPara financeAaPara1 = new FinanceAaPara();
        financeAaPara1.setCompanyId(financeAaPara.getCompanyId());
        financeAaPara1.setParameterKey(financeAaPara.getParameterKey());
        List<FinanceAaPara> financeAaParas = financeAaParaMapper.selectFinanceAaParaList(financeAaPara1);
        if (financeAaParas.size()>0){
            throw new ServiceException("该系统参数已存在！");
        }
        return financeAaParaMapper.insertFinanceAaPara(financeAaPara);
    }

    /**
     * 修改会计系统参数维护
     *
     * @param financeAaPara 会计系统参数维护
     * @return 结果
     */
    @Override
    public int updateFinanceAaPara(FinanceAaPara financeAaPara)
    {
        financeAaPara.setUpdateTime(DateUtils.getNowDate());
        return financeAaParaMapper.updateFinanceAaPara(financeAaPara);
    }

    /**
     * 批量删除会计系统参数维护
     *
     * @param ids 需要删除的会计系统参数维护主键
     * @return 结果
     */
    @Override
    public int deleteFinanceAaParaByIds(String[] ids)
    {
        return financeAaParaMapper.deleteFinanceAaParaByIds(ids);
    }

    /**
     * 删除会计系统参数维护信息
     *
     * @param id 会计系统参数维护主键
     * @return 结果
     */
    @Override
    public int deleteFinanceAaParaById(String id)
    {
        return financeAaParaMapper.deleteFinanceAaParaById(id);
    }
}
