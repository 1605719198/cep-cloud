package com.jlkj.finance.aa.service.impl;

import cn.hutool.core.util.IdUtil;
import com.jlkj.common.core.exception.ServiceException;
import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.finance.aa.domain.FinanceAaCashflowCodeCompid;
import com.jlkj.finance.aa.dto.FinanceAaCashflowCodeDTO;
import com.jlkj.finance.aa.mapper.FinanceAaCashflowCodeCompidMapper;
import com.jlkj.finance.aa.service.IFinanceAaCashflowCodeCompidService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.jlkj.common.security.utils.SecurityUtils.getUsername;

/**
 * 现金流量代码公司级Service业务层处理
 *
 * @author 265799
 * @date 2023-05-04
 */
@Service
public class FinanceAaCashflowCodeCompidServiceImpl implements IFinanceAaCashflowCodeCompidService
{
    @Autowired
    private FinanceAaCashflowCodeCompidMapper financeAaCashflowCodeCompidMapper;

    /**
     * 查询现金流量代码公司级
     *
     * @param id 现金流量代码公司级主键
     * @return 现金流量代码公司级
     */
    @Override
    public FinanceAaCashflowCodeCompid selectFinanceAaCashflowCodeCompidById(String id)
    {
        return financeAaCashflowCodeCompidMapper.selectFinanceAaCashflowCodeCompidById(id);
    }

    /**
     * 查询现金流量代码公司级列表
     *
     * @param financeAaCashflowCodeCompid 现金流量代码公司级
     * @return 现金流量代码公司级
     */
    @Override
    public List<FinanceAaCashflowCodeCompid> selectFinanceAaCashflowCodeCompidList(FinanceAaCashflowCodeCompid financeAaCashflowCodeCompid)
    {
        return financeAaCashflowCodeCompidMapper.selectFinanceAaCashflowCodeCompidList(financeAaCashflowCodeCompid);
    }

    /**
     * 新增现金流量代码公司级
     *
     * @param projectIds 现金流量代码
     * @return 结果
     */
    @Override
    public int insertFinanceAaCashflowCodeCompid( List<FinanceAaCashflowCodeDTO> projectIds)
    {
        int insertFinanceAaCashflowCode = 0;
        for ( FinanceAaCashflowCodeDTO financeAaCashflowCodeDTO:projectIds){
            FinanceAaCashflowCodeCompid financeAaCashflowCodeCompid = new FinanceAaCashflowCodeCompid();
            if (!StringUtils.isEmpty(financeAaCashflowCodeDTO.getId())){
                financeAaCashflowCodeDTO.setUpdateTime(DateUtils.getNowDate());
                financeAaCashflowCodeDTO.setUpdateBy(getUsername());
                BeanUtils.copyProperties(financeAaCashflowCodeDTO,financeAaCashflowCodeCompid);
                insertFinanceAaCashflowCode =  financeAaCashflowCodeCompidMapper
                        .updateFinanceAaCashflowCodeCompid(financeAaCashflowCodeCompid);
            }else {
                financeAaCashflowCodeDTO.setCreateTime(DateUtils.getNowDate());
                financeAaCashflowCodeDTO.setCreateBy(getUsername());
                financeAaCashflowCodeDTO.setId(IdUtil.randomUUID());
                BeanUtils.copyProperties(financeAaCashflowCodeDTO,financeAaCashflowCodeCompid);
                List<FinanceAaCashflowCodeCompid> financeAaCashflowCodeCompids = financeAaCashflowCodeCompidMapper.selectFinanceAaCashflowCodeCompidList(financeAaCashflowCodeCompid);
                if (financeAaCashflowCodeCompids.size()>0){
                    throw new ServiceException("现金流量代码已存在!");
                }

                insertFinanceAaCashflowCode = financeAaCashflowCodeCompidMapper
                        .insertFinanceAaCashflowCodeCompid(financeAaCashflowCodeCompid);
            }
        }
        return insertFinanceAaCashflowCode;
    }

    /**
     * 修改现金流量代码公司级
     *
     * @param financeAaCashflowCodeCompid 现金流量代码公司级
     * @return 结果
     */
    @Override
    public int updateFinanceAaCashflowCodeCompid(FinanceAaCashflowCodeCompid financeAaCashflowCodeCompid)
    {
        financeAaCashflowCodeCompid.setUpdateTime(DateUtils.getNowDate());
        return financeAaCashflowCodeCompidMapper.updateFinanceAaCashflowCodeCompid(financeAaCashflowCodeCompid);
    }

    /**
     * 批量删除现金流量代码公司级
     *
     * @param ids 需要删除的现金流量代码公司级主键
     * @return 结果
     */
    @Override
    public int deleteFinanceAaCashflowCodeCompidByIds(String[] ids)
    {
        return financeAaCashflowCodeCompidMapper.deleteFinanceAaCashflowCodeCompidByIds(ids);
    }

    /**
     * 删除现金流量代码公司级信息
     *
     * @param id 现金流量代码公司级主键
     * @return 结果
     */
    @Override
    public int deleteFinanceAaCashflowCodeCompidById(String id)
    {
        return financeAaCashflowCodeCompidMapper.deleteFinanceAaCashflowCodeCompidById(id);
    }
}
