package com.jlkj.safety.si.controller;

import com.jlkj.safety.si.service.SafeWorkshopFireExtinguisherAccService;
import com.jlkj.safety.si.utils.ResponseUtil;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author su
 * @Description 灭火器台账
 * @create 2020-09-21 7:53
 */
@RestController
@RequestMapping("/safe/workshopfireextinguisheracc")
@Slf4j
@Api(tags = "3.5 灭火器台账")
public class SafeWorkshopFireExtinguisherAccController {

    @Resource
    SafeWorkshopFireExtinguisherAccService safeWorkshopFireExtinguisherAccService;

    @ApiOperation(value = "灭火器台账-查询列表", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:current", value = "页码-从1开始", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:size", value = "每页行数-[web调用自动传，app不用传此参数固定为10]", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:order", value = "排序字段", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:orderby", value = "顺序-[asc]升序，[desc]降序，传空字符串则升序", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:workshop_name", value = "车间名称", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:material_name", value = "灭火器类型", required = false, dataTypeClass = String.class),
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class,
                    examples = @Example(
                            @ExampleProperty(
                                    "id:行标识\n" +
                                    "workshop_name:车间名称\n" +
                                    "position:地点位置\n" +
                                    "material_name:消防灭火器类型" +
                                    "specification:型号\n" +
                                    "amount:数量\n" +
                                    "out_date:出厂日期\n" +
                                    "first_date:首次充装日期\n" +
                                    "next_date:下次充装日期\n" +
                                    "bad_date:报废时间\n" +
                                    "charge_person:负责人\n" +
                                    "remark:备注\n" +
                                    "create_user:创建人\n" +
                                    "create_time:创建时间\n"
                            )
                    )
            )
    )
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Object getSafeJobFirePageList(@RequestParam Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        return safeWorkshopFireExtinguisherAccService.getSafeWorkshopFireExtinguisherAccPageList(params);
    }

    @ApiOperation(value = "灭火器台账-新增", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:workshop_name", value = "车间名称", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:position", value = "地点位置", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:material_name", value = "消防灭火器类型", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:specification", value = "型号", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:amount", value = "数量", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:out_date", value = "出厂日期", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:first_date", value = "首次充装日期", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:next_date", value = "下次充装日期", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:bad_date", value = "报废时间", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:charge_person", value = "负责人", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:remark", value = "备注", required = false, dataTypeClass = String.class),
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
            return safeWorkshopFireExtinguisherAccService.insertSafeWorkshopFireExtinguisherAcc(params);
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseUtil.toError(params, "灭火器台账增加失败");
        }
    }

    @ApiOperation(value = "灭火器台账-修改", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:id", value = "行标识", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:workshop_name", value = "车间名称", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:position", value = "地点位置", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:material_name", value = "消防灭火器类型", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:specification", value = "型号", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:amount", value = "数量", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:out_date", value = "出厂日期", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:first_date", value = "首次充装日期", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:next_date", value = "下次充装日期", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:bad_date", value = "报废时间", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:charge_person", value = "负责人", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:remark", value = "备注", required = false, dataTypeClass = String.class),
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class)
    )
    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = "application/json")
    @Transactional(rollbackFor = Exception.class)
    public Object updateSafeJobFire(@RequestBody Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        try {
            return safeWorkshopFireExtinguisherAccService.updateSafeWorkshopFireExtinguisherAcc(params);
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseUtil.toError(params, "灭火器台账修改失败");
        }
    }

    @ApiOperation(value = "灭火器台账-删除", notes = "")
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
            return safeWorkshopFireExtinguisherAccService.deleteSafeWorkshopFireExtinguisherAcc(params);
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseUtil.toError(params, "灭火器台账删除失败");
        }
    }
}
