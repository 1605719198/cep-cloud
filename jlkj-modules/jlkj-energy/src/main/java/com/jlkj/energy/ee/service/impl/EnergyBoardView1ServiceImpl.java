package com.jlkj.energy.ee.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
* @description 针对表【v_energy_board_view1】的数据库操作Service实现
* @createDate 2023-02-16 14:50:28
*/
@Service
public class EnergyBoardView1ServiceImpl extends ServiceImpl<EnergyBoardView1Mapper, EnergyBoardView1>
    implements EnergyBoardView1Service{
    public Map<String, Object> getEngyTopAnalys() {
        Map<String, Object> returnList = new HashMap<>(1);
        List<Map<String, Object>> energyBoardView1 = baseMapper.getEngyTopAnalys();

        final BigDecimal[] sum1 = {new BigDecimal("0")};
        final BigDecimal[] sum2 = {new BigDecimal("0")};
        final BigDecimal[] sum3 = {new BigDecimal("0")};
        final BigDecimal[] sum4 = {new BigDecimal("0")};
        final BigDecimal[] sum5 = {new BigDecimal("0")};
        String engyD = "电";
        String engyS = "水";
        String engyL = "动力气体";
        String engyR = "燃气";
        String engyZ = "蒸汽";
        String typeName = "energy_type_name";

        energyBoardView1.forEach(item -> {
            Object qty = item.get("qty");
            BigDecimal qtyBig = new BigDecimal(qty.toString());
            BigDecimal qtyScale = qtyBig.setScale(0, RoundingMode.HALF_UP);

            if(item.get(typeName).equals(engyD)){
                sum1[0] = sum1[0].add(qtyScale);
            }
            if(item.get(typeName).equals(engyS)){
                sum2[0] = sum2[0].add(qtyScale);
            }
            if(item.get(typeName).equals(engyL)){
                sum3[0] = sum3[0].add(qtyScale);
            }
            if(item.get(typeName).equals(engyR)){
                sum4[0] = sum4[0].add(qtyScale);
            }
            if(item.get(typeName).equals(engyZ)){
                sum5[0] = sum5[0].add(qtyScale);
            }
            returnList.put(item.get("energy_code_name").toString(),qtyScale);
        });
        returnList.put("sum1",sum1[0]);
        returnList.put("sum2",sum2[0]);
        returnList.put("sum3",sum3[0]);
        returnList.put("sum4",sum4[0]);
        returnList.put("sum5",sum5[0]);
        return returnList;
    }

    public List<Map<String, Object>> getEngyBottomAnalys(GetDashBoardEnergyDTO dto) {
        List<Map<String, Object>> energyBoardView2 = baseMapper.getEngyBottomAnalys(dto);
        return energyBoardView2;
    }

    public List<Object> getEngyBottomRightAnalys(GetDashBoardEnergyDTO dto) {
        List<Map<String, Object>> energyBoardView2 = baseMapper.getEngyBottomAnalys(dto);
        List<Object> returnList  = new ArrayList();
        String typeName = "energy_code_name";
        String countDate = "e_count_date";
        String qty = "qty";
        energyBoardView2.forEach(item -> {
            List data = new ArrayList();
            data.add(item.get(countDate));
            data.add(item.get(typeName));
            data.add(item.get(qty).toString());
            returnList.add(data);
        });
        return returnList;
    }
}




