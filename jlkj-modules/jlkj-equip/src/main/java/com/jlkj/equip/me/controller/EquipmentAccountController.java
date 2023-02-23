package com.jlkj.equip.me.controller;


import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.equip.me.domain.EquipmentBasicProperties;
import com.jlkj.equip.me.service.IEquipmentAccountService;
import com.jlkj.equip.mg.dto.EquipmentDomainTreeDTO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 设备台账 前端控制器
 * </p>
 *
 * @author xin
 * @since 2022-06-13
 */
@RestController
@RequestMapping("/equipment/account")
public class EquipmentAccountController {

    private final IEquipmentAccountService equipmentAccountService;

    public EquipmentAccountController(IEquipmentAccountService equipmentAccountService) {
        this.equipmentAccountService = equipmentAccountService;
    }

    /**
     * 设备树、上级设备
     */
    @Operation(summary = "设备树、上级设备", description = "设备树、上级设备")
    @GetMapping("/getEquipmentAccountTree")
    @Log(title = "查询设备树、上级设备",businessType = BusinessType.OTHER)
    public Object getEquipmentAccountTree(){
        List<EquipmentBasicProperties> list = equipmentAccountService.getEquipmentAccountTree();
        return AjaxResult.success(list);
    }

    /**
     * 设备域与设备树结构
     */
    @Operation(summary = "设备域与设备树结构", description = "设备域与设备树结构")
    @GetMapping("/getEquipmentCombineTree")
    @Log(title = "设备域与设备树结构",businessType = BusinessType.OTHER)
    public Object getEquipmentCombineTree(){
        List<EquipmentDomainTreeDTO> list = equipmentAccountService.getEquipmentCombineTree();
        return AjaxResult.success(list);
    }
}
