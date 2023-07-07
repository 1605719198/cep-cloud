package com.jlkj.finance.ft.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.common.core.utils.uuid.IdUtils;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.finance.ft.domain.FinanceFtStatusDetail;
import com.jlkj.finance.ft.mapper.FinanceFtStatusDetailMapper;
import com.jlkj.finance.ft.service.IFinanceFtStatusDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 资产使用状态明细设定Service业务层处理
 *
 * @author SunXuTong
 * @date 2023-07-06
 * @version 1.0
 */
@Service
public class FinanceFtStatusDetailServiceImpl extends ServiceImpl<FinanceFtStatusDetailMapper, FinanceFtStatusDetail>
    implements IFinanceFtStatusDetailService{
    @Autowired
    private FinanceFtStatusDetailMapper financeFtStatusDetailMapper;

    /**
     * 查询资产使用状态明细设定
     *
     * @author SunXuTong
     * @date 2023-07-06
     * @param uuid 资产使用状态明细设定主键
     * @return 资产使用状态明细设定
     */
    @Override
    public FinanceFtStatusDetail selectFinanceFtStatusDetailByUuid(String uuid)
    {
        return financeFtStatusDetailMapper.selectFinanceFtStatusDetailByUuid(uuid);
    }

    /**
     * 查询资产使用状态明细设定列表
     *
     * @author SunXuTong
     * @date 2023-07-06
     * @param financeFtStatusDetail 资产使用状态明细设定
     * @return 资产使用状态明细设定
     */
    @Override
    public List<FinanceFtStatusDetail> selectFinanceFtStatusDetailList(FinanceFtStatusDetail financeFtStatusDetail)
    {
        return financeFtStatusDetailMapper.selectFinanceFtStatusDetailList(financeFtStatusDetail);
    }

    /**
     * 新增资产使用状态明细设定
     *
     * @author SunXuTong
     * @date 2023-07-06
     * @param financeFtStatusDetail 资产使用状态明细设定
     * @return 结果
     */
    @Override
    public int insertFinanceFtStatusDetail(FinanceFtStatusDetail financeFtStatusDetail)
    {
        financeFtStatusDetail.setUuid(IdUtils.fastSimpleUUID());
        financeFtStatusDetail.setCreateTime(DateUtils.getNowDate());
        financeFtStatusDetail.setCreateBy(SecurityUtils.getUsername());
        financeFtStatusDetail.setCreateName(SecurityUtils.getNickName());
        return financeFtStatusDetailMapper.insertFinanceFtStatusDetail(financeFtStatusDetail);
    }

    /**
     * 修改资产使用状态明细设定
     *
     * @author SunXuTong
     * @date 2023-07-06
     * @param financeFtStatusDetail 资产使用状态明细设定
     * @return 结果
     */
    @Override
    public int updateFinanceFtStatusDetail(FinanceFtStatusDetail financeFtStatusDetail)
    {
        financeFtStatusDetail.setUpdateTime(DateUtils.getNowDate());
        financeFtStatusDetail.setUpdateBy(SecurityUtils.getUsername());
        financeFtStatusDetail.setUpdateName(SecurityUtils.getNickName());
        return financeFtStatusDetailMapper.updateFinanceFtStatusDetail(financeFtStatusDetail);
    }

    /**
     * 批量删除资产使用状态明细设定
     *
     * @author SunXuTong
     * @date 2023-07-06
     * @param uuids 需要删除的资产使用状态明细设定主键
     * @return 结果
     */
    @Override
    public int deleteFinanceFtStatusDetailByUuids(String[] uuids)
    {
        return financeFtStatusDetailMapper.deleteFinanceFtStatusDetailByUuids(uuids);
    }

    /**
     * 删除资产使用状态明细设定信息
     *
     * @author SunXuTong
     * @date 2023-07-06
     * @param uuid 资产使用状态明细设定主键
     * @return 结果
     */
    @Override
    public int deleteFinanceFtStatusDetailByUuid(String uuid)
    {
        return financeFtStatusDetailMapper.deleteFinanceFtStatusDetailByUuid(uuid);
    }
}
