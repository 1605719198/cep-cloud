package com.jlkj.logistics.wv.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.dto.logistics.wv.LogisticsPlanDayDTO;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.logistics.wv.domain.LogisticsPlanDay;
import com.jlkj.logistics.wv.dto.LogisticsPlanDayQueryDTO;
import com.jlkj.logistics.wv.dto.LogisticsPlanDayResultDTO;
import com.jlkj.logistics.wv.service.ILogisticsPlanDayService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 日运输计划表 前端控制器
 * </p>
 *
 * @author xin
 * @since 2022-05-07
 */
@Slf4j
@RestController
@RequestMapping("/logistics/plan/day")
public class LogisticsPlanDayController {
    private final ILogisticsPlanDayService logisticsPlanDayService;

    public LogisticsPlanDayController(ILogisticsPlanDayService logisticsPlanDayService) {
        this.logisticsPlanDayService = logisticsPlanDayService;
    }

    /**
     * 新增日运输计划资料
     */
    @Operation(summary = "新增日运输计划资料")
    @PostMapping("/addPlanDayData")
    @Log(title = "新增日运输计划资料",businessType = BusinessType.INSERT)
    public Object addPlanDayData(@RequestBody LogisticsPlanDayDTO logisticsPlanDayDTO, @RequestHeader("token") String token){
        log.info("新增日运输计划资料=>" + logisticsPlanDayDTO);
        LogisticsPlanDay logisticsPlanDay = new LogisticsPlanDay();
        BeanUtils.copyProperties(logisticsPlanDayDTO,logisticsPlanDay);
        boolean save = logisticsPlanDayService.save(logisticsPlanDay);
        return AjaxResult.success(save);
    }
    /**
     * 修改日运输计划资料
     */
    @Operation(summary = "修改日运输计划资料")
    @PutMapping("/updatePlanDayData")
    @Log(title = "修改日运输计划资料",businessType = BusinessType.UPDATE)
    public Object updatePlanDayData(@RequestBody LogisticsPlanDayDTO logisticsPlanDayDTO,@RequestHeader("token") String token){
        log.info("修改日运输计划资料=>" + logisticsPlanDayDTO);
        LogisticsPlanDay logisticsPlanDay = new LogisticsPlanDay();
        BeanUtils.copyProperties(logisticsPlanDayDTO,logisticsPlanDay);
        //执行删除操作
        deletePlanDayData(logisticsPlanDayDTO,token);
        boolean save = logisticsPlanDayService.save(logisticsPlanDay);
        return AjaxResult.success(save);
    }

