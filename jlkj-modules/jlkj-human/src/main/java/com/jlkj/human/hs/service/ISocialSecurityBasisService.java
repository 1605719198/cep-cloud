package com.jlkj.human.hs.service;

import com.jlkj.human.hs.domain.SocialSecurityBasis;
import com.jlkj.human.hs.dto.SocialSecurityBasisDTO;

import java.util.List;

/**
 * 社保公积金标准核定Service接口
 * 
 * @author 266861
 * @date 2023-05-30
 */
public interface ISocialSecurityBasisService 
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
     * 批量删除社保公积金标准核定
     * 
     * @param ids 需要删除的社保公积金标准核定主键集合
     * @return 结果
     */
    public int deleteSocialSecurityBasisByIds(String[] ids);

    /**
     * 删除社保公积金标准核定信息
     * 
     * @param id 社保公积金标准核定主键
     * @return 结果
     */
    public int deleteSocialSecurityBasisById(String id);

    /**
     * 导入社保公积金维护数据
     *
     * @param dtos 年收入资料列表
     * @param isUpdateSupport 是否新增
     * @param compId 公司别                       
     * @param operName 操作用户
     * @return 结果
     */
    public String importUser(List<SocialSecurityBasisDTO> dtos, Boolean isUpdateSupport, String operName, String compId);
}
