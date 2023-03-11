package com.jlkj.human.hm.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.human.hm.domain.HumanresourceBaseinfo;
import com.jlkj.human.hm.dto.HumanresourceBaseInfoDTO;
import com.jlkj.human.hm.service.impl.HumanresourceBaseinfoServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author HuangBing
 * @Description
 * @create 2023-03-07 18:42
 */
@RestController
@RequestMapping("/baseInfo")
public class HumanresourceBaseInfoController {

    @Autowired
    HumanresourceBaseinfoServiceImpl humanresourceBaseinfoService;

    /**
     * 获取选单配置树
     */
    @Log(title = "选单配置树",businessType = BusinessType.OTHER)
    @Operation(summary = "选单配置树")
    @GetMapping("/tree")
    public Object getDepartmentTree() {
        List<HumanresourceBaseinfo> list = humanresourceBaseinfoService.getBaseInfoTreeList();
        return AjaxResult.success(list);
    }

    /**
     * 获取子节点查询列表
     */
    @Log(title = "获取子节点查询列表",businessType = BusinessType.OTHER)
    @Operation(summary = "获取子节点查询列表")
    @GetMapping("/list")
    public Object getChildrenList(HumanresourceBaseInfoDTO humanresourceBaseInfoDTO) {
        try {
            String uuid = humanresourceBaseInfoDTO.getUuid();
            Long pageNum = humanresourceBaseInfoDTO.getPageNum();
            Long pageSize = humanresourceBaseInfoDTO.getPageSize();
            LambdaQueryWrapper<HumanresourceBaseinfo> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(StringUtils.isNotBlank(uuid), HumanresourceBaseinfo::getParentId, uuid);
            Page<HumanresourceBaseinfo> page = humanresourceBaseinfoService.page(new Page<>(pageNum, pageSize), queryWrapper);
            long total = page.getTotal();
            //数据list集合
            List<HumanresourceBaseinfo> records = page.getRecords();
            Map<String, Object> dataMap = new HashMap<>(16);
            dataMap.put("total", total);
            dataMap.put("list", records);
            if (page.getRecords().isEmpty()) {
                return AjaxResult.error("查无资料");
            } else {
                return AjaxResult.success("查询成功！", dataMap);
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
    public Object addBaseInfo(@RequestBody HumanresourceBaseinfo humanresourceBaseinfo) {
        try {
            String dicNo = humanresourceBaseinfo.getDicNo();
            String dicName = humanresourceBaseinfo.getDicName();
            String format = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            humanresourceBaseinfo.setUpdateDate(format);
            humanresourceBaseinfo.setUpdateEmp(SecurityUtils.getNickName());
            LambdaQueryWrapper<HumanresourceBaseinfo> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(HumanresourceBaseinfo::getDicNo, dicNo)
                    .eq(HumanresourceBaseinfo::getDicName, dicName);
            List<HumanresourceBaseinfo> list = humanresourceBaseinfoService.list(queryWrapper);
            if (!list.isEmpty()) {
                return AjaxResult.error("数据已存在，不允许重复！！！");
            }
            boolean result = humanresourceBaseinfoService.save(humanresourceBaseinfo);
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
    public Object updateBaseInfo(@RequestBody HumanresourceBaseinfo humanresourceBaseinfo) {
        try {
            String uuid = humanresourceBaseinfo.getUuid();
            LambdaUpdateWrapper<HumanresourceBaseinfo> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(StringUtils.isNotBlank(uuid), HumanresourceBaseinfo::getUuid, uuid);
            boolean result = humanresourceBaseinfoService.update(humanresourceBaseinfo, updateWrapper);
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
            LambdaQueryWrapper<HumanresourceBaseinfo> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(HumanresourceBaseinfo::getParentId, uuid);
            List<HumanresourceBaseinfo> list = humanresourceBaseinfoService.list(wrapper);
            if (!list.isEmpty()) {
                return AjaxResult.error("存在子类选单资料，不可删除资料");
            }
            LambdaQueryWrapper<HumanresourceBaseinfo> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(HumanresourceBaseinfo::getUuid, uuid);
            boolean delete = humanresourceBaseinfoService.remove(queryWrapper);
            return AjaxResult.success(delete);
        } catch (Exception e) {
            return AjaxResult.error();
        }
    }
}
