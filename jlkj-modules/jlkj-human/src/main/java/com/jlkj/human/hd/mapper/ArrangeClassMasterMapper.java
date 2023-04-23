package com.jlkj.human.hd.mapper;

import com.jlkj.human.hd.domain.ArrangeClassMaster;

import java.util.List;

/**
 * 排班记录Mapper接口
 * 
 * @author 266861
 * @date 2023-04-10
 */
public interface ArrangeClassMasterMapper 
{
    /**
     * 查询排班记录
     * 
     * @param id 排班记录主键
     * @return 排班记录
     */
    public ArrangeClassMaster selectArrangeClassMasterById(String id);

    /**
     * 查询排班记录列表
     * 
     * @param arrangeClassMaster 排班记录
     * @return 排班记录集合
     */
    public List<ArrangeClassMaster> selectArrangeClassMasterList(ArrangeClassMaster arrangeClassMaster);

    /**
     * 新增排班记录
     * 
     * @param arrangeClassMaster 排班记录
     * @return 结果
     */
    public int insertArrangeClassMaster(ArrangeClassMaster arrangeClassMaster);

    /**
     * 修改排班记录
     * 
     * @param arrangeClassMaster 排班记录
     * @return 结果
     */
    public int updateArrangeClassMaster(ArrangeClassMaster arrangeClassMaster);

    /**
     * 删除排班记录
     * 
     * @param id 排班记录主键
     * @return 结果
     */
    public int deleteArrangeClassMasterById(String id);

    /**
     * 批量删除排班记录
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteArrangeClassMasterByIds(String[] ids);
}
