package com.jlkj.energy.ee.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.common.core.constant.Constants;
import com.jlkj.energy.ee.dto.dashboardenergy.GetDashBoardEnergyDTO;
import com.jlkj.energy.ee.domain.EnergyBoardView1;
import com.jlkj.energy.ee.mapper.EnergyBoardView1Mapper;
import com.jlkj.energy.ee.service.EnergyBoardView1Service;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Liukuiyan
 * 针对表【v_energy_board_view1】的数据库操作Service实现
 * @createDate 2023-02-16 14:50:28
 */
@Service
public class EnergyBoardView1ServiceImpl extends ServiceImpl<EnergyBoardView1Mapper, EnergyBoardView1>
        implements EnergyBoardView1Service {

    /**
     * 能源看板数据查询
     *
     * @param dto GetDashBoardEnergyDTO
     * @return Map<Object>
     * @author Liukuiyan
     * @date 上午 11:05:28 2023年6月29日, 0029
     */
    @Override
    public Map<String, Object> getEngyTopAnalys(GetDashBoardEnergyDTO dto) {
        Map<String, Object> engyTop = this.getEngyTopAnalys();
        List<Map<String, Object>> engyBottom = this.getEngyBottomAnalys(dto);
        List<Object> engyBottomRight = this.getEngyBottomRightAnalys(dto);
        Map<String, Object> res = new HashMap<>(16);
        res.put("engyTop", engyTop);
        res.put("engyBottom", engyBottom);
        res.put("engyBottomRight", engyBottomRight);
        return res;
    }

    /**
     * @return Map<Object>
     * @author Liukuiyan
     * @date 上午 11:13:46 2023年6月29日, 0029
     */
    public Map<String, Object> getEngyTopAnalys() {
        Map<String, Object> returnList = new HashMap<>(1);
        List<Map<String, Object>> energyBoardView1 = baseMapper.getEngyTopAnalys();

        final BigDecimal[] sum1 = {BigDecimal.ZERO}, sum2 = {BigDecimal.ZERO},sum3 = {BigDecimal.ZERO}, sum4 = {BigDecimal.ZERO}, sum5 = {BigDecimal.ZERO};

        energyBoardView1.forEach(item -> {
            Object qty = item.get("qty");
            BigDecimal qtyBig = new BigDecimal(qty.toString());
            BigDecimal qtyScale = qtyBig.setScale(0, RoundingMode.HALF_UP);

            if (item.get(Constants.STR_TYPENAME).equals(Constants.STR_ENGYD)) {
                sum1[0] = sum1[0].add(qtyScale);
            }
            if (item.get(Constants.STR_TYPENAME).equals(Constants.STR_ENGYS)) {
                sum2[0] = sum2[0].add(qtyScale);
            }
            if (item.get(Constants.STR_TYPENAME).equals(Constants.STR_ENGYL)) {
                sum3[0] = sum3[0].add(qtyScale);
            }
            if (item.get(Constants.STR_TYPENAME).equals(Constants.STR_ENGYR)) {
                sum4[0] = sum4[0].add(qtyScale);
            }
            if (item.get(Constants.STR_TYPENAME).equals(Constants.STR_ENGYZ)) {
                sum5[0] = sum5[0].add(qtyScale);
            }
            returnList.put(item.get("energy_code_name").toString(), qtyScale);
        });
        returnList.put("sum1", sum1[0]);
        returnList.put("sum2", sum2[0]);
        returnList.put("sum3", sum3[0]);
        returnList.put("sum4", sum4[0]);
        returnList.put("sum5", sum5[0]);
        return returnList;
    }

    /**
     * 取得下部分析数据
     * @param dto GetDashBoardEnergyDTO
     * @return List<Map < Object>>
     * @author Liukuiyan
     * @date 上午 11:14:06 2023年6月29日, 0029
     */
    public List<Map<String, Object>> getEngyBottomAnalys(GetDashBoardEnergyDTO dto) {
        return baseMapper.getEngyBottomAnalys(dto);
    }
    /**
     * 取得右下方分析数据
     * @author 265800
     * @date 2023/7/12 10:52
     * @param dto GetDashBoardEnergyDTO
     * @return java.util.List<java.lang.Object>
     */
    public List<Object> getEngyBottomRightAnalys(GetDashBoardEnergyDTO dto) {
        List<Map<String, Object>> energyBoardView2 = baseMapper.getEngyBottomAnalys(dto);
        ArrayList<Object> returnList = new ArrayList<>();
        energyBoardView2.forEach(item -> {
            List<Object> data = new ArrayList<>();
            data.add(item.get(Constants.STR_COUNT_DATE));
            data.add(item.get(Constants.STR_TYPENAME));
            data.add(item.get(Constants.STR_QTY).toString());
            returnList.add(data);
        });
        return returnList;
    }
}




