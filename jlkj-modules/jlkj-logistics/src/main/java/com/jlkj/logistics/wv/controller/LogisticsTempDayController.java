package com.jlkj.logistics.wv.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.dto.logistics.wv.LogisticsTempDayDTO;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.logistics.wv.domain.LogisticsTempDay;
import com.jlkj.logistics.wv.domain.LogisticsTransActual;
import com.jlkj.logistics.wv.dto.LogisticsTempDayQueryDTO;
import com.jlkj.logistics.wv.dto.LogisticsTempDayResultDTO;
import com.jlkj.logistics.wv.service.ILogisticsTempDayService;
import com.jlkj.logistics.wv.service.ILogisticsTransActualService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 临时用车申请表 前端控制器
 * </p>
 *
 * @author xin
 * @since 2022-05-09
 */
@Slf4j
@RestController
@RequestMapping("/logistics/temp/day")
public class LogisticsTempDayController {

    private final ILogisticsTempDayService logisticsTempDayService;
    private final ILogisticsTransActualService logisticsTransActualService;

    public LogisticsTempDayController(ILogisticsTempDayService logisticsTempDayService, ILogisticsTransActualService logisticsTransActualService) {
        this.logisticsTempDayService = logisticsTempDayService;
        this.logisticsTransActualService = logisticsTransActualService;
    }

