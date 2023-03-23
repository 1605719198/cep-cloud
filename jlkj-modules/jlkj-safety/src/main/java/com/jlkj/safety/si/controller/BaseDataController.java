package com.jlkj.safety.si.controller;

import com.alibaba.fastjson.JSONObject;

import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.safety.si.entity.SafeSiKnowledgeLibrary;
import com.jlkj.safety.si.service.*;
import com.jlkj.safety.si.utils.ResponseUtil;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.jlkj.safety.si.constants.Constant.FILE_LIST;

/**
 * @author su
 * @Description 基础数据
 * @create 2020-09-21 7:53
 */
@RestController
@RequestMapping("/safe")
@Slf4j
@Api(tags = "1.4 基础数据")
public class BaseDataController {

    @Resource
    private SafeAreaService safeAreaService;

    @Resource
    private SafeWorkTypeService safeWorkTypeService;

    @Resource
    private SafeDangerTypeService safeDangerTypeService;

    @Resource
    private SafeCheckTypeService safeCheckTypeService;

    @Resource
    private SafeKnowledgeCategoryService safeKnowledgeCategoryService;

    @Resource
    private SafeKnowledgeLibraryService safeKnowledgeLibraryService;

    @Resource
    private SafeConstructionUnitService safeConstructionUnitService;

    @Resource
    private SafeConstructionUnitPersonsService safeConstructionUnitPersonsService;

    @Resource
    private SafeKnowledgeLibraryFileService safeKnowledgeLibraryFileService;


