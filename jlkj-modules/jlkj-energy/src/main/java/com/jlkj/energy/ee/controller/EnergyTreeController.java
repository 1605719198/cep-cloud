package com.jlkj.energy.ee.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

 
import com.jlkj.common.dto.dto.energyprovider.EnergyTreeDTO;
import com.jlkj.common.dto.resp.Result;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.energy.ee.domain.EnergyTree;
import com.jlkj.energy.ee.service.EnergyTreeService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author 智能研发室 黄兵
 * @Description 能介资料controller
 * @date 2022/5/6 - 10:27
 */
@RestController
@RequestMapping("/energy/mediated/data")
public class EnergyTreeController {

    @Autowired
    EnergyTreeService energyTreeService;

    /**
     * 新增能介资料
     */
    @Log(title = "新增能介资料", businessType = BusinessType.INSERT)
    @Operation(summary = "新增能介资料")
    @PostMapping("/ea03/xctl")
    public Object addEnergyTree(@RequestBody EnergyTreeDTO energyTreeDTO) {
        try {
            EnergyTree energyTree = new EnergyTree();
            BeanUtils.copyProperties(energyTreeDTO, energyTree);
            energyTree.setId(energyTreeDTO.getItemId());
            energyTree.setEngyId(energyTree.getColumn4());
            boolean result = energyTreeService.save(energyTree);
            if(result){
                return Result.success("新增成功");
            }else {
                return Result.failedTwo("新增失败");
            }
        } catch (Exception e) {
            return Result.failed();
        }
    }

    /**
     * 修改能介资料
     */
    @Log(title = "修改能介资料", businessType = BusinessType.UPDATE)
    @Operation(summary = "修改能介资料")
    @PostMapping("/update")
    public Object updateEnergyTree(@RequestBody EnergyTreeDTO energyTreeDTO) {
        try {
            EnergyTree energyTree = new EnergyTree();
            BeanUtils.copyProperties(energyTreeDTO, energyTree);
            energyTree.setId(energyTreeDTO.getItemId());
            energyTree.setEngyId(energyTree.getColumn4());
            boolean result = energyTreeService.updateById(energyTree);
            if (result){
                return Result.success("修改成功");
            } else {
                return Result.failedTwo("修改失败，请重新提交");
            }
        } catch (Exception e) {
            return Result.failed();
        }
    }

    /**
     * 删除能介资料
     */
    @Log(title = "删除能介资料", businessType = BusinessType.DELETE)
    @Operation(summary = "删除能介资料")
    @DeleteMapping("/delete")
    public Object deleteEnergyTree(@RequestParam List<String> id) {
        try {
            boolean result = energyTreeService.removeBatchByIds(id);
            if (result) {
                return Result.success("删除成功");
            } else {
                return Result.failedTwo("删除失败，请重新提交");
            }
        } catch (Exception e) {
            return Result.failed();
        }
    }

    /**
     * 能介资料 - 下拉列表
     */
    @Log(title = "下拉列表-能介资料", businessType = BusinessType.OTHER)
    @Operation(summary = "下拉列表-能介资料")
    @GetMapping("/select")
    public Object selectEnergyTree() {
        List<Map<String, Object>> list = energyTreeService.listMaps(new LambdaQueryWrapper<EnergyTree>()
                .eq(EnergyTree::getDelFlag, 0).orderByAsc(EnergyTree::getPid).orderByAsc(EnergyTree::getSerialNo));
        return list;
    }

}
