package com.jlkj.finance.gp.service;

import java.util.List;
import com.jlkj.finance.gp.domain.FinanceGpManufacturerBase;


/**
 * 销售管理-厂商基本资料Service接口
 * 
 * @author 265799
 * @date 2023-04-08
 */
public interface IFinanceGpManufacturerBaseService 
{
    /**
     * 查询销售管理-厂商基本资料
     * 
     * @param id 销售管理-厂商基本资料主键
     * @return 销售管理-厂商基本资料
     */
    public FinanceGpManufacturerBase selectFinanceGpManufacturerBaseById(String id);

    /**
     * 查询销售管理-厂商基本资料列表
     * @param financeGpManufacturerBase 销售管理-厂商基本资料
     * @return 销售管理-厂商基本资料集合
     */
    public List<FinanceGpManufacturerBase> selectFinanceGpManufacturerBaseList(FinanceGpManufacturerBase financeGpManufacturerBase);
    /**
     * 弹窗查询厂商基本资料列表
     * @param financeGpManufacturerBase 销售管理-厂商基本资料
     * @return 销售管理-厂商基本资料集合
     */
    public List<FinanceGpManufacturerBase> selectFinanceGpManufacturerBaseListBaseSelect(FinanceGpManufacturerBase financeGpManufacturerBase);

    /**
     * 新增销售管理-厂商基本资料
     * 
     * @param financeGpManufacturerBase 销售管理-厂商基本资料
     * @return 结果
     */
    public int insertFinanceGpManufacturerBase(FinanceGpManufacturerBase financeGpManufacturerBase);

    /**
     * 修改销售管理-厂商基本资料
     * 
     * @param financeGpManufacturerBase 销售管理-厂商基本资料
     * @return 结果
     */
    public int updateFinanceGpManufacturerBase(FinanceGpManufacturerBase financeGpManufacturerBase);

    /**
     * 批量删除销售管理-厂商基本资料
     * 
     * @param ids 需要删除的销售管理-厂商基本资料主键集合
     * @return 结果
     */
    public int deleteFinanceGpManufacturerBaseByIds(String[] ids);

    /**
     * 删除销售管理-厂商基本资料信息
     * 
     * @param id 销售管理-厂商基本资料主键
     * @return 结果
     */
    public int deleteFinanceGpManufacturerBaseById(String id);
}
