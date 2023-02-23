package com.jlkj.equip.mg.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.equip.mg.domain.EquipmentMgma1;
import com.jlkj.equip.mg.domain.EquipmentMgmc2;
import com.jlkj.equip.mg.service.IEquipmentMgma1Service;
import com.jlkj.equip.mg.service.IEquipmentMgmc2Service;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 产线基本数据  前端控制器
 * </p>
 *
 * @author xin
 * @since 2022-05-09
 */
@RestController
@RequestMapping("/mgma1/baseLine")
@Slf4j
public class EquipmentMgma1Controller {
    private final IEquipmentMgma1Service iEquipmentMgma1Service;
    private final IEquipmentMgmc2Service iEquipmentMgmc2Service;

    public EquipmentMgma1Controller(IEquipmentMgma1Service iEquipmentMgma1Service, IEquipmentMgmc2Service iEquipmentMgmc2Service) {
        this.iEquipmentMgma1Service = iEquipmentMgma1Service;
        this.iEquipmentMgmc2Service = iEquipmentMgmc2Service;
    }

    /**
     * 新增产线
     */
    @Operation(summary = "新增产线", description = "新增产线")
    @PostMapping("/insert")
    @Log(title = "产线新增",businessType = BusinessType.INSERT)
    public Object addLine(@RequestBody EquipmentMgma1 equipmentMgma1) {
        try {
            LambdaQueryWrapper<EquipmentMgma1> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(EquipmentMgma1::getMgma1no, equipmentMgma1.getMgma1no()).or().eq(EquipmentMgma1::getMgma1name, equipmentMgma1.getMgma1name());
            List<EquipmentMgma1> list = iEquipmentMgma1Service.list(lambdaQueryWrapper);
            if (list.size() == 0) {
                boolean save = iEquipmentMgma1Service.save(equipmentMgma1);
                if (save) {
                    return AjaxResult.success("新增成功");
                } else {
                    return AjaxResult.error("新增失败");
                }

            } else {
                return AjaxResult.error("产线名称或产线代码已存在");
            }

        } catch (Exception e) {
            return AjaxResult.error();
        }

    }

    /**
     * 更新产线
     */
    @Operation(summary = "更新产线", description = "更新产线")
    @PostMapping("/updata")
    @Log(title = "产线修改",businessType = BusinessType.UPDATE)
    public Object updataLine(@RequestBody EquipmentMgma1 equipmentMgma1) {
        try {
            LambdaQueryWrapper<EquipmentMgma1> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(EquipmentMgma1::getId, equipmentMgma1.getId());
            List<EquipmentMgma1> list = iEquipmentMgma1Service.list(lambdaQueryWrapper);
            boolean equals = list.get(0).getMgma1no().equals(equipmentMgma1.getMgma1no());
            boolean equals1 = list.get(0).getMgma1name().equals(equipmentMgma1.getMgma1name());
            if (equals && equals1) {
                boolean b = iEquipmentMgma1Service.updateById(equipmentMgma1);
                if (b) {
                    return AjaxResult.success("修改成功");

                } else {
                    return AjaxResult.error("修改失败");
                }

            } else if (equals1) {
                QueryWrapper<EquipmentMgma1> objectQueryWrapper = new QueryWrapper<>();
                objectQueryWrapper.eq("mgma1no",equipmentMgma1.getMgma1no());
                List<EquipmentMgma1> list2 = iEquipmentMgma1Service.list(objectQueryWrapper);
                if (list2.size() > 0) {
                    return AjaxResult.error("产线代码重复");
                } else {
                    boolean b = iEquipmentMgma1Service.updateById(equipmentMgma1);
                    if (b) {
                        return AjaxResult.success("修改成功");
                    } else {
                        return AjaxResult.error("修改失败");
                    }
                }
            } else if (equals) {
                QueryWrapper<EquipmentMgma1> objectQueryWrapper = new QueryWrapper<>();
                objectQueryWrapper.eq("mgma1name",equipmentMgma1.getMgma1name());
                List<EquipmentMgma1> list2 = iEquipmentMgma1Service.list(objectQueryWrapper);
                if (list2.size() > 0) {
                    return AjaxResult.error("产线名称重复");
                } else {
                    boolean b = iEquipmentMgma1Service.updateById(equipmentMgma1);
                    if (b) {
                        return AjaxResult.success("修改成功");
                    } else {
                        return AjaxResult.error("修改失败");
                    }
                }
            } else {
                lambdaQueryWrapper.eq(EquipmentMgma1::getMgma1no, equipmentMgma1.getMgma1no()).or().eq(EquipmentMgma1::getMgma1name, equipmentMgma1.getMgma1name());
                List<EquipmentMgma1> list2 = iEquipmentMgma1Service.list(lambdaQueryWrapper);
                if (list2.size() == 0) {
                    boolean b = iEquipmentMgma1Service.updateById(equipmentMgma1);
                    if (b) {
                        return AjaxResult.success("修改成功");
                    } else {
                        return AjaxResult.error("修改失败");
                    }
                } else {
                    return AjaxResult.error("产线名称或代码重复");
                }
            }
        } catch (Exception e) {
            return AjaxResult.error();
        }
    }


