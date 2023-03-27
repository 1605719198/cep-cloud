package com.jlkj.safety.si.controller;


import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.safety.si.service.SafeWorkshopAccSectionService;
import com.jlkj.safety.si.utils.ResponseUtil;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author su
 * @Description 车间明细台账-工段
 * @create 2020-09-21 7:53
 */
@RestController
@RequestMapping("/safe/workshopacc/section")
@Slf4j
@Api(tags = "3.6 车间明细台账-工段")
public class SafeWorkshopAccSectionController {

    @Resource
    SafeWorkshopAccSectionService safeWorkshopAccSectionService;

    @ApiOperation(value = "工段-查询列表", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:current", value = "页码-从1开始", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:size", value = "每页行数-[web调用自动传，app不用传此参数固定为10]", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:order", value = "排序字段", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:orderby", value = "顺序-[asc]升序，[desc]降序，传空字符串则升序", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:workshop_name", value = "车间名称", required = false, dataTypeClass = String.class),
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class,
                    examples = @Example(
                            @ExampleProperty(
                                    "id:行标识\n" +
                                    "section_name:工段名称\n"
                            )
                    )
            )
    )
    @RequestMapping(value = "/list", method = RequestMethod.GET)

    public Object getSafeJobFirePageList(@RequestParam Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        return AjaxResult.success(safeWorkshopAccSectionService.getSafeWorkshopAccSectionPageList(params));
    }

    @ApiOperation(value = "工段-新增", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:section_name", value = "工段名称", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:workshop_name", value = "车间名称", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:create_user", value = "创建人", required = false, dataTypeClass = String.class),
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class)
    )
    @RequestMapping(value = "/insert", method = RequestMethod.POST, produces = "application/json")

    @Transactional(rollbackFor = Exception.class)
    public Object insertSafeJobFire(@RequestBody Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        try {
            return AjaxResult.success(safeWorkshopAccSectionService.insertSafeWorkshopAccSection(params));
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseUtil.toError(params, "工段增加失败");
        }
    }

    @ApiOperation(value = "工段-修改", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:id", value = "行标识", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:section_name", value = "工段名称", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:workshop_name", value = "车间名称", required = false, dataTypeClass = String.class),
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class)
    )
    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = "application/json")

    @Transactional(rollbackFor = Exception.class)
    public Object updateSafeJobFire(@RequestBody Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        try {
            return AjaxResult.success(safeWorkshopAccSectionService.updateSafeWorkshopAccSection(params));
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseUtil.toError(params, "工段修改失败");
        }
    }

    @ApiOperation(value = "工段-删除", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:id", value = "行标识", required = false, dataTypeClass = String.class),
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class)
    )
    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces = "application/json")

    public Object deleteSafeJobFire(@RequestBody Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        try {
            return AjaxResult.success(safeWorkshopAccSectionService.deleteSafeWorkshopAccSection(params));
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseUtil.toError(params, "工段删除失败");
        }
    }
}
