package com.jlkj.human.ex.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.human.ex.domain.ExamBankPicture;
import com.jlkj.human.ex.service.IExamBankPictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 题库图片Controller
 * 
 * @author 111191
 * @Date: 2023-07-11
 */
@RestController
@RequestMapping("/questions/bankpicture")
public class ExamBankPictureController extends BaseController
{
    @Autowired
    private IExamBankPictureService examBankPictureService;

    /**
     * 查询题库图片列表
     */
    @RequiresPermissions("questions:bankpicture:list")
    @GetMapping("/list")
    public TableDataInfo list(ExamBankPicture examBankPicture)
    {
        startPage();
        List<ExamBankPicture> list = examBankPictureService.selectExamBankPictureList(examBankPicture);
        return getDataTable(list);
    }

    /**
     * 导出题库图片列表
     */
    @RequiresPermissions("questions:bankpicture:export")
    @Log(title = "题库图片", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public void export(HttpServletResponse response,ExamBankPicture examBankPicture)
    {
        List<ExamBankPicture> list = examBankPictureService.selectExamBankPictureList(examBankPicture);
        ExcelUtil<ExamBankPicture> util = new ExcelUtil<ExamBankPicture>(ExamBankPicture.class);
        util.exportExcel(response,list, "题库图片数据");
    }

    /**
     * 获取题库图片详细信息
     */
    @RequiresPermissions("questions:bankpicture:query")
    @GetMapping(value = "/{photoCode}")
    public AjaxResult getInfo(@PathVariable("photoCode") String photoCode)
    {
        return AjaxResult.success(examBankPictureService.selectExamBankPictureById(photoCode));
    }

    /**
     * 新增题库图片
     */
    @RequiresPermissions("questions:bankpicture:add")
    @Log(title = "题库图片", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ExamBankPicture examBankPicture)
    {
        return toAjax(examBankPictureService.insertExamBankPicture(examBankPicture));
    }

    /**
     * 修改题库图片
     */
    @RequiresPermissions("questions:bankpicture:edit")
    @Log(title = "题库图片", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ExamBankPicture examBankPicture)
    {
        return toAjax(examBankPictureService.updateExamBankPicture(examBankPicture));
    }

    /**
     * 删除题库图片
     */
    @RequiresPermissions("questions:bankpicture:remove")
    @Log(title = "题库图片", businessType = BusinessType.DELETE)
	@DeleteMapping("/{photoCodes}")
    public AjaxResult remove(@PathVariable String[] photoCodes)
    {
        return toAjax(examBankPictureService.deleteExamBankPictureByIds(photoCodes));
    }
}
