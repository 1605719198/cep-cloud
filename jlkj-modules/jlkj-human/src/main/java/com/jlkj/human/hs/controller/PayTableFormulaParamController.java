package com.jlkj.human.hs.controller;

import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.human.hs.domain.PayTableFormulaParam;
import com.jlkj.human.hs.dto.PayTableFormulaParamDTO;
import com.jlkj.human.hs.service.IPayTableFormulaParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 公司薪资计算参数Controller
 *
 * @author 266861
 * @date 2023-05-15
 */
@RestController
@RequestMapping("/payFormulaParam")
public class PayTableFormulaParamController extends BaseController
{
    @Autowired
    private IPayTableFormulaParamService payTableFormulaParamService;

    /**
     * 查询公司薪资计算参数列表
     */
    @RequiresPermissions("human:payFormulaParam:list")
    @GetMapping("/list")
    public TableDataInfo list(PayTableFormulaParam payTableFormulaParam)
    {
        startPage();
        List<PayTableFormulaParam> list = payTableFormulaParamService.selectPayTableFormulaParamList(payTableFormulaParam);
        return getDataTable(list);
    }

    /**
     * 保存公司薪资计算参数
     */
    @RequiresPermissions("human:payFormulaParam:save")
    @Log(title = "公司薪资计算参数", businessType = BusinessType.INSERT)
    @PostMapping("/save")
    public AjaxResult add(@RequestBody PayTableFormulaParamDTO payTableFormulaParamDTO)
    {
        return toAjax(payTableFormulaParamService.savePayTableFormulaParam(payTableFormulaParamDTO));
    }

    /**
     * 复制公司薪资计算参数
     */
    @RequiresPermissions("human:payFormulaParam:copy")
    @Log(title = "公司薪资计算参数", businessType = BusinessType.INSERT)
    @PostMapping("/copy")
    public AjaxResult edit(@RequestBody PayTableFormulaParamDTO payTableFormulaParamDTO)
    {
        return toAjax(payTableFormulaParamService.copyPayTableFormulaParam(payTableFormulaParamDTO));
    }

}
