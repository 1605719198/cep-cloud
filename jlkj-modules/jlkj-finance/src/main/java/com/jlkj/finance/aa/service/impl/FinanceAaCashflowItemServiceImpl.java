package com.jlkj.finance.aa.service.impl;

import java.util.List;

import cn.hutool.core.util.IdUtil;
import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.finance.aa.domain.FinanceAaCashflowCode;
import com.jlkj.finance.aa.dto.FinanceAaCashflowCodeDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jlkj.finance.aa.mapper.FinanceAaCashflowItemMapper;
import com.jlkj.finance.aa.domain.FinanceAaCashflowItem;
import com.jlkj.finance.aa.service.IFinanceAaCashflowItemService;

import static com.jlkj.common.security.utils.SecurityUtils.getUsername;

/**
 * 现金流量代码-对应会计科目Service业务层处理
 *
 * @author 265799
 * @date 2023-04-17
 */
@Service
public class FinanceAaCashflowItemServiceImpl implements IFinanceAaCashflowItemService
{
    @Autowired
    private FinanceAaCashflowItemMapper financeAaCashflowItemMapper;

    /**
     * 查询现金流量代码-对应会计科目
     *
     * @param id 现金流量代码-对应会计科目主键
     * @return 现金流量代码-对应会计科目
     */
    @Override
    public FinanceAaCashflowItem selectFinanceAaCashflowItemById(String id)
    {
        return financeAaCashflowItemMapper.selectFinanceAaCashflowItemById(id);
    }

    /**
     * 查询现金流量代码-对应会计科目列表
     *
     * @param financeAaCashflowItem 现金流量代码-对应会计科目
     * @return 现金流量代码-对应会计科目
     */
    @Override
    public List<FinanceAaCashflowItem> selectFinanceAaCashflowItemList(FinanceAaCashflowItem financeAaCashflowItem)
    {
        return financeAaCashflowItemMapper.selectFinanceAaCashflowItemList(financeAaCashflowItem);
    }

    /**
     * 新增现金流量代码-对应会计科目
     *
     * @param codeList 现金流量代码-对应会计科目
     * @return 结果
     */
    @Override
    public int insertFinanceAaCashflowItem( List<FinanceAaCashflowItem> codeList)
    {
        int insertFinanceAaCashflowCode = 0;
        for ( FinanceAaCashflowItem financeAaCashflowItem:codeList){
            if (financeAaCashflowItem.getId()!=null){
                financeAaCashflowItem.setUpdateTime(DateUtils.getNowDate());
                financeAaCashflowItem.setUpdateBy(getUsername());
                insertFinanceAaCashflowCode =  financeAaCashflowItemMapper.updateFinanceAaCashflowItem(financeAaCashflowItem);
            }else {
                financeAaCashflowItem.setCreateTime(DateUtils.getNowDate());
                financeAaCashflowItem.setCreateBy(getUsername());
                financeAaCashflowItem.setId(IdUtil.randomUUID());
                insertFinanceAaCashflowCode = financeAaCashflowItemMapper.insertFinanceAaCashflowItem(financeAaCashflowItem);
            }
        }

        return insertFinanceAaCashflowCode;
    }

    /**
     * 修改现金流量代码-对应会计科目
     *
     * @param financeAaCashflowItem 现金流量代码-对应会计科目
     * @return 结果
     */
    @Override
    public int updateFinanceAaCashflowItem(FinanceAaCashflowItem financeAaCashflowItem)
    {
        financeAaCashflowItem.setUpdateTime(DateUtils.getNowDate());
        return financeAaCashflowItemMapper.updateFinanceAaCashflowItem(financeAaCashflowItem);
    }

    /**
     * 批量删除现金流量代码-对应会计科目
     *
     * @param ids 需要删除的现金流量代码-对应会计科目主键
     * @return 结果
     */
    @Override
    public int deleteFinanceAaCashflowItemByIds(String[] ids)
    {
        return financeAaCashflowItemMapper.deleteFinanceAaCashflowItemByIds(ids);
    }

    /**
     * 删除现金流量代码-对应会计科目信息
     *
     * @param id 现金流量代码-对应会计科目主键
     * @return 结果
     */
    @Override
    public int deleteFinanceAaCashflowItemById(String id)
    {
        return financeAaCashflowItemMapper.deleteFinanceAaCashflowItemById(id);
    }
}
