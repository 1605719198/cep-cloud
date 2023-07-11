package com.jlkj.human.ex.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.human.ex.domain.QuestionBank;
import com.jlkj.human.ex.service.IQuestionBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 题库Controller
 * @Description:
 * @author 116519
 * @date 2023-07-03
 * @version:V1.0
 */
@RestController
@RequestMapping("/bank")
public class QuestionBankController extends BaseController
{
    @Autowired
    private IQuestionBankService questionBankService;

    /**
     * 查询题库列表
     * @author jlkj
     * @date 2023-07-03
     * @param questionBank
     * @return
     */
    @RequiresPermissions("human:bank:list")
    @GetMapping("/list")
    public TableDataInfo list(QuestionBank questionBank)
    {
        startPage();
        List<QuestionBank> list = questionBankService.selectQuestionBankList(questionBank);
        return getDataTable(list);
    }

    /**
     * 导出题库列表
     * @author jlkj
     * @date 2023-07-03
     * @param questionBank
     * @return
     */
    @RequiresPermissions("human:bank:export")
    @Log(title = "题库", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, QuestionBank questionBank)
    {
        List<QuestionBank> list = questionBankService.selectQuestionBankList(questionBank);
        ExcelUtil<QuestionBank> util = new ExcelUtil<QuestionBank>(QuestionBank.class);
        util.exportExcel(response, list, "题库数据");
    }

    /**
     * 获取题库详细信息
     * @author jlkj
     * @date 2023-07-03
     * @param id
     * @return
     */
    @RequiresPermissions("human:bank:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(questionBankService.selectQuestionBankById(id));
    }

    /**
     * 新增题库
     * @author jlkj
     * @date 2023-07-03
     * @param questionBank
     * @return
     */
    @RequiresPermissions("human:bank:add")
    @Log(title = "题库", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody QuestionBank questionBank)
    {
        return toAjax(questionBankService.insertQuestionBank(questionBank));
    }

    /**
     * 修改题库
     * @author jlkj
     * @date 2023-07-03
     * @param questionBank
     * @return
     */
    @RequiresPermissions("human:bank:edit")
    @Log(title = "题库", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody QuestionBank questionBank)
    {
        return toAjax(questionBankService.updateQuestionBank(questionBank));
    }

    /**
     * 删除题库
     * @author jlkj
     * @date 2023-07-03
     * @param ids
     * @return
     */
    @RequiresPermissions("human:bank:remove")
    @Log(title = "题库", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(questionBankService.deleteQuestionBankByIds(ids));
    }

    /**
     * 查询题库 下拉框
     * @author jlkj
     * @date 2023-07-05
     * @param compId
     * @return
     */
    @GetMapping("/listByCompId/{compId}")
    public TableDataInfo listByCompId(@PathVariable String compId) {
        List<QuestionBank> list = questionBankService.selectQuestionBankListByCompId(compId);
        return getDataTable(list);
    }

}
