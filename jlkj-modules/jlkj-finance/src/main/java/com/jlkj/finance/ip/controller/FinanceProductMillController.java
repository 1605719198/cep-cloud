package com.jlkj.finance.ip.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.finance.ip.domain.*;
import com.jlkj.finance.ip.dto.FinanceProductMillDTO;
import com.jlkj.finance.ip.service.*;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 智能研发室 黄兵
 * @Description 产线资料设定controller
 * @date 2022/5/31 - 15:36
 */
@RestController
@RequestMapping("/product/mill")
public class FinanceProductMillController {

    @Autowired
    FinanceProductMillService financeProductMillService;

    @Autowired
    FinanceProductMillAttriService financeProductMillAttriService;

    @Autowired
    FinanceProductMillSystemService financeProductMillSystemService;

    @Autowired
    FinanceProductMillActivityService financeProductMillActivityService;

    @Autowired
    FinanceProductMillProductService financeProductMillProductService;

    @Autowired
    FinanceProductSettingDetailService financeProductSettingDetailService;
    @Autowired
    FinanceProductSettingDetailServicea financeProductSettingDetailServicea;

    /**
     * 产线清单查询
     */
    @Operation(summary = "产线清单查询")
    @PostMapping("/doQueryAll")
    @Log(title = "产线清单查询", businessType = BusinessType.OTHER)
    public Object doQueryAllFinanceProductMill(@RequestBody FinanceProductMillDTO financeProductMillDTO) {
        try {
            String millId = financeProductMillDTO.getMillId();
            String millName = financeProductMillDTO.getMillName();
            Long pageNum = financeProductMillDTO.getPageNum();
            Long pageSize = financeProductMillDTO.getPageSize();
            Page<FinanceProductMill> page = new Page<>(pageNum, pageSize);
            LambdaQueryWrapper<FinanceProductMill> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.likeRight(StringUtils.isNotBlank(millId), FinanceProductMill::getMillId, millId)
                        .likeRight(StringUtils.isNotBlank(millName), FinanceProductMill::getMillName, millName);
            Page<FinanceProductMill> millPage = financeProductMillService.page(page, queryWrapper);
            //总记录数
            long total = millPage.getTotal();
            //数据list集合
            List<FinanceProductMill> records = millPage.getRecords();
            Map<String,Object> dataMap = new HashMap<>(16);
            dataMap.put("total",total);
            dataMap.put("list",records);
            if (records.isEmpty()){
                return AjaxResult.success("查无资料", dataMap);
            } else {
                return AjaxResult.success("查询成功", dataMap);
            }
        } catch (Exception e) {
            return AjaxResult.error();
        }
    }

    /**
     * 产线资料新增
     */
    @Operation(summary = "产线资料新增")
    @PostMapping("/doAdd")
    @Log(title = "产线资料新增", businessType = BusinessType.INSERT)
    public Object doAddFinanceProductMill(@RequestBody FinanceProductMillDTO financeProductMillDTO) {
        try {
            String millId = financeProductMillDTO.getMillId();
            String millName = financeProductMillDTO.getMillName();
            FinanceProductMill financeProductMill = new FinanceProductMill();
            BeanUtils.copyProperties(financeProductMillDTO, financeProductMill);
            List<FinanceProductMillAttri> attriList = financeProductMillDTO.getAttriList();
            List<FinanceProductMillSystem> systemList = financeProductMillDTO.getSystemList();
            List<FinanceProductMillActivity> activityList = financeProductMillDTO.getActivityList();
            List<FinanceProductMillProduct> productList = financeProductMillDTO.getProductList();
            List<FinanceProductMill> millList = financeProductMillService
                    .lambdaQuery()
                    .eq(FinanceProductMill::getMillId, millId)
                    .eq(FinanceProductMill::getMillName, millName)
                    .list();
            if (!millList.isEmpty()) {
                return AjaxResult.error("该产线资料已存在，请重新输入");
            }
            boolean result = financeProductMillService.save(financeProductMill);
            financeProductMillAttriService.saveBatch(attriList);
            financeProductMillSystemService.saveBatch(systemList);
            financeProductMillActivityService.saveBatch(activityList);
            financeProductMillProductService.saveBatch(productList);
            if (result) {
                return AjaxResult.success("新增成功");
            } else {
                return AjaxResult.error("新增失败，请重新提交！");
            }
        } catch (Exception e) {
            return AjaxResult.error();
        }
    }

