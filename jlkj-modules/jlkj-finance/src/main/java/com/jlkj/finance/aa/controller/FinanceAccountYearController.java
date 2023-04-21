package com.jlkj.finance.aa.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.finance.aa.domain.FinanceAccountYear;
import com.jlkj.finance.aa.domain.FinanceAccountYearPost;
import com.jlkj.finance.aa.dto.FinanceAccountYearDTO;
import com.jlkj.finance.aa.service.FinanceAccountYearPostService;
import com.jlkj.finance.aa.service.FinanceAccountYearService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

/**
 * @author wang'mai
 */
@RestController
@RequestMapping("/account/period")
public class FinanceAccountYearController {
    @Autowired
    private FinanceAccountYearPostService financeAccountYearPostService;
    @Autowired
    private FinanceAccountYearService financeAccountYearService;
    /**
     * 根据会计周期
     */
    @Operation(summary = "根据会计周期", description =" ")
    @RequiresPermissions("aa:period:queryAll")
    @GetMapping("/queryAll")
    @Log(title = "会计公司查询", businessType = BusinessType.OTHER)
    public Object queryAll(FinanceAccountYearDTO financeAccountYearDTO) {
        try {
            String compId = financeAccountYearDTO.getCompId();
            String accountPeriod = financeAccountYearDTO.getAccountPeriod();
            if (!StringUtils.isNotBlank(accountPeriod)){
                Calendar date = Calendar.getInstance();
                accountPeriod = String.valueOf(date.get(Calendar.YEAR));
            }

            Long pageNum = financeAccountYearDTO.getPageNum();
            Long pageSize = financeAccountYearDTO.getPageSize();
            LambdaQueryWrapper<FinanceAccountYear> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.like(StringUtils.isNotBlank(compId), FinanceAccountYear::getCompId, compId)
                    .likeRight(StringUtils.isNotBlank(accountPeriod), FinanceAccountYear::getAccountPeriod, accountPeriod);
            Page<FinanceAccountYear> page = financeAccountYearService.page(new Page<>(pageNum, pageSize), queryWrapper);

            long total = page.getTotal();
            //数据list集合
            List<FinanceAccountYear> records = page.getRecords();
            Map<String, Object> dataMap = new HashMap<>(2);
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
     * 会计公司新增
     */
    @Operation(summary = "会计公司新增", description = " ")
    @RequiresPermissions("aa:period:doAdd")
    @PostMapping("/doAdd")
    @Log(title = "会计公司新增", businessType = BusinessType.INSERT)
    public Object addFinanceCost(@RequestBody FinanceAccountYearDTO financeAccountYearDTO) {
        try {
            QueryWrapper<FinanceAccountYear> wrapper = new QueryWrapper<>();
            String accountYear = financeAccountYearDTO.getAccountYear();
            String stMonth = financeAccountYearDTO.getStMonth();
            String compId = financeAccountYearDTO.getCompId();
            FinanceAccountYear financeAccountYear = new FinanceAccountYear();
            FinanceAccountYearPost financeAccountYearPost = new FinanceAccountYearPost();
            wrapper.likeRight("account_period", accountYear)
                    .eq("comp_id", compId);
            List<FinanceAccountYear> list = financeAccountYearService.list(wrapper);
            if (list.size() >= 1) {
                return AjaxResult.error("您输入的会计年度系统中已存在，请重新输入！");
            }
            boolean save = false;
            int b = 12;
            String c = "01";
            for (int i = Integer.parseInt(stMonth); i <= b ; i++) {
                LocalDate firstDayOfCurrentDate = LocalDate.of(Integer.parseInt(accountYear),i, Integer.parseInt(c));
                String startDate = firstDayOfCurrentDate.toString();
                String[] startDateStartDates = startDate.split("-");
                String startYear = startDateStartDates[0].substring(startDateStartDates[0].length() - 2);
                financeAccountYearDTO.setManVoucherhrCurrentId("M" + startYear + startDateStartDates[1]);
                financeAccountYearDTO.setTransVoucherCurrentId("T" + startYear + startDateStartDates[1]);
                financeAccountYearDTO.setPayVoucherCurrentId("P" + startYear + startDateStartDates[1]);
                financeAccountYearDTO.setReceiveVoucherCurrentId("R" + startYear + startDateStartDates[1]);
                financeAccountYearDTO.setAccountPeriod(startDateStartDates[0] + startDateStartDates[1]);
                financeAccountYearDTO.setStartDate(startDateStartDates[0] + startDateStartDates[1] + c);
                LocalDate lastDayOfCurrentDate = firstDayOfCurrentDate.with(TemporalAdjusters.lastDayOfMonth());
                String terminationDate = lastDayOfCurrentDate.toString();
                String[] terminationDates = terminationDate.split("-");
                financeAccountYearDTO.setEndDate(terminationDates[0] + terminationDates[1] + terminationDates[2]);
                BeanUtils.copyProperties(financeAccountYearDTO, financeAccountYear);
                save = financeAccountYearService.save(financeAccountYear);
                financeAccountYearPost.setAccountPeriod(financeAccountYearDTO.getAccountPeriod());
                financeAccountYearPost.setCompId(financeAccountYearDTO.getCompId());
                financeAccountYearPost.setAllVoucherCurrentSrl(financeAccountYearDTO.getReceiveVoucherCurrentSrl());
                financeAccountYearPost.setId("");
                financeAccountYearPostService.save(financeAccountYearPost);
            }
            return AjaxResult.success(save);
        } catch (Exception e) {
            return AjaxResult.error();
        }
    }

    /**
     * 删除会计公司
     */
    @Operation(summary = "删除会计公司")
    @RequiresPermissions("aa:period:delete")
    @DeleteMapping("/delete")
    @Log(title = "会计公司删除", businessType = BusinessType.DELETE)
    public Object delete(@RequestParam String accountPeriodDelete, String compId){
        QueryWrapper<FinanceAccountYear> wrapper = new QueryWrapper<>();
        wrapper.likeRight("account_period", accountPeriodDelete)
                .eq("comp_id", compId);
        QueryWrapper<FinanceAccountYearPost> wrapperYearPost = new QueryWrapper<>();
        wrapperYearPost.likeRight("account_period", accountPeriodDelete)
                .eq("comp_id", compId);
        List<FinanceAccountYear> list = financeAccountYearService.list(wrapper);
        for (FinanceAccountYear financeAccountYearPost : list){
            if ("Y".equals(financeAccountYearPost.getIsClosed())){
                return AjaxResult.error("您删除的会计年度系统中存在关闭的会计周期！");
            }
        }
        boolean delete = financeAccountYearService.remove(wrapper);
        financeAccountYearPostService.remove(wrapperYearPost);
        return AjaxResult.success(delete);
    }
    /**
     * 状态修改
     *
     */
    @Operation(summary = "状态修改")
    @RequiresPermissions("aa:period:doEditYn")
    @PostMapping("/doEditYn")
    @Log(title = "状态修改", businessType = BusinessType.UPDATE)
    public Object updateEnergyCode(@RequestParam String isClosed ,String id) {
        try {
            FinanceAccountYear financeAccountYear;
            QueryWrapper<FinanceAccountYear> wrapperYearPost = new QueryWrapper<>();
            wrapperYearPost.eq("id", id);
            financeAccountYear =  financeAccountYearService.getOne(wrapperYearPost);
            financeAccountYear.setIsClosed(isClosed);
            boolean result = financeAccountYearService.updateById(financeAccountYear);
            if (result) {
                Map<String, String> resultMap = new HashMap<>(5);
                resultMap.put("id", financeAccountYear.getId());
                resultMap.put("update_user", financeAccountYear.getUpdateUser());
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
}
