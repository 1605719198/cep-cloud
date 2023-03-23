package com.jlkj.safety.si.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jlkj.safety.si.dto.environmentalcollectionpoints.PageEnvironmentalCollectionPointsDTO;
import com.jlkj.safety.si.entity.EnvironmentalCollectionPoints;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @author yzl
 * @create 2022-12-14 09:04:30.933272
 */
public interface EnvironmentalCollectionPointsMapper extends BaseMapper<EnvironmentalCollectionPoints> {

    /**
     * 查询环保采集点位
     * @param sort
     * @return
     */
    @Select("select * from safe_si_environmental_collection_points where '' = #{sort} or sort = #{sort} ")
    List<Map<String, String>> getEnvironmentalCollectionPoints(String sort);

    /**
     * 查询-分页-环保采集点位
     * @param page 分页
     * @param condition 查询条件dto
     * @return
     */
    @MapKey("id")
    IPage<Map<String, String>> getPageData(Page<?> page, @Param("condition") PageEnvironmentalCollectionPointsDTO condition);
}


