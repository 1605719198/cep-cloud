package com.jlkj.finance.aa.service.impl;

import java.util.List;

import cn.hutool.core.util.IdUtil;
import com.jlkj.common.core.utils.DateUtils;

import com.jlkj.finance.aa.dto.FinanceAaDetailDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jlkj.finance.aa.mapper.FinanceAaDetailMapper;
import com.jlkj.finance.aa.domain.FinanceAaDetail;
import com.jlkj.finance.aa.service.IFinanceAaDetailService;

import static com.jlkj.common.security.utils.SecurityUtils.getUsername;

/**
 * 系统选单-明细设定Service业务层处理
 *
 * @author 265799
 * @date 2023-04-19
 */
@Service
public class FinanceAaDetailServiceImpl implements IFinanceAaDetailService
{
    @Autowired
    private FinanceAaDetailMapper financeAaDetailMapper;

    /**
     * 查询系统选单-明细设定
     *
     * @param id 系统选单-明细设定主键
     * @return 系统选单-明细设定
     */
    @Override
    public FinanceAaDetail selectFinanceAaDetailById(String id)
    {
        return financeAaDetailMapper.selectFinanceAaDetailById(id);
    }

    /**
     * 查询系统选单-明细设定列表
     *
     * @param financeAaDetail 系统选单-明细设定
     * @return 系统选单-明细设定
     */
    @Override
    public List<FinanceAaDetail> selectFinanceAaDetailList(FinanceAaDetail financeAaDetail)
    {
        return financeAaDetailMapper.selectFinanceAaDetailList(financeAaDetail);
    }

    /**
     * 新增系统选单-明细设定
     *
     * @param detailList 系统选单-明细设定
     * @return 结果
     */
    @Override
    public int insertFinanceAaDetail( List<FinanceAaDetailDTO> detailList)
    {
        int insertFinanceAaCashflowCode = 0;
        for ( FinanceAaDetailDTO financeAaDetailDTO:detailList){
            FinanceAaDetail financeAaDetail = new FinanceAaDetail();
            financeAaDetailDTO.setCreateTime(DateUtils.getNowDate());
            financeAaDetailDTO.setCreateBy(getUsername());
            financeAaDetailDTO.setId(IdUtil.randomUUID());
            BeanUtils.copyProperties(financeAaDetailDTO,financeAaDetail);
                insertFinanceAaCashflowCode = financeAaDetailMapper.insertFinanceAaDetail(financeAaDetail);
            }

        return insertFinanceAaCashflowCode;
    }

    /**
     * 修改系统选单-明细设定
     *
     * @param financeAaDetail 系统选单-明细设定
     * @return 结果
     */
    @Override
    public int updateFinanceAaDetail(FinanceAaDetail financeAaDetail)
    {
        financeAaDetail.setUpdateTime(DateUtils.getNowDate());
        financeAaDetail.setUpdateBy(getUsername());

        return financeAaDetailMapper.updateFinanceAaDetail(financeAaDetail);
    }

    /**
     * 批量删除系统选单-明细设定
     *
     * @param id 需要删除的系统选单-明细设定主键
     * @return 结果
     */
    @Override
    public int deleteFinanceAaDetailByIds(String[] id)
    {
        return financeAaDetailMapper.deleteFinanceAaDetailByIds(id);
    }

    /**
     * 删除系统选单-明细设定信息
     *
     * @param id 系统选单-明细设定主键
     * @return 结果
     */
    @Override
    public int deleteFinanceAaDetailById(String id)
    {
        return financeAaDetailMapper.deleteFinanceAaDetailById(id);
    }
}
