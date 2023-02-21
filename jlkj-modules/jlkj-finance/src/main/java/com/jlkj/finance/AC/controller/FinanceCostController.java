package com.jlkj.finance.AC.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.finance.AC.domain.FinanceCostCostcenter;
import com.jlkj.finance.AC.domain.FinanceCostCostcenterAttri;
import com.jlkj.finance.AC.dto.FinanceCostCostcenterAttriDTO;
import com.jlkj.finance.AC.dto.FinanceCostDTO;
import com.jlkj.finance.AC.service.FinanceCostCostcenterAttriService;
import com.jlkj.finance.AC.service.FinanceCostCostcenterService;
import com.jlkj.finance.IP.feign.SysDictDataFeignService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author wang'mai
 */
@RestController
@RequestMapping("/finance/cost/costCenter")
public class FinanceCostController {
    @Autowired
    private FinanceCostCostcenterService financeCostCostcenterService;
    @Autowired
    private FinanceCostCostcenterAttriService financeCostCostcenterAttriService;
    @Autowired
    private SysDictDataFeignService sysDictDataFeignService;

    /**
     * 根据成本中心编码、成本中心名称、年度查询
     */
    @Operation(summary = "根据成本中心编码、成本中心名称、年度查询")
    @GetMapping("/queryAll")
    @Log(title = "成本中心编码、成本中心名称、年度查询", businessType = BusinessType.OTHER)
    public Object queryAll(FinanceCostDTO financeCostDTO) {
        try {
            String costCenter = financeCostDTO.getCostCenter();
            String costCenterDesc = financeCostDTO.getCostCenterDesc();
            String fiscalYear = financeCostDTO.getFiscalYear();
            if ("".equals(fiscalYear)) {
                Calendar date = Calendar.getInstance();
                fiscalYear = String.valueOf(date.get(Calendar.YEAR));
            }
            Long pageNum = financeCostDTO.getPageNum();
            Long pageSize = financeCostDTO.getPageSize();
            LambdaQueryWrapper<FinanceCostCostcenter> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.like(StringUtils.isNotBlank(costCenter), FinanceCostCostcenter::getCostCenter, costCenter)
                    .like(StringUtils.isNotBlank(costCenterDesc), FinanceCostCostcenter::getCostCenterDesc, costCenterDesc)
                    .like(StringUtils.isNotBlank(fiscalYear), FinanceCostCostcenter::getFiscalYear, fiscalYear);
            Page<FinanceCostCostcenter> page = financeCostCostcenterService.page(new Page<>(pageNum, pageSize), queryWrapper);
            long total = page.getTotal();
            //数据list集合
            List<FinanceCostCostcenter> records = page.getRecords();
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
     * 成本中心设定接口
     */
    @Operation(summary = "根据当前年度查询")
    @GetMapping("/queryAllInterface")
    @Log(title = "当前年度查询", businessType = BusinessType.OTHER)
    public Object queryAllInterface() {
            Calendar date = Calendar.getInstance();
            String fiscalYear = String.valueOf(date.get(Calendar.YEAR));
            LambdaQueryWrapper<FinanceCostCostcenter> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.like(StringUtils.isNotBlank(fiscalYear), FinanceCostCostcenter::getFiscalYear, fiscalYear);
            List<FinanceCostCostcenter> list = financeCostCostcenterService.list(queryWrapper);
            return AjaxResult.success(list);
    }
    /**
     * 详情查询
     */
    @Operation(summary = "根据成本中心编码、成本中心名称、年度查询")
    @GetMapping("/queryOne")
    @Log(title = "成本中心编码、成本中心名称、年度详情查询", businessType = BusinessType.OTHER)
    public Object queryOne(@RequestParam String costCenter, String id) {
        QueryWrapper<FinanceCostCostcenter> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(costCenter)) {
            wrapper.eq("cost_center", costCenter)
                    .eq("id", id);
        }
        FinanceCostCostcenter one = financeCostCostcenterService.getOne(wrapper);
        return AjaxResult.success(one);
    }
    /**
     * 详情查询
     */
    @Operation(summary = "根据成本中心名称")
    @GetMapping("/queryLike")
    @Log(title = "成本中心名称详情查询", businessType = BusinessType.OTHER)
    public Object queryLike( FinanceCostCostcenter financeCostCostcenter) {
        LambdaQueryWrapper<FinanceCostCostcenter> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.like(StringUtils.isNotBlank(financeCostCostcenter.getCostCenterDesc()), FinanceCostCostcenter::getCostCenterDesc, financeCostCostcenter.getCostCenterDesc());
        List<FinanceCostCostcenter> list = financeCostCostcenterService.list(queryWrapper);
        return AjaxResult.success(list);
    }
    /**
     * 属性查询
     */
    @SuppressWarnings("SuspiciousMethodCalls")
    @Operation(summary = "属性查询")
    @GetMapping("/attributeQuery")
    @Log(title = "属性详情查询", businessType = BusinessType.OTHER)
    public Object attributeQuery(FinanceCostDTO financeCostDTO) {
        String parentId = financeCostDTO.getId();
        LambdaQueryWrapper<FinanceCostCostcenterAttri> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotBlank(parentId), FinanceCostCostcenterAttri::getParentId, parentId);
        List<FinanceCostCostcenterAttri> list = financeCostCostcenterAttriService.list(queryWrapper);
        List<FinanceCostDTO> list1 = new ArrayList<>();
        for (FinanceCostCostcenterAttri attr : list) {
            FinanceCostDTO costdto = new FinanceCostDTO();
            String attriValue;
            if (StringUtils.isNotBlank(attr.getAttriValue())&& "CCType".equals(attr.getAttriName())) {
                String str = attr.getAttriValue();
                String[] strs = str.split("-");
                attr.setAttriValue(strs[0]);
                attriValue = attr.getAttriValue();
                String accountItemAttriName = "cost_CCType";
                Map<String, Object> labelByDictValue = sysDictDataFeignService.getLabelByDictValue(accountItemAttriName, attriValue);
                Set<String> set = labelByDictValue.keySet();
                String idKindNoMap = "";
                for (Object obj : set) {
                    idKindNoMap = "" + labelByDictValue.get(obj);
                }
                attr.setAttriValue(idKindNoMap);
                costdto.setAttriValue(idKindNoMap);
            } else {
                costdto.setAttriValue("");
            }
            list1.add(costdto);
            BeanUtils.copyProperties(attr, costdto);
            costdto.setValue(attr.getAttriName());
            costdto.setLabel(attr.getAttriDesc());
            list1.add(costdto);
        }
        return AjaxResult.success(list1);
    }


