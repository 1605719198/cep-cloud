package com.jlkj.logistics.wv.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.dto.logistics.wv.LogisticsTransActualDTO;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.logistics.wv.domain.LogisticsTransActual;
import com.jlkj.logistics.wv.service.ILogisticsTransActualService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 运输实绩表 前端控制器
 * </p>
 *
 * @author xin
 * @since 2022-05-09
 */
@Slf4j
@RestController
@RequestMapping("/logistics/trans/actual")
public class LogisticsTransActualController {

    private final ILogisticsTransActualService logisticsTransActualService;

    public LogisticsTransActualController(ILogisticsTransActualService logisticsTransActualService) {
        this.logisticsTransActualService = logisticsTransActualService;
    }

    /**
     * 查询运输实际
     */
    @Operation(summary = "查询运输实际")
    @GetMapping("/queryActual")
    @Log(title = "查询运输实际",businessType = BusinessType.OTHER)
    public Object queryTransportActual(@RequestParam(defaultValue = "1") Integer pageNum,
                            @RequestParam(defaultValue = "10") Integer pageSize,
                            @RequestParam(defaultValue = "") List<String> dateRange,
                            @RequestParam(defaultValue = "") String carNo,
                            @RequestParam(defaultValue = "") String addressToName,
                            @RequestParam(defaultValue = "") String addressFromName,
                            @RequestParam(defaultValue = "") String goodsName
    ) {
        try {
            log.info("查询运输实际=>dateRange:" + dateRange + ",carNo:"+carNo+",addressToName:"+addressToName+",addressFromName"+addressFromName+",goodsName"+goodsName);
            String s, s1;
            if (dateRange.size() == 0) {
                s = "";
                s1 = "";
            } else {
                s = dateRange.get(0);
                s1 = dateRange.get(1);
            }
            Map<String, Object> map = new HashMap<>(16);
            //车号
            map.put("car_no", carNo);
            //到达地点名称
            map.put("address_to_name", addressToName);
            //货物名称
            map.put("goods_name", goodsName);
            //起始地点名称
            map.put("address_from_name", addressFromName);
            //停机主类型编
            Page<LogisticsTransActual> logisticsTransActualPage = logisticsTransActualService.page(new Page<>(pageNum, pageSize), Wrappers.<LogisticsTransActual>query().allEq(map, false).between(
                    dateRange.size() != 0, "car_send_date", s, s1));
            return AjaxResult.success("成功", logisticsTransActualPage);
        } catch (Exception e) {
            return AjaxResult.error();
        }

    }

    /**
     * 新增运输实际记录
     */
    @Operation(summary = "新增运输实际")
    @PostMapping("/addTransportActual")
    @Log(title = "新增运输实际",businessType = BusinessType.INSERT)
    public Object addTransportActual(@RequestBody LogisticsTransActualDTO logisticsTransActualDTO, @RequestHeader("token") String token) {
        try {
            log.info("新增运输实际=>"+logisticsTransActualDTO);
            LogisticsTransActual logisticsTransActual = new LogisticsTransActual();
            BeanUtils.copyProperties(logisticsTransActualDTO, logisticsTransActual);
            boolean save = logisticsTransActualService.save(logisticsTransActual);
            return AjaxResult.success(save);
        } catch (Exception e) {
            return AjaxResult.error();
        }
    }

    /**
     * 修改运输实际资料
     */
    @Operation(summary = "修改运输实际")
    @PutMapping("/updateTransportActual")
    @Log(title = "修改运输实际",businessType = BusinessType.UPDATE)
    public Object updateTransportActual(@RequestBody LogisticsTransActualDTO logisticsTransActualDTO,@RequestHeader("token") String token) {
        try {
            log.info("修改运输实际=>"+logisticsTransActualDTO);
            LogisticsTransActual logisticsTransActual = new LogisticsTransActual();
            BeanUtils.copyProperties(logisticsTransActualDTO, logisticsTransActual);
            //执行删除操作
            deleteTransportActual(logisticsTransActualDTO,token);
            boolean save = logisticsTransActualService.save(logisticsTransActual);
            return AjaxResult.success(save);
        } catch (Exception e) {
            return AjaxResult.error();
        }
    }

