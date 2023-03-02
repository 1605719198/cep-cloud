package com.jlkj.logistics.wv.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.dto.logistics.wv.LogisticsPlanMonthDTO;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.logistics.wv.domain.LogisticsPlanMonth;
import com.jlkj.logistics.wv.dto.LogisticsPlanMonthQueryDTO;
import com.jlkj.logistics.wv.dto.LogisticsPlanMonthResultDTO;
import com.jlkj.logistics.wv.service.ILogisticsPlanMonthService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 月度运输计划表 前端控制器
 * </p>
 *
 * @author Xin
 * @since 2022-05-04
 */
@Slf4j
@RestController
@RequestMapping("/logistics/plan/month")
public class LogisticsPlanMonthController {
    @Autowired
    private ILogisticsPlanMonthService logisticsPlanMonthService;
    /**
     * 新增月度运输计划资料
     */
    @Operation(summary = "新增月度运输计划资料")
    @PostMapping("/addPlanMonthData")
    @Log(title = "新增月度运输计划资料",businessType = BusinessType.INSERT)
    public Object addPlanMonthData(@RequestBody LogisticsPlanMonthDTO logisticsPlanMonthDTO, @RequestHeader("token") String token){
        log.info("新增月度运输计划资料=>" + logisticsPlanMonthDTO);
        LogisticsPlanMonth logisticsPlanMonth = new LogisticsPlanMonth();
        BeanUtils.copyProperties(logisticsPlanMonthDTO,logisticsPlanMonth);
        boolean save = logisticsPlanMonthService.save(logisticsPlanMonth);
        return AjaxResult.success(save);
    }
    /**
     * 修改月度运输计划资料
     */
    @Operation(summary = "修改月度运输计划资料")
    @PutMapping("/updatePlanMonthData")
    @Log(title = "修改月度运输计划资料",businessType = BusinessType.UPDATE)
    public Object updatePlanMonthData(@RequestBody LogisticsPlanMonthDTO logisticsPlanMonthDTO,@RequestHeader("token") String token){
        log.info("修改月度运输计划资料=>" + logisticsPlanMonthDTO);
        LogisticsPlanMonth logisticsPlanMonth = new LogisticsPlanMonth();
        BeanUtils.copyProperties(logisticsPlanMonthDTO,logisticsPlanMonth);
        //构造条件进行删除操作
        QueryWrapper<LogisticsPlanMonth> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotNull(logisticsPlanMonth.getPlanMonth())) {
            wrapper.eq("plan_month",logisticsPlanMonth.getPlanMonth());
        }
        if (StringUtils.isNotNull(logisticsPlanMonth.getTransportTypeNo())) {
            wrapper.eq("transport_type_no",logisticsPlanMonth.getTransportTypeNo());
        }
        //先删除已有数据在进行添加
        logisticsPlanMonthService.remove(wrapper);
        boolean save = logisticsPlanMonthService.save(logisticsPlanMonth);
        return AjaxResult.success(save);
    }

    /**
     * 根据条件修改月运输计划资料
     * @param logisticsPlanMonthDTO
     * @param token
     */
    @Operation(summary = "根据条件修改月运输计划资料")
    @PutMapping("/updatePlanMonthByParams")
    @Log(title = "根据条件修改月运输计划资料",businessType = BusinessType.UPDATE)
    public void updatePlanMonthByParams(@RequestBody LogisticsPlanMonthDTO logisticsPlanMonthDTO,@RequestHeader("token") String token){
        log.info("根据条件修改月运输计划资料=>" + logisticsPlanMonthDTO);
        QueryWrapper<LogisticsPlanMonth> wrapper = new QueryWrapper();
        wrapper.eq(StringUtils.isNotNull(logisticsPlanMonthDTO.getAddressFromNo()),"address_from_no",logisticsPlanMonthDTO.getAddressFromNo())
                .eq(StringUtils.isNotNull(logisticsPlanMonthDTO.getAddressToNo()),"address_to_no",logisticsPlanMonthDTO.getAddressToNo())
                .eq(StringUtils.isNotNull(logisticsPlanMonthDTO.getCostBudg()),"cost_budg",logisticsPlanMonthDTO.getCostBudg())
                .eq(StringUtils.isNotNull(logisticsPlanMonthDTO.getGoodsNo()),"goods_no",logisticsPlanMonthDTO.getGoodsNo())
                .eq(StringUtils.isNotNull(logisticsPlanMonthDTO.getPlanMonth()),"plan_month",logisticsPlanMonthDTO.getPlanMonth())
                .eq(StringUtils.isNotNull(logisticsPlanMonthDTO.getTransportTypeNo()),"transport_type_no",logisticsPlanMonthDTO.getTransportTypeNo())
                .eq(StringUtils.isNotNull(logisticsPlanMonthDTO.getWorkTypeNo()),"work_type_no",logisticsPlanMonthDTO.getWorkTypeNo());
        LogisticsPlanMonth planMonth = logisticsPlanMonthService.getOne(wrapper);
        planMonth.setCompleteMachineHourNum(logisticsPlanMonthDTO.getCompleteMachineHourNum());
        planMonth.setCompletePoundNum(logisticsPlanMonthDTO.getCompletePoundNum());
        logisticsPlanMonthService.updateById(planMonth);
    }
    /**
     * 删除月度运输计划资料
     */
    @Operation(summary = "删除月度运输计划资料")
    @DeleteMapping("/deletePlanMonthData")
    @Log(title = "删除月度运输计划资料",businessType = BusinessType.DELETE)
    public Object deletePlanMonthData(@RequestBody LogisticsPlanMonthDTO logisticsPlanMonthDTO,@RequestHeader("token") String token){
        log.info("删除月度运输计划资料=>" + logisticsPlanMonthDTO);
        LogisticsPlanMonth logisticsPlanMonth = new LogisticsPlanMonth();
        BeanUtils.copyProperties(logisticsPlanMonthDTO,logisticsPlanMonth);
        //构造条件进行删除操作
        QueryWrapper<LogisticsPlanMonth> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotNull(logisticsPlanMonth.getPlanMonth())) {
            wrapper.eq("plan_month",logisticsPlanMonth.getPlanMonth());
        }
        if (StringUtils.isNotNull(logisticsPlanMonth.getTransportTypeNo())) {
            wrapper.eq("transport_type_no",logisticsPlanMonth.getTransportTypeNo());
        }
        //先删除已有数据在进行添加
        boolean remove = logisticsPlanMonthService.remove(wrapper);
        return AjaxResult.success(remove);
    }

    /**
     * 条件查询月度运输计划资料
     */
    @Operation(summary = "条件查询月度运输计划资料")
    @GetMapping("/queryPlanMonthData")
    @Log(title = "条件查询月度运输计划资料",businessType = BusinessType.OTHER)
    public Object queryPlanMonthData(LogisticsPlanMonthQueryDTO logisticsPlanMonthQueryDTO){
        log.info("条件查询月度运输计划资料=>" + logisticsPlanMonthQueryDTO);
        Long current = logisticsPlanMonthQueryDTO.getPage();
        Long limit = logisticsPlanMonthQueryDTO.getLimit();
        //创建page对象
        Page<LogisticsPlanMonth> planMonthPage = new Page<>(current, limit);
        //构造条件
        QueryWrapper<LogisticsPlanMonth> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotNull(logisticsPlanMonthQueryDTO.getPlanMonth())) {
            wrapper.eq("plan_month",logisticsPlanMonthQueryDTO.getPlanMonth());
        }
        //实现分页查询
        logisticsPlanMonthService.page(planMonthPage, wrapper);
        //总记录数
        long total = planMonthPage.getTotal();
        //数据list集合
        List<LogisticsPlanMonth> records = planMonthPage.getRecords();
        //计划台时量求和
        Integer planMachineHourNumSum = 0;
        //剩余计划台时量求和
        Integer planSurplusMachineHourNumSum = 0;
        //称重计划量求和
        BigDecimal planPoundNumSum = BigDecimal.ZERO;
        //剩余称重计划量求和
        BigDecimal planSurplusPoundNumSum = BigDecimal.ZERO;
        //返回结果List
        List<LogisticsPlanMonthResultDTO> resultList = new ArrayList<>();
        //返回json结果集
        Map<String,Object> resultMap = new HashMap<>(10);
        if (StringUtils.isNotNull(records)) {
            for (LogisticsPlanMonth item : records) {
                LogisticsPlanMonthResultDTO logisticsPlanMonthResultDTO = new LogisticsPlanMonthResultDTO();
                BeanUtils.copyProperties(item,logisticsPlanMonthResultDTO);
                //计算剩余台时量
                if (StringUtils.isNotNull(item.getPlanMachineHourNum()) && StringUtils.isNotNull(item.getCompleteMachineHourNum())) {
                    logisticsPlanMonthResultDTO.setPlanSurplusMachineHourNum(item.getPlanMachineHourNum()-item.getCompleteMachineHourNum());
                } else {
                    logisticsPlanMonthResultDTO.setPlanSurplusMachineHourNum(0);
                }
                //计算剩余称重计划量
                if (StringUtils.isNotNull(item.getPlanPoundNum()) && StringUtils.isNotNull(item.getCompletePoundNum())) {
                    logisticsPlanMonthResultDTO.setPlanSurplusPoundNum(item.getPlanPoundNum().subtract(item.getCompletePoundNum()));
                } else {
                    logisticsPlanMonthResultDTO.setPlanSurplusPoundNum(BigDecimal.ZERO);
                }
                //累加计划台时量
                planMachineHourNumSum += logisticsPlanMonthResultDTO.getPlanMachineHourNum();
                //累加剩余台时量
                planSurplusMachineHourNumSum += logisticsPlanMonthResultDTO.getPlanSurplusMachineHourNum();
                //累加称重计划量
                planPoundNumSum = planPoundNumSum.add(logisticsPlanMonthResultDTO.getPlanPoundNum());
                //累加剩余称重计划量
                planSurplusPoundNumSum = planSurplusPoundNumSum.add(logisticsPlanMonthResultDTO.getPlanSurplusPoundNum());
                resultList.add(logisticsPlanMonthResultDTO);
            }
        }
        resultMap.put("planMachineHourNumSum",planMachineHourNumSum);
        resultMap.put("planSurplusMachineHourNumSum",planSurplusMachineHourNumSum);
        resultMap.put("planPoundNumSum",planPoundNumSum);
        resultMap.put("planSurplusPoundNumSum",planSurplusPoundNumSum);

        Map<String, Object> dataMap = new HashMap<>(10);
        dataMap.put("total",total);
        dataMap.put("dataList",resultList);
        dataMap.put("sumData",resultMap);
        return AjaxResult.success(dataMap);
    }

}
