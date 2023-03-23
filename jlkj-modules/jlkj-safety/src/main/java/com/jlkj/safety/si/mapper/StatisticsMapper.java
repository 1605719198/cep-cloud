package com.jlkj.safety.si.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jlkj.safety.si.entity.Empty;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 安环统计 Mapper 接口
 * </p>
 *
 * @author su
 * @since 2020-12-28
 */
public interface StatisticsMapper extends BaseMapper<Empty> {

    /**
     * 报警历史
     * @param page 分页
     * @param condition 查询条件
     * @return 结果列表
     */
    @Select(
            "select id,safe_area_name,alarm_type,alarm_position,DATE_FORMAT(alarm_time,'%Y-%m-%d %H:%i:%s') as alarm_time " +
            "from safe_si_alarm_his where alarm_time >= #{condition.start_time} and alarm_time <= #{condition.end_time} " +
            "order by ${condition.order} ${condition.orderby}"
    )
    List<Map<String, Object>> getSafeAlarmHisList(Page<?> page, @Param("condition") Map<String, Object> condition);

}
