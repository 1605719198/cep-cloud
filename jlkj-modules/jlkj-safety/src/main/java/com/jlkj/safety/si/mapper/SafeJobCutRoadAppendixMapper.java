package com.jlkj.safety.si.mapper;

import com.jlkj.safety.si.entity.SafeJobCutRoadAppendix;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.MapKey;

import java.util.List;
import java.util.Map;

/**
* @author zyf
* @description 针对表【safe_si_job_cut_road_appendix(断路作业票-附件表)】的数据库操作Mapper
* @createDate 2022-06-21 13:52:56
* @Entity com.jlkj.safety.si.entity.TSafeJobCutRoadAppendix
*/
public interface SafeJobCutRoadAppendixMapper extends BaseMapper<SafeJobCutRoadAppendix> {

    /**
     * 断路安全作业证-附件列表
     * @param params 查询条件
     * @return 结果列表
     */
    @MapKey("id")
    List<Map<String, Object>> getFiles(Map<String, Object> params);
}




