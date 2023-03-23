package com.jlkj.safety.si.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jlkj.safety.si.entity.Empty;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 公共-下拉列表 Mapper 接口
 * </p>
 *
 * @author su
 * @since 2020-12-28
 */
public interface CommonComboMapper extends BaseMapper<Empty> {

    /**
     * 安全区域下拉列表
     * @param condition 查询条件
     * @return 结果列表
     */
    @Select(" select id,safe_area_name as name from safe_si_area order by id")
    List<Map<String, Object>> getSafeAreaDropList(Map<String, Object> condition);

    /**
     * 工种下拉列表
     * @param condition 查询条件
     * @return 结果列表
     */
    @Select(" select id,work_type_name as name from safe_si_work_type order by id")
    List<Map<String, Object>> getSafeWorkTypeDropList(Map<String, Object> condition);

    /**
     * 巡检类型下拉列表
     * @param condition 查询条件
     * @return 结果列表
     */
    @Select(" select id,check_type_name as name from safe_si_check_type order by id")
    List<Map<String, Object>> getSafeCheckTypeDropList(Map<String, Object> condition);

    /**
     * 隐患类型下拉列表
     * @param condition 查询条件
     * @return 结果列表
     */
    @Select(" select id,danger_type_name as name from safe_si_danger_type order by id")
    List<Map<String, Object>> getSafeDangerTypeDropList(Map<String, Object> condition);

    /**
     * 工段下拉下拉列表
     * @param condition 查询条件
     * @return 结果列表
     */
    @Select(" select id,name from (\n" +
            " select id as id1, section_name as id,section_name as name from safe_si_workshop_acc_section where workshop_name = #{workshop_name}\n" +
            " ) as a  order by id1")
    List<Map<String, Object>> getSafeWorkshopSectionDropList(Map<String, Object> condition);
}
