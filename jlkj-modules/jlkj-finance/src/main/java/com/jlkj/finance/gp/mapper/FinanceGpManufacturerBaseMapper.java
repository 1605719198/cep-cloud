package com.jlkj.finance.gp.mapper;

import com.jlkj.finance.gp.domain.FinanceGpManufacturerBase;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


/**
 * 销售管理-厂商基本资料Mapper接口
 * 
 * @author 265799
 * @date 2023-04-08
 */
@Repository
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
     * 查询销售管理-厂商基本资料列表
     *
     * @param financeGpManufacturerBase 销售管理-厂商基本资料
     * @return 销售管理-厂商基本资料集合
     */
    public List<FinanceGpManufacturerBase> selectFinanceGpManufacturerBaseListSelect(FinanceGpManufacturerBase financeGpManufacturerBase);
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

/**
*
*@author 265799
*@date 2023/7/10  15:31
 * @param companyId : 
 * @return : java.util.List<java.util.Map<java.lang.String,java.lang.String>>
*/        

    public List<Map<String,String>> selectManufacturerList(String companyId);
}
