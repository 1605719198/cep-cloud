package com.jlkj.quality.ts.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.dto.dto.qualityprovider.TsTestElementCodeDTO;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.quality.ts.domain.TestElementCode;
import com.jlkj.quality.ts.service.TestElementCodeService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 智能研发室 黄兵
 * @Description 试验元素代号维护controller
 * @date 2022/5/11 - 10:43
 */
@RestController
@RequestMapping("/quality/chemicalTest/elementId")
public class TestElementCodeController {

    @Autowired
    TestElementCodeService testElementCodeService;

    /**
     * 搜索试验元素代号
     */
    @Operation(summary = "搜索试验元素代号")
    @PostMapping("/queryList")
    @Log(title = "试验元素代号查询", businessType = BusinessType.OTHER)
    public Object queryListTsTestElementCode(@RequestBody TsTestElementCodeDTO tsTestElementCodeDTO) {
        try {
            String elementIndex = tsTestElementCodeDTO.getElementIndex();
            String elementName = tsTestElementCodeDTO.getElementName();
            Long pageNum = tsTestElementCodeDTO.getPageNum();
            Long pageSize = tsTestElementCodeDTO.getPageSize();
            LambdaQueryWrapper<TestElementCode> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.likeRight(StringUtils.isNotBlank(elementIndex), TestElementCode::getElementIndex, elementIndex)
                        .like(StringUtils.isNotBlank(elementName), TestElementCode::getElementName, elementName)
                        .orderByAsc(TestElementCode::getElementIndex);
            Page<TestElementCode> page = testElementCodeService.page(new Page<>(pageNum, pageSize), queryWrapper);
            //总记录数
            long total = page.getTotal();
            //数据list集合
            List<TestElementCode> records = page.getRecords();
            Map<String,Object> dataMap = new HashMap<>(16);
            dataMap.put("total",total);
            dataMap.put("list",records);
            if (records.isEmpty()){
                return AjaxResult.success("查无资料", dataMap);
            } else {
                return AjaxResult.success("搜索成功,为您找到" + total + "笔资料", dataMap);
            }
        } catch (Exception e) {
            return AjaxResult.error();
        }
    }

    /**
     * 添加试验元素代号
     */
    @Operation(summary = "添加试验元素代号")
    @PostMapping("/add")
    @Log(title = "试验元素代号添加", businessType = BusinessType.INSERT)
    public Object addTsTestElementCode(@RequestBody TsTestElementCodeDTO tsTestElementCodeDTO, @RequestHeader("token") String token) {
        try {
            String elementIndex = tsTestElementCodeDTO.getElementIndex();
            String elementName = tsTestElementCodeDTO.getElementName();
            TestElementCode testElementCode = new TestElementCode();
            BeanUtils.copyProperties(tsTestElementCodeDTO, testElementCode);
            LambdaQueryWrapper<TestElementCode> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(TestElementCode::getElementIndex, elementIndex)
                        .eq(TestElementCode::getElementName, elementName);
            List<TestElementCode> list = testElementCodeService.list(queryWrapper);
            if (!list.isEmpty()) {
                return AjaxResult.error("试验元素代号不允许重复！");
            }
            boolean result = testElementCodeService.save(testElementCode);
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
     * 编辑试验元素代号
     */
    @Operation(summary = "编辑试验元素代号")
    @PostMapping("/update")
    @Log(title = "试验元素代号编辑", businessType = BusinessType.UPDATE)
    public Object updateTsTestElementCode(@RequestBody TsTestElementCodeDTO tsTestElementCodeDTO, @RequestHeader("token") String token) {
        try {
            String elementIndex = tsTestElementCodeDTO.getElementIndex();
            TestElementCode testElementCode = new TestElementCode();
            BeanUtils.copyProperties(tsTestElementCodeDTO, testElementCode);
            LambdaUpdateWrapper<TestElementCode> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(StringUtils.isNotBlank(elementIndex), TestElementCode::getElementIndex, elementIndex);
            boolean result = testElementCodeService.saveOrUpdate(testElementCode, updateWrapper);
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
     * 删除试验元素代号
     */
    @Operation(summary = "删除试验元素代号")
    @DeleteMapping("/delete")
    @Log(title = "试验元素代号删除", businessType = BusinessType.DELETE)
    public Object deleteTsTestElementCode(@RequestParam String elementIndex, @RequestHeader("token") String token) {
        try {
            LambdaQueryWrapper<TestElementCode> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(TestElementCode::getElementIndex, elementIndex);
            boolean result = testElementCodeService.remove(queryWrapper);
            if (result) {
                return AjaxResult.success("删除成功");
            } else {
                return AjaxResult.error("删除失败");
            }
        } catch (Exception e) {
            return AjaxResult.error();
        }
    }

    /**
     * 根据试验元素代号查询
     */
    @Operation(summary = "根据试验元素代号查询")
    @GetMapping("/query")
    @Log(title = "试验元素代号查询", businessType = BusinessType.OTHER)
    public Object queryTsTestElementCode(@RequestParam String elementIndex) {
        try {
            List<TestElementCode> list = testElementCodeService.query().eq("element_index", elementIndex).list();
            if (list.isEmpty()) {
                return AjaxResult.success("查无资料");
            } else {
                return AjaxResult.success("查询成功", list);
            }
        } catch (Exception e) {
            return AjaxResult.error();
        }
    }
}
