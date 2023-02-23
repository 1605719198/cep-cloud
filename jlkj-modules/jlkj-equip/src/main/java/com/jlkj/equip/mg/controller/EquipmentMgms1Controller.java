package com.jlkj.equip.mg.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.equip.mg.domain.EquipmentDownTypeTree;
import com.jlkj.equip.mg.domain.EquipmentMgma1;
import com.jlkj.equip.mg.domain.EquipmentMgmc2;
import com.jlkj.equip.mg.domain.EquipmentMgms1;
import com.jlkj.equip.mg.dto.EquipmentMgms1QueryDTO;
import com.jlkj.equip.mg.service.IEquipmentDownTypeTreeService;
import com.jlkj.equip.mg.service.IEquipmentMgma1Service;
import com.jlkj.equip.mg.service.IEquipmentMgmc2Service;
import com.jlkj.equip.mg.service.IEquipmentMgms1Service;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 停机纪录 前端控制器
 * </p>
 *
 * @author xin
 * @since 2022-05-16
 */
@RestController
@RequestMapping("/equipment/mgms1")
public class EquipmentMgms1Controller {
    private final IEquipmentMgms1Service equipmentMgms1Service;
    private final IEquipmentMgma1Service equipmentMgma1Service;
    private final IEquipmentDownTypeTreeService equipmentDownTypeTreeService;
    private final IEquipmentMgmc2Service equipmentMgmc2Service;

    public EquipmentMgms1Controller(IEquipmentMgms1Service equipmentMgms1Service, IEquipmentMgma1Service equipmentMgma1Service, IEquipmentDownTypeTreeService equipmentDownTypeTreeService, IEquipmentMgmc2Service equipmentMgmc2Service) {
        this.equipmentMgms1Service = equipmentMgms1Service;
        this.equipmentMgma1Service = equipmentMgma1Service;
        this.equipmentDownTypeTreeService = equipmentDownTypeTreeService;
        this.equipmentMgmc2Service = equipmentMgmc2Service;
    }

    /**
     * 新增立案记录
     */
    @Operation(summary = "新增立案记录")
    @PostMapping("/insert")
    @Log(title = "停机立案新增",businessType = BusinessType.INSERT)
    public Object addDefect(@RequestBody EquipmentMgms1 equipmentMgms1) {
        // 生成停机记录编号流水号
        String dateTime = new SimpleDateFormat("yyyyMMdd").format(new Date());
        // 当天生成的第一个编号为 yyyyMMdd0001
        String initOrderNo = dateTime + "0001";
        //查询当前编号最大的数据
        EquipmentMgms1 mgms1 = new EquipmentMgms1();
        QueryWrapper<EquipmentMgms1> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("mgms1_no");
        wrapper.last("limit 1");
        List<EquipmentMgms1> list = equipmentMgms1Service.list(wrapper);
        if (list.size() > 0){
            mgms1 = list.get(0);
        }
        if (StringUtils.isNotNull(mgms1) && StringUtils.isNotNull(mgms1.getMgms1No())) {
            // 获取这个编号的年月日信息
            String dateTimeInfo = mgms1.getMgms1No().substring(0,8);
            // 判断这个编号是否是当天生成的
            if (dateTime.equals(dateTimeInfo)){
                // 如果是当天生成的
                String orderNo = mgms1.getMgms1No().substring(8);
                initOrderNo = dateTime + String.format("%04d",Integer.parseInt(orderNo) + 1);
            }
        }
        equipmentMgms1.setMgms1No(initOrderNo);
        boolean save = equipmentMgms1Service.save(equipmentMgms1);
        if (save) {
            return AjaxResult.success("新增成功");
        } else {
            return AjaxResult.error("新增失败");
        }
    }
    /**
     * 修改立案记录
     */
    @Operation(summary = "修改立案记录")
    @PutMapping("/update")
    @Log(title = "停机立案修改",businessType = BusinessType.UPDATE)
    public Object updateDefect(@RequestBody EquipmentMgms1 equipmentMgms1) {
        boolean save = equipmentMgms1Service.updateById(equipmentMgms1);
        if (save) {
            return AjaxResult.success("修改成功");
        } else {
            return AjaxResult.error("修改失败");
        }
    }
    /**
     * 查询立案
     */
    @Operation(summary = "查询立案记录")
    @GetMapping("/query")
    @Log(title = "停机立案查询",businessType = BusinessType.OTHER)
    public Object queryDefect(EquipmentMgms1QueryDTO paramsDTO) {
        Long current = paramsDTO.getPageNum();
        Long limit = paramsDTO.getPageSize();
        // 创建page对象
        Page<EquipmentMgms1> pageData = new Page<>(current, limit);
        // 构造条件
        QueryWrapper<EquipmentMgms1> wrapper = new QueryWrapper<>();
                // 停机记录编码
        wrapper.eq(StringUtils.isNotEmpty(paramsDTO.getMgms1No()),"mgms1_no",paramsDTO.getMgms1No())
                // 责任单位班别
                .eq(StringUtils.isNotEmpty(paramsDTO.getDeptclassMgms1No()),"deptclass_mgms1_no",paramsDTO.getDeptclassMgms1No())
                // 停机记录状态
                .eq(StringUtils.isNotEmpty(paramsDTO.getMgms1Stus()),"mgms1_stus",paramsDTO.getMgms1Stus())
                // 产线名称
                .eq(StringUtils.isNotEmpty(paramsDTO.getMgma1Name()),"mgma1_name",paramsDTO.getMgma1Name())
                // 停机主类型编号
                .eq(StringUtils.isNotEmpty(paramsDTO.getMgmc1Name()),"mgmc1_name",paramsDTO.getMgmc1Name());
        // 日期区间
        if (StringUtils.isNotNull(paramsDTO.getDatetimeRange())){
            if (paramsDTO.getDatetimeRange().size() > 0) {
                wrapper.between("begin_time",paramsDTO.getDatetimeRange().get(0),paramsDTO.getDatetimeRange().get(1));
            }
        }
        wrapper.orderByDesc("mgms1_no");
        equipmentMgms1Service.page(pageData,wrapper);
        // 总记录数
        long total = pageData.getTotal();
        // 数据list集合
        List<EquipmentMgms1> records = pageData.getRecords();
        Map<String,Object> dataMap = new HashMap<>(16);
        dataMap.put("total",total);
        dataMap.put("list",records);
        return AjaxResult.success(dataMap);
    }
    /**
     * 查询产线
     */
    @Operation(summary = "查询产线和停机主类型名称")
    @GetMapping("/init")
    @Log(title = "查询产线和停机主类型名称",businessType = BusinessType.OTHER)
    public Object queryLineName() {
            // 产线名称及产线编号
            List<EquipmentMgma1> mgma1List = equipmentMgma1Service.list();
            // 根据产线名称和产线编号组成select下拉格式返回
            List<Map<String, Object>> lineList = mgma1List.stream().map(item -> {
                Map<String, Object> map = new HashMap<>(16);
                map.put("label", item.getMgma1name());
                map.put("value", item.getMgma1no());
                return map;
            }).collect(Collectors.toList());
            // 查询停机主类型
            List<EquipmentDownTypeTree> mainTypeList = equipmentDownTypeTreeService.list(new QueryWrapper<EquipmentDownTypeTree>().eq("equipment_down_type_level", 2));
            // 根据主类型名称和主类型编号组成select下拉格式返回
            List<Map<String, Object>> mainList = mainTypeList.stream().map(item -> {
                Map<String, Object> map = new HashMap<>(16);
                map.put("label", item.getEquipmentDownTypeName());
                map.put("value", item.getEquipmentDownTypeCode());
                return map;
            }).collect(Collectors.toList());
            //将结果集返回
            Map<String, Object> resultMap = new HashMap<>(16);
            resultMap.put("lineList",lineList);
            resultMap.put("mainList",mainList);
            return AjaxResult.success(resultMap);
    }
    /**
     * 查询编辑手动立案
     */
    @Operation(summary = "查询编辑手动立案")
    @GetMapping("/queryEditForm/{id}")
    @Log(title = "查询编辑手动立案",businessType = BusinessType.UPDATE)
    public Object queryEditForm(@PathVariable String id) {
        // 根据id查询数据
        EquipmentMgms1 mgms1 = equipmentMgms1Service.getById(id);
        return AjaxResult.success(mgms1);
    }
    /**
     * 根据停机主类型查询 细类型
     */
    @Operation(summary = "查询编辑手动立案")
    @GetMapping("/getChildrenMgmc1Name/{optionValue}")
    @Log(title = "查询编辑手动立案",businessType = BusinessType.OTHER)
    public Object getChildrenMgmc1Name(@PathVariable String optionValue) {
        // 根据typeCode获取主类型id
        EquipmentDownTypeTree equipmentDownTypeCode = equipmentDownTypeTreeService.getOne(new QueryWrapper<EquipmentDownTypeTree>().eq(StringUtils.isNotEmpty(optionValue), "equipment_down_type_code", optionValue));
        // 根据主类型ID 查询细类型
        List<EquipmentDownTypeTree> list = equipmentDownTypeTreeService.list(new QueryWrapper<EquipmentDownTypeTree>().eq("equipment_down_type_parent_code", equipmentDownTypeCode.getId()));
        List<Map<String, Object>> returnList = list.stream().map(item -> {
            Map<String, Object> map = new HashMap<>(16);
            map.put("label", item.getEquipmentDownTypeName());
            map.put("value", item.getEquipmentDownTypeCode());
            return map;
        }).collect(Collectors.toList());
        return AjaxResult.success(returnList);
    }
    /**
     * 根据停机主类型查询 细类型
     */
    @Operation(summary = "查询编辑手动立案")
    @GetMapping("/getChildrenReasonNo/{optionValue}")
    @Log(title = "查询编辑手动立案",businessType = BusinessType.OTHER)
    public Object getChildrenReasonNo(@PathVariable String optionValue) {
        // 根据typeCode查询停机原因群码
        List<EquipmentMgmc2> mgmc2noList = equipmentMgmc2Service.list(new QueryWrapper<EquipmentMgmc2>().eq(StringUtils.isNotEmpty(optionValue), "mgmc1no", optionValue));
        List<Map<String, Object>> returnList = mgmc2noList.stream().map(item -> {
            Map<String, Object> map = new HashMap<>(16);
            map.put("label", item.getMgmc2name());
            map.put("value", item.getMgmc2no());
            map.put("groupCode", item.getGroupcode());
            return map;
        }).collect(Collectors.toList());
        return AjaxResult.success(returnList);
    }
    /**
     * 更新重启立案资料
     */
    @Operation(summary = "更新重启立案资料")
    @PutMapping("/updateEditForm")
    @Log(title = "更新重启立案资料",businessType = BusinessType.UPDATE)
    public Object updateEditForm(@RequestBody EquipmentMgms1 equipmentMgms1) throws ParseException {
        long beginTime = DateUtils.parseDate(equipmentMgms1.getBeginTime(), "yyyy-MM-dd HH:mm:ss").getTime();
        long modifyDateTime = DateUtils.parseDate(equipmentMgms1.getModifyDate(), "yyyy-MM-dd HH:mm:ss").getTime();
        if (modifyDateTime - beginTime < 0) {
            return AjaxResult.error("恢复运行时间必须大于停机发生时间！");
        }
        // 更新重启立案资料
        boolean update = equipmentMgms1Service.updateById(equipmentMgms1);
        return AjaxResult.success(update);
    }
    /**
     * 查询产线编码是否使用
     */
    @Operation(summary = "查询产线编码是否使用")
    @GetMapping("/getLineEditForm")
    @Log(title = "查询产线编码是否使用",businessType = BusinessType.OTHER)
    public Object getLineEditForm(@RequestParam(defaultValue = "") String mgma1no) {
        QueryWrapper<EquipmentMgms1> wrapper = new QueryWrapper<>();
        wrapper.eq("mgma1_no",mgma1no);
        List<EquipmentMgms1> list = equipmentMgms1Service.list(wrapper);
        if (list.size()>0){
            return AjaxResult.success("成功",1);
        }
        return AjaxResult.success("成功",2);
    }

}
