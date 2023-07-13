package com.jlkj.finance.ap.service.impl;

import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.common.core.utils.uuid.IdUtils;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.finance.ap.domain.FinanceApItemDetailCompany;
import com.jlkj.finance.ap.mapper.FinanceApItemDetailCompanyMapper;
import com.jlkj.finance.ap.service.IFinanceApItemDetailCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 报支类别公司细项Service业务层处理
 *
 * @author SunXuTong
 * @date 2023-06-08
 */
@Service
public class FinanceApItemDetailCompanyServiceImpl implements IFinanceApItemDetailCompanyService
{
    @Autowired
    private FinanceApItemDetailCompanyMapper financeApItemDetailCompanyMapper;

    /**
     * 查询报支类别公司细项
     *
     * @param id 报支类别公司细项主键
     * @return 报支类别公司细项
     */
    @Override
    public FinanceApItemDetailCompany selectFinanceApItemDetailCompanyById(String id)
    {
        return financeApItemDetailCompanyMapper.selectFinanceApItemDetailCompanyById(id);
    }

    /**
     * 查询报支类别公司细项列表
     *
     * @param financeApItemDetailCompany 报支类别公司细项
     * @return 报支类别公司细项
     */
    @Override
    public List<FinanceApItemDetailCompany> selectFinanceApItemDetailCompanyList(FinanceApItemDetailCompany financeApItemDetailCompany)
    {
        return financeApItemDetailCompanyMapper.selectFinanceApItemDetailCompanyList(financeApItemDetailCompany);
    }

    /**
     * 新增报支类别公司细项
     *
     * @param financeApItemDetailCompany 报支类别公司细项
     * @return 结果
     */
    @Override
    public int insertFinanceApItemDetailCompany(FinanceApItemDetailCompany financeApItemDetailCompany)
    {
        financeApItemDetailCompany.setId(IdUtils.fastSimpleUUID());
        financeApItemDetailCompany.setCreateTime(DateUtils.getNowDate());
        financeApItemDetailCompany.setCreateBy(SecurityUtils.getUsername());
        financeApItemDetailCompany.setCreateName(SecurityUtils.getNickName());
        return financeApItemDetailCompanyMapper.insertFinanceApItemDetailCompany(financeApItemDetailCompany);
    }

    /**
     * 修改报支类别公司细项
     *
     * @param financeApItemDetailCompany 报支类别公司细项
     * @return 结果
     */
    @Override
    public int updateFinanceApItemDetailCompany(FinanceApItemDetailCompany financeApItemDetailCompany)
    {
        financeApItemDetailCompany.setUpdateTime(DateUtils.getNowDate());
        financeApItemDetailCompany.setUpdateBy(SecurityUtils.getUsername());
        financeApItemDetailCompany.setUpdateName(SecurityUtils.getNickName());
        return financeApItemDetailCompanyMapper.updateFinanceApItemDetailCompany(financeApItemDetailCompany);
    }

    /**
     * 批量删除报支类别公司细项
     *
     * @param ids 需要删除的报支类别公司细项主键
     * @return 结果
     */
    @Override
    public int deleteFinanceApItemDetailCompanyByIds(String[] ids)
    {
        return financeApItemDetailCompanyMapper.deleteFinanceApItemDetailCompanyByIds(ids);
    }

    /**
     * 删除报支类别公司细项信息
     *
     * @param id 报支类别公司细项主键
     * @return 结果
     */
    @Override
    public int deleteFinanceApItemDetailCompanyById(String id)
    {
        return financeApItemDetailCompanyMapper.deleteFinanceApItemDetailCompanyById(id);
    }
    /**
     * 查询报支类别下拉选单
     * @return
     */
    @Override
    public List<Map<String,String>> selectItemNoList(FinanceApItemDetailCompany financeApItemDetailCompany){
        return financeApItemDetailCompanyMapper.selectItemNoList(financeApItemDetailCompany);
    }
}
