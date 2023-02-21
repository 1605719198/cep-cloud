package com.jlkj.finance.IP.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.finance.AA.domain.FinanceAccountCode;
import com.jlkj.finance.AA.domain.FinanceIdCode;
import com.jlkj.finance.AA.domain.FinanceRefCode;
import com.jlkj.finance.AC.domain.FinanceCostCode;
import com.jlkj.finance.IP.domain.FinanceProductAccount;
import com.jlkj.finance.IP.domain.FinanceProductAttribute;
import com.jlkj.finance.IP.domain.FinanceProductBase;
import com.jlkj.finance.IP.domain.FinanceProductSettingDetail;
import com.jlkj.finance.IP.dto.FinanceAccountDTO;
import com.jlkj.finance.IP.dto.FinanceProductAccountDTO;
import com.jlkj.finance.IP.dto.FinanceProductAttributeDTO;
import com.jlkj.finance.IP.dto.FinanceProductDTO;
import com.jlkj.finance.IP.feign.SysDictDataFeignService;
import com.jlkj.finance.IP.service.FinanceProductAccountService;
import com.jlkj.finance.IP.service.FinanceProductAttributeService;
import com.jlkj.finance.IP.service.FinanceProductBaseService;
import com.jlkj.finance.IP.service.FinanceProductSettingDetailService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author wang'mai
 */
@RestController
@RequestMapping("/finance/product/code")
public class FinanceProductController {
    @Autowired
    private FinanceProductBaseService financeProductBaseService;
    @Autowired
    private FinanceProductAttributeService financeProductAttributeService;
    @Autowired
    private FinanceProductSettingDetailService financeProductSettingDetailService;
    @Autowired
    private FinanceProductAccountService financeProductAccountService;
    @Autowired
    private SysDictDataFeignService sysDictDataFeignService;

