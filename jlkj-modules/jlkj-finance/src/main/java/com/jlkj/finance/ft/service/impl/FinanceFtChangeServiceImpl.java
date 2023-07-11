package com.jlkj.finance.ft.service.impl;

import java.util.List;
import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.common.core.utils.uuid.IdUtils;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.finance.utils.ConstantsUtil;
import com.jlkj.system.api.model.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Map;

import com.jlkj.common.core.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import com.jlkj.finance.ft.domain.FinanceFtChangeDetail;
import com.jlkj.finance.ft.mapper.FinanceFtChangeMapper;
import com.jlkj.finance.ft.domain.FinanceFtChange;
import com.jlkj.finance.ft.service.IFinanceFtChangeService;

/**
 * 资产变动单主档Service业务层处理
 *
 * @author jlkj
 * @date 2023-07-04
 * @version 1.0
 */
@Service
public class FinanceFtChangeServiceImpl implements IFinanceFtChangeService
{
    @Autowired
    private FinanceFtChangeMapper financeFtChangeMapper;

    /**
     * 查询资产变动单主档
     *
     * @author jlkj
     * @date 2023-07-04
     * @param uuid 资产变动单主档主键
     * @return 资产变动单主档
     */
    @Override
    public FinanceFtChange selectFinanceFtChangeByUuid(String uuid)
    {
        return financeFtChangeMapper.selectFinanceFtChangeByUuid(uuid);
    }

    /**
     * 查询资产变动单主档列表
     *
     * @author jlkj
     * @date 2023-07-04
     * @param financeFtChange 资产变动单主档
     * @return 资产变动单主档
     */
    @Override
    public List<FinanceFtChange> selectFinanceFtChangeList(FinanceFtChange financeFtChange)
    {
        return financeFtChangeMapper.selectFinanceFtChangeList(financeFtChange);
    }

    /**
     * 新增资产变动单主档
     *
     * @author jlkj
     * @date 2023-07-04
     * @param financeFtChange 资产变动单主档
     * @return 结果
     */
    @Transactional
    @Override
    public int insertFinanceFtChange(FinanceFtChange financeFtChange)
    {
        financeFtChange.setCreateTime(DateUtils.getNowDate());
        financeFtChange.setUuid(IdUtils.simpleUUID());
        financeFtChange.setChangeNo(this.getChangeNo(financeFtChange.getCompanyId(), ConstantsUtil.STR_C));
        financeFtChange.setStatus("00");
        financeFtChange.setCreateName(SecurityUtils.getNickName());
        int rows = financeFtChangeMapper.insertFinanceFtChange(financeFtChange);
        insertFinanceFtChangeDetail(financeFtChange);
        return rows;
    }

    /**
     * 修改资产变动单主档
     *
     * @author jlkj
     * @date 2023-07-04
     * @param financeFtChange 资产变动单主档
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
     * 批量删除资产变动单主档
     *
     * @author jlkj
     * @date 2023-07-04
     * @param uuids 需要删除的资产变动单主档主键
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
     * 删除资产变动单主档信息
     *
     * @author jlkj
     * @date 2023-07-04
     * @param uuid 资产变动单主档主键
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
     * @author jlkj
     * @date 2023-07-04
     * @param financeFtChange 资产变动单主档对象
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
                financeFtChangeDetail.setUuid(IdUtils.simpleUUID());
                financeFtChangeDetail.setChangeId(uuid);
                list.add(financeFtChangeDetail);
            }
            if (list.size() > 0)
            {
                financeFtChangeMapper.batchFinanceFtChangeDetail(list);
            }
        }
    }
    /**
    * @Description: 生成变动单号
    * @param:[companyId 公司别, changeType 单据类型]
    * @return:java.lang.String 变动单号
    * @Author: 116524_wxj
    * @Date: 2023/7/11 17:20
    */
    public String  getChangeNo(String companyId,String changeType) {
        String changeNoInit = "00001" ;
        String dateStr = DateUtils.getDateYMD();
        String changeNoStr = dateStr.substring(2, 6) + "FT"+changeType;
        String maxBillNo = financeFtChangeMapper.selectMaxChangeNo(companyId, changeNoStr);
        String seqNo = StringUtils.isEmpty(maxBillNo) ? changeNoInit : String.format("%05d",(Integer.parseInt(maxBillNo.substring(7, 12)) + 1));
        return changeNoStr+seqNo;
    }
}
