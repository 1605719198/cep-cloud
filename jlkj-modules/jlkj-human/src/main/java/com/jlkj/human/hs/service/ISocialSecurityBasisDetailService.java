package com.jlkj.human.hs.service;

import com.jlkj.human.hs.domain.SocialSecurityBasis;
import com.jlkj.human.hs.domain.SocialSecurityBasisDetail;

import java.util.List;

/**
 * 社保公积金标准核定明细Service接口
 *
 * @author 266861
 * @date 2023-05-31
 */
public interface ISocialSecurityBasisDetailService {
    /**
     * 查询社保公积金标准核定明细
     *
     * @param id 社保公积金标准核定明细主键
     * @return 社保公积金标准核定明细
     */
    public SocialSecurityBasisDetail selectSocialSecurityBasisDetailById(String id);

    /**
     * 查询社保公积金标准核定明细列表
     *
     * @param socialSecurityBasisDetail 社保公积金标准核定明细
     * @return 社保公积金标准核定明细集合
     */
    public List<SocialSecurityBasisDetail> selectSocialSecurityBasisDetailList(SocialSecurityBasisDetail socialSecurityBasisDetail);

    /**
     * 新增社保公积金标准核定明细
     *
     * @param socialSecurityBasisDetail 社保公积金标准核定明细
     * @return 结果
     */
    public int insertSocialSecurityBasisDetail(SocialSecurityBasisDetail socialSecurityBasisDetail);

    /**
     * 通过主档新增社保公积金标准核定明细
     *
     * @param socialSecurityBasis 社保公积金标准核定主档
     * @return 结果
     */
    public int insertSocialSecurityBasisDetailByMain(SocialSecurityBasis socialSecurityBasis);

    /**
     * 修改社保公积金标准核定明细
     *
     * @param socialSecurityBasisDetail 社保公积金标准核定明细
     * @return 结果
     */
    public int updateSocialSecurityBasisDetail(SocialSecurityBasisDetail socialSecurityBasisDetail);

    /**
     * 批量删除社保公积金标准核定明细
     *
     * @param ids 需要删除的社保公积金标准核定明细主键集合
     * @return 结果
     */
    public int deleteSocialSecurityBasisDetailByIds(String[] ids);

    /**
     * 删除社保公积金标准核定明细信息
     *
     * @param id 社保公积金标准核定明细主键
     * @return 结果
     */
    public int deleteSocialSecurityBasisDetailById(String id);

    /**
     * 删除社保公积金标准核定明细信息
     *
     * @param socialSecurityId 社保公积金标准核定明细主档id
     * @return 结果
     */
    public int deleteSocialSecurityBasisDetailByMain(String socialSecurityId);
}