    /**
     * 产线属性资料添加
     */
    @Operation(summary = "产线属性资料添加")
    @PostMapping("/doAttriAdd")
    @Log(title = "产线属性资料添加", businessType = BusinessType.INSERT)
    public Object doAttriAdd(@RequestBody FinanceProductMillAttri financeProductMillAttri) {
        try {
            boolean result = financeProductMillAttriService.save(financeProductMillAttri);
            if (result) {
                return AjaxResult.success("新增成功");
            } else {
                return AjaxResult.error("新增失败，请重新提交！");
            }
        } catch (Exception e) {
            return AjaxResult.error();
        }
    }

    /**
     * 产线相关系统资料添加
     */
    @Operation(summary = "产线相关系统资料添加")
    @PostMapping("/doSystemAdd")
    @Log(title = "产线相关系统资料添加", businessType = BusinessType.INSERT)
    public Object doSystemAdd(@RequestBody FinanceProductMillSystem financeProductMillSystem) {
        try {
            String millId = financeProductMillSystem.getMillId();
            String sysId = financeProductMillSystem.getSysId();
            LambdaQueryWrapper<FinanceProductMillSystem> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(FinanceProductMillSystem::getMillId, millId)
                        .eq(FinanceProductMillSystem::getSysId, sysId);
            List<FinanceProductMillSystem> list = financeProductMillSystemService.list(queryWrapper);
            if (!list.isEmpty()) {
                return AjaxResult.error("产线代码：" + millId + "和系统代码：" + sysId + "已存在");
            }
            boolean result = financeProductMillSystemService.save(financeProductMillSystem);
            if (result) {
                return AjaxResult.success("新增成功");
            } else {
                return AjaxResult.error("新增失败，请重新提交！");
            }
        } catch (Exception e) {
            return AjaxResult.error();
        }
    }

    /**
     * 产线相关活动资料添加
     */
    @Operation(summary = "产线相关活动资料添加")
    @PostMapping("/doActivityAdd")
    @Log(title = "产线相关活动资料添加", businessType = BusinessType.INSERT)
    public Object doActivityAdd(@RequestBody FinanceProductMillActivity financeProductMillActivity) {
        try {
            String millId = financeProductMillActivity.getMillId();
            String sysId = financeProductMillActivity.getSysId();
            LambdaQueryWrapper<FinanceProductMillActivity> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(FinanceProductMillActivity::getMillId, millId)
                        .eq(FinanceProductMillActivity::getSysId, sysId);
            List<FinanceProductMillActivity> list = financeProductMillActivityService.list(queryWrapper);
            if (!list.isEmpty()) {
                return AjaxResult.error("产线代码：" + millId + "和系统代码：" + sysId + "已存在");
            }
            boolean result = financeProductMillActivityService.save(financeProductMillActivity);
            if (result) {
                return AjaxResult.success("新增成功");
            } else {
                return AjaxResult.error("新增失败，请重新提交！");
            }
        } catch (Exception e) {
            return AjaxResult.error();
        }
    }

    /**
     * 产线产出产副品资料添加
     */
    @Operation(summary = "产线产出产副品资料添加")
    @PostMapping("/doProductAdd")
    @Log(title = "产线产出产副品资料添加", businessType = BusinessType.INSERT)
    public Object doProductAdd(@RequestBody FinanceProductMillProduct financeProductMillProduct) {
        try {
            String millId = financeProductMillProduct.getMillId();
            String productCode = financeProductMillProduct.getProductCode();
            LambdaQueryWrapper<FinanceProductMillProduct> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(FinanceProductMillProduct::getMillId, millId)
                        .eq(FinanceProductMillProduct::getProductCode, productCode);
            List<FinanceProductMillProduct> list = financeProductMillProductService.list(queryWrapper);
            if (!list.isEmpty()) {
                return AjaxResult.error("产线代码：" + millId + "和产副品代码：" + productCode + "已存在");
            }
            boolean result = financeProductMillProductService.save(financeProductMillProduct);
            if (result) {
                return AjaxResult.success("新增成功");
            } else {
                return AjaxResult.error("新增失败，请重新提交！");
            }
        } catch (Exception e) {
            return AjaxResult.error();
        }
    }

