package com.jlkj.human.pa.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.human.hd.dto.BasisOptionsDTO;
import com.jlkj.human.hd.dto.OptionTypeDTO;
import com.jlkj.human.pa.domain.PerformanceBasis;
import com.jlkj.human.pa.service.IPerformanceBasisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;

/**
 * 绩效管理基本资料维护Controller
 *
 * @author 116519
 * @date 2023-05-17
 */
@RestController
@RequestMapping("/basis")
public class PerformanceBasisController extends BaseController
{
    @Autowired
    private IPerformanceBasisService performanceBasisService;

    /**
     * 查询绩效管理基本资料维护列表
     */
    @RequiresPermissions("human:basis:list")
    @GetMapping("/list")
    public TableDataInfo list(PerformanceBasis performanceBasis)
    {
        startPage();
        List<PerformanceBasis> list = performanceBasisService.selectPerformanceBasisList(performanceBasis);
        return getDataTable(list);
    }

    /**
     * 导出绩效管理基本资料维护列表
     */
    @RequiresPermissions("human:basis:export")
    @Log(title = "绩效管理基本资料维护", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PerformanceBasis performanceBasis)
    {
        List<PerformanceBasis> list = performanceBasisService.selectPerformanceBasisList(performanceBasis);
        ExcelUtil<PerformanceBasis> util = new ExcelUtil<PerformanceBasis>(PerformanceBasis.class);
        util.exportExcel(response, list, "绩效管理基本资料维护数据");
    }

    /**
     * 获取绩效管理基本资料维护详细信息
     */
    @RequiresPermissions("human:basis:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(performanceBasisService.selectPerformanceBasisById(id));
    }

    /**
     * 新增绩效管理基本资料维护
     */
    @RequiresPermissions("human:basis:add")
    @Log(title = "绩效管理基本资料维护", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PerformanceBasis performanceBasis)
    {
        return toAjax(performanceBasisService.insertPerformanceBasis(performanceBasis));
    }

    /**
     * 修改绩效管理基本资料维护
     */
    @RequiresPermissions("human:basis:edit")
    @Log(title = "绩效管理基本资料维护", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PerformanceBasis performanceBasis) throws Exception
    {
        return toAjax(performanceBasisService.updatePerformanceBasis(performanceBasis));
    }

    /**
     * 删除绩效管理基本资料维护
     */
    @RequiresPermissions("human:basis:remove")
    @Log(title = "绩效管理基本资料维护", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(performanceBasisService.deletePerformanceBasisByIds(ids));
    }

    /**
     * 获取绩效管理基本资料列表
     */
    @GetMapping("/treeselect")
    public AjaxResult treeselect(PerformanceBasis performanceBasis)
    {
        List<PerformanceBasis> performanceBasiss = performanceBasisService.selectPerformanceBasisList(performanceBasis);
        return AjaxResult.success(performanceBasisService.buildAttendenceTreeSelect(performanceBasiss));
    }

    /**
     * 获取绩效作业下拉选单列表
     */
    @GetMapping(value = "/getBasisOptions")
    public Object getBasisOptions(OptionTypeDTO optionType)
    {
        List<String> optionsType = optionType.getOptionsType();
        HashMap<String, List<BasisOptionsDTO>> map = new HashMap<>(16);
        for (String item : optionsType) {
            List<BasisOptionsDTO> list = performanceBasisService.selectBasisOptions(item);
            map.put(item, list);
        }
        return AjaxResult.success(map);
    }
}
