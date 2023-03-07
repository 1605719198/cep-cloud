package com.jlkj.material.mr.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jlkj.material.mr.dto.materialssiloarea.ListMaterialsSiloAreaDTO;
import com.jlkj.material.mr.domain.MaterialsSiloArea;
import org.apache.ibatis.annotations.MapKey;

import java.util.List;
import java.util.Map;

/**
 * Mapper接口-料仓区域维护
 * @author sudeyou
 */
public interface MaterialsSiloAreaMapper extends BaseMapper<MaterialsSiloArea> {

    /**
     * 查询-列表-料仓区域维护表
     * @param condition 查询条件dto
     * @return
     */
    @MapKey("id")
    List<Map<String, String>> getListData(ListMaterialsSiloAreaDTO condition);

}
