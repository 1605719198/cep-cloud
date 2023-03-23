package com.jlkj.safety.si.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.safety.si.dto.safesiteenvironmentalgovernance.PageSafeSiteEnvironmentalGovernanceDTO;
import com.jlkj.safety.si.entity.SafeSiteEnvironmentalGovernance;
import com.jlkj.safety.si.mapper.SafeSiteEnvironmentalGovernanceMapper;
import com.jlkj.safety.si.service.SafeSiteEnvironmentalGovernanceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author yzl
 * @description 针对表【safe_si_site_environmental_governance(现场环境治理)】的数据库操作Service
 * @createDate 2022年6月8日 11:04:36
 */
@Service
@Slf4j
public class SafeSiteEnvironmentalGovernanceServiceImpl extends ServiceImpl<SafeSiteEnvironmentalGovernanceMapper, SafeSiteEnvironmentalGovernance>
        implements SafeSiteEnvironmentalGovernanceService {
    @Override
    public IPage<Map<String, String>> getListPage(PageSafeSiteEnvironmentalGovernanceDTO pageSafeSiteEnvironmentalGovernanceDTO) {
        Page<Map<String, String>> page = new Page<>(pageSafeSiteEnvironmentalGovernanceDTO.getCurrent(), pageSafeSiteEnvironmentalGovernanceDTO.getSize());
        return getBaseMapper().getListPage(page, pageSafeSiteEnvironmentalGovernanceDTO);
    }
}

