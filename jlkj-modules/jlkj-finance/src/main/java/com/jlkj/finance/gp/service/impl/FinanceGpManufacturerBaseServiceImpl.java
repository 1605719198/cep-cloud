package com.jlkj.finance.gp.service.impl;

import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.finance.gp.domain.FinanceGpManufacturerBase;
import com.jlkj.finance.gp.mapper.FinanceGpManufacturerBaseMapper;
import com.jlkj.finance.gp.service.IFinanceGpManufacturerBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 销售管理-厂商基本资料Service业务层处理
 *
 * @author 265799
 * @date 2023-04-08
 */
@Service
public class FinanceGpManufacturerBaseServiceImpl implements IFinanceGpManufacturerBaseService
{
    @Autowired
    private FinanceGpManufacturerBaseMapper financeGpManufacturerBaseMapper;

    /**
     * 查询销售管理-厂商基本资料
     *
     * @param id 销售管理-厂商基本资料主键
     * @return 销售管理-厂商基本资料
     */
    @Override
    public FinanceGpManufacturerBase selectFinanceGpManufacturerBaseById(String id)
    {
        return financeGpManufacturerBaseMapper.selectFinanceGpManufacturerBaseById(id);
    }
    /**
     * 查询销售管理-厂商基本资料列表
     * @param financeGpManufacturerBase 销售管理-厂商基本资料
     * @return 销售管理-厂商基本资料
     */
    @Override
    public List<FinanceGpManufacturerBase> selectFinanceGpManufacturerBaseList(FinanceGpManufacturerBase financeGpManufacturerBase)
    {
        return financeGpManufacturerBaseMapper.selectFinanceGpManufacturerBaseList(financeGpManufacturerBase);
    }
    /**
     * 弹窗查询厂商基本资料列表
     * @param financeGpManufacturerBase 销售管理-厂商基本资料
     * @return 销售管理-厂商基本资料
     */
    @Override
    public List<FinanceGpManufacturerBase> selectFinanceGpManufacturerBaseListBaseSelect(FinanceGpManufacturerBase financeGpManufacturerBase)
    {
        return financeGpManufacturerBaseMapper.selectFinanceGpManufacturerBaseListSelect(financeGpManufacturerBase);
    }

    /**
     * 新增销售管理-厂商基本资料
     *
     * @param financeGpManufacturerBase 销售管理-厂商基本资料
     * @return 结果
     */
    @Override
    public int insertFinanceGpManufacturerBase(FinanceGpManufacturerBase financeGpManufacturerBase)
    {
        financeGpManufacturerBase.setCreateTime(DateUtils.getNowDate());
        return financeGpManufacturerBaseMapper.insertFinanceGpManufacturerBase(financeGpManufacturerBase);
    }

    /**
     * 修改销售管理-厂商基本资料
     *
     * @param financeGpManufacturerBase 销售管理-厂商基本资料
     * @return 结果
     */
    @Override
    public int updateFinanceGpManufacturerBase(FinanceGpManufacturerBase financeGpManufacturerBase)
    {
        financeGpManufacturerBase.setUpdateTime(DateUtils.getNowDate());
        return financeGpManufacturerBaseMapper.updateFinanceGpManufacturerBase(financeGpManufacturerBase);
    }

    /**
     * 批量删除销售管理-厂商基本资料
     *
     * @param ids 需要删除的销售管理-厂商基本资料主键
     * @return 结果
     */
    @Override
    public int deleteFinanceGpManufacturerBaseByIds(String[] ids)
    {
        return financeGpManufacturerBaseMapper.deleteFinanceGpManufacturerBaseByIds(ids);
    }

    /**
     * 删除销售管理-厂商基本资料信息
     *
     * @param id 销售管理-厂商基本资料主键
     * @return 结果
     */
    @Override
    public int deleteFinanceGpManufacturerBaseById(String id)
    {
        return financeGpManufacturerBaseMapper.deleteFinanceGpManufacturerBaseById(id);
    }

 /**
 *
 *@author 265799
 *@date 2023/7/10  15:32
  * @param companyId : 
  * @return : java.util.List<java.util.Map<java.lang.String,java.lang.String>>
 */        
    @Override
    public List<Map<String,String>> selectManufacturerList(String companyId){
        return financeGpManufacturerBaseMapper.selectManufacturerList(companyId);
    }
}
