package com.jlkj.human.ex.controller;
import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.human.ex.domain.Questions;
import com.jlkj.human.ex.service.IQuestionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 试题Controller
 * @Description:
 * @author 116519
 * @date 2023-07-04
 * @version:V1.0
 */
@RestController
@RequestMapping("/questions")
public class QuestionsController extends BaseController
{
    @Autowired
    private IQuestionsService questionsService;

    /**
     * 查询试题列表
     * @author 116519
     * @date 2023-07-04
     * @param questions
     * @return
     */
    @RequiresPermissions("human:questions:list")
    @GetMapping("/list")
    public TableDataInfo list(Questions questions)
    {
        startPage();
        List<Questions> list = questionsService.selectQuestionsList(questions);
        return getDataTable(list);
    }

    /**
     * 导出试题列表
     * @author 116519
     * @date 2023-07-04
     * @param questions
     * @return
     */
    @RequiresPermissions("human:questions:export")
    @Log(title = "试题", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Questions questions)
    {
        List<Questions> list = questionsService.selectQuestionsList(questions);
        ExcelUtil<Questions> util = new ExcelUtil<Questions>(Questions.class);
        util.exportExcel(response, list, "试题数据");
    }

    /**
     * 获取试题详细信息
     * @author 116519
     * @date 2023-07-04
     * @param questions
     * @return
     */
    @RequiresPermissions("human:questions:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(questionsService.selectQuestionsById(id));
    }

    /**
     * 新增试题
     * @author 116519
     * @date 2023-07-04
     * @param questions
     * @return
     */
    @RequiresPermissions("human:questions:add")
    @Log(title = "试题", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Questions questions)
    {
        return toAjax(questionsService.insertQuestions(questions));
    }

    /**
     * 修改试题
     * @author 116519
     * @date 2023-07-04
     * @param questions
     * @return
     */
    @RequiresPermissions("human:questions:edit")
    @Log(title = "试题", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Questions questions)
    {
        return toAjax(questionsService.updateQuestions(questions));
    }

    /**
     * 删除试题
     * @author 116519
     * @date 2023-07-04
     * @param questions
     * @return
     */
    @RequiresPermissions("human:questions:remove")
    @Log(title = "试题", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(questionsService.deleteQuestionsByIds(ids));
    }
}
