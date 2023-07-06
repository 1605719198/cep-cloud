package com.jlkj.finance.ft.service.impl;

import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.common.core.utils.uuid.IdUtils;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.finance.ft.domain.FinanceFtAcctMain;
import com.jlkj.finance.ft.dto.FinanceFtAcctMainDTO;
import com.jlkj.finance.ft.mapper.FinanceFtAcctMainMapper;
import com.jlkj.finance.ft.service.IFinanceFtAcctDetailService;
import com.jlkj.finance.ft.service.IFinanceFtAcctMainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 账务代码Service业务层处理
 *
 * @author SunXuTong
 * @date 2023-06-27
 */
@Service
public class FinanceFtAcctMainServiceImpl implements IFinanceFtAcctMainService
{
    @Autowired
    private FinanceFtAcctMainMapper financeFtAcctMainMapper;

    @Resource
    private IFinanceFtAcctDetailService iFinanceFtAcctDetailServicel;

    /**
     * 查询账务代码
     *
     * @author SunXuTong
     * @date 2023-06-27
     * @param uuid 账务代码主键
     * @return 账务代码
     */
    @Override
    public FinanceFtAcctMain selectFinanceFtAcctMainByUuid(String uuid)
    {
        return financeFtAcctMainMapper.selectFinanceFtAcctMainByUuid(uuid);
    }

    /**
     * 查询账务代码列表
     *
     * @author SunXuTong
     * @date 2023-06-27
     * @param financeFtAcctMain 账务代码
     * @return 账务代码
     */
    @Override
    public List<FinanceFtAcctMain> selectFinanceFtAcctMainList(FinanceFtAcctMain financeFtAcctMain)
    {
        return financeFtAcctMainMapper.selectFinanceFtAcctMainList(financeFtAcctMain);
    }

    /**
     * 新增账务代码
     *
     * @author SunXuTong
     * @date 2023-06-27
     * @param financeFtAcctMainDTO 账务代码
     * @return 结果
     */
    @Override
    public int insertFinanceFtAcctMain(FinanceFtAcctMainDTO financeFtAcctMainDTO)
    {
        iFinanceFtAcctDetailServicel.saveBatch(financeFtAcctMainDTO.getAcctMainListA());
        financeFtAcctMainDTO.setUuid(IdUtils.fastSimpleUUID());
        return financeFtAcctMainMapper.insertFinanceFtAcctMain(financeFtAcctMainDTO);
    }

    /**
     * 修改账务代码
     *
     * @author SunXuTong
     * @date 2023-06-27
     * @param financeFtAcctMain 账务代码
     * @return 结果
     */
    @Override
    public int updateFinanceFtAcctMain(FinanceFtAcctMain financeFtAcctMain)
    {
        financeFtAcctMain.setUpdateTime(DateUtils.getNowDate());
        financeFtAcctMain.setUpdateBy(SecurityUtils.getUsername());
        financeFtAcctMain.setUpdateName(SecurityUtils.getNickName());
        return financeFtAcctMainMapper.updateFinanceFtAcctMain(financeFtAcctMain);
    }

    /**
     * 批量删除账务代码
     *
     * @author SunXuTong
     * @date 2023-06-27
     * @param uuids 需要删除的账务代码主键
     * @return 结果
     */
    @Override
    public int deleteFinanceFtAcctMainByUuids(String[] uuids)
    {
        return financeFtAcctMainMapper.deleteFinanceFtAcctMainByUuids(uuids);
    }

    /**
     * 删除账务代码信息
     *
     * @author SunXuTong
     * @date 2023-06-27
     * @param uuid 账务代码主键
     * @return 结果
     */
    @Override
    public int deleteFinanceFtAcctMainByUuid(String uuid)
    {
        return financeFtAcctMainMapper.deleteFinanceFtAcctMainByUuid(uuid);
    }
}
