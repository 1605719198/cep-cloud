package com.jlkj.product.oi.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.product.oi.domain.*;
import com.jlkj.product.oi.dto.changelog.InsertChangeLogDTO;
import com.jlkj.product.oi.dto.productionplantarget.*;
import com.jlkj.product.oi.mapper.ProductionParameterTargetItemMapper;
import com.jlkj.product.oi.mapper.ProductionPlanTargetYearMapper;
import com.jlkj.product.oi.service.ChangeLogService;
import com.jlkj.product.oi.service.ProductionPlanTargetYearService;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

import static com.jlkj.product.oi.constants.RedissonUtil.getLock;
import static com.jlkj.product.oi.constants.DateConstants.MONTHS;

/**
 * @author zyf
 * @description 针对表【product_oi_plan_target_year(计划管理--年指标计划表)】的数据库操作Service实现
 * @createDate 2022-04-21 14:46:03
 */
@Service
public class ProductionPlanTargetYearServiceImpl extends ServiceImpl<ProductionPlanTargetYearMapper, ProductionPlanTargetYear>
        implements ProductionPlanTargetYearService {

    @Autowired
    RedissonClient redissonClient;

    @Autowired
    com.jlkj.product.oi.service.impl.ProductionPlanTargetMonthServiceImpl planTargetMonthService;

    @Autowired
    ProductionPlanTargetDateServiceImpl planTargetDateService;

    @Autowired
    com.jlkj.product.oi.service.impl.ProductionRealOutputYearServiceImpl realOutputYearService;

    @Autowired
    com.jlkj.product.oi.service.impl.ProductionRealOutputMonthServiceImpl realOutputMonthService;

    @Autowired
    com.jlkj.product.oi.service.impl.ProductionRealOutputDateServiceImpl realOutputDateService;

    @Resource
    ProductionParameterTargetItemMapper parameterTargetItemMapper;

    @Resource
    @Lazy
    com.jlkj.product.oi.service.impl.ProductionParameterTargetItemServiceImpl productionParameterTargetItemService;

    @Resource
    ChangeLogService changeLogService;

    public List<Map<String, String>> getColToRowList(String sql) {
        return getBaseMapper().getList(sql);
    }

    ;

    public Object get(List<ProductionParameterTargetItem> itemlist) {
        StringBuilder sqlString = new StringBuilder();
        sqlString.append("select plan_year, ");
        for (ProductionParameterTargetItem item : itemlist) {
            sqlString.append("max(case target_item_id when '");
            sqlString.append(item.getId());
            sqlString.append("' then target_item_value else 0 end) as '");
            sqlString.append(item.getId());
            sqlString.append("', ");
        }
        sqlString.append("'' as blk");
        sqlString.append(" from product_oi_plan_target_year group by plan_year order by plan_year desc");
        List<Map<String, String>> targetList = getColToRowList(sqlString.toString());
        return AjaxResult.success(targetList);
    }

    public Object getMonth(GetProductionPlanMonthDTO dto, List<ProductionParameterTargetItem> itemlist) {
        StringBuilder sqlString = new StringBuilder();
        sqlString.append("select plan_month, ");
        for (ProductionParameterTargetItem item : itemlist) {
            sqlString.append("max(case target_item_id when '");
            sqlString.append(item.getId());
            sqlString.append("' then target_item_value else 0 end) as '");
            sqlString.append(item.getId());
            sqlString.append("', ");
        }
        sqlString.append("'' as blk");
        sqlString.append(" from product_oi_plan_target_month ");
        sqlString.append("where plan_year = ");
        sqlString.append(dto.getPlanYear());
        sqlString.append(" group by plan_month order by plan_year desc, plan_month asc");
        List<Map<String, String>> targetList = planTargetMonthService.getColToRowList(sqlString.toString());
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
            sqlString.append("', ");
        }
        sqlString.append("'' as blk");
        sqlString.append(" from product_oi_plan_target_month ");
        sqlString.append(" where plan_year = ");
        sqlString.append(dto.getPlanYear());
        sqlString.append(" and plan_month = ");
        sqlString.append(dto.getPlanMonth());
        sqlString.append(" group by plan_month");
        List<Map<String, String>> targetList = planTargetMonthService.getColToRowList(sqlString.toString());
        return AjaxResult.success(targetList);
    }

    public Object getDate(GetProductionPlanDayDTO dto, List<ProductionParameterTargetItem> itemlist) {
        StringBuilder sqlString = new StringBuilder();
        sqlString.append("select plan_date, ");
        for (ProductionParameterTargetItem item : itemlist) {
            sqlString.append("max(case target_item_id when '");
            sqlString.append(item.getId());
            sqlString.append("' then target_item_value else 0 end) as '");
            sqlString.append(item.getId());
            sqlString.append("', ");
        }
        sqlString.append("'' as blk");
        sqlString.append(" from product_oi_plan_target_date ");
        sqlString.append("where plan_year = ");
        sqlString.append(dto.getPlanYear());
        sqlString.append(" and plan_month = ");
        sqlString.append(dto.getPlanMonth());
        sqlString.append(" group by plan_date order by plan_year desc, plan_month asc, plan_date asc");
        List<Map<String, String>> targetList = planTargetDateService.getColToRowList(sqlString.toString());
        return AjaxResult.success(targetList);
    }

    private void saveNewLog(AddProductionPlanYearDTO productionPlanTargetYearDTO) {
        StringBuilder content = new StringBuilder();
        content.append("新增：" + "[计划年度：").append(productionPlanTargetYearDTO.getPlanYear()).append("],");
        for (int y = 0; y < productionPlanTargetYearDTO.getTargetItems().size(); y++) {
            ProductionParameterTargetItem productionParameterTargetItem = productionParameterTargetItemService.getById(productionPlanTargetYearDTO.getTargetItems().get(y).getId());
            content.append("[").append(productionParameterTargetItem.getTargetItemName()).append("：").append(productionPlanTargetYearDTO.getTargetItems().get(y).getValue().stripTrailingZeros().toPlainString()).append("],");
        }
        InsertChangeLogDTO insertChangeLogDTO = new InsertChangeLogDTO();
        insertChangeLogDTO.setModuleName("生产管理");
        insertChangeLogDTO.setFunctionName("生产计划->焦化指标");
        insertChangeLogDTO.setContent(content.toString());
        insertChangeLogDTO.setCreateUserId(productionPlanTargetYearDTO.getUserId());
        insertChangeLogDTO.setCreateUserName(productionPlanTargetYearDTO.getUserName());
        changeLogService.insertChangeLogData(insertChangeLogDTO);
    }

    private void saveUpdateLog(UpdateProductionPlanMonthDTO updateProductionPlanMonthDTO, List<ProductionPlanTargetMonth> monthList) {
        StringBuilder content = new StringBuilder();
        StringBuilder contentHeader = new StringBuilder();
        contentHeader.append("修改：" + "[计划年度：").append(updateProductionPlanMonthDTO.getPlanYear()).append("],").append("[计划月份").append(updateProductionPlanMonthDTO.getPlanMonth()).append("],");
        for (ProductionPlanTargetMonth plan : monthList) {
            for (int i = 0; i < updateProductionPlanMonthDTO.getTargetItems().size(); i++) {
                if (updateProductionPlanMonthDTO.getTargetItems().get(i).getId().equals(plan.getTargetItemId())) {
                    if (plan.getTargetItemValue().compareTo(updateProductionPlanMonthDTO.getTargetItems().get(i).getValue()) != 0) {
                        ProductionParameterTargetItem productionParameterTargetItem = productionParameterTargetItemService.getById(updateProductionPlanMonthDTO.getTargetItems().get(i).getId());
                        content.append("[").append(productionParameterTargetItem.getTargetItemName()).append("：")
                                .append(plan.getTargetItemValue().stripTrailingZeros().toPlainString()).append("->").append(updateProductionPlanMonthDTO.getTargetItems().get(i).getValue().stripTrailingZeros().toPlainString()).append("],");
                    }
                    break;
                }
            }
        }
        if (content.length() > 0) {
            InsertChangeLogDTO insertChangeLogDTO = new InsertChangeLogDTO();
            insertChangeLogDTO.setModuleName("生产管理");
            insertChangeLogDTO.setFunctionName("生产计划->焦化指标");
            insertChangeLogDTO.setContent(contentHeader.append(content).toString());
            insertChangeLogDTO.setCreateUserId(updateProductionPlanMonthDTO.getUserId());
            insertChangeLogDTO.setCreateUserName(updateProductionPlanMonthDTO.getUserName());
            changeLogService.insertChangeLogData(insertChangeLogDTO);
        }
    }

    public Object save(AddProductionPlanYearDTO productionPlanTargetYearDTO) {
        RLock rLock = redissonClient.getLock(getLock("YearProductionTargetPlan", productionPlanTargetYearDTO.getPlanYear()));
        rLock.lock();
        try {
            Date now = DateUtil.date();
            List<ProductionPlanTargetYear> plans = list(new QueryWrapper<ProductionPlanTargetYear>().lambda()
                    .eq(ProductionPlanTargetYear::getPlanYear, productionPlanTargetYearDTO.getPlanYear()));
            if (plans.size() > 0) {
                return AjaxResult.error("当年计划已存在");
            }
            List<String> itemIds = new ArrayList<String>();
            for (TartgetItemsDTO items : productionPlanTargetYearDTO.getTargetItems()) {
                itemIds.add(items.getId());
            }
            List<ProductionParameterTargetItem> itemList = parameterTargetItemMapper.selectList(new LambdaQueryWrapper<ProductionParameterTargetItem>()
                    .in(ProductionParameterTargetItem::getId, itemIds));
            ProductionParameterTargetItem parameterTargetItem = new ProductionParameterTargetItem();
            parameterTargetItem.setId("123456789");
            parameterTargetItem.setMaterialName("焦炭");
            parameterTargetItem.setTargetItemName("焦炭");
            itemList.add(parameterTargetItem);

            List<ProductionPlanTargetYear> yearList = new ArrayList<>();
            List<ProductionPlanTargetMonth> monthList = new ArrayList<>();
            List<ProductionPlanTargetDate> dayList = new ArrayList<>();
            List<ProductionRealOutputYear> yearRealList = new ArrayList<>();
            List<ProductionRealOutputMonth> monthRealList = new ArrayList<>();
            List<ProductionRealOutputDate> dayRealList = new ArrayList<>();
            saveNewLog(productionPlanTargetYearDTO);
            for (int y = 0; y < productionPlanTargetYearDTO.getTargetItems().size(); y++) {
                int finalI = y;
                AtomicReference<String> targetItemName = new AtomicReference<>("");
                Optional<ProductionParameterTargetItem> optional = itemList.stream().filter(productionParameterTargetItem -> productionParameterTargetItem.getId().equals(productionPlanTargetYearDTO.getTargetItems().get(finalI).getId())).findFirst();
                optional.ifPresent(productionParameterTargetItem -> {
                    targetItemName.set(productionParameterTargetItem.getTargetItemName());
                });

                ProductionPlanTargetYear planTargetYear = new ProductionPlanTargetYear();
                planTargetYear.setId(IdUtil.randomUUID());
                planTargetYear.setPlanYear(productionPlanTargetYearDTO.getPlanYear());
                planTargetYear.setTargetItemId(productionPlanTargetYearDTO.getTargetItems().get(y).getId());
                planTargetYear.setTargetItemValue(productionPlanTargetYearDTO.getTargetItems().get(y).getValue());
                planTargetYear.setCreateUserId(productionPlanTargetYearDTO.getUserId());
                planTargetYear.setCreateUserName(productionPlanTargetYearDTO.getUserName());
                planTargetYear.setCreateTime(now);
                planTargetYear.setModifyUserId(productionPlanTargetYearDTO.getUserId());
                planTargetYear.setModifyUserName(productionPlanTargetYearDTO.getUserName());
                planTargetYear.setModifyTime(now);
                yearList.add(planTargetYear);

                ProductionRealOutputYear realOutputYear = new ProductionRealOutputYear();
                realOutputYear.setId(IdUtil.randomUUID());
                realOutputYear.setYear(productionPlanTargetYearDTO.getPlanYear());
                realOutputYear.setTargetItemId(productionPlanTargetYearDTO.getTargetItems().get(y).getId());
                realOutputYear.setTargetItemOutput(productionPlanTargetYearDTO.getTargetItems().get(y).getValue());
                realOutputYear.setTargetItemName(targetItemName.get());
                yearRealList.add(realOutputYear);

                for (int i = 1; i < MONTHS; i++) {
                    ProductionPlanTargetMonth planTargetMonth = new ProductionPlanTargetMonth();
                    planTargetMonth.setId(IdUtil.randomUUID());
                    planTargetMonth.setPlanYear(productionPlanTargetYearDTO.getPlanYear());
                    planTargetMonth.setPlanMonth(i);
                    planTargetMonth.setTargetItemId(productionPlanTargetYearDTO.getTargetItems().get(y).getId());
                    planTargetMonth.setTargetItemValue(productionPlanTargetYearDTO.getTargetItems().get(y).getValue());
                    planTargetMonth.setCreateUserId(productionPlanTargetYearDTO.getUserId());
                    planTargetMonth.setCreateUserName(productionPlanTargetYearDTO.getUserName());
                    planTargetMonth.setCreateTime(now);
                    planTargetMonth.setModifyUserId(productionPlanTargetYearDTO.getUserId());
                    planTargetMonth.setModifyUserName(productionPlanTargetYearDTO.getUserName());
                    planTargetMonth.setModifyTime(now);
                    monthList.add(planTargetMonth);

                    ProductionRealOutputMonth realOutputMonth = new ProductionRealOutputMonth();
                    realOutputMonth.setId(IdUtil.randomUUID());
                    realOutputMonth.setYear(productionPlanTargetYearDTO.getPlanYear());
                    realOutputMonth.setMonth(i);
                    realOutputMonth.setTargetItemId(productionPlanTargetYearDTO.getTargetItems().get(y).getId());
                    realOutputMonth.setTargetItemOutput(productionPlanTargetYearDTO.getTargetItems().get(y).getValue());
                    realOutputMonth.setTargetItemName(targetItemName.get());
                    monthRealList.add(realOutputMonth);

                    int days = DateUtil.lengthOfMonth(i, DateUtil.isLeapYear(productionPlanTargetYearDTO.getPlanYear()));
                    for (int j = 1; j <= days; j++) {
                        ProductionPlanTargetDate planTargetDate = new ProductionPlanTargetDate();
                        planTargetDate.setId(IdUtil.randomUUID());
                        planTargetDate.setPlanYear(productionPlanTargetYearDTO.getPlanYear());
                        planTargetDate.setPlanMonth(i);
                        planTargetDate.setPlanDate(j);
                        planTargetDate.setTargetItemId(productionPlanTargetYearDTO.getTargetItems().get(y).getId());
                        planTargetDate.setTargetItemValue(productionPlanTargetYearDTO.getTargetItems().get(y).getValue());
                        planTargetDate.setCreateUserId(productionPlanTargetYearDTO.getUserId());
                        planTargetDate.setCreateUserName(productionPlanTargetYearDTO.getUserName());
                        planTargetDate.setCreateTime(now);
                        planTargetDate.setModifyUserId(productionPlanTargetYearDTO.getUserId());
                        planTargetDate.setModifyUserName(productionPlanTargetYearDTO.getUserName());
                        planTargetDate.setModifyTime(now);
                        dayList.add(planTargetDate);

                        ProductionRealOutputDate realOutputDate = new ProductionRealOutputDate();
                        realOutputDate.setId(IdUtil.randomUUID());
                        realOutputDate.setYear(productionPlanTargetYearDTO.getPlanYear());
                        realOutputDate.setMonth(i);
                        realOutputDate.setDate(j);
                        realOutputDate.setTargetItemId(productionPlanTargetYearDTO.getTargetItems().get(y).getId());
                        realOutputDate.setTargetItemOutput(productionPlanTargetYearDTO.getTargetItems().get(y).getValue());
                        realOutputDate.setTargetItemName(targetItemName.get());
                        dayRealList.add(realOutputDate);
                    }
                }
            }
            planTargetDateService.saveBatch(dayList, dayList.size());
            planTargetMonthService.saveBatch(monthList, monthList.size());
            saveBatch(yearList, yearList.size());
            realOutputDateService.saveBatch(dayRealList, dayRealList.size());
            realOutputMonthService.saveBatch(monthRealList, monthRealList.size());
            realOutputYearService.saveBatch(yearRealList, yearRealList.size());;
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return AjaxResult.error(e.getMessage());
        } finally {
            rLock.unlock();
        }
        return AjaxResult.success();
    }

    public Object update(UpdateProductionPlanMonthDTO dto) {
        RLock rLock = redissonClient.getLock(getLock("MonthProductionTargetPlan", dto.getPlanYear(), dto.getPlanMonth()));
        rLock.lock();
        try {
            Date now = DateUtil.date();
            List<ProductionPlanTargetMonth> monthList = planTargetMonthService.list(new QueryWrapper<ProductionPlanTargetMonth>().lambda()
                    .eq(ProductionPlanTargetMonth::getPlanYear, dto.getPlanYear())
                    .eq(ProductionPlanTargetMonth::getPlanMonth, dto.getPlanMonth()));
            List<ProductionPlanTargetDate> dayList = planTargetDateService.list(new QueryWrapper<ProductionPlanTargetDate>().lambda()
                    .eq(ProductionPlanTargetDate::getPlanYear, dto.getPlanYear())
                    .eq(ProductionPlanTargetDate::getPlanMonth, dto.getPlanMonth()));
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
            for (ProductionPlanTargetMonth plan : monthList) {
                loop:
                for (int i = 0; i < dto.getTargetItems().size(); i++) {
                    if (dto.getTargetItems().get(i).getId().equals(plan.getTargetItemId())) {
                        plan.setTargetItemValue(dto.getTargetItems().get(i).getValue());
                        plan.setModifyTime(now);
                        plan.setModifyUserId(dto.getUserId());
                        plan.setModifyUserName(dto.getUserName());
                        break loop;
                    }
                }
            }
            for (ProductionPlanTargetDate plan : dayList) {
                loop:
                for (int i = 0; i < dto.getTargetItems().size(); i++) {
                    if (dto.getTargetItems().get(i).getId().equals(plan.getTargetItemId())) {
                        plan.setTargetItemValue(dto.getTargetItems().get(i).getValue());
                        plan.setModifyTime(now);
                        plan.setModifyUserId(dto.getUserId());
                        plan.setModifyUserName(dto.getUserName());
                        break loop;
                    }
                }
            }
            planTargetMonthService.updateBatchById(monthList, monthList.size());
            planTargetDateService.updateBatchById(dayList, dayList.size());
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return AjaxResult.error(e.getMessage());
        } finally {
            rLock.unlock();
        }
        return AjaxResult.success();
    }
}




