package com.jlkj.human.hm.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.human.hm.domain.Baseinfo;
import com.jlkj.human.hm.dto.BaseInfoDTO;
import com.jlkj.human.hm.service.impl.BaseinfoServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * @author HuangBing
 * @Description
 * @create 2023-03-07 18:42
 */
@RestController
@RequestMapping("/baseInfo")
public class BaseInfoController extends BaseController {

    @Autowired
    BaseinfoServiceImpl baseinfoService;

    /**
     * 获取选单配置树
     */
    @Log(title = "选单配置树",businessType = BusinessType.OTHER)
    @Operation(summary = "选单配置树")
    @GetMapping("/tree")
    public Object getDepartmentTree() {
        List<Baseinfo> list = baseinfoService.getBaseInfoTreeList();
        return AjaxResult.success(list);
    }

    /**
     * 获取子节点查询列表
     */
    @Log(title = "获取子节点查询列表",businessType = BusinessType.OTHER)
    @Operation(summary = "获取子节点查询列表")
    @GetMapping("/list")
    public Object getChildrenList(BaseInfoDTO baseInfoDTO) {
        try {
            startPage();
            String uuid = baseInfoDTO.getUuid();
            LambdaQueryWrapper<Baseinfo> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(StringUtils.isNotBlank(uuid), Baseinfo::getParentId, uuid)
                        .orderByAsc(Baseinfo::getDicNo);
            List<Baseinfo> list = baseinfoService.list(queryWrapper);
            if (list.isEmpty()) {
                return AjaxResult.error("查无资料");
            } else {
                return AjaxResult.success("查询成功！", getDataTable(list));
            }
        } catch (Exception e) {
            return AjaxResult.error();
        }
    }

    /**
     * 添加选单数据
     */
    @Operation(summary = "添加选单数据")
    @PostMapping("/add")
    @Log(title = "添加选单数据", businessType = BusinessType.INSERT)
    public Object addBaseInfo(@RequestBody Baseinfo baseinfo) {
        try {
            String dicNo = baseinfo.getDicNo();
            String dicName = baseinfo.getDicName();
            baseinfo.setUpdateEmp(SecurityUtils.getNickName());
            LambdaQueryWrapper<Baseinfo> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Baseinfo::getDicNo, dicNo)
                    .eq(Baseinfo::getDicName, dicName);
            List<Baseinfo> list = baseinfoService.list(queryWrapper);
            if (!list.isEmpty()) {
                return AjaxResult.error("数据已存在，不允许重复！！！");
            }
            boolean result = baseinfoService.save(baseinfo);
            if (result) {
                return AjaxResult.success("保存成功");
            } else {
                return AjaxResult.error("保存失败");
            }
        } catch (Exception e) {
            return AjaxResult.error();
        }
    }

    /**
     * 修改选单数据
     */
    @Operation(summary = "修改选单数据")
    @PostMapping("/update")
    @Log(title = "选单数据编辑", businessType = BusinessType.UPDATE)
    public Object updateBaseInfo(@RequestBody Baseinfo baseinfo) {
        try {
            String uuid = baseinfo.getUuid();
            LambdaUpdateWrapper<Baseinfo> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(StringUtils.isNotBlank(uuid), Baseinfo::getUuid, uuid);
            boolean result = baseinfoService.update(baseinfo, updateWrapper);
            if (result) {
                return AjaxResult.success("保存成功");
            } else {
                return AjaxResult.error("保存失败");
            }
        } catch (Exception e) {
            return AjaxResult.error();
        }
    }

    /**
     * 删除选单数据
     */
    @Operation(summary = "删除选单数据")
    @DeleteMapping("/delete")
    @Log(title = "删除选单数据", businessType = BusinessType.DELETE)
    public Object deleteBaseInfo(@RequestParam String uuid) {
        try {
            LambdaQueryWrapper<Baseinfo> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Baseinfo::getParentId, uuid);
            List<Baseinfo> list = baseinfoService.list(wrapper);
            if (!list.isEmpty()) {
                return AjaxResult.error("存在子类选单资料，不可删除资料");
            }
            LambdaQueryWrapper<Baseinfo> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Baseinfo::getUuid, uuid);
            boolean delete = baseinfoService.remove(queryWrapper);
            return AjaxResult.success(delete);
        } catch (Exception e) {
            return AjaxResult.error();
        }
    }

    /**
     * 获取专业下拉选单列表
     */
    @Log(title = "获取专业下拉选单列表",businessType = BusinessType.OTHER)
    @Operation(summary = "获取专业下拉选单列表")
    @GetMapping("/getDegreeMajor")
    public Object getDegreeMajor(BaseInfoDTO humanresourceBaseInfoDTO) {
        try {
            List<String> baseInfoList = humanresourceBaseInfoDTO.getBaseInfoList();
            HashMap<String, List<Baseinfo>> map = new HashMap<>(16);
            for (String item : baseInfoList) {
                LambdaQueryWrapper<Baseinfo> queryWrapper = new LambdaQueryWrapper<>();
                LambdaQueryWrapper<Baseinfo> queryWrapperA = new LambdaQueryWrapper<>();
                queryWrapper.eq(Baseinfo::getDicNo, item);
                Baseinfo baseInfo = baseinfoService.getOne(queryWrapper);
                queryWrapperA.eq(Baseinfo::getParentId, baseInfo.getUuid());
                List<Baseinfo> list = baseinfoService.list(queryWrapperA);
                map.put(item, list);
            }
            return AjaxResult.success("查询成功！", map);
        } catch (Exception e) {
            return AjaxResult.error();
        }
    }

    /**
     * 获取专业细分下拉选单列表
     */
    @Log(title = "获取专业细分下拉选单列表",businessType = BusinessType.OTHER)
    @Operation(summary = "获取专业细分下拉选单列表")
    @GetMapping("/getDegreeMajorSpecialization")
    public Object getDegreeMajorSpecialization(BaseInfoDTO baseInfoDTO) {
        try {
            String uuid = baseInfoDTO.getUuid();
            LambdaQueryWrapper<Baseinfo> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Baseinfo::getParentId, uuid);
            List<Baseinfo> list = baseinfoService.list(queryWrapper);
            return AjaxResult.success("查询成功！", list);
        } catch (Exception e) {
            return AjaxResult.error();
        }
    }
}
