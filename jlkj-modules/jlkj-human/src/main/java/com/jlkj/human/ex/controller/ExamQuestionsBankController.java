package com.jlkj.human.ex.controller;

import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.core.utils.file.FileTypeUtils;
import com.jlkj.common.core.utils.file.MimeTypeUtils;
import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.human.ex.domain.ExamQuestionsBank;
import com.jlkj.human.ex.service.IExamBankPictureService;
import com.jlkj.human.ex.service.IExamQuestionsBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

/**
 * 题库管理Controller
 *
 * @author qnsdt
 * @Date: 2022-12-19
 */
@RestController
@RequestMapping("/questionsbank")
public class ExamQuestionsBankController extends BaseController {
    @Autowired
    private IExamQuestionsBankService examQuestionsBankService;
    @Autowired
    private IExamBankPictureService examBankPictureService;

    /**
     * 查询题库管理列表
     */
    @RequiresPermissions("human:questionsbank:list")
    @GetMapping("/list")
    public TableDataInfo list(ExamQuestionsBank examQuestionsBank) {
        startPage();
        List<ExamQuestionsBank> list = examQuestionsBankService.selectExamQuestionsBankList(examQuestionsBank);
        return getDataTable(list);
    }

    @GetMapping("/arraylist/{bankCodes}")
    public AjaxResult arrayList(@PathVariable("bankCodes") String bankCodes) {
        List<ExamQuestionsBank> list = examQuestionsBankService.selectExamQuestionsBankListByCode(bankCodes.split(","));
        return AjaxResult.success(list);
    }

    /**
     * 导出题库管理列表
     */
    @RequiresPermissions("human:questionsbank:export")
    @Log(title = "题库管理", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public void export(HttpServletResponse response, ExamQuestionsBank examQuestionsBank) {
        List<ExamQuestionsBank> list = examQuestionsBankService.selectExamQuestionsBankList(examQuestionsBank);
        ExcelUtil<ExamQuestionsBank> util = new ExcelUtil<ExamQuestionsBank>(ExamQuestionsBank.class);
        util.exportExcel(response, list, "题库管理数据");
    }

    /**
     * 获取题库管理详细信息
     */
    @RequiresPermissions("human:questionsbank:query")
    @GetMapping(value = "/{bankCode}")
    public AjaxResult getInfo(@PathVariable("bankCode") String bankCode) {
        return AjaxResult.success(examQuestionsBankService.selectExamQuestionsBankById(bankCode));
    }

    /**
     * 新增题库管理
     */
    @RequiresPermissions("human:questionsbank:add")
    @Log(title = "题库管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ExamQuestionsBank examQuestionsBank) {

        return toAjax(examQuestionsBankService.insertExamQuestionsBank(examQuestionsBank));
    }

    /**
     * 修改题库管理
     */
    @RequiresPermissions("human:questionsbank:edit")
    @Log(title = "题库管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ExamQuestionsBank examQuestionsBank) {
        System.out.println("examQuestionsBank:" + examQuestionsBank.toString());
        return toAjax(examQuestionsBankService.updateExamQuestionsBank(examQuestionsBank));
    }

    /**
     * 删除题库管理
     */
    @RequiresPermissions("human:questionsbank:remove")
    @Log(title = "题库管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{bankCodes}")
    public AjaxResult remove(@PathVariable String[] bankCodes) {
        return toAjax(examQuestionsBankService.deleteExamQuestionsBankByIds(bankCodes));
    }

    /**
     * 新增参数配置
     */
    @PostMapping("/uploadphoto/{bankcode}")
    public AjaxResult uploadPhoto(@PathVariable String bankcode, @RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            //取得文件扩展名，用于判断是否符合格式要求。
            String extension = FileTypeUtils.getExtension(file);

            if (!StringUtils.equalsAnyIgnoreCase(extension, MimeTypeUtils.IMAGE_EXTENSION)) {
                return error("文件格式不正确，请上传" + Arrays.toString(MimeTypeUtils.IMAGE_EXTENSION) + "格式");
            }
        }
        return AjaxResult.success(examBankPictureService.insertExamBankPicture(bankcode, file));
    }
}
