package com.jlkj.finance.gp.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.dto.finance.gp.ManufacturerBaseInterfaceDTO;
import com.jlkj.finance.gp.domain.*;
import com.jlkj.finance.gp.dto.ManufacturerBaseDTO;
import com.jlkj.finance.feign.system.SysDictDataFeignService;
import com.jlkj.finance.gp.service.*;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.*;


/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author wm
 * @since 2022-04-24
 */
@RestController
@RequestMapping("/finance/manufacturer")
public class ManufacturerBaseController {
    @Autowired
    private ManufacturerBaseService manufacturerBaseService;
    @Autowired
    private ManufacturerCustomerService manufacturerCustomerService;
    @Autowired
    private ManufacturerFinanceService manufacturerFinanceService;
    @Autowired
    private ManufacturerPurchaseService manufacturerPurchaseService;
    @Autowired
    private ManufacturerRelationService manufacturerRelationService;
    @Autowired
    private ManufacturerTransportService manufacturerTransportService;
    @Autowired
    private SysDictDataFeignService sysDictDataFeignService;

    /**
     * 根据厂商编码、厂商中文名称、税号
     */
    @Log(title = "根据厂商编码、厂商中文名称、税号查询", businessType = BusinessType.OTHER)
    @Operation(summary = "根据厂商编码、厂商中文名称、税号")
    @GetMapping("/queryAll")
    public Object queryAll(ManufacturerBaseDTO manufacturerBaseDTO) {
        try {
            String manufacturerChineseName = manufacturerBaseDTO.getManufacturerChineseName();
            String manufacturerId = manufacturerBaseDTO.getManufacturerId();
            String taxNo = manufacturerBaseDTO.getTaxNo();
            Long pageNum = manufacturerBaseDTO.getPageNum();
            Long pageSize = manufacturerBaseDTO.getPageSize();
            LambdaQueryWrapper<ManufacturerBase> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.like(StringUtils.isNotBlank(manufacturerChineseName), ManufacturerBase::getManufacturerChineseName, manufacturerChineseName)
                    .like(StringUtils.isNotBlank(manufacturerId), ManufacturerBase::getManufacturerId, manufacturerId)
                    .like(StringUtils.isNotBlank(taxNo), ManufacturerBase::getTaxNo, taxNo);
            Page<ManufacturerBase> page = manufacturerBaseService.page(new Page<>(pageNum, pageSize), queryWrapper);
            //总记录数
            long total = page.getTotal();
            //数据list集合
            List<ManufacturerBase> records = page.getRecords();
            Map<String, Object> dataMap = new HashMap<>(16);
            dataMap.put("total", total);
            dataMap.put("list", records);
            if (page.getRecords().isEmpty()) {
                return AjaxResult.error("查无资料");
            } else {
                return AjaxResult.success("查询成功！", dataMap);
            }
        } catch(Exception e)
        {
            return AjaxResult.error();
        }

    }
    /**
     * 根据公司别、厂商编码
     */
    @Log(title = "根据公司别、厂商编码查询", businessType = BusinessType.OTHER)
    @SuppressWarnings("SuspiciousMethodCalls")
    @Operation(summary = "查看详情")
    @GetMapping("/queryOne")
    public Object queryOne(@RequestParam String manufacturerId) {

        QueryWrapper<ManufacturerBase> wrapper = new QueryWrapper<>();
        String mainCompIdValue = "finance_compId";
        List<Map<String, Object>> compIdValue = sysDictDataFeignService.getMainCompIdValue(mainCompIdValue);
        Map<String, Object> compIdMap = compIdValue.get(1);
        String compId = null;
        for (String key : compIdMap.keySet()) {
            compId = (String) compIdMap.get(key);
        }

        if (!StringUtils.isEmpty(manufacturerId)) {
            wrapper.eq("manufacturer_id", manufacturerId)
                    .eq("comp_id", compId);
        }
        ManufacturerBase one = manufacturerBaseService.getOne(wrapper);
        String accountItemAttriName = "vendor_primary_area_code";
        if (StringUtils.isNotBlank(one.getMainAreaId())){
            Object mainAreaIdValue = sysDictDataFeignService.getMainAreaIdValue(accountItemAttriName, one.getMainAreaId());
            Map entity = (Map) mainAreaIdValue;
            String msg = entity.get("msg").toString();
            Map<String, Object> labelByDictValue = sysDictDataFeignService.getLabelByDictValue(msg, one.getDetailAreaId());
            Set<String> set = labelByDictValue.keySet();
            String idKindNoMap = "";
            for (Object obj : set) {
                idKindNoMap = "" + labelByDictValue.get(obj);
            }
            String[] strs = idKindNoMap.split("_");
            one.setDetailAreaId(strs[1]);
        }

        return AjaxResult.success(one);
    }
    /**
     * 根据公司别、厂商编码
     */
    @Log(title = "根据公司别、厂商编码查询", businessType = BusinessType.OTHER)
    @Operation(summary = "查询接口")
    @GetMapping("/queryInterface")
    public ManufacturerBaseInterfaceDTO queryInterface(@RequestParam String manufacturerId, String compId) {
        ManufacturerBaseInterfaceDTO manufacturerBaseInterfaceDTO =new  ManufacturerBaseInterfaceDTO();
        QueryWrapper<ManufacturerBase> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(manufacturerId)) {
            wrapper.like("manufacturer_id", manufacturerId)
                    .like("comp_id", compId);
        }
        ManufacturerBase manufacturerBase = manufacturerBaseService.getOne(wrapper);
        BeanUtils.copyProperties(manufacturerBase, manufacturerBaseInterfaceDTO);
        return manufacturerBaseInterfaceDTO;
    }

    /**
     * 根据公司别、厂商编码查询厂商客户关系资料表
     */
    @Log(title = "查询厂商客户关系资料表", businessType = BusinessType.OTHER)
    @Operation(summary = "查看详情")
    @GetMapping("/querySo")
    public Object querySo(@RequestParam String manufacturerId) {
        QueryWrapper<ManufacturerCustomer> wrapper = new QueryWrapper<>();
        String mainCompIdValue = "finance_compId";
        List<Map<String, Object>> compIdValue = sysDictDataFeignService.getMainCompIdValue(mainCompIdValue);

        Map<String, Object> compIdMap = compIdValue.get(1);

        String compId = null;
        for (String key : compIdMap.keySet()) {
            compId = (String) compIdMap.get(key);
        }

        if (!StringUtils.isEmpty(manufacturerId)) {
            wrapper.eq("manufacturer_id", manufacturerId)
                    .eq("comp_id", compId);
        }
        ManufacturerCustomer queryCus = manufacturerCustomerService.getOne(wrapper);
        return AjaxResult.success(queryCus);
    }

    /**
     * 根据公司别、厂商编码查询厂商报支关系资料表
     */
    @Log(title = "查询厂商报支关系资料表", businessType = BusinessType.OTHER)
    @SuppressWarnings("SuspiciousMethodCalls")
    @Operation(summary = "查看报支关系")
    @GetMapping("/queryFc")
    public Object queryFc(@RequestParam String manufacturerId) {
        QueryWrapper<ManufacturerFinance> wrapper = new QueryWrapper<>();
        String mainCompIdValue = "finance_compId";
        List<Map<String, Object>> compIdValue = sysDictDataFeignService.getMainCompIdValue(mainCompIdValue);
        Map<String, Object> compIdMap = compIdValue.get(1);
        String compId = null;
        for (String key : compIdMap.keySet()) {
            compId = (String) compIdMap.get(key);
        }
        if (!StringUtils.isEmpty(manufacturerId)) {
            wrapper.eq("manufacturer_id", manufacturerId)
                    .eq("comp_id", compId);
        }
        ManufacturerFinance queryFin = manufacturerFinanceService.getOne(wrapper);
        String accountItemAttriName = "vendor_primary_area_code";
        if(StringUtils.isNotBlank(queryFin.getMainAreaId1())){
            Object mainAreaIdValue = sysDictDataFeignService.getMainAreaIdValue(accountItemAttriName, queryFin.getMainAreaId1());
            Map entity = (Map)mainAreaIdValue;
            String msg = entity.get("msg").toString();
            Map<String, Object> labelByDictValue = sysDictDataFeignService.getLabelByDictValue(msg, queryFin.getProvince1());
            Set<String> set = labelByDictValue.keySet();
            String idKindNoMap = "";
            for (Object obj : set) {
                idKindNoMap = "" + labelByDictValue.get(obj);
            }
            String[] strs = idKindNoMap.split("_");
            queryFin.setProvince1(strs[1]);
        }

        if(StringUtils.isNotBlank( queryFin.getMainAreaId2())){
            Object mainAreaIdValue2 = sysDictDataFeignService.getMainAreaIdValue(accountItemAttriName, queryFin.getMainAreaId2());
            Map entity2 = (Map)mainAreaIdValue2;
            String msg2 = entity2.get("msg").toString();

            Map<String, Object> labelByDictValue2 = sysDictDataFeignService.getLabelByDictValue(msg2, queryFin.getProvince2());
            Set<String> set2 = labelByDictValue2.keySet();
            String idKindNoMap2 = "";
            for (Object obj : set2) {
                idKindNoMap2 = "" + labelByDictValue2.get(obj);
            }
            String[] strs2 = idKindNoMap2.split("_");
            queryFin.setProvince2(strs2[1]);
        }

        if (StringUtils.isNotBlank(queryFin.getMainAreaId3())){
            Object mainAreaIdValue3 = sysDictDataFeignService.getMainAreaIdValue(accountItemAttriName, queryFin.getMainAreaId3());
            Map entity3 = (Map)mainAreaIdValue3;
            String msg3 = entity3.get("msg").toString();
            Map<String, Object> labelByDictValue3 = sysDictDataFeignService.getLabelByDictValue(msg3, queryFin.getProvince3());
            Set<String> set3 = labelByDictValue3.keySet();
            String idKindNoMap3 = "";
            for (Object obj : set3) {
                idKindNoMap3 = "" + labelByDictValue3.get(obj);
            }
            String[] strs3 = idKindNoMap3.split("_");
            queryFin.setProvince3(strs3[1]);
        }

        return AjaxResult.success(queryFin);
    }

    /**
     * 根据公司别、厂商编码查询厂商采购关系资料表
     */
    @Log(title = "查询厂商采购关系资料表", businessType = BusinessType.OTHER)
    @Operation(summary = "查看详情")
    @GetMapping("/queryMp")
    public Object queryMp(@RequestParam String manufacturerId) {
        String mainCompIdValue = "finance_compId";
        List<Map<String, Object>> compIdValue = sysDictDataFeignService.getMainCompIdValue(mainCompIdValue);
        Map<String, Object> compIdMap = compIdValue.get(1);
        String compId = null;
        for (String key : compIdMap.keySet()) {
            compId = (String) compIdMap.get(key);
        }
        QueryWrapper<ManufacturerPurchase> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(manufacturerId)) {
            wrapper.eq("manufacturer_id", manufacturerId)
                    .eq("comp_id", compId);
        }
        ManufacturerPurchase queryPur = manufacturerPurchaseService.getOne(wrapper);
        return AjaxResult.success(queryPur);
    }
    /**
     * 根据公司别、厂商编码查看关系
     */
    @Log(title = "查看编码关系", businessType = BusinessType.OTHER)
    @Operation(summary = "查看详情查询对应关系")
    @GetMapping("/queryRelations")
    public Object queryRelations(@RequestParam String manufacturerId) {
        String mainCompIdValue = "finance_compId";
        List<Map<String, Object>> compIdValue = sysDictDataFeignService.getMainCompIdValue(mainCompIdValue);
        Map<String, Object> compIdMap = compIdValue.get(1);
        String compId = null;
        for (String key : compIdMap.keySet()) {
            compId = (String) compIdMap.get(key);
        }
        QueryWrapper<ManufacturerRelation> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(manufacturerId)) {
            wrapper.eq("manufacturer_id", manufacturerId)
                    .eq("comp_id", compId);
        }
        List<ManufacturerRelation> list = manufacturerRelationService.list(wrapper);
        return AjaxResult.success(list);
    }

    /**
     * 根据公司别、厂商编码查询厂商承运关系资料表
     */
    @Log(title = "询厂商承运关系资料表", businessType = BusinessType.OTHER)
    @Operation(summary = "查看承运关系")
    @GetMapping("/querySt")
    public Object querySt(@RequestParam String manufacturerId) {
        String mainCompIdValue = "finance_compId";
        List<Map<String, Object>> compIdValue = sysDictDataFeignService.getMainCompIdValue(mainCompIdValue);
        Map<String, Object> compIdMap = compIdValue.get(1);
        String compId = null;
        for (String key : compIdMap.keySet()) {
            compId = (String) compIdMap.get(key);
        }
        QueryWrapper<ManufacturerTransport> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(manufacturerId)) {
            wrapper.eq("manufacturer_id", manufacturerId)
                    .eq("comp_id", compId);
        }
        ManufacturerTransport queryTran = manufacturerTransportService.getOne(wrapper);
        return AjaxResult.success(queryTran);
    }

}
