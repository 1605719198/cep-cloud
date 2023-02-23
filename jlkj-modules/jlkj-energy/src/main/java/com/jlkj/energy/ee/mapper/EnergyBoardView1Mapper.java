package com.jlkj.energy.ee.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jlkj.energy.ee.dto.dashboardenergy.GetDashBoardEnergyDTO;
import com.jlkj.energy.ee.domain.EnergyBoardView1;
import org.apache.ibatis.annotations.MapKey;

import java.util.List;
import java.util.Map;

/**
* @author Liukuiyan
* @description 针对表【v_energy_board_view1】的数据库操作Mapper
* @createDate 2023-02-16 14:50:28
* @domain com.jlkj.energy.ee.domain.EnergyBoardView1
*/
public interface EnergyBoardView1Mapper extends BaseMapper<EnergyBoardView1> {

    /**
     * 能源看板-上部分
     * @return
     */
    @MapKey("id")
    List<Map<String, Object>> getEngyTopAnalys();

    /**
     * 能源看板-下部分
     * @param dto 查询条件dto
     * @return 列表数据
     */
    @MapKey("id")
    List<Map<String, Object>> getEngyBottomAnalys(GetDashBoardEnergyDTO dto);
}




