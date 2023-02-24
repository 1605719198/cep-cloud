package com.jlkj.energy.ee.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

 
import com.jlkj.common.dto.resp.Result;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.energy.ee.dto.energymillofcost.EnergyMillOfCostDTO;
import com.jlkj.energy.ee.domain.EnergyMillOfCost;
import com.jlkj.energy.ee.service.EnergyMillOfCostService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 智能研发室 黄兵
 * @Description 产线代码对应成本中心资料维护controller
 * @date 2022/4/30 - 8:37
 */
@RestController
@RequestMapping("/energy/mill/cost")
public class EnergyMillOfCostController {

    @Autowired
    EnergyMillOfCostService energyMillOfCostService;

    /**
     * 新增产线代码对应成本中心资料
     */
    @Log(title = "新增产线代码对应成本中心资料", businessType = BusinessType.INSERT)
    @Operation(summary = "新增产线代码对应成本中心资料")
    @PostMapping("/add")
    public Object addEnergyMillOfCost(@RequestBody EnergyMillOfCost energyMillOfCost) {
        try {
            LambdaQueryWrapper<EnergyMillOfCost> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper
                    .eq(EnergyMillOfCost::getMillIdCode, energyMillOfCost.getMillIdCode())
                    .or().eq(EnergyMillOfCost::getCostCenter, energyMillOfCost.getCostCenter());

            List<EnergyMillOfCost> list = energyMillOfCostService.list(queryWrapper);
            if(list.size() >= 1){
                return Result.failedOne("您输入的工场产线代号与成本中心系统中已存在，请重新输入！");
            }
            boolean result = energyMillOfCostService.save(energyMillOfCost);
            if(result){
                Map<String, String> resultMap = new HashMap<>(16);
                resultMap.put("compId", energyMillOfCost.getCompId());
                resultMap.put("millIdCode", energyMillOfCost.getMillIdCode());
                resultMap.put("millIdName", energyMillOfCost.getMillIdName());
                resultMap.put("costCenter", energyMillOfCost.getCostCenter());
                resultMap.put("createEmpNo", energyMillOfCost.getCreateEmpNo());
                resultMap.put("createDate", energyMillOfCost.getCreateDate());
                resultMap.put("updateEmpNo", energyMillOfCost.getUpdateEmpNo());
                resultMap.put("updateDate", energyMillOfCost.getUpdateDate());
                List<Map<String, String>> resultList = new ArrayList<>();
                resultList.add(resultMap);
                return Result.successOne("新增成功", resultList);
            }else {
                return Result.failedTwo("新增失败，请重新提交");
            }
        } catch (Exception e) {
            return Result.failed();
        }
    }

    /**
     * 删除产线代码对应成本中心资料
     */
    @Log(title = "删除产线代码对应成本中心资料", businessType = BusinessType.DELETE)
    @Operation(summary = "删除产线代码对应成本中心资料")
    @DeleteMapping("/delete")
    public Object deleteEnergyMillOfCost(@RequestParam List<String> id) {
        try {
            boolean result = energyMillOfCostService.removeBatchByIds(id);
            if (result) {
                return Result.success("删除成功");
            } else {
                return Result.failedTwo("删除失败，请重新提交");
            }
        } catch (Exception e) {
            return Result.failed();
        }
    }

    /**
     * 产线代码对应成本中心资料查询与列表
     */
    @Log(title = "能源信息配置查询", businessType = BusinessType.OTHER)
    @Operation(summary = "产线代码对应成本中心资料查询与列表")
    @GetMapping("/query")
    public Object queryEnergyMillOfCost(EnergyMillOfCostDTO energyMillOfCostDTO) {
        try {
            String millIdCodeStart = energyMillOfCostDTO.getMillIdCodeStart();
            String millIdCodeEnd = energyMillOfCostDTO.getMillIdCodeEnd();
            String millIdName = energyMillOfCostDTO.getMillIdName();
            Long pageNum = energyMillOfCostDTO.getPageNum();
            Long pageSize = energyMillOfCostDTO.getPageSize();
            LambdaQueryWrapper<EnergyMillOfCost> queryWrapper = new LambdaQueryWrapper<>();
            if (StringUtils.isNotBlank(millIdCodeStart) && StringUtils.isNotBlank(millIdCodeEnd)) {
                queryWrapper.between(EnergyMillOfCost::getMillIdCode, millIdCodeStart, millIdCodeEnd);
            } else if (StringUtils.isNotBlank(millIdCodeStart)) {
                queryWrapper.eq(EnergyMillOfCost::getMillIdCode, millIdCodeStart);
            } else if (StringUtils.isNotBlank(millIdCodeEnd)) {
                queryWrapper.eq(EnergyMillOfCost::getMillIdCode, millIdCodeEnd);
            } else if (StringUtils.isNotBlank(millIdName)) {
                queryWrapper.eq(EnergyMillOfCost::getMillIdName, millIdName);
            }
            Page<EnergyMillOfCost> page = energyMillOfCostService.page(new Page<>(pageNum, pageSize), queryWrapper);
            //总记录数
            long total = page.getTotal();
            //数据list集合
            List<EnergyMillOfCost> records = page.getRecords();
            Map<String,Object> dataMap = new HashMap<>(16);
            dataMap.put("total",total);
            dataMap.put("list",records);
            if (records.isEmpty()){
                return Result.successOne("查无资料", dataMap);
            } else {
                return Result.successOne("查询成功！", dataMap);
            }
        } catch (Exception e) {
            return Result.failed();
        }
    }
}
