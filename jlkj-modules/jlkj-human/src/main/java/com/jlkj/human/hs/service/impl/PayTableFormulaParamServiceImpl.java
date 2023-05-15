package com.jlkj.human.hs.service.impl;

import java.util.List;
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

    /**
     * 批量删除公司薪资计算参数
     *
     * @param uuids 需要删除的公司薪资计算参数主键
     * @return 结果
     */
    @Override
    public int deletePayTableFormulaParamByUuids(String[] uuids)
    {
        return payTableFormulaParamMapper.deletePayTableFormulaParamByUuids(uuids);
    }

    /**
     * 删除公司薪资计算参数信息
     *
     * @param uuid 公司薪资计算参数主键
     * @return 结果
     */
    @Override
    public int deletePayTableFormulaParamByUuid(String uuid)
    {
        return payTableFormulaParamMapper.deletePayTableFormulaParamByUuid(uuid);
    }
}
