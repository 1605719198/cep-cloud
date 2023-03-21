package com.jlkj.rabbit.feign.logisticsprovider;

import com.jlkj.common.dto.logistics.wv.*;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

/**
 * @author Xin
 */
@Component
@FeignClient(value = "logistics-service-provider")
public interface LogisticsFeignService {
    /**
     * 新增月度运输计划资料
     * @param logisticsPlanMonthDTO
     * @param token
     * @return
     */
    @Operation(summary = "新增月度运输计划资料")
    @PostMapping("/logistics/plan/month/addPlanMonthData")
    Object addPlanMonthData(@RequestBody LogisticsPlanMonthDTO logisticsPlanMonthDTO,@RequestHeader("token") String token);
    /**
     * 修改月度运输计划资料
     * @param logisticsPlanMonthDTO
     * @param token
     * @return
     */
    @Operation(summary = "修改月度运输计划资料")
    @PutMapping("/logistics/plan/month/updatePlanMonthData")
    Object updatePlanMonthData(@RequestBody LogisticsPlanMonthDTO logisticsPlanMonthDTO,@RequestHeader("token") String token);
    /**
     * 删除月度运输计划资料
     * @param logisticsPlanMonthDTO
     * @param token
     * @return
     */
    @DeleteMapping("/logistics/plan/month/deletePlanMonthData")
    Object deletePlanMonthData(@RequestBody LogisticsPlanMonthDTO logisticsPlanMonthDTO,@RequestHeader("token") String token);
    /**
     * 新增日运输计划资料
     * @param logisticsPlanDayDTO
     * @param token
     * @return
     */
    @PostMapping("/logistics/plan/day/addPlanDayData")
    Object addPlanDayData(@RequestBody LogisticsPlanDayDTO logisticsPlanDayDTO, @RequestHeader("token") String token);
    /**
     * 修改日运输计划资料
     * @param logisticsPlanDayDTO
     * @param token
     * @return
     */
    @PutMapping("/logistics/plan/day/updatePlanDayData")
    Object updatePlanDayData(@RequestBody LogisticsPlanDayDTO logisticsPlanDayDTO,@RequestHeader("token") String token);
    /**
     * 删除日运输计划资料
     * @param logisticsPlanDayDTO
     * @param token
     * @return
     */
    @DeleteMapping("/logistics/plan/day/deletePlanDayData")
    Object deletePlanDayData(@RequestBody LogisticsPlanDayDTO logisticsPlanDayDTO,@RequestHeader("token") String token);

    /**
     * 新增临时用车资料
     * @param logisticsTempDayDTO
     * @param token
     * @return
     */
    @PostMapping("/logistics/temp/day/addTempDayData")
    Object addTempDayData(@RequestBody LogisticsTempDayDTO logisticsTempDayDTO, @RequestHeader("token") String token);
    /**
     * 修改临时用车资料
     * @param logisticsTempDayDTO
     * @param token
     * @return
     */
    @PutMapping("/logistics/temp/day/updateTempDayData")
    Object updateTempDayData(@RequestBody LogisticsTempDayDTO logisticsTempDayDTO,@RequestHeader("token") String token);
    /**
     * 删除临时用车资料
     * @param logisticsTempDayDTO
     * @param token
     * @return
     */
    @DeleteMapping("/logistics/temp/day/deleteTempDayData")
    Object deleteTempDayData(@RequestBody LogisticsTempDayDTO logisticsTempDayDTO,@RequestHeader("token") String token);
    /**
     * 新增运输实际资料
     * @param logisticsTransActualDTO
     * @param token
     * @return
     */
    @PostMapping("/logistics/trans/actual/addTransportActual")
    Object addTransportActual(@RequestBody LogisticsTransActualDTO logisticsTransActualDTO, @RequestHeader("token") String token);
    /**
     * 修改日运输实际资料
     * @param logisticsTransActualDTO
     * @param token
     * @return
     */
    @PutMapping("/logistics/trans/actual/updateTransportActual")
    Object updateTransportActual(@RequestBody LogisticsTransActualDTO logisticsTransActualDTO,@RequestHeader("token") String token);
    /**
     * 删除日运输实际资料
     * @param logisticsTransActualDTO
     * @param token
     * @return
     */
    @DeleteMapping("/logistics/trans/actual/deleteTransportActual")
    Object deleteTransportActual(@RequestBody LogisticsTransActualDTO logisticsTransActualDTO,@RequestHeader("token") String token);
    /**
     * 根据条件修改月运输计划资料
     * @param logisticsPlanMonthDTO
     * @param token
     */
    @PutMapping("/logistics/plan/month/updatePlanMonthByParams")
    void updatePlanMonthByParams(@RequestBody LogisticsPlanMonthDTO logisticsPlanMonthDTO, @RequestHeader("token") String token);
    /**
     * 根据条件更新日运输计划
     * @param logisticsPlanDayDTO
     * @param token
     */
    @PutMapping("/logistics/plan/day/updatePlanDayByParams")
    void updatePlanDayByParams(@RequestBody LogisticsPlanDayDTO logisticsPlanDayDTO,@RequestHeader("token") String token);
    /**
     * 根据条件更新临时用车资料
     * @param logisticsTempDayDTO
     * @param token
     */
    @PutMapping("/logistics/temp/day/updateTempDayByParams")
    void updateTempDayByParams(@RequestBody LogisticsTempDayDTO logisticsTempDayDTO, @RequestHeader("token") String token);
    /**
     * 物流管理-MR倒运计划新增
     * @param dto
     * @param token
     * @return
     */
    @PostMapping("/logistics/transport/mrplan/addLogisticsMrPlan")
    Object save(@RequestBody LogisticsTransportPlanMrDTO dto, @RequestHeader("token") String token);
    /**
     * 物流管理-MR倒运计划修改
     * @param dto
     * @param token
     * @return
     */
    @PutMapping("/logistics/transport/mrplan/updateLogisticsMrPlan")
    Object update(@RequestBody LogisticsTransportPlanMrDTO dto,@RequestHeader("token") String token);
    /**
     * 物流管理-MR倒运计划删除
     * @param dto
     * @param token
     * @return
     */
    @DeleteMapping("/logistics/transport/mrplan/deleteLogisticsMrPlan")
    Object delete(@RequestBody LogisticsTransportPlanMrDTO dto,@RequestHeader("token") String token);
    /**
     * 运输实绩-焦炭每日倒运新增
     * @param dto
     * @param token
     * @return
     */
    @PostMapping("/logistics/trans/coke/addActualCoke")
    Object addActualCoke(@RequestBody LogisticsTransActualOtherDTO dto, @RequestHeader("token") String token);
    /**
     * 运输实绩-焦丁每日倒运新增
     * @param dto
     * @param token
     * @return
     */
    @PostMapping("/logistics/trans/nut/addActualNut")
    Object addActualNut(@RequestBody LogisticsTransActualOtherDTO dto, @RequestHeader("token") String token);
    /**
     * 运输实绩-焦粉每日倒运新增
     * @param dto
     * @param token
     * @return
     */
    @PostMapping("/logistics/trans/fines/addActualFines")
    Object addActualFines(@RequestBody LogisticsTransActualOtherDTO dto, @RequestHeader("token") String token);
}
