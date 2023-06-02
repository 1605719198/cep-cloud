package com.jlkj.human.hs.service;

import com.jlkj.human.hs.domain.SocialSecurityCompany;

import java.util.List;

/**
 * 社保公积金缴费公司维护Service接口
 * 
 * @author 267383
 * @date 2023-05-28
 */
public interface ISocialSecurityCompanyService 
{
    /**
     * 查询社保公积金缴费公司维护
     * 
     * @param id 社保公积金缴费公司维护主键
     * @return 社保公积金缴费公司维护
     */
    public SocialSecurityCompany selectSocialSecurityCompanyById(String id);

    /**
     * 查询社保公积金缴费公司维护列表
     * 
     * @param socialSecurityCompany 社保公积金缴费公司维护
     * @return 社保公积金缴费公司维护集合
     */
    public List<SocialSecurityCompany> selectSocialSecurityCompanyList(SocialSecurityCompany socialSecurityCompany);

    /**
     * 新增社保公积金缴费公司维护
     * 
     * @param socialSecurityCompanyList 社保公积金缴费公司维护
     * @return 结果
     */
    public int insertSocialSecurityCompany(List<SocialSecurityCompany> socialSecurityCompanyList);

    /**
     * 修改社保公积金缴费公司维护
     * 
     * @param socialSecurityCompany 社保公积金缴费公司维护
     * @return 结果
     */
    public int updateSocialSecurityCompany(SocialSecurityCompany socialSecurityCompany);

    /**
     * 批量删除社保公积金缴费公司维护
     * 
     * @param ids 需要删除的社保公积金缴费公司维护主键集合
     * @return 结果
     */
    public int deleteSocialSecurityCompanyByIds(String[] ids);

    /**
     * 删除社保公积金缴费公司维护信息
     * 
     * @param id 社保公积金缴费公司维护主键
     * @return 结果
     */
    public int deleteSocialSecurityCompanyById(String id);
}
