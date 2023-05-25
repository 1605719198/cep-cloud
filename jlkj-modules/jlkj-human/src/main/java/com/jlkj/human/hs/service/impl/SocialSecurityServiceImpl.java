package com.jlkj.human.hs.service.impl;

import com.jlkj.common.core.exception.ServiceException;
import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.common.core.utils.uuid.UUID;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.human.hs.domain.SocialSecurity;
import com.jlkj.human.hs.mapper.SocialSecurityMapper;
import com.jlkj.human.hs.service.ISocialSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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
        String payAreaId = socialSecurityList.get(0).getPayAreaId();
        Date inEffectDate = socialSecurityList.get(0).getEffectDate();
        Date nowdate = DateUtils.getNowDate();
        if(nowdate.compareTo(inEffectDate)>0){
            throw new ServiceException("生效日期不允许小于当前日期");
        }
        //查询当前公司别 最大版次号 及 生效日期
        Map<String, Object> versionMap = socialSecurityMapper.selectMaxVersion(payAreaId);
        Long version = 1L;
        version = Long.parseLong(versionMap.get("version").toString())+1;
        if(versionMap != null && versionMap.get("version") != null){
            Date effectDate= (Date) versionMap.get("effectDate");
            if(nowdate.compareTo(effectDate)<0){
                for (SocialSecurity socialSecurity : socialSecurityList) {
                    if (socialSecurity.getId() != null) {
                        socialSecurityMapper.deleteSocialSecurityById(socialSecurity.getId());
                    }
                }
            }
            for (SocialSecurity socialSecurity : socialSecurityList) {
                socialSecurity.setId(UUID.randomUUID().toString().substring(0, 32));
                socialSecurity.setCreatorId(SecurityUtils.getUserId().toString());
                socialSecurity.setCreator(SecurityUtils.getNickName());
                socialSecurity.setCreateDate(new Date());
                socialSecurity.setVersion(version);
                socialSecurityMapper.insertSocialSecurity(socialSecurity);
            }
        }



        else{
            for (SocialSecurity socialSecurity : socialSecurityList) {
                if (socialSecurity.getId() != null) {
                    socialSecurity.setCreatorId(SecurityUtils.getUserId().toString());
                    socialSecurity.setCreator(SecurityUtils.getNickName());
                    socialSecurity.setCreateDate(new Date());
                    socialSecurity.setVersion(version);
                    socialSecurityMapper.updateSocialSecurity(socialSecurity);
                } else {
                    socialSecurity.setId(UUID.randomUUID().toString().substring(0, 32));
                    socialSecurity.setVersion(version);
                    socialSecurityMapper.insertSocialSecurity(socialSecurity);
                }
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

    /**
     * 版本号列表
     * @return
     */
    @Override
    public List<Map<String,Long>> getVersionList(String payAreaId) {
        List<SocialSecurity> versionList = socialSecurityMapper.getVersionList(payAreaId);
        List<Map<String,Long>> collect = new ArrayList<>();
        if (versionList.size() > 0) {
            collect = versionList.stream().map(item -> {
                Map<String, Long> map = new HashMap<>(16);
                map.put("key", item.getVersion());
                map.put("value", item.getVersion());
                return map;
            }).collect(Collectors.toList());
        }
        return collect;
    }

}