    /**
     * 属性弹窗查询
     */
    @Operation(summary = "属性弹窗查询")
    @GetMapping("/attributeQueryOne")
    @Log(title = "属性弹窗详情查询", businessType = BusinessType.OTHER)
    public Object attributeQueryOne(@RequestParam String attriName) {
        QueryWrapper<FinanceCostCostcenterAttri> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(attriName)) {
            wrapper.eq("attri_name", attriName);
        }
        FinanceCostCostcenterAttri one = financeCostCostcenterAttriService.getOne(wrapper);
        return AjaxResult.success(one);
    }

    /**
     * 成本中心新增
     */
    @Operation(summary = "成本中心新增")
    @PostMapping("/doAdd")
    @Log(title = "成本中心新增", businessType = BusinessType.INSERT)
    public Object addFinanceCost(@RequestBody FinanceCostDTO financeCostDTO) {
        try {
            QueryWrapper<FinanceCostCostcenter> wrapper = new QueryWrapper<>();
            FinanceCostCostcenter financeCostCostcenter = new FinanceCostCostcenter();
            if (StringUtils.isNotBlank(financeCostDTO.getCcType())) {
                String str = financeCostDTO.getCcType();
                String[] strs = str.split("_");
                financeCostDTO.setCcType(strs[0]);
            }
            if (StringUtils.isNotBlank(financeCostDTO.getIsProduct())) {
                String str = financeCostDTO.getIsProduct();
                String[] strs = str.split("_");
                financeCostDTO.setIsProduct(strs[0]);
            }
            if (StringUtils.isNotBlank(financeCostDTO.getStusCode())) {
                String str = financeCostDTO.getStusCode();
                String[] strs = str.split("_");
                financeCostDTO.setStusCode(strs[0]);
            }
            if (StringUtils.isNotBlank(financeCostDTO.getIsPostAc())) {
                String str = financeCostDTO.getIsPostAc();
                String[] strs = str.split("_");
                financeCostDTO.setIsPostAc(strs[0]);
            }
            BeanUtils.copyProperties(financeCostDTO, financeCostCostcenter);
            String fiscalYear = financeCostDTO.getFiscalYear();
            String compIdIdValue = "finance_compId";
            List<Map<String, Object>> compIdValue = sysDictDataFeignService.getMainCompIdValue(compIdIdValue);
            Map<String, Object> compIdMap = compIdValue.get(0);
            String compId = null;
            for (String key : compIdMap.keySet()) {
                compId = (String) compIdMap.get(key);
            }
            financeCostCostcenter.setCompId(compId);
            String costCenter = financeCostDTO.getCostCenter();
            wrapper.eq("fiscal_year", fiscalYear)
                    .eq("comp_id", compId)
                    .eq("cost_center", costCenter);
            List<FinanceCostCostcenter> list = financeCostCostcenterService.list(wrapper);
            if (list.size() >= 1) {
                return AjaxResult.error("您输入的成本中心系统中已存在，请重新输入！");
            }
            System.out.println(financeCostCostcenter);
            boolean result = financeCostCostcenterService.save(financeCostCostcenter);
            if (result) {
                Map<String, String> resultMap = new HashMap<>(16);
                resultMap.put("id", financeCostCostcenter.getId());
                resultMap.put("create_user", financeCostCostcenter.getCreateUser());
                resultMap.put("create_time", financeCostCostcenter.getCreateTime().toString());
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
    public Object attriAdd(@RequestBody FinanceCostDTO financeCostDTO) {
        try {
            List<FinanceCostCostcenterAttriDTO> attrlist = financeCostDTO.getPropertydata();
            FinanceCostCostcenterAttri financeCostCostcenterAttri = new FinanceCostCostcenterAttri();
            for (FinanceCostCostcenterAttriDTO attr : attrlist) {
                if (StringUtils.isNotBlank(attr.getAttriValue())) {
                    String str = attr.getAttriValue();
                    String[] strs = str.split("_");
                    attr.setAttriValue(strs[0]);
                }
                BeanUtils.copyProperties(attr, financeCostCostcenterAttri);
                financeCostCostcenterAttri.setParentId(financeCostDTO.getParentId());
                financeCostCostcenterAttri.setAttriName(attr.getValue());
                financeCostCostcenterAttri.setAttriDesc(attr.getLabel());
                financeCostCostcenterAttri.setId("");
                financeCostCostcenterAttriService.save(financeCostCostcenterAttri);

            }
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error();
        }
        return null;
    }


    /**
     * 修改成本中心
     *
     */
    @Operation(summary = "修改成本中心")
    @PostMapping("/doEdit")
    @Log(title = "成本中心修改", businessType = BusinessType.UPDATE)
    public Object updateEnergyCode(@RequestBody FinanceCostDTO financeCostDTO) {
        try {
            FinanceCostCostcenter financeCostCostcenter = new FinanceCostCostcenter();
            if (StringUtils.isNotBlank(financeCostDTO.getCcType())) {
                String str = financeCostDTO.getCcType();
                String[] strs = str.split("_");
                financeCostDTO.setCcType(strs[0]);
            } else {
                financeCostDTO.setCcType("");
            }
            if (StringUtils.isNotBlank(financeCostDTO.getIsProduct())) {
                String str = financeCostDTO.getIsProduct();
                String[] strs = str.split("_");
                financeCostDTO.setIsProduct(strs[0]);
            } else {
                financeCostDTO.setIsProduct("");
            }
            if (StringUtils.isNotBlank(financeCostDTO.getStusCode())) {
                String str = financeCostDTO.getStusCode();
                String[] strs = str.split("_");
                financeCostDTO.setStusCode(strs[0]);
            } else {
                financeCostDTO.setStusCode("");
            }
            if (StringUtils.isNotBlank(financeCostDTO.getIsPostAc())) {
                String str = financeCostDTO.getIsPostAc();
                String[] strs = str.split("_");
                financeCostDTO.setIsPostAc(strs[0]);
            } else {
                financeCostDTO.setIsPostAc("");
            }
            BeanUtils.copyProperties(financeCostDTO, financeCostCostcenter);
            LambdaQueryWrapper<FinanceCostCostcenter> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(FinanceCostCostcenter::getId, financeCostCostcenter.getId());
            Map<String, Object> map = financeCostCostcenterService.getMap(queryWrapper);
            String id = "id";
            if (map.get(id).equals(financeCostCostcenter.getId())) {
                boolean result = financeCostCostcenterService.updateById(financeCostCostcenter);
                if (result) {
                    Map<String, String> resultMap = new HashMap<>(16);
                    resultMap.put("id", financeCostCostcenter.getId());
                    resultMap.put("update_user", financeCostCostcenter.getUpdateUser());
                    List<Map<String, String>> resultList = new ArrayList<>();
                    resultList.add(resultMap);
                    return AjaxResult.success("修改成功", resultList);
                } else {
                    return AjaxResult.error("修改失败，请重新提交");
                }
            } else {
                return AjaxResult.error("成本中心，不允许修改");
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
    public Object updateFinanceattri(@RequestBody FinanceCostDTO financeCostDTO) {
        try {
            List<FinanceCostCostcenterAttriDTO> attrlist = financeCostDTO.getPropertydata();

            FinanceCostCostcenterAttri financeCostCostcenterAttri = new FinanceCostCostcenterAttri();
            List<Map<String, String>> resultList = new ArrayList<>();
            for (FinanceCostCostcenterAttriDTO attr : attrlist) {
                if (StringUtils.isNotBlank(attr.getAttriValue())) {
                    String str = attr.getAttriValue();
                    String[] strs = str.split("_");
                    attr.setAttriValue(strs[0]);
                }
                BeanUtils.copyProperties(attr, financeCostCostcenterAttri);
                LambdaUpdateWrapper<FinanceCostCostcenterAttri> updateWrapper = new LambdaUpdateWrapper<>();
                updateWrapper.set(FinanceCostCostcenterAttri::getAttriValue, attr.getAttriValue())
                        .eq(FinanceCostCostcenterAttri::getId, attr.getId());
                if (attr.getParentId().isEmpty()) {
                    financeCostCostcenterAttri.setParentId(financeCostDTO.getParentId());
                    financeCostCostcenterAttri.setAttriName(attr.getValue());
                    financeCostCostcenterAttri.setAttriDesc(attr.getLabel());
                    financeCostCostcenterAttri.setId("");
                    financeCostCostcenterAttriService.save(financeCostCostcenterAttri);
                }
                boolean result = financeCostCostcenterAttriService.update(updateWrapper);
                if (result) {
                    Map<String, String> resultMap = new HashMap<>(16);
                    resultMap.put("id", financeCostCostcenterAttri.getId());
                    resultMap.put("update_user", financeCostCostcenterAttri.getUpdateUser());
                    resultList.add(resultMap);
                }
            }
            return AjaxResult.success("修改成功", resultList);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error();
        }
    }


    /**
     * 删除成本中心资料
     *
     */
    @Operation(summary = "删除成本中心资料")
    @DeleteMapping("/delete")
    @Log(title = "成本中心资料删除", businessType = BusinessType.DELETE)
    public Object deleteEnergyCode(@RequestParam String id) {
        try {
            FinanceCostCostcenterAttri financeCostCostcenterAttri = new FinanceCostCostcenterAttri();
            financeCostCostcenterAttri.setParentId(id);
            QueryWrapper<FinanceCostCostcenterAttri> wrapper = new QueryWrapper<>();
            String parentId = financeCostCostcenterAttri.getParentId();
            wrapper.eq("parent_id", parentId);
            List<FinanceCostCostcenterAttri> list = financeCostCostcenterAttriService.list(wrapper);
            List<FinanceCostCostcenterAttri> list1 = new ArrayList<>();
            String accountItemAttriValue;
            for (FinanceCostCostcenterAttri attri:list){
                accountItemAttriValue = attri.getAttriValue();
                if(StringUtils.isNotBlank(accountItemAttriValue)){
                    list1.add(attri);
                }
            }
            if (list1.size()>=1) {
                return AjaxResult.error("删除产副品资料系统中有属性资料，请先删除属性资料！");
            }else {
                boolean result = financeCostCostcenterService.removeById(id);
                if (result) {
                    Map<String, String> resultMap = new HashMap<>(16);
                    resultMap.put("id", financeCostCostcenterAttri.getId());
                    resultMap.put("update_user", financeCostCostcenterAttri.getUpdateUser());
                    List<Map<String, String>> resultList = new ArrayList<>();
                    resultList.add(resultMap);
                    return AjaxResult.success("删除成功", resultList);
                } else {
                    return AjaxResult.error("删除失败，请重新提交");
                }
            }
        } catch (Exception e) {
            return AjaxResult.error();
        }
    }

    /**
     * 删除删除属性资料
     *
     */
    @Operation(summary = "删除属性资料")
    @DeleteMapping("/delAttribute")
    @Log(title = "属性资料删除", businessType = BusinessType.DELETE)
    public Object delAttribute(@RequestParam String attriValue, String value) {
        try {
            FinanceCostCostcenterAttri financeCostCostcenterAttri = new FinanceCostCostcenterAttri();
            QueryWrapper<FinanceCostCostcenterAttri> wrapper = new QueryWrapper<>();
            wrapper.eq("attri_value", attriValue)
                    .eq("attri_name", value);
            boolean result = financeCostCostcenterAttriService.remove(wrapper);
            if (result) {
                Map<String, String> resultMap = new HashMap<>(16);
                resultMap.put("id", financeCostCostcenterAttri.getId());
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
