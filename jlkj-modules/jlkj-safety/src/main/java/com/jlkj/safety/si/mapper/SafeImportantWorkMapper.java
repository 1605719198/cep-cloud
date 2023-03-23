package com.jlkj.safety.si.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jlkj.safety.si.dto.safeimportantwork.PageSafeImportantWorkDTO;
import com.jlkj.safety.si.entity.SafeImportantWork;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * Mapper接口
 * @author yzl
 * @createDate 2022年6月6日 13:20:45
 */
public interface SafeImportantWorkMapper extends BaseMapper<SafeImportantWork> {

    /**
     * 查询
     * @param page
     * @param condition
     * @return
     */
    @MapKey("id")
    IPage<Map<String, String>> getPageData(Page<?> page,@Param("condition") PageSafeImportantWorkDTO condition);
}
