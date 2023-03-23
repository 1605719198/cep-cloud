package com.jlkj.safety.si.controller;

import com.jlkj.safety.si.service.SafeEmergencyReliefMaterialsService;
import com.jlkj.safety.si.utils.ResponseUtil;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author su
 * @Description 气防站应急救援物资
 * @create 2020-09-21 7:53
 */
@RestController
@RequestMapping("/safe/emergencyreliefmaterials")
@Slf4j
@Api(tags = "3.3 气防站应急救援物资")
public class SafeEmergencyReliefMaterialsController {

    @Resource
    SafeEmergencyReliefMaterialsService safeEmergencyReliefMaterialsService;

    @ApiOperation(value = "气防站应急救援物资-查询列表", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:current", value = "页码-从1开始", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:size", value = "每页行数-[web调用自动传，app不用传此参数固定为10]", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:order", value = "排序字段", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:orderby", value = "顺序-[asc]升序，[desc]降序，传空字符串则升序", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:material_name", value = "物资名称", required = false, dataTypeClass = String.class),
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class,
                    examples = @Example(
                            @ExampleProperty(
                                    "id:物资ID\n" +
                                    "material_name:名称n" +
                                    "specification:型号\n" +
                                    "amount:数量\n" +
                                    "charge_person_id:负责人id\n" +
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
        return safeEmergencyReliefMaterialsService.getSafeEmergencyReliefMaterialsPageList(params);
    }

    @ApiOperation(value = "气防站应急救援物资-新增", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:material_name", value = "名称", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:specification", value = "型号", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:amount", value = "数量", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:charge_person_id", value = "负责人id", required = false, dataTypeClass = String.class),
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
            return safeEmergencyReliefMaterialsService.insertSafeEmergencyReliefMaterials(params);
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseUtil.toError(params, "气防站应急救援物资增加失败");
        }
    }

    @ApiOperation(value = "气防站应急救援物资-修改", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:id", value = "物资ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:material_name", value = "名称", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:specification", value = "型号", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:amount", value = "数量", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:charge_person_id", value = "负责人id", required = false, dataTypeClass = String.class),
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
            return safeEmergencyReliefMaterialsService.updateSafeEmergencyReliefMaterials(params);
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseUtil.toError(params, "气防站应急救援物资修改失败");
        }
    }

    @ApiOperation(value = "气防站应急救援物资-删除", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:id", value = "物资ID", required = false, dataTypeClass = String.class),
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class)
    )
    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces = "application/json")
    public Object deleteSafeJobFire(@RequestBody Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        try {
            return safeEmergencyReliefMaterialsService.deleteSafeEmergencyReliefMaterials(params);
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseUtil.toError(params, "气防站应急救援物资删除失败");
        }
    }
}
