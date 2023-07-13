package com.jlkj.human.hd.controller;

import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.security.annotation.InnerAuth;
import com.jlkj.human.hd.service.ISaveTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 存班记录Controller
 *
 * @author 266861
 * @date 2023-07-10
 */
@RestController
@RequestMapping("/saveTime")
public class SaveTimeController extends BaseController
{
    @Autowired
    private ISaveTimeService saveTimeService;

    /**
     * 月度存班
     * @return 定时排班结果
     * @author 266861
     * @date 2023/7/4 9:40
     **/
    @InnerAuth
    @GetMapping("/scheduledZeroing")
    public AjaxResult scheduledZeroing(){
        return success(saveTimeService.scheduledZeroing());
    }

}
