package com.jlkj.rabbit.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jlkj.rabbit.entity.TsExperimentRecord;
import org.springframework.stereotype.Repository;

/**
* @author 265675
* @description 针对表【t_ts_experiment_record(质量记录表)】的数据库操作Mapper
* @createDate 2022-09-06 13:38:03
* @Entity com.jljl.messagecenter.entity.TsExperimentRecord
*/
@Repository
public interface TsExperimentRecordMapper extends BaseMapper<TsExperimentRecord> {
//    @Select("select case shift when '2201' then '白班' else '夜班' end as shift \n" +
//                    "from v_humanresource_scheduling_work where shift != '01'\n" +
//                    "and #{time} >= due_attendance_time_work and #{time} <= due_attendance_time_offduty")
    String getShift(String condition);
}




