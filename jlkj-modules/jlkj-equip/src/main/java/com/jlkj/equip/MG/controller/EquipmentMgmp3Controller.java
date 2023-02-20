package com.jlkj.equip.MG.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.equip.MG.domain.EquipmentMgmp3;
import com.jlkj.equip.MG.dto.EquipmentMgmp3QueryDTO;
import com.jlkj.equip.MG.service.IEquipmentMgmp3Service;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 三级机运转讯息纪录 前端控制器
 * </p>
 *
 * @author xin
 * @since 2022-05-16
 */
@RestController
@RequestMapping("/equipment/mgmp3")
public class EquipmentMgmp3Controller {
    private final IEquipmentMgmp3Service equipmentMgmp3Service;

    public EquipmentMgmp3Controller(IEquipmentMgmp3Service equipmentMgmp3Service) {
        this.equipmentMgmp3Service = equipmentMgmp3Service;
    }

    /**
     * 分页查询、根据条件查询信息
     */
    @Operation(summary = "分页查询、根据条件查询字典信息，名称", description = "分页查询、根据条件查询字典信息，名称")
    @GetMapping("/queryDataByParams")
    @Log(title = "启停机记录分页查询",businessType = BusinessType.OTHER)
    public Object queryDictByParams(EquipmentMgmp3QueryDTO equipmentMgmp3QueryDTO) {
        Long current = equipmentMgmp3QueryDTO.getPage();
        Long limit = equipmentMgmp3QueryDTO.getLimit();
        //创建page对象
        Page<EquipmentMgmp3> pageData = new Page<>(current, limit);
        //构造条件
        QueryWrapper<EquipmentMgmp3> wrapper = new QueryWrapper<>();
        //条件组合查询
        wrapper.eq(StringUtils.isNotEmpty(equipmentMgmp3QueryDTO.getMgmp3No()),"mgmp3_no",equipmentMgmp3QueryDTO.getMgmp3No())
                .eq(StringUtils.isNotEmpty(equipmentMgmp3QueryDTO.getMgma1No()),"mgma1_no",equipmentMgmp3QueryDTO.getMgma1No())
                .eq(StringUtils.isNotEmpty(equipmentMgmp3QueryDTO.getMgmp3Stus()),"mgmp3_stus",equipmentMgmp3QueryDTO.getMgmp3Stus());
        if (StringUtils.isNotEmpty(equipmentMgmp3QueryDTO.getStartDate()) && StringUtils.isNotEmpty(equipmentMgmp3QueryDTO.getEndDate())) {
            wrapper.between("receive_date",equipmentMgmp3QueryDTO.getStartDate(),equipmentMgmp3QueryDTO.getEndDate());
        }
        wrapper.orderByDesc("receive_date");
        //实现分页查询
        equipmentMgmp3Service.page(pageData,wrapper);
        //总记录数
        long total = pageData.getTotal();
        //数据list集合
        List<EquipmentMgmp3> records = pageData.getRecords();
        Map<String,Object> dataMap = new HashMap<>(16);
        dataMap.put("total",total);
        dataMap.put("list",records);
        return AjaxResult.success(dataMap);
    }

}
