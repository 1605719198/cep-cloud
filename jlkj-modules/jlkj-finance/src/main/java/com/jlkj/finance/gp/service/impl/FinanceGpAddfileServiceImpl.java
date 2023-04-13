package com.jlkj.finance.gp.service.impl;

import java.util.List;
import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.common.core.utils.uuid.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jlkj.finance.gp.mapper.FinanceGpAddfileMapper;
import com.jlkj.finance.gp.domain.FinanceGpAddfile;
import com.jlkj.finance.gp.service.IFinanceGpAddfileService;

/**
 * 厂商增户证照资料Service业务层处理
 *
 * @author 265799
 * @date 2023-04-10
 */
@Service
public class FinanceGpAddfileServiceImpl implements IFinanceGpAddfileService
{
    @Autowired
    private FinanceGpAddfileMapper financeGpAddfileMapper;


    /**
     * 查询厂商增户证照资料
     *
     * @param uuid 厂商增户证照资料主键
     * @return 厂商增户证照资料
     */
    @Override
    public FinanceGpAddfile selectFinanceGpAddfileByUuid(String uuid)
    {
        return financeGpAddfileMapper.selectFinanceGpAddfileByUuid(uuid);
    }

    /**
     * 查询厂商增户证照资料列表
     *
     * @param financeGpAddfile 厂商增户证照资料
     * @return 厂商增户证照资料
     */
    @Override
    public List<FinanceGpAddfile> selectFinanceGpAddfileList(FinanceGpAddfile financeGpAddfile)
    {
        return financeGpAddfileMapper.selectFinanceGpAddfileList(financeGpAddfile);
    }
    /**
     * 查询厂商异动证照资料列表
     *
     * @param financeGpAddfile 查询厂商异动证照资料列表
     * @return 查询厂商异动证照资料列表
     */
    @Override
    public List<FinanceGpAddfile> selectFinanceGpChangeFileList(FinanceGpAddfile financeGpAddfile)
    {
        return financeGpAddfileMapper.selectFinanceGpChangeFileList(financeGpAddfile);
    }

    /**
     * 新增厂商增户证照资料
     *
     * @param financeGpAddfile 厂商增户证照资料
     * @return 结果
     */
    @Override
    public int insertFinanceGpAddfile(FinanceGpAddfile financeGpAddfile)
    {
        financeGpAddfile.setCreateTime(DateUtils.getNowDate());
        if (financeGpAddfile.getUuid()==null){
            financeGpAddfile.setUuid(UUID.fastUUID().toString());
        }
        return financeGpAddfileMapper.insertFinanceGpAddfile(financeGpAddfile);
    }

    /**
     * 修改厂商增户证照资料
     *
     * @param financeGpAddfile 厂商增户证照资料
     * @return 结果
     */
    @Override
    public int updateFinanceGpAddfile(FinanceGpAddfile financeGpAddfile)
    {
        financeGpAddfile.setUpdateTime(DateUtils.getNowDate());
        return financeGpAddfileMapper.updateFinanceGpAddfile(financeGpAddfile);
    }

    /**
     * 批量删除厂商增户证照资料
     *
     * @param uuids 需要删除的厂商增户证照资料主键
     * @return 结果
     */
    @Override
    public int deleteFinanceGpAddfileByUuids(String[] uuids)
    {
        return financeGpAddfileMapper.deleteFinanceGpAddfileByUuids(uuids);
    }

    /**
     * 删除厂商增户证照资料信息
     *
     * @param uuid 厂商增户证照资料主键
     * @return 结果
     */
    @Override
    public int deleteFinanceGpAddfileByUuid(String uuid)
    {

        return financeGpAddfileMapper.deleteFinanceGpAddfileByUuid(uuid);
    }
}
