package com.jlkj.human.hs.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.jlkj.common.core.exception.ServiceException;
import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.core.utils.uuid.IdUtils;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.human.hs.domain.SocialSecurity;
import com.jlkj.human.hs.mapper.SocialSecurityMapper;
import com.jlkj.human.hs.service.ISocialSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

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
     * 查询社保公积金
     *
     * @param compId 社保公积金
     * @return 社保公积金
     */
    @Override
    public List<SocialSecurity> selectSocialSecurityByempNo(String compId)
    {
        return socialSecurityMapper.selectSocialSecurityByempNo(compId);
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
            Long version = 1L;
            //查询当前公司别 最大版次号 及 生效日期
            Map<String, Object> versionMap = socialSecurityMapper.selectMaxVersion(payAreaId);
            if (!StringUtils.isEmpty(versionMap)) {
                String inEffectDate1 = versionMap.get("effectDate").toString();
                    version = Long.parseLong(versionMap.get("version").toString());
                    if (inEffectDate1.compareTo(DateUtils.parseDateToStr("yyyy-MM-dd",nowdate)) < 0) {
                        version = version + 1;
                    }
                }
            for (SocialSecurity socialSecurities : socialSecurityList) {
            if (socialSecurities.getId() != null) {
                SocialSecurity dbSocialSecuritie = socialSecurityMapper.selectSocialSecurityById(socialSecurities.getId());
                if (!dbSocialSecuritie.getSalaryProjectId().equals(socialSecurities.getSalaryProjectId())) {
                    List<SocialSecurity> socialSecurityVersion = socialSecurityMapper.selectSocialSecurityListByVersion(socialSecurities);
                    if (!ObjectUtil.isEmpty(socialSecurityVersion)) {
                        List<SocialSecurity> socialSecurityCompanies = socialSecurityMapper.selectSocialSecurityListBySalaryProjectId(socialSecurities);
                        if (!ObjectUtil.isEmpty(socialSecurityCompanies)) {
                            throw new ServiceException("同一版本下社保公积金项目不允许重复");
                        }
                    }
                }
            }else {
                List<SocialSecurity> socialSecurityVersion = socialSecurityMapper.selectSocialSecurityListByVersion(socialSecurities);
                for (SocialSecurity item : socialSecurityVersion) {
                    if (!ObjectUtil.isEmpty(socialSecurityVersion)) {
                        List<SocialSecurity> socialSecurityCompanies = socialSecurityMapper.selectSocialSecurityListBySalaryProjectId(socialSecurities);
                        for (SocialSecurity items : socialSecurityCompanies) {
                            if (!ObjectUtil.isEmpty(socialSecurityCompanies)) {
                                throw new ServiceException("同一版本下社保公积金项目不允许重复");
                            }
                        }
                    }
                }
            }
        }
                if (!StringUtils.isEmpty(versionMap)) {
                    Date effectDate = (Date) versionMap.get("effectDate");
                    if (nowdate.compareTo(effectDate) < 0) {
                        for (SocialSecurity socialSecurity : socialSecurityList) {
                            if (socialSecurity.getId() != null) {
                                socialSecurityMapper.deleteSocialSecurityById(socialSecurity.getId());
                            }
                        }
                    }
                    for (SocialSecurity socialSecurity : socialSecurityList) {
                        socialSecurity.setId(IdUtils.simpleUUID());
                        socialSecurity.setCreatorId(SecurityUtils.getUserId().toString());
                        socialSecurity.setCreator(SecurityUtils.getNickName());
                        socialSecurity.setCreateDate(new Date());
                        socialSecurity.setVersion(version);
                        socialSecurityMapper.insertSocialSecurity(socialSecurity);
                    }
                }
                else {
                    for (SocialSecurity socialSecurity : socialSecurityList) {
                        if (socialSecurity.getId() != null) {
                            socialSecurity.setCreatorId(SecurityUtils.getUserId().toString());
                            socialSecurity.setCreator(SecurityUtils.getNickName());
                            socialSecurity.setCreateDate(new Date());
                            socialSecurity.setVersion(version);
                            socialSecurityMapper.updateSocialSecurity(socialSecurity);
                        } else {
                            socialSecurity.setId(IdUtils.simpleUUID());
                            socialSecurity.setVersion(version);
                            socialSecurityMapper.insertSocialSecurity(socialSecurity);
                        }
                    }
                }
        return socialSecurityMapper.updateSocialSecurityById(payAreaId,version.toString(),DateUtils.parseDateToStr("yyyy-MM-dd",inEffectDate));
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