    /**
     * 根据产副品代码、产副品名称查询
     */
    @SuppressWarnings("SuspiciousMethodCalls")
    @Operation(summary = "根据产副品代码、产副品名称查询")
    @GetMapping("/queryAll")
    @Log(title = "产副品代码、产副品名称查询", businessType = BusinessType.OTHER)
    public Object queryAll(FinanceProductDTO financeProductDTO) {
        try {
            String prodCode = financeProductDTO.getProductCode();
            String prodCodeDesc = financeProductDTO.getProductCodeDesc();
            Long pageNum = financeProductDTO.getPageNum();
            Long pageSize = financeProductDTO.getPageSize();
            LambdaQueryWrapper<FinanceProductBase> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.like(StringUtils.isNotBlank(prodCode), FinanceProductBase::getProductCode, prodCode)
                    .like(StringUtils.isNotBlank(prodCodeDesc), FinanceProductBase::getProductCodeDesc, prodCodeDesc);
            Page<FinanceProductBase> page = financeProductBaseService.page(new Page<>(pageNum, pageSize), queryWrapper);
            long total = page.getTotal();
            //数据list集合
            List<FinanceProductBase> list1 = page.getRecords();
            List<FinanceProductBase> records = new ArrayList<>();
            for (FinanceProductBase attr : list1){
                String productAccountAttri = attr.getProductAccountAttri();
                String productAccountAttriName= "cost_productAccountAttri";
                Map<String, Object> labelByDictValue = sysDictDataFeignService.getLabelByDictValue(productAccountAttriName,productAccountAttri);
                Set<String> set = labelByDictValue.keySet();
                String idKindNoMap = "";
                for (Object obj : set) {
                    idKindNoMap = "" + labelByDictValue.get(obj);
                }
                attr.setProductAccountAttri(idKindNoMap);
                records.add(attr);
            }
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
     * 查询财务资料的科目名称
     */
    @Operation(summary = "查询财务资料的科目名称")
    @GetMapping("/queryAccount")
    @Log(title = "财务资料的科目名称查询", businessType = BusinessType.OTHER)
    public Object queryAccount(FinanceAccountDTO financeAccountDTO) {
        try {
            String compIdIdValue = "finance_compId";
            List<Map<String, Object>> compIdValue = sysDictDataFeignService.getMainCompIdValue(compIdIdValue);
            Map<String, Object> compIdMap = compIdValue.get(0);
            String compId = null;
            for (String key : compIdMap.keySet()) {
                compId = (String) compIdMap.get(key);
            }

            String leafId = financeAccountDTO.getLeafId();
            Long pageNum = financeAccountDTO.getPageNum();
            Long pageSize = financeAccountDTO.getPageSize();
            LambdaQueryWrapper<FinanceProductSettingDetail> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(StringUtils.isNotBlank(compId), FinanceProductSettingDetail::getCompId, compId)
                    .eq(StringUtils.isNotBlank(leafId), FinanceProductSettingDetail::getLeafId, leafId);
            Page<FinanceProductSettingDetail> page = financeProductSettingDetailService.page(new Page<>(pageNum, pageSize), queryWrapper);
            long total = page.getTotal();
            //数据list集合
            List<FinanceProductSettingDetail> records = page.getRecords();
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
     * 根据副产品编码查询财务资料
     */
    @SuppressWarnings("SuspiciousMethodCalls")
    @Operation(summary = "根据副产品编码查询财务资料")
    @GetMapping("/queryByproduct")
    @Log(title = "财务资料查询", businessType = BusinessType.OTHER)
    public Object queryByproduct(FinanceProductAccountDTO financeProductAccountDTO) {
        try {
            String compIdIdValue = "finance_compId";
            List<Map<String, Object>> compIdValue = sysDictDataFeignService.getMainCompIdValue(compIdIdValue);
            Map<String, Object> compIdMap = compIdValue.get(0);
            String compId = null;
            for (String key : compIdMap.keySet()) {
                compId = (String) compIdMap.get(key);
            }
            String leafId = financeProductAccountDTO.getLeafId();
            String productCode = financeProductAccountDTO.getProductCode();
            Long pageNum = financeProductAccountDTO.getPageNum();
            Long pageSize = financeProductAccountDTO.getPageSize();
            Page<FinanceProductAccountDTO> page1 = new Page<>(pageNum, pageSize);
            LambdaQueryWrapper<FinanceProductSettingDetail> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(StringUtils.isNotBlank(compId), FinanceProductSettingDetail::getCompId, compId)
                    .eq(StringUtils.isNotBlank(leafId), FinanceProductSettingDetail::getLeafId, leafId);
            Page<FinanceProductSettingDetail> page = financeProductSettingDetailService.page(new Page<>(pageNum, pageSize), queryWrapper);
            MPJLambdaWrapper<FinanceProductAccountDTO> wrapper = new MPJLambdaWrapper<>();
            wrapper.selectAll(FinanceProductAccount.class)
                    .select(FinanceCostCode::getCostCode, FinanceCostCode::getCostDesc)
                    .select(FinanceProductSettingDetail::getItemA, FinanceProductSettingDetail::getDataA, FinanceProductSettingDetail::getDataB)
                    .select(FinanceAccountCode::getIdTypeStus, FinanceAccountCode::getRefTypeStus,FinanceAccountCode::getAccountChineseName)
                    .select(FinanceIdCode::getIdKindNo)
                    .select(FinanceRefCode::getRefKindNum, FinanceRefCode::getRefTypeNum)
                    .leftJoin(FinanceCostCode.class, FinanceCostCode::getCostCode, FinanceProductAccount::getCostCode)
                    .leftJoin(FinanceProductSettingDetail.class, FinanceProductSettingDetail::getItemA, FinanceProductAccount::getAccountName)
                    .leftJoin(FinanceAccountCode.class, FinanceAccountCode::getAccountCode, FinanceProductAccount::getAccountCode)
                    .leftJoin(FinanceIdCode.class, FinanceIdCode::getIdTypeNo, FinanceAccountCode::getIdTypeStus)
                    .leftJoin(FinanceRefCode.class, FinanceRefCode::getRefKindNum, FinanceAccountCode::getRefTypeStus)
                    .eq(StringUtils.isNotBlank(productCode), FinanceProductAccount::getProductCode, productCode);
            IPage<FinanceProductAccountDTO> iPage = financeProductAccountService.selectJoinListPage(page1, FinanceProductAccountDTO.class, wrapper);

            long total = page.getTotal();
            //数据list集合
            List<FinanceProductAccountDTO> records = iPage.getRecords();
            List<FinanceProductAccountDTO> list = new ArrayList<>();
            for (FinanceProductAccountDTO attr : records) {
                FinanceProductAccountDTO costdto = new FinanceProductAccountDTO();
                BeanUtils.copyProperties(attr, costdto);
                String idkind = attr.getIdTypeStus();
                String idRefStus = "finance_id_type";
                Map<String, Object> labelByDictValue = sysDictDataFeignService.getLabelByDictValue(idRefStus, idkind);
                Set<String> set = labelByDictValue.keySet();
                String idKindNoMap = "";
                for (Object obj : set) {
                    idKindNoMap = "" + labelByDictValue.get(obj);
                }
                costdto.setAccountIdIdex(idKindNoMap);
                String refTypeNum = attr.getRefKindNum();
                String refTypeStus = "finance_ref_type";
                Map<String, Object> refTypeStusValue = sysDictDataFeignService.getLabelByDictValue(refTypeStus, refTypeNum);
                Set<String> setRefType = refTypeStusValue.keySet();
                String idRefStusMap = "";
                for (Object obj : setRefType) {
                    idRefStusMap = "" + refTypeStusValue.get(obj);
                }
                costdto.setAccountRefnoIndex(idRefStusMap);
                if (attr.getCostDesc() != null && attr.getCostCode() != null) {
                    costdto.setCostDesc(attr.getCostDesc());
                    costdto.setCostCode(attr.getCostCode());
                } else {
                    costdto.setCostDesc("");
                    costdto.setCostCode("");
                }
                list.add(costdto);
            }
            Map<String, Object> dataMap = new HashMap<>(16);
            dataMap.put("total", total);
            dataMap.put("list", list);
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
     * 详情查询
     */
    @SuppressWarnings("SuspiciousMethodCalls")
    @Operation(summary = "详情查询")
    @GetMapping("/queryOne")
    @Log(title = "详情查询", businessType = BusinessType.OTHER)
    public Object queryOne(@RequestParam String productCode, String id) {
        QueryWrapper<FinanceProductBase> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(productCode)) {
            wrapper.eq("product_code", productCode)
            .eq("id",id);
        }
        FinanceProductBase one = financeProductBaseService.getOne(wrapper);
        String pdUnit = one.getPdUnit();
        String  pdUnitName = "cost_pdUnit";
        String productAccountAttri = one.getProductAccountAttri();
        String productAccountAttriName= "cost_productAccountAttri";
        Map<String, Object> labelByDictValue = sysDictDataFeignService.getLabelByDictValue(productAccountAttriName,productAccountAttri);
        Set<String> set = labelByDictValue.keySet();
        String idKindNoMap = "";

        for (Object obj : set) {
            idKindNoMap = "" + labelByDictValue.get(obj);

        }
        one.setProductAccountAttri(idKindNoMap);
        Map<String, Object> labelByDictValuepdUnit = sysDictDataFeignService.getLabelByDictValue(pdUnitName,pdUnit);
        Set<String> setUnit = labelByDictValuepdUnit.keySet();
        String idKindNoMapUnit = "";

        for (Object obj : setUnit) {
            idKindNoMapUnit = "" + labelByDictValuepdUnit.get(obj);

        }
        one.setPdUnit(idKindNoMapUnit);
        return AjaxResult.success(one);
    }

    /**
     * 属性查询
     */
    @SuppressWarnings("SuspiciousMethodCalls")
    @Operation(summary = "属性查询")
    @GetMapping("/attributeQuery")
    @Log(title = "属性查询", businessType = BusinessType.OTHER)
    public Object attributeQuery(FinanceProductAttributeDTO financeProductAttributeDTO) {
        String productCode = financeProductAttributeDTO.getProductCode();
        LambdaQueryWrapper<FinanceProductAttribute> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotBlank(productCode), FinanceProductAttribute::getProductCode, productCode);
        List<FinanceProductAttribute> list = financeProductAttributeService.list(queryWrapper);
        List<FinanceProductAttribute> list1 = new ArrayList<>();
        for (FinanceProductAttribute attr : list) {
            String  attriValue;
            if (StringUtils.isNotBlank(attr.getAttriValue())){
                String str = attr.getAttriValue();
                String [] strs = str.split("_");
                attr.setAttriValue( strs[0]);
                attriValue= attr.getAttriValue();
                String accountItemAttriName ="cost_"+ attr.getAttriName();
                    Map<String, Object> labelByDictValue = sysDictDataFeignService.getLabelByDictValue(accountItemAttriName,attriValue);
                    Set<String> set = labelByDictValue.keySet();
                    String idKindNoMap = "";
                    for (Object obj : set) {

                        idKindNoMap = "" + labelByDictValue.get(obj);
                    }
                    attr.setAttriValue(idKindNoMap);
                }
            else {
                attr.setAttriValue("");
            }
            list1.add(attr);
        }
        return AjaxResult.success(list1);
    }

    /**
     * 修改产副品
     */
    @Operation(summary = "修改产副品")
    @PostMapping("/doEdit")
    @Log(title = "产副品修改", businessType = BusinessType.UPDATE)
    public Object updateEnergyCode(@RequestBody FinanceProductDTO financeProductDTO) {
        try {
            FinanceProductBase financeProductBase = new FinanceProductBase();
            if (StringUtils.isNotBlank(financeProductDTO.getProductAccountAttri())) {
                String str = financeProductDTO.getProductAccountAttri();
                String [] strs = str.split("_");
                financeProductDTO.setProductAccountAttri( strs[0]);
            }else {
                financeProductDTO.setProductAccountAttri("");
            }
            if (StringUtils.isNotBlank(financeProductDTO.getPdUnit())) {
                String str = financeProductDTO.getPdUnit();
                String [] strs = str.split("_");
                financeProductDTO.setPdUnit( strs[0]);
            }else {
                financeProductDTO.setPdUnit("");
            }
            BeanUtils.copyProperties(financeProductDTO, financeProductBase);
            LambdaQueryWrapper<FinanceProductBase> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(FinanceProductBase::getProductCode, financeProductDTO.getProductCode());
            Map<String, Object> map = financeProductBaseService.getMap(queryWrapper);
            String  productCode = "product_code";
            if (map.get(productCode).equals(financeProductBase.getProductCode())) {
                boolean result = financeProductBaseService.updateById(financeProductBase);
                if (result) {
                    Map<String, String> resultMap = new HashMap<>(16);
                    resultMap.put("id", financeProductBase.getId());
                    resultMap.put("productCode", financeProductBase.getProductCode());
                    resultMap.put("update_user", financeProductBase.getUpdateUser());
                    resultMap.put("create_date", financeProductBase.getCreateDate());
                    List<Map<String, String>> resultList = new ArrayList<>();
                    resultList.add(resultMap);
                    return AjaxResult.success("修改成功", resultList);
                } else {
                    return AjaxResult.error("修改失败，请重新提交");
                }
            } else {
                return AjaxResult.error("产副品编码，不允许修改");
            }
        } catch (Exception e) {
            return AjaxResult.error();
        }
    }

    /**
     * 属性修改
     */
    @Operation(summary = "属性修改")
    @PostMapping("/doEditAttri")
    @Log(title = "属性修改", businessType = BusinessType.UPDATE)
    public Object updateFinanceAttri(@RequestBody FinanceProductAttributeDTO financeProductAttributeDTO) {
        try {
            List<FinanceProductAttributeDTO> attrlist = financeProductAttributeDTO.getPropertydata();
            FinanceProductAttribute financeCostCostcenterAttri = new FinanceProductAttribute();
            boolean result = false;
            for (FinanceProductAttributeDTO attr : attrlist) {
                if (StringUtils.isNotBlank(attr.getAttriValue())) {
                    String str = attr.getAttriValue();
                    String [] strs = str.split("_");
                    attr.setAttriValue( strs[0]);
                }
                BeanUtils.copyProperties(attr, financeCostCostcenterAttri);
                LambdaUpdateWrapper<FinanceProductAttribute> updateWrapper = new LambdaUpdateWrapper<>();
                updateWrapper.set(FinanceProductAttribute::getAttriValue, attr.getAttriValue())
                        .eq(FinanceProductAttribute::getProductCode, attr.getProductCode())
                        .eq(FinanceProductAttribute::getId, attr.getId());
                 result = financeProductAttributeService.update(updateWrapper);

            }
            if (result =true) {
                Map<String, String> resultMap = new HashMap<>(16);
                resultMap.put("id", financeCostCostcenterAttri.getId());
                resultMap.put("update_user", financeCostCostcenterAttri.getUpdateUser());
                List<Map<String, String>> resultList = new ArrayList<>();
                resultList.add(resultMap);
                return AjaxResult.success("修改成功", resultList);
            }
            return AjaxResult.success("修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error();
        }
    }

    /**
     * 财务资料修改
     */
    @Operation(summary = "财务修改")
    @PostMapping("/doEditAccount")
    @Log(title = "财务修改", businessType = BusinessType.UPDATE)
    public Object updateAccount(@RequestBody FinanceProductAccountDTO financeProductAccountDTO) {
        try {
            List<FinanceProductAccountDTO> attrlist = financeProductAccountDTO.getPropertydata();
            FinanceProductAccount financeProductAccount = new FinanceProductAccount();
            boolean result = false;
            for (FinanceProductAccountDTO attr : attrlist) {
                if (StringUtils.isNotBlank(attr.getAccountCode())){
                    String str = attr.getAccountCode();
                    String [] strs = str.split("_");
                    attr.setAccountCode( strs[0]);
                }

                String costCodeAccount = attr.getCostCodeAccount();
                BeanUtils.copyProperties(attr, financeProductAccount);
                LambdaUpdateWrapper<FinanceProductAccount> updateWrapper = new LambdaUpdateWrapper<>();
                updateWrapper.set(FinanceProductAccount::getCostCode, costCodeAccount)
                        .set(FinanceProductAccount::getAccountCode, financeProductAccount.getAccountCode())
                        .eq(FinanceProductAccount::getProductCode, financeProductAccountDTO.getProductCode())
                        .eq(FinanceProductAccount::getId, attr.getId());
               result = financeProductAccountService.update(updateWrapper);
            } if (result) {
                Map<String, String> resultMap = new HashMap<>(16);
                resultMap.put("id", financeProductAccount.getId());
                resultMap.put("update_user", financeProductAccount.getUpdateUser());
                List<Map<String, String>> resultList = new ArrayList<>();
                resultList.add(resultMap);
                return AjaxResult.success("修改成功", resultList);
            }else {
                return AjaxResult.error("修改失败，请重新提交");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error();
        }
    }

    /**
     * 产副品新增
     */
    @Operation(summary = "产副品新增")
    @PostMapping("/doAdd")
    @Log(title = "产副品新增", businessType = BusinessType.INSERT)
    public Object addFinanceCost(@RequestBody FinanceProductDTO financeProductDTO) {
        try {
            QueryWrapper<FinanceProductBase> wrapper = new QueryWrapper<>();
            FinanceProductBase financeProductBase = new FinanceProductBase();
            if (StringUtils.isNotBlank(financeProductDTO.getProductAccountAttri())) {
                String str = financeProductDTO.getProductAccountAttri();
                String [] strs = str.split("_");
                financeProductDTO.setProductAccountAttri( strs[0]);
            }
            if (StringUtils.isNotBlank(financeProductDTO.getPdUnit())) {
                String str = financeProductDTO.getPdUnit();
                String [] strs = str.split("_");
                financeProductDTO.setPdUnit( strs[0]);
            }
            BeanUtils.copyProperties(financeProductDTO, financeProductBase);
            String productCode = financeProductDTO.getProductCode();
            String compIdIdValue = "finance_compId";
            List<Map<String, Object>> compIdValue = sysDictDataFeignService.getMainCompIdValue(compIdIdValue);
            Map<String, Object> compIdMap = compIdValue.get(0);
            String compId = null;
            for (String key : compIdMap.keySet()) {
                compId = (String) compIdMap.get(key);
            }
            financeProductBase.setCompId(compId);
            wrapper.eq("product_code", productCode)
                    .eq("comp_id", compId);
            List<FinanceProductBase> list = financeProductBaseService.list(wrapper);
            if (list.size() >= 1) {
                return AjaxResult.error("您输入的产副品系统中已存在，请重新输入！");
            }

            boolean result = financeProductBaseService.save(financeProductBase);

            if (result) {
                Map<String, String> resultMap = new HashMap<>(16);
                resultMap.put("id", financeProductBase.getId());
                resultMap.put("compId", financeProductBase.getCompId());
                resultMap.put("productCode", financeProductBase.getProductCode());
                resultMap.put("create_user", financeProductBase.getCreateUser());
                resultMap.put("create_date", financeProductBase.getCreateDate());
                List<Map<String, String>> resultList = new ArrayList<>();
                resultList.add(resultMap);
                return AjaxResult.success("新增成功", resultList);
            } else {
                return AjaxResult.error("新增失败，请重新提交");
            }
        } catch (Exception e) {
            return AjaxResult.error();
        }
    }

    /**
     * 属性新增
     */
    @Operation(summary = "属性新增")
    @PostMapping("/attriAdd")
    @Log(title = "属性新增", businessType = BusinessType.INSERT)
    public Object attriAdd(@RequestBody FinanceProductAttributeDTO financeProductAttributeDTO) {
        try {
            String productCode = financeProductAttributeDTO.getProductCode();
            List<FinanceProductAttributeDTO> attrlist = financeProductAttributeDTO.getPropertydata();
            FinanceProductAttribute financeCostCostcenterAttri = new FinanceProductAttribute();
            for (FinanceProductAttributeDTO attr : attrlist) {
                if (StringUtils.isNotBlank(attr.getAttriValue())) {
                    String str = attr.getAttriValue();
                    String [] strs = str.split("_");
                    attr.setAttriValue( strs[0]);
                }
                BeanUtils.copyProperties(attr, financeCostCostcenterAttri);
                financeCostCostcenterAttri.setId("");
                String compIdIdValue = "finance_compId";
                List<Map<String, Object>> compIdValue = sysDictDataFeignService.getMainCompIdValue(compIdIdValue);
                Map<String, Object> compIdMap = compIdValue.get(0);
                String compId = null;
                for (String key : compIdMap.keySet()) {
                    compId = (String) compIdMap.get(key);
                }
                financeCostCostcenterAttri.setCompId(compId);
                financeCostCostcenterAttri.setProductCode(productCode);
                financeCostCostcenterAttri.setAttriName(attr.getValue());
                financeCostCostcenterAttri.setAttriValue(attr.getAttriValue());
                financeProductAttributeService.save(financeCostCostcenterAttri);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error();
        }
        return null;
    }

    /**
     * 财务新增
     */
    @Operation(summary = "财务新增")
    @PostMapping("/accountAdd")
    @Log(title = "财务新增", businessType = BusinessType.INSERT)
    public Object accountAdd(@RequestBody FinanceProductAccountDTO financeProductAccountDTO) {
        try {
            List<FinanceProductAccountDTO> attrlist = financeProductAccountDTO.getPropertydata();
            FinanceProductAccount financeProductAccount = new FinanceProductAccount();
            for (FinanceProductAccountDTO attr : attrlist) {
                BeanUtils.copyProperties(attr, financeProductAccount);
                financeProductAccount.setProductCode(financeProductAccountDTO.getProductCode());
                financeProductAccount.setCostCode(attr.getCostCode());
                financeProductAccount.setAccountName(attr.getItemA());
                financeProductAccount.setId("");
                financeProductAccount.setAccountIdIdex("");
                financeProductAccount.setAccountRefnoIndex("");
                financeProductAccountService.save(financeProductAccount);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error();
        }
        return null;
    }

    /**
     * 删除产副品资料
     *
     */
    @Operation(summary = "删除产副品资料")
    @DeleteMapping("/delete")
    @Log(title = "产副品资料删除", businessType = BusinessType.DELETE)
    public Object deleteEnergyCode(@RequestParam String id) {
        try {
            QueryWrapper<FinanceProductBase> wrapper = new QueryWrapper<>();
            wrapper.eq("id", id);
            FinanceProductBase financeProductBase = financeProductBaseService.getOne(wrapper);
            String compIdIdValue = "finance_compId";
            List<Map<String, Object>> compIdValue = sysDictDataFeignService.getMainCompIdValue(compIdIdValue);
            Map<String, Object> compIdMap = compIdValue.get(0);
            String compId = null;
            for (String key : compIdMap.keySet()) {
                compId = (String) compIdMap.get(key);
            }
            String productCode = financeProductBase.getProductCode();
            QueryWrapper<FinanceProductAttribute> wrapperProductAttribute = new QueryWrapper<>();
            wrapperProductAttribute.eq("product_code", productCode)
                    .eq("comp_id", compId);
            List<FinanceProductAttribute> list = financeProductAttributeService.list(wrapperProductAttribute);
            List<FinanceProductAttribute> list1 = new ArrayList<>();
            String accountItemAttriValue;
            for (FinanceProductAttribute attri:list){
                accountItemAttriValue = attri.getAttriValue();
                if(StringUtils.isNotBlank(accountItemAttriValue)){
                    list1.add(attri);
                }
            }
            if (list1.size()>=1) {
                return AjaxResult.error("删除产副品资料系统中有属性资料，请先删除属性资料！");
            }else {
                financeProductAttributeService.remove(wrapperProductAttribute);
            }

            QueryWrapper<FinanceProductAccount> wrapperProductAccount = new QueryWrapper<>();
            wrapperProductAccount.eq("product_code", productCode)
                    .eq("comp_id", compId);
            List<FinanceProductAccount> listProductAccount = financeProductAccountService.list(wrapperProductAccount);
            for (FinanceProductAccount attr : listProductAccount) {
                if (StringUtils.isNotBlank(attr.getCostCode()) || StringUtils.isNotBlank(attr.getAccountCode())) {
                    return AjaxResult.error("删除产副品资料系统中有帐务资料，请先删除帐务资料！");
                }else {
                    financeProductAccountService.remove(wrapperProductAccount);
                }
            }
            boolean result = financeProductBaseService.removeById(id);
            if (result) {
                Map<String, String> resultMap = new HashMap<>(16);
                resultMap.put("id", financeProductBase.getId());
                resultMap.put("update_user", financeProductBase.getUpdateUser());
                resultMap.put("create_date", financeProductBase.getCreateDate());
                List<Map<String, String>> resultList = new ArrayList<>();
                resultList.add(resultMap);
                return AjaxResult.success("删除成功", resultList);
            } else {
                return AjaxResult.error("删除失败，请重新提交");
            }
        } catch (Exception e) {
            return AjaxResult.error();
        }

    }
}
