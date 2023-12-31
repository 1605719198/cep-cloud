package com.jlkj.safety.si.mapper;

import com.jlkj.safety.si.entity.SafeSiJobSoilAppendix;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.MapKey;

import java.util.List;
import java.util.Map;

/**
* @author zyf
* @description 针对表【safe_si_job_soil_appendix(动土作业票-附件表)】的数据库操作Mapper
* @createDate 2022-06-16 14:56:23
* @Entity com.jlkj.safety.si.entity.TSafeJobSoilAppendix
*/
public interface SafeJobSoilAppendixMapper extends BaseMapper<SafeSiJobSoilAppendix> {

    /**
     * 动土安全作业证-附件列表
     * @param params 查询条件
     * @return 结果列表
     */
    @MapKey("id")
    List<Map<String, Object>> getFiles(Map<String, Object> params);
}




