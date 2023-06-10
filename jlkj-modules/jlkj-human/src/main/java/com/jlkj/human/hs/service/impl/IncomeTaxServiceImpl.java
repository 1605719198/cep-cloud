package com.jlkj.human.hs.service.impl;

import com.jlkj.common.core.utils.uuid.UUID;
import com.jlkj.human.hs.domain.IncomeTax;
import com.jlkj.human.hs.mapper.IncomeTaxMapper;
import com.jlkj.human.hs.service.IIncomeTaxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 各公司所得税起征点设定Service业务层处理
 *
 * @author 116519
 * @date 2023-05-16
 */
@Service
public class IncomeTaxServiceImpl implements IIncomeTaxService
{
    @Autowired
    private IncomeTaxMapper incomeTaxMapper;

    /**
     * 查询各公司所得税起征点设定
     *
     * @param uuid 各公司所得税起征点设定主键
     * @return 各公司所得税起征点设定
     */
    @Override
    public IncomeTax selectIncomeTaxByUuid(String uuid)
    {
        IncomeTax incomeTax =incomeTaxMapper.selectIncomeTaxByUuid(uuid);
        IncomeTax incomeTaxNew = new IncomeTax();
        incomeTaxNew.setCompId(incomeTax.getCompId());
        incomeTaxNew.setVersion(incomeTax.getVersion());
        List<IncomeTax> incometaxDetailListList= incomeTaxMapper.selectIncomeTaxList(incomeTaxNew);
        incomeTax.setIncometaxDetailListList(incometaxDetailListList);
        return incomeTax;
    }

    /**
     * 查询各公司所得税起征点设定列表
     *
     * @param incomeTax 各公司所得税起征点设定
     * @return 各公司所得税起征点设定
     */
    @Override
    public List<IncomeTax> selectIncomeTaxList(IncomeTax incomeTax)
    {
        return incomeTaxMapper.selectIncomeTaxList(incomeTax);
    }

    /**
     * 新增各公司所得税起征点设定
     *
     * @param incomeTax 各公司所得税起征点设定
     * @return 结果
     */
    @Override
    public int insertIncomeTax(IncomeTax incomeTax)
    {
        //查询当前公司别 最大版次号 及 生效日期
        Map<String, Object> versionMap = incomeTaxMapper.selectMaxVersion(incomeTax.getCompId());
        if(versionMap == null || versionMap.get("version") == null){
            incomeTax.setVersion(1L);
        }else{
            //判断当前数据生效日期 必须要大于最大班次生效日期
            Long version = Long.parseLong(versionMap.get("version").toString());
            incomeTax.setVersion(version+ 1);
        }
        List<IncomeTax> incomeTaxList = incomeTax.getIncometaxDetailListList();
        for (IncomeTax item : incomeTaxList) {
            item.setUuid(UUID.randomUUID().toString().substring(0, 32));
            item.setCompId(incomeTax.getCompId());
            item.setEffectDate(incomeTax.getEffectDate());
            item.setVersion(incomeTax.getVersion());
            item.setCreator(incomeTax.getCreator());
            item.setCreatorId(incomeTax.getCreatorId());
            item.setCreatorName(incomeTax.getCreatorName());
            item.setCreateDate(incomeTax.getCreateDate());
             incomeTaxMapper.insertIncomeTax(item);
        }
        return 1;
    }

    /**
     * 修改各公司所得税起征点设定
     *
     * @param incomeTax 各公司所得税起征点设定
     * @return 结果
     */
    @Override
    public int updateIncomeTax(IncomeTax incomeTax)

    {
       int  del =  incomeTaxMapper.deleteIncomeTaxByVersion(incomeTax.getCompId(),incomeTax.getVersion());
        List<IncomeTax> incomeTaxList = incomeTax.getIncometaxDetailListList();
        for (IncomeTax item : incomeTaxList) {
            item.setEffectDate(incomeTax.getEffectDate());
            item.setCreator(incomeTax.getCreator());
            item.setCreatorId(incomeTax.getCreatorId());
            item.setCreatorName(incomeTax.getCreatorName());
            item.setCreateDate(incomeTax.getCreateDate());
            item.setUuid(UUID.randomUUID().toString().substring(0, 32));
            item.setVersion(incomeTax.getVersion());
            item.setCompId(incomeTax.getCompId());
            incomeTaxMapper.insertIncomeTax(item);

        }
        return 1;
    }

    /**
     * 批量删除各公司所得税起征点设定
     *
     * @param uuids 需要删除的各公司所得税起征点设定主键
     * @return 结果
     */
    @Override
    public int deleteIncomeTaxByUuids(String[] uuids)
    {
        return incomeTaxMapper.deleteIncomeTaxByUuids(uuids);
    }

    /**
     * 删除各公司所得税起征点设定信息
     *
     * @param uuid 各公司所得税起征点设定主键
     * @return 结果
     */
    @Override
    public int deleteIncomeTaxByUuid(String uuid)
    {
        return incomeTaxMapper.deleteIncomeTaxByUuid(uuid);
    }

    /**
     * 版本号列表
     * @return
     */
    @Override
    public List<Map<String,Long>> getVersionList(String compId) {
        List<IncomeTax> versionList = incomeTaxMapper.getVersionList(compId);
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
