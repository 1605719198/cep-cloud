package com.jlkj.logistics.wv.controller;

import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.dto.logistics.wv.LogisticsTransActualOtherDTO;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.logistics.wv.domain.LogisticsTransActualNut;
import com.jlkj.logistics.wv.service.ILogisticsTransActualNutService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 运输实绩-焦丁每日倒运表 前端控制器
 * </p>
 *
 * @author xin
 * @since 2022-11-16
 */
@Slf4j
@RestController
@RequestMapping("/logistics/trans/nut")
public class LogisticsTransActualNutController {
    @Autowired
    private ILogisticsTransActualNutService logisticsTransActualNutService;
    /**
     * 新增焦丁每日倒运
     */
    @Operation(summary = "焦丁每日倒运")
    @PostMapping("/addActualNut")
    @Log(title = "焦丁每日倒运",businessType = BusinessType.INSERT)
    public Object addActualNut(@RequestBody LogisticsTransActualOtherDTO dto, @RequestHeader("token") String token) {
        try {
            log.info("焦丁每日倒运=>" + dto);
            LogisticsTransActualNut nut = new LogisticsTransActualNut();
            BeanUtils.copyProperties(dto, nut);
            boolean save = logisticsTransActualNutService.save(nut);
            return AjaxResult.success(save);
        } catch (Exception e) {
            return AjaxResult.error();
        }
    }
}