    /**
     * 产线资料修改
     */
    @Operation(summary = "产线资料修改")
    @PostMapping("/doEdit")
    @Log(title = "产线资料修改", businessType = BusinessType.UPDATE)
    public Object doEditFinanceProductMill(@RequestBody FinanceProductMillDTO financeProductMillDTO) {
        try {
            String millId = financeProductMillDTO.getMillId();
            List<FinanceProductMillAttri> attriList = financeProductMillDTO.getAttriList();
            FinanceProductMill financeProductMill = new FinanceProductMill();
            FinanceProductMillAttri financeProductMillAttri = new FinanceProductMillAttri();
            BeanUtils.copyProperties(financeProductMillDTO, financeProductMill);
            BeanUtils.copyProperties(financeProductMillDTO, financeProductMillAttri);
            LambdaUpdateWrapper<FinanceProductMill> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(FinanceProductMill::getMillId, millId);
            boolean result = financeProductMillService.update(financeProductMill, updateWrapper);
            financeProductMillAttriService.saveOrUpdateBatch(attriList);
            if (result) {
                return AjaxResult.success("修改成功");
            } else {
                return AjaxResult.error("修改失败，请重新提交！");
            }
        } catch (Exception e) {
            return AjaxResult.error();
        }
    }

    /**
     * 产线资料删除
     */
    @Operation(summary = "产线资料删除")
    @DeleteMapping("/doDelete")
    @Log(title = "产线资料删除", businessType = BusinessType.DELETE)
    public Object doDeleteFinanceProductMill(@RequestParam String millId) {
        try {
            LambdaQueryWrapper<FinanceProductMill> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(FinanceProductMill::getMillId, millId);
            LambdaQueryWrapper<FinanceProductMillAttri> queryWrapperAttri = new LambdaQueryWrapper<>();
            queryWrapperAttri.eq(FinanceProductMillAttri::getMillId, millId);
            LambdaQueryWrapper<FinanceProductMillSystem> queryWrapperSystem = new LambdaQueryWrapper<>();
            queryWrapperSystem.eq(FinanceProductMillSystem::getMillId, millId);
            LambdaQueryWrapper<FinanceProductMillActivity> queryWrapperActivity = new LambdaQueryWrapper<>();
            queryWrapperActivity.eq(FinanceProductMillActivity::getMillId, millId);
            LambdaQueryWrapper<FinanceProductMillProduct> queryWrapperProduct = new LambdaQueryWrapper<>();
            queryWrapperProduct.eq(FinanceProductMillProduct::getMillId, millId);
            boolean result = financeProductMillService.remove(queryWrapper);
            financeProductMillAttriService.remove(queryWrapperAttri);
            financeProductMillSystemService.remove(queryWrapperSystem);
            financeProductMillActivityService.remove(queryWrapperActivity);
            financeProductMillProductService.remove(queryWrapperProduct);
            if (result) {
                return AjaxResult.success("删除成功");
            } else {
                return AjaxResult.error("删除失败，请重新提交！");
            }
        } catch (Exception e) {
            return AjaxResult.error();
        }
    }