    /**
     * 根据条件更新日运输计划
     * @param logisticsPlanDayDTO
     * @param token
     */
    @Operation(summary = "根据条件更新日运输计划")
    @PutMapping("/updatePlanDayByParams")
    @Log(title = "根据条件更新日运输计划",businessType = BusinessType.UPDATE)
    public void updatePlanDayByParams(@RequestBody LogisticsPlanDayDTO logisticsPlanDayDTO,@RequestHeader("token") String token){
        log.info("根据条件更新日运输计划=>" + logisticsPlanDayDTO);
        QueryWrapper<LogisticsPlanDay> wrapper = new QueryWrapper();
        wrapper.eq(StringUtils.isNotNull(logisticsPlanDayDTO.getCostBudg()),"cost_budg",logisticsPlanDayDTO.getCostBudg())
                .eq(StringUtils.isNotNull(logisticsPlanDayDTO.getGoodsNo()),"goods_no",logisticsPlanDayDTO.getGoodsNo())
                .eq(StringUtils.isNotNull(logisticsPlanDayDTO.getAddressToNo()),"address_to_no",logisticsPlanDayDTO.getAddressToNo())
                .eq(StringUtils.isNotNull(logisticsPlanDayDTO.getPlanDay()),"plan_day",logisticsPlanDayDTO.getPlanDay())
                .eq(StringUtils.isNotNull(logisticsPlanDayDTO.getAddressFromNo()),"address_from_no",logisticsPlanDayDTO.getAddressFromNo())
                .eq(StringUtils.isNotNull(logisticsPlanDayDTO.getWorkTypeNo()),"work_type_no",logisticsPlanDayDTO.getWorkTypeNo())
                .eq(StringUtils.isNotNull(logisticsPlanDayDTO.getLogicId()),"logic_id",logisticsPlanDayDTO.getLogicId());
        LogisticsPlanDay planDay = logisticsPlanDayService.getOne(wrapper);
        planDay.setCompleteMachineHourNum(logisticsPlanDayDTO.getCompleteMachineHourNum());
        planDay.setCompletePoundNum(logisticsPlanDayDTO.getCompletePoundNum());
        logisticsPlanDayService.updateById(planDay);
    }
    /**
     * 删除日运输计划资料
     */
    @Operation(summary = "删除日运输计划资料")
    @DeleteMapping("/deletePlanDayData")
    @Log(title = "根据条件更新日运输计划",businessType = BusinessType.DELETE)
    public Object deletePlanDayData(@RequestBody LogisticsPlanDayDTO logisticsPlanDayDTO,@RequestHeader("token") String token){
        log.info("删除日运输计划资料=>" + logisticsPlanDayDTO);
        LogisticsPlanDay logisticsPlanDay = new LogisticsPlanDay();
        BeanUtils.copyProperties(logisticsPlanDayDTO,logisticsPlanDay);
        //构造条件进行删除操作
        QueryWrapper<LogisticsPlanDay> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotNull(logisticsPlanDay.getLogicId()),"logic_id",logisticsPlanDay.getLogicId());
        boolean remove = logisticsPlanDayService.remove(wrapper);
        return AjaxResult.success(remove);
    }

    /**
     * 条件查询日运输计划资料
     */
    @Operation(summary = "条件查询日运输计划资料")
    @GetMapping("/queryPlanDayData")
    @Log(title = "条件查询日运输计划资料",businessType = BusinessType.OTHER)
    public Object queryPlanDayData(LogisticsPlanDayQueryDTO logisticsPlanDayQueryDTO){
        log.info("条件查询日运输计划资料=>" + logisticsPlanDayQueryDTO);
        Long current = logisticsPlanDayQueryDTO.getPage();
        Long limit = logisticsPlanDayQueryDTO.getLimit();
        //创建page对象
        Page<LogisticsPlanDay> planDayPage = new Page<>(current, limit);
        //构造条件
        QueryWrapper<LogisticsPlanDay> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotNull(logisticsPlanDayQueryDTO.getPlanDay())) {
            wrapper.eq("plan_day",logisticsPlanDayQueryDTO.getPlanDay());
        }
        //实现分页查询
        logisticsPlanDayService.page(planDayPage, wrapper);
        //数据list集合
        List<LogisticsPlanDay> records = planDayPage.getRecords();
        long total = planDayPage.getTotal();
        //计划台时量求和
        Integer planMachineHourNumSum = 0;
        //剩余计划台时量求和
        Integer planSurplusMachineHourNumSum = 0;
        //称重计划量求和
        BigDecimal planPoundNumSum = BigDecimal.ZERO;
        //剩余称重计划量求和
        BigDecimal planSurplusPoundNumSum = BigDecimal.ZERO;
        //返回结果List
        List<LogisticsPlanDayResultDTO> resultList = new ArrayList<>();
        //返回json结果集
        Map<String,Object> resultMap = new HashMap<>(16);
        if (StringUtils.isNotNull(records)) {
            for (LogisticsPlanDay item : records) {
                LogisticsPlanDayResultDTO logisticsPlanDayResultDTO = new LogisticsPlanDayResultDTO();
                BeanUtils.copyProperties(item,logisticsPlanDayResultDTO);
                //计算剩余台时量
                if (StringUtils.isNotNull(item.getPlanMachineHourNum()) && StringUtils.isNotNull(item.getCompleteMachineHourNum())) {
                    logisticsPlanDayResultDTO.setPlanSurplusMachineHourNum(item.getPlanMachineHourNum()-item.getCompleteMachineHourNum());
                } else {
                    logisticsPlanDayResultDTO.setPlanSurplusMachineHourNum(0);
                }
                //计算剩余称重计划量
                if (StringUtils.isNotNull(item.getPlanPoundNum()) && StringUtils.isNotNull(item.getCompletePoundNum())) {
                    logisticsPlanDayResultDTO.setPlanSurplusPoundNum(item.getPlanPoundNum().subtract(item.getCompletePoundNum()));
                } else {
                    logisticsPlanDayResultDTO.setPlanSurplusPoundNum(BigDecimal.ZERO);
                }
                //累加计划台时量
                planMachineHourNumSum += logisticsPlanDayResultDTO.getPlanMachineHourNum();
                //累加剩余台时量
                planSurplusMachineHourNumSum += logisticsPlanDayResultDTO.getPlanSurplusMachineHourNum();
                //累加称重计划量
                planPoundNumSum = planPoundNumSum.add(logisticsPlanDayResultDTO.getPlanPoundNum());
                //累加剩余称重计划量
                planSurplusPoundNumSum = planSurplusPoundNumSum.add(logisticsPlanDayResultDTO.getPlanSurplusPoundNum());
                resultList.add(logisticsPlanDayResultDTO);
            }
        }
        resultMap.put("planMachineHourNumSum",planMachineHourNumSum);
        resultMap.put("planSurplusMachineHourNumSum",planSurplusMachineHourNumSum);
        resultMap.put("planPoundNumSum",planPoundNumSum);
        resultMap.put("planSurplusPoundNumSum",planSurplusPoundNumSum);

        Map<String, Object> dataMap = new HashMap<>(16);
        dataMap.put("total",total);
        dataMap.put("dataList",resultList);
        dataMap.put("sumData",resultMap);
        return AjaxResult.success(dataMap);
    }

}
