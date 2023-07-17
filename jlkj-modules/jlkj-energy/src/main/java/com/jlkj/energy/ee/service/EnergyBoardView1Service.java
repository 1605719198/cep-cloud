package com.jlkj.energy.ee.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.energy.ee.domain.EnergyBoardView1;
import com.jlkj.energy.ee.dto.dashboardenergy.GetDashBoardEnergyDTO;

import java.util.Map;

/**
 * @author Liukuiyan
 * 针对表【v_energy_board_view1】的数据库操作Service
 * @createDate 2023-02-16 14:50:28
 */
public interface EnergyBoardView1Service extends IService<EnergyBoardView1> {
    /**
     * 能源看板数据查询
     * @param dto GetDashBoardEnergyDTO
     * @return Map<Object>
     * @author 111191
     * @date 上午 11:07:32 2023年6月29日, 0029
     */
    Map<String, Object> getEngyTopAnalys(GetDashBoardEnergyDTO dto);

}