    @ApiOperation(value = "安全区域-查询", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:safe_area_name", value = "安全区域名称", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:current", value = "页码-从1开始", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:size", value = "每页行数-[web调用自动传，app不用传此参数固定为10]", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:order", value = "排序字段", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:orderby", value = "顺序-[asc]升序，[desc]降序，传空字符串则升序", required = false, dataTypeClass = String.class),
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class,
                    examples = @Example(
                            @ExampleProperty(
                                    "id:安全区域ID\n" +
                                    "safe_area_name:安全区域名称\n" +
                                    "lng:经度坐标\n" +
                                    "lat:纬度坐标\n" +
                                    "create_user_name:创建人姓名\n" +
                                    "ceate_time:创建时间"
                            )
                    )
            )
    )
    @RequestMapping(value = "/area/list", method = RequestMethod.GET)
    //@RoleAndPermission(permission = "area_list")
    public Object getSafeAreaPageList(@RequestParam Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        return AjaxResult.success(safeAreaService.getSafeAreaPageList(params));
    }

    @ApiOperation(value = "安全区域-新增", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:safe_area_name", value = "安全区域名称", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:lng", value = "经度坐标", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:lat", value = "纬度坐标", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:create_user_id", value = "创建人ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:create_user_name", value = "创建人姓名", required = false, dataTypeClass = String.class)
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class)
    )
    @RequestMapping(value = "/area/insert", method = RequestMethod.POST, produces = "application/json")
    //@RoleAndPermission(permission = "area_insert")
    public Object insertSafeArea(@RequestBody Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        try {
            return AjaxResult.success(safeAreaService.insertSafeArea(params));
        }
        catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.success(ResponseUtil.toError(params, "安全区域添加失败"));
        }
    }

    @ApiOperation(value = "安全区域-修改", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:id", value = "安全区域ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:safe_area_name", value = "安全区域名称", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:lng", value = "经度坐标", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:lat", value = "纬度坐标", required = false, dataTypeClass = String.class)
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class)
    )
    @RequestMapping(value = "/area/update", method = RequestMethod.POST, produces = "application/json")
    //@RoleAndPermission(permission = "area_update")
    public Object updateSafeArea(@RequestBody Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        try {
            return AjaxResult.success(safeAreaService.updateSafeArea(params));
        }
        catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.success(ResponseUtil.toError(params, "安全区域修改失败"));
        }
    }

    @ApiOperation(value = "安全区域-删除", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:id", value = "安全区域ID", required = false, dataTypeClass = String.class)
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class)
    )
    @RequestMapping(value = "/area/delete", method = RequestMethod.POST, produces = "application/json")
    //@RoleAndPermission(permission = "area_delete")
    public Object deleteSafeArea(@RequestBody Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        try {
            return AjaxResult.success(safeAreaService.deleteSafeArea(params));
        }
        catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.success(ResponseUtil.toError(params, "安全区域删除失败"));
        }
    }

    @ApiOperation(value = "工种-查询", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:current", value = "页码-从1开始", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:size", value = "每页行数-[web调用自动传，app不用传此参数固定为10]", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:order", value = "排序字段", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:orderby", value = "顺序-[asc]升序，[desc]降序，传空字符串则升序", required = false, dataTypeClass = String.class),
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class,
                    examples = @Example(
                            @ExampleProperty(
                                    "id:工种ID\n" +
                                    "work_type_name:工种名称\n" +
                                    "create_user_name:创建人姓名\n" +
                                    "ceate_time:创建时间"
                            )
                    )
            )
    )
    @RequestMapping(value = "/worktype/list", method = RequestMethod.GET)
    //@RoleAndPermission(permission = "worktype_list")
    public Object getSafeWorkTypePageList(@RequestParam Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        return AjaxResult.success(safeWorkTypeService.getSafeWorkTypePageList(params));
    }

    @ApiOperation(value = "工种-新增", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:work_type_name", value = "工种名称", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:create_user_id", value = "创建人ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:create_user_name", value = "创建人姓名", required = false, dataTypeClass = String.class)
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class)
    )
    @RequestMapping(value = "/worktype/insert", method = RequestMethod.POST, produces = "application/json")
    //@RoleAndPermission(permission = "worktype_insert")
    public Object insertSafeWorkType(@RequestBody Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        try {
            return AjaxResult.success(safeWorkTypeService.insertSafeWorkType(params));
        }
        catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.success(ResponseUtil.toError(params, "工种添加失败"));
        }
    }

    @ApiOperation(value = "工种-修改", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:id", value = "工种ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:work_type_name", value = "工种名称", required = false, dataTypeClass = String.class)
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class)
    )
    @RequestMapping(value = "/worktype/update", method = RequestMethod.POST, produces = "application/json")
    //@RoleAndPermission(permission = "worktype_update")
    public Object updateSafeWorkType(@RequestBody Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        try {
            return AjaxResult.success(safeWorkTypeService.updateSafeWorkType(params));
        }
        catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.success(ResponseUtil.toError(params, "工种修改失败"));
        }
    }

    @ApiOperation(value = "工种-删除", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:id", value = "工种ID", required = false, dataTypeClass = String.class)
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class)
    )
    @RequestMapping(value = "/worktype/delete", method = RequestMethod.POST, produces = "application/json")
    //@RoleAndPermission(permission = "worktype_delete")
    public Object deleteSafeWorkType(@RequestBody Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        try {
            return AjaxResult.success(safeWorkTypeService.deleteSafeWorkType(params));
        }
        catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.success(ResponseUtil.toError(params, "工种删除失败"));
        }
    }

    @ApiOperation(value = "隐患等级-查询", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:current", value = "页码-从1开始", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:size", value = "每页行数-[web调用自动传，app不用传此参数固定为10]", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:order", value = "排序字段", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:orderby", value = "顺序-[asc]升序，[desc]降序，传空字符串则升序", required = false, dataTypeClass = String.class),
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class,
                    examples = @Example(
                            @ExampleProperty(
                                    "id:隐患等级ID\n" +
                                    "danger_type_name:隐患等级名称\n" +
                                    "create_user_name:创建人姓名\n" +
                                    "ceate_time:创建时间"
                            )
                    )
            )
    )
    @RequestMapping(value = "/dangertype/list", method = RequestMethod.GET)
    //@RoleAndPermission(permission = "dangertype_list")
    public Object getSafeDangerTypePageList(@RequestParam Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        return AjaxResult.success(safeDangerTypeService.getSafeDangerTypePageList(params));
    }

    @ApiOperation(value = "隐患等级-新增", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:danger_type_name", value = "隐患等级名称", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:create_user_id", value = "创建人ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:create_user_name", value = "创建人姓名", required = false, dataTypeClass = String.class)
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class)
    )
    @RequestMapping(value = "/dangertype/insert", method = RequestMethod.POST, produces = "application/json")
    //@RoleAndPermission(permission = "dangertype_insert")
    public Object insertSafeDangerType(@RequestBody Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        try {
            return AjaxResult.success(safeDangerTypeService.insertSafeDangerType(params));
        }
        catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.success(ResponseUtil.toError(params, "隐患等级添加失败"));
        }
    }

    @ApiOperation(value = "隐患等级-修改", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:id", value = "隐患等级ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:danger_type_name", value = "隐患等级名称", required = false, dataTypeClass = String.class)
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class)
    )
    @RequestMapping(value = "/dangertype/update", method = RequestMethod.POST, produces = "application/json")
    //@RoleAndPermission(permission = "dangertype_update")
    public Object updateSafeDangerType(@RequestBody Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        try {
            return AjaxResult.success(safeDangerTypeService.updateSafeDangerType(params));
        }
        catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.success(ResponseUtil.toError(params, "隐患等级修改失败"));
        }
    }

    @ApiOperation(value = "隐患等级-删除", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:id", value = "隐患等级ID", required = false, dataTypeClass = String.class)
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class)
    )
    @RequestMapping(value = "/dangertype/delete", method = RequestMethod.POST, produces = "application/json")
    //@RoleAndPermission(permission = "dangertype_delete")
    public Object deleteSafeDangerType(@RequestBody Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        try {
            return AjaxResult.success(safeDangerTypeService.deleteSafeDangerType(params));
        }
        catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.success(ResponseUtil.toError(params, "隐患等级删除失败"));
        }
    }

    @ApiOperation(value = "安全巡检类型-查询", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:current", value = "页码-从1开始", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:size", value = "每页行数-[web调用自动传，app不用传此参数固定为10]", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:order", value = "排序字段", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:orderby", value = "顺序-[asc]升序，[desc]降序，传空字符串则升序", required = false, dataTypeClass = String.class),
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class,
                    examples = @Example(
                            @ExampleProperty(
                                    "id:安全巡检类型ID\n" +
                                    "check_type_name:安全巡检类型名称\n" +
                                    "create_user_name:创建人姓名\n" +
                                    "ceate_time:创建时间"
                            )
                    )
            )
    )
    @RequestMapping(value = "/checktype/list", method = RequestMethod.GET)
    //@RoleAndPermission(permission = "checktype_list")
    public Object getSafeCheckTypePageList(@RequestParam Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        return AjaxResult.success(safeCheckTypeService.getSafeCheckTypePageList(params));
    }

    @ApiOperation(value = "安全巡检类型-新增", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:check_type_name", value = "安全巡检类型名称", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:create_user_id", value = "创建人ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:create_user_name", value = "创建人姓名", required = false, dataTypeClass = String.class)
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class)
    )
    @RequestMapping(value = "/checktype/insert", method = RequestMethod.POST, produces = "application/json")
    //@RoleAndPermission(permission = "checktype_insert")
    public Object insertSafeCheckType(@RequestBody Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        try {
            return AjaxResult.success(safeCheckTypeService.insertSafeCheckType(params));
        }
        catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.success(ResponseUtil.toError(params, "安全巡检类型添加失败"));
        }
    }

    @ApiOperation(value = "安全巡检类型-修改", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:id", value = "安全巡检类型ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:check_type_name", value = "安全巡检类型名称", required = false, dataTypeClass = String.class)
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class)
    )
    @RequestMapping(value = "/checktype/update", method = RequestMethod.POST, produces = "application/json")
    //@RoleAndPermission(permission = "checktype_update")
    public Object updateSafeCheckType(@RequestBody Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        try {
            return AjaxResult.success(safeCheckTypeService.updateSafeCheckType(params));
        }
        catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.success(ResponseUtil.toError(params, "安全巡检类型修改失败"));
        }
    }

    @ApiOperation(value = "安全巡检类型-删除", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:id", value = "安全巡检类型ID", required = false, dataTypeClass = String.class)
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class)
    )
    @RequestMapping(value = "/checktype/delete", method = RequestMethod.POST, produces = "application/json")
    //@RoleAndPermission(permission = "checktype_delete")
    public Object deleteSafeCheckType(@RequestBody Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        try {
            return AjaxResult.success(safeCheckTypeService.deleteSafeCheckType(params));
        }
        catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.success(ResponseUtil.toError(params, "安全巡检类型删除失败"));
        }
    }

    @ApiOperation(value = "知识类别-查询", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:current", value = "页码-从1开始", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:size", value = "每页行数-[web调用自动传，app不用传此参数固定为10]", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:order", value = "排序字段", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:orderby", value = "顺序-[asc]升序，[desc]降序，传空字符串则升序", required = false, dataTypeClass = String.class),
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class,
                    examples = @Example(
                            @ExampleProperty(
                                    "id:知识类别ID\n" +
                                    "knowledge_category_name:知识类别名称\n" +
                                    "parent_knowledge_category_id:上级知识类别ID\n" +
                                    "parent_knowledge_category_name:上级知识类别名称\n" +
                                    "create_user_name:创建人姓名\n" +
                                    "ceate_time:创建时间"
                            )
                    )
            )
    )
    @RequestMapping(value = "/knowledgecategory/list", method = RequestMethod.GET)
    //@RoleAndPermission(permission = "knowledgecategory_list")
    public Object getSafeKnowledgeCategoryPageList(@RequestParam Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        return AjaxResult.success(safeKnowledgeCategoryService.getSafeKnowledgeCategoryPageList(params));
    }

    @ApiOperation(value = "知识类别-新增", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:knowledge_category_name", value = "知识类别名称", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:parent_knowledge_category_id", value = "上级知识类别ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:sort", value = "序号", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:create_user_id", value = "创建人ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:create_user_name", value = "创建人姓名", required = false, dataTypeClass = String.class)
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class)
    )
    @RequestMapping(value = "/knowledgecategory/insert", method = RequestMethod.POST, produces = "application/json")
    //@RoleAndPermission(permission = "knowledgecategory_insert")
    public Object insertSafeKnowledgeCategory(@RequestBody Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        try {
            return AjaxResult.success(safeKnowledgeCategoryService.insertSafeKnowledgeCategory(params));
        }
        catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.success(ResponseUtil.toError(params, "知识类别添加失败"));
        }
    }

    @ApiOperation(value = "知识类别-修改", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:id", value = "知识类别ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:knowledge_category_name", value = "知识类别名称", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:parent_knowledge_category_id", value = "上级知识类别ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:sort", value = "序号", required = false, dataTypeClass = String.class)
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class)
    )
    @RequestMapping(value = "/knowledgecategory/update", method = RequestMethod.POST, produces = "application/json")
    //@RoleAndPermission(permission = "knowledgecategory_update")
    public Object updateSafeKnowledgeCategory(@RequestBody Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        try {
            return AjaxResult.success(safeKnowledgeCategoryService.updateSafeKnowledgeCategory(params));
        }
        catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.success(ResponseUtil.toError(params, "知识类别修改失败"));
        }
    }

    @ApiOperation(value = "知识类别-删除", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:id", value = "知识类别ID", required = false, dataTypeClass = String.class)
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class)
    )
    @RequestMapping(value = "/knowledgecategory/delete", method = RequestMethod.POST, produces = "application/json")
    //@RoleAndPermission(permission = "knowledgecategory_delete")
    public Object deleteSafeKnowledgeCategory(@RequestBody Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        try {
            return AjaxResult.success(safeKnowledgeCategoryService.deleteSafeKnowledgeCategory(params));
        }
        catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.success(ResponseUtil.toError(params, "知识类别删除失败"));
        }
    }

    @ApiOperation(value = "知识库-查询", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:current", value = "页码-从1开始", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:size", value = "每页行数-[web调用自动传，app不用传此参数固定为10]", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:order", value = "排序字段", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:orderby", value = "顺序-[asc]升序，[desc]降序，传空字符串则升序", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:title", value = "标题", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:knowledge_category_id", value = "知识类别ID，0-全部", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:keywords", value = "关键字", required = false, dataTypeClass = String.class)
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class,
                    examples = @Example(
                            @ExampleProperty(
                                    "id:知识库ID\n" +
                                    "title:标题\n" +
                                    "knowledge_category_id:知识类别ID\n" +
                                    "knowledge_category_name:知识类别名称\n" +
                                    "keywords:关键字\n" +
                                    "contents:内容\n" +
                                    "create_user_name:创建人姓名\n" +
                                    "ceate_time:创建时间"
                            )
                    )
            )
    )
    @RequestMapping(value = "/knowledgelibrary/list", method = RequestMethod.GET)
    //@RoleAndPermission(permission = "knowledgelibrary_list")
    public Object getSafeKnowledgeLibraryPageList(@RequestParam Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        return AjaxResult.success(safeKnowledgeLibraryService.getSafeKnowledgeLibraryPageList(params));
    }

    @ApiOperation(value = "知识库-附件列表", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:id", value = "知识库ID", required = false, dataTypeClass = String.class)
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class,
                    examples = @Example(
                            @ExampleProperty(
                                    "id:行标识\n" +
                                    "file_name:原始文件名\n" +
                                    "disk_name:磁盘文件名\n" +
                                    "file_size:文件大小\n" +
                                    "file_id:文件id\n"
                            )
                    )
            )
    )
    @RequestMapping(value = "/knowledgelibrary/file/list", method = RequestMethod.GET)
    public Object getSafeKnowledgeLibraryFileList(@RequestParam Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        return AjaxResult.success(safeKnowledgeLibraryService.getSafeKnowledgeLibraryFileList(params));
    }

    @ApiOperation(value = "知识库-新增", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:title", value = "标题", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:knowledge_category_id", value = "知识类别ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:keywords", value = "关键字", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:contents", value = "内容", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:create_user_id", value = "创建人ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:create_user_name", value = "创建人姓名", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:file_list", value = "知识库附件JSON对象", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:file_list[file_name]", value = "原始文件名", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:file_list[disk_name]", value = "磁盘文件名", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:file_list[file_size]", value = "文件大小", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:file_list[file_id]", value = "文件id", required = false, dataTypeClass = String.class)
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class)
    )
    @RequestMapping(value = "/knowledgelibrary/insert", method = RequestMethod.POST, produces = "application/json")
    //@RoleAndPermission(permission = "knowledgelibrary_insert")
    public Object insertSafeKnowledgeLibrary(@RequestBody Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        String msg = "知识库添加失败";
        try {
            boolean succ = true;
            SafeSiKnowledgeLibrary safeSiKnowledgeLibrary = null;
            Map<String, Object> objectMap = (Map<String, Object>) safeKnowledgeLibraryService.insertSafeKnowledgeLibrary(params);
            int code = Integer.parseInt(objectMap.get("code").toString());
            if (code == 0) {
                safeSiKnowledgeLibrary = (SafeSiKnowledgeLibrary) objectMap.get("data");
                if (!"".equals(params.get(FILE_LIST).toString())) {
                    List<Map> listFile = JSONObject.parseArray(JSONObject.toJSON(params.get("file_list")).toString(), Map.class);
                    for (int i = 0; i < listFile.size(); i++) {
                        Map<String, Object> param = listFile.get(i);
                        param.put("knowledge_id", safeSiKnowledgeLibrary.getId());
                        if (!safeKnowledgeLibraryFileService.insertSafeKnowledgeLibraryFile(param)) {
                            succ = false;
                            msg = "知识库附件添加失败";
                            break;
                        }
                    }
                }
            } else {
                msg = objectMap.get("msg").toString();
                succ = false;
            }
            if (succ) {
                Map<String, Object> outData = new HashMap<>(1);
                outData.put("id", safeSiKnowledgeLibrary.getId());
                return AjaxResult.success(ResponseUtil.toResult(params, "知识库添加成功", outData));
            } else {
                return AjaxResult.success(ResponseUtil.toError(params, msg));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return AjaxResult.success(ResponseUtil.toError(params, msg));
        }
    }

    @ApiOperation(value = "知识库-修改", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:id", value = "知识库ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:title", value = "标题", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:knowledge_category_id", value = "知识类别ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:keywords", value = "关键字", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:contents", value = "内容", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:file_list", value = "知识库附件JSON对象", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:file_list[file_name]", value = "原始文件名", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:file_list[disk_name]", value = "磁盘文件名", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:file_list[file_size]", value = "文件大小", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:file_list[file_id]", value = "文件id", required = false, dataTypeClass = String.class)
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class)
    )
    @RequestMapping(value = "/knowledgelibrary/update", method = RequestMethod.POST, produces = "application/json")
    //@RoleAndPermission(permission = "knowledgelibrary_update")
    public Object updateSafeKnowledgeLibrary(@RequestBody Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        String msg = "知识库修改失败";
        try {
            boolean succ = true;
            Map<String, Object> objectMap = (Map<String, Object>) safeKnowledgeLibraryService.updateSafeKnowledgeLibrary(params);
            int code = Integer.parseInt(objectMap.get("code").toString());
            if (code == 0) {
                Map<String, Object> deleteParam = new HashMap<>(1);
                deleteParam.put("id", params.get("id").toString());
                if (!safeKnowledgeLibraryService.deleteSafeKnowledgeLibraryFiles(deleteParam)) {
                    succ = false;
                    msg = "知识库附件删除失败";
                }
                if (succ) {
                    if (!"".equals(params.get(FILE_LIST).toString())) {
                        List<Map> listFile = JSONObject.parseArray(JSONObject.toJSON(params.get("file_list")).toString(), Map.class);
                        for (int i = 0; i < listFile.size(); i++) {
                            Map<String, Object> param = listFile.get(i);
                            param.put("knowledge_id", params.get("id").toString());
                            if (!safeKnowledgeLibraryFileService.insertSafeKnowledgeLibraryFile(param)) {
                                succ = false;
                                msg = "知识库附件添加失败";
                                break;
                            }
                        }
                    }
                }
            } else {
                msg = objectMap.get("msg").toString();
                succ = false;
            }
            if (succ) {
                return AjaxResult.success(ResponseUtil.toResult(params, "知识库修改成功"));
            } else {
                return ResponseUtil.toError(params, msg);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponseUtil.toError(params, msg);
        }
    }

    @ApiOperation(value = "知识库-删除", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:id", value = "知识库ID", required = false, dataTypeClass = String.class)
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class)
    )
    @RequestMapping(value = "/knowledgelibrary/delete", method = RequestMethod.POST, produces = "application/json")
    //@RoleAndPermission(permission = "knowledgelibrary_delete")
    public Object deleteSafeKnowledgeLibrary(@RequestBody Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        try {
            return AjaxResult.success(safeKnowledgeLibraryService.deleteSafeKnowledgeLibrary(params));
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseUtil.toError(params, "知识库删除失败");
        }
    }

    @ApiOperation(value = "外委施工单位-查询", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:current", value = "页码-从1开始", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:size", value = "每页行数-[web调用自动传，app不用传此参数固定为10]", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:order", value = "排序字段", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:orderby", value = "顺序-[asc]升序，[desc]降序，传空字符串则升序", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:construction_unit_name", value = "施工单位名称", required = false, dataTypeClass = String.class)
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class,
                    examples = @Example(
                            @ExampleProperty(
                                    "id:施工单位ID\n" +
                                    "construction_unit_name:施工单位名称\n" +
                                    "content:施工内容\n" +
                                    "create_user_name:创建人姓名\n" +
                                    "ceate_time:创建时间"
                            )
                    )
            )
    )
    @RequestMapping(value = "/constructionunit/list", method = RequestMethod.GET)
    //@RoleAndPermission(permission = "constructionunit_list")
    public Object getSafeConstructionUnitPageList(@RequestParam Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        return AjaxResult.success(safeConstructionUnitService.getSafeConstructionUnitPageList(params));
    }

    @ApiOperation(value = "外委施工单位-新增", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:construction_unit_name", value = "施工单位名称", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:content", value = "施工内容", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:create_user_id", value = "创建人ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:create_user_name", value = "创建人姓名", required = false, dataTypeClass = String.class)
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class)
    )
    @RequestMapping(value = "/constructionunit/insert", method = RequestMethod.POST, produces = "application/json")
    //@RoleAndPermission(permission = "constructionunit_insert")
    public Object insertSafeConstructionUnit(@RequestBody Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        try {
            return AjaxResult.success(safeConstructionUnitService.insertSafeConstructionUnit(params));
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseUtil.toError(params, "外委施工单位添加失败");
        }
    }

    @ApiOperation(value = "外委施工单位-修改", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:id", value = "施工单位ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:construction_unit_name", value = "施工单位名称", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:content", value = "施工内容", required = false, dataTypeClass = String.class)
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class)
    )
    @RequestMapping(value = "/constructionunit/update", method = RequestMethod.POST, produces = "application/json")
    //@RoleAndPermission(permission = "constructionunit_update")
    public Object updateSafeConstructionUnit(@RequestBody Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        try {
            return AjaxResult.success(safeConstructionUnitService.updateSafeConstructionUnit(params));
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseUtil.toError(params, "外委施工单位修改失败");
        }
    }

    @ApiOperation(value = "外委施工单位-删除", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:id", value = "施工单位ID", required = false, dataTypeClass = String.class)
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class)
    )
    @RequestMapping(value = "/constructionunit/delete", method = RequestMethod.POST, produces = "application/json")
    //@RoleAndPermission(permission = "constructionunit_delete")
    public Object deleteSafeConstructionUnit(@RequestBody Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        try {
            return AjaxResult.success(safeConstructionUnitService.deleteSafeConstructionUnit(params));
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseUtil.toError(params, "外委施工单位删除失败");
        }
    }

    @ApiOperation(value = "施工人员-查询", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:current", value = "页码-从1开始", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:size", value = "每页行数-[web调用自动传，app不用传此参数固定为10]", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:order", value = "排序字段", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:orderby", value = "顺序-[asc]升序，[desc]降序，传空字符串则升序", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:construction_unit_id", value = "所属单位ID,0-全部", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:type", value = "所属单位类型(1本厂,2外委),0-全部", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:person_name", value = "人员姓名", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:create_user_id", value = "创建人ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:create_user_name", value = "创建人姓名", required = false, dataTypeClass = String.class)
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class,
                    examples = @Example(
                            @ExampleProperty(
                                    "id:行标识\n" +
                                    "construction_unit_id:所属单位ID\n" +
                                    "construction_unit_name:所属单位名称\n" +
                                    "type:所属单位类型(1本厂,2外委)\n" +
                                    "type_text:所属单位类型\n" +
                                    "person_id:人员ID\n" +
                                    "person_name:人员姓名\n" +
                                    "work_type_name:工种名称\n" +
                                    "work_card_code:作业证编号\n" +
                                    "is_leader:是否负责人(0否,1是)\n" +
                                    "is_leader_text:是否负责人\n" +
                                    "create_user_name:创建人姓名\n" +
                                    "ceate_time:创建时间"
                            )
                    )
            )
    )
    @RequestMapping(value = "/constructionunitpersons/list", method = RequestMethod.GET)
    //@RoleAndPermission(permission = "constructionunitpersons_list")
    public Object getSafeConstructionUnitPersonsPageList(@RequestParam Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        return AjaxResult.success(safeConstructionUnitPersonsService.getSafeConstructionUnitPersonsPageList(params));
    }

    @ApiOperation(value = "施工人员-新增", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:construction_unit_id", value = "所属单位ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:construction_unit_name", value = "施工单位名称", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:type", value = "所属单位类型(1本厂,2外委)", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:person_id", value = "人员ID-外委人员固定传0", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:person_name", value = "人员姓名", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:work_type_name", value = "工种名称", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:work_card_code", value = "作业证编号", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:is_leader", value = "是否负责人(0否,1是)", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:create_user_id", value = "创建人ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:create_user_name", value = "创建人姓名", required = false, dataTypeClass = String.class)
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class)
    )
    @RequestMapping(value = "/constructionunitpersons/insert", method = RequestMethod.POST, produces = "application/json")
    //@RoleAndPermission(permission = "constructionunitpersons_insert")
    public Object insertSafeConstructionUnitPersons(@RequestBody Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        try {
            return AjaxResult.success(safeConstructionUnitPersonsService.insertSafeConstructionUnitPersons(params));
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseUtil.toError(params, "施工人员添加失败");
        }
    }

    @ApiOperation(value = "施工人员-修改", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:id", value = "行标识", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:construction_unit_id", value = "所属单位ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:construction_unit_name", value = "施工单位名称", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:type", value = "所属单位类型(1本厂,2外委)", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:person_id", value = "人员ID-外委人员固定传0", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:person_name", value = "人员姓名", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:work_type_name", value = "工种名称", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:work_card_code", value = "作业证编号", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:is_leader", value = "是否负责人(0否,1是)", required = false, dataTypeClass = String.class)
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class)
    )
    @RequestMapping(value = "/constructionunitpersons/update", method = RequestMethod.POST, produces = "application/json")
    //@RoleAndPermission(permission = "constructionunitpersons_update")
    public Object updateSafeConstructionUnitPersons(@RequestBody Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        try {
            return AjaxResult.success(safeConstructionUnitPersonsService.updateSafeConstructionUnitPersons(params));
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseUtil.toError(params, "施工人员修改失败");
        }
    }

    @ApiOperation(value = "施工人员-删除", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:id", value = "行标识", required = false, dataTypeClass = String.class)
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class)
    )
    @RequestMapping(value = "/constructionunitpersons/delete", method = RequestMethod.POST, produces = "application/json")
    //@RoleAndPermission(permission = "constructionunitpersons_delete")
    public Object deleteSafeConstructionUnitPersons(@RequestBody Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        try {
            return AjaxResult.success(safeConstructionUnitPersonsService.deleteSafeConstructionUnitPersons(params));
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseUtil.toError(params, "施工人员删除失败");
        }
    }



}
