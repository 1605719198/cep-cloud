package com.jlkj.finance.ft.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.core.utils.bean.BeanUtils;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.finance.ft.domain.FinanceFtChange;
import com.jlkj.finance.ft.domain.FinanceFtChangeDetail;
import com.jlkj.finance.ft.dto.FinanceFtAssetAddDTO;
import com.jlkj.finance.ft.mapper.FinanceFtChangeMapper;
import com.jlkj.finance.ft.service.IFinanceFtAssetSubtractService;
import com.jlkj.finance.ft.service.IFinanceFtChangeDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 资产减少单Service业务层处理
 *
 * @author SunXuTong
 * @date 2023-07-15
 * @version 1.0
 */
@Service
public class FinanceFtAssetSubtractServiceImpl extends ServiceImpl<FinanceFtChangeMapper, FinanceFtChange> implements IFinanceFtAssetSubtractService
{
    @Autowired
    private FinanceFtChangeMapper financeFtChangeMapper;

    @Resource
    private IFinanceFtChangeDetailService financeFtChangeDetailService;

    /**
     * 查询资产减少单
     *
     * @author SunXuTong
     * @date 2023-07-15
     * @param uuid 资产减少单主键
     * @return 资产减少单
     */
    @Override
    public FinanceFtChange selectFinanceFtChangeByUuid(String uuid)
    {
        return financeFtChangeMapper.selectFinanceFtChangeByUuid(uuid);
    }

    /**
     * 查询资产减少单列表
     *
     * @author SunXuTong
     * @date 2023-07-15
     * @param financeFtAssetSubtract 资产减少单
     * @return 资产减少单
     */
    @Override
    public List<FinanceFtChange> selectFinanceFtChangeList(FinanceFtChange financeFtChange)
    {
        return financeFtChangeMapper.selectFinanceFtChangeList(financeFtChange);
    }

    @Override
    public int insertFinanceFtChange(FinanceFtChange financeFtChange) {
        return 0;
    }

    /**
     * 新增资产减少单
     *
     * @param
     * @return 结果
     * @author SunXuTong
     * @date 2023-07-15
     */
    @Transactional
    @Override
    public Object insertFinanceFtChange(FinanceFtAssetAddDTO financeFtAssetAddDTO)
    {
        FinanceFtChange financeFtChange = new FinanceFtChange();
        FinanceFtChangeDetail financeFtChangeDetail = new FinanceFtChangeDetail();
        BeanUtils.copyProperties(financeFtAssetAddDTO, financeFtChange);
        BeanUtils.copyProperties(financeFtAssetAddDTO, financeFtChangeDetail);
        boolean save = save(financeFtChange);
        boolean save1 = financeFtChangeDetailService.save(financeFtChangeDetail);
        if (save && save1){
            return AjaxResult.success();
        } else {
            return AjaxResult.error();
        }
    }

    /**
     * 修改资产减少单
     *
     * @author SunXuTong
     * @date 2023-07-15
     * @param financeFtChange 资产减少单
     * @return 结果
     */
    @Override
    public int updateFinanceFtChange(FinanceFtChange financeFtChange)
    {
        financeFtChange.setUpdateTime(DateUtils.getNowDate());
        financeFtChangeMapper.deleteFinanceFtChangeDetailByChangeId(financeFtChange.getUuid());
        insertFinanceFtChangeDetail(financeFtChange);
        return financeFtChangeMapper.updateFinanceFtChange(financeFtChange);
    }

    /**
     * 批量删除资产减少单
     *
     * @author SunXuTong
     * @date 2023-07-15
     * @param uuids 需要删除的资产减少单主键
     * @return 结果
     */
    @Override
    public int deleteFinanceFtChangeByUuids(String[] uuids)
    {
        return financeFtChangeMapper.deleteFinanceFtChangeByUuids(uuids);
    }

    /**
     * 删除资产减少单信息
     *
     * @author SunXuTong
     * @date 2023-07-15
     * @param uuid 资产减少单主键
     * @return 结果
     */
    @Override
    public int deleteFinanceFtChangeByUuid(String uuid)
    {
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
