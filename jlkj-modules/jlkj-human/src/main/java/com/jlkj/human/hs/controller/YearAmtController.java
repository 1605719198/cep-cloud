package com.jlkj.human.hs.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.human.hs.domain.YearAmt;
import com.jlkj.human.hs.service.IYearAmtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 年收入维护Controller
 *
 * @author 266861
 * @date 2023-05-17
 */
@RestController
@RequestMapping("/yearAmt")
public class YearAmtController extends BaseController
{
    @Autowired
    private IYearAmtService yearAmtService;

    /**
     * 查询年收入维护列表
     */
    @RequiresPermissions("human:yearAmt:list")
    @GetMapping("/list")
    public TableDataInfo list(YearAmt yearAmt)
    {
        startPage();
        List<YearAmt> list = yearAmtService.selectYearAmtList(yearAmt);
        return getDataTable(list);
    }

    /**
     * 导出年收入维护列表
     */
    @RequiresPermissions("human:yearAmt:export")
    @Log(title = "年收入维护", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, YearAmt yearAmt)
    {
        List<YearAmt> list = yearAmtService.selectYearAmtList(yearAmt);
        ExcelUtil<YearAmt> util = new ExcelUtil<YearAmt>(YearAmt.class);
        util.exportExcel(response, list, "年收入维护数据");
    }

    /**
     * 获取年收入维护详细信息
     */
    @RequiresPermissions("human:yearAmt:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(yearAmtService.selectYearAmtById(id));
    }


    /**
     * 修改年收入维护
     */
    @RequiresPermissions("human:yearAmt:edit")
    @Log(title = "年收入维护", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody YearAmt yearAmt)
    {
        return toAjax(yearAmtService.updateYearAmt(yearAmt));
    }

    /**
     * 删除年收入维护
     */
    @RequiresPermissions("human:yearAmt:remove")
    @Log(title = "年收入维护", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(yearAmtService.deleteYearAmtByIds(ids));
    }

    /**
     * 保存年收入维护
     */
    @RequiresPermissions("human:yearAmt:add")
    @Log(title = "年收入保存", businessType = BusinessType.INSERT)
    @PostMapping("/save")
    public int save(@RequestBody List<YearAmt> yearAmtList)
    {
        return yearAmtService.insertYearAmt(yearAmtList);
    }


    /**
     * 导入年收入数据
     * @param file
     * @param updateSupport
     * @return
     * @throws Exception
     */
    @Log(title = "年收入资料导入", businessType = BusinessType.IMPORT)
    @RequiresPermissions("human:yearAmt:export")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<YearAmt> util = new ExcelUtil<YearAmt>(YearAmt.class);
        List<YearAmt> yearAmtList = util.importExcel(file.getInputStream());
        String operName = SecurityUtils.getUsername();
        String message = yearAmtService.importUser(yearAmtList, updateSupport, operName);
        return success(message);
    }

    /**
     * 导入年收入资料数据
     * @param response
     * @throws IOException
     */
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) throws IOException
    {
        ExcelUtil<YearAmt> util = new ExcelUtil<YearAmt>(YearAmt.class);
        util.importTemplateExcel(response, "年收入部门数据");
    }
}
