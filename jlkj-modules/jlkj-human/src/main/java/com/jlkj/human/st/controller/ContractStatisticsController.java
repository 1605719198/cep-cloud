package com.jlkj.human.st.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.human.st.domain.ContractStatistics;
import com.jlkj.human.st.service.IContractStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 劳动合同统计分析Controller
 * @Description:
 * @author 267383
 * @date 2023-07-17
 * @version:V1.0
 */
@RestController
@RequestMapping("/contractStatistics")
public class ContractStatisticsController extends BaseController
{
    @Autowired
    private IContractStatisticsService contractStatisticsService;

    /**
     * 查询劳动合同统计分析列表
     * @author 267383
     * @date 2023-07-17
     * @param contractStatistics
     * @return
     */
    @RequiresPermissions("human:contractStatistics:list")
    @GetMapping("/list")
    public TableDataInfo list(ContractStatistics contractStatistics)
    {
        startPage();
        List<ContractStatistics> list = contractStatisticsService.selectContractStatisticsList(contractStatistics);
        return getDataTable(list);
    }

    /**
     * 导出劳动合同统计分析列表
     * @author 267383
     * @date 2023-07-17
     * @param contractStatistics
     * @return
     */
    @RequiresPermissions("human:contractStatistics:export")
    @Log(title = "劳动合同统计分析", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ContractStatistics contractStatistics)
    {
        List<ContractStatistics> list = contractStatisticsService.selectContractStatisticsList(contractStatistics);
        ExcelUtil<ContractStatistics> util = new ExcelUtil<ContractStatistics>(ContractStatistics.class);
        util.exportExcel(response, list, "劳动合同统计分析数据");
    }

    /**
     * 获取劳动合同统计分析详细信息
     * @author 267383
     * @date 2023-07-17
     * @param compId
     * @return
     */
    @RequiresPermissions("human:contractStatistics:query")
    @GetMapping(value = "/{compId}")
    public AjaxResult getInfo(@PathVariable("compId") String compId)
    {
        return success(contractStatisticsService.selectContractStatisticsByCompId(compId));
    }

    /**
     * 新增劳动合同统计分析
     * @author 267383
     * @date 2023-07-17
     * @param contractStatistics
     * @return
     */
    @RequiresPermissions("human:contractStatistics:add")
    @Log(title = "劳动合同统计分析", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ContractStatistics contractStatistics)
    {
        return toAjax(contractStatisticsService.insertContractStatistics(contractStatistics));
    }

    /**
     * 修改劳动合同统计分析
     * @author 267383
     * @date 2023-07-17
     * @param contractStatistics
     * @return
     */
    @RequiresPermissions("human:contractStatistics:edit")
    @Log(title = "劳动合同统计分析", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ContractStatistics contractStatistics)
    {
        return toAjax(contractStatisticsService.updateContractStatistics(contractStatistics));
    }

    /**
     * 删除劳动合同统计分析
     * @author 267383
     * @date 2023-07-17
     * @param compIds
     * @return
     */
    @RequiresPermissions("human:contractStatistics:remove")
    @Log(title = "劳动合同统计分析", businessType = BusinessType.DELETE)
	@DeleteMapping("/{compIds}")
    public AjaxResult remove(@PathVariable String[] compIds)
    {
        return toAjax(contractStatisticsService.deleteContractStatisticsByCompIds(compIds));
    }
}