    /**
     * 删除运输实际资料
     */
    @Operation(summary = "删除运输实际")
    @DeleteMapping("/deleteTransportActual")
    @Log(title = "删除运输实际",businessType = BusinessType.DELETE)
    public Object deleteTransportActual(@RequestBody LogisticsTransActualDTO logisticsTransActualDTO,@RequestHeader("token") String token) {
        try {
            log.info("删除运输实际=>"+logisticsTransActualDTO);
            LogisticsTransActual logisticsTransActual = new LogisticsTransActual();
            BeanUtils.copyProperties(logisticsTransActualDTO, logisticsTransActual);
            //构造条件进行删除操作
            QueryWrapper<LogisticsTransActual> wrapper = new QueryWrapper<>();
            wrapper.eq(StringUtils.isNotNull(logisticsTransActual.getCarExecuteNo()), "car_execute_no", logisticsTransActual.getCarExecuteNo());
            //先删除已有数据在进行添加
            boolean remove = logisticsTransActualService.remove(wrapper);
            return AjaxResult.success(remove);
        } catch (Exception e) {
            return AjaxResult.error();
        }
    }

    /**
     * 查询月倒出焦炭比
     * @param startDate
     * @param endDate
     * @return
     */
    @Operation(summary = "查询月倒出焦炭比")
    @GetMapping("/getOutCokeScale")
    @Log(title = "查询月倒出焦炭比",businessType = BusinessType.OTHER)
    public Object getOutCokeScale(@RequestParam(defaultValue = "") String startDate,
                                  @RequestParam(defaultValue = "") String endDate) {
        JSONArray resultArr = logisticsTransActualService.getOutCokeScale(startDate,endDate);
        return AjaxResult.success(resultArr);
    }
    /**
     * 查询月焦炭倒出量(按收货地点)
     * @param startDate
     * @param endDate
     * @return
     */
    @Operation(summary = "查询月焦炭倒出量(按收货地点)")
    @GetMapping("/getOutCokeLocation")
    @Log(title = "查询月焦炭倒出量(按收货地点)",businessType = BusinessType.OTHER)
    public Object getOutCokeLocation(@RequestParam(defaultValue = "") String startDate,
                                  @RequestParam(defaultValue = "") String endDate) {
        JSONObject json = logisticsTransActualService.getOutCokeLocation(startDate,endDate);
        return AjaxResult.success(json);
    }

    /**
     * 查询月焦炭倒出量实际-计划
     * @param startDate
     * @param endDate
     * @return
     */
    @Operation(summary = "查询月焦炭倒出量实际-计划")
    @GetMapping("/getOutCokeAmount")
    @Log(title = "查询月焦炭倒出量实际-计划",businessType = BusinessType.OTHER)
    public Object getOutCokeAmount(@RequestParam(defaultValue = "") String startDate,
                                   @RequestParam(defaultValue = "") String endDate) {
        JSONObject json = logisticsTransActualService.getOutCokeAmount(startDate,endDate);
        return AjaxResult.success(json);
    }

    /**
     * 查询月焦炭倒入量
     * @param startDate
     * @param endDate
     * @return
     */
    @Operation(summary = "查询月焦炭倒入量")
    @GetMapping("/getInCokeAmount")
    @Log(title = "查询月焦炭倒入量",businessType = BusinessType.OTHER)
    public Object getInCokeAmount(@RequestParam(defaultValue = "") String startDate,
                                   @RequestParam(defaultValue = "") String endDate) {
        JSONObject json = logisticsTransActualService.getInCokeAmount(startDate,endDate);
        return AjaxResult.success(json);
    }

}
