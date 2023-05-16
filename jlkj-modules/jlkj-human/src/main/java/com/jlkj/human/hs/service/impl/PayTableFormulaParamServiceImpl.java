package com.jlkj.human.hs.service.impl;

import java.security.Security;
import java.util.Date;
import java.util.List;

import com.jlkj.common.core.utils.uuid.UUID;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.human.hs.dto.PayTableFormulaParamDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jlkj.human.hs.mapper.PayTableFormulaParamMapper;
import com.jlkj.human.hs.domain.PayTableFormulaParam;
import com.jlkj.human.hs.service.IPayTableFormulaParamService;

/**
 * 公司薪资计算参数Service业务层处理
 *
 * @author 266861
 * @date 2023-05-15
 */
@Service
public class PayTableFormulaParamServiceImpl implements IPayTableFormulaParamService
{
    @Autowired
    private PayTableFormulaParamMapper payTableFormulaParamMapper;

    /**
     * 查询公司薪资计算参数
     *
     * @param uuid 公司薪资计算参数主键
     * @return 公司薪资计算参数
     */
    @Override
    public PayTableFormulaParam selectPayTableFormulaParamByUuid(String uuid)
    {
        return payTableFormulaParamMapper.selectPayTableFormulaParamByUuid(uuid);
    }

    /**
     * 查询公司薪资计算参数列表
     *
     * @param payTableFormulaParam 公司薪资计算参数
     * @return 公司薪资计算参数
     */
    @Override
    public List<PayTableFormulaParam> selectPayTableFormulaParamList(PayTableFormulaParam payTableFormulaParam)
    {
        return payTableFormulaParamMapper.selectPayTableFormulaParamList(payTableFormulaParam);
    }

    /**
     * 保存公司薪资计算参数
     *
     * @param payTableFormulaParamDTO 公司薪资计算参数列表
     * @return 结果
     */
    @Override
    public int savePayTableFormulaParam(PayTableFormulaParamDTO payTableFormulaParamDTO){
        payTableFormulaParamMapper.deletePayTableFormulaParamByCompId(payTableFormulaParamDTO.getCompId());
        List<PayTableFormulaParam> list = payTableFormulaParamDTO.getPayTableFormulaParamList();
        int number = 0;
        for (PayTableFormulaParam param : list){
            param.setUuid(UUID.randomUUID().toString().substring(0, 32));
            param.setCompId(payTableFormulaParamDTO.getCompId());
            param.setCreatorId(SecurityUtils.getUserId().toString());
            param.setCreator(SecurityUtils.getNickName());
            param.setCreatorNo(SecurityUtils.getUsername());
            param.setCreateDate(new Date());
            insertPayTableFormulaParam(param);
            number++;
        }
        return number;
    }

    /**
     * 复制公司薪资计算参数
     *
     * @param payTableFormulaParamDTO 公司薪资计算参数
     * @return 结果
     */
    @Override
    public int copyPayTableFormulaParam(PayTableFormulaParamDTO payTableFormulaParamDTO){
        payTableFormulaParamMapper.deletePayTableFormulaParamByCompId(payTableFormulaParamDTO.getCompIdTo());
        List<PayTableFormulaParam> list = payTableFormulaParamMapper.selectPayTableFormulaParamByCompId(payTableFormulaParamDTO.getCompId());
        int number = 0;
        for (PayTableFormulaParam param : list){
            param.setUuid(UUID.randomUUID().toString().substring(0, 32));
            param.setCompId(payTableFormulaParamDTO.getCompIdTo());
            param.setCreatorId(SecurityUtils.getUserId().toString());
            param.setCreator(SecurityUtils.getNickName());
            param.setCreatorNo(SecurityUtils.getUsername());
            param.setCreateDate(new Date());
            insertPayTableFormulaParam(param);
            number++;
        }
        return number;
    }

    /**
     * 新增公司薪资计算参数
     *
     * @param payTableFormulaParam 公司薪资计算参数
     * @return 结果
     */
    @Override
    public int insertPayTableFormulaParam(PayTableFormulaParam payTableFormulaParam)
    {
        return payTableFormulaParamMapper.insertPayTableFormulaParam(payTableFormulaParam);
    }

    /**
     * 修改公司薪资计算参数
     *
     * @param payTableFormulaParam 公司薪资计算参数
     * @return 结果
     */
    @Override
    public int updatePayTableFormulaParam(PayTableFormulaParam payTableFormulaParam)
    {
        return payTableFormulaParamMapper.updatePayTableFormulaParam(payTableFormulaParam);
    }

}
