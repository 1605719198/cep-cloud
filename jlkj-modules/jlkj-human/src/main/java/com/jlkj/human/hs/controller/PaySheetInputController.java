package com.jlkj.human.hs.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.human.hs.domain.PaySheetInput;
import com.jlkj.human.hs.service.IPaySheetInputService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 各公司薪给输入Controller
 *
 * @author jiangbingchen
 * @date 2023-05-21
 */
@RestController
@RequestMapping("/paySheetInput")
public class PaySheetInputController extends BaseController
{
    @Autowired
    private IPaySheetInputService paySheetInputService;

    /**
     * 查询各公司薪给输入列表
     */
    @RequiresPermissions("human:paySheetInput:list")
    @GetMapping("/list")
    public TableDataInfo list(PaySheetInput paySheetInput)
    {
        startPage();
        List<PaySheetInput> list = paySheetInputService.selectPaySheetInputList(paySheetInput);
        return getDataTable(list);
    }

    /**
     * 导出各公司薪给输入列表
     */
    @RequiresPermissions("human:paySheetInput:export")
    @Log(title = "各公司薪给输入", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PaySheetInput paySheetInput)
    {
        List<PaySheetInput> list = paySheetInputService.selectPaySheetInputList(paySheetInput);
        ExcelUtil<PaySheetInput> util = new ExcelUtil<PaySheetInput>(PaySheetInput.class);
        util.exportExcel(response, list, "各公司薪给输入数据");
    }

    /**
     * 获取各公司薪给输入详细信息
     */
    @RequiresPermissions("human:paySheetInput:query")
    @GetMapping(value = "/{uuid}")
    public AjaxResult getInfo(@PathVariable("uuid") String uuid)
    {
        return success(paySheetInputService.selectPaySheetInputByUuid(uuid));
    }

    /**
     * 保存各公司薪给输入
     */
    @RequiresPermissions("human:paySheetInput:add")
    @Log(title = "各公司薪给输入", businessType = BusinessType.INSERT)
    @PostMapping
    public int save(@RequestBody List<PaySheetInput> paySheetInputList)
    {
        return paySheetInputService.insertPaySheetInput(paySheetInputList);
    }

    /**
     * 修改各公司薪给输入
     */
    @RequiresPermissions("human:paySheetInput:edit")
    @Log(title = "各公司薪给输入", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PaySheetInput paySheetInput)
    {
        return toAjax(paySheetInputService.updatePaySheetInput(paySheetInput));
    }

    /**
     * 删除各公司薪给输入
     */
    @RequiresPermissions("human:paySheetInput:remove")
    @Log(title = "各公司薪给输入", businessType = BusinessType.DELETE)
	@DeleteMapping("/{uuids}")
    public AjaxResult remove(@PathVariable String[] uuids)
    {
        return toAjax(paySheetInputService.deletePaySheetInputByUuids(uuids));
    }
}
