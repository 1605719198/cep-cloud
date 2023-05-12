package com.jlkj.finance.aa.service;

import com.jlkj.finance.aa.domain.financeAaDeprmethod;

import java.util.List;

/**
 * 折旧方法维护Service接口
 * 
 * @author SunXuTong
 * @date 2023-05-10
 */
public interface IfinanceAaDeprmethodService 
{
    /**
     * 查询折旧方法维护
     * 
     * @param id 折旧方法维护主键
     * @return 折旧方法维护
     */
    public financeAaDeprmethod selectfinanceAaDeprmethodById(String id);

    /**
     * 查询折旧方法维护列表
     * 
     * @param financeAaDeprmethod 折旧方法维护
     * @return 折旧方法维护集合
     */
    public List<financeAaDeprmethod> selectfinanceAaDeprmethodList(financeAaDeprmethod financeAaDeprmethod);

    /**
     * 新增折旧方法维护
     * 
     * @param financeAaDeprmethod 折旧方法维护
     * @return 结果
     */
    public int insertfinanceAaDeprmethod(financeAaDeprmethod financeAaDeprmethod);

    /**
     * 修改折旧方法维护
     * 
     * @param financeAaDeprmethod 折旧方法维护
     * @return 结果
     */
    public int updatefinanceAaDeprmethod(financeAaDeprmethod financeAaDeprmethod);

    /**
     * 批量删除折旧方法维护
     * 
     * @param ids 需要删除的折旧方法维护主键集合
     * @return 结果
     */
    public int deletefinanceAaDeprmethodByIds(String[] ids);

    /**
     * 删除折旧方法维护信息
     * 
     * @param id 折旧方法维护主键
     * @return 结果
     */
    public int deletefinanceAaDeprmethodById(String id);
}
