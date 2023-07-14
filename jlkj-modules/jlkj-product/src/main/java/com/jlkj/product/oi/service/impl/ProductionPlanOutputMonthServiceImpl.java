package com.jlkj.product.oi.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.common.core.exception.ServiceException;
import com.jlkj.product.oi.domain.*;
import com.jlkj.product.oi.dto.changelog.InsertChangeLogDTO;
import com.jlkj.product.oi.dto.productionplanoutputmonth.ListProductionPlanOutputMonthTargetItemDTO;
import com.jlkj.product.oi.dto.productionplantarget.*;
import com.jlkj.product.oi.mapper.ProductionParameterTargetItemMapper;
import com.jlkj.product.oi.mapper.ProductionPlanOutputDateMapper;
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
import java.util.stream.Collectors;

import static com.jlkj.product.oi.constants.DateConstants.MONTHS;
import static com.jlkj.product.oi.constants.RedissonUtil.getLock;

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
    ProductionPlanOutputYearServiceImpl planOutputYearService;

    @Resource
    ProductionPlanOutputDateMapper dateMapper;

    @Autowired
    ProductionPlanRepairServiceImpl planRepairService;

    @Autowired
    ProductionPlanTargetYearServiceImpl planTargetYearService;

    @Autowired
    ProductionYieldAnalysisYearServiceImpl yieldAnalysisYearService;

    @Autowired
    ProductionYieldAnalysisMonthServiceImpl yieldAnalysisMonthService;

    @Autowired
    ProductionYieldAnalysisDateServiceImpl yieldAnalysisDateService;

    @Resource
    ProductionParameterTargetItemMapper parameterTargetItemMapper;

    public List<Map<String, String>> getColToRowList(String sql) {
        return getBaseMapper().getList(sql);
    }

    @Resource
    ProductionParameterTargetItemMapper itemMapper;

    @Resource
    ChangeLogService changeLogService;

    /**
     * 生产管理-指标跟踪-图表-指标项(月计划)
     * @param listProductionPlanOutputMonthTargetItemDTO 查询条件dto
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<ListProductionPlanOutputMonthTargetItemVO> getProductionPlanOutputMonthTargetItemChartData(ListProductionPlanOutputMonthTargetItemDTO listProductionPlanOutputMonthTargetItemDTO) {
        return getBaseMapper().getProductionPlanOutputMonthTargetItemChartData(listProductionPlanOutputMonthTargetItemDTO);
    }

    /**
     * 查询月产量计划
     * @param dto
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public List<Map<String, String>> get(GetProductionPlanMonthDTO dto) {
        List<ProductionParameterTargetItem> itemlist =
                itemMapper.selectList(new QueryWrapper<ProductionParameterTargetItem>().lambda()
                        .eq(ProductionParameterTargetItem::getTargetItemTypeId, 1));
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
        return targetList;
    }

    /**
     * 查询单条月生产产量计划
     * @param dto
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public List<Map<String, String>> getOneCustom(GetProductionPlanOneMonthDTO dto) {
        List<ProductionParameterTargetItem> itemlist =
                itemMapper.selectList(new QueryWrapper<ProductionParameterTargetItem>().lambda()
                        .eq(ProductionParameterTargetItem::getTargetItemTypeId, 1));
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
        return targetList;
    }

    /**
     * 修改月产量计划
     * @param dto
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateCustom(UpdateProductionPlanMonthDTO dto) {
        RLock rLock = redissonClient.getLock(getLock("MonthProductionTargetPlan", dto.getPlanYear(), dto.getPlanMonth()));
        rLock.lock();
        try {
            Date now = DateUtil.date();
            List<ProductionPlanOutputMonth> monthList = list(new QueryWrapper<ProductionPlanOutputMonth>().lambda()
                    .eq(ProductionPlanOutputMonth::getPlanYear, dto.getPlanYear())
                    .eq(ProductionPlanOutputMonth::getPlanMonth, dto.getPlanMonth()));
            List<ProductionPlanOutputDate> dayList = dateMapper.selectList(new QueryWrapper<ProductionPlanOutputDate>().lambda()
                    .eq(ProductionPlanOutputDate::getPlanYear, dto.getPlanYear())
                    .eq(ProductionPlanOutputDate::getPlanMonth, dto.getPlanMonth()));
            List<ProductionYieldAnalysisMonth> monthAnaList = yieldAnalysisMonthService.list(new QueryWrapper<ProductionYieldAnalysisMonth>().lambda()
                    .eq(ProductionYieldAnalysisMonth::getYear, dto.getPlanYear())
                    .eq(ProductionYieldAnalysisMonth::getMonth, dto.getPlanMonth()));
            List<ProductionYieldAnalysisDate> dayAnaList = yieldAnalysisDateService.list(new QueryWrapper<ProductionYieldAnalysisDate>().lambda()
                    .eq(ProductionYieldAnalysisDate::getYear, dto.getPlanYear())
                    .eq(ProductionYieldAnalysisDate::getMonth, dto.getPlanMonth()));
            if (monthList.size() < 1) {
                throw new ServiceException("当月计划不存在或已删除");
            }
            if (dayList.size() < 1) {
                throw new ServiceException("当月的日计划不存在或已删除");
            }
            if (DateUtil.year(now) >= dto.getPlanYear() && (DateUtil.month(now) >= dto.getPlanMonth())) {
                throw new ServiceException("当月和当月之前的计划不能被修改");
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
                List<ProductionPlanOutputDate> dateRelationRepairlist = dateMapper.getOutputDateWithMonthRelationRepair(dto.getPlanYear(), dto.getPlanMonth());
                for (ProductionPlanOutputDate planOutputDate : dateRelationRepairlist) {
                    planOutputDate.setTargetItemOutput(new BigDecimal("0.000"));
                    dateMapper.updateById(planOutputDate);
                }
                List<ProductionYieldAnalysisDate> dateAnaRelationRepairlist = dateMapper.getAnaDateWithMonthRelationRepair(dto.getPlanYear(), dto.getPlanMonth());
                for (ProductionYieldAnalysisDate planAnaDate : dateAnaRelationRepairlist) {
                    planAnaDate.setProductionPlan(new BigDecimal("0.000"));
                }
                yieldAnalysisDateService.updateBatchById(dateAnaRelationRepairlist);
            }
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new ServiceException(e.getMessage());
        } finally {
            rLock.unlock();
        }
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
        for (ProductionPlanOutputDate plan : dayList) {
            dateMapper.updateById(plan);
        }
        yieldAnalysisMonthService.updateBatchById(monthAnaList);
        yieldAnalysisDateService.updateBatchById(dayAnaList);
    }

    private void saveNewLog(AddProductionPlanYearDTO productionPlanYearDTO) {
        StringBuilder content = new StringBuilder();
        content.append("新增：" + "[计划年度：").append(productionPlanYearDTO.getPlanYear()).append("],");
        for (int y = 0; y < productionPlanYearDTO.getTargetItems().size(); y++) {
            ProductionParameterTargetItem productionParameterTargetItem = itemMapper.selectById(productionPlanYearDTO.getTargetItems().get(y).getId());
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
                        ProductionParameterTargetItem productionParameterTargetItem = itemMapper.selectById(updateProductionPlanMonthDTO.getTargetItems().get(i).getId());
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

    /**
     * 新增年生产产量计划
     * @param productionPlanYearDTO
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveCustom(AddProductionPlanYearDTO productionPlanYearDTO) {
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
                throw new ServiceException("当年产量计划已存在");
            }
            List<ProductionPlanTargetYear> targetPlans = planTargetYearService.list(new QueryWrapper<ProductionPlanTargetYear>().lambda()
                    .eq(ProductionPlanTargetYear::getPlanYear, productionPlanYearDTO.getPlanYear()));
            if (targetPlans.size() < 1) {
                throw new ServiceException("当年指标计划不存在");
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
                List<ProductionPlanOutputDate> dateRelationRepairlist = dateMapper.getOutputDateRelationRepair(productionPlanYearDTO.getPlanYear());
                for (ProductionPlanOutputDate planOutputDate : dateRelationRepairlist) {
                    planOutputDate.setTargetItemOutput(new BigDecimal("0.000"));
                    dateMapper.updateById(planOutputDate);
                }
                List<ProductionYieldAnalysisDate> dateAnaRelationRepairlist = dateMapper.getAnaDateRelationRepair(productionPlanYearDTO.getPlanYear());
                for (ProductionYieldAnalysisDate planAnaDate : dateAnaRelationRepairlist) {
                    planAnaDate.setProductionPlan(new BigDecimal("0.000"));
                }
                yieldAnalysisDateService.updateBatchById(dateAnaRelationRepairlist);
            }
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new ServiceException(e.getMessage());
        } finally {
            rLock.unlock();
        }
    }

    /**
     * 删除年计划
     * @param deleteProductionPlanYearDTO
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(DeleteProductionPlanYearDTO deleteProductionPlanYearDTO) {
        List<ProductionPlanOutputYear> yearList = planOutputYearService.list(new QueryWrapper<ProductionPlanOutputYear>().lambda()
                .eq(ProductionPlanOutputYear::getPlanYear, deleteProductionPlanYearDTO.getPlanYear()));
        if (yearList.size() < 1) {
            throw new ServiceException("当前年份计划不存在");
//            ResultCode.EXISTSORNOT
        }
        if (deleteProductionPlanYearDTO.getPlanYear() <= DateUtil.year(DateUtil.date())) {
            throw new ServiceException("往年计划不能删除");
        }

        StringBuilder content = new StringBuilder();
        content.append("删除：" + "[计划年度：").append(deleteProductionPlanYearDTO.getPlanYear()).append("],");
        for (ProductionPlanOutputYear year: yearList) {
            ProductionParameterTargetItem productionParameterTargetItem = itemMapper.selectById(year.getTargetItemId());
            if (ObjectUtil.isNotNull(productionParameterTargetItem)) {
                content.append("[").append(productionParameterTargetItem.getTargetItemName()).append("：").append(year.getTargetItemOutput().stripTrailingZeros().toPlainString()).append("],");
            }
        }
        InsertChangeLogDTO insertChangeLogDTO = new InsertChangeLogDTO();
        insertChangeLogDTO.setModuleName("生产管理");
        insertChangeLogDTO.setFunctionName("生产计划->焦化产量");
        insertChangeLogDTO.setContent(content.toString());
        insertChangeLogDTO.setCreateUserId(deleteProductionPlanYearDTO.getDeleteUserId());
        insertChangeLogDTO.setCreateUserName(deleteProductionPlanYearDTO.getDeleteUserName());
        changeLogService.insertChangeLogData(insertChangeLogDTO);

        List<ProductionPlanOutputMonth> monthList = getBaseMapper().selectList(new QueryWrapper<ProductionPlanOutputMonth>().lambda()
                .eq(ProductionPlanOutputMonth::getPlanYear, deleteProductionPlanYearDTO.getPlanYear()));
        List<ProductionPlanOutputDate> dateList = dateMapper.selectList(new QueryWrapper<ProductionPlanOutputDate>().lambda()
                .eq(ProductionPlanOutputDate::getPlanYear, deleteProductionPlanYearDTO.getPlanYear()));
        List<String> collect = dateList.stream().map(item -> item.getId()).collect(Collectors.toList());
        dateMapper.deleteBatchIds(collect);
        getBaseMapper().deleteBatchIds(monthList);
        planOutputYearService.removeBatchByIds(yearList, yearList.size());
        yieldAnalysisYearService.remove(new LambdaQueryWrapper<ProductionYieldAnalysisYear>()
                .eq(ProductionYieldAnalysisYear::getYear, deleteProductionPlanYearDTO.getPlanYear()));
        getBaseMapper().delete(new LambdaQueryWrapper<ProductionPlanOutputMonth>()
                .eq(ProductionPlanOutputMonth::getPlanYear, deleteProductionPlanYearDTO.getPlanYear()));
        dateMapper.delete(new LambdaQueryWrapper<ProductionPlanOutputDate>()
                .eq(ProductionPlanOutputDate::getPlanYear, deleteProductionPlanYearDTO.getPlanYear()));
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
                    dateMapper.insert(planOutputDate);
                }
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




