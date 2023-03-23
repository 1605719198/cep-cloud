package com.jlkj.safety.si.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jlkj.safety.si.dto.safesiteenvironmentalgovernance.PageSafeSiteEnvironmentalGovernanceDTO;
import com.jlkj.safety.si.entity.SafeSiteEnvironmentalGovernance;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * Mapper接口
 * @author yzl
 * @createDate 2022年6月7日 13:20:45
 */
public interface SafeSiteEnvironmentalGovernanceMapper extends BaseMapper<SafeSiteEnvironmentalGovernance> {

    /**
     * 查询
     * @param page
     * @param condition
     * @return
     */
    @MapKey("id")
    IPage<Map<String, String>> getListPage(Page<Map<String, String>> page,@Param("condition") PageSafeSiteEnvironmentalGovernanceDTO condition);

}


