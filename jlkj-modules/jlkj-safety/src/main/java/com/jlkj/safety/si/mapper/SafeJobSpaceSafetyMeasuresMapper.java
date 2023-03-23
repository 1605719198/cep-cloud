package com.jlkj.safety.si.mapper;

import com.jlkj.safety.si.entity.SafeSiJobSpaceSafetyMeasures;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

/**
 * <p>
 *
    Mapper 接口
 * </p>
 *
 * @author su
 * @since 2021-01-13
 */
public interface SafeJobSpaceSafetyMeasuresMapper extends BaseMapper<SafeSiJobSpaceSafetyMeasures> {

    /**
     * 受限空间安全作业证-安全措施模板列表
     * @param condition 查询条件
     * @return 结果
     */
    @Select("select safety_measures from v_safe_job_space_measures where sort=#{sort}")
    Map<String, Object> getOldSafeJobMeasures(Map<String, Object> condition);
}
