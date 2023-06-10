package com.jlkj.human.hs.mapper;

import com.jlkj.human.hs.domain.EnterpriseRevenueRate;

import java.util.List;

/**
 * 企业效益系数设定Mapper接口
 * 
 * @author 267383
 * @date 2023-06-07
 */
public interface EnterpriseRevenueRateMapper 
{
    /**
     * 查询企业效益系数设定
     * 
     * @param id 企业效益系数设定主键
     * @return 企业效益系数设定
     */
    public EnterpriseRevenueRate selectEnterpriseRevenueRateById(String id);

    /**
     * 查询企业效益系数设定列表
     * 
     * @param enterpriseRevenueRate 企业效益系数设定
     * @return 企业效益系数设定集合
     */
    public List<EnterpriseRevenueRate> selectEnterpriseRevenueRateList(EnterpriseRevenueRate enterpriseRevenueRate);

    /**
     * 新增企业效益系数设定
     * 
     * @param enterpriseRevenueRate 企业效益系数设定
     * @return 结果
     */
    public int insertEnterpriseRevenueRate(EnterpriseRevenueRate enterpriseRevenueRate);

    /**
     * 修改企业效益系数设定
     * 
     * @param enterpriseRevenueRate 企业效益系数设定
     * @return 结果
     */
    public int updateEnterpriseRevenueRate(EnterpriseRevenueRate enterpriseRevenueRate);

    /**
     * 删除企业效益系数设定
     * 
     * @param id 企业效益系数设定主键
     * @return 结果
     */
    public int deleteEnterpriseRevenueRateById(String id);

    /**
     * 批量删除企业效益系数设定
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteEnterpriseRevenueRateByIds(String[] ids);
}
