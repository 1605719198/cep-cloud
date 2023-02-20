package com.jlkj.equip.MG.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.equip.MG.domain.EquipmentDownTypeTree;
import com.jlkj.equip.MG.domain.EquipmentMgmc2;
import com.jlkj.equip.MG.domain.EquipmentMgms1;
import com.jlkj.equip.MG.domain.EquipmentMgmsa;
import com.jlkj.equip.MG.dto.EquipmentMgmsaAddDTO;
import com.jlkj.equip.MG.service.IEquipmentDownTypeTreeService;
import com.jlkj.equip.MG.service.IEquipmentMgmc2Service;
import com.jlkj.equip.MG.service.IEquipmentMgms1Service;
import com.jlkj.equip.MG.service.IEquipmentMgmsaService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 停机责任分摊纪录 前端控制器
 * </p>
 *
 * @author xin
 * @since 2022-05-23
 */
@RestController
@RequestMapping("/equipment/mgmsa")
public class EquipmentMgmsaController {
    private final IEquipmentMgmsaService iEquipmentMgmsaService;
    private final IEquipmentMgms1Service iEquipmentMgms1Service;
    private final IEquipmentDownTypeTreeService equipmentDownTypeTreeService;
    private final IEquipmentMgmc2Service equipmentMgmc2Service;

    public EquipmentMgmsaController(IEquipmentMgmsaService iEquipmentMgmsaService, IEquipmentMgms1Service iEquipmentMgms1Service, IEquipmentDownTypeTreeService equipmentDownTypeTreeService, IEquipmentMgmc2Service equipmentMgmc2Service) {
        this.iEquipmentMgmsaService = iEquipmentMgmsaService;
        this.iEquipmentMgms1Service = iEquipmentMgms1Service;
        this.equipmentDownTypeTreeService = equipmentDownTypeTreeService;
        this.equipmentMgmc2Service = equipmentMgmc2Service;
    }

