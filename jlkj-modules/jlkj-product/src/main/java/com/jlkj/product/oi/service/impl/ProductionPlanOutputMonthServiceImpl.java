package com.jlkj.product.oi.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.product.oi.domain.*;
import com.jlkj.product.oi.dto.changelog.InsertChangeLogDTO;
import com.jlkj.product.oi.dto.productionplanoutputmonth.ListProductionPlanOutputMonthTargetItemDTO;
import com.jlkj.product.oi.dto.productionplantarget.*;
import com.jlkj.product.oi.mapper.ProductionParameterTargetItemMapper;
import com.jlkj.product.oi.mapper.ProductionPlanOutputMonthMapper;
import com.jlkj.product.oi.service.ChangeLogService;
import com.jlkj.product.oi.service.ProductionPlanOutputMonthService;
import com.jlkj.product.oi.vo.productionplanoutputmonth.ListProductionPlanOutputMonthTargetItemVO;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

import static com.jlkj.product.oi.constants.RedissonUtil.getLock;
import static com.jlkj.product.oi.constants.DateConstants.MONTHS;

/**
 * @author zyf
 * @description 针对表【product_oi_plan_output_month(计划管理--月产量计划表)】的数据库操作Service实现
 * @createDate 2022-04-21 14:46:03
 */
