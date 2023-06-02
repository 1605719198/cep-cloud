package com.jlkj.human.hs.controller;

import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.human.hs.domain.SalaryAdd;
import com.jlkj.human.hs.domain.SalaryStatus;
import com.jlkj.human.hs.dto.SalaryAddDTO;
import com.jlkj.human.hs.service.ISalaryAddService;
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
@RequestMapping("/salaryAdd")
public class SalaryAddController extends BaseController {

    private final ISalaryAddService iSalaryAddService;
    private final ISalaryStatusService iSalaryStatusService;

    /**
     * 查询薪资项目金额追补扣列表
     */
    @RequiresPermissions("human:salaryAdd:list")
    @Log(title = "查询薪资项目金额追补扣列表",businessType = BusinessType.OTHER)
    @Operation(summary = "查询薪资项目金额追补扣列表")
    @GetMapping("/list")
    public Object listSalaryAdd(SalaryAdd salaryAdd) {
        startPage();
        List<SalaryAdd> list = iSalaryAddService.lambdaQuery()
                .eq(SalaryAdd::getCompId, salaryAdd.getCompId())
                .eq(StringUtils.isNotBlank(salaryAdd.getPayType()), SalaryAdd::getPayType, salaryAdd.getPayType())
                .eq(StringUtils.isNotBlank(salaryAdd.getEmpId()), SalaryAdd::getEmpId, salaryAdd.getEmpId())
                .eq(SalaryAdd::getYear, salaryAdd.getYear())
                .eq(SalaryAdd::getMonth, salaryAdd.getMonth()).list();
        return AjaxResult.success("查询成功", getDataTable(list));
    }

    /**
     * 保存薪资项目金额追补扣列表
     */
    @RequiresPermissions("human:salaryAdd:add")
    @Log(title = "保存薪资项目金额追补扣列表", businessType = BusinessType.INSERT)
    @Operation(summary = "保存薪资项目金额追补扣列表")
    @PostMapping
    public Object addSalaryAdd(@RequestBody SalaryAddDTO salaryAddDTO) {
        List<SalaryStatus> list1 = iSalaryStatusService.lambdaQuery()
                .eq(SalaryStatus::getCompId, salaryAddDTO.getCompId())
                .eq(SalaryStatus::getYear, salaryAddDTO.getYear())
                .eq(SalaryStatus::getMonth, salaryAddDTO.getMonth())
                .eq(StringUtils.isNotBlank(salaryAddDTO.getPayType()), SalaryStatus::getPayTypeNo, salaryAddDTO.getPayType()).list();
        if (new BigDecimal(list1.get(0).getStatus()).compareTo(new BigDecimal("20")) > 0) {
            return AjaxResult.error("不可以操作");
        } else {
            for (SalaryAdd item : salaryAddDTO.getSalaryAddList()) {
                item.setCreator(SecurityUtils.getNickName());
                item.setCreatorId(String.valueOf(SecurityUtils.getUserId()));
            }
            return iSalaryAddService.saveOrUpdateBatch(salaryAddDTO.getSalaryAddList().stream().filter(item -> null != item.getEmpId()).filter(item -> !item.getEmpId().isEmpty()).collect(Collectors.toList()));
        }
    }


    /**
     * 删除薪资项目金额追补扣信息
     */
    @RequiresPermissions("human:salaryAdd:remove")
    @Operation(summary = "删除薪资项目金额追补扣信息")
    @DeleteMapping("/{id}")
    @Log(title = "删除薪资项目金额追补扣信息", businessType = BusinessType.DELETE)
    public Object delSalaryAdd(@PathVariable List<String> id) {
        for (String item : id) {
            SalaryAdd salaryAdd = iSalaryAddService.lambdaQuery()
                    .eq(SalaryAdd::getId, item).one();
            List<SalaryStatus> list1 = iSalaryStatusService.lambdaQuery()
                    .eq(SalaryStatus::getCompId, salaryAdd.getCompId())
                    .eq(SalaryStatus::getYear, salaryAdd.getYear())
                    .eq(SalaryStatus::getMonth, salaryAdd.getMonth())
                    .eq(SalaryStatus::getPayTypeNo, salaryAdd.getPayType()).list();
            if (new BigDecimal(list1.get(0).getStatus()).compareTo(new BigDecimal("20")) > 0) {
                return AjaxResult.error("不可以删除");
            } else {
                iSalaryAddService.lambdaUpdate().eq(SalaryAdd::getId, item).remove();
            }
        }
        return AjaxResult.success("删除成功");
    }

    @Log(title = "薪资项目追补导入", businessType = BusinessType.IMPORT)
    @RequiresPermissions("human:salaryAdd:import")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport, String compId, String date) throws Exception
    {
        ExcelUtil<SalaryAdd> util = new ExcelUtil<SalaryAdd>(SalaryAdd.class);
        List<SalaryAdd> salaryAddList = util.importExcel(file.getInputStream());
        String operName = SecurityUtils.getUsername();
        String message = iSalaryAddService.importUser(salaryAddList, updateSupport, operName, compId, date);
        return success(message);
    }

    @Log(title = "专项附加追补导入", businessType = BusinessType.IMPORT)
    @RequiresPermissions("human:salaryAdd:import")
    @PostMapping("/importData1")
    public AjaxResult importData1(MultipartFile file, boolean updateSupport, String compId, String date) throws Exception
    {
        ExcelUtil<SalaryAdd> util = new ExcelUtil<SalaryAdd>(SalaryAdd.class);
        List<SalaryAdd> salaryAddList = util.importExcel(file.getInputStream());
        String operName = SecurityUtils.getUsername();
        String message = iSalaryAddService.importUser1(salaryAddList, updateSupport, operName, compId, date);
        return success(message);
    }

    /**
     * 导入薪资项目追补数据
     * @param response
     * @throws IOException
     */
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) throws IOException
    {
        ExcelUtil<SalaryAdd> util = new ExcelUtil<SalaryAdd>(SalaryAdd.class);
        util.importTemplateExcel(response, "薪资项目追补数据");
    }
}
