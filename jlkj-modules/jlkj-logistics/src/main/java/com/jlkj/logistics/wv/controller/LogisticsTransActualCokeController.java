package com.jlkj.logistics.wv.controller;

import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.dto.logistics.wv.LogisticsTransActualOtherDTO;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.logistics.wv.domain.LogisticsTransActualCoke;
import com.jlkj.logistics.wv.service.ILogisticsTransActualCokeService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 运输实绩-焦炭每日倒运表 前端控制器
 * </p>
 *
 * @author xin
 * @since 2022-11-16
 */
@Slf4j
@RestController
@RequestMapping("/logistics/trans/coke")
public class LogisticsTransActualCokeController {
    @Autowired
    private ILogisticsTransActualCokeService logisticsTransActualCokeService;
    /**
     * 新增焦粉每日倒运
     */
    @Operation(summary = "焦炭每日倒运")
    @PostMapping("/addActualCoke")
    @Log(title = "焦炭每日倒运",businessType = BusinessType.INSERT)
    public Object addActualCoke(@RequestBody LogisticsTransActualOtherDTO dto, @RequestHeader("token") String token) {
        try {
            log.info("焦炭每日倒运=>" + dto);
            LogisticsTransActualCoke nut = new LogisticsTransActualCoke();
            BeanUtils.copyProperties(dto, nut);
            boolean save = logisticsTransActualCokeService.save(nut);
            return AjaxResult.success(save);
        } catch (Exception e) {
            return AjaxResult.error();
        }
    }
}
