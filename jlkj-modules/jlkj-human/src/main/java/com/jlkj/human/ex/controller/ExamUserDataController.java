package com.jlkj.human.ex.controller;

import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.human.ex.domain.dto.ExamUserData;
import com.jlkj.human.ex.service.IExamUserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;



@RestController
@RequestMapping("/userdata")
public class ExamUserDataController extends BaseController {
	@Autowired
    IExamUserDataService sysUserDataService;

    /**
     * 查询用户选择列表
     */
    @GetMapping("/list")
    public TableDataInfo list(ExamUserData sysUser)
    {
        startPage();
        List<ExamUserData> list = sysUserDataService.selectUserList(sysUser);
        return getDataTable(list);
    }
    
    @GetMapping("/choicelist")
    public TableDataInfo choiceList(ExamUserData sysUser)
    {
        startPage();
        List<ExamUserData> list = sysUserDataService.selectChoiceUserList(sysUser);
        return getDataTable(list);
    }
}