    /**
     * 新增临时用车资料
     */
    @Operation(summary = "新增临时用车资料")
    @PostMapping("/addTempDayData")
    @Log(title = "新增临时用车资料",businessType = BusinessType.INSERT)
    public Object addTempDayData(@RequestBody LogisticsTempDayDTO logisticsTempDayDTO, @RequestHeader("token") String token){
        log.info("新增临时用车资料=>" + logisticsTempDayDTO);
        LogisticsTempDay logisticsTempDay = new LogisticsTempDay();
        BeanUtils.copyProperties(logisticsTempDayDTO,logisticsTempDay);
        boolean save = logisticsTempDayService.save(logisticsTempDay);
        return AjaxResult.success(save);
    }
    /**
     * 修改临时用车资料
     */
    @Operation(summary = "修改临时用车资料")
    @PutMapping("/updateTempDayData")
    @Log(title = "修改临时用车资料",businessType = BusinessType.UPDATE)
    public Object updateTempDayData(@RequestBody LogisticsTempDayDTO logisticsTempDayDTO,@RequestHeader("token") String token){
        log.info("修改临时用车资料=>" + logisticsTempDayDTO);
        LogisticsTempDay logisticsTempDay = new LogisticsTempDay();
        BeanUtils.copyProperties(logisticsTempDayDTO,logisticsTempDay);
        //先删除已有数据在进行添加
        deleteTempDayData(logisticsTempDayDTO,token);
        boolean save = logisticsTempDayService.save(logisticsTempDay);
        return AjaxResult.success(save);
    }
    /**
     * 根据条件更新临时用车资料
     * @param logisticsTempDayDTO
     * @param token
     */
    @Operation(summary = "根据条件更新临时用车资料")
    @PutMapping("/updateTempDayByParams")
    @Log(title = "根据条件更新临时用车资料",businessType = BusinessType.UPDATE)
    public void updateTempDayByParams(@RequestBody LogisticsTempDayDTO logisticsTempDayDTO, @RequestHeader("token") String token){
        log.info("根据条件更新临时用车资料=>" + logisticsTempDayDTO);
        QueryWrapper<LogisticsTempDay> wrapper = new QueryWrapper();
        wrapper.eq(StringUtils.isNotNull(logisticsTempDayDTO.getCostBudg()),"cost_budg",logisticsTempDayDTO.getCostBudg())
                .eq(StringUtils.isNotNull(logisticsTempDayDTO.getGoodsNo()),"goods_no",logisticsTempDayDTO.getGoodsNo())
                .eq(StringUtils.isNotNull(logisticsTempDayDTO.getAddressToNo()),"address_to_no",logisticsTempDayDTO.getAddressToNo())
                .eq(StringUtils.isNotNull(logisticsTempDayDTO.getApplyNo()),"apply_no",logisticsTempDayDTO.getApplyNo())
                .eq(StringUtils.isNotNull(logisticsTempDayDTO.getAddressFromNo()),"address_from_no",logisticsTempDayDTO.getAddressFromNo())
                .eq(StringUtils.isNotNull(logisticsTempDayDTO.getWorkTypeNo()),"work_type_no",logisticsTempDayDTO.getWorkTypeNo())
                .eq(StringUtils.isNotNull(logisticsTempDayDTO.getLogicId()),"logic_id",logisticsTempDayDTO.getLogicId());
        LogisticsTempDay tempDay = logisticsTempDayService.getOne(wrapper);
        tempDay.setCompleteMachineHourNum(logisticsTempDayDTO.getCompleteMachineHourNum());
        tempDay.setCompletePoundNum(logisticsTempDayDTO.getCompletePoundNum());
        logisticsTempDayService.updateById(tempDay);
    }
    /**
     * 删除临时用车资料
     */
    @Operation(summary = "删除临时用车资料")
    @DeleteMapping("/deleteTempDayData")
    @Log(title = "删除临时用车资料",businessType = BusinessType.DELETE)
    public Object deleteTempDayData(@RequestBody LogisticsTempDayDTO logisticsTempDayDTO,@RequestHeader("token") String token){
        log.info("删除临时用车资料=>" + logisticsTempDayDTO);
        LogisticsTempDay logisticsTempDay = new LogisticsTempDay();
        BeanUtils.copyProperties(logisticsTempDayDTO,logisticsTempDay);
        //构造条件进行删除操作
        QueryWrapper<LogisticsTempDay> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotNull(logisticsTempDay.getLogicId()),"logic_id",logisticsTempDay.getLogicId());
        boolean remove = logisticsTempDayService.remove(wrapper);
        return AjaxResult.success(remove);
    }

    /**
     * 根据申请单号查询临时用车申请
     */
    @Operation(summary = "条件查询临时用车申请资料")
    @GetMapping("/queryTempDayDataByApplyNo")
    @Log(title = "条件查询临时用车申请资料",businessType = BusinessType.OTHER)
    public Object queryTempDayDataByApplyNo(LogisticsTempDayQueryDTO logisticsTempDayQueryDTO){
        log.info("条件查询临时用车申请资料=>" + logisticsTempDayQueryDTO);
        Long current = logisticsTempDayQueryDTO.getPage();
        Long limit = logisticsTempDayQueryDTO.getLimit();
        //创建page对象
        Page<LogisticsTempDay> tempDayPage = new Page<>(current, limit);
        //构造条件
        QueryWrapper<LogisticsTempDay> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotNull(logisticsTempDayQueryDTO.getApplyNo())) {
            wrapper.eq("apply_no",logisticsTempDayQueryDTO.getApplyNo());
        }
        //实现分页查询
        logisticsTempDayService.page(tempDayPage, wrapper);
        //数据list集合
        List<LogisticsTempDay> records = tempDayPage.getRecords();
        long total = tempDayPage.getTotal();
        //计划台时量求和
        Integer planMachineHourNumSum = 0;
        //剩余计划台时量求和
        Integer planSurplusMachineHourNumSum = 0;
        //返回结果List
        List<LogisticsTempDayResultDTO> resultList = new ArrayList<>();
        //返回json结果集
        Map<String,Object> resultMap = new HashMap<>(16);
        if (StringUtils.isNotNull(records)) {
            for (LogisticsTempDay item : records) {
                LogisticsTempDayResultDTO logisticsTempDayResultDTO = new LogisticsTempDayResultDTO();
                BeanUtils.copyProperties(item,logisticsTempDayResultDTO);
                //计算剩余台时量
                if(StringUtils.isNotNull(item.getPlanMachineHourNum()) && StringUtils.isNotNull(item.getCompleteMachineHourNum())) {
                    logisticsTempDayResultDTO.setPlanSurplusMachineHourNum(item.getPlanMachineHourNum()-item.getCompleteMachineHourNum());
                } else {
                    logisticsTempDayResultDTO.setPlanSurplusMachineHourNum(planMachineHourNumSum);
                }
                //累加计划台时量
                if (StringUtils.isNotNull(logisticsTempDayResultDTO.getPlanMachineHourNum())) {
                    planMachineHourNumSum += logisticsTempDayResultDTO.getPlanMachineHourNum();
                } else {
                    planMachineHourNumSum += planMachineHourNumSum;
                }

                //累加剩余台时量
                if (StringUtils.isNotNull(logisticsTempDayResultDTO.getPlanSurplusMachineHourNum())) {
                    planSurplusMachineHourNumSum += logisticsTempDayResultDTO.getPlanSurplusMachineHourNum();
                } else {
                    planSurplusMachineHourNumSum += planSurplusMachineHourNumSum;
                }

                resultList.add(logisticsTempDayResultDTO);
            }
        }
        resultMap.put("planMachineHourNumSum",planMachineHourNumSum);
        resultMap.put("planSurplusMachineHourNumSum",planSurplusMachineHourNumSum);

        Map<String, Object> dataMap = new HashMap<>(16);
        dataMap.put("total",total);
        dataMap.put("dataList",resultList);
        dataMap.put("sumData",resultMap);
        return AjaxResult.success(dataMap);
    }
    /**
     * 查询运输实际
     */
    @Operation(summary = "查询运输实际")
    @GetMapping("/queryTransportActual")
    @Log(title = "查询运输实际",businessType = BusinessType.OTHER)
    public Object queryTransportActual(@RequestParam(defaultValue ="1") Integer pageNum,
                            @RequestParam (defaultValue = "10")Integer pageSize,
                            @RequestParam String logicId
    ) {
        try {
            log.info("查询运输实际=>" + logicId);
            Page<LogisticsTransActual> logisticsTransActualPage = logisticsTransActualService.page(new Page<>(pageNum, pageSize), Wrappers.<LogisticsTransActual>query().eq("logic_id",logicId));
            return AjaxResult.success("成功", logisticsTransActualPage);
        } catch (Exception e) {
            return AjaxResult.error();
        }


    }


}
