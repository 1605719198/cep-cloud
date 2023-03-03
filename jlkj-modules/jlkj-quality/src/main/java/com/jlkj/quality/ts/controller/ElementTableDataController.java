package com.jlkj.quality.ts.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.quality.ts.domain.ElementTableData;
import com.jlkj.quality.ts.domain.ElementTableDescription;
import com.jlkj.quality.ts.domain.TestElementCode;
import com.jlkj.quality.ts.dto.TsElementTableDataDTO;
import com.jlkj.quality.ts.service.ElementTableDataService;
import com.jlkj.quality.ts.service.ElementTableDescriptionService;
import com.jlkj.quality.ts.service.TestElementCodeService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author 智能研发室 黄兵
 * @Description 试验元素表格维护controller
 * @date 2022/5/17 - 11:51
 */
@RestController
@RequestMapping("/quality/chemicalTest/elementTable")
public class ElementTableDataController {

    @Autowired
    ElementTableDescriptionService elementTableDescriptionService;

    @Autowired
    ElementTableDataService elementTableDataService;

    @Autowired
    TestElementCodeService testElementCodeService;

    /**
     * 搜索试验元素表格
     */
    @Operation(summary = "搜索试验元素表格")
    @PostMapping("/queryList")
    @Log(title = "试验元素表格查询", businessType = BusinessType.OTHER)
    public Object queryListTsElementTableData(@RequestBody TsElementTableDataDTO tsElementTableDataDTO) {
        try {
            String formatId = tsElementTableDataDTO.getFormatId();
            Long pageNum = tsElementTableDataDTO.getPageNum();
            Long pageSize = tsElementTableDataDTO.getPageSize();
            Page<TsElementTableDataDTO> page = new Page<>(pageNum, pageSize);
            MPJLambdaWrapper<TsElementTableDataDTO> wrapper = new MPJLambdaWrapper<>();
            wrapper.select(ElementTableData::getId, ElementTableData::getFormatId, ElementTableData::getElementType, ElementTableData::getElementIndex, ElementTableData::getElementSeq, ElementTableData::getCreateTime, ElementTableData::getUpdateTime)
                   .select(TestElementCode::getElementName, TestElementCode::getElementUnit)
                   .select(ElementTableDescription::getCreateUser, ElementTableDescription::getUpdateUser)
                   .innerJoin(TestElementCode.class, TestElementCode::getElementIndex, ElementTableData::getElementIndex)
                   .leftJoin(ElementTableDescription.class, ElementTableDescription::getFormatId, ElementTableData::getFormatId)
                   .likeRight(StringUtils.isNotBlank(formatId), ElementTableData::getFormatId, formatId)
                   .orderByAsc(ElementTableData::getFormatId, ElementTableData::getElementSeq);
            IPage<TsElementTableDataDTO> iPage = elementTableDataService.selectJoinListPage(page, TsElementTableDataDTO.class, wrapper);
            //总记录数
            long total = iPage.getTotal();
            //数据list集合
            List<TsElementTableDataDTO> records = iPage.getRecords();
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
     * 添加试验元素表格
     */
    @Operation(summary = "添加试验元素表格")
    @PostMapping("/add")
    @Log(title = "试验元素表格添加", businessType = BusinessType.INSERT)
    public Object addTsElementTableData(@RequestBody TsElementTableDataDTO tsElementTableDataDTO) {
        try {
            String formatId = tsElementTableDataDTO.getFormatId();
            List<ElementTableData> elementTable = tsElementTableDataDTO.getElementTable();
            int elementSeq = 0;
            for (ElementTableData item : elementTable) {
                List<ElementTableData> list = elementTableDataService.query().eq("format_id", item.getFormatId()).eq("element_index", item.getElementIndex()).list();
                if(!list.isEmpty()){
                    return AjaxResult.error("您输入的" + item.getFormatId()+ "+" + item.getElementIndex() + "系统中已存在，请重新输入！");
                }
                item.setId(UUID.randomUUID().toString());
                item.setElementSeq(elementSeq += 10);
            }
            ElementTableDescription elementTableDescription = new ElementTableDescription();
            BeanUtils.copyProperties(tsElementTableDataDTO, elementTableDescription);
            LambdaQueryWrapper<ElementTableDescription> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ElementTableDescription::getFormatId, formatId);
            List<ElementTableDescription> list = elementTableDescriptionService.list(queryWrapper);
            if (!list.isEmpty()) {
                return AjaxResult.error("表格代号不允许重复！");
            }
            boolean result = elementTableDescriptionService.save(elementTableDescription);
            boolean result1 = elementTableDataService.saveBatch(elementTable);
            if (result && result1) {
                return AjaxResult.success("保存成功");
            } else {
                return AjaxResult.error("保存失败");
            }
        } catch (Exception e) {
            return AjaxResult.error();
        }
    }

    /**
     * 编辑试验元素表格
     */
    @Operation(summary = "编辑试验元素表格")
    @PostMapping("/update")
    @Log(title = "试验元素表格编辑", businessType = BusinessType.UPDATE)
    public Object updateTsElementTableData(@RequestBody TsElementTableDataDTO tsElementTableDataDTO) {
        try {
            String formatId = tsElementTableDataDTO.getFormatId();
            List<ElementTableData> elementTable = tsElementTableDataDTO.getElementTable();
            for (int i = 0; i < elementTable.size() - 1; i++){
                for (int j = i + 1; j < elementTable.size(); j++) {
                    if (elementTable.get(i).getFormatId().equals(elementTable.get(j).getFormatId()) && elementTable.get(i).getElementIndex().equals(elementTable.get(j).getElementIndex()) ) {
                        elementTable.remove(j);
                    }
                }
            }
            ElementTableDescription elementTableDescription = new ElementTableDescription();
            BeanUtils.copyProperties(tsElementTableDataDTO, elementTableDescription);
            LambdaUpdateWrapper<ElementTableDescription> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(ElementTableDescription::getFormatId, formatId);
            elementTableDescriptionService.update(elementTableDescription, updateWrapper);
            elementTableDataService.saveOrUpdateBatch(elementTable);
            return AjaxResult.success("保存成功");
        } catch (Exception e) {
            return AjaxResult.error();
        }
    }

    /**
     * 删除试验元素表格
     */
    @Operation(summary = "删除试验元素表格")
    @DeleteMapping("/delete")
    @Log(title = "试验元素表格删除", businessType = BusinessType.DELETE)
    public Object deleteTsElementTableData(@RequestParam String formatId, @RequestHeader("token") String token) {
        try {
            LambdaQueryWrapper<ElementTableDescription> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ElementTableDescription::getFormatId, formatId);
            LambdaQueryWrapper<ElementTableData> queryWrapperA = new LambdaQueryWrapper<>();
            queryWrapperA.eq(ElementTableData::getFormatId, formatId);
            boolean result = elementTableDescriptionService.remove(queryWrapper);
            elementTableDataService.remove(queryWrapperA);
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
     * 删除试验元素表格详细信息元素
     */
    @Operation(summary = "删除试验元素表格详细信息元素")
    @DeleteMapping("/deleteA")
    @Log(title = "试验元素表格详细信息删除", businessType = BusinessType.DELETE)
    public Object deleteTsElementTableDataA(@RequestParam String elementIndex, String formatId, @RequestHeader("token") String token) {
        try {
            LambdaQueryWrapper<ElementTableData> queryWrapperA = new LambdaQueryWrapper<>();
            queryWrapperA.eq(ElementTableData::getFormatId, formatId)
                         .eq(ElementTableData::getElementIndex, elementIndex);
            List<ElementTableData> list = elementTableDataService.list(queryWrapperA);
            if (list.isEmpty()) {
                return null;
            } else {
                boolean result = elementTableDataService.remove(queryWrapperA);
                if (result) {
                    return AjaxResult.success("删除成功");
                } else {
                    return AjaxResult.error("删除失败");
                }
            }
        } catch (Exception e) {
            return AjaxResult.error();
        }
    }

    /**
     * 根据表格代号查询
     */
    @Operation(summary = "根据表格代号查询")
    @PostMapping("/query")
    @Log(title = "表格代号查询", businessType = BusinessType.OTHER)
    public Object queryTsElementTableData(@RequestBody TsElementTableDataDTO tsElementTableDataDTO) {
        try {
            String formatId = tsElementTableDataDTO.getFormatId();
            Long pageNum = tsElementTableDataDTO.getPageNum();
            Long pageSize = tsElementTableDataDTO.getPageSize();
            Page<TsElementTableDataDTO> page = new Page<>(pageNum, pageSize);
            Page<ElementTableDescription> page1 = new Page<>(pageNum, pageSize);
            LambdaQueryWrapper<ElementTableDescription> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.likeRight(ElementTableDescription::getFormatId, formatId);
            Page<ElementTableDescription> page2 = elementTableDescriptionService.page(page1, queryWrapper);
            MPJLambdaWrapper<TsElementTableDataDTO> wrapper = new MPJLambdaWrapper<>();
            wrapper.select(ElementTableData::getElementIndex, ElementTableData::getElementSeq, ElementTableData::getId)
                   .select(TestElementCode::getElementName, TestElementCode::getElementUnit)
                   .select(ElementTableDescription::getCreateUser, ElementTableDescription::getCreateTime, ElementTableDescription::getUpdateUser, ElementTableDescription::getUpdateTime)
                   .eq(ElementTableData::getFormatId, formatId)
                   .orderByAsc(ElementTableData::getFormatId, ElementTableData::getElementSeq)
                   .leftJoin(ElementTableDescription.class, ElementTableDescription::getFormatId, ElementTableData::getFormatId)
                   .innerJoin(TestElementCode.class, TestElementCode::getElementIndex, ElementTableData::getElementIndex);
            IPage<TsElementTableDataDTO> iPage = elementTableDataService.selectJoinListPage(page, TsElementTableDataDTO.class, wrapper);
            //总记录数
            long total = iPage.getTotal();
            long total1 = page2.getTotal();
            //数据list集合
            List<TsElementTableDataDTO> records = iPage.getRecords();
            List<ElementTableDescription> records1 = page2.getRecords();
            Map<String,Object> dataMap = new HashMap<>(16);
            dataMap.put("descriptionList", records1);
            dataMap.put("tableDataList", records);
            dataMap.put("total", total);
            dataMap.put("total1", total1);
            if (records1.isEmpty()) {
                return AjaxResult.success("查无资料");
            } else {
                return AjaxResult.success("查询成功", dataMap);
            }
        } catch (Exception e) {
            return AjaxResult.error();
        }
    }

    /**
     * 试验元素表格维护同步新增接口
     */
    @Operation(summary = "试验元素表格维护同步新增接口")
    @PostMapping("/addErp")
    @Log(title = "试验元素表格新增", businessType = BusinessType.INSERT)
    public Object addErpData(@RequestBody TsElementTableDataDTO tsElementTableDataDTO, @RequestHeader("token") String token) {
        try {
            String formatId = tsElementTableDataDTO.getFormatId();
            ElementTableData elementTableData = new ElementTableData();
            BeanUtils.copyProperties(tsElementTableDataDTO, elementTableData);
            ElementTableDescription elementTableDescription = new ElementTableDescription();
            BeanUtils.copyProperties(tsElementTableDataDTO, elementTableDescription);
            LambdaQueryWrapper<ElementTableDescription> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ElementTableDescription::getFormatId, formatId);
            List<ElementTableDescription> list = elementTableDescriptionService.list(queryWrapper);
            if (!list.isEmpty()) {
                boolean result = elementTableDataService.save(elementTableData);
                if (result) {
                    return AjaxResult.success("保存成功");
                } else {
                    return AjaxResult.error("保存失败");
                }
            } else {
                elementTableDescriptionService.save(elementTableDescription);
                boolean result = elementTableDataService.save(elementTableData);
                if (result) {
                    return AjaxResult.success("保存成功");
                } else {
                    return AjaxResult.error("保存失败");
                }
            }
        } catch (Exception e) {
            return AjaxResult.error();
        }
    }

    /**
     * 试验元素表格维护同步编辑接口
     */
    @Operation(summary = "试验元素表格维护同步编辑接口")
    @PostMapping("/updateErp")
    @Log(title = "试验元素表格编辑", businessType = BusinessType.UPDATE)
    public Object updateErpData(@RequestBody TsElementTableDataDTO tsElementTableDataDTO, @RequestHeader("token") String token) {
        try {
            String formatId = tsElementTableDataDTO.getFormatId();
            String elementIndex = tsElementTableDataDTO.getElementIndex();
            ElementTableData elementTableData = new ElementTableData();
            BeanUtils.copyProperties(tsElementTableDataDTO, elementTableData);
            LambdaUpdateWrapper<ElementTableData> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(StringUtils.isNotBlank(formatId), ElementTableData::getFormatId, formatId)
                         .eq(StringUtils.isNotBlank(elementIndex), ElementTableData::getElementIndex, elementIndex);
            boolean result = elementTableDataService.update(elementTableData, updateWrapper);
            if (result) {
                return AjaxResult.success("保存成功");
            } else {
                return AjaxResult.error("保存失败");
            }
        } catch (Exception e) {
            return AjaxResult.error();
        }
    }
}
