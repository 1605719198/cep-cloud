package com.jlkj.human.hs.service.impl;

import com.jlkj.common.core.utils.uuid.IdUtils;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.human.hs.domain.SocialSecurityBasis;
import com.jlkj.human.hs.domain.SocialSecurityBasisDetail;
import com.jlkj.human.hs.mapper.SocialSecurityBasisDetailMapper;
import com.jlkj.human.hs.service.ISocialSecurityBasisDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 社保公积金标准核定明细Service业务层处理
 *
 * @author 266861
 * @date 2023-05-31
 */
@Service
public class SocialSecurityBasisDetailServiceImpl implements ISocialSecurityBasisDetailService
{
    @Autowired
    private SocialSecurityBasisDetailMapper socialSecurityBasisDetailMapper;

    /**
     * 查询社保公积金标准核定明细
     *
     * @param id 社保公积金标准核定明细主键
     * @return 社保公积金标准核定明细
     */
    @Override
    public SocialSecurityBasisDetail selectSocialSecurityBasisDetailById(String id)
    {
        return socialSecurityBasisDetailMapper.selectSocialSecurityBasisDetailById(id);
    }

    /**
     * 查询社保公积金标准核定明细列表
     *
     * @param socialSecurityBasisDetail 社保公积金标准核定明细
     * @return 社保公积金标准核定明细
     */
    @Override
    public List<SocialSecurityBasisDetail> selectSocialSecurityBasisDetailList(SocialSecurityBasisDetail socialSecurityBasisDetail)
    {
        return socialSecurityBasisDetailMapper.selectSocialSecurityBasisDetailList(socialSecurityBasisDetail);
    }

    /**
     * 新增社保公积金标准核定明细
     *
     * @param socialSecurityBasisDetail 社保公积金标准核定明细
     * @return 结果
     */
    @Override
    public int insertSocialSecurityBasisDetail(SocialSecurityBasisDetail socialSecurityBasisDetail)
    {

        socialSecurityBasisDetail.setId(IdUtils.simpleUUID());
        socialSecurityBasisDetail.setCreatorId(SecurityUtils.getUserId().toString());
        socialSecurityBasisDetail.setCreator(SecurityUtils.getNickName());
        socialSecurityBasisDetail.setCreatorNo(SecurityUtils.getUsername());
        socialSecurityBasisDetail.setCreateDate(new Date());
        return socialSecurityBasisDetailMapper.insertSocialSecurityBasisDetail(socialSecurityBasisDetail);
    }

    /**
     * 通过主档新增社保公积金标准核定明细
     *
     * @param socialSecurityBasis 社保公积金标准核定主档
     * @return 结果
     */
    @Override
    public int insertSocialSecurityBasisDetailByMain(SocialSecurityBasis socialSecurityBasis){
        socialSecurityBasisDetailMapper.deleteSocialSecurityBasisDetailByMain(socialSecurityBasis.getId());
        int number = 0;
        List<SocialSecurityBasisDetail> detailList = socialSecurityBasis.getDetailList();
        for(SocialSecurityBasisDetail detail :detailList){
            detail.setSocialSecurityId(socialSecurityBasis.getId());
            insertSocialSecurityBasisDetail(detail);
            number++;
        }
        return number;
    }

    /**
     * 修改社保公积金标准核定明细
     *
     * @param socialSecurityBasisDetail 社保公积金标准核定明细
     * @return 结果
     */
    @Override
    public int updateSocialSecurityBasisDetail(SocialSecurityBasisDetail socialSecurityBasisDetail)
    {
        return socialSecurityBasisDetailMapper.updateSocialSecurityBasisDetail(socialSecurityBasisDetail);
    }

    /**
     * 批量删除社保公积金标准核定明细
     *
     * @param ids 需要删除的社保公积金标准核定明细主键
     * @return 结果
     */
    @Override
    public int deleteSocialSecurityBasisDetailByIds(String[] ids)
    {
        return socialSecurityBasisDetailMapper.deleteSocialSecurityBasisDetailByIds(ids);
    }

    /**
     * 删除社保公积金标准核定明细信息
     *
     * @param id 社保公积金标准核定明细主键
     * @return 结果
     */
    @Override
    public int deleteSocialSecurityBasisDetailById(String id)
    {
        return socialSecurityBasisDetailMapper.deleteSocialSecurityBasisDetailById(id);
    }

    /**
     * 删除社保公积金标准核定明细信息
     *
     * @param socialSecurityId 社保公积金标准核定明细主档id
     * @return 结果
     */
    @Override
    public int deleteSocialSecurityBasisDetailByMain(String socialSecurityId){
        return socialSecurityBasisDetailMapper.deleteSocialSecurityBasisDetailByMain(socialSecurityId);
    }
}
