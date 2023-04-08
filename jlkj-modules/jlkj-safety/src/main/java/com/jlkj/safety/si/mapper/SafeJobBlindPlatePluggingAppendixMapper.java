package com.jlkj.safety.si.mapper;

import com.jlkj.safety.si.entity.SafeSiJobBlindPlatePluggingAppendix;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.MapKey;

import java.util.List;
import java.util.Map;

/**
* @author zyf
* @description 针对表【safe_si_job_blind_plate_plugging_appendix(抽堵盲板作业票-附件表)】的数据库操作Mapper
* @createDate 2022-06-14 14:08:25
* @Entity com.jlkj.safety.si.entity.TSafeJobBlindPlatePluggingAppendix
*/
public interface SafeJobBlindPlatePluggingAppendixMapper extends BaseMapper<SafeSiJobBlindPlatePluggingAppendix> {

    /**
     * 附件列表
     * @param params 查询条件
     * @return 结果列表
     */
    @MapKey("id")
    List<Map<String, Object>> getFiles(Map<String, Object> params);
}




