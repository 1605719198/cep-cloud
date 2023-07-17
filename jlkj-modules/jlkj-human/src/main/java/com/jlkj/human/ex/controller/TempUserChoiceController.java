package com.jlkj.human.ex.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.human.ex.domain.TempUserChoice;
import com.jlkj.human.ex.service.ITempUserChoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;


/**
 * 用户选择Controller
 * 
 * @author qnsdt
 * @date 2023-01-31
 */
@RestController
@RequestMapping("/userchoice")
public class TempUserChoiceController extends BaseController
{
    @Autowired
    private ITempUserChoiceService tempUserChoiceService;

    /**
     * 查询用户选择列表
     */
    @GetMapping("/list")
    public TableDataInfo list(TempUserChoice tempUserChoice)
    {
        startPage();
        List<TempUserChoice> list = tempUserChoiceService.selectTempUserChoiceList(tempUserChoice);
        return getDataTable(list);
    }

    /**
     * 导出用户选择列表
     */
    @GetMapping("/export")
    public void export(HttpServletResponse response, TempUserChoice tempUserChoice)
    {
        List<TempUserChoice> list = tempUserChoiceService.selectTempUserChoiceList(tempUserChoice);
        ExcelUtil<TempUserChoice> util = new ExcelUtil<TempUserChoice>(TempUserChoice.class);
        util.exportExcel(response,list, "考试成绩数据");
    }

    /**
     * 获取用户选择详细信息
     */
    @GetMapping(value = "/{choiceCode}")
    public AjaxResult getInfo(@PathVariable("choiceCode") String choiceCode)
    {
        return AjaxResult.success(tempUserChoiceService.selectTempUserChoiceById(choiceCode));
    }

    /**
     * 新增用户选择
     */
    @PostMapping
    public AjaxResult add(@RequestBody TempUserChoice tempUserChoice)
    {
        return toAjax(tempUserChoiceService.insertTempUserChoice(tempUserChoice));
    }
    
    /**
     * 新增用户选择
     */
    @PostMapping("/batch")
    public AjaxResult batchAdd(@RequestBody List<TempUserChoice> userList)
    {
        return toAjax(tempUserChoiceService.batchInsertTempUser(userList));
    }

    /**
     * 修改用户选择
     */
    @PostMapping("/update")
    public AjaxResult edit(@RequestBody TempUserChoice tempUserChoice)
    {
        return toAjax(tempUserChoiceService.updateTempUserChoice(tempUserChoice));
    }

    @PostMapping("/remove")
    public AjaxResult remove(@RequestBody List<TempUserChoice> userList)
    {
        return toAjax(tempUserChoiceService.deleteTempUserChoice(userList));
    }

}
