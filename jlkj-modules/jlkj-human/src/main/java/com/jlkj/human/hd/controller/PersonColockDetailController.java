package com.jlkj.human.hd.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.human.hd.domain.PersonColockDetail;
import com.jlkj.human.hd.dto.ClockworkPersonDTO;
import com.jlkj.human.hd.service.IPersonColockDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 人员卡钟明细Controller
 *
 * @author 266861
 * @date 2023-03-29
 */
@RestController
@RequestMapping("/personColockDetail")
public class PersonColockDetailController extends BaseController
{
    @Autowired
    private IPersonColockDetailService personColockDetailService;

    /**
     * 查询人员卡钟明细列表
     */
    @RequiresPermissions("human:personColockDetail:list")
    @GetMapping("/list")
    public TableDataInfo list(PersonColockDetail personColockDetail)
    {
        startPage();
        List<PersonColockDetail> list = personColockDetailService.selectPersonColockDetailList(personColockDetail);
        return getDataTable(list);
    }

    /**
     * 通过卡钟查询人员
     */
    @GetMapping("/listPerson/{macId}")
    public TableDataInfo listPerson(@PathVariable("macId") String macId)
    {
        List<ClockworkPersonDTO> list = personColockDetailService.queryPersonByColock(macId);
        return getDataTable(list);
    }

    /**
     * 导出人员卡钟明细列表
     */
    @RequiresPermissions("human:personColockDetail:export")
    @Log(title = "人员卡钟明细", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PersonColockDetail personColockDetail)
    {
        List<PersonColockDetail> list = personColockDetailService.selectPersonColockDetailList(personColockDetail);
        ExcelUtil<PersonColockDetail> util = new ExcelUtil<PersonColockDetail>(PersonColockDetail.class);
        util.exportExcel(response, list, "人员卡钟明细数据");
    }

    /**
     * 获取人员卡钟明细详细信息
     */
    @RequiresPermissions("human:personColockDetail:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(personColockDetailService.selectPersonColockDetailById(id));
    }

    /**
     * 新增人员卡钟明细
     */
    @RequiresPermissions("human:personColockDetail:add")
    @Log(title = "人员卡钟明细", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PersonColockDetail personColockDetail)
    {
        return toAjax(personColockDetailService.insertPersonColockDetail(personColockDetail));
    }

    /**
     * 修改人员卡钟明细
     */
    @RequiresPermissions("human:personColockDetail:edit")
    @Log(title = "人员卡钟明细", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PersonColockDetail personColockDetail)
    {
        return toAjax(personColockDetailService.updatePersonColockDetail(personColockDetail));
    }

    /**
     * 删除人员卡钟明细
     */
    @RequiresPermissions("human:personColockDetail:remove")
    @Log(title = "人员卡钟明细", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(personColockDetailService.deletePersonColockDetailByIds(ids));
    }
}
