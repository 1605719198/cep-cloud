package com.jlkj.human.hs.mapper;

import com.jlkj.human.hs.domain.SocialSecurityCompany;

import java.util.List;

/**
 * 社保公积金缴费公司维护Mapper接口
 * 
 * @author 267383
 * @date 2023-05-28
 */
public interface SocialSecurityCompanyMapper 
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
     * @param socialSecurityCompany 社保公积金缴费公司维护
     * @return 结果
     */
    public int insertSocialSecurityCompany(SocialSecurityCompany socialSecurityCompany);

    /**
     * 修改社保公积金缴费公司维护
     * 
     * @param socialSecurityCompany 社保公积金缴费公司维护
     * @return 结果
     */
    public int updateSocialSecurityCompany(SocialSecurityCompany socialSecurityCompany);

    /**
     * 删除社保公积金缴费公司维护
     * 
     * @param id 社保公积金缴费公司维护主键
     * @return 结果
     */
    public int deleteSocialSecurityCompanyById(String id);

    /**
     * 批量删除社保公积金缴费公司维护
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSocialSecurityCompanyByIds(String[] ids);
}
