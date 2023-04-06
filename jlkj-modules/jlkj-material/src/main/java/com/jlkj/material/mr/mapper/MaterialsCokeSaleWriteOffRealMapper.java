package com.jlkj.material.mr.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jlkj.material.mr.dto.materialscokesalewriteoffreal.ListHomeCokeSaleWriteOffRealDTO;
import com.jlkj.material.mr.dto.materialscokesalewriteoffreal.PageMaterialsCokeSaleWriteOffRealDto;
import com.jlkj.material.mr.domain.MaterialsCokeSaleWriteOffReal;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * Mapper接口-焦炭外售销账实际
 * @author sudeyou
 * @since 2022-08-19 13:58:42
 */
public interface MaterialsCokeSaleWriteOffRealMapper extends BaseMapper<MaterialsCokeSaleWriteOffReal> {

    /**
     * 焦炭外售销账实际-列表
     * @param page 分页参数
     * @param condition 查询条件dto
     * @return 分页列表
     */
    @MapKey("id")
    IPage<Map<String, String>> getListPage(Page<Map<String, String>> page, @Param("condition") PageMaterialsCokeSaleWriteOffRealDto condition);

    /**
     * 焦炭外发-首页-图表
     * @param condition
     * @return
     */
    @MapKey("id")
    List<Map<String, String>> getHomeCokeOut(ListHomeCokeSaleWriteOffRealDTO condition);

    /**
     * 获取当前班次信息
     * @return 当前班次信息
     */
    @Select(
            "select shift,class_type,full_name\n" +
            "from v_humanresource_scheduling_work\n" +
            "where post_id = #{postId} and #{time} >= due_attendance_time_work and #{time} <= due_attendance_time_offduty and shift != '01'"
    )
    Map<String, Object> getShift(Map<String, String> condition);
}

