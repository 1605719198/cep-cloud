package com.jlkj.safety.si.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.safety.si.dto.safeimportantwork.PageSafeImportantWorkDTO;
import com.jlkj.safety.si.entity.SafeImportantWork;

import java.util.Map;

/**
 * @author yzl
 * @description 针对表【safe_si_important_work(安全重点工作)】的数据库操作Service
 * @createDate 2022年6月6日 11:04:36
 */
public interface SafeImportantWorkService extends IService<SafeImportantWork> {
    /**
     * 安全重点工作
     * @param pageSafeImportantWorkDTO 查询条件dto
     * @return 分页列表
     */
    IPage<Map<String, String>> getPageData(PageSafeImportantWorkDTO pageSafeImportantWorkDTO);
}