    /**
     * 新增缺陷记录
     */
    @Operation(summary = "新增分摊记录")
    @PostMapping("/insert")
    @Log(title = "新增分摊记录",businessType = BusinessType.INSERT)
    public Object addDefect(@RequestBody EquipmentMgmsaAddDTO equipmentMgmsa) {
        try {
            // 已分摊责任总时间
            BigDecimal isShareTime = BigDecimal.ZERO;
            if (StringUtils.isNotNull(iEquipmentMgmsaService.getShareTime(equipmentMgmsa.getMgms1No(),null))){
                isShareTime = isShareTime.add(iEquipmentMgmsaService.getShareTime(equipmentMgmsa.getMgms1No(),null));
            }
            // 总时间
            BigDecimal totalTime = equipmentMgmsa.getTotalTime();
            if ( (equipmentMgmsa.getShareTime().add(isShareTime)).compareTo(totalTime) > 0 ) {
                return AjaxResult.error("分摊总时间超出停机总时间，请重新输入！");
            }
            // 当天生成的第一个编号为 停机记录编号+001
            String initOrderNo = equipmentMgmsa.getMgms1No() + "001";
            //查询当前编号最大的数据
            EquipmentMgmsa mgmsa1 = new EquipmentMgmsa();
            QueryWrapper<EquipmentMgmsa> wrapper = new QueryWrapper<>();
            wrapper.eq("mgms1_no",equipmentMgmsa.getMgms1No());
            wrapper.orderByDesc("mgmsa_no");
            wrapper.last("limit 1");
            List<EquipmentMgmsa> list = iEquipmentMgmsaService.list(wrapper);
            if (list.size() > 0){
                mgmsa1 = list.get(0);
            }
            if (StringUtils.isNotNull(mgmsa1) && StringUtils.isNotNull(mgmsa1.getMgms1No())) {
                // 获取停机记录编号
                String dateTimeInfo = mgmsa1.getMgms1No();
                // 判断这两个编号是否一致
                if (equipmentMgmsa.getMgms1No().equals(mgmsa1.getMgms1No())){
                    // 如果是当天生成的
                    String orderNo = mgmsa1.getMgmsaNo().substring(12);
                    initOrderNo =  dateTimeInfo + String.format("%03d",Integer.parseInt(orderNo) + 1);
                }
            }
            EquipmentMgmsa mgmsa = new EquipmentMgmsa();
            mgmsa.setMgmsaNo(initOrderNo);
            mgmsa.setMgmsaName(equipmentMgmsa.getMgmsaName());
            mgmsa.setMgms1No(equipmentMgmsa.getMgms1No());
            mgmsa.setMgma1No(equipmentMgmsa.getMgma1No());
            mgmsa.setMgmc1No(equipmentMgmsa.getMgmc1No());
            mgmsa.setMgmc1Name(equipmentMgmsa.getMgmc1Name());
            mgmsa.setMgmc2No(equipmentMgmsa.getMgmc2No());
            mgmsa.setMgmc2Name(equipmentMgmsa.getMgmc2Name());
            mgmsa.setShareTime(equipmentMgmsa.getShareTime());
            mgmsa.setSharePercent(equipmentMgmsa.getSharePercent());
            mgmsa.setCreateUser(equipmentMgmsa.getCreateUser());
            mgmsa.setCreateUserName(equipmentMgmsa.getCreateUserName());
            boolean save = iEquipmentMgmsaService.save(mgmsa);
            if (save) {
                return AjaxResult.success("新增成功");
            } else {
                return AjaxResult.error("新增失败");
            }
        } catch (Exception e) {
            return AjaxResult.error();
        }
    }
    /**
     * 删除缺陷记录
     */
    @Operation(summary = "删除分摊记录")
    @DeleteMapping("/delete")
    @Log(title = "删除分摊记录",businessType = BusinessType.DELETE)
     public Object deleteDefect(@RequestParam List<String> ids) {
        try {
            boolean updata = iEquipmentMgmsaService.removeBatchByIds(ids);
            if (updata) {
                return AjaxResult.success("删除成功");
            } else {
                return AjaxResult.error("删除失败");
            }

        } catch (Exception e) {
            return AjaxResult.error();
        }
    }
    /**
     * 查询缺陷记录
     */
    @Operation(summary = "查询分摊记录")
    @GetMapping("/query")
    @Log(title = "查询分摊记录",businessType = BusinessType.OTHER)
    public Object queryDefect(@RequestParam(defaultValue ="1") Integer pageNum,
                              @RequestParam (defaultValue = "20")Integer pageSize,
                              @RequestParam(defaultValue = "") String mgmsaName,
                              @RequestParam(defaultValue = "") String mgms1No
    )
    {
        try {
            Map<String, Object> map = new HashMap<>(1);
            map.put("mgmsa_name", mgmsaName);
            map.put("mgms1_no", mgms1No);
            Page<EquipmentMgmsa> equipmentMgsaPage = iEquipmentMgmsaService.page(new Page<>(pageNum, pageSize), Wrappers.<EquipmentMgmsa>query().allEq(map, false).orderByAsc("mgmsa_no"));
            return AjaxResult.success("成功", equipmentMgsaPage);
        } catch (Exception e) {
            return AjaxResult.error();
        }
    }
    /**
     * 更新缺陷记录
     */
    @Operation(summary = "更新分摊记录")
    @PostMapping("/update")
    @Log(title = "更新分摊记录",businessType = BusinessType.UPDATE)
    public Object updataDefect(@RequestBody EquipmentMgmsaAddDTO equipmentMgmsa) {
        try {
            // 已分摊责任总时间
            BigDecimal isShareTime = BigDecimal.ZERO;
            if (StringUtils.isNotNull(iEquipmentMgmsaService.getShareTime(equipmentMgmsa.getMgms1No(),equipmentMgmsa.getId()))){
                isShareTime = isShareTime.add(iEquipmentMgmsaService.getShareTime(equipmentMgmsa.getMgms1No(),equipmentMgmsa.getId()));
            }
            // 总时间
            BigDecimal totalTime = equipmentMgmsa.getTotalTime();
            if ( (equipmentMgmsa.getShareTime().add(isShareTime)).compareTo(totalTime) > 0 ) {
                return AjaxResult.error("分摊总时间超出停机总时间，请重新输入！");
            }
            EquipmentMgmsa equipmentMgmsa1 = new EquipmentMgmsa();
            BeanUtils.copyProperties(equipmentMgmsa,equipmentMgmsa1);
            boolean updata = iEquipmentMgmsaService.updateById(equipmentMgmsa1);
            if (updata) {
                return AjaxResult.success("更新成功");
            } else {
                return AjaxResult.error("更新失败");
            }
        } catch (Exception e) {
            return AjaxResult.error();
        }
    }
    /**
     * 更新缺陷记录
     */
    @Operation(summary = "查询分摊单条")
    @GetMapping("/queryone")
    @Log(title = "查询分摊单条",businessType = BusinessType.OTHER)
    public Object queryone(@RequestParam String id) {
        try {
            EquipmentMgmsa equipmentMgmsa = iEquipmentMgmsaService.getOne(new QueryWrapper<EquipmentMgmsa>().eq("id", id));
            if (StringUtils.isNotNull(equipmentMgmsa)) {
                return AjaxResult.success(equipmentMgmsa);
            } else {
                return AjaxResult.error("查询失败");
            }
        } catch (Exception e) {
            return AjaxResult.error();
        }
    }
    /**
     * 查询停机类型
     *
     */
    @Operation(summary = "查询停机细类型")
    @GetMapping("/querymgmc1no")
    @Log(title = "查询停机细类型",businessType = BusinessType.OTHER)
    public Object querygropcode(@RequestParam String mgmc1No) {
        EquipmentDownTypeTree typeTree = equipmentDownTypeTreeService.getOne(new QueryWrapper<EquipmentDownTypeTree>().eq("equipment_down_type_code",mgmc1No));
        List<EquipmentDownTypeTree> typeList = equipmentDownTypeTreeService.list(new QueryWrapper<EquipmentDownTypeTree>().eq("equipment_down_type_parent_code", typeTree.getId()));
        List<Map<String, Object>> returnList = typeList.stream().map(item -> {
            Map<String, Object> map = new HashMap<>(2);
            map.put("label", item.getEquipmentDownTypeName());
            map.put("value", item.getEquipmentDownTypeCode());
            return map;
        }).collect(Collectors.toList());
        return AjaxResult.success(returnList);
    }
    /**
     * 查询停机原因编号
     *
     */
    @Operation(summary = "查询停机原因编号")
    @GetMapping("/querymgmc2no")
    @Log(title = "查询停机原因编号",businessType = BusinessType.OTHER)
    public Object querymgmc2no(@RequestParam String mgmc2No) {
        List<EquipmentMgmc2> mgmc2List= equipmentMgmc2Service.list(new QueryWrapper<EquipmentMgmc2>().eq("mgmc1no",mgmc2No));
        List<Map<String, Object>> returnList = mgmc2List.stream().map(item -> {
            Map<String, Object> map = new HashMap<>(2);
            map.put("label", item.getMgmc2name());
            map.put("value", item.getMgmc2no());
            return map;
        }).collect(Collectors.toList());
        return AjaxResult.success(returnList);
    }
    /**
     * 查询立案详情
     */
    @Operation(summary = "查询立案记录")
    @GetMapping("/queryquerydetails")
    @Log(title = "查询立案记录",businessType = BusinessType.OTHER)
    public Object queryMgms1(@RequestParam String mgms1No) {
        try {
            EquipmentMgms1 mgms1 = iEquipmentMgms1Service.getOne(new QueryWrapper<EquipmentMgms1>().eq("mgms1_no", mgms1No));
            //总时间
            BigDecimal totalTime = mgms1.getTotalTime();
            //已分摊时间
            BigDecimal shareTime = BigDecimal.ZERO;
            List<EquipmentMgmsa> list = iEquipmentMgmsaService.list(new QueryWrapper<EquipmentMgmsa>().eq(StringUtils.isNotEmpty(mgms1No),"mgms1_no", mgms1No));
            if (StringUtils.isNotNull(list)) {
                for (EquipmentMgmsa mgmsa : list) {
                    shareTime = shareTime.add(mgmsa.getShareTime());
                }
            }
            //剩余分摊时间
            BigDecimal unShareTime = totalTime.subtract(shareTime);
            Map<String,Object> returnMap = new HashMap<>(16);
            returnMap.put("data",mgms1);
            returnMap.put("unShareTime",unShareTime);
            return AjaxResult.success(returnMap);
        } catch (Exception e) {
            return AjaxResult.error();
        }
    }
    /**
     * 验证停机类型是否重复
     */
    @Operation(summary = "验证停机类型是否重复")
    @GetMapping("/repeatVal")
    @Log(title = "验证停机类型是否重复",businessType = BusinessType.OTHER)
    public Object repeatVal(@RequestParam(defaultValue = "") String optionValue,
                            @RequestParam(defaultValue = "") String mgms1No,
                            @RequestParam(defaultValue = "") String id) {
        QueryWrapper<EquipmentMgmsa> wrapper = new QueryWrapper<>();
        wrapper.eq("mgms1_no",mgms1No);
        wrapper.eq("mgmc1_no",optionValue);
        // 修改时验证重复需要排除当前数据的 ID
        if (StringUtils.isNotEmpty(id)) {
            wrapper.ne("id",id);
        }
        List<EquipmentMgmsa> list = iEquipmentMgmsaService.list(wrapper);
        if (list.size() > 0) {
            return AjaxResult.error("停机类型已存在");
        } else {
            return AjaxResult.success(true);
        }
    }
}
