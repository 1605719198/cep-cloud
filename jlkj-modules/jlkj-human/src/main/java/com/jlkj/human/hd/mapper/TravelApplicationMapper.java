package com.jlkj.human.hd.mapper;

import com.jlkj.human.hd.domain.TravelApplication;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 出差申请Mapper接口
 * 
 * @author jlkj
 * @date 2023-04-27
 */
public interface TravelApplicationMapper 
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
     * 删除出差申请
     * 
     * @param id 出差申请主键
     * @return 结果
     */
    public int deleteTravelApplicationById(String id);

    /**
     * 批量删除出差申请
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTravelApplicationByIds(String[] ids);

    /**
     * 查询历史出差记录
     *@param id 工号
     * @param empNo 工号
     * @param startDate  出差开始日期
     * @param endDate   出差结束日期
     * @return
     */
    List<TravelApplication> getTravelApplication(@Param("id") String id,@Param("empNo") String empNo,@Param("startDate") String startDate, @Param("endDate") String endDate);
}
