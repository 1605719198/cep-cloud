package com.jlkj.human.hs.controller;

import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.human.hs.domain.SalaryPunishment;
import com.jlkj.human.hs.domain.SalaryStatus;
import com.jlkj.human.hs.dto.SalaryPunishmentDTO;
import com.jlkj.human.hs.service.ISalaryPunishmentService;
import com.jlkj.human.hs.service.ISalaryStatusService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author HuangBing
 * @Description
 * @create 2023-05-31 11:00
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/salaryPunishment")
public class SalaryPunishmentController extends BaseController {

    private final ISalaryPunishmentService iSalaryPunishmentService;
    private final ISalaryStatusService iSalaryStatusService;

    /**
     * 查询薪资项目金额追补扣列表
     */
    @RequiresPermissions("human:salaryPunishment:list")
    @Log(title = "查询薪资项目金额追补扣列表",businessType = BusinessType.OTHER)
    @Operation(summary = "查询薪资项目金额追补扣列表")
    @GetMapping("/list")
    public Object listSalaryPunishment(SalaryPunishment salaryPunishment) {
        startPage();
        List<SalaryPunishment> list = iSalaryPunishmentService.lambdaQuery()
                .eq(SalaryPunishment::getCompId, salaryPunishment.getCompId())
                .eq(StringUtils.isNotBlank(salaryPunishment.getEmpId()), SalaryPunishment::getEmpId, salaryPunishment.getEmpId())
                .eq(SalaryPunishment::getYear, salaryPunishment.getYear())
                .eq(SalaryPunishment::getMonth, salaryPunishment.getMonth()).list();
        return AjaxResult.success("查询成功", getDataTable(list));
    }

    /**
     * 保存薪资项目金额追补扣列表
     */
    @RequiresPermissions("human:salaryPunishment:add")
    @Log(title = "保存薪资项目金额追补扣列表", businessType = BusinessType.INSERT)
    @Operation(summary = "保存薪资项目金额追补扣列表")
    @PostMapping
    public Object addSalaryPunishment(@RequestBody SalaryPunishmentDTO salaryPunishmentDTO) {
        List<SalaryStatus> list1 = iSalaryStatusService.lambdaQuery()
                .eq(SalaryStatus::getCompId, salaryPunishmentDTO.getCompId())
                .eq(SalaryStatus::getYear, salaryPunishmentDTO.getYear())
                .eq(SalaryStatus::getMonth, salaryPunishmentDTO.getMonth())
                .eq(StringUtils.isNotBlank(salaryPunishmentDTO.getPayType()), SalaryStatus::getPayTypeNo, salaryPunishmentDTO.getPayType()).list();
        if (new BigDecimal(list1.get(0).getStatus()).compareTo(new BigDecimal("20")) > 0) {
            return AjaxResult.error("不可以操作");
        } else {
            for (SalaryPunishment item : salaryPunishmentDTO.getSalaryPunishmentList()) {
                item.setPayType("5");
                item.setCreator(SecurityUtils.getNickName());
                item.setCreatorId(String.valueOf(SecurityUtils.getUserId()));
            }
            return iSalaryPunishmentService.saveOrUpdateBatch(salaryPunishmentDTO.getSalaryPunishmentList().stream().filter(item -> null != item.getEmpId()).filter(item -> !item.getEmpId().isEmpty()).collect(Collectors.toList()));
        }
    }


    /**
     * 删除薪资项目金额追补扣信息
     */
    @RequiresPermissions("human:salaryPunishment:remove")
    @Operation(summary = "删除薪资项目金额追补扣信息")
    @DeleteMapping("/{id}")
    @Log(title = "删除薪资项目金额追补扣信息", businessType = BusinessType.DELETE)
    public Object delSalaryPunishment(@PathVariable List<String> id) {
        for (String item : id) {
            SalaryPunishment salaryPunishment = iSalaryPunishmentService.lambdaQuery()
                    .eq(SalaryPunishment::getId, item).one();
            List<SalaryStatus> list1 = iSalaryStatusService.lambdaQuery()
                    .eq(SalaryStatus::getCompId, salaryPunishment.getCompId())
                    .eq(SalaryStatus::getYear, salaryPunishment.getYear())
                    .eq(SalaryStatus::getMonth, salaryPunishment.getMonth())
                    .eq(SalaryStatus::getPayTypeNo, salaryPunishment.getPayType()).list();
            if (new BigDecimal(list1.get(0).getStatus()).compareTo(new BigDecimal("20")) > 0) {
                return AjaxResult.error("不可以删除");
            } else {
                iSalaryPunishmentService.lambdaUpdate().eq(SalaryPunishment::getId, item).remove();
            }
        }
        return AjaxResult.success("删除成功");
    }

    @Log(title = "专业奖罚导入", businessType = BusinessType.IMPORT)
    @RequiresPermissions("human:salaryPunishment:import")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport, String compId, String date) throws Exception
    {
        ExcelUtil<SalaryPunishment> util = new ExcelUtil<SalaryPunishment>(SalaryPunishment.class);
        List<SalaryPunishment> salaryPunishmentList = util.importExcel(file.getInputStream());
        String operName = SecurityUtils.getUsername();
        String message = iSalaryPunishmentService.importUser(salaryPunishmentList, updateSupport, operName, compId, date);
        return success(message);
    }

    /**
     * 导入专业奖罚数据
     * @param response
     * @throws IOException
     */
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) throws IOException
    {
        ExcelUtil<SalaryPunishment> util = new ExcelUtil<SalaryPunishment>(SalaryPunishment.class);
        util.importTemplateExcel(response, "专业奖罚数据");
    }
}
