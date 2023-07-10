package com.jlkj.human.hs.service.impl;

import com.jlkj.common.core.exception.ServiceException;
import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.core.utils.uuid.UUID;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.human.hs.domain.MiniStandard;
import com.jlkj.human.hs.mapper.MiniStandardMapper;
import com.jlkj.human.hs.service.IMiniStandardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 各公司最低工资标准及生活保障标准设定Service业务层处理
 *
 * @author jiangbingchen
 * @date 2023-05-24
 */
@Service
public class MiniStandardServiceImpl implements IMiniStandardService
{
    @Autowired
    private MiniStandardMapper miniStandardMapper;

    /**
     * 查询各公司最低工资标准及生活保障标准设定
     *
     * @param uuid 各公司最低工资标准及生活保障标准设定主键
     * @return 各公司最低工资标准及生活保障标准设定
     */
    @Override
    public MiniStandard selectMiniStandardByUuid(String uuid)
    {
        return miniStandardMapper.selectMiniStandardByUuid(uuid);
    }

    /**
     * 查询各公司最低工资标准及生活保障标准设定列表
     *
     * @param miniStandard 各公司最低工资标准及生活保障标准设定
     * @return 各公司最低工资标准及生活保障标准设定
     */
    @Override
    public List<MiniStandard> selectMiniStandardList(MiniStandard miniStandard)
    {
        return miniStandardMapper.selectMiniStandardList(miniStandard);
    }

    /**
     * 新增各公司最低工资标准及生活保障标准设定
     *
     * @param miniStandardList 各公司最低工资标准及生活保障标准设定
     * @return 结果
     */
    @Override
    public int insertMiniStandard(List<MiniStandard> miniStandardList) {
        String compId = miniStandardList.get(0).getCompId();
        Date inEffectDate = miniStandardList.get(0).getEffectDate();
        Date nowdate = DateUtils.getNowDate();
        if(nowdate.compareTo(inEffectDate)>0){
            throw new ServiceException("生效日期不允许小于当前日期");
        }
        Long version = 1L;
        //查询当前公司别 最大版次号 及 生效日期
        Map<String, Object> versionMap = miniStandardMapper.selectMaxVersion(compId);
        if(!StringUtils.isEmpty(versionMap)) {
            //生效日期
            String inEffectDate1 = versionMap.get("effectDate").toString();
            version = Long.parseLong(versionMap.get("version").toString());
            //生效日期小于操作日期
            if(inEffectDate1.compareTo(DateUtils.parseDateToStr("yyyy-MM-dd",nowdate))<0 ){
                version = version + 1;
            }
        }
        if(!StringUtils.isEmpty(versionMap)) {
            Date effectDate= (Date) versionMap.get("effectDate");
            if(nowdate.compareTo(effectDate)<0){
                for (MiniStandard miniStandard : miniStandardList) {
                    if (miniStandard.getUuid() != null) {
                        miniStandardMapper.deleteMiniStandardByUuid(miniStandard.getUuid());
                    }
                }
            }
            for (MiniStandard miniStandard : miniStandardList) {
                miniStandard.setUuid(UUID.randomUUID().toString().substring(0, 32));
                miniStandard.setCreatorId(SecurityUtils.getUserId().toString());
                miniStandard.setCreator(SecurityUtils.getNickName());
                miniStandard.setCreateDate(new Date());
                miniStandard.setVersion(version);
                miniStandardMapper.insertMiniStandard(miniStandard);
            }
        }
        else{
            for (MiniStandard miniStandard : miniStandardList) {
                if (miniStandard.getUuid() != null) {
                    miniStandard.setCreatorId(SecurityUtils.getUserId().toString());
                    miniStandard.setCreator(SecurityUtils.getNickName());
                    miniStandard.setCreateDate(new Date());
                    miniStandard.setVersion(version);
                    miniStandardMapper.updateMiniStandard(miniStandard);
                } else {
                    miniStandard.setUuid(UUID.randomUUID().toString().substring(0, 32));
                    miniStandard.setVersion(version);
                    miniStandardMapper.insertMiniStandard(miniStandard);
                }
            }
        }
        return miniStandardMapper.updateMiniStandardById(compId,version.toString(),DateUtils.parseDateToStr("yyyy-MM-dd",inEffectDate));
    }

    /**
     * 修改各公司最低工资标准及生活保障标准设定
     *
     * @param miniStandard 各公司最低工资标准及生活保障标准设定
     * @return 结果
     */
    @Override
    public int updateMiniStandard(MiniStandard miniStandard)
    {
        return miniStandardMapper.updateMiniStandard(miniStandard);
    }

    /**
     * 批量删除各公司最低工资标准及生活保障标准设定
     *
     * @param uuids 需要删除的各公司最低工资标准及生活保障标准设定主键
     * @return 结果
     */
    @Override
    public int deleteMiniStandardByUuids(String[] uuids)
    {
        return miniStandardMapper.deleteMiniStandardByUuids(uuids);
    }

    /**
     * 删除各公司最低工资标准及生活保障标准设定信息
     *
     * @param uuid 各公司最低工资标准及生活保障标准设定主键
     * @return 结果
     */
    @Override
    public int deleteMiniStandardByUuid(String uuid)
    {
        return miniStandardMapper.deleteMiniStandardByUuid(uuid);
    }

    /**
     * 版本号列表
     * @return
     */
    @Override
    public List<Map<String,Long>> getVersionList(String compId) {
        List<MiniStandard> versionList = miniStandardMapper.getVersionList(compId);
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
