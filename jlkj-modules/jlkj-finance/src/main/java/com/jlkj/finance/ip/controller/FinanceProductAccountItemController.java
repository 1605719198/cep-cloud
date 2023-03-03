package com.jlkj.finance.ip.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.finance.feign.system.SysDictDataFeignService;
import com.jlkj.finance.ip.domain.FinanceProductAccountItem;
import com.jlkj.finance.ip.domain.FinanceProductAccountItemAttri;
import com.jlkj.finance.ip.domain.FinanceProductSettingDetail;
import com.jlkj.finance.ip.dto.FinanceAccountDTO;
import com.jlkj.finance.ip.dto.FinanceProductAccountItemAttriDTO;
import com.jlkj.finance.ip.dto.FinanceProductAccountItemDTO;
import com.jlkj.finance.ip.service.FinanceProductAccountItemAttriService;
import com.jlkj.finance.ip.service.FinanceProductAccountItemService;
import com.jlkj.finance.ip.service.FinanceProductSettingDetailService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author wang'mai
 */
@RestController
@RequestMapping("/product/accountItemCode")
public class FinanceProductAccountItemController {
    @Autowired
    private FinanceProductAccountItemAttriService financeProductAccountItemAttriService;
    @Autowired
    private FinanceProductAccountItemService financeProductAccountItemService;
    @Autowired
    private FinanceProductSettingDetailService financeProductSettingDetailService;
    @Autowired
    private SysDictDataFeignService sysDictDataFeignService;

