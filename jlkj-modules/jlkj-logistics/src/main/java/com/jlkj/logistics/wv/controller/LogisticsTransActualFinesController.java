package com.jlkj.logistics.wv.controller;

import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.dto.logistics.wv.LogisticsTransActualOtherDTO;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.logistics.wv.domain.LogisticsTransActualFines;
import com.jlkj.logistics.wv.service.ILogisticsTransActualFinesService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 运输实绩-焦粉每日倒运表 前端控制器
 * </p>
 *
 * @author xin
 * @since 2022-11-16
 */
@Slf4j
@RestController
@RequestMapping("/logistics/trans/fines")
public class LogisticsTransActualFinesController {
    @Autowired
    private ILogisticsTransActualFinesService logisticsTransActualFinesService;
    /**
     * 新增焦粉每日倒运
     */
    @Operation(summary = "焦粉每日倒运")
    @PostMapping("/addActualFines")
    @Log(title = "焦粉每日倒运",businessType = BusinessType.INSERT)
    public Object addActualFines(@RequestBody LogisticsTransActualOtherDTO dto, @RequestHeader("token") String token) {
        try {
            log.info("焦粉每日倒运=>" + dto);
            LogisticsTransActualFines fines = new LogisticsTransActualFines();
            BeanUtils.copyProperties(dto, fines);
            boolean save = logisticsTransActualFinesService.save(fines);
            return AjaxResult.success(save);
        } catch (Exception e) {
            return AjaxResult.error();
        }
    }
}
