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
    /**
     * 取得班别
     * @author 265675
     * @Date 下午 02:30:06 2023年6月26日, 0026
     * @param condition
     * @return String
     */
    String getShift(String condition);
}




