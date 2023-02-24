package com.jlkj.quality.ts.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.dto.resp.Result;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.quality.ts.domain.ElementHighLowStandard;
import com.jlkj.quality.ts.domain.SampleBasicDataA;
import com.jlkj.quality.ts.domain.TestElementCode;
import com.jlkj.quality.ts.dto.TsElementHighLowStandardDTO;
import com.jlkj.quality.ts.service.ElementHighLowStandardService;
import com.jlkj.quality.ts.service.SampleBasicDataaService;
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
 * @Description 试样品试验高低标准controller
 * @date 2022/5/16 - 16:03
 */
@RestController
@RequestMapping("/quality/chemicalTest/highAndLowStandards")
public class ElementHighLowStandardController {

    @Autowired
    ElementHighLowStandardService elementHighLowStandardService;

    @Autowired
    SampleBasicDataaService sampleBasicDataaService;

    @Autowired
    TestElementCodeService testElementCodeService;

    /**
     * 搜索样品试验高低标准
     */
    @Operation(summary = "搜索样品试验高低标准")
    @PostMapping("/queryList")
    @Log(title = "样品试验高低标准查询", businessType = BusinessType.OTHER)
    public Object queryListTsElementHighLowStandard(@RequestBody TsElementHighLowStandardDTO tsElementHighLowStandardDTO) {
        try {
            String sampleClass = tsElementHighLowStandardDTO.getSampleClass();
            Long pageNum = tsElementHighLowStandardDTO.getPageNum();
            Long pageSize = tsElementHighLowStandardDTO.getPageSize();
            Page<TsElementHighLowStandardDTO> page = new Page<>(pageNum, pageSize);
            MPJLambdaWrapper<TsElementHighLowStandardDTO> wrapper = new MPJLambdaWrapper<>();
            wrapper.selectAll(ElementHighLowStandard.class)
                   .select(TestElementCode::getElementName, TestElementCode::getElementUnit)
                   .eq(StringUtils.isNotBlank(sampleClass), ElementHighLowStandard::getSampleClass, sampleClass)
                   .innerJoin(TestElementCode.class, TestElementCode::getElementIndex, ElementHighLowStandard::getElementIndex);
            IPage<TsElementHighLowStandardDTO> iPage = elementHighLowStandardService.selectJoinListPage(page, TsElementHighLowStandardDTO.class, wrapper);
            //总记录数
            long total = iPage.getTotal();
            //数据list集合
            List<TsElementHighLowStandardDTO> records = iPage.getRecords();
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
     * 添加样品试验高低标准
     */
    @Operation(summary = "添加样品试验高低标准")
    @PostMapping("/add")
    @Log(title = "样品试验高低标准添加", businessType = BusinessType.INSERT)
    public Object addTsElementHighLowStandard(@RequestBody TsElementHighLowStandardDTO tsElementHighLowStandardDTO) {
        try {
            List<ElementHighLowStandard> elementHighLowStandard = tsElementHighLowStandardDTO.getElementHighLowStandard();
            for (ElementHighLowStandard item : elementHighLowStandard) {
                List<ElementHighLowStandard> list = elementHighLowStandardService.query().eq("sample_class", item.getSampleClass()).eq("element_index", item.getElementIndex()).list();
                if(!list.isEmpty()){
                    return Result.failed("您输入的" + item.getSampleClass()+ "+" + item.getElementIndex() + "系统中已存在，请重新输入！");
                }
                item.setId(UUID.randomUUID().toString());
            }
            boolean result = elementHighLowStandardService.saveBatch(elementHighLowStandard);
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
     * 编辑样品试验高低标准
     */
    @Operation(summary = "编辑样品试验高低标准")
    @PostMapping("/update")
    @Log(title = "样品试验高低标准编辑", businessType = BusinessType.UPDATE)
    public Object updateTsElementHighLowStandard(@RequestBody TsElementHighLowStandardDTO tsElementHighLowStandardDTO) {
        try {
            List<ElementHighLowStandard> elementHighLowStandard = tsElementHighLowStandardDTO.getElementHighLowStandard();
            for (int i = 0; i < elementHighLowStandard.size() - 1; i++){
                for (int j = i + 1; j < elementHighLowStandard.size(); j++) {
                    if (elementHighLowStandard.get(i).getSampleClass().equals(elementHighLowStandard.get(j).getSampleClass()) && elementHighLowStandard.get(i).getElementIndex().equals(elementHighLowStandard.get(j).getElementIndex()) ) {
                        elementHighLowStandard.remove(j);
                    }
                }
            }
            elementHighLowStandardService.saveOrUpdateBatch(elementHighLowStandard);
            return AjaxResult.success("保存成功");
        } catch (Exception e) {
            return AjaxResult.error();
        }
    }

    /**
     * 删除样品试验高低标准
     */
    @Operation(summary = "删除样品试验高低标准")
    @DeleteMapping("/delete")
    @Log(title = "样品试验高低标准删除", businessType = BusinessType.DELETE)
    public Object deleteTsElementHighLowStandard(@RequestParam String sampleClass, @RequestHeader("token") String token) {
        try {
            LambdaQueryWrapper<ElementHighLowStandard> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ElementHighLowStandard::getSampleClass, sampleClass);
            boolean result = elementHighLowStandardService.remove(queryWrapper);
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
     * 删除样品试验高低标准详细信息元素
     */
    @Operation(summary = "删除样品试验高低标准详细信息元素")
    @DeleteMapping("/deleteA")
    @Log(title = "样品试验高低标准详细信息删除", businessType = BusinessType.DELETE)
    public Object deleteTsElementHighLowStandardA(@RequestParam String elementIndex, String sampleClass, @RequestHeader("token") String token) {
        try {
            LambdaQueryWrapper<ElementHighLowStandard> queryWrapperA = new LambdaQueryWrapper<>();
            queryWrapperA.eq(ElementHighLowStandard::getSampleClass, sampleClass)
                    .eq(ElementHighLowStandard::getElementIndex, elementIndex);
            boolean result = elementHighLowStandardService.remove(queryWrapperA);
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
     * 根据样品类别前两码查询样品基本资料代码
     */
    @Operation(summary = "根据样品类别前两码查询样品基本资料代码")
    @GetMapping("/queryBasicData")
    @Log(title = "样品类别前两码查询", businessType = BusinessType.OTHER)
    public Object queryTsSampleBasicDataA(@RequestParam String sampleClass) {
        try {
            LambdaQueryWrapper<SampleBasicDataA> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.likeRight(StringUtils.isNotBlank(sampleClass), SampleBasicDataA::getSampleClass, sampleClass)
                        .orderByDesc(SampleBasicDataA::getSampleClass);
            List<SampleBasicDataA> list = sampleBasicDataaService.list(queryWrapper);
            return AjaxResult.success("查询成功", list);
        } catch (Exception e) {
            return AjaxResult.error();
        }
    }

    /**
     * 根据样品类别查询
     */
    @Operation(summary = "根据样品类别查询")
    @GetMapping("/query")
    @Log(title = "样品类别查询", businessType = BusinessType.OTHER)
    public Object queryTsElementHighLowStandard(@RequestParam String sampleClass) {
        try {
            List<SampleBasicDataA> sampleBasicDataList = sampleBasicDataaService.query().eq("sample_class", sampleClass).list();
            MPJLambdaWrapper<TsElementHighLowStandardDTO> wrapper = new MPJLambdaWrapper<>();
            wrapper.select(ElementHighLowStandard::getElementIndex, ElementHighLowStandard::getMaxValue, ElementHighLowStandard::getMinValue, ElementHighLowStandard::getId)
                    .select(TestElementCode::getElementName, TestElementCode::getElementUnit)
                    .eq(ElementHighLowStandard::getSampleClass, sampleClass)
                    .innerJoin(TestElementCode.class, TestElementCode::getElementIndex, ElementHighLowStandard::getElementIndex);
            List<TsElementHighLowStandardDTO> list = elementHighLowStandardService.selectJoinList(TsElementHighLowStandardDTO.class, wrapper);
            Map<String,Object> dataMap = new HashMap<>(16);
            dataMap.put("sampleBasicDataList", sampleBasicDataList);
            dataMap.put("elementHighLowStandardList", list);
            if (sampleBasicDataList.isEmpty()) {
                return AjaxResult.success("查无资料");
            } else {
                return AjaxResult.success("查询成功", dataMap);
            }
        } catch (Exception e) {
            return AjaxResult.error();
        }
    }

    /**
     * 样品试验高低标准维护同步新增接口
     */
    @Operation(summary = "样品试验高低标准维护同步新增接口")
    @PostMapping("/addErp")
    @Log(title = "试验高低标准维护新增", businessType = BusinessType.INSERT)
    public Object addErpData(@RequestBody TsElementHighLowStandardDTO tsElementHighLowStandardDTO, @RequestHeader("token") String token) {
        try {
            ElementHighLowStandard elementHighLowStandard = new ElementHighLowStandard();
            BeanUtils.copyProperties(tsElementHighLowStandardDTO, elementHighLowStandard);
            boolean result = elementHighLowStandardService.save(elementHighLowStandard);
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
     * 试验元素表格维护同步编辑接口
     */
    @Operation(summary = "试验元素表格维护同步编辑接口")
    @PostMapping("/updateErp")
    @Log(title = "试验高低标准维护编辑", businessType = BusinessType.UPDATE)
    public Object updateErpData(@RequestBody TsElementHighLowStandardDTO tsElementHighLowStandardDTO, @RequestHeader("token") String token) {
        try {
            String sampleClass = tsElementHighLowStandardDTO.getSampleClass();
            String elementIndex = tsElementHighLowStandardDTO.getElementIndex();
            ElementHighLowStandard elementHighLowStandard = new ElementHighLowStandard();
            BeanUtils.copyProperties(tsElementHighLowStandardDTO, elementHighLowStandard);
            LambdaUpdateWrapper<ElementHighLowStandard> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(StringUtils.isNotBlank(sampleClass), ElementHighLowStandard::getSampleClass, sampleClass)
                         .eq(StringUtils.isNotBlank(elementIndex), ElementHighLowStandard::getElementIndex, elementIndex);
            boolean result = elementHighLowStandardService.update(elementHighLowStandard, updateWrapper);
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
