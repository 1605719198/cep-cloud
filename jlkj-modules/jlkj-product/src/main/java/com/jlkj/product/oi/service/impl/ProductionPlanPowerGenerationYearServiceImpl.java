package com.jlkj.product.oi.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.product.oi.domain.ProductionParameterTargetItem;
import com.jlkj.product.oi.domain.ProductionPlanPowerGenerationDate;
import com.jlkj.product.oi.domain.ProductionPlanPowerGenerationMonth;
import com.jlkj.product.oi.domain.ProductionPlanPowerGenerationYear;
import com.jlkj.product.oi.dto.changelog.InsertChangeLogDTO;
import com.jlkj.product.oi.dto.productionplantarget.*;
import com.jlkj.product.oi.mapper.ProductionPlanPowerGenerationYearMapper;
import com.jlkj.product.oi.service.ChangeLogService;
import com.jlkj.product.oi.service.ProductionPlanPowerGenerationYearService;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.jlkj.product.oi.constants.RedissonUtil.getLock;
import static com.jlkj.product.oi.constants.DateConstants.MONTHS;

/**
* @author zyf
* @description 针对表【product_oi_plan_power_generation_year(计划管理--发电年指标计划)】的数据库操作Service实现
* @createDate 2022-04-29 13:46:55
*/
@Service
public class ProductionPlanPowerGenerationYearServiceImpl extends ServiceImpl<ProductionPlanPowerGenerationYearMapper, ProductionPlanPowerGenerationYear>
    implements ProductionPlanPowerGenerationYearService {

    @Autowired
    RedissonClient redissonClient;

    @Autowired
    ProductionParameterTargetItemServiceImpl productionParameterTargetItemService;

    @Autowired
    ProductionPlanPowerGenerationMonthServiceImpl planPowerGenerationMonthService;

    @Autowired
    ProductionPlanPowerGenerationDateServiceImpl planPowerGenerationDateService;

    @Resource
    ChangeLogService changeLogService;

    /**
     * 年发电计划查询
     * @param sql
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public List<Map<String, String>> getColToRowList(String sql) {
        return getBaseMapper().getList(sql);
    };

    /**
     * 查询日发电计划
     * @param dto
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public Object get(GetProductionPlanMonthDTO dto) {
        List<ProductionParameterTargetItem> itemlist =
                productionParameterTargetItemService.list(new QueryWrapper<ProductionParameterTargetItem>().lambda()
                        .eq(ProductionParameterTargetItem::getTargetItemTypeId, 2));
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
        sqlString.append(" from product_oi_plan_power_generation_month ");
        sqlString.append("where plan_year = ");
        sqlString.append(dto.getPlanYear());
        sqlString.append(" group by plan_month order by plan_year desc, plan_month asc");
        List<Map<String, String>> targetList = planPowerGenerationMonthService.getColToRowList(sqlString.toString());
        return AjaxResult.success(targetList);
    }

    /**
     * 查询单条月发电计划
     * @param dto
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public Object getOneCustom(GetProductionPlanOneMonthDTO dto) {
        List<ProductionParameterTargetItem> itemlist =
                productionParameterTargetItemService.list(new QueryWrapper<ProductionParameterTargetItem>().lambda()
                        .eq(ProductionParameterTargetItem::getTargetItemTypeId, 2));
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
        sqlString.append(" from product_oi_plan_power_generation_month ");
        sqlString.append(" where plan_year = ");
        sqlString.append(dto.getPlanYear());
        sqlString.append(" and plan_month = ");
        sqlString.append(dto.getPlanMonth());
        sqlString.append(" group by plan_month");
        List<Map<String, String>> targetList = planPowerGenerationMonthService.getColToRowList(sqlString.toString());
        return AjaxResult.success(targetList);
    }

    /**
     * 查询日发电计划
     * @param dto
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public Object getDate(GetProductionPlanDayDTO dto) {
        List<ProductionParameterTargetItem> itemlist =
                productionParameterTargetItemService.list(new QueryWrapper<ProductionParameterTargetItem>().lambda()
                        .eq(ProductionParameterTargetItem::getTargetItemTypeId, 2));
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
        sqlString.append(" from product_oi_plan_power_generation_date ");
        sqlString.append("where plan_year = ");
        sqlString.append(dto.getPlanYear());
        sqlString.append(" and plan_month = ");
        sqlString.append(dto.getPlanMonth());
        sqlString.append(" group by plan_date order by plan_year desc, plan_month asc, plan_date desc");
        List<Map<String, String>> targetList = planPowerGenerationDateService.getColToRowList(sqlString.toString());
        return AjaxResult.success(targetList);
    }

    private void saveNewLog(AddProductionPowerPlanYearDTO addProductionPowerPlanYearDTO) {
        StringBuilder content = new StringBuilder();
        content.append("新增：" + "[计划年度：").append(addProductionPowerPlanYearDTO.getPlanYear()).append("],").append("指标项列表：");
        for (int y = 0; y < addProductionPowerPlanYearDTO.getTargetItems().size(); y++) {
            ProductionParameterTargetItem productionParameterTargetItem = productionParameterTargetItemService.getById(addProductionPowerPlanYearDTO.getTargetItems().get(y).getId());
            content.append("[指标项目：").append(productionParameterTargetItem.getTargetItemName()).append("],")
                    .append("[措施及完成目标：").append(addProductionPowerPlanYearDTO.getTargetItems().get(y).getMeasuresAndGoals()).append("],")
                    .append("[责任人：").append(addProductionPowerPlanYearDTO.getTargetItems().get(y).getResponsiblePerson()).append("],")
                    .append("[指标值：").append(addProductionPowerPlanYearDTO.getTargetItems().get(y).getValue().stripTrailingZeros().toPlainString()).append("],")
            ;
        }
        InsertChangeLogDTO insertChangeLogDTO = new InsertChangeLogDTO();
        insertChangeLogDTO.setModuleName("生产管理");
        insertChangeLogDTO.setFunctionName("生产计划->发电指标");
        insertChangeLogDTO.setContent(content.toString());
        insertChangeLogDTO.setCreateUserId(addProductionPowerPlanYearDTO.getUserId());
        insertChangeLogDTO.setCreateUserName(addProductionPowerPlanYearDTO.getUserName());
        changeLogService.insertChangeLogData(insertChangeLogDTO);
    }

    private void saveUpdateLog(UpdateProductionPlanMonthDTO updateProductionPlanMonthDTO, List<ProductionPlanPowerGenerationMonth> monthList) {
        StringBuilder content = new StringBuilder();
        StringBuilder contentHeader = new StringBuilder();
        contentHeader.append("修改：" + "[计划年度：").append(updateProductionPlanMonthDTO.getPlanYear()).append("],").append("[计划月份：").append(updateProductionPlanMonthDTO.getPlanMonth()).append("],");
        for (ProductionPlanPowerGenerationMonth plan : monthList) {
            for (int i = 0; i < updateProductionPlanMonthDTO.getTargetItems().size(); i++) {
                if (updateProductionPlanMonthDTO.getTargetItems().get(i).getId().equals(plan.getTargetItemId())) {
                    if (plan.getTargetItemValue().compareTo(updateProductionPlanMonthDTO.getTargetItems().get(i).getValue()) != 0) {
                        ProductionParameterTargetItem productionParameterTargetItem = productionParameterTargetItemService.getById(updateProductionPlanMonthDTO.getTargetItems().get(i).getId());
                        content.append("[").append(productionParameterTargetItem.getTargetItemName()).append("：")
                                .append(plan.getTargetItemValue().stripTrailingZeros().toPlainString()).append("->")
                                .append(updateProductionPlanMonthDTO.getTargetItems().get(i).getValue().stripTrailingZeros().toPlainString()).append("],");
                    }
                    break;
                }
            }
        }
        if (content.length() > 0) {
            InsertChangeLogDTO insertChangeLogDTO = new InsertChangeLogDTO();
            insertChangeLogDTO.setModuleName("生产管理");
            insertChangeLogDTO.setFunctionName("生产计划->发电指标");
            insertChangeLogDTO.setContent(contentHeader.append(content).toString());
            insertChangeLogDTO.setCreateUserId(updateProductionPlanMonthDTO.getUserId());
            insertChangeLogDTO.setCreateUserName(updateProductionPlanMonthDTO.getUserName());
            changeLogService.insertChangeLogData(insertChangeLogDTO);
        }
    }

    /**
     * 新增年发电计划
     * @param productionPlanYearDTO
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Object saveCustom(AddProductionPowerPlanYearDTO productionPlanYearDTO) {
        RLock rLock = redissonClient.getLock(getLock("ProductionPlanPowerGenerationYear", productionPlanYearDTO.getPlanYear()));
        rLock.lock();
        try {
            Date now = DateUtil.date();
            List<ProductionPlanPowerGenerationYear> plans = list(new QueryWrapper<ProductionPlanPowerGenerationYear>().lambda()
                    .eq(ProductionPlanPowerGenerationYear::getPlanYear, productionPlanYearDTO.getPlanYear()));
            if (plans.size() > 0) {
                return AjaxResult.error("当年发电计划已存在");
            }

            BigDecimal lengthOfYear = new BigDecimal(DateUtil.lengthOfYear(productionPlanYearDTO.getPlanYear()));
            List<ProductionPlanPowerGenerationYear> yearList = new ArrayList<>();
            saveNewLog(productionPlanYearDTO);
            for (int i = 0; i < productionPlanYearDTO.getTargetItems().size(); i++) {
                BigDecimal pValue = productionPlanYearDTO.getTargetItems().get(i).getValue();
                ProductionPlanPowerGenerationYear planPowerGenerationYear = new ProductionPlanPowerGenerationYear();
                planPowerGenerationYear.setId(IdUtil.randomUUID());
                planPowerGenerationYear.setPlanYear(productionPlanYearDTO.getPlanYear());
                planPowerGenerationYear.setTargetItemId(productionPlanYearDTO.getTargetItems().get(i).getId());
                planPowerGenerationYear.setTargetItemValue(pValue);
                planPowerGenerationYear.setMeasuresAndGoals(productionPlanYearDTO.getTargetItems().get(i).getMeasuresAndGoals());
                planPowerGenerationYear.setResponsiblePerson(productionPlanYearDTO.getTargetItems().get(i).getResponsiblePerson());
                planPowerGenerationYear.setCreateUserId(productionPlanYearDTO.getUserId());
                planPowerGenerationYear.setCreateUserName(productionPlanYearDTO.getUserName());
                planPowerGenerationYear.setCreateTime(now);
                yearList.add(planPowerGenerationYear);

                BigDecimal dayOfOutput = pValue;
                if (productionPlanYearDTO.getTargetItems().get(i).getCalcUnitId() == 2) {
                    dayOfOutput = pValue.divide(lengthOfYear, 3, RoundingMode.HALF_UP);
                }
                List<ProductionPlanPowerGenerationMonth> monthList = new ArrayList<>();
                for (int m = 1; m < MONTHS; m++) {
                    int days = DateUtil.lengthOfMonth(m, DateUtil.isLeapYear(productionPlanYearDTO.getPlanYear()));
                    BigDecimal lengthOfMonth = new BigDecimal(days);
                    ProductionPlanPowerGenerationMonth planPowerGenerationMonth = new ProductionPlanPowerGenerationMonth();
                    planPowerGenerationMonth.setId(IdUtil.randomUUID());
                    planPowerGenerationMonth.setPlanYear(productionPlanYearDTO.getPlanYear());
                    planPowerGenerationMonth.setPlanMonth(m);
                    planPowerGenerationMonth.setTargetItemId(productionPlanYearDTO.getTargetItems().get(i).getId());
                    planPowerGenerationMonth.setTargetItemValue(dayOfOutput.multiply(lengthOfMonth));
                    planPowerGenerationMonth.setCreateUserId(productionPlanYearDTO.getUserId());
                    planPowerGenerationMonth.setCreateUserName(productionPlanYearDTO.getUserName());
                    planPowerGenerationMonth.setCreateTime(now);
                    planPowerGenerationMonth.setModifyUserId(productionPlanYearDTO.getUserId());
                    planPowerGenerationMonth.setModifyUserName(productionPlanYearDTO.getUserName());
                    planPowerGenerationMonth.setModifyTime(now);
                    monthList.add(planPowerGenerationMonth);

                    List<ProductionPlanPowerGenerationDate> dayList = new ArrayList<>();
                    for (int j = 1; j <= days; j++) {
                        ProductionPlanPowerGenerationDate planPowerGenerationDate = new ProductionPlanPowerGenerationDate();
                        planPowerGenerationDate.setId(IdUtil.randomUUID());
                        planPowerGenerationDate.setPlanYear(productionPlanYearDTO.getPlanYear());
                        planPowerGenerationDate.setPlanMonth(m);
                        planPowerGenerationDate.setPlanDate(j);
                        planPowerGenerationDate.setTargetItemId(productionPlanYearDTO.getTargetItems().get(i).getId());
                        planPowerGenerationDate.setTargetItemValue(dayOfOutput);
                        planPowerGenerationDate.setCreateUserId(productionPlanYearDTO.getUserId());
                        planPowerGenerationDate.setCreateUserName(productionPlanYearDTO.getUserName());
                        planPowerGenerationDate.setCreateTime(now);
                        planPowerGenerationDate.setModifyUserId(productionPlanYearDTO.getUserId());
                        planPowerGenerationDate.setModifyUserName(productionPlanYearDTO.getUserName());
                        planPowerGenerationDate.setModifyTime(now);
                        dayList.add(planPowerGenerationDate);
                    }
                    planPowerGenerationDateService.saveBatch(dayList, dayList.size());
                }
                planPowerGenerationMonthService.saveBatch(monthList, monthList.size());
            }
            saveBatch(yearList, yearList.size());
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return AjaxResult.error(e.getMessage());
        } finally {
            rLock.unlock();
        }
        return AjaxResult.success();
    }

    /**
     * 删除年计划
     * @param deleteProductionPlanYearDTO
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public AjaxResult delete(DeleteProductionPlanYearDTO deleteProductionPlanYearDTO) {
        List<ProductionPlanPowerGenerationYear> yearList = getBaseMapper().selectList(new QueryWrapper<ProductionPlanPowerGenerationYear>().lambda()
                .eq(ProductionPlanPowerGenerationYear::getPlanYear, deleteProductionPlanYearDTO.getPlanYear()));
        if (yearList.size() < 1) {
            return AjaxResult.error("当前年份计划不存在");
        }
        if (deleteProductionPlanYearDTO.getPlanYear() <= DateUtil.year(DateUtil.date())) {
            return AjaxResult.error("往年计划不能删除");
        }

        StringBuilder content = new StringBuilder();
        content.append("删除：" + "[计划年度：").append(deleteProductionPlanYearDTO.getPlanYear()).append("],").append("指标项列表：");
        for (ProductionPlanPowerGenerationYear year: yearList) {
            ProductionParameterTargetItem productionParameterTargetItem = productionParameterTargetItemService.getById(year.getTargetItemId());
            content.append("[指标项目：").append(productionParameterTargetItem.getTargetItemName()).append("],")
                    .append("[措施及完成目标：").append(year.getMeasuresAndGoals()).append("],")
                    .append("[责任人：").append(year.getResponsiblePerson()).append("],")
                    .append("[指标值：").append(year.getTargetItemValue().stripTrailingZeros().toPlainString()).append("],")
            ;
        }
        InsertChangeLogDTO insertChangeLogDTO = new InsertChangeLogDTO();
        insertChangeLogDTO.setModuleName("生产管理");
        insertChangeLogDTO.setFunctionName("生产计划->发电指标");
        insertChangeLogDTO.setContent(content.toString());
        insertChangeLogDTO.setCreateUserId(deleteProductionPlanYearDTO.getDeleteUserId());
        insertChangeLogDTO.setCreateUserName(deleteProductionPlanYearDTO.getDeleteUserName());
        changeLogService.insertChangeLogData(insertChangeLogDTO);

        List<ProductionPlanPowerGenerationMonth> monthList = planPowerGenerationMonthService.list(new QueryWrapper<ProductionPlanPowerGenerationMonth>().lambda()
                .eq(ProductionPlanPowerGenerationMonth::getPlanYear, deleteProductionPlanYearDTO.getPlanYear()));
        List<ProductionPlanPowerGenerationDate> dateList = planPowerGenerationDateService.list(new QueryWrapper<ProductionPlanPowerGenerationDate>().lambda()
                .eq(ProductionPlanPowerGenerationDate::getPlanYear, deleteProductionPlanYearDTO.getPlanYear()));
        planPowerGenerationDateService.removeBatchByIds(dateList, dateList.size());
        planPowerGenerationMonthService.removeBatchByIds(monthList, monthList.size());
        getBaseMapper().deleteBatchIds(yearList);
        return AjaxResult.success();
    }

    /**
     * 修改月发电计划
     * @param dto
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Object updateCustom(UpdateProductionPlanMonthDTO dto) {
        RLock rLock = redissonClient.getLock(getLock("ProductionPlanPowerGenerationMonth", dto.getPlanYear(), dto.getPlanMonth()));
        rLock.lock();
        try {
            Date now = DateUtil.date();
            List<ProductionPlanPowerGenerationMonth> monthList = planPowerGenerationMonthService.list(new QueryWrapper<ProductionPlanPowerGenerationMonth>().lambda()
                    .eq(ProductionPlanPowerGenerationMonth::getPlanYear, dto.getPlanYear())
                    .eq(ProductionPlanPowerGenerationMonth::getPlanMonth, dto.getPlanMonth()));
            List<ProductionPlanPowerGenerationDate> dayList = planPowerGenerationDateService.list(new QueryWrapper<ProductionPlanPowerGenerationDate>().lambda()
                    .eq(ProductionPlanPowerGenerationDate::getPlanYear, dto.getPlanYear())
                    .eq(ProductionPlanPowerGenerationDate::getPlanMonth, dto.getPlanMonth()));
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
            for (ProductionPlanPowerGenerationMonth plan : monthList) {
                loop:for (int i = 0; i < dto.getTargetItems().size(); i++) {
                    if (dto.getTargetItems().get(i).getId().equals(plan.getTargetItemId())) {
                        plan.setTargetItemValue(dto.getTargetItems().get(i).getValue());
                        plan.setModifyTime(now);
                        plan.setModifyUserId(dto.getUserId());
                        plan.setModifyUserName(dto.getUserName());
                        break loop;
                    }
                }
            }
            int days = DateUtil.lengthOfMonth(dto.getPlanMonth(), DateUtil.isLeapYear(dto.getPlanYear()));
            BigDecimal lengthOfMonth = new BigDecimal(days);
            for (ProductionPlanPowerGenerationDate plan : dayList) {
                loop:for (int i = 0; i < dto.getTargetItems().size(); i++) {
                    if (dto.getTargetItems().get(i).getId().equals(plan.getTargetItemId())) {
                        BigDecimal dayOfOutput = dto.getTargetItems().get(i).getValue();
                        if (dto.getTargetItems().get(i).getCalcUnitId() == 2) {
                            dayOfOutput = dto.getTargetItems().get(i).getValue().divide(lengthOfMonth, 3, RoundingMode.HALF_UP);
                        }
                        plan.setTargetItemValue(dayOfOutput);
                        plan.setModifyTime(now);
                        plan.setModifyUserId(dto.getUserId());
                        plan.setModifyUserName(dto.getUserName());
                        break loop;
                    }
                }
            }
            planPowerGenerationMonthService.updateBatchById(monthList, monthList.size());
            planPowerGenerationDateService.updateBatchById(dayList, dayList.size());
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return AjaxResult.error(e.getMessage());
        } finally {
            rLock.unlock();
        }
        return AjaxResult.success();
    }
}




