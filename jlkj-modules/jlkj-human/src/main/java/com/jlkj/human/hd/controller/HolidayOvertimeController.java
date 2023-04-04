package com.jlkj.human.hd.controller;

import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.core.utils.bean.BeanUtils;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.human.hd.domain.HolidayOvertime;
import com.jlkj.human.hd.dto.HolidayOvertimeDTO;
import com.jlkj.human.hd.service.IHolidayOvertimeService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author HuangBing
 * @Description
 * @create 2023-04-3 11:32
 */
@RestController
@RequestMapping("/holidayOvertime")
public class HolidayOvertimeController extends BaseController {

    @Autowired
    IHolidayOvertimeService iHolidayOvertimeService;

    /**
     * 查询倒班人员法定假日加班资料列表
     */
    @RequiresPermissions("human:holidayOvertime:list")
    @Log(title = "查询倒班人员法定假日加班资料列表",businessType = BusinessType.OTHER)
    @Operation(summary = "查询倒班人员法定假日加班资料列表")
    @GetMapping("/list")
    public Object listHolidayOvertime(HolidayOvertimeDTO holidayOvertimeDTO) {
        startPage();
        HolidayOvertime holidayOvertime = new HolidayOvertime();
        BeanUtils.copyProperties(holidayOvertimeDTO, holidayOvertime);
        List<HolidayOvertime> list = iHolidayOvertimeService.lambdaQuery()
                .eq(StringUtils.isNotBlank(holidayOvertime.getCompId()), HolidayOvertime::getCompId, holidayOvertime.getCompId())
                .eq(HolidayOvertime::getEmpNo, holidayOvertime.getEmpNo())
                .between(HolidayOvertime::getLegalHolDate, holidayOvertimeDTO.getStartTime(), holidayOvertimeDTO.getEndTime()).list();
        return AjaxResult.success("查询成功", getDataTable(list));
    }
}
