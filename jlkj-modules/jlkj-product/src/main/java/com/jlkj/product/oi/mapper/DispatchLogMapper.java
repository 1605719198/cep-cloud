package com.jlkj.product.oi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jlkj.product.oi.domain.DispatchLog;
import com.jlkj.product.oi.dto.dispatchlog.PageDispatchLogDTO;
import org.apache.ibatis.annotations.MapKey;

import java.util.Map;

/**
 * Mapper接口-调度日志
 * @author sudeyou
 */
public interface DispatchLogMapper extends BaseMapper<DispatchLog> {

    /**
     * 查询-分页-调度日志
     * @param page 分页
     * @param condition 查询条件dto
     * @return
     */
    @MapKey("id")
    IPage<Map<String, String>> getPageData(Page<?> page, PageDispatchLogDTO condition);
}

