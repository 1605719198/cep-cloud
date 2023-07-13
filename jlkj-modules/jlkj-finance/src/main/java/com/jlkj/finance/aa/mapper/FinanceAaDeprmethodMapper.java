package com.jlkj.finance.aa.mapper;

import com.jlkj.finance.aa.domain.FinanceAaDeprmethod;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 折旧方法维护Mapper接口
 * 
 * @author SunXuTong
 * @date 2023-05-10
 */
@Repository
public interface FinanceAaDeprmethodMapper
{
    /**
     * 查询折旧方法维护
     * 
     * @param id 折旧方法维护主键
     * @return 折旧方法维护
     */
    public FinanceAaDeprmethod selectFinanceAaDeprmethodById(String id);

    /**
     * 查询折旧方法维护列表
     * 
     * @param financeAaDeprmethod 折旧方法维护
     * @return 折旧方法维护集合
     */
    public List<FinanceAaDeprmethod> selectFinanceAaDeprmethodList(FinanceAaDeprmethod financeAaDeprmethod);

    /**
     * 新增折旧方法维护
     * 
     * @param financeAaDeprmethod 折旧方法维护
     * @return 结果
     */
    public int insertFinanceAaDeprmethod(FinanceAaDeprmethod financeAaDeprmethod);

    /**
     * 修改折旧方法维护
     * 
     * @param financeAaDeprmethod 折旧方法维护
     * @return 结果
     */
    public int updateFinanceAaDeprmethod(FinanceAaDeprmethod financeAaDeprmethod);

    /**
     * 删除折旧方法维护
     * 
     * @param id 折旧方法维护主键
     * @return 结果
     */
    public int deleteFinanceAaDeprmethodById(String id);

    /**
     * 批量删除折旧方法维护
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFinanceAaDeprmethodByIds(String[] ids);

    /**
     * 查询折旧方法（下拉选单用）
     * @return
     */
    public List<Map<String,String>> selectDeprMethodList();
}
