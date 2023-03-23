package com.jlkj.safety.si.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.safety.si.dto.safeindex.PageSafeIndexDTO;
import com.jlkj.safety.si.entity.SafeIndex;

import java.util.Map;


/**
 * @author yzl
 * @Description 环保指标项
 * @create 2022年7月19日 08:51:54
 */
public interface SafeIndexService extends IService<SafeIndex> {
    /**
     * 查询
     * @param pageSafeIndexDTO 查询条件dto
     * @return 分页列表
     */
    IPage<Map<String, String>> getListPage(PageSafeIndexDTO pageSafeIndexDTO);
}
