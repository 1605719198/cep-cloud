package com.jlkj.human.hs.controller;

import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.human.hs.domain.PayTableFormulaParam;
import com.jlkj.human.hs.service.IPayTableFormulaParamService;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.page.TableDataInfo;

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
     * 导出公司薪资计算参数列表
     */
    @RequiresPermissions("human:payFormulaParam:export")
    @Log(title = "公司薪资计算参数", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PayTableFormulaParam payTableFormulaParam)
    {
        List<PayTableFormulaParam> list = payTableFormulaParamService.selectPayTableFormulaParamList(payTableFormulaParam);
        ExcelUtil<PayTableFormulaParam> util = new ExcelUtil<PayTableFormulaParam>(PayTableFormulaParam.class);
        util.exportExcel(response, list, "公司薪资计算参数数据");
    }

    /**
     * 获取公司薪资计算参数详细信息
     */
    @RequiresPermissions("human:payFormulaParam:query")
    @GetMapping(value = "/{uuid}")
    public AjaxResult getInfo(@PathVariable("uuid") String uuid)
    {
        return success(payTableFormulaParamService.selectPayTableFormulaParamByUuid(uuid));
    }

    /**
     * 新增公司薪资计算参数
     */
    @RequiresPermissions("human:payFormulaParam:add")
    @Log(title = "公司薪资计算参数", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PayTableFormulaParam payTableFormulaParam)
    {
        return toAjax(payTableFormulaParamService.insertPayTableFormulaParam(payTableFormulaParam));
    }

    /**
     * 修改公司薪资计算参数
     */
    @RequiresPermissions("human:payFormulaParam:edit")
    @Log(title = "公司薪资计算参数", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PayTableFormulaParam payTableFormulaParam)
    {
        return toAjax(payTableFormulaParamService.updatePayTableFormulaParam(payTableFormulaParam));
    }

    /**
     * 删除公司薪资计算参数
     */
    @RequiresPermissions("human:payFormulaParam:remove")
    @Log(title = "公司薪资计算参数", businessType = BusinessType.DELETE)
	@DeleteMapping("/{uuids}")
    public AjaxResult remove(@PathVariable String[] uuids)
    {
        return toAjax(payTableFormulaParamService.deletePayTableFormulaParamByUuids(uuids));
    }
}
