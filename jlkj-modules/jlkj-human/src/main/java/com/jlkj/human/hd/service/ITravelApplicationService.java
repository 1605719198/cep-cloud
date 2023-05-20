package com.jlkj.human.hd.service;

import com.jlkj.human.hd.domain.TravelApplication;

import java.util.List;

/**
 * 出差申请Service接口
 * 
 * @author jlkj
 * @date 2023-04-25
 */
public interface ITravelApplicationService 
{
    /**
     * 查询出差申请
     * 
     * @param id 出差申请主键
     * @return 出差申请
     */
    public TravelApplication selectTravelApplicationById(String id);

    /**
     * 查询出差申请列表
     * 
     * @param travelApplication 出差申请
     * @return 出差申请集合
     */
    public List<TravelApplication> selectTravelApplicationList(TravelApplication travelApplication);

    /**
     * 新增出差申请
     * 
     * @param travelApplication 出差申请
     * @return 结果
     */
    public int insertTravelApplication(TravelApplication travelApplication);

    /**
     * 修改出差申请
     * 
     * @param travelApplication 出差申请
     * @return 结果
     */
    public int updateTravelApplication(TravelApplication travelApplication);

    /**
     * 批量删除出差申请
     * 
     * @param ids 需要删除的出差申请主键集合
     * @return 结果
     */
    public int deleteTravelApplicationByIds(String[] ids);

    /**
     * 删除出差申请信息
     * 
     * @param id 出差申请主键
     * @return 结果
     */
    public int deleteTravelApplicationById(String id);
}
