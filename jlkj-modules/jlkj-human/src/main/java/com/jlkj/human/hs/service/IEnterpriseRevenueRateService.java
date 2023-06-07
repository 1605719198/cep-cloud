package com.jlkj.human.hs.service;

import java.util.List;
import com.jlkj.human.hs.domain.EnterpriseRevenueRate;

/**
 * 企业效益系数设定Service接口
 * 
 * @author 267383
 * @date 2023-06-07
 */
public interface IEnterpriseRevenueRateService 
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
     * 批量删除企业效益系数设定
     * 
     * @param ids 需要删除的企业效益系数设定主键集合
     * @return 结果
     */
    public int deleteEnterpriseRevenueRateByIds(String[] ids);

    /**
     * 删除企业效益系数设定信息
     * 
     * @param id 企业效益系数设定主键
     * @return 结果
     */
    public int deleteEnterpriseRevenueRateById(String id);
}
