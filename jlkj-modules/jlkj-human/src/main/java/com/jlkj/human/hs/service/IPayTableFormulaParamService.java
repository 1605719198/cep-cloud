package com.jlkj.human.hs.service;

import java.util.List;
import com.jlkj.human.hs.domain.PayTableFormulaParam;

/**
 * 公司薪资计算参数Service接口
 * 
 * @author 266861
 * @date 2023-05-15
 */
public interface IPayTableFormulaParamService 
{
    /**
     * 查询公司薪资计算参数
     * 
     * @param uuid 公司薪资计算参数主键
     * @return 公司薪资计算参数
     */
    public PayTableFormulaParam selectPayTableFormulaParamByUuid(String uuid);

    /**
     * 查询公司薪资计算参数列表
     * 
     * @param payTableFormulaParam 公司薪资计算参数
     * @return 公司薪资计算参数集合
     */
    public List<PayTableFormulaParam> selectPayTableFormulaParamList(PayTableFormulaParam payTableFormulaParam);

    /**
     * 新增公司薪资计算参数
     * 
     * @param payTableFormulaParam 公司薪资计算参数
     * @return 结果
     */
    public int insertPayTableFormulaParam(PayTableFormulaParam payTableFormulaParam);

    /**
     * 修改公司薪资计算参数
     * 
     * @param payTableFormulaParam 公司薪资计算参数
     * @return 结果
     */
    public int updatePayTableFormulaParam(PayTableFormulaParam payTableFormulaParam);

    /**
     * 批量删除公司薪资计算参数
     * 
     * @param uuids 需要删除的公司薪资计算参数主键集合
     * @return 结果
     */
    public int deletePayTableFormulaParamByUuids(String[] uuids);

    /**
     * 删除公司薪资计算参数信息
     * 
     * @param uuid 公司薪资计算参数主键
     * @return 结果
     */
    public int deletePayTableFormulaParamByUuid(String uuid);
}
