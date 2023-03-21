package com.jlkj.rabbit.feign.energyprovider;

import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.dto.energy.ee.*;
import com.jlkj.rabbit.dto.energy.energyconsumptionoutputaccounting.InsertEnergyConsumptionOutputAccountingDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author 智能研发室 黄兵
 * @Description 能源模块远程调用
 * @date 2022/5/2 - 9:52
 */
@Component
@FeignClient(value = "energy-service-provider")
public interface EnergyFeignService {

    /**
     * 新增能源代码资料
     * @param energyCodeDTO
     * @param token
     * @return
     */
    @PostMapping("energy/code/add")
    Object addEnergyCode(EnergyCodeDTO energyCodeDTO, @RequestHeader("token") String token);

    /**
     * 修改能源代码资料
     * @param energyCodeDTO
     * @param token
     * @return
     */
    @PostMapping("/energy/code/update")
    Object updateEnergyCode(EnergyCodeDTO energyCodeDTO, @RequestHeader("token") String token);

    /**
     * 删除能源代码资料
     * @param engyId
     * @param token
     * @return
     */
    @DeleteMapping("/energy/code/deleteErp")
    Object deleteEnergyCode(@RequestParam String engyId, @RequestHeader("token") String token);

    /**
     * 新增固液体能源代码对应料号维护资料
     * @param energyCodeFoMaterialDTO
     * @param token
     * @return
     */
    @PostMapping("/energy/code/material/add")
    Object addEnergyCodeFoMaterial(EnergyCodeFoMaterialDTO energyCodeFoMaterialDTO, @RequestHeader("token") String token);

    /**
     * 修改固液体能源代码对应料号维护资料
     * @param energyCodeFoMaterialDTO
     * @param token
     * @return
     */
    @PostMapping("/energy/code/material/update")
    Object updateEnergyCodeFoMaterial(EnergyCodeFoMaterialDTO energyCodeFoMaterialDTO, @RequestHeader("token") String token);

    /**
     * 删除固液体能源代码对应料号维护资料
     * @param engyId
     * @param token
     * @return
     */
    @DeleteMapping("/energy/code/material/deleteErp")
    Object deleteEnergyCodeFoMaterial(@RequestParam String engyId, @RequestHeader("token") String token);

    /**
     * 新增月计划能耗资料
     * @param energyMonthPlanConsumeDTO
     * @param token
     * @return
     */
    @PostMapping("/energy/month/plan/consume/ea01/xctl")
    Object addEnergyMonthPlanConsume(EnergyMonthPlanConsumeDTO energyMonthPlanConsumeDTO, @RequestHeader("token") String token);

    /**
     * 修改月计划能耗资料
     * @param energyMonthPlanConsumeDTO
     * @param token
     * @return
     */
    @PostMapping("/energy/month/plan/consume/update")
    Object updateEnergyMonthPlanConsume(EnergyMonthPlanConsumeDTO energyMonthPlanConsumeDTO,@RequestHeader("token") String token);

    /**
     * 删除月计划能耗资料
     * @param id
     * @param token
     * @return
     */
    @DeleteMapping("/energy/month/plan/consume/delete")
    Object deleteEnergyMonthPlanConsume(@RequestParam List<String> id,@RequestHeader("token") String token);

    /**
     * 新增月计划产量资料
     * @param energyMonthPlanOutputDTO
     * @param token
     * @return
     */
    @PostMapping("/energy/month/Plan/OutPut/ea02/xctl")
    Object addEnergyMonthPlanOutPut(EnergyMonthPlanOutputDTO energyMonthPlanOutputDTO, @RequestHeader("token") String token);

    /**
     * 修改月计划产量资料
     * @param energyMonthPlanOutputDTO
     * @param token
     * @return
     */
    @PostMapping("/energy/month/Plan/OutPut/update")
    Object updateEnergyMonthPlanOutPut(EnergyMonthPlanOutputDTO energyMonthPlanOutputDTO,@RequestHeader("token") String token);

