package com.jlkj.safety.si.controller;


import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.safety.si.service.SafeWorkshopAccService;
import com.jlkj.safety.si.utils.ResponseUtil;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author su
 * @Description 车间明细台账
 * @create 2020-09-21 7:53
 */
@RestController
@RequestMapping("/safe/workshopacc")
@Slf4j
@Api(tags = "3.4 车间明细台账")
public class SafeWorkshopAccController {

    @Resource
    SafeWorkshopAccService safeWorkshopAccService;

    @ApiOperation(value = "车间明细台账-查询列表", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:current", value = "页码-从1开始", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:size", value = "每页行数-[web调用自动传，app不用传此参数固定为10]", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:order", value = "排序字段", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:orderby", value = "顺序-[asc]升序，[desc]降序，传空字符串则升序", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:material_name", value = "名称", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:workshop_name", value = "车间名称", required = false, dataTypeClass = String.class),
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class,
                    examples = @Example(
                            @ExampleProperty(
                                    "workshop_name:车间名称\n" +
                                    "material_name:名称\n" +
                                    "specification:型号\n" +
                                    "工段名称【这个是动态列】:工段ID_数量\n"
                            )
                    )
            )
    )
    @RequestMapping(value = "/list", method = RequestMethod.GET)

    public Object getSafeWorkshopAccPageListByProcedure(@RequestParam Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        return AjaxResult.success(safeWorkshopAccService.getSafeWorkshopAccPageListByProcedure(params));
    }

    @ApiOperation(value = "车间明细台账-新增", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:workshop_name", value = "车间名称", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:material_name", value = "名称", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:type", value = "01消防类/02防护类/03防汛类", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:specification", value = "型号", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:amount", value = "数量", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:position", value = "位置", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:create_user_id", value = "创建人id", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:create_user_no", value = "创建人工号", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:create_user", value = "创建人", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:person_liable_id", value = "责任人id", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:person_liable_no", value = "责任人工号", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:person_liable_name", value = "责任人姓名", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:person_liable_tel", value = "责任人电话", required = false, dataTypeClass = String.class),
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class)
    )
    @RequestMapping(value = "/insert", method = RequestMethod.POST, produces = "application/json")
    @Transactional(rollbackFor = Exception.class)
    public Object insertSafeWorkshopAcc(@RequestBody Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        try {
            return AjaxResult.success(safeWorkshopAccService.insertSafeWorkshopAcc(params));
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseUtil.toError(params, "车间明细台账增加失败");
        }
    }

    @ApiOperation(value = "车间明细台账-修改", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:id", value = "行标识", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:workshop_name", value = "车间名称", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:material_name", value = "名称", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:type", value = "01消防类/02防护类/03防汛类", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:specification", value = "型号", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:amount", value = "数量", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:position", value = "位置", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:modify_user_id", value = "修改人id", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:modify_user_no", value = "修改人工号", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:modify_user", value = "修改人", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:person_liable_id", value = "责任人id", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:person_liable_no", value = "责任人工号", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:person_liable_name", value = "责任人姓名", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:person_liable_tel", value = "责任人电话", required = false, dataTypeClass = String.class),
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class)
    )
    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = "application/json")

    @Transactional(rollbackFor = Exception.class)
    public Object updateSafeWorkshopAcc(@RequestBody Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        try {
            return AjaxResult.success(safeWorkshopAccService.updateSafeWorkshopAcc(params));
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseUtil.toError(params, "车间明细台账修改失败");
        }
    }

    @ApiOperation(value = "车间明细台账-删除", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:id", value = "行标识", required = false, dataTypeClass = String.class),
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class)
    )
    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces = "application/json")
    public Object deleteSafeWorkshopAcc(@RequestBody Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        try {
            return AjaxResult.success(safeWorkshopAccService.deleteSafeWorkshopAcc(params));
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseUtil.toError(params, "车间明细台账删除失败");
        }
    }

    @ApiOperation(value = "车间明细台账-批量删除", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:workshop_name", value = "车间名称", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:material_name", value = "名称", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:specification", value = "型号", required = false, dataTypeClass = String.class),
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class)
    )
    @RequestMapping(value = "/batch/delete", method = RequestMethod.POST, produces = "application/json")

    public Object deleteSafeWorkshopAccs(@RequestBody Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        try {
            return AjaxResult.success(safeWorkshopAccService.deleteSafeWorkshopAccs(params));
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseUtil.toError(params, "车间明细台账删除失败");
        }
    }

    @ApiOperation(value = "车间明细台账-工段列表", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:workshop_name", value = "车间名称", required = false, dataTypeClass = String.class),
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class,
                    examples = @Example(
                            @ExampleProperty(
                                    "section_name:工段名称\n" +
                                    "prop:工段id\n"
                            )
                    )
            )
    )
    @RequestMapping(value = "/sectionlist", method = RequestMethod.GET)

    public Object getSafeworkshopAccSectionList(@RequestParam Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        return AjaxResult.success(safeWorkshopAccService.getSafeworkshopAccSectionList(params));
    }

    @ApiOperation(value = "车间明细台账-详情", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:id", value = "行标识", required = false, dataTypeClass = String.class),
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class,
                    examples = @Example(
                            @ExampleProperty(
                                    "workshop_name:车间名称\n" +
                                    "material_name:名称\n" +
                                    "type:01消防类/02防护类/03防汛类\n" +
                                    "specification:型号\n" +
                                    "amount:数量\n" +
                                    "position:位置\n" +
                                    "person_liable_id:责任人id\n" +
                                    "person_liable_no:责任人工号\n" +
                                    "person_liable_name:责任人姓名\n" +
                                    "person_liable_tel:责任人电话\n"
                            )
                    )
            )
    )
    @RequestMapping(value = "/info", method = RequestMethod.GET)

    public Object getSafeworkshopAccInfo(@RequestParam Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        return AjaxResult.success(safeWorkshopAccService.getSafeworkshopAccInfo(params));
    }
}
