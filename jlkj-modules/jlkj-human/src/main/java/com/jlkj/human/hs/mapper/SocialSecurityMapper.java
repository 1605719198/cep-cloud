package com.jlkj.human.hs.mapper;

import com.jlkj.human.hs.domain.SocialSecurity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

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
     * 查询员工社保公积金
     *
     * @param compId 公司别
     * @return 设定值
     */
    public List<SocialSecurity> selectSocialSecurityByempNo(String compId);

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
     * @param socialSecurity 社保公积金缴费比例设定
     * @return 结果
     */
    public int insertSocialSecurity(SocialSecurity socialSecurity);

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

    /**
     * 版本号列表
     *
     * @param payAreaId 缴费地区
     * @return 结果
     */
    List<SocialSecurity> getVersionList(String payAreaId);

    /**
     * 获取最大版本号
     *
     * @param payAreaId 缴费地区
     * @return 结果
     */
    Map<String,Object> selectMaxVersion(String payAreaId);

    /**
     * 删除社保公积金缴费比例数据
     * @Description 删除社保公积金缴费比例数据
     * @param payAreaId 缴费地区
     * @param version 版本号
     * @return 社保公积金缴费比例删除结果
     * @author 267383
     * @Date 2023/6/26 15:35
     **/
    public int deleteSocialSecurityByVersion(@Param("payAreaId") String payAreaId, @Param("version") Long version);

    /**
     * 获取最大版本号
     *
     * @param socialSecurity 社保公积金项目
     * @return 结果
     */
    public List<SocialSecurity> selectSocialSecurityListBySalaryProjectId(SocialSecurity socialSecurity);

    /**
     * 获取版本号
     *
     * @param socialSecurity 社保公积金项目
     * @return 结果
     */
    public List<SocialSecurity> selectSocialSecurityListByVersion(SocialSecurity socialSecurity);
}
