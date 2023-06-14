package com.jlkj.human.hs.service.impl;

import com.jlkj.common.core.exception.ServiceException;
import com.jlkj.common.core.utils.uuid.UUID;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.human.hs.domain.SocialSecurityCompany;
import com.jlkj.human.hs.mapper.SocialSecurityCompanyMapper;
import com.jlkj.human.hs.service.ISocialSecurityCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 社保公积金缴费公司维护Service业务层处理
 *
 * @author 267383
 * @date 2023-05-28
 */
@Service
public class SocialSecurityCompanyServiceImpl implements ISocialSecurityCompanyService
{
    @Autowired
    private SocialSecurityCompanyMapper socialSecurityCompanyMapper;

    /**
     * 查询社保公积金缴费公司维护
     *
     * @param id 社保公积金缴费公司维护主键
     * @return 社保公积金缴费公司维护
     */
    @Override
    public SocialSecurityCompany selectSocialSecurityCompanyById(String id)
    {
        return socialSecurityCompanyMapper.selectSocialSecurityCompanyById(id);
    }

    /**
     * 查询社保公积金缴费公司维护列表
     *
     * @param socialSecurityCompany 社保公积金缴费公司维护
     * @return 社保公积金缴费公司维护
     */
    @Override
    public List<SocialSecurityCompany> selectSocialSecurityCompanyList(SocialSecurityCompany socialSecurityCompany)
    {
        return socialSecurityCompanyMapper.selectSocialSecurityCompanyList(socialSecurityCompany);
    }

    /**
     * 保存社保公积金缴费公司维护
     *
     * @param socialSecurityCompanyList 社保公积金缴费公司维护
     * @return 结果
     */
    @Override
    public int insertSocialSecurityCompany(List<SocialSecurityCompany> socialSecurityCompanyList) {
        for (SocialSecurityCompany socialSecurityCompany : socialSecurityCompanyList) {
            SocialSecurityCompany param = new SocialSecurityCompany();
            param.setCompId(socialSecurityCompany.getCompId());
            List<SocialSecurityCompany> socialSecurityCompanies = socialSecurityCompanyMapper.selectSocialSecurityCompanyList(param);
            for (SocialSecurityCompany item : socialSecurityCompanies) {
                if (!item.getCompId().isEmpty()) {
                    throw new ServiceException("公司别不允许重复");
                }
            }
            if (socialSecurityCompany.getId() != null) {
                socialSecurityCompany.setCreatorId(SecurityUtils.getUserId().toString());
                socialSecurityCompany.setCreator(SecurityUtils.getNickName());
                socialSecurityCompany.setCreateDate(new Date());
                socialSecurityCompanyMapper.updateSocialSecurityCompany(socialSecurityCompany);
            } else {
                socialSecurityCompany.setId(UUID.randomUUID().toString().substring(0, 32));
                socialSecurityCompanyMapper.insertSocialSecurityCompany(socialSecurityCompany);
            }
        }
        return 1;
    }

    /**
     * 修改社保公积金缴费公司维护
     *
     * @param socialSecurityCompany 社保公积金缴费公司维护
     * @return 结果
     */
    @Override
    public int updateSocialSecurityCompany(SocialSecurityCompany socialSecurityCompany)
    {
        return socialSecurityCompanyMapper.updateSocialSecurityCompany(socialSecurityCompany);
    }

    /**
     * 批量删除社保公积金缴费公司维护
     *
     * @param ids 需要删除的社保公积金缴费公司维护主键
     * @return 结果
     */
    @Override
    public int deleteSocialSecurityCompanyByIds(String[] ids)
    {
        return socialSecurityCompanyMapper.deleteSocialSecurityCompanyByIds(ids);
    }

    /**
     * 删除社保公积金缴费公司维护信息
     *
     * @param id 社保公积金缴费公司维护主键
     * @return 结果
     */
    @Override
    public int deleteSocialSecurityCompanyById(String id)
    {
        return socialSecurityCompanyMapper.deleteSocialSecurityCompanyById(id);
    }
}
