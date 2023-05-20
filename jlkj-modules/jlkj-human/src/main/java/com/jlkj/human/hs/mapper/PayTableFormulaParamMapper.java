package com.jlkj.human.hs.mapper;

import com.jlkj.human.hs.domain.PayTableFormulaParam;

import java.util.List;

/**
 * 公司薪资计算参数Mapper接口
 * 
 * @author 266861
 * @date 2023-05-15
 */
public interface PayTableFormulaParamMapper 
{
    /**
     * 查询公司薪资计算参数
     * 
     * @param uuid 公司薪资计算参数主键
     * @return 公司薪资计算参数
     */
    public PayTableFormulaParam selectPayTableFormulaParamByUuid(String uuid);

    /**
     * 查询公司薪资计算参数
     *
     * @param compId 公司别
     * @return 公司薪资计算参数
     */
    public List<PayTableFormulaParam> selectPayTableFormulaParamByCompId(String compId);

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
     * 删除公司薪资计算参数
     * 
     * @param compId 公司薪资计算参数公司别
     * @return 结果
     */
    public int deletePayTableFormulaParamByCompId(String compId);



    /**
     * 批量删除公司薪资计算参数
     * 
     * @param uuids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePayTableFormulaParamByUuids(String[] uuids);
}
