package com.jlkj.finance.aa.service.impl;

import java.util.List;

import com.jlkj.common.core.exception.ServiceException;
import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.common.core.utils.uuid.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jlkj.finance.aa.mapper.FinanceAaIndexMapper;
import com.jlkj.finance.aa.domain.FinanceAaIndex;
import com.jlkj.finance.aa.service.IFinanceAaIndexService;

/**
 * 系统选单-索引设定Service业务层处理
 *
 * @author jlkj
 * @date 2023-04-19
 */
@Service
public class FinanceAaIndexServiceImpl implements IFinanceAaIndexService
{
    @Autowired
    private FinanceAaIndexMapper financeAaIndexMapper;

    /**
     * 查询系统选单-索引设定
     *
     * @param id 系统选单-索引设定主键
     * @return 系统选单-索引设定
     */
    @Override
    public FinanceAaIndex selectFinanceAaIndexById(String id)
    {
        return financeAaIndexMapper.selectFinanceAaIndexById(id);
    }

    /**
     * 查询系统选单-索引设定列表
     *
     * @param financeAaIndex 系统选单-索引设定
     * @return 系统选单-索引设定
     */
    @Override
    public List<FinanceAaIndex> selectFinanceAaIndexList(FinanceAaIndex financeAaIndex)
    {
        return financeAaIndexMapper.selectFinanceAaIndexList(financeAaIndex);
    }

    /**
     * 新增系统选单-索引设定
     *
     * @param financeAaIndex 系统选单-索引设定
     * @return 结果
     */
    @Override
    public int insertFinanceAaIndex(FinanceAaIndex financeAaIndex)
    {
        financeAaIndex.setCreateTime(DateUtils.getNowDate());
        financeAaIndex.setId(UUID.fastUUID().toString());
        FinanceAaIndex financeAaIndex1 = new FinanceAaIndex();
        financeAaIndex1.setCompanyId(financeAaIndex.getCompanyId());
        financeAaIndex1.setNodeNo(financeAaIndex.getNodeNo());
        List<FinanceAaIndex> financeAaIndices = financeAaIndexMapper.selectFinanceAaIndexList(financeAaIndex1);
        if (financeAaIndices.size()>0){
            throw new ServiceException("该系统选单索引设定已存在！");
        }

        return financeAaIndexMapper.insertFinanceAaIndex(financeAaIndex);
    }

    /**
     * 修改系统选单-索引设定
     *
     * @param financeAaIndex 系统选单-索引设定
     * @return 结果
     */
    @Override
    public int updateFinanceAaIndex(FinanceAaIndex financeAaIndex)
    {
        financeAaIndex.setUpdateTime(DateUtils.getNowDate());

        String nodeType = "DIR";
        if (nodeType.equals(financeAaIndex.getNodeType())){
         FinanceAaIndex financeAaIndex1 = new FinanceAaIndex();
            financeAaIndex1.setParentId(financeAaIndex.getId());
        List<FinanceAaIndex> financeAaIndices = financeAaIndexMapper.selectFinanceAaIndexList(financeAaIndex1);
        if (financeAaIndices.size()>0){
            throw new ServiceException("该系统选单索引设定下层有资料不允许修改类别，请先删除下层资料！");
        }
        }
        return financeAaIndexMapper.updateFinanceAaIndex(financeAaIndex);
    }

    /**
     * 批量删除系统选单-索引设定
     *
     * @param ids 需要删除的系统选单-索引设定主键
     * @return 结果
     */
    @Override
    public int deleteFinanceAaIndexByIds(String[] ids)
    {
        for (String id:ids){
            FinanceAaIndex financeAaIndex1 = new FinanceAaIndex();
            financeAaIndex1.setParentId(id);
            List<FinanceAaIndex> financeAaIndices = financeAaIndexMapper.selectFinanceAaIndexList(financeAaIndex1);
            if (financeAaIndices.size()>0){
                throw new ServiceException("该系统选单索引设定下层有资料不允许删除！");
            }
        }


        return financeAaIndexMapper.deleteFinanceAaIndexByIds(ids);
    }

    /**
     * 删除系统选单-索引设定信息
     *
     * @param id 系统选单-索引设定主键
     * @return 结果
     */
    @Override
    public int deleteFinanceAaIndexById(String id)
    {
        return financeAaIndexMapper.deleteFinanceAaIndexById(id);
    }
}