    /**
     * 删除月计划产量资料
     * @param id
     * @param token
     * @return
     */
    @DeleteMapping("/energy/month/Plan/OutPut/delete")
    Object deleteEnergyMonthPlanOutPut(@RequestParam List<String> id,@RequestHeader("token") String token);

    /**
     * 新增能介资料
     * @param energyTreeDTO
     * @param token
     * @return
     */
    @PostMapping("/energy/mediated/data/ea03/xctl")
    Object addEnergyTree(EnergyTreeDTO energyTreeDTO, @RequestHeader("token") String token);

    /**
     * 修改月计划产量资料
     * @param energyTreeDTO
     * @param token
     * @return
     */
    @PostMapping("/energy/mediated/data/update")
    Object updateEnergyTree(EnergyTreeDTO energyTreeDTO,@RequestHeader("token") String token);

    /**
     * 删除月计划产量资料
     * @param id
     * @param token
     * @return
     */
    @DeleteMapping("/energy/mediated/data/delete")
    Object deleteEnergyTree(@RequestParam List<String> id,@RequestHeader("token") String token);

    /**
     * 接收报文执行每日能源产耗资料(t_energy_consume_output)档的新增
     * @param energyConsumeOutputDTO
     * @return
     */
    @PostMapping("/energy/consume/output/eeozc1/xctl")
    Object addEnergyConsumeOutput(EnergyConsumeOutputDTO energyConsumeOutputDTO);

    /**
     * 新增日计划能耗资料
     * @param energyDayPlanConsumeDTO
     * @param token
     * @return
     */
    @PostMapping("/energy/day/plan/consume/ea04/xctl")
    Object addEnergyDayPlanConsume(EnergyDayPlanConsumeDTO energyDayPlanConsumeDTO,@RequestHeader("token") String token);

    /**
     * 修改日计划能耗资料
     * @param energyDayPlanConsumeDTO
     * @param token
     * @return
     */
    @PostMapping("/energy/day/plan/consume/update")
    Object updateEnergyDayPlanConsume(EnergyDayPlanConsumeDTO energyDayPlanConsumeDTO,@RequestHeader("token") String token);

    /**
     * 删除日计划能耗资料
     * @param id
     * @param token
     * @return
     */
    @DeleteMapping("/energy/day/plan/consume/delete")
    Object deleteEnergyDayPlanConsume(@RequestParam List<String> id,@RequestHeader("token") String token);

    /**
     * 新增日计划产量资料
     * @param energyDayPlanOutputDTO
     * @param token
     * @return
     */
    @PostMapping("/energy/day/Plan/OutPut/ea05/xctl")
    Object addEnergyDayPlanOutPut(EnergyDayPlanOutputDTO energyDayPlanOutputDTO,@RequestHeader("token") String token);

    /**
     * 修改日计划产量资料
     * @param energyDayPlanOutputDTO
     * @param token
     * @return
     */
    @PostMapping("/energy/day/Plan/OutPut/update")
    Object updateEnergyDayPlanOutPut(EnergyDayPlanOutputDTO energyDayPlanOutputDTO,@RequestHeader("token") String token);

    /**
     * 删除日计划产量资料
     * @param id
     * @param token
     * @return
     */
    @DeleteMapping("/energy/day/Plan/OutPut/delete")
    Object deleteEnergyDayPlanOutPut(@RequestParam List<String> id,@RequestHeader("token") String token);

    /**
     * 能源耗用产出账务信息-新增
     * @param dto dto参数
     * @param token token
     * @return 结果
     */
    @PostMapping("/energyconsumptionoutputaccounting/insertEnergyConsumptionOutputAccounting")
    AjaxResult insertEnergyConsumptionOutputAccounting(InsertEnergyConsumptionOutputAccountingDTO dto, @RequestHeader("token") String token);
}
