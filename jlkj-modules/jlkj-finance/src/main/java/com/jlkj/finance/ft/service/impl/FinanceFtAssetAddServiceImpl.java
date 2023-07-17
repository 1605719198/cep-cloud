package com.jlkj.finance.ft.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.core.utils.bean.BeanUtils;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.finance.ft.domain.FinanceFtChange;
import com.jlkj.finance.ft.domain.FinanceFtChangeDetail;
import com.jlkj.finance.ft.domain.FinanceFtChangeDevice;
import com.jlkj.finance.ft.dto.FinanceFtAssetAddDTO;
import com.jlkj.finance.ft.mapper.FinanceFtChangeMapper;
import com.jlkj.finance.ft.service.IFinanceFtAssetAddService;
import com.jlkj.finance.ft.service.IFinanceFtChangeDetailService;
import com.jlkj.finance.ft.service.IFinanceFtChangeDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 资产增加单Service业务层处理
 *
 * @author SunXuTong
 * @date 2023-07-11
 * @version 1.0
 */
@Service
public class FinanceFtAssetAddServiceImpl extends ServiceImpl<FinanceFtChangeMapper, FinanceFtChange> implements IFinanceFtAssetAddService
{
    @Autowired
    private FinanceFtChangeMapper financeFtChangeMapper;
    @Resource
    private IFinanceFtChangeDetailService financeFtChangeDetailService;
    @Resource
    private IFinanceFtChangeDeviceService financeFtChangeDeviceService;
    /**
     * 查询资产增加单
     *
     * @author SunXuTong
     * @date 2023-07-11
     * @param uuid 资产增加单主键
     * @return 资产增加单
     */
    @Override
    public FinanceFtChange selectFinanceFtChangeByUuid(String uuid)
    {
        FinanceFtChange financeFtChange = financeFtChangeMapper.selectFinanceFtChangeByUuid(uuid);
        FinanceFtChangeDetail financeFtChangeDetail = financeFtChangeDetailService.lambdaQuery().eq(FinanceFtChangeDetail::getChangeId, uuid).one();
        return financeFtChangeMapper.selectFinanceFtChangeByUuid(uuid);
    }

    /**
     * 查询资产增加单列表
     *
     * @author SunXuTong
     * @date 2023-07-11
     * @param financeFtChange 资产增加单
     * @return 资产增加单
     */
    @Override
    public List<FinanceFtChange> selectFinanceFtChangeList(FinanceFtChange financeFtChange)
    {
        return financeFtChangeMapper.selectFinanceFtChangeList(financeFtChange);
    }

    /**
     * 新增资产增加单
     *
     * @author SunXuTong
     * @date 2023-07-11
     * @param financeFtAssetAddDTO 资产增加单
     * @return 结果
     */
    @Transactional
    @Override
    public Object insertFinanceFtChange(FinanceFtAssetAddDTO financeFtAssetAddDTO)
    {
        FinanceFtChange financeFtChange = new FinanceFtChange();
        FinanceFtChangeDetail financeFtChangeDetail = new FinanceFtChangeDetail();
        FinanceFtChangeDevice financeFtChangeDevice = new FinanceFtChangeDevice();
        BeanUtils.copyProperties(financeFtAssetAddDTO, financeFtChange);
        BeanUtils.copyProperties(financeFtAssetAddDTO, financeFtChangeDetail);
        BeanUtils.copyProperties(financeFtAssetAddDTO, financeFtChangeDevice);
        boolean save = save(financeFtChange);
        boolean save1 = financeFtChangeDetailService.save(financeFtChangeDetail);
        boolean save2 = financeFtChangeDeviceService.save(financeFtChangeDevice);
        if (save && save1 && save2){
            return AjaxResult.success();
        } else {
            return AjaxResult.error();
        }
    }

    /**
     * 修改资产增加单
     *
     * @author SunXuTong
     * @date 2023-07-11
     * @param financeFtChange 资产增加单
     * @return 结果
     */
    @Transactional
    @Override
    public int updateFinanceFtChange(FinanceFtChange financeFtChange)
    {
        financeFtChange.setUpdateTime(DateUtils.getNowDate());
        financeFtChangeMapper.deleteFinanceFtChangeDetailByChangeId(financeFtChange.getUuid());
        insertFinanceFtChangeDetail(financeFtChange);
        return financeFtChangeMapper.updateFinanceFtChange(financeFtChange);
    }

    /**
     * 批量删除资产增加单
     *
     * @author SunXuTong
     * @date 2023-07-11
     * @param uuids 需要删除的资产增加单主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteFinanceFtChangeByUuids(String[] uuids)
    {
        financeFtChangeMapper.deleteFinanceFtChangeDetailByChangeIds(uuids);
        return financeFtChangeMapper.deleteFinanceFtChangeByUuids(uuids);
    }

    /**
     * 删除资产增加单信息
     *
     * @author SunXuTong
     * @date 2023-07-11
     * @param uuid 资产增加单主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteFinanceFtChangeByUuid(String uuid)
    {
        financeFtChangeMapper.deleteFinanceFtChangeDetailByChangeId(uuid);
        return financeFtChangeMapper.deleteFinanceFtChangeByUuid(uuid);
    }

    /**
     * 新增资产变动单主档信息
     *
     * @author SunXuTong
     * @date 2023-07-11
     * @param financeFtChange 资产增加单对象
     */
    public void insertFinanceFtChangeDetail(FinanceFtChange financeFtChange)
    {
        List<FinanceFtChangeDetail> financeFtChangeDetailList = financeFtChange.getFinanceFtChangeDetailList();
        String uuid = financeFtChange.getUuid();
        if (StringUtils.isNotNull(financeFtChangeDetailList))
        {
            List<FinanceFtChangeDetail> list = new ArrayList<FinanceFtChangeDetail>();
            for (FinanceFtChangeDetail financeFtChangeDetail : financeFtChangeDetailList)
            {
                financeFtChangeDetail.setChangeId(uuid);
                list.add(financeFtChangeDetail);
            }
            if (list.size() > 0)
            {
                financeFtChangeMapper.batchFinanceFtChangeDetail(list);
            }
        }
    }
}
