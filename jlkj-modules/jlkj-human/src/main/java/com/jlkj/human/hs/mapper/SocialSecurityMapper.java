package com.jlkj.human.hs.mapper;

import com.jlkj.human.hs.domain.SocialSecurity;

import java.util.List;

/**
 * 社保公积金缴费比例设定Mapper接口
 * 
 * @author jiangbingchen
 * @date 2023-05-11
 */
public interface SocialSecurityMapper 
{
    /**
     * 查询社保公积金缴费比例设定
     * 
     * @param id 社保公积金缴费比例设定主键
     * @return 社保公积金缴费比例设定
     */
    public SocialSecurity selectSocialSecurityById(String id);

    /**
     * 查询社保公积金缴费比例设定列表
     * 
     * @param socialSecurity 社保公积金缴费比例设定
     * @return 社保公积金缴费比例设定集合
     */
    public List<SocialSecurity> selectSocialSecurityList(SocialSecurity socialSecurity);

    /**
     * 新增社保公积金缴费比例设定
     * 
     * @param socialSecurityList 社保公积金缴费比例设定
     * @return 结果
     */
    public int insertSocialSecurity(List<SocialSecurity> socialSecurityList);

    /**
     * 修改社保公积金缴费比例设定
     * 
     * @param socialSecurity 社保公积金缴费比例设定
     * @return 结果
     */
    public int updateSocialSecurity(SocialSecurity socialSecurity);

    /**
     * 删除社保公积金缴费比例设定
     * 
     * @param id 社保公积金缴费比例设定主键
     * @return 结果
     */
    public int deleteSocialSecurityById(String id);

    /**
     * 批量删除社保公积金缴费比例设定
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSocialSecurityByIds(String[] ids);
}
