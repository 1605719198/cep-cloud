package com.jlkj.finance.ft.service.impl;

import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.common.core.utils.uuid.IdUtils;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.finance.ft.domain.FinanceFtStatus;
import com.jlkj.finance.ft.dto.FinanceFtStatusDTO;
import com.jlkj.finance.ft.mapper.FinanceFtStatusMapper;
import com.jlkj.finance.ft.service.IFinanceFtStatusDetailService;
import com.jlkj.finance.ft.service.IFinanceFtStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 资产使用状态Service业务层处理
 *
 * @author SunXuTong
 * @date 2023-07-03
 * @version 1.0
 */
@Service
public class FinanceFtStatusServiceImpl implements IFinanceFtStatusService
{
    @Autowired
    private FinanceFtStatusMapper financeFtStatusMapper;

    @Resource
    private IFinanceFtStatusDetailService iFinanceFtStatusDetailService;

    /**
     * 查询资产使用状态
     *
     * @author SunXuTong
     * @date 2023-07-03
     * @param uuid 资产使用状态主键
     * @return 资产使用状态
     */
    @Override
    public FinanceFtStatus selectFinanceFtStatusByUuid(String uuid)
    {
        return financeFtStatusMapper.selectFinanceFtStatusByUuid(uuid);
    }

    /**
     * 查询资产使用状态列表
     *
     * @author SunXuTong
     * @date 2023-07-03
     * @param financeFtStatus 资产使用状态
     * @return 资产使用状态
     */
    @Override
    public List<FinanceFtStatus> selectFinanceFtStatusList(FinanceFtStatus financeFtStatus)
    {
        return financeFtStatusMapper.selectFinanceFtStatusList(financeFtStatus);
    }

    /**
     * 新增资产使用状态
     *
     * @author SunXuTong
     * @date 2023-07-03
     * @param financeFtStatusDTO 资产使用状态
     * @return 结果
     */
    @Override
    public int insertFinanceFtStatus(FinanceFtStatusDTO financeFtStatusDTO)
    {
        iFinanceFtStatusDetailService.saveBatch(financeFtStatusDTO.getStatusListA());
        financeFtStatusDTO.setUuid(IdUtils.fastSimpleUUID());
        return financeFtStatusMapper.insertFinanceFtStatus(financeFtStatusDTO);
    }

    /**
     * 修改资产使用状态
     *
     * @author SunXuTong
     * @date 2023-07-03
     * @param financeFtStatus 资产使用状态
     * @return 结果
     */
    @Override
    public int updateFinanceFtStatus(FinanceFtStatus financeFtStatus)
    {
        financeFtStatus.setUpdateTime(DateUtils.getNowDate());
        financeFtStatus.setUpdateBy(SecurityUtils.getUsername());
        financeFtStatus.setUpdateName(SecurityUtils.getNickName());
        return financeFtStatusMapper.updateFinanceFtStatus(financeFtStatus);
    }

    /**
     * 批量删除资产使用状态
     *
     * @author SunXuTong
     * @date 2023-07-03
     * @param uuids 需要删除的资产使用状态主键
     * @return 结果
     */
    @Override
    public int deleteFinanceFtStatusByUuids(String[] uuids)
    {
        return financeFtStatusMapper.deleteFinanceFtStatusByUuids(uuids);
    }

    /**
     * 删除资产使用状态信息
     *
     * @author SunXuTong
     * @date 2023-07-03
     * @param uuid 资产使用状态主键
     * @return 结果
     */
    @Override
    public int deleteFinanceFtStatusByUuid(String uuid)
    {
        return financeFtStatusMapper.deleteFinanceFtStatusByUuid(uuid);
    }
}
