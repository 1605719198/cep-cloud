package com.jlkj.human.hd.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.human.hd.domain.Holidaysetting;
import com.jlkj.human.hd.dto.CopyHolidaysettingDTO;
import com.jlkj.human.hd.service.IHolidaysettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 假别参数设定Controller
 *
 * @author 266861
 * @date 2023-03-21
 */
@RestController
@RequestMapping("/holidaysetting")
public class HolidaysettingController extends BaseController
{
    @Autowired
    private IHolidaysettingService holidaysettingService;

    /**
     * 查询假别参数设定列表
     */
    @RequiresPermissions("human:holidaysetting:list")
    @GetMapping("/list")
    public TableDataInfo list(Holidaysetting holidaysetting)
    {
        startPage();
        List<Holidaysetting> list = holidaysettingService.selectHolidaysettingList(holidaysetting);
        return getDataTable(list);
    }

    /**
     * 导出假别参数设定列表
     */
    @RequiresPermissions("human:holidaysetting:export")
    @Log(title = "假别参数设定", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Holidaysetting holidaysetting)
    {
        List<Holidaysetting> list = holidaysettingService.selectHolidaysettingList(holidaysetting);
        ExcelUtil<Holidaysetting> util = new ExcelUtil<Holidaysetting>(Holidaysetting.class);
        util.exportExcel(response, list, "假别参数设定数据");
    }

    /**
     * 获取假别参数设定详细信息
     */
    @RequiresPermissions("human:holidaysetting:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(holidaysettingService.selectHolidaysettingById(id));
    }

    /**
     * 新增假别参数设定
     */
    @RequiresPermissions("human:holidaysetting:add")
    @Log(title = "假别参数设定", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Holidaysetting holidaysetting) throws Exception
    {
        return toAjax(holidaysettingService.insertHolidaysetting(holidaysetting));
    }


    /**
     * 复制假别参数设定
     */
    @RequiresPermissions("human:holidaysetting:copy")
    @Log(title = "假别参数复制", businessType = BusinessType.INSERT)
    @PostMapping("/copyholidaysetting")
    public AjaxResult copy(@RequestBody CopyHolidaysettingDTO copyHolidaysettingDTO) throws Exception
    {
        return toAjax(holidaysettingService.copyHolidaysetting(copyHolidaysettingDTO));
    }

    /**
     * 修改假别参数设定
     */
    @RequiresPermissions("human:holidaysetting:edit")
    @Log(title = "假别参数设定", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Holidaysetting holidaysetting) throws Exception
    {
        return toAjax(holidaysettingService.updateHolidaysetting(holidaysetting));
    }

    /**
     * 删除假别参数设定
     */
    @RequiresPermissions("human:holidaysetting:remove")
    @Log(title = "假别参数设定", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(holidaysettingService.deleteHolidaysettingByIds(ids));
    }
}
