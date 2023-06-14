package com.jlkj.human.hs.service.impl;

import com.jlkj.common.core.exception.ServiceException;
import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.common.core.utils.uuid.UUID;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.human.hs.domain.PersonalIncomeTax;
import com.jlkj.human.hs.mapper.PersonalIncomeTaxMapper;
import com.jlkj.human.hs.service.IPersonalIncomeTaxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 个人所得税比例维护Service业务层处理
 *
 * @author jiangbingchen
 * @date 2023-05-18
 */
@Service
public class PersonalIncomeTaxServiceImpl implements IPersonalIncomeTaxService
{
    @Autowired
    private PersonalIncomeTaxMapper personalIncomeTaxMapper;

    /**
     * 查询个人所得税比例维护
     *
     * @param id 个人所得税比例维护主键
     * @return 个人所得税比例维护
     */
    @Override
    public PersonalIncomeTax selectPersonalIncomeTaxById(String id)
    {
        return personalIncomeTaxMapper.selectPersonalIncomeTaxById(id);
    }

    /**
     * 查询个人所得税比例维护列表
     *
     * @param personalIncomeTax 个人所得税比例维护
     * @return 个人所得税比例维护
     */
    @Override
    public List<PersonalIncomeTax> selectPersonalIncomeTaxList(PersonalIncomeTax personalIncomeTax)
    {
        return personalIncomeTaxMapper.selectPersonalIncomeTaxList(personalIncomeTax);
    }

    /**
     * 新增个人所得税比例维护
     *
     * @param personalIncomeTaxList 个人所得税比例维护
     * @return 结果
     */
    @Override
    public int insertPersonalIncomeTax(List<PersonalIncomeTax> personalIncomeTaxList)
    {
        String type = personalIncomeTaxList.get(0).getType();
        Date inEffectDate = personalIncomeTaxList.get(0).getEffectDate();
        Date nowdate = DateUtils.getNowDate();
        if(nowdate.compareTo(inEffectDate)>0){
            throw new ServiceException("生效日期不允许小于当前日期");
        }
        //查询当前公司别 最大版次号 及 生效日期
        Map<String, Object> versionNoMap = personalIncomeTaxMapper.selectMaxVersionNo(type);
        Long versionNo = 1L;
        versionNo = Long.parseLong(versionNoMap.get("versionNo").toString()) + 1;
        if(versionNoMap.get("versionNo") != null) {
            Date effectDate= (Date) versionNoMap.get("effectDate");
            if(nowdate.compareTo(effectDate)<0){
                for (PersonalIncomeTax personalIncomeTax : personalIncomeTaxList) {
                    if (personalIncomeTax.getId() != null) {
                        personalIncomeTaxMapper.deletePersonalIncomeTaxById(personalIncomeTax.getId());
                    }
                }
            }
            for(PersonalIncomeTax personalIncomeTax : personalIncomeTaxList) {
                personalIncomeTax.setId(UUID.randomUUID().toString().substring(0, 32));
                personalIncomeTax.setCreatorId(SecurityUtils.getUserId().toString());
                personalIncomeTax.setCreator(SecurityUtils.getNickName());
                personalIncomeTax.setCreateDate(new Date());
                personalIncomeTax.setVersionNo(versionNo);
                personalIncomeTaxMapper.insertPersonalIncomeTax(personalIncomeTax);
            }
        }
        else {
            for (PersonalIncomeTax personalIncomeTax : personalIncomeTaxList) {
                if (personalIncomeTax.getId() != null) {
                    personalIncomeTax.setCreatorId(SecurityUtils.getUserId().toString());
                    personalIncomeTax.setCreator(SecurityUtils.getNickName());
                    personalIncomeTax.setCreateDate(new Date());
                    personalIncomeTax.setVersionNo(versionNo);
                    personalIncomeTaxMapper.updatePersonalIncomeTax(personalIncomeTax);
                } else {
                    personalIncomeTax.setId(UUID.randomUUID().toString().substring(0, 32));
                    personalIncomeTax.setVersionNo(versionNo);
                    personalIncomeTaxMapper.insertPersonalIncomeTax(personalIncomeTax);
                }
            }
        }
        return 1;
    }

    /**
     * 修改个人所得税比例维护
     *
     * @param personalIncomeTax 个人所得税比例维护
     * @return 结果
     */
    @Override
    public int updatePersonalIncomeTax(PersonalIncomeTax personalIncomeTax)
    {
        return personalIncomeTaxMapper.updatePersonalIncomeTax(personalIncomeTax);
    }

    /**
     * 批量删除个人所得税比例维护
     *
     * @param ids 需要删除的个人所得税比例维护主键
     * @return 结果
     */
    @Override
    public int deletePersonalIncomeTaxByIds(String[] ids)
    {
        return personalIncomeTaxMapper.deletePersonalIncomeTaxByIds(ids);
    }

    /**
     * 删除个人所得税比例维护信息
     *
     * @param id 个人所得税比例维护主键
     * @return 结果
     */
    @Override
    public int deletePersonalIncomeTaxById(String id)
    {
        return personalIncomeTaxMapper.deletePersonalIncomeTaxById(id);
    }

    /**
     * 版本号列表
     * @return
     */
    @Override
    public List<Map<String,Long>> getVersionList(String type) {
        List<PersonalIncomeTax> versionList = personalIncomeTaxMapper.getVersionList(type);
        List<Map<String,Long>> collect = new ArrayList<>();
        if (versionList.size() > 0) {
            collect = versionList.stream().map(item -> {
                Map<String, Long> map = new HashMap<>(16);
                map.put("key", item.getVersionNo());
                map.put("value", item.getVersionNo());
                return map;
            }).collect(Collectors.toList());
        }
        return collect;
    }
}
