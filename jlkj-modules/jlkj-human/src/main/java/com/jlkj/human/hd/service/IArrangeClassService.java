package com.jlkj.human.hd.service;

import com.jlkj.human.hd.domain.ArrangeClass;

import java.util.List;

/**
 * 排班明细Service接口
 * 
 * @author 266861
 * @date 2023-04-10
 */
public interface IArrangeClassService 
{
    /**
     * 查询排班明细
     * 
     * @param id 排班明细主键
     * @return 排班明细
     */
    public ArrangeClass selectArrangeClassById(String id);

    /**
     * 查询排班明细
     *
     * @param arrangeClass 排班明细
     * @return 排班明细
     */
    public ArrangeClass selectArrangeClassByClass(ArrangeClass arrangeClass);

    /**
     * 查询排班明细列表
     * 
     * @param arrangeClass 排班明细
     * @return 排班明细集合
     */
    public List<ArrangeClass> selectArrangeClassList(ArrangeClass arrangeClass);

    /**
     * 查询排班明细列表
     *
     * @param arrangeClass 排班明细
     * @return 排班明细集合
     */
    public List<ArrangeClass> queryArrangeClass(ArrangeClass arrangeClass);

    /**
     * 新增排班明细
     * 
     * @param arrangeClass 排班明细
     * @return 结果
     */
    public int insertArrangeClass(ArrangeClass arrangeClass);

    /**
     * 修改排班明细
     * 
     * @param arrangeClass 排班明细
     * @return 结果
     */
    public int updateArrangeClass(ArrangeClass arrangeClass);

    /**
     * 批量删除排班明细
     * 
     * @param ids 需要删除的排班明细主键集合
     * @return 结果
     */
    public int deleteArrangeClassByIds(String[] ids);

    /**
     * 删除排班明细信息
     * 
     * @param id 排班明细主键
     * @return 结果
     */
    public int deleteArrangeClassById(String id);
}