    /**
     * 产线属性资料删除
     */
    @Operation(summary = "产线属性资料删除")
    @DeleteMapping("/doAttriDelete")
    @Log(title = "产线属性资料删除", businessType = BusinessType.DELETE)
    public Object doAttriDelete(@RequestParam String millId, String attriValue) {
        try {
            LambdaQueryWrapper<FinanceProductMillAttri> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(FinanceProductMillAttri::getMillId, millId)
                        .eq(FinanceProductMillAttri::getAttriValue, attriValue);
            boolean result = financeProductMillAttriService.remove(queryWrapper);
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
     * 产线相关系统资料删除
     */
    @Operation(summary = "产线相关系统资料删除")
    @DeleteMapping("/doSystemDelete")
    @Log(title = "产线相关系统资料删除", businessType = BusinessType.DELETE)
    public Object doSystemDelete(@RequestParam String millId, String sysId) {
        try {
            LambdaQueryWrapper<FinanceProductMillSystem> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(FinanceProductMillSystem::getMillId, millId)
                    .eq(FinanceProductMillSystem::getSysId, sysId);
            boolean result = financeProductMillSystemService.remove(queryWrapper);
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
     * 产线相关活动资料删除
     */
    @Operation(summary = "产线相关活动资料删除")
    @DeleteMapping("/doActivityDelete")
    @Log(title = "产线相关活动资料删除", businessType = BusinessType.DELETE)
    public Object doActivityDelete(@RequestParam String millId, String activityId) {
        try {
            LambdaQueryWrapper<FinanceProductMillActivity> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(FinanceProductMillActivity::getMillId, millId)
                    .eq(FinanceProductMillActivity::getActivityId, activityId);
            boolean result = financeProductMillActivityService.remove(queryWrapper);
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
     * 产线产出产副品资料删除
     */
    @Operation(summary = "产线产出产副品资料删除")
    @DeleteMapping("/doProductDelete")
    @Log(title = "产线产出产副品资料删除", businessType = BusinessType.DELETE)
    public Object doProductDelete(@RequestParam String millId, String productCode) {
        try {
            LambdaQueryWrapper<FinanceProductMillProduct> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(FinanceProductMillProduct::getMillId, millId)
                    .eq(FinanceProductMillProduct::getProductCode, productCode);
            boolean result = financeProductMillProductService.remove(queryWrapper);
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
     * 产线单笔资料查询
     */
    @Operation(summary = "产线单笔资料查询")
    @GetMapping("/doQueryOne")
    @Log(title = "产线单笔资料查询", businessType = BusinessType.OTHER)
    public Object doQueryOneFinanceProductMill(FinanceProductMillDTO financeProductMillDTO) {
        try {
            String millId = financeProductMillDTO.getMillId();
            //构造产线单笔资料查询条件
            LambdaQueryWrapper<FinanceProductMill> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(StringUtils.isNotBlank(millId), FinanceProductMill::getMillId, millId);
            //构造属性资料查询条件
            MPJLambdaWrapper<FinanceProductMillDTO> wrapper = new MPJLambdaWrapper<>();
            wrapper.select(FinanceProductSettingDetail::getItemA, FinanceProductSettingDetail::getDataA)
                   .select(FinanceProductMillAttri::getAttriValue, FinanceProductMillAttri::getId)
                   .innerJoin(FinanceProductMillAttri.class, FinanceProductMillAttri::getAttriName, FinanceProductSettingDetail::getItemA)
                   .eq(FinanceProductSettingDetail::getCompId, "J00")
                   .eq(FinanceProductSettingDetail::getLeafId, "01020803")
                   .eq(StringUtils.isNotBlank(millId), FinanceProductMillAttri::getMillId, millId);
            //构造产线相关系统页签查询条件
            LambdaQueryWrapper<FinanceProductMillSystem> systemQueryWrapper = new LambdaQueryWrapper<>();
            systemQueryWrapper.eq(StringUtils.isNotBlank(millId), FinanceProductMillSystem::getMillId, millId);
            //构造产线相关活动页签查询条件
            LambdaQueryWrapper<FinanceProductMillActivity> activityQueryWrapper = new LambdaQueryWrapper<>();
            activityQueryWrapper.eq(StringUtils.isNotBlank(millId), FinanceProductMillActivity::getMillId, millId);
            //构造产线产出产副品页签查询条件
            MPJLambdaWrapper<FinanceProductMillDTO> wrapper1 = new MPJLambdaWrapper<>();
            wrapper1.select(FinanceProductMillProduct::getProductCode)
                    .select(FinanceProductBase::getProductCodeDesc)
                    .innerJoin(FinanceProductBase.class, FinanceProductBase::getProductCode, FinanceProductMillProduct::getProductCode)
                    .eq(StringUtils.isNotBlank(millId), FinanceProductMillProduct::getMillId, millId);
            List<FinanceProductMill> list = financeProductMillService.list(queryWrapper);
            List<FinanceProductMillDTO> listA = financeProductSettingDetailServicea.selectJoinList(FinanceProductMillDTO.class, wrapper);
            List<FinanceProductMillSystem> listB = financeProductMillSystemService.list(systemQueryWrapper);
            List<FinanceProductMillActivity> listC = financeProductMillActivityService.list(activityQueryWrapper);
            List<FinanceProductMillDTO> listD = financeProductSettingDetailServicea.selectJoinList(FinanceProductMillDTO.class, wrapper1);
            Map<String,Object> dataMap = new HashMap<>(16);
            dataMap.put("millData", list);
            dataMap.put("attriData", listA);
            dataMap.put("systemData", listB);
            dataMap.put("activityData", listC);
            dataMap.put("productData", listD);
            if (list.isEmpty() && listA.isEmpty()) {
                return AjaxResult.success("查无资料", dataMap);
            } else {
                return AjaxResult.success("查询成功", dataMap);
            }
        } catch (Exception e) {
            return AjaxResult.error();
        }
    }
}