    /**
     * 根据帐务代码、帐务名称查询
     */
    @Operation(summary = "根据帐务代码、帐务名称查询")
    @GetMapping("/queryAll")
    @Log(title = "帐务代码、帐务名称查询", businessType = BusinessType.OTHER)
    public Object queryAll(FinanceProductAccountItemDTO financeProductAccountItemDTO) {
        try {
            String accountItemCode = financeProductAccountItemDTO.getAccountItemCode();
            String accountItemName = financeProductAccountItemDTO.getAccountItemName();
            Long pageNum = financeProductAccountItemDTO.getPageNum();
            Long pageSize = financeProductAccountItemDTO.getPageSize();
            LambdaQueryWrapper<FinanceProductAccountItem> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.like(StringUtils.isNotBlank(accountItemCode), FinanceProductAccountItem::getAccountItemCode, accountItemCode)
                    .like(StringUtils.isNotBlank(accountItemName), FinanceProductAccountItem::getAccountItemName, accountItemName);
            Page<FinanceProductAccountItem> page = financeProductAccountItemService.page(new Page<>(pageNum, pageSize), queryWrapper);
            long total = page.getTotal();
            //数据list集合
            List<FinanceProductAccountItem> records = page.getRecords();
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
     * 详情查询
     */
    @SuppressWarnings("SuspiciousMethodCalls")
    @Operation(summary = "根据帐务代码")
    @GetMapping("/queryOne")
    @Log(title = "帐务代码详情查询", businessType = BusinessType.OTHER)
    public Object queryOne(@RequestParam String accountItemCode, String id) {
        QueryWrapper<FinanceProductAccountItem> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(accountItemCode)) {
            wrapper.eq("account_item_code", accountItemCode)
                    .eq("id", id);
        }
        FinanceProductAccountItem one = financeProductAccountItemService.getOne(wrapper);
        LambdaQueryWrapper<FinanceProductAccountItemAttri> wrapperAttri = new LambdaQueryWrapper<>();
        wrapperAttri.eq(StringUtils.isNotBlank(accountItemCode), FinanceProductAccountItemAttri::getAccountItemCode, accountItemCode);
        List<FinanceProductAccountItemAttri> resultList = financeProductAccountItemAttriService.list(wrapperAttri);
        FinanceProductSettingDetail financeProductSettingDetail;
        List<FinanceProductAccountItemAttri> resultList1 = new ArrayList<>();
        for (FinanceProductAccountItemAttri attr : resultList) {
            String accountItemAttriValue;
            String accountItemAttriName;
            if (StringUtils.isNotBlank(attr.getAccountItemAttriValue())) {
                String str = attr.getAccountItemAttriValue();
                String[] strs = str.split("_");
                attr.setAccountItemAttriValue(strs[0]);
                if ("ISCONSUMPTION".equals(attr.getAccountItemAttriName())) {
                    accountItemAttriName = "sys_yes_no";
                } else if(!"MCCRPTITEM".equals(attr.getAccountItemAttriName())){
                    accountItemAttriName = "cost_" + attr.getAccountItemAttriName();
                }else {
                    accountItemAttriName =  attr.getAccountItemAttriName();
                }
                accountItemAttriValue = attr.getAccountItemAttriValue();

                if ("MCCRPTITEM".equals(accountItemAttriName) && StringUtils.isNotBlank(accountItemAttriValue)) {
                    String compIdIdValue = "finance_compId";
                    List<Map<String, Object>> compIdValue = sysDictDataFeignService.getMainCompIdValue(compIdIdValue);
                    Map<String, Object> compIdMap = compIdValue.get(0);
                    String compId = null;
                    for (String key : compIdMap.keySet()) {
                        compId = (String) compIdMap.get(key);
                    }
                    String leafId = "01010802";
                    LambdaQueryWrapper<FinanceProductSettingDetail> queryWrapper = new LambdaQueryWrapper<>();
                    queryWrapper.eq(StringUtils.isNotBlank(compId), FinanceProductSettingDetail::getCompId, compId)
                            .eq(StringUtils.isNotBlank(leafId), FinanceProductSettingDetail::getLeafId, leafId)
                            .eq(StringUtils.isNotBlank(accountItemAttriValue), FinanceProductSettingDetail::getItemA, accountItemAttriValue);
                    financeProductSettingDetail = financeProductSettingDetailService.getOne(queryWrapper);
                    String value = financeProductSettingDetail.getItemA() + '_' + financeProductSettingDetail.getDataA();
                    attr.setAccountItemAttriValue(value);
                    resultList1.add(attr);
                } else if ("MCCRPTITEM".equals(accountItemAttriName) && StringUtils.isEmpty(accountItemAttriValue)) {
                    attr.setAccountItemAttriValue("");
                    resultList1.add(attr);
                } else if ("cost_IO_TYPE".equals(accountItemAttriName) || "sys_yes_no".equals(accountItemAttriName) || "cost_MCCITEMSIG".equals(accountItemAttriName)) {
                    Map<String, Object> labelByDictValue = sysDictDataFeignService.getLabelByDictValue(accountItemAttriName, accountItemAttriValue);
                    Set<String> set = labelByDictValue.keySet();
                    String idKindNoMap = "";
                    for (Object obj : set) {
                        idKindNoMap = "" + labelByDictValue.get(obj);
                    }
                    attr.setAccountItemAttriValue(idKindNoMap);
                    resultList1.add(attr);
                }
            } else {
                attr.setAccountItemAttriValue("");
                resultList1.add(attr);
            }
        }

        Map<String, Object> dataMap = new HashMap<>(16);
        dataMap.put("one", one);
        dataMap.put("resultList1", resultList1);
        return AjaxResult.success("查询成功！", dataMap);
    }

    /**
     * 帐务代码新增
     */
    @Operation(summary = "帐务代码新增")
    @PostMapping("/doAdd")
    @Log(title = "帐务代码新增", businessType = BusinessType.INSERT)
    public Object addFinanceCost(@RequestBody FinanceProductAccountItemDTO financeProductAccountItemDTO) {
        try {
            QueryWrapper<FinanceProductAccountItem> wrapper = new QueryWrapper<>();
            FinanceProductAccountItem financeProductAccountItem = new FinanceProductAccountItem();
            BeanUtils.copyProperties(financeProductAccountItemDTO, financeProductAccountItem);
            String accountItemCode = financeProductAccountItemDTO.getAccountItemCode();
            wrapper.eq("account_item_code", accountItemCode);
            List<FinanceProductAccountItem> list = financeProductAccountItemService.list(wrapper);
            if (list.size() >= 1) {
                return AjaxResult.error("您输入的帐务代码系统中已存在，请重新输入！");
            }
            boolean result = financeProductAccountItemService.save(financeProductAccountItem);
            if (result) {
                Map<String, String> resultMap = new HashMap<>(16);
                resultMap.put("id", financeProductAccountItem.getId());
                resultMap.put("compId", financeProductAccountItem.getCompId());
                resultMap.put("accountItemCode", financeProductAccountItem.getAccountItemCode());
                resultMap.put("create_user", financeProductAccountItem.getCreateUser());
                resultMap.put("create_date", financeProductAccountItem.getCreateDate());
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
     * 修改帐务代码
     */
    @Operation(summary = "修改帐务代码")
    @PostMapping("/doEdit")
    @Log(title = "帐务代码修改", businessType = BusinessType.UPDATE)
    public Object updateEnergyCode(@RequestBody FinanceProductAccountItemDTO financeProductAccountItemDTO) {
        try {
            FinanceProductAccountItem financeProductAccountItem = new FinanceProductAccountItem();
            BeanUtils.copyProperties(financeProductAccountItemDTO, financeProductAccountItem);
            boolean result = financeProductAccountItemService.updateById(financeProductAccountItem);
            if (result) {
                Map<String, String> resultMap = new HashMap<>(16);
                resultMap.put("id", financeProductAccountItem.getId());
                resultMap.put("accountItemCode", financeProductAccountItem.getAccountItemCode());
                resultMap.put("update_user", financeProductAccountItem.getUpdateUser());
                resultMap.put("create_date", financeProductAccountItem.getCreateDate());
                List<Map<String, String>> resultList = new ArrayList<>();
                resultList.add(resultMap);
                return AjaxResult.success("修改成功", resultList);
            } else {
                return AjaxResult.error("修改失败，请重新提交");
            }

        } catch (Exception e) {
            return AjaxResult.error();
        }
    }

    /**
     * 删除帐务代码
     */
    @Operation(summary = "删除帐务代码")
    @DeleteMapping("/delete")
    @Log(title = "帐务代码删除", businessType = BusinessType.DELETE)
    public Object deleteEnergyCode(@RequestParam String id) {
        try {
            FinanceProductAccountItem financeProductAccountItem = new FinanceProductAccountItem();
            QueryWrapper<FinanceProductAccountItem> wrapper = new QueryWrapper<>();
            wrapper.eq("id", id);
            FinanceProductAccountItem one = financeProductAccountItemService.getOne(wrapper);
            String accountItemCode = one.getAccountItemCode();
            QueryWrapper<FinanceProductAccountItemAttri> wrapperAccountItemAttri = new QueryWrapper<>();
            wrapperAccountItemAttri.eq("account_item_code", accountItemCode);
            List<FinanceProductAccountItemAttri> list = financeProductAccountItemAttriService.list(wrapperAccountItemAttri);
            List<FinanceProductAccountItemAttri> list1 = new ArrayList<>();
            String accountItemAttriValue;
            for (FinanceProductAccountItemAttri attri : list) {
                accountItemAttriValue = attri.getAccountItemAttriValue();
                if (StringUtils.isNotBlank(accountItemAttriValue)) {
                    list1.add(attri);
                }
            }

            if (list1.size() >= 1) {
                return AjaxResult.error("您删除的帐务代码系统中有属性资料，请先删除属性资料！");
            } else {
                boolean result = financeProductAccountItemService.removeById(id);
                if (result) {
                    Map<String, String> resultMap = new HashMap<>(16);
                    resultMap.put("id", financeProductAccountItem.getId());
                    resultMap.put("update_user", financeProductAccountItem.getUpdateUser());
                    resultMap.put("create_date", financeProductAccountItem.getCreateDate());
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
            LambdaQueryWrapper<FinanceProductSettingDetail> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(StringUtils.isNotBlank(compId), FinanceProductSettingDetail::getCompId, compId)
                    .eq(StringUtils.isNotBlank(leafId), FinanceProductSettingDetail::getLeafId, leafId);
            List<FinanceProductSettingDetail> list = financeProductSettingDetailService.list(queryWrapper);
            //数据list集合
            Map<String, Object> dataMap = new HashMap<>(16);
            dataMap.put("list", list);
            if (list.isEmpty()) {
                return AjaxResult.error("查无资料");
            } else {
                return AjaxResult.success("查询成功！", dataMap);
            }
        } catch (Exception e) {
            return AjaxResult.error();
        }
    }

    /**
     * 查询财务资料的下拉选单
     */
    @Operation(summary = "查询财务资料的下拉选单")
    @GetMapping("/queryAccountMcc")
    @Log(title = "财务资料的下拉选单查询", businessType = BusinessType.OTHER)
    public Object queryAccountMcc(@RequestParam String compIdMcc, String leafIdMcc) {
        try {
            LambdaQueryWrapper<FinanceProductSettingDetail> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(StringUtils.isNotBlank(compIdMcc), FinanceProductSettingDetail::getCompId, compIdMcc)
                    .eq(StringUtils.isNotBlank(leafIdMcc), FinanceProductSettingDetail::getLeafId, leafIdMcc);
            List<FinanceProductSettingDetail> list = financeProductSettingDetailService.list(queryWrapper);
            //数据list集合
            Map<String, Object> dataMap = new HashMap<>(16);
            dataMap.put("list", list);
            if (list.isEmpty()) {
                return AjaxResult.error("查无资料");
            } else {
                return AjaxResult.success("查询成功！", dataMap);
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
    public Object attriAdd(@RequestBody FinanceProductAccountItemAttriDTO financeProductAccountItemAttriDTO) {
        try {
            List<FinanceProductAccountItemAttriDTO> attrlist = financeProductAccountItemAttriDTO.getPropertydata();
            FinanceProductAccountItemAttri financeProductAccountItemAttri = new FinanceProductAccountItemAttri();
            for (FinanceProductAccountItemAttriDTO attr : attrlist) {
                if (StringUtils.isNotBlank(attr.getAccountItemAttriValue())) {
                    String str = attr.getAccountItemAttriValue();
                    String[] strs = str.split("_");
                    attr.setAccountItemAttriValue(strs[0]);
                }
                BeanUtils.copyProperties(attr, financeProductAccountItemAttri);
                financeProductAccountItemAttri.setAccountItemCode(financeProductAccountItemAttriDTO.getAccountItemCode());
                financeProductAccountItemAttri.setAccountItemAttriValue(attr.getAccountItemAttriValue());
                financeProductAccountItemAttri.setAccountItemAttriName(attr.getAccountItemAttriName());
                financeProductAccountItemAttri.setId("");
                financeProductAccountItemAttriService.save(financeProductAccountItemAttri);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error();
        }
        return null;
    }

    /**
     * 属性修改
     */
    @Operation(summary = "属性修改")
    @PostMapping("/doEditAttri")
    @Log(title = "属性修改", businessType = BusinessType.UPDATE)
    public Object updateFinanceAttri(@RequestBody FinanceProductAccountItemAttriDTO financeProductAccountItemAttriDTO) {
        try {
            List<FinanceProductAccountItemAttriDTO> attrlist = financeProductAccountItemAttriDTO.getPropertydata();
            FinanceProductAccountItemAttri financeProductAccountItemAttri = new FinanceProductAccountItemAttri();
            boolean result = false;
            for (FinanceProductAccountItemAttriDTO attr : attrlist) {
                if (StringUtils.isNotBlank(attr.getAccountItemAttriValue())) {
                    String str = attr.getAccountItemAttriValue();
                    String[] strs = str.split("_");
                    attr.setAccountItemAttriValue(strs[0]);
                }
                BeanUtils.copyProperties(attr, financeProductAccountItemAttri);
                LambdaUpdateWrapper<FinanceProductAccountItemAttri> updateWrapper = new LambdaUpdateWrapper<>();
                updateWrapper.set(FinanceProductAccountItemAttri::getAccountItemAttriValue, attr.getAccountItemAttriValue())
                        .eq(FinanceProductAccountItemAttri::getAccountItemAttriName, attr.getAccountItemAttriName())
                        .eq(FinanceProductAccountItemAttri::getAccountItemCode, financeProductAccountItemAttriDTO.getAccountItemCode());
                result = financeProductAccountItemAttriService.update(updateWrapper);
            }
            if (result) {
                Map<String, String> resultMap = new HashMap<>(16);
                resultMap.put("id", financeProductAccountItemAttri.getId());
                resultMap.put("update_user", financeProductAccountItemAttri.getUpdateUser());
                List<Map<String, String>> resultList = new ArrayList<>();
                resultList.add(resultMap);
                return AjaxResult.success("修改成功", resultList);
            } else {
                return AjaxResult.error("修改失败，请重新提交");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error();
        }

    }


}
