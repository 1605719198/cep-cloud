package com.jlkj.safety.si.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.safety.si.dto.safesiteenvironmentalgovernance.PageSafeSiteEnvironmentalGovernanceDTO;
import com.jlkj.safety.si.entity.SafeSiteEnvironmentalGovernance;

import java.util.Map;

/**
 * @author yzl
 * @description 针对表【safe_si_site_environmental_governance(现场环境治理)】的数据库操作Service
 * @createDate 2022年6月8日 11:04:36
 */
public interface SafeSiteEnvironmentalGovernanceService extends IService<SafeSiteEnvironmentalGovernance> {

    /**
     * 分页列表
     *
     * @param pageSafeSiteEnvironmentalGovernanceDTO 查询条件dto
     * @return IPage<Map < String, String>>
     */
    IPage<Map<String, String>> getListPage(PageSafeSiteEnvironmentalGovernanceDTO pageSafeSiteEnvironmentalGovernanceDTO);
}
