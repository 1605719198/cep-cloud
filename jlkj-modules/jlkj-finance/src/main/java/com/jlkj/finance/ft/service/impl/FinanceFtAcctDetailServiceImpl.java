package com.jlkj.finance.ft.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.common.core.utils.uuid.IdUtils;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.finance.ft.domain.FinanceFtAcctDetail;
import com.jlkj.finance.ft.mapper.FinanceFtAcctDetailMapper;
import com.jlkj.finance.ft.service.IFinanceFtAcctDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 账务代码明细设定Service业务层处理
 *
 * @author SunXuTong
 * @date 2023-06-30
 */
@Service
public class FinanceFtAcctDetailServiceImpl extends ServiceImpl<FinanceFtAcctDetailMapper,  FinanceFtAcctDetail>
    implements IFinanceFtAcctDetailService{
    @Autowired
    private FinanceFtAcctDetailMapper financeFtAcctDetailMapper;

    /**
     * 查询账务代码明细设定
     *
     * @author SunXuTong
     * @date 2023-06-30
     * @param uuid 账务代码明细设定主键
     * @return 账务代码明细设定
     */
    @Override
    public FinanceFtAcctDetail selectFinanceFtAcctDetailByUuid(String uuid)
    {
        return financeFtAcctDetailMapper.selectFinanceFtAcctDetailByUuid(uuid);
    }

    /**
     * 查询账务代码明细设定列表
     *
     * @author SunXuTong
     * @date 2023-06-30
     * @param financeFtAcctDetail 账务代码明细设定
     * @return 账务代码明细设定
     */
    @Override
    public List<FinanceFtAcctDetail> selectFinanceFtAcctDetailList(FinanceFtAcctDetail financeFtAcctDetail)
    {
        return financeFtAcctDetailMapper.selectFinanceFtAcctDetailList(financeFtAcctDetail);
    }

    /**
     * 新增账务代码明细设定
     *
     * @author SunXuTong
     * @date 2023-06-30
     * @param financeFtAcctDetail 账务代码明细设定
     * @return 结果
     */
    @Override
    public int insertFinanceFtAcctDetail(FinanceFtAcctDetail financeFtAcctDetail)
    {
        financeFtAcctDetail.setUuid(IdUtils.fastSimpleUUID());
        financeFtAcctDetail.setCreateTime(DateUtils.getNowDate());
        financeFtAcctDetail.setCreateBy(SecurityUtils.getUsername());
        financeFtAcctDetail.setCreateName(SecurityUtils.getNickName());
        return financeFtAcctDetailMapper.insertFinanceFtAcctDetail(financeFtAcctDetail);
    }

    /**
     * 修改账务代码明细设定
     *
     * @author SunXuTong
     * @date 2023-06-30
     * @param financeFtAcctDetail 账务代码明细设定
     * @return 结果
     */
    @Override
    public int updateFinanceFtAcctDetail(FinanceFtAcctDetail financeFtAcctDetail)
    {
        financeFtAcctDetail.setUpdateTime(DateUtils.getNowDate());
        financeFtAcctDetail.setUpdateBy(SecurityUtils.getUsername());
        financeFtAcctDetail.setUpdateName(SecurityUtils.getNickName());
        return financeFtAcctDetailMapper.updateFinanceFtAcctDetail(financeFtAcctDetail);
    }

    /**
     * 批量删除账务代码明细设定
     *
     * @author SunXuTong
     * @date 2023-06-30
     * @param uuids 需要删除的账务代码明细设定主键
     * @return 结果
     */
    @Override
    public int deleteFinanceFtAcctDetailByUuids(String[] uuids)
    {
        return financeFtAcctDetailMapper.deleteFinanceFtAcctDetailByUuids(uuids);
    }

    /**
     * 删除账务代码明细设定信息
     *
     * @author SunXuTong
     * @date 2023-06-30
     * @param uuid 账务代码明细设定主键
     * @return 结果
     */
    @Override
    public int deleteFinanceFtAcctDetailByUuid(String uuid)
    {
        return financeFtAcctDetailMapper.deleteFinanceFtAcctDetailByUuid(uuid);
    }
}
