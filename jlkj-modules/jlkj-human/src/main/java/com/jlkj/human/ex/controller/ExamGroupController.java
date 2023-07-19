package com.jlkj.human.ex.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.human.ex.domain.ExamGroup;
import com.jlkj.human.ex.service.IExamGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;


/**
 * 人员分组Controller
 * 
 * @author qnsdt
 * @date 2023-01-28
 */
@RestController
@RequestMapping("/group")
public class ExamGroupController extends BaseController
{
    @Autowired
    private IExamGroupService examGroupService;

    /**
     * 查询人员分组列表
     */
    @RequiresPermissions("@ss.hasPermi('human:group:list')")
    @GetMapping("/list")
    public TableDataInfo list(ExamGroup sysGroup)
    {
        startPage();
        List<ExamGroup> list = examGroupService.selectSysGroupList(sysGroup);
        return getDataTable(list);
    }

    @GetMapping("/choicelist/{examCode}")
    public TableDataInfo selectChoiceSysGroupList(@PathVariable String examCode) {
		// TODO Auto-generated method stub
    	startPage();
        List<ExamGroup> list = examGroupService.selectChoiceSysGroupList(examCode);
        return getDataTable(list);
	}
    /**
     * 导出人员分组列表
     */
    @RequiresPermissions("@ss.hasPermi('system:group:export')")
    @Log(title = "人员分组", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public void export(HttpServletResponse response, ExamGroup sysGroup)
    {
        List<ExamGroup> list = examGroupService.selectSysGroupList(sysGroup);
        ExcelUtil<ExamGroup> util = new ExcelUtil<ExamGroup>(ExamGroup.class);
        util.exportExcel(response,list, "人员分组数据");
    }

    /**
     * 获取人员分组详细信息
     */
    @RequiresPermissions("@ss.hasPermi('human:group:query')")
    @GetMapping(value = "/{groupCode}")
    public AjaxResult getInfo(@PathVariable("groupCode") String groupCode)
    {
        return AjaxResult.success(examGroupService.selectSysGroupById(groupCode));
    }

    /**
     * 新增人员分组
     */
    @RequiresPermissions("@ss.hasPermi('human:group:add')")
    @Log(title = "人员分组", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ExamGroup sysGroup)
    {
        return toAjax(examGroupService.insertSysGroup(sysGroup));
    }

    /**
     * 修改人员分组
     */
    @RequiresPermissions("@ss.hasPermi('human:group:edit')")
    @Log(title = "人员分组", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ExamGroup sysGroup)
    {
        return toAjax(examGroupService.updateSysGroup(sysGroup));
    }

    /**
     * 删除人员分组
     */
    @RequiresPermissions("@ss.hasPermi('human:group:remove')")
    @Log(title = "人员分组", businessType = BusinessType.DELETE)
	@DeleteMapping("/{groupIds}")
    public AjaxResult remove(@PathVariable Long[] groupIds)
    {
        return toAjax(examGroupService.deleteSysGroupByIds(groupIds));
    }
}
