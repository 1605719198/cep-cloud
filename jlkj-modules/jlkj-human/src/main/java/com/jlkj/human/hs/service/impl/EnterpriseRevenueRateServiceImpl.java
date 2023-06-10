package com.jlkj.human.hs.service.impl;

import com.jlkj.common.core.exception.ServiceException;
import com.jlkj.common.core.utils.uuid.UUID;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.human.hs.domain.EnterpriseRevenueRate;
import com.jlkj.human.hs.mapper.EnterpriseRevenueRateMapper;
import com.jlkj.human.hs.service.IEnterpriseRevenueRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 企业效益系数设定Service业务层处理
 *
 * @author 267383
 * @date 2023-06-07
 */
@Service
public class EnterpriseRevenueRateServiceImpl implements IEnterpriseRevenueRateService
{
    @Autowired
    private EnterpriseRevenueRateMapper enterpriseRevenueRateMapper;

    /**
     * 查询企业效益系数设定
     *
     * @param id 企业效益系数设定主键
     * @return 企业效益系数设定
     */
    @Override
    public EnterpriseRevenueRate selectEnterpriseRevenueRateById(String id)
    {
        return enterpriseRevenueRateMapper.selectEnterpriseRevenueRateById(id);
    }

    /**
     * 查询企业效益系数设定列表
     *
     * @param enterpriseRevenueRate 企业效益系数设定
     * @return 企业效益系数设定
     */
    @Override
    public List<EnterpriseRevenueRate> selectEnterpriseRevenueRateList(EnterpriseRevenueRate enterpriseRevenueRate)
    {
        return enterpriseRevenueRateMapper.selectEnterpriseRevenueRateList(enterpriseRevenueRate);
    }

    /**
     * 新增企业效益系数设定
     *
     * @param enterpriseRevenueRate 企业效益系数设定
     * @return 结果
     */
    @Override
    public int insertEnterpriseRevenueRate(EnterpriseRevenueRate enterpriseRevenueRate)
    {
        List<EnterpriseRevenueRate> enterpriseRevenueRates = enterpriseRevenueRateMapper.selectEnterpriseRevenueRateList(enterpriseRevenueRate);
                if (enterpriseRevenueRates.isEmpty()) {
                    enterpriseRevenueRate.setId(UUID.randomUUID().toString().substring(0, 32));
                    enterpriseRevenueRate.setCreatorId(SecurityUtils.getUserId().toString());
                    enterpriseRevenueRate.setCreator(SecurityUtils.getNickName());
                    enterpriseRevenueRate.setCreateDate(new Date());
                    enterpriseRevenueRateMapper.insertEnterpriseRevenueRate(enterpriseRevenueRate);
                }else{
                    throw new ServiceException("不可重复新增");
                }
        return 1;
    }

    /**
     * 修改企业效益系数设定
     *
     * @param enterpriseRevenueRate 企业效益系数设定
     * @return 结果
     */
    @Override
    public int updateEnterpriseRevenueRate(EnterpriseRevenueRate enterpriseRevenueRate)
    {
        return enterpriseRevenueRateMapper.updateEnterpriseRevenueRate(enterpriseRevenueRate);
    }

    /**
     * 批量删除企业效益系数设定
     *
     * @param ids 需要删除的企业效益系数设定主键
     * @return 结果
     */
    @Override
    public int deleteEnterpriseRevenueRateByIds(String[] ids)
    {
        return enterpriseRevenueRateMapper.deleteEnterpriseRevenueRateByIds(ids);
    }

    /**
     * 删除企业效益系数设定信息
     *
     * @param id 企业效益系数设定主键
     * @return 结果
     */
    @Override
    public int deleteEnterpriseRevenueRateById(String id)
    {
        return enterpriseRevenueRateMapper.deleteEnterpriseRevenueRateById(id);
    }
}
