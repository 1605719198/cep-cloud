package com.jlkj.finance.aa.controller;

import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;

import com.jlkj.finance.aa.dto.FinanceAaIndexDTO;

import com.jlkj.finance.aa.service.IFinanceAaIndexTreeService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 系统选单-索引设定树Controller
 *
 * @author 265799
 * @date 2023-04-17
 */
@RestController
@RequestMapping("/index")
public class FinanceAaIndexTreeController extends BaseController {
    @Autowired
    private IFinanceAaIndexTreeService financeAaIndexTreeService;
    /**
     * 获取选单配置树
     */
    @Log(title = "选单配置树",businessType = BusinessType.OTHER)
    @Operation(summary = "选单配置树")
    @GetMapping("/tree")
    public Object getDepartmentTree(@RequestParam String companyId) {
        List<FinanceAaIndexDTO> list = financeAaIndexTreeService.getBaseInfoTreeList(companyId);

        return AjaxResult.success(list);
    }

}
