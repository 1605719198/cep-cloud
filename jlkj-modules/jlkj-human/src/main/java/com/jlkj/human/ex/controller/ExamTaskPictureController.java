package com.jlkj.human.ex.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.human.ex.domain.ExamTaskPicture;
import com.jlkj.human.ex.service.IExamTaskPictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 考试任务图片管理Controller
 * 
 * @author qnsdt
 * @date 2023-01-18
 */
@RestController
@RequestMapping("/exampicture")
public class ExamTaskPictureController extends BaseController
{
    @Autowired
    private IExamTaskPictureService examTaskPictureService;

    /**
     * 查询考试任务图片管理列表
     */
    @RequiresPermissions("human:exampicture:list")
    @GetMapping("/list")
    public TableDataInfo list(ExamTaskPicture examTaskPicture)
    {
        startPage();
        List<ExamTaskPicture> list = examTaskPictureService.selectExamTaskPictureList(examTaskPicture);
        return getDataTable(list);
    }

    /**
     * 导出考试任务图片管理列表
     */
    @RequiresPermissions("human:exampicture:export")
    @Log(title = "考试任务图片管理", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public void export(HttpServletResponse response,ExamTaskPicture examTaskPicture)
    {
        List<ExamTaskPicture> list = examTaskPictureService.selectExamTaskPictureList(examTaskPicture);
        ExcelUtil<ExamTaskPicture> util = new ExcelUtil<ExamTaskPicture>(ExamTaskPicture.class);
        util.exportExcel(response,list, "考试任务图片管理数据");
    }

    /**
     * 获取考试任务图片管理详细信息
     */
    @RequiresPermissions("human:exampicture:query")
    @GetMapping(value = "/{photoCode}")
    public AjaxResult getInfo(@PathVariable("photoCode") String photoCode)
    {
        return AjaxResult.success(examTaskPictureService.selectExamTaskPictureById(photoCode));
    }

    /**
     * 新增考试任务图片管理
     */
    @RequiresPermissions("human:exampicture:add")
    @Log(title = "考试任务图片管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ExamTaskPicture examTaskPicture)
    {
        return toAjax(examTaskPictureService.insertExamTaskPicture(examTaskPicture));
    }

    /**
     * 修改考试任务图片管理
     */
    @RequiresPermissions("human:exampicture:edit")
    @Log(title = "考试任务图片管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ExamTaskPicture examTaskPicture)
    {
        return toAjax(examTaskPictureService.updateExamTaskPicture(examTaskPicture));
    }

    /**
     * 删除考试任务图片管理
     */
    @RequiresPermissions("human:exampicture:remove")
    @Log(title = "考试任务图片管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{photoCodes}")
    public AjaxResult remove(@PathVariable String[] photoCodes)
    {
        return toAjax(examTaskPictureService.deleteExamTaskPictureByIds(photoCodes));
    }
}
