package com.jlkj.equip.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.equip.dto.EquipmentMgmc2AddDTO;
import com.jlkj.equip.dto.EquipmentMgmc2QueryDTO;
import com.jlkj.equip.entity.EquipmentMgmc2;
import com.jlkj.equip.service.IEquipmentMgmc2Service;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 停机原因数据  前端控制器
 * </p>
 *
 * @author xin
 * @since 2022-05-09
 */
@RestController
@RequestMapping("/equipment/mgmc2")
public class EquipmentMgmc2Controller {

    private final IEquipmentMgmc2Service equipmentMgmc2Service;

    public EquipmentMgmc2Controller(IEquipmentMgmc2Service equipmentMgmc2Service) {
        this.equipmentMgmc2Service = equipmentMgmc2Service;
    }

    /**
     * 新增停机原因数据
     *
     */
    @Operation(summary = "新增停机原因数据" )
    @PostMapping("/addMgmc2Data")
    public Object addMgmc2Data(@RequestBody EquipmentMgmc2AddDTO equipmentMgmc2AddDTO){
        QueryWrapper<EquipmentMgmc2> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotNull(equipmentMgmc2AddDTO.getMgmc2no()),"mgmc2no",equipmentMgmc2AddDTO.getMgmc2no())
                .eq(StringUtils.isNotNull(equipmentMgmc2AddDTO.getMgmc1no()),"mgmc1no",equipmentMgmc2AddDTO.getMgmc1no())
                .eq(StringUtils.isNotNull(equipmentMgmc2AddDTO.getGroupcode()),"groupcode",equipmentMgmc2AddDTO.getGroupcode());
        List<EquipmentMgmc2> list = equipmentMgmc2Service.list(wrapper);
        if (list.size() > 0) {
            return AjaxResult.error("停机原因群码和停机原因编码已存在，请重新输入！");
        }
        EquipmentMgmc2 equipmentMgmc2 = new EquipmentMgmc2();
        BeanUtils.copyProperties(equipmentMgmc2AddDTO,equipmentMgmc2);
        boolean save = equipmentMgmc2Service.save(equipmentMgmc2);
        return AjaxResult.success(save);
    }
    /**
     * 修改停机原因数据
     *
     */
    @Operation(summary = "修改停机原因数据" )
    @PutMapping("/updateMgmc2Data")
    public Object updateMgmc2Data(@RequestBody EquipmentMgmc2AddDTO equipmentMgmc2AddDTO){
        QueryWrapper<EquipmentMgmc2> wrapper = new QueryWrapper<>();
        wrapper.ne(StringUtils.isNotNull(equipmentMgmc2AddDTO.getId()),"id",equipmentMgmc2AddDTO.getId());
        wrapper.and(wrapper1 -> wrapper1.eq(StringUtils.isNotNull(equipmentMgmc2AddDTO.getMgmc2no()),"mgmc2no",equipmentMgmc2AddDTO.getMgmc2no())
                .eq(StringUtils.isNotNull(equipmentMgmc2AddDTO.getMgmc1no()),"mgmc1no",equipmentMgmc2AddDTO.getMgmc1no())
                .eq(StringUtils.isNotNull(equipmentMgmc2AddDTO.getGroupcode()),"groupcode",equipmentMgmc2AddDTO.getGroupcode()));
        List<EquipmentMgmc2> list = equipmentMgmc2Service.list(wrapper);
        if (list.size() > 0) {
            return AjaxResult.error("停机原因群码和停机原因编号已存在，请重新输入！");
        }
        EquipmentMgmc2 equipmentMgmc2 = new EquipmentMgmc2();
        BeanUtils.copyProperties(equipmentMgmc2AddDTO,equipmentMgmc2);
        boolean update = equipmentMgmc2Service.updateById(equipmentMgmc2);
        return AjaxResult.success(update);
    }
    /**
     * 删除停机原因数据
     *
     */
    @Operation(summary = "删除停机原因数据" )
    @DeleteMapping("/deleteMgmc2Data/{ids}")
    public Object deleteMgmc2Data(@PathVariable String[] ids){
        List<String> idList = Arrays.asList(ids);
        boolean delete = equipmentMgmc2Service.removeBatchByIds(idList);
        return AjaxResult.success(delete);
    }
    /**
     * 分页查询、根据条件查询信息
     */
    @Operation(summary = "分页查询、根据条件查询字典信息，名称" )
    @GetMapping("/queryDataByParams")
    public Object queryDictByParams(EquipmentMgmc2QueryDTO equipmentMgmc2QueryDTO) {
        Long current = equipmentMgmc2QueryDTO.getPage();
        Long limit = equipmentMgmc2QueryDTO.getLimit();
        //创建page对象
        Page<EquipmentMgmc2> pageData = new Page<>(current, limit);
        //构造条件
        QueryWrapper<EquipmentMgmc2> wrapper = new QueryWrapper<>();
        //条件组合查询
        wrapper.eq(StringUtils.isNotEmpty(equipmentMgmc2QueryDTO.getMgmc1no()),"mgmc1no",equipmentMgmc2QueryDTO.getMgmc1no())
                .eq(StringUtils.isNotEmpty(equipmentMgmc2QueryDTO.getGroupcode()),"groupcode",equipmentMgmc2QueryDTO.getGroupcode());
        wrapper.orderByDesc("create_time");
        //实现分页查询
        equipmentMgmc2Service.page(pageData,wrapper);
        //总记录数
        long total = pageData.getTotal();
        //数据list集合
        List<EquipmentMgmc2> records = pageData.getRecords();
        Map<String,Object> dataMap = new HashMap<>(16);
        dataMap.put("total",total);
        dataMap.put("list",records);
        return AjaxResult.success(dataMap);
    }
    /**
     * 根据ID查询单笔数据
     *
     */
    @Operation(summary = "根据ID查询单笔数据" )
    @GetMapping("/getByDataId/{id}")
    public Object getByDataId(@PathVariable String id){
        EquipmentMgmc2 equipmentMgmc2 = equipmentMgmc2Service.getById(id);
        return AjaxResult.success(equipmentMgmc2);
    }
}
