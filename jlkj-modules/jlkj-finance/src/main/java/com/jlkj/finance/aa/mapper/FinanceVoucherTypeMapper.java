package com.jlkj.finance.aa.mapper;

import java.util.List;
import java.util.Map;

import com.jlkj.finance.aa.domain.FinanceVoucherType;
import org.springframework.stereotype.Repository;

/**
 * 凭证类别维护Mapper接口
 * 
 * @author 116514
 * @date 2023-04-13
 */
@Repository
public interface FinanceVoucherTypeMapper 
{
    /**
     * 查询凭证类别维护
     * 
     * @param id 凭证类别维护主键
     * @return 凭证类别维护
     */
    public FinanceVoucherType selectFinanceVoucherTypeById(String id);

    /**
     * 查询凭证类别维护列表
     * 
     * @param financeVoucherType 凭证类别维护
     * @return 凭证类别维护集合
     */
    public List<FinanceVoucherType> selectFinanceVoucherTypeList(FinanceVoucherType financeVoucherType);

    /**
     * 新增凭证类别维护
     * 
     * @param financeVoucherType 凭证类别维护
     * @return 结果
     */
    public int insertFinanceVoucherType(FinanceVoucherType financeVoucherType);

    /**
     * 修改凭证类别维护
     * 
     * @param financeVoucherType 凭证类别维护
     * @return 结果
     */
    public int updateFinanceVoucherType(FinanceVoucherType financeVoucherType);

    /**
     * 删除凭证类别维护
     * 
     * @param id 凭证类别维护主键
     * @return 结果
     */
    public int deleteFinanceVoucherTypeById(String id);

    /**
     * 批量删除凭证类别维护
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFinanceVoucherTypeByIds(String[] ids);

    /**
     * 查询凭证类别列表(下拉选单用)
     * @return
     */
    public List<Map<String,String>> selectVoucherTypeList();
}
