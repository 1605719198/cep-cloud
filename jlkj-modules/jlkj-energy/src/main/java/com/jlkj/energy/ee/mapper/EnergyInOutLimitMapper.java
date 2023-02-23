package com.jlkj.energy.ee.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jlkj.energy.ee.dto.energyinoutlimit.PageEnergyInOutLimitDTO;
import com.jlkj.energy.ee.domain.EnergyInOutLimit;
import org.apache.ibatis.annotations.MapKey;

import java.util.Map;

/**
* @author Liukuiyanb
* @description 针对表【t_energy_in_out_limit(能源产耗标准配置表)】的数据库操作Mapper
* @createDate 2023-02-13 16:39:04
* @domain com.jlkj.energy.ee.domain.EnergyInOutLimit
*/
public interface EnergyInOutLimitMapper extends BaseMapper<EnergyInOutLimit> {

    /**
     * 生产信息配置-查询-分页
     * @param page 分页
     * @param pageEnergyInOutLimitDTO 查询条件dto
     * @return 分页数据
     */
    @MapKey("id")
    IPage<EnergyInOutLimit> getEnergyInOutLimitPageData(Page<Map<String, Object>> page, PageEnergyInOutLimitDTO pageEnergyInOutLimitDTO);
}




