package com.jlkj.human.hd.controller;

import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.InnerAuth;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.human.hd.domain.ArrangeClassMaster;
import com.jlkj.human.hd.service.IArrangeClassMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 排班记录Controller
 *
 * @author 266861
 * @date 2023-04-10
 */
@RestController
@RequestMapping("/arrangeClassMaster")
public class ArrangeClassMasterController extends BaseController
{
    @Autowired
    private IArrangeClassMasterService arrangeClassMasterService;


    /**
     * 新增排班记录
     */
    @RequiresPermissions("human:shiftMode:add")
    @Log(title = "排班记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ArrangeClassMaster arrangeClassMaster)
    {
        return toAjax(arrangeClassMasterService.insertArrangeClassMaster(arrangeClassMaster));
    }

    /**
     * @Description 年底定时排班
     * @return 定时排班结果
     * @author 266861
     * @date 2023/7/4 9:40
     **/
    @InnerAuth
    @GetMapping("/scheduledShifts")
    public AjaxResult scheduledShifts(){
        return success(arrangeClassMasterService.scheduledShifts());
    }


}