    /**
     * 删除产线
     */
    @Operation(summary = "删除产线", description = "删除产线")
    @DeleteMapping("/delete")
    @Log(title = "产线删除",businessType = BusinessType.DELETE)
    public Object deleteLine(@RequestParam List<String> ids) {
        try {
            boolean updata = iEquipmentMgma1Service.removeBatchByIds(ids);
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
     * 查询产线
     */
    @Operation(summary = "查询产线", description = "查询产线")
    @GetMapping("/query")
    @Log(title = "产线查询",businessType = BusinessType.OTHER)
    public Object queryLine(@RequestParam(defaultValue ="1") Integer pageNum,
                            @RequestParam (defaultValue = "20")Integer pageSize,
                            @RequestParam(defaultValue = "") String mgma1no,
                            @RequestParam(defaultValue = "") String mgma1name
    ) {
        try {
            Map<String, Object> map = new HashMap<>(16);
            map.put("mgma1no", mgma1no);
            Page<EquipmentMgma1> equipmentMgma1Page = iEquipmentMgma1Service.page(new Page<>(pageNum, pageSize), Wrappers.<EquipmentMgma1>query().allEq(map, false).like("mgma1name",mgma1name));
            return AjaxResult.success("成功", equipmentMgma1Page);
        } catch (Exception e) {
            return AjaxResult.error();
        }


    }

    /**
     * 查询名称
     */
    @Operation(summary = "查询名称", description = "查询名称")
    @GetMapping("/checkname")
    @Log(title = "产线名称查询",businessType = BusinessType.OTHER)
    public Object queryLinename(@RequestParam(defaultValue = "") String mgma1name,
                                @RequestParam(defaultValue = "") String id) {
        try {
            EquipmentMgma1 mgma1name1 = iEquipmentMgma1Service.query()
                    .eq("mgma1name", mgma1name)
                    .ne(StringUtils.isNotEmpty(id),"id",id)
                    .one();
            if (mgma1name1 == null) {
                return AjaxResult.success();
            } else {
                return AjaxResult.error("产线名称已存在");
            }
        } catch (Exception e) {
            return AjaxResult.error();

        }
    }


    /**
     * 查询产线编号
     */
    @Operation(summary = "查询产线编号", description = "查询产线编号")
    @GetMapping("/checkno")
    @Log(title = "产线编号查询",businessType = BusinessType.OTHER)
    public Object queryLine(@RequestParam(defaultValue = "") String mgma1no,
                            @RequestParam(defaultValue = "") String id) {
        System.out.println(mgma1no+":"+id);
        EquipmentMgma1 mgma1name1 = iEquipmentMgma1Service.query()
                .eq("mgma1no", mgma1no)
                .ne(StringUtils.isNotEmpty(id),"id",id)
                .one();
        if (mgma1name1 == null) {
            return AjaxResult.success();
        } else {
            return AjaxResult.error("产线编号已存在");
        }

    }

    /**
     * 查询
     */
    @Operation(summary = "查询停机群码", description = "查询停机群码")
    @GetMapping("/querygroupcod")
    @Log(title = "停机群码查询",businessType = BusinessType.OTHER)
    public Object querygropcode() {
        QueryWrapper<EquipmentMgmc2> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("distinct groupcode");
        List<EquipmentMgmc2> list = iEquipmentMgmc2Service.list(queryWrapper);
        return AjaxResult.success(list);

    }

}
