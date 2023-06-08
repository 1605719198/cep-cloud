package com.jlkj.human.hs.mapper;

import com.jlkj.human.hs.domain.SocialSecurityBasis;

import java.util.List;

/**
 * 社保公积金标准核定Mapper接口
 * 
 * @author 266861
 * @date 2023-05-30
 */
public interface SocialSecurityBasisMapper 
{
    /**
     * 查询社保公积金标准核定
     * 
     * @param id 社保公积金标准核定主键
     * @return 社保公积金标准核定
     */
    public SocialSecurityBasis selectSocialSecurityBasisById(String id);

    /**
     * 查询社保公积金标准核定列表
     * 
     * @param socialSecurityBasis 社保公积金标准核定
     * @return 社保公积金标准核定集合
     */
    public List<SocialSecurityBasis> selectSocialSecurityBasisList(SocialSecurityBasis socialSecurityBasis);

    /**
     * 查询某员工最新社保公积金标准核定数据
     *
     * @param socialSecurityBasis 社保公积金标准核定
     * @return 社保公积金标准核定集合
     */
    public List<SocialSecurityBasis> selectSocialSecurityBasisByEmp(SocialSecurityBasis socialSecurityBasis);



    /**
     * 新增社保公积金标准核定
     * 
     * @param socialSecurityBasis 社保公积金标准核定
     * @return 结果
     */
    public int insertSocialSecurityBasis(SocialSecurityBasis socialSecurityBasis);

    /**
     * 修改社保公积金标准核定
     * 
     * @param socialSecurityBasis 社保公积金标准核定
     * @return 结果
     */
    public int updateSocialSecurityBasis(SocialSecurityBasis socialSecurityBasis);

    /**
     * 删除社保公积金标准核定
     * 
     * @param id 社保公积金标准核定主键
     * @return 结果
     */
    public int deleteSocialSecurityBasisById(String id);

    /**
     * 批量删除社保公积金标准核定
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSocialSecurityBasisByIds(String[] ids);
}
