package com.jlkj.equip.ME.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.datascope.annotation.ParamModel;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.equip.ME.domain.EquipmentAccount;
import com.jlkj.equip.ME.domain.EquipmentBasicProperties;
import com.jlkj.equip.ME.dto.EquipmentAccountDTO;
import com.jlkj.equip.ME.dto.EquipmentBasicAccountDTO;
import com.jlkj.equip.ME.dto.EquipmentBasicPropertiesDTO;
import com.jlkj.equip.ME.dto.EquipmentQueryAccountDTO;
import com.jlkj.equip.ME.service.IEquipmentAccountService;
import com.jlkj.equip.ME.service.IEquipmentBasicPropertiesService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * <p>
 * 设备台账 前端控制器
 * </p>
 *
 * @author xin
 * @since 2022-06-13
 */
@RestController
@RequestMapping("/equipment/basic/properties")
public class EquipmentBasicPropertiesController {

    private final IEquipmentBasicPropertiesService equipmentBasicPropertiesService;
    private final IEquipmentAccountService equipmentAccountService;

    public EquipmentBasicPropertiesController(IEquipmentBasicPropertiesService equipmentBasicPropertiesService, IEquipmentAccountService equipmentAccountService) {
        this.equipmentBasicPropertiesService = equipmentBasicPropertiesService;
        this.equipmentAccountService = equipmentAccountService;
    }

    /**
     * 新增设备台账基础资料
     */
    @Operation(summary = "新增设备台账基础资料", description = "新增设备台账基础资料")
    @PostMapping("/addEquipmentBasicProperties")
    @Log(title = "新增设备台账基础资料",businessType = BusinessType.INSERT)
    public Object addEquipmentBasicProperties(@RequestBody Map<String,Object> dataMap){
        EquipmentBasicProperties basicProperties = new EquipmentBasicProperties();
        EquipmentAccount equipmentAccount = new EquipmentAccount();
        ObjectMapper objectMapper = new ObjectMapper();
        Object baseForm = dataMap.get("baseForm");
        Object ledgerForm = dataMap.get("ledgerForm");
        // 基础资料
        EquipmentBasicPropertiesDTO basicPropertiesDTO = objectMapper.convertValue(baseForm, EquipmentBasicPropertiesDTO.class);
        // 设备台账
        EquipmentAccountDTO equipmentAccountDTO = objectMapper.convertValue(ledgerForm, EquipmentAccountDTO.class);

        QueryWrapper<EquipmentBasicProperties> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotNull(basicPropertiesDTO.getEquipmentNumbert()),"equipment_numbert",basicPropertiesDTO.getEquipmentNumbert());
        List<EquipmentBasicProperties> list = equipmentBasicPropertiesService.list(wrapper);
        if (list.size() > 0) {
            return AjaxResult.error("设备编码已存在，请重新输入！");
        }
        BeanUtils.copyProperties(basicPropertiesDTO,basicProperties);
        BeanUtils.copyProperties(equipmentAccountDTO,equipmentAccount);
        // 生成UUID与台账设备ID绑定
        basicProperties.setId(UUID.randomUUID().toString());
        equipmentBasicPropertiesService.save(basicProperties);
        equipmentAccount.setEquipmentId(basicProperties.getId());
        equipmentAccount.setEquipmentName(basicProperties.getEquipmentName());
        equipmentAccountService.save(equipmentAccount);

