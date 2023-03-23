package com.jlkj.safety.si.controller;

import com.jlkj.safety.si.service.SafeBulletinBoardService;
import com.jlkj.safety.si.utils.ResponseUtil;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author su
 * @Description 安全承诺公告牌
 * @create 2020-09-21 7:53
 */
@RestController
@RequestMapping("/safe/bulletinboard")
@Slf4j
@Api(tags = "3.2 安全承诺公告牌")
public class SafeBulletinBoardController {

    @Resource
    SafeBulletinBoardService safeBulletinBoardService;

    @ApiOperation(value = "安全承诺公告牌-查询列表", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:current", value = "页码-从1开始", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:size", value = "每页行数-[web调用自动传，app不用传此参数固定为10]", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:order", value = "排序字段", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:orderby", value = "顺序-[asc]升序，[desc]降序，传空字符串则升序", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:start_time", value = "开始时间", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:end_time", value = "结束时间", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:title", value = "标题", required = false, dataTypeClass = String.class),
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class,
                    examples = @Example(
                            @ExampleProperty(
                                    "id:行标识\n" +
                                    "title:标题\n" +
                                    "team_risk_assessment:班组风险研判\n" +
                                    "team_commitment:班组承诺\n" +
                                    "principal_responsible_person:班组主要负责人\n" +
                                    "create_user_name:创建人\n" +
                                    "create_time:创建时间\n"
                            )
                    )
            )
    )
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Object getSafeJobFirePageList(@RequestParam Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        return safeBulletinBoardService.getSafeBulletinBoardPageList(params);
    }

    @ApiOperation(value = "安全承诺公告牌-新增", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:title", value = "标题", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:team_risk_assessment", value = "班组风险研判", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:team_commitment", value = "班组承诺", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:principal_responsible_person", value = "班组主要负责人", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:create_user_name", value = "创建人", required = false, dataTypeClass = String.class),
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class)
    )
    @RequestMapping(value = "/insert", method = RequestMethod.POST, produces = "application/json")
    @Transactional(rollbackFor = Exception.class)
    public Object insertSafeJobFire(@RequestBody Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        try {
            return safeBulletinBoardService.insertSafeBulletinBoard(params);
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseUtil.toError(params, "安全承诺公告牌增加失败");
        }
    }

    @ApiOperation(value = "安全承诺公告牌-修改", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:id", value = "行标识", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:title", value = "标题", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:team_risk_assessment", value = "班组风险研判", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:team_commitment", value = "班组承诺", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:principal_responsible_person", value = "班组主要负责人", required = false, dataTypeClass = String.class),
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class)
    )
    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = "application/json")
    @Transactional(rollbackFor = Exception.class)
    public Object updateSafeJobFire(@RequestBody Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        try {
            return safeBulletinBoardService.updateSafeBulletinBoard(params);
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseUtil.toError(params, "安全承诺公告牌修改失败");
        }
    }

    @ApiOperation(value = "安全承诺公告牌-删除", notes = "")
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
            return safeBulletinBoardService.deleteSafeBulletinBoard(params);
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseUtil.toError(params, "安全承诺公告牌删除失败");
        }
    }

    @ApiOperation(value = "安全承诺公告牌-模板", notes = "")
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class,
                    examples = @Example(
                            @ExampleProperty(
                                    "title:标题\n" +
                                    "team_risk_assessment:班组风险研判\n" +
                                    "team_commitment:班组承诺\n" +
                                    "create_time:日期\n"
                            )
                    )
            )
    )
    @RequestMapping(value = "/template", method = RequestMethod.GET)
    public Object getSafeBulletinBoardTemplateInfo(@RequestParam Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        return safeBulletinBoardService.getSafeBulletinBoardTemplateInfo(params);
    }
}
