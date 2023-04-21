package com.jlkj.human.hd.controller;

import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

import com.jlkj.human.hd.dto.PersonShiftCodeDTO;
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
import com.jlkj.human.hd.domain.ShiftCode;
import com.jlkj.human.hd.service.IShiftCodeService;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.page.TableDataInfo;

/**
 * 班次数据Controller
 *
 * @author 266861
 * @date 2023-04-06
 */
@RestController
@RequestMapping("/shiftCode")
public class ShiftCodeController extends BaseController
{
    @Autowired
    private IShiftCodeService shiftCodeService;

    /**
     * 查询班次数据列表
     */
    @RequiresPermissions("human:shiftCode:list")
    @GetMapping("/list")
    public TableDataInfo list(ShiftCode shiftCode)
    {
        startPage();
        List<ShiftCode> list = shiftCodeService.selectShiftCodeList(shiftCode);
        return getDataTable(list);
    }

    /**
     * 导出班次数据列表
     */
    @RequiresPermissions("human:shiftCode:export")
    @Log(title = "班次数据", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ShiftCode shiftCode)
    {
        List<ShiftCode> list = shiftCodeService.selectShiftCodeList(shiftCode);
        ExcelUtil<ShiftCode> util = new ExcelUtil<ShiftCode>(ShiftCode.class);
        util.exportExcel(response, list, "班次数据数据");
    }

    /**
     * 获取班次数据详细信息
     */
    @RequiresPermissions("human:shiftCode:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(shiftCodeService.selectShiftCodeById(id));
    }

    /**
     * 通过人员日期及工号获取班次详细信息
     */
    @PostMapping("/getShiftData")
    public AjaxResult getShiftData(PersonShiftCodeDTO personShiftCodeDTO){
        System.out.println(personShiftCodeDTO.toString());
        return success(shiftCodeService.selectShiftCodeByPerson(personShiftCodeDTO));
    }


    /**
     * 新增班次数据
     */
    @RequiresPermissions("human:shiftCode:add")
    @Log(title = "班次数据", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ShiftCode shiftCode)
    {
        return toAjax(shiftCodeService.insertShiftCode(shiftCode));
    }

    /**
     * 修改班次数据
     */
    @RequiresPermissions("human:shiftCode:edit")
    @Log(title = "班次数据", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ShiftCode shiftCode)
    {
        return toAjax(shiftCodeService.updateShiftCode(shiftCode));
    }

    /**
     * 删除班次数据
     */
    @RequiresPermissions("human:shiftCode:remove")
    @Log(title = "班次数据", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(shiftCodeService.deleteShiftCodeByIds(ids));
    }


}
