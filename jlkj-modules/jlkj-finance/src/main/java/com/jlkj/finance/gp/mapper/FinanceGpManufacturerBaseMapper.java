package com.jlkj.finance.gp.mapper;

import java.util.List;
import com.jlkj.finance.gp.domain.FinanceGpManufacturerBase;


/**
 * 销售管理-厂商基本资料Mapper接口
 * 
 * @author 265799
 * @date 2023-04-08
 */
public interface FinanceGpManufacturerBaseMapper 
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
     *
     * @param financeGpManufacturerBase 销售管理-厂商基本资料
     * @return 销售管理-厂商基本资料集合
     */
    public List<FinanceGpManufacturerBase> selectFinanceGpManufacturerBaseList(FinanceGpManufacturerBase financeGpManufacturerBase);

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
     * 删除销售管理-厂商基本资料
     * 
     * @param id 销售管理-厂商基本资料主键
     * @return 结果
     */
    public int deleteFinanceGpManufacturerBaseById(String id);

    /**
     * 批量删除销售管理-厂商基本资料
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFinanceGpManufacturerBaseByIds(String[] ids);
}