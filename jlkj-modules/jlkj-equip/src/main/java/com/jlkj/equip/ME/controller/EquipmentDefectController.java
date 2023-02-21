package com.jlkj.equip.ME.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.equip.ME.domain.EquipmentDefect;
import com.jlkj.equip.ME.service.IEquipmentDefectService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 设备缺陷统计 前端控制器
 * </p>
 *
 * @author xin
 * @since 2022-05-13
 */
@RestController
@RequestMapping("/equipment/defect")
public class EquipmentDefectController {
    private final IEquipmentDefectService iEquipmentDefectService;

    public EquipmentDefectController(IEquipmentDefectService iEquipmentDefectService) {
        this.iEquipmentDefectService = iEquipmentDefectService;
    }

    /**
     * 新增缺陷记录
     */
    @Operation(summary = "新增缺陷记录")
    @PostMapping("/insert")
    @Log(title = "新增缺陷记录", businessType = BusinessType.INSERT)
    public Object addDefect(@RequestBody EquipmentDefect equipmentDefect) {
        try {
            QueryWrapper<EquipmentDefect> wrapper = new QueryWrapper<>();
            wrapper.eq(StringUtils.isNotNull(equipmentDefect.getDefectTitle()),"defect_title",equipmentDefect.getDefectTitle())
                    .eq(StringUtils.isNotNull(equipmentDefect.getDefectSpecialtyNo()),"defect_specialty_no",equipmentDefect.getDefectSpecialtyNo());
            List<EquipmentDefect> list = iEquipmentDefectService.list(wrapper);
            if (list.size() > 0) {
                return AjaxResult.error("缺陷项目与专业已存在，请重新输入！");
            }
            boolean save = iEquipmentDefectService.save(equipmentDefect);
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
    @Operation(summary = "删除缺陷记录")
    @DeleteMapping("/delete")
    @Log(title = "删除缺陷记录", businessType = BusinessType.DELETE)
    public Object deleteDefect(@RequestParam List<String> ids) {
        try {
            boolean update = iEquipmentDefectService.removeBatchByIds(ids);
            if (update) {
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
    @Operation(summary = "查询缺陷记录")
    @GetMapping("/query")
    @Log(title = "查询缺陷记录", businessType = BusinessType.OTHER)
    public Object queryDefect(@RequestParam(defaultValue ="1") Integer pageNum,
                            @RequestParam(defaultValue = "10") Integer pageSize,
                            @RequestParam(defaultValue = "") String startDate,
                            @RequestParam(defaultValue = "") String endDate,
                            @RequestParam(defaultValue = "") String defectTitle,
                            @RequestParam (defaultValue = "") String processConditionNo
    ) {
        try {
            QueryWrapper<EquipmentDefect> wrapper = new QueryWrapper<>();
            wrapper.eq(StringUtils.isNotEmpty(defectTitle),"defect_title",defectTitle)
                    .eq(StringUtils.isNotEmpty(processConditionNo),"process_condition_no",processConditionNo);
            if (StringUtils.isNotEmpty(startDate) && StringUtils.isNotEmpty(endDate)) {
                wrapper.between("defect_update_date",startDate,endDate);
            }
            wrapper.orderByDesc("defect_update_date");
            Page<EquipmentDefect> equipmentDefectPage = iEquipmentDefectService.page(new Page<>(pageNum, pageSize), wrapper);
            return AjaxResult.success("成功", equipmentDefectPage);
        } catch (Exception e) {
            return AjaxResult.error();
        }
    }
    /**
     * 更新缺陷记录
     */
    @Operation(summary = "更新缺陷记录")
    @PostMapping("/updata")
    @Log(title = "更新缺陷记录", businessType = BusinessType.UPDATE)
    public Object updateDefect(@RequestBody EquipmentDefect equipmentDefect) {
        try {
            QueryWrapper<EquipmentDefect> wrapper = new QueryWrapper<>();
            wrapper.ne(StringUtils.isNotNull(equipmentDefect.getId()),"id",equipmentDefect.getId());
            wrapper.and(wrapper1 -> wrapper1.eq(StringUtils.isNotNull(equipmentDefect.getDefectTitle()),"defect_title",equipmentDefect.getDefectTitle())
                    .eq(StringUtils.isNotNull(equipmentDefect.getDefectSpecialtyNo()),"defect_specialty_no",equipmentDefect.getDefectSpecialtyNo()));
            List<EquipmentDefect> list = iEquipmentDefectService.list(wrapper);
            if (list.size() > 0) {
                return AjaxResult.error("缺陷项目与专业已存在，请重新输入！");
            }
            boolean update = iEquipmentDefectService.updateById(equipmentDefect);
            if (update) {
                return AjaxResult.success("更新成功");
            } else {
                return AjaxResult.error("更新失败");
            }
        } catch (Exception e) {
            return AjaxResult.error();
        }
    }
    /**
     * 根据ID查询数据
     */
    @Operation(summary = "根据ID查询数据")
    @GetMapping("/queryInfoById")
    @Log(title = "根据ID查询数据", businessType = BusinessType.OTHER)
    public Object queryInfoById(@RequestParam(defaultValue = "") String id) {
        EquipmentDefect byId = iEquipmentDefectService.getById(id);
        return AjaxResult.success(byId);
    }

}
