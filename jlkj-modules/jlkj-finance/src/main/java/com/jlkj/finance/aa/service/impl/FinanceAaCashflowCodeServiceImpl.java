package com.jlkj.finance.aa.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jlkj.common.core.mapper.BaseMapperPlus;
import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.finance.aa.dto.FinanceAaCashflowCodeDTO;
import com.jlkj.finance.ac.domain.FinanceCostCode;
import com.jlkj.finance.ac.domain.FinanceCostTree;
import com.jlkj.finance.gp.domain.ManufacturerTree;
import com.jlkj.finance.gp.dto.ManufacturerTreeDTO;
import com.jlkj.finance.ip.dto.FinanceCostDownTypeTreeDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jlkj.finance.aa.mapper.FinanceAaCashflowCodeMapper;
import com.jlkj.finance.aa.domain.FinanceAaCashflowCode;
import com.jlkj.finance.aa.service.IFinanceAaCashflowCodeService;

import static com.jlkj.common.security.utils.SecurityUtils.getUsername;

/**
 * 现金流量代码Service业务层处理
 *
 * @author 265799
 * @date 2023-04-17
 */
@Service
public class FinanceAaCashflowCodeServiceImpl implements
        IFinanceAaCashflowCodeService
{
    @Autowired
    private FinanceAaCashflowCodeMapper financeAaCashflowCodeMapper;


    /**
     * 查询现金流量代码
     *
     * @param id 现金流量代码主键
     * @return 现金流量代码
     */
    @Override
    public FinanceAaCashflowCode selectFinanceAaCashflowCodeById(String id)
    {
        return financeAaCashflowCodeMapper.selectFinanceAaCashflowCodeById(id);
    }

    /**
     * 查询现金流量代码列表
     *
     * @param financeAaCashflowCode 现金流量代码
     * @return 现金流量代码
     */
    @Override
    public List<FinanceAaCashflowCode> selectFinanceAaCashflowCodeList(FinanceAaCashflowCode financeAaCashflowCode)
    {
        return financeAaCashflowCodeMapper.selectFinanceAaCashflowCodeList(financeAaCashflowCode);
    }

    /**
     * 新增现金流量代码
     *
     * @param projectIds 现金流量代码
     * @return 结果
     */
    @Override
    public int insertFinanceAaCashflowCode( List<FinanceAaCashflowCodeDTO> projectIds)
    {
        int insertFinanceAaCashflowCode = 0;
        for ( FinanceAaCashflowCodeDTO financeAaCashflowCodeDTO:projectIds){
            FinanceAaCashflowCode financeAaCashflowCode = new FinanceAaCashflowCode();
            if (financeAaCashflowCodeDTO.getId()!=null){
                financeAaCashflowCodeDTO.setUpdateTime(DateUtils.getNowDate());
                financeAaCashflowCodeDTO.setUpdateBy(getUsername());
                BeanUtils.copyProperties(financeAaCashflowCodeDTO,financeAaCashflowCode);
                insertFinanceAaCashflowCode =  financeAaCashflowCodeMapper.updateFinanceAaCashflowCode(financeAaCashflowCode);
            }else {
                financeAaCashflowCodeDTO.setCreateTime(DateUtils.getNowDate());
                financeAaCashflowCodeDTO.setCreateBy(getUsername());
                financeAaCashflowCodeDTO.setId(IdUtil.randomUUID());


                BeanUtils.copyProperties(financeAaCashflowCodeDTO,financeAaCashflowCode);
                insertFinanceAaCashflowCode = financeAaCashflowCodeMapper.insertFinanceAaCashflowCode(financeAaCashflowCode);
            }
        }
        return insertFinanceAaCashflowCode ;
    }

    /**
     * 修改现金流量代码
     *
     * @param financeAaCashflowCode 现金流量代码
     * @return 结果
     */
    @Override
    public int updateFinanceAaCashflowCode(FinanceAaCashflowCode financeAaCashflowCode)
    {
        financeAaCashflowCode.setUpdateTime(DateUtils.getNowDate());
        return financeAaCashflowCodeMapper.updateFinanceAaCashflowCode(financeAaCashflowCode);
    }

    /**
     * 批量删除现金流量代码
     *
     * @param ids 需要删除的现金流量代码主键
     * @return 结果
     */
    @Override
    public int deleteFinanceAaCashflowCodeByIds(String[] ids)
    {
        return financeAaCashflowCodeMapper.deleteFinanceAaCashflowCodeByIds(ids);
    }

    /**
     * 删除现金流量代码信息
     *
     * @param id 现金流量代码主键
     * @return 结果
     */
    @Override
    public int deleteFinanceAaCashflowCodeById(String id)
    {
        return financeAaCashflowCodeMapper.deleteFinanceAaCashflowCodeById(id);
    }
}
