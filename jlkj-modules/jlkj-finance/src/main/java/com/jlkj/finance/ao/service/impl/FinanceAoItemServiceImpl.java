package com.jlkj.finance.ao.service.impl;

import com.jlkj.common.core.exception.ServiceException;
import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.common.core.utils.uuid.IdUtils;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.finance.ao.domain.FinanceAoItem;
import com.jlkj.finance.ao.mapper.FinanceAoItemMapper;
import com.jlkj.finance.ao.service.IFinanceAoItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 报支管理-报支类别集团设定主档Service业务层处理
 *
 * @author 116514
 * @date 2023-05-20
 */
@Service
public class FinanceAoItemServiceImpl implements IFinanceAoItemService
{
    @Autowired
    private FinanceAoItemMapper financeAoItemMapper;

    /**
     * 查询报支管理-报支类别集团设定主档
     *
     * @param id 报支管理-报支类别集团设定主档主键
     * @return 报支管理-报支类别集团设定主档
     */
    @Override
    public FinanceAoItem selectFinanceAoItemById(String id)
    {
        return financeAoItemMapper.selectFinanceAoItemById(id);
    }

    /**
     * 查询报支管理-报支类别集团设定主档列表
     *
     * @param financeAoItem 报支管理-报支类别集团设定主档
     * @return 报支管理-报支类别集团设定主档
     */
    @Override
    public List<FinanceAoItem> selectFinanceAoItemList(FinanceAoItem financeAoItem)
    {
        return financeAoItemMapper.selectFinanceAoItemList(financeAoItem);
    }

    /**
     * 新增报支管理-报支类别集团设定主档
     *
     * @param financeAoItem 报支管理-报支类别集团设定主档
     * @return 结果
     */
    @Override
    public int insertFinanceAoItem(FinanceAoItem financeAoItem)
    {
        int count = financeAoItemMapper.updateCheckUnique(financeAoItem.getItemNo());
        if(count!=0){
            throw new ServiceException("报支类别编码："+financeAoItem.getItemNo()+"已经存在，不可重复新增！");
        }
        financeAoItem.setId(IdUtils.simpleUUID());
        financeAoItem.setCreateBy(SecurityUtils.getUsername());
        financeAoItem.setCreateName(SecurityUtils.getNickName());
        financeAoItem.setCreateTime(DateUtils.getNowDate());
        return financeAoItemMapper.insertFinanceAoItem(financeAoItem);
    }

    /**
     * 修改报支管理-报支类别集团设定主档
     *
     * @param financeAoItem 报支管理-报支类别集团设定主档
     * @return 结果
     */
    @Override
    public int updateFinanceAoItem(FinanceAoItem financeAoItem)
    {
        financeAoItem.setUpdateBy(SecurityUtils.getUsername());
        financeAoItem.setUpdateName(SecurityUtils.getNickName());
        financeAoItem.setUpdateTime(DateUtils.getNowDate());
        return financeAoItemMapper.updateFinanceAoItem(financeAoItem);
    }

    /**
     * 批量删除报支管理-报支类别集团设定主档
     *
     * @param ids 需要删除的报支管理-报支类别集团设定主档主键
     * @return 结果
     */
    @Override
    public int deleteFinanceAoItemByIds(String[] ids)
    {
        List<Map<String, String>> itemNos = financeAoItemMapper.selectItemNosByIds(ids);
        if(itemNos.size()>0){
            throw new ServiceException("删除失败，存在细项资料, 请先删除细项资料！");
        }
        return financeAoItemMapper.deleteFinanceAoItemByIds(ids);
    }

    /**
     * 删除报支管理-报支类别集团设定主档信息
     *
     * @param id 报支管理-报支类别集团设定主档主键
     * @return 结果
     */
    @Override
    public int deleteFinanceAoItemById(String id)
    {
        return financeAoItemMapper.deleteFinanceAoItemById(id);
    }

    /**
     * 选取集团报支类别(公司级报支类别用)
     * @param companyId
     * @return
     */
    @Override
    public List<Map<String,Object>> selectItemAndDetailList(String companyId){
        return financeAoItemMapper.selectItemAndDetailList(companyId);
    }
}
