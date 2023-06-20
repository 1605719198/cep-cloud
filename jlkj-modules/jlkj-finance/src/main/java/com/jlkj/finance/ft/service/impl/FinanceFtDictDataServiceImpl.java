package com.jlkj.finance.ft.service.impl;

import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.common.core.utils.uuid.IdUtils;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.finance.ft.domain.FinanceFtDictData;
import com.jlkj.finance.ft.mapper.FinanceFtDictDataMapper;
import com.jlkj.finance.ft.service.IFinanceFtDictDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 选单内容设定Service业务层处理
 *
 * @author SunXuTong
 * @date 2023-06-15
 */
@Service
public class FinanceFtDictDataServiceImpl implements IFinanceFtDictDataService
{
    @Autowired
    private FinanceFtDictDataMapper financeFtDictDataMapper;

    /**
     * 查询选单内容设定
     *
     * @param uuid 选单内容设定主键
     * @return 选单内容设定
     */
    @Override
    public FinanceFtDictData selectFinanceFtDictDataByUuid(String uuid)
    {
        return financeFtDictDataMapper.selectFinanceFtDictDataByUuid(uuid);
    }

    /**
     * 查询选单内容设定列表
     *
     * @param financeFtDictData 选单内容设定
     * @return 选单内容设定
     */
    @Override
    public List<FinanceFtDictData> selectFinanceFtDictDataList(FinanceFtDictData financeFtDictData)
    {
        return financeFtDictDataMapper.selectFinanceFtDictDataList(financeFtDictData);
    }

    /**
     * 新增选单内容设定
     *
     * @param financeFtDictData 选单内容设定
     * @return 结果
     */
    @Override
    public int insertFinanceFtDictData(FinanceFtDictData financeFtDictData)
    {
        financeFtDictData.setUuid(IdUtils.fastSimpleUUID());
        financeFtDictData.setCreateBy(SecurityUtils.getUsername());
        financeFtDictData.setCreateName(SecurityUtils.getNickName());
        financeFtDictData.setCreateTime(DateUtils.getNowDate());
        return financeFtDictDataMapper.insertFinanceFtDictData(financeFtDictData);
    }

    /**
     * 修改选单内容设定
     *
     * @param financeFtDictData 选单内容设定
     * @return 结果
     */
    @Override
    public int updateFinanceFtDictData(FinanceFtDictData financeFtDictData)
    {
        financeFtDictData.setUpdateTime(DateUtils.getNowDate());
        financeFtDictData.setUpdateBy(SecurityUtils.getUsername());
        financeFtDictData.setUpdateName(SecurityUtils.getNickName());
        return financeFtDictDataMapper.updateFinanceFtDictData(financeFtDictData);
    }

    /**
     * 批量删除选单内容设定
     *
     * @param uuids 需要删除的选单内容设定主键
     * @return 结果
     */
    @Override
    public int deleteFinanceFtDictDataByUuids(String[] uuids)
    {
        return financeFtDictDataMapper.deleteFinanceFtDictDataByUuids(uuids);
    }

    /**
     * 删除选单内容设定信息
     *
     * @param uuid 选单内容设定主键
     * @return 结果
     */
    @Override
    public int deleteFinanceFtDictDataByUuid(String uuid)
    {
        return financeFtDictDataMapper.deleteFinanceFtDictDataByUuid(uuid);
    }
}
