package com.jlkj.human.hs.service.impl;

import com.jlkj.common.core.utils.uuid.UUID;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.human.hs.domain.SocialSecurity;
import com.jlkj.human.hs.mapper.SocialSecurityMapper;
import com.jlkj.human.hs.service.ISocialSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 社保公积金缴费比例设定Service业务层处理
 *
 * @author jiangbingchen
 * @date 2023-05-11
 */
@Service
public class SocialSecurityServiceImpl implements ISocialSecurityService {
    @Autowired
    private SocialSecurityMapper socialSecurityMapper;

    /**
     * 查询社保公积金缴费比例设定
     *
     * @param id 社保公积金缴费比例设定主键
     * @return 社保公积金缴费比例设定
     */
    @Override
    public SocialSecurity selectSocialSecurityById(String id) {
        return socialSecurityMapper.selectSocialSecurityById(id);
    }

    /**
     * 查询社保公积金缴费比例设定列表
     *
     * @param socialSecurity 社保公积金缴费比例设定
     * @return 社保公积金缴费比例设定
     */
    @Override
    public List<SocialSecurity> selectSocialSecurityList(SocialSecurity socialSecurity) {
        return socialSecurityMapper.selectSocialSecurityList(socialSecurity);
    }

    /**
     * 新增社保公积金缴费比例设定
     *
     * @param socialSecurityList 社保公积金缴费比例设定
     * @return 结果
     */
    @Override
    public int insertSocialSecurity(List<SocialSecurity> socialSecurityList) {
        for (SocialSecurity socialSecurity : socialSecurityList) {
            if (socialSecurity.getId() != null) {
                socialSecurity.setCreatorId(SecurityUtils.getUserId().toString());
                socialSecurity.setCreator(SecurityUtils.getNickName());
                socialSecurity.setCreateDate(new Date());
                socialSecurityMapper.updateSocialSecurity(socialSecurity);
            } else {
                socialSecurity.setId(UUID.randomUUID().toString().substring(0, 32));
                socialSecurityMapper.insertSocialSecurity(socialSecurity);
            }
        }
        return 1;
    }

    /**
     * 修改社保公积金缴费比例设定
     *
     * @param socialSecurity 社保公积金缴费比例设定
     * @return 结果
     */
    @Override
    public int updateSocialSecurity(SocialSecurity socialSecurity) {
        return socialSecurityMapper.updateSocialSecurity(socialSecurity);
    }

    /**
     * 批量删除社保公积金缴费比例设定
     *
     * @param ids 需要删除的社保公积金缴费比例设定主键
     * @return 结果
     */
    @Override
    public int deleteSocialSecurityByIds(String[] ids) {
        return socialSecurityMapper.deleteSocialSecurityByIds(ids);
    }

    /**
     * 删除社保公积金缴费比例设定信息
     *
     * @param id 社保公积金缴费比例设定主键
     * @return 结果
     */
    @Override
    public int deleteSocialSecurityById(String id) {
        return socialSecurityMapper.deleteSocialSecurityById(id);
    }
}