        return AjaxResult.success();
    }

    /**
     * 联表查询设备台账信息
     */
    @Operation(summary = "联表查询设备台账信息", description = "联表查询设备台账信息")
    @GetMapping("/queryEquipmentAccount")
    @Log(title = "联表查询设备台账信息",businessType = BusinessType.OTHER)
    public Object queryEquipmentAccount(){
        IPage<EquipmentBasicAccountDTO> pageData = equipmentBasicPropertiesService.queryEquipmentAccount(1,10);
        //总记录数
        long total = pageData.getTotal();
        // 数据list集合
        List<EquipmentBasicAccountDTO> records = pageData.getRecords();
        Map<String,Object> dataMap = new HashMap<>(16);
        dataMap.put("total",total);
        dataMap.put("list",records);
        return AjaxResult.success(dataMap);
    }
    /**
     * 条件查询设备台账信息
     */
    @Operation(summary = "条件查询设备台账信息", description = "条件查询设备台账信息")
    @GetMapping("/getEquipmentAccountByParams")
    @Log(title = "条件查询设备台账信息",businessType = BusinessType.OTHER)
    public Object queryEquipmentAccount(@ParamModel EquipmentQueryAccountDTO equipmentQueryAccountDTO){
        IPage<EquipmentBasicAccountDTO> pageData = equipmentBasicPropertiesService.getEquipmentAccountByParams(equipmentQueryAccountDTO);
        //总记录数
        long total = pageData.getTotal();
        // 数据list集合
        List<EquipmentBasicAccountDTO> records = pageData.getRecords();
        Map<String,Object> dataMap = new HashMap<>(5);
        dataMap.put("total",total);
        dataMap.put("list",records);
        return AjaxResult.success(dataMap);
    }
    /**
     * 删除设备台账信息
     */
    @Operation(summary = "删除设备台账信息", description = "删除设备台账信息")
    @DeleteMapping("/deleteAccountData/{ids}")
    @Log(title = "删除设备台账信息",businessType = BusinessType.DELETE)
    public Object deleteAccountData(@PathVariable String[] ids) {
        List<String> idList = Arrays.asList(ids);
        equipmentBasicPropertiesService.removeByIds(idList);
        for (String itemId : idList) {
            QueryWrapper<EquipmentAccount> wrapper = new QueryWrapper<>();
            wrapper.eq("equipment_id",itemId);
            equipmentAccountService.remove(wrapper);
        }
        return AjaxResult.success();
    }
    /**
     * 根据ID查询设备台账及基础资料信息
     */
    @Operation(summary = "根据ID查询设备台账及基础资料信息", description = "根据ID查询设备台账及基础资料信息")
    @GetMapping("/getAccountValueById")
    @Log(title = "根据ID查询设备台账及基础资料信息",businessType = BusinessType.OTHER)
    public Object getAccountValueById(@RequestParam(defaultValue = "") String id) {
        EquipmentBasicProperties basicProperties = equipmentBasicPropertiesService.getById(id);
        EquipmentAccount account = equipmentAccountService.getOne(new QueryWrapper<EquipmentAccount>().eq("equipment_id",id));
        Map<String, Object> returnMap = new HashMap<>(5);
        returnMap.put("baseForm",basicProperties);
        returnMap.put("ledgerForm",account);
        return AjaxResult.success(returnMap);
    }
    /**
     * 根据ID修改设备台账及基础资料信息
     */
    @Operation(summary = "根据ID修改设备台账及基础资料信息", description = "根据ID修改设备台账及基础资料信息")
    @PutMapping("/updateAccountValueById")
    @Log(title = "根据ID修改设备台账及基础资料信息",businessType = BusinessType.OTHER)
    public Object updateAccountValueById(@RequestBody Map<String,Object> dataMap) {
        ObjectMapper objectMapper = new ObjectMapper();
        Object baseForm = dataMap.get("baseForm");
        Object ledgerForm = dataMap.get("ledgerForm");
        // 基础资料
        EquipmentBasicProperties basicProperties = objectMapper.convertValue(baseForm, EquipmentBasicProperties.class);
        // 设备台账
        EquipmentAccount account = objectMapper.convertValue(ledgerForm, EquipmentAccount.class);
        QueryWrapper<EquipmentBasicProperties> wrapper = new QueryWrapper<>();
        wrapper.ne(StringUtils.isNotNull(basicProperties.getId()),"id",basicProperties.getId());
        wrapper.and(wrapper1 -> wrapper1.eq(StringUtils.isNotNull(basicProperties.getEquipmentNumbert()),"equipment_numbert",basicProperties.getEquipmentNumbert()));
        List<EquipmentBasicProperties> list = equipmentBasicPropertiesService.list(wrapper);
        if (list.size() > 0) {
            return AjaxResult.error("设备编码已存在，请重新输入！");
        }
        equipmentBasicPropertiesService.updateById(basicProperties);
        boolean update = equipmentAccountService.updateById(account);
        return AjaxResult.success(update);
    }
    /**
     * 根据设备位置查询设备信息
     */
    @Operation(summary = "根据设备位置查询设备信息", description = "根据设备位置查询设备信息")
    @GetMapping("/getEquipmentByLocationId")
    @Log(title = "根据设备位置查询设备信息",businessType = BusinessType.OTHER)
    public Object getEquipmentByLocationId(@RequestParam(defaultValue = "") String locationId) {
        List<EquipmentBasicProperties> list = equipmentBasicPropertiesService.getEquipmentByLocationId(locationId);
        return AjaxResult.success(list);
    }

    /**
     * 根据树节点ID查询，判断是设备域还是上级设备
     * @param id
     * @return
     */
    @Operation(summary = "根据树节点ID查询t_equipment_basic_properties，判断是设备域还是上级设备", description = "根据树节点ID查询t_equipment_basic_properties，判断是设备域还是上级设备")
    @GetMapping("/getEquipmentBasicNumById")
    @Log(title = "根据树节点ID查询，判断是设备域还是上级设备",businessType = BusinessType.OTHER)
    public Object getEquipmentBasicNumById(@RequestParam(defaultValue = "") String id) {
        EquipmentBasicProperties properties = equipmentBasicPropertiesService.getById(id);
        Map<String,String> resultMap = new HashMap<>();
        if (StringUtils.isNotNull(properties)) {
            resultMap.put("equipment_location",properties.getEquipmentLocation());
            resultMap.put("upper_level",id);
        } else {
            resultMap.put("equipment_location",id);
            resultMap.put("upper_level","");
        }
        return AjaxResult.success(resultMap);
    }
}