@Service
public class ProductionPlanOutputMonthServiceImpl extends ServiceImpl<ProductionPlanOutputMonthMapper, ProductionPlanOutputMonth>
        implements ProductionPlanOutputMonthService {

    @Autowired
    RedissonClient redissonClient;

    @Autowired
    com.jlkj.product.oi.service.impl.ProductionPlanOutputYearServiceImpl planOutputYearService;

    @Autowired
    com.jlkj.product.oi.service.impl.ProductionPlanOutputDateServiceImpl planOutputDateService;

    @Autowired
    com.jlkj.product.oi.service.impl.ProductionPlanRepairServiceImpl planRepairService;

    @Autowired
    com.jlkj.product.oi.service.impl.ProductionPlanTargetYearServiceImpl planTargetYearService;

    @Autowired
    com.jlkj.product.oi.service.impl.ProductionYieldAnalysisYearServiceImpl yieldAnalysisYearService;

    @Autowired
    com.jlkj.product.oi.service.impl.ProductionYieldAnalysisMonthServiceImpl yieldAnalysisMonthService;

    @Autowired
    com.jlkj.product.oi.service.impl.ProductionYieldAnalysisDateServiceImpl yieldAnalysisDateService;

    @Resource
    ProductionParameterTargetItemMapper parameterTargetItemMapper;

    public List<Map<String, String>> getColToRowList(String sql) {
        return getBaseMapper().getList(sql);
    }

    @Resource
    @Lazy
    com.jlkj.product.oi.service.impl.ProductionParameterTargetItemServiceImpl productionParameterTargetItemService;

    @Resource
    ChangeLogService changeLogService;

    @Override
    @Transactional(readOnly = true)
    public List<ListProductionPlanOutputMonthTargetItemVO> getProductionPlanOutputMonthTargetItemChartData(ListProductionPlanOutputMonthTargetItemDTO listProductionPlanOutputMonthTargetItemDTO) {
        return getBaseMapper().getProductionPlanOutputMonthTargetItemChartData(listProductionPlanOutputMonthTargetItemDTO);
    }

    public Object get(GetProductionPlanMonthDTO dto, List<ProductionParameterTargetItem> itemlist) {
        StringBuilder sqlString = new StringBuilder();
        sqlString.append("select plan_month, ");
        for (ProductionParameterTargetItem item : itemlist) {
            sqlString.append("max(case target_item_id when '");
            sqlString.append(item.getId());
            sqlString.append("' then target_item_output else 0 end) as '");
            sqlString.append(item.getId());
            sqlString.append("', ");
        }
        sqlString.append("'' as blk");
        sqlString.append(" from product_oi_plan_output_month ");
        sqlString.append("where plan_year = ");
        sqlString.append(dto.getPlanYear());
        sqlString.append(" group by plan_month order by plan_year desc, plan_month asc");
        List<Map<String, String>> targetList = getColToRowList(sqlString.toString());
        return AjaxResult.success(targetList);
    }

    public Object getOne(GetProductionPlanOneMonthDTO dto, List<ProductionParameterTargetItem> itemlist) {
        StringBuilder sqlString = new StringBuilder();
        sqlString.append("select plan_month, ");
        for (ProductionParameterTargetItem item : itemlist) {
            sqlString.append("max(case target_item_id when '");
            sqlString.append(item.getId());
            sqlString.append("' then target_item_value else 0 end) as '");
            sqlString.append(item.getId());
            sqlString.append("' from product_oi_plan_output_month ");
            sqlString.append(" where plan_year = ");
            sqlString.append(dto.getPlanYear());
            sqlString.append(" and plan_month = ");
            sqlString.append(dto.getPlanMonth());
            sqlString.append(" group by plan_month");
        }
        List<Map<String, String>> targetList = getColToRowList(sqlString.toString());
        return AjaxResult.success(targetList);
    }

    public Object update(UpdateProductionPlanMonthDTO dto) {
        RLock rLock = redissonClient.getLock(getLock("MonthProductionTargetPlan", dto.getPlanYear(), dto.getPlanMonth()));
        rLock.lock();
        try {
            Date now = DateUtil.date();
            List<ProductionPlanOutputMonth> monthList = list(new QueryWrapper<ProductionPlanOutputMonth>().lambda()
                    .eq(ProductionPlanOutputMonth::getPlanYear, dto.getPlanYear())
                    .eq(ProductionPlanOutputMonth::getPlanMonth, dto.getPlanMonth()));
            List<ProductionPlanOutputDate> dayList = planOutputDateService.list(new QueryWrapper<ProductionPlanOutputDate>().lambda()
                    .eq(ProductionPlanOutputDate::getPlanYear, dto.getPlanYear())
                    .eq(ProductionPlanOutputDate::getPlanMonth, dto.getPlanMonth()));
            List<ProductionYieldAnalysisMonth> monthAnaList = yieldAnalysisMonthService.list(new QueryWrapper<ProductionYieldAnalysisMonth>().lambda()
                    .eq(ProductionYieldAnalysisMonth::getYear, dto.getPlanYear())
                    .eq(ProductionYieldAnalysisMonth::getMonth, dto.getPlanMonth()));
            List<ProductionYieldAnalysisDate> dayAnaList = yieldAnalysisDateService.list(new QueryWrapper<ProductionYieldAnalysisDate>().lambda()
                    .eq(ProductionYieldAnalysisDate::getYear, dto.getPlanYear())
                    .eq(ProductionYieldAnalysisDate::getMonth, dto.getPlanMonth()));
            if (monthList.size() < 1) {
                return AjaxResult.error("当月计划不存在或已删除");
            }
            if (dayList.size() < 1) {
                return AjaxResult.error("当月的日计划不存在或已删除");
            }
            if (DateUtil.year(now) >= dto.getPlanYear() && (DateUtil.month(now) >= dto.getPlanMonth())) {
                return AjaxResult.error("当月和当月之前的计划不能被修改");
            }

            saveUpdateLog(dto, monthList);

            batch(dto, now, monthList, dayList, monthAnaList, dayAnaList);
            List<ProductionPlanRepair> repairs = planRepairService.lambdaQuery()
                    .eq(ProductionPlanRepair::getIsAffectOutput, 1)
                    .and(productionPlanRepairLambdaQueryWrapper -> {
                        productionPlanRepairLambdaQueryWrapper
                                .likeRight(ProductionPlanRepair::getPlanStartDate, dto.getPlanYear().toString())
                                .or().likeRight(ProductionPlanRepair::getPlanEndDate, dto.getPlanYear().toString());
                    })
                    .list();
            if (repairs.size() > 0) {
                List<ProductionPlanOutputDate> dateRelationRepairlist = planOutputDateService.getBaseMapper()
                        .getOutputDateWithMonthRelationRepair(dto.getPlanYear(), dto.getPlanMonth());
                for (ProductionPlanOutputDate planOutputDate : dateRelationRepairlist) {
                    planOutputDate.setTargetItemOutput(new BigDecimal("0.000"));
                }
                planOutputDateService.updateBatchById(dateRelationRepairlist);
                List<ProductionYieldAnalysisDate> dateAnaRelationRepairlist = planOutputDateService.getBaseMapper()
                        .getAnaDateWithMonthRelationRepair(dto.getPlanYear(), dto.getPlanMonth());
                for (ProductionYieldAnalysisDate planAnaDate : dateAnaRelationRepairlist) {
                    planAnaDate.setProductionPlan(new BigDecimal("0.000"));
                }
                yieldAnalysisDateService.updateBatchById(dateAnaRelationRepairlist);
            }
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return AjaxResult.error(e.getMessage());
        } finally {
            rLock.unlock();
        }
        return AjaxResult.success();
    }

    private void batch(UpdateProductionPlanMonthDTO dto, Date now,
                       List<ProductionPlanOutputMonth> monthList, List<ProductionPlanOutputDate> dayList,
                       List<ProductionYieldAnalysisMonth> monthAnaList, List<ProductionYieldAnalysisDate> dayAnaList) {
        for (ProductionPlanOutputMonth plan : monthList) {
            loop:
            for (int i = 0; i < dto.getTargetItems().size(); i++) {
                if (dto.getTargetItems().get(i).getId().equals(plan.getTargetItemId())) {
                    plan.setTargetItemOutput(dto.getTargetItems().get(i).getValue());
                    plan.setModifyTime(now);
                    plan.setModifyUserId(dto.getUserId());
                    plan.setModifyUserName(dto.getUserName());
                    break loop;
                }
            }
        }
        for (ProductionYieldAnalysisMonth planAna : monthAnaList) {
            loop:
            for (int i = 0; i < dto.getTargetItems().size(); i++) {
                if (dto.getTargetItems().get(i).getId().equals(planAna.getMaterialsId())) {
                    planAna.setProductionPlan(dto.getTargetItems().get(i).getValue());
                    break loop;
                }
            }
        }
        BigDecimal lengthOfMonth = new BigDecimal(DateUtil.lengthOfMonth(dto.getPlanMonth(), DateUtil.isLeapYear(dto.getPlanYear())));
        for (ProductionPlanOutputDate plan : dayList) {
            loop:
            for (int i = 0; i < dto.getTargetItems().size(); i++) {
                if (dto.getTargetItems().get(i).getId().equals(plan.getTargetItemId())) {
                    BigDecimal dayOfOutput = dto.getTargetItems().get(i).getValue().divide(lengthOfMonth, 3, RoundingMode.HALF_UP);
                    plan.setTargetItemOutput(dayOfOutput);
                    plan.setModifyTime(now);
                    plan.setModifyUserId(dto.getUserId());
                    plan.setModifyUserName(dto.getUserName());
                    break loop;
                }
            }
        }
        for (ProductionYieldAnalysisDate planAna : dayAnaList) {
            loop:
            for (int i = 0; i < dto.getTargetItems().size(); i++) {
                if (dto.getTargetItems().get(i).getId().equals(planAna.getMaterialsId())) {
                    BigDecimal dayOfOutput = dto.getTargetItems().get(i).getValue().divide(lengthOfMonth, 3, RoundingMode.HALF_UP);
                    planAna.setProductionPlan(dayOfOutput);
                    break loop;
                }
            }
        }
        updateBatchById(monthList, monthList.size());
        planOutputDateService.updateBatchById(dayList, dayList.size());
        yieldAnalysisMonthService.updateBatchById(monthAnaList);
        yieldAnalysisDateService.updateBatchById(dayAnaList);
    }

    private void saveNewLog(AddProductionPlanYearDTO productionPlanYearDTO) {
        StringBuilder content = new StringBuilder();
        content.append("新增：" + "[计划年度：").append(productionPlanYearDTO.getPlanYear()).append("],");
        for (int y = 0; y < productionPlanYearDTO.getTargetItems().size(); y++) {
            ProductionParameterTargetItem productionParameterTargetItem = productionParameterTargetItemService.getById(productionPlanYearDTO.getTargetItems().get(y).getId());
            if (ObjectUtil.isNotNull(productionParameterTargetItem)) {
                content.append("[").append(productionParameterTargetItem.getTargetItemName()).append("：").append(productionPlanYearDTO.getTargetItems().get(y).getValue().stripTrailingZeros().toPlainString()).append("],");
            }
        }
        InsertChangeLogDTO insertChangeLogDTO = new InsertChangeLogDTO();
        insertChangeLogDTO.setModuleName("生产管理");
        insertChangeLogDTO.setFunctionName("生产计划->焦化产量");
        insertChangeLogDTO.setContent(content.toString());
        insertChangeLogDTO.setCreateUserId(productionPlanYearDTO.getUserId());
        insertChangeLogDTO.setCreateUserName(productionPlanYearDTO.getUserName());
        changeLogService.insertChangeLogData(insertChangeLogDTO);
    }

    private void saveUpdateLog(UpdateProductionPlanMonthDTO updateProductionPlanMonthDTO, List<ProductionPlanOutputMonth> monthList) {
        StringBuilder content = new StringBuilder();
        StringBuilder contentHeader = new StringBuilder();
        contentHeader.append("修改：" + "[计划年度：").append(updateProductionPlanMonthDTO.getPlanYear()).append("],").append("[计划月份：").append(updateProductionPlanMonthDTO.getPlanMonth()).append("],");
        for (ProductionPlanOutputMonth plan : monthList) {
            for (int i = 0; i < updateProductionPlanMonthDTO.getTargetItems().size(); i++) {
                if (updateProductionPlanMonthDTO.getTargetItems().get(i).getId().equals(plan.getTargetItemId())) {
                    if (plan.getTargetItemOutput().compareTo(updateProductionPlanMonthDTO.getTargetItems().get(i).getValue()) != 0) {
                        ProductionParameterTargetItem productionParameterTargetItem = productionParameterTargetItemService.getById(updateProductionPlanMonthDTO.getTargetItems().get(i).getId());
                        if (ObjectUtil.isNotNull(productionParameterTargetItem)) {
                            content.append("[").append(productionParameterTargetItem.getTargetItemName()).append("：")
                                    .append(plan.getTargetItemOutput().stripTrailingZeros().toPlainString()).append("->")
                                    .append(updateProductionPlanMonthDTO.getTargetItems().get(i).getValue().stripTrailingZeros().toPlainString()).append("],");
                         }
                    }
                    break;
                }
            }
        }
        if (content.length() > 0) {
            InsertChangeLogDTO insertChangeLogDTO = new InsertChangeLogDTO();
            insertChangeLogDTO.setModuleName("生产管理");
            insertChangeLogDTO.setFunctionName("生产计划->焦化产量");
            insertChangeLogDTO.setContent(contentHeader.append(content).toString());
            insertChangeLogDTO.setCreateUserId(updateProductionPlanMonthDTO.getUserId());
            insertChangeLogDTO.setCreateUserName(updateProductionPlanMonthDTO.getUserName());
            changeLogService.insertChangeLogData(insertChangeLogDTO);
        }
    }

    public Object save(AddProductionPlanYearDTO productionPlanYearDTO) {
        RLock rLock = redissonClient.getLock(getLock("saveYearProductionOutputPlan", productionPlanYearDTO.getPlanYear()));
        rLock.lock();
        try {
            List<String> itemIds = new ArrayList<String>();
            for (TartgetItemsDTO items : productionPlanYearDTO.getTargetItems()) {
                itemIds.add(items.getId());
            }
            List<ProductionParameterTargetItem> itemList = parameterTargetItemMapper.selectList(new LambdaQueryWrapper<ProductionParameterTargetItem>()
                    .in(ProductionParameterTargetItem::getId, itemIds));
            Date now = DateUtil.date();
            List<ProductionPlanOutputYear> plans = planOutputYearService.list(new QueryWrapper<ProductionPlanOutputYear>().lambda()
                    .eq(ProductionPlanOutputYear::getPlanYear, productionPlanYearDTO.getPlanYear()));
            if (plans.size() > 0) {
                return AjaxResult.error("当年产量计划已存在");
            }
            List<ProductionPlanTargetYear> targetPlans = planTargetYearService.list(new QueryWrapper<ProductionPlanTargetYear>().lambda()
                    .eq(ProductionPlanTargetYear::getPlanYear, productionPlanYearDTO.getPlanYear()));
            if (targetPlans.size() < 1) {
                return AjaxResult.error("当年指标计划不存在");
            }
            saveNewLog(productionPlanYearDTO);
            BigDecimal lengthOfYear = new BigDecimal(DateUtil.lengthOfYear(productionPlanYearDTO.getPlanYear()));
            List<ProductionPlanOutputYear> yearList = new ArrayList<>();
            List<ProductionYieldAnalysisYear> yearAnaList = new ArrayList<>();
            batchs(productionPlanYearDTO, now, yearList, lengthOfYear);
            batchsAnalys(productionPlanYearDTO, now, yearAnaList, lengthOfYear, itemList);
            planOutputYearService.saveBatch(yearList, yearList.size());
            yieldAnalysisYearService.saveBatch(yearAnaList, yearList.size());
            List<ProductionPlanRepair> repairs = planRepairService.lambdaQuery()
                    .eq(ProductionPlanRepair::getIsAffectOutput, 1)
                    .and(productionPlanRepairLambdaQueryWrapper -> {
                        productionPlanRepairLambdaQueryWrapper
                                .likeRight(ProductionPlanRepair::getPlanStartDate, productionPlanYearDTO.getPlanYear().toString())
                                .or().likeRight(ProductionPlanRepair::getPlanEndDate, productionPlanYearDTO.getPlanYear().toString());
                    })
                    .list();
            if (repairs.size() > 0) {
                List<ProductionPlanOutputDate> dateRelationRepairlist = planOutputDateService.getBaseMapper()
                        .getOutputDateRelationRepair(productionPlanYearDTO.getPlanYear());
                for (ProductionPlanOutputDate planOutputDate : dateRelationRepairlist) {
                    planOutputDate.setTargetItemOutput(new BigDecimal("0.000"));
                }
                planOutputDateService.updateBatchById(dateRelationRepairlist);
                List<ProductionYieldAnalysisDate> dateAnaRelationRepairlist = planOutputDateService.getBaseMapper()
                        .getAnaDateRelationRepair(productionPlanYearDTO.getPlanYear());
                for (ProductionYieldAnalysisDate planAnaDate : dateAnaRelationRepairlist) {
                    planAnaDate.setProductionPlan(new BigDecimal("0.000"));
                }
                yieldAnalysisDateService.updateBatchById(dateAnaRelationRepairlist);
            }
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return AjaxResult.error(e.getMessage());
        } finally {
            rLock.unlock();
        }
        return AjaxResult.success();
    }

    private void batchs(AddProductionPlanYearDTO productionPlanYearDTO, Date now, List<ProductionPlanOutputYear> yearList, BigDecimal lengthOfYear) {
        for (int i = 0; i < productionPlanYearDTO.getTargetItems().size(); i++) {
            BigDecimal pValue = productionPlanYearDTO.getTargetItems().get(i).getValue();
            ProductionPlanOutputYear planOutputYear = new ProductionPlanOutputYear();
            planOutputYear.setId(IdUtil.randomUUID());
            planOutputYear.setPlanYear(productionPlanYearDTO.getPlanYear());
            planOutputYear.setTargetItemId(productionPlanYearDTO.getTargetItems().get(i).getId());
            planOutputYear.setTargetItemOutput(pValue);
            planOutputYear.setCreateUserId(productionPlanYearDTO.getUserId());
            planOutputYear.setCreateUserName(productionPlanYearDTO.getUserName());
            planOutputYear.setCreateTime(now);
            yearList.add(planOutputYear);

            BigDecimal dayOfOutput = pValue.divide(lengthOfYear, 3, RoundingMode.HALF_UP);
            List<ProductionPlanOutputMonth> monthList = new ArrayList<>();
            for (int m = 1; m < MONTHS; m++) {
                int days = DateUtil.lengthOfMonth(m, DateUtil.isLeapYear(productionPlanYearDTO.getPlanYear()));
                BigDecimal lengthOfMonth = new BigDecimal(days);
                ProductionPlanOutputMonth planOutputMonth = new ProductionPlanOutputMonth();
                planOutputMonth.setId(IdUtil.randomUUID());
                planOutputMonth.setPlanYear(productionPlanYearDTO.getPlanYear());
                planOutputMonth.setPlanMonth(m);
                planOutputMonth.setTargetItemId(productionPlanYearDTO.getTargetItems().get(i).getId());
                planOutputMonth.setTargetItemOutput(dayOfOutput.multiply(lengthOfMonth));
                planOutputMonth.setCreateUserId(productionPlanYearDTO.getUserId());
                planOutputMonth.setCreateUserName(productionPlanYearDTO.getUserName());
                planOutputMonth.setCreateTime(now);
                planOutputMonth.setModifyUserId(productionPlanYearDTO.getUserId());
                planOutputMonth.setModifyUserName(productionPlanYearDTO.getUserName());
                planOutputMonth.setModifyTime(now);
                monthList.add(planOutputMonth);

                List<ProductionPlanOutputDate> dayList = new ArrayList<>();
                for (int j = 1; j <= days; j++) {
                    ProductionPlanOutputDate planOutputDate = new ProductionPlanOutputDate();
                    planOutputDate.setId(IdUtil.randomUUID());
                    planOutputDate.setPlanYear(productionPlanYearDTO.getPlanYear());
                    planOutputDate.setPlanMonth(m);
                    planOutputDate.setPlanDate(j);
                    planOutputDate.setTargetItemId(productionPlanYearDTO.getTargetItems().get(i).getId());
                    planOutputDate.setTargetItemOutput(dayOfOutput);
                    planOutputDate.setCreateUserId(productionPlanYearDTO.getUserId());
                    planOutputDate.setCreateUserName(productionPlanYearDTO.getUserName());
                    planOutputDate.setCreateTime(now);
                    planOutputDate.setModifyUserId(productionPlanYearDTO.getUserId());
                    planOutputDate.setModifyUserName(productionPlanYearDTO.getUserName());
                    planOutputDate.setModifyTime(now);
                    dayList.add(planOutputDate);
                }
                planOutputDateService.saveBatch(dayList, dayList.size());
            }
            saveBatch(monthList, monthList.size());
        }
    }

    private void batchsAnalys(AddProductionPlanYearDTO productionPlanYearDTO, Date now, List<ProductionYieldAnalysisYear> yearList, BigDecimal lengthOfYear, List<ProductionParameterTargetItem> items) {
        ProductionParameterTargetItem parameterTargetItem = new ProductionParameterTargetItem();
        parameterTargetItem.setId("123456789");
        parameterTargetItem.setMaterialName("焦炭");
        parameterTargetItem.setTargetItemName("焦炭");
        items.add(parameterTargetItem);
        for (int i = 0; i < productionPlanYearDTO.getTargetItems().size(); i++) {
            BigDecimal pValue = productionPlanYearDTO.getTargetItems().get(i).getValue();
            ProductionYieldAnalysisYear planYear = new ProductionYieldAnalysisYear();
            planYear.setId(IdUtil.randomUUID());
            planYear.setYear(productionPlanYearDTO.getPlanYear());
            planYear.setMaterialsId(productionPlanYearDTO.getTargetItems().get(i).getId());
            int finalI = i;
            Optional<ProductionParameterTargetItem> optional = items.stream().filter(productionParameterTargetItem -> productionParameterTargetItem.getId().equals(productionPlanYearDTO.getTargetItems().get(finalI).getId())).findFirst();
            optional.ifPresent(productionParameterTargetItem -> {
                planYear.setMaterialsName(productionParameterTargetItem.getTargetItemName());
            });
            planYear.setProductionPlan(pValue);
            yearList.add(planYear);

            BigDecimal dayOfOutput = pValue.divide(lengthOfYear, 3, RoundingMode.HALF_UP);
            List<ProductionYieldAnalysisMonth> monthList = new ArrayList<>();
            for (int m = 1; m < MONTHS; m++) {
                int days = DateUtil.lengthOfMonth(m, DateUtil.isLeapYear(productionPlanYearDTO.getPlanYear()));
                BigDecimal lengthOfMonth = new BigDecimal(days);
                ProductionYieldAnalysisMonth planMonth = new ProductionYieldAnalysisMonth();
                planMonth.setId(IdUtil.randomUUID());
                planMonth.setYear(productionPlanYearDTO.getPlanYear());
                planMonth.setMonth(m);
                planMonth.setMaterialsId(productionPlanYearDTO.getTargetItems().get(i).getId());
                Optional<ProductionParameterTargetItem> optionalm = items.stream().filter(productionParameterTargetItem -> productionParameterTargetItem.getId().equals(productionPlanYearDTO.getTargetItems().get(finalI).getId())).findFirst();
                optionalm.ifPresent(productionParameterTargetItem -> {
                    planMonth.setMaterialsName(productionParameterTargetItem.getTargetItemName());
                });
                planMonth.setProductionPlan(dayOfOutput.multiply(lengthOfMonth));
                monthList.add(planMonth);

                List<ProductionYieldAnalysisDate> dayList = new ArrayList<>();
                for (int j = 1; j <= days; j++) {
                    ProductionYieldAnalysisDate planDate = new ProductionYieldAnalysisDate();
                    planDate.setId(IdUtil.randomUUID());
                    planDate.setYear(productionPlanYearDTO.getPlanYear());
                    planDate.setMonth(m);
                    planDate.setDate(j);
                    planDate.setMaterialsId(productionPlanYearDTO.getTargetItems().get(i).getId());
                    Optional<ProductionParameterTargetItem> optionald = items.stream().filter(productionParameterTargetItem -> productionParameterTargetItem.getId().equals(productionPlanYearDTO.getTargetItems().get(finalI).getId())).findFirst();
                    optionald.ifPresent(productionParameterTargetItem -> {
                        planDate.setMaterialsName(productionParameterTargetItem.getTargetItemName());
                    });
                    planDate.setProductionPlan(dayOfOutput);
                    dayList.add(planDate);
                }
                yieldAnalysisDateService.saveBatch(dayList, dayList.size());
            }
            yieldAnalysisMonthService.saveBatch(monthList, monthList.size());
        }
    }
}




