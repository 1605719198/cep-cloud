package com.jlkj.safety.si.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.safety.si.dto.safeimportantwork.PageSafeImportantWorkDTO;
import com.jlkj.safety.si.entity.SafeImportantWork;
import com.jlkj.safety.si.mapper.SafeImportantWorkMapper;
import com.jlkj.safety.si.service.SafeImportantWorkService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * @author yzl
 * @description 针对表【safe_si_important_work(安全重点工作)】的数据库操作Service
 * @createDate 2022年6月6日 11:04:36
 */
@Service
public class SafeImportantWorkServiceImpl extends ServiceImpl<SafeImportantWorkMapper, SafeImportantWork>
        implements SafeImportantWorkService {
    @Override
    @Transactional(readOnly = true)
    public IPage<Map<String, String>> getPageData(PageSafeImportantWorkDTO pageSafeImportantWorkDTO) {
        Page<Map<String, String>> page = new Page<>(pageSafeImportantWorkDTO.getCurrent(), pageSafeImportantWorkDTO.getSize());
        return getBaseMapper().getPageData(page, pageSafeImportantWorkDTO);
    }
}
