package com.jlkj.product.oi.controller;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.resp.ResultCode;
import com.jlkj.common.datascope.annotation.ParamModel;
import com.jlkj.common.core.web.resp.PageResult;
import com.jlkj.common.core.web.resp.Result;
import com.jlkj.common.core.web.resp.ValidUtil;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.product.oi.domain.HumanresourceOrganization;
import com.jlkj.product.oi.domain.ProductionPlanRepair;
import com.jlkj.product.oi.dto.changelog.InsertChangeLogDTO;
import com.jlkj.product.oi.dto.productionplanrepair.*;
import com.jlkj.product.oi.service.ChangeLogService;
import com.jlkj.product.oi.service.HumanresourceOrganizationService;
import com.jlkj.product.oi.service.impl.ProductionPlanRepairServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;
import java.util.Map;

import static com.jlkj.product.oi.constants.SysLogConstant.SYS_LOG_PARAM_KEY;

/**
 * @author sdy
 * @Description
 * @create 2022-04-19 8:45
 */
@Tag(name = "设备维修计划")
@RestController
@RequestMapping("/plan")
@Slf4j
public class ProductionPlanRepairController {

    @Autowired
    HttpServletRequest httpServletRequest;

    @Autowired
    ProductionPlanRepairServiceImpl productionPlanRepairService;

    @Resource
    ChangeLogService changeLogService;

    @Resource
    HumanresourceOrganizationService humanresourceOrganizationService;

    @Operation(summary = "分页列表查询",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token"),
                    @Parameter(name = "department_id", description = "部门编号"),
                    @Parameter(name = "equipment_number", description = "设备编号"),
                    @Parameter(name = "start_date", description = "开始日期"),
                    @Parameter(name = "end_date", description = "结束日期"),
                    @Parameter(name = "repair_content", description = "维修内容"),
                    @Parameter(name = "plan_state", description = "计划完成状态[-1全部,0未完成,1已完成]"),
                    @Parameter(name = "current", description = "页码-从1开始"),
                    @Parameter(name = "size", description = "每页行数"),
                    @Parameter(name = "order", description = "排序字段"),
                    @Parameter(name = "orderby", description = "排序规则字段")
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功",
                            content = @Content(examples = {
                                    @ExampleObject(name = "id", description = "计划ID"),
                                    @ExampleObject(name = "plan_code", description = "计划编号"),
                                    @ExampleObject(name = "department_id", description = "部门编号"),
                                    @ExampleObject(name = "repair_type_id", description = "维修类型编号"),
                                    @ExampleObject(name = "person_main_liable_id", description = "主体责任人编号"),
                                    @ExampleObject(name = "person_main_liable_name", description = "主体责任人"),
                                    @ExampleObject(name = "person_profession_liable_id", description = "专业责任人编号"),
                                    @ExampleObject(name = "person_profession_liable_name", description = "专业责任人"),
                                    @ExampleObject(name = "equipment_number", description = "设备编号"),
                                    @ExampleObject(name = "equipment_name", description = "设备名称"),
                                    @ExampleObject(name = "plan_start_date", description = "计划开始"),
                                    @ExampleObject(name = "plan_end_date", description = "计划结束"),
                                    @ExampleObject(name = "real_start_date", description = "实际开始时间"),
                                    @ExampleObject(name = "real_end_date", description = "实际完成时间"),
                                    @ExampleObject(name = "plan_state", description = "计划完成状态"),
                                    @ExampleObject(name = "repair_content", description = "维修内容"),
                                    @ExampleObject(name = "is_affect_output", description = "是否影响产量"),
                                    @ExampleObject(name = "complete_user_id", description = "完成操作人id"),
                                    @ExampleObject(name = "complete_user_name", description = "完成操作人姓名"),
                                    @ExampleObject(name = "remark", description = "计划完成说明"),
                                    @ExampleObject(name = "remind_days", description = "预提醒天数"),
                                    @ExampleObject(name = "create_user_id", description = "创建人id"),
                                    @ExampleObject(name = "create_user_name", description = "创建人"),
                                    @ExampleObject(name = "create_time", description = "创建时间"),
                                    @ExampleObject(name = "modify_user_id", description = "修改人id"),
                                    @ExampleObject(name = "modify_user_name", description = "修改人"),
                                    @ExampleObject(name = "modify_time", description = "修改时间"),
                                    @ExampleObject(name = "size", description = "10"),
                                    @ExampleObject(name = "current", description = "1"),
                                    @ExampleObject(name = "organization_name", description = "部门名称")

                            })
                    ),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )

    @Log(title = "分页列表查询",businessType = BusinessType.OTHER)
    @RequestMapping(value = "/RepairPlan/list", method = RequestMethod.GET)
    public Object search(@Validated @ParamModel PageProductionPlanRepairDTO pageProductionPlanRepairDTO) {
        log.info("params => " + pageProductionPlanRepairDTO);
        String errorMsg = ValidUtil.checkValid(pageProductionPlanRepairDTO);
        if (!"".equals(errorMsg)) {
            return AjaxResult.error(errorMsg);
        }
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, pageProductionPlanRepairDTO);
        IPage<Map<String, String>> list = productionPlanRepairService.getListPage(pageProductionPlanRepairDTO);
        return AjaxResult.success(list);
    }

    @Operation(summary = "新增",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token")
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = {
                    @Content(examples = {
                            @ExampleObject(name = "plan_code", description = "计划编号"),
                            @ExampleObject(name = "department_id", description = "部门编号"),
                            @ExampleObject(name = "repair_type_id", description = "维修类型编号"),
                            @ExampleObject(name = "person_main_liable_id", description = "主体责任人编号"),
                            @ExampleObject(name = "person_main_liable_name", description = "主体责任人"),
                            @ExampleObject(name = "person_profession_liable_id", description = "专业责任人编号"),
                            @ExampleObject(name = "person_profession_liable_name", description = "专业责任人"),
                            @ExampleObject(name = "equipment_number", description = "设备编号"),
                            @ExampleObject(name = "equipment_name", description = "设备名称"),
                            @ExampleObject(name = "plan_start_date", description = "计划开始"),
                            @ExampleObject(name = "plan_end_date", description = "计划结束"),
                            @ExampleObject(name = "repair_content", description = "维修内容"),
                            @ExampleObject(name = "is_affect_output", description = "是否影响产量"),
                            @ExampleObject(name = "remind_days", description = "预提醒天数"),
                            @ExampleObject(name = "create_user_id", description = "创建人id"),
                            @ExampleObject(name = "create_user_name", description = "创建人")
                    })
            }),
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败[具体原因]"),
                    @ApiResponse(responseCode = "0", description = "成功"),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "新增",businessType = BusinessType.INSERT)
    @RequestMapping(value = "/RepairPlan/add", method = RequestMethod.POST, produces = "application/json")
    public Object insert(@Valid @RequestBody AddProductionPlanRepairDTO addProductionPlanRepairDTO) {
        log.info("params => " + addProductionPlanRepairDTO);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, addProductionPlanRepairDTO);
        return productionPlanRepairService.insert(addProductionPlanRepairDTO);
    }

    @Operation(summary = "修改",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token")
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = {
                    @Content(examples = {
                            @ExampleObject(name = "id", description = "计划ID"),
                            @ExampleObject(name = "department_id", description = "部门编号"),
                            @ExampleObject(name = "repair_type_id", description = "维修类型编号"),
                            @ExampleObject(name = "person_main_liable_id", description = "主体责任人编号"),
                            @ExampleObject(name = "person_main_liable_name", description = "主体责任人"),
                            @ExampleObject(name = "person_profession_liable_id", description = "专业责任人编号"),
                            @ExampleObject(name = "person_profession_liable_name", description = "专业责任人"),
                            @ExampleObject(name = "equipment_number", description = "设备编号"),
                            @ExampleObject(name = "equipment_name", description = "设备名称"),
                            @ExampleObject(name = "plan_start_date", description = "计划开始"),
                            @ExampleObject(name = "plan_end_date", description = "计划结束"),
                            @ExampleObject(name = "repair_content", description = "维修内容"),
                            @ExampleObject(name = "is_affect_output", description = "是否影响产量"),
                            @ExampleObject(name = "remind_days", description = "预提醒天数"),
                            @ExampleObject(name = "modify_user_id", description = "修改人id"),
                            @ExampleObject(name = "modify_user_name", description = "修改人")
                    })
            }),
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败[具体原因]"),
                    @ApiResponse(responseCode = "0", description = "成功"),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "修改",businessType = BusinessType.UPDATE)
    @RequestMapping(value = "/RepairPlan/edit", method = RequestMethod.POST, produces = "application/json")
    public Object update(@Valid @RequestBody UpdateProductionPlanRepairDTO updateProductionPlanRepairDTO) {
        log.info("params => " + updateProductionPlanRepairDTO);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, updateProductionPlanRepairDTO);
        return productionPlanRepairService.update(updateProductionPlanRepairDTO);
    }

    @Operation(summary = "删除",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token")
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = {
                    @Content(examples = {
                            @ExampleObject(name = "id", description = "计划ID")
                    })
            }),
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败[具体原因]"),
                    @ApiResponse(responseCode = "0", description = "成功"),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "删除",businessType = BusinessType.DELETE)
    @RequestMapping(value = "/RepairPlan/delete", method = RequestMethod.POST, produces = "application/json")
    public Object delete(@Valid @RequestBody DeleteProductionPlanRepairDTO deleteProductionPlanRepairDTO) {
        log.info("params => " + deleteProductionPlanRepairDTO);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, deleteProductionPlanRepairDTO);
        ProductionPlanRepair productionPlanRepair = productionPlanRepairService.getById(deleteProductionPlanRepairDTO.getId());
        if (null != productionPlanRepair) {
            if (DateUtil.compare(new Date(), productionPlanRepair.getPlanStartDate()) > 0 || productionPlanRepair.getPlanState() == 1) {
                return AjaxResult.error("计划已经开始或完成，不能删除");
            }
            HumanresourceOrganization humanresourceOrganization = humanresourceOrganizationService.getById(productionPlanRepair.getDepartmentId());
            String repairTypeName = productionPlanRepair.getRepairTypeId() == 1 ? "大修" :
                    productionPlanRepair.getRepairTypeId() == 2 ? "中修" :
                    productionPlanRepair.getRepairTypeId() == 3 ? "小修" :
                    productionPlanRepair.getRepairTypeId() == 4 ? "临修" :
                    productionPlanRepair.getRepairTypeId() == 5 ? "设备消缺" :
                    productionPlanRepair.getRepairTypeId() == 6 ? "工艺技改" :
                    productionPlanRepair.getRepairTypeId() == 7 ? "零星修缮" :
                    productionPlanRepair.getRepairTypeId() == 8 ? "环保项目" : "";
            StringBuilder content = new StringBuilder();
            content.append("删除：" + "[计划编号：").append(productionPlanRepair.getPlanCode()).append("],")
                    .append("[开始时间：").append(productionPlanRepair.getPlanStartDate()).append("],")
                    .append("[部门：").append(humanresourceOrganization.getOrganizationName()).append("]")
                    .append("[结束时间：").append(productionPlanRepair.getPlanEndDate()).append("]")
                    .append("[设备名称：").append(productionPlanRepair.getEquipmentName()).append("]")
                    .append("[主体责任人：").append(productionPlanRepair.getPersonMainLiableName()).append("]")
                    .append("[维修类型：").append(repairTypeName).append("]")
                    .append("[专业责任人：").append(productionPlanRepair.getPersonProfessionLiableName()).append("]")
                    .append("[维修内容：").append(productionPlanRepair.getRepairContent()).append("]")
                    .append("[影响产量：").append(productionPlanRepair.getIsAffectOutput() == 1 ? "影响" : "未影响").append("]")
                    .append("[预提醒天数：").append(productionPlanRepair.getRemindDays()).append("]");
            InsertChangeLogDTO insertChangeLogDTO = new InsertChangeLogDTO();
            insertChangeLogDTO.setModuleName("生产管理");
            insertChangeLogDTO.setFunctionName("生产计划->维修计划");
            insertChangeLogDTO.setContent(content.toString());
            insertChangeLogDTO.setCreateUserId(deleteProductionPlanRepairDTO.getDeleteUserId());
            insertChangeLogDTO.setCreateUserName(deleteProductionPlanRepairDTO.getDeleteUserName());
            changeLogService.insertChangeLogData(insertChangeLogDTO);
            productionPlanRepairService.removeById(productionPlanRepair);
            return AjaxResult.success("设备维修计划删除成功");
        }
        else {
            return AjaxResult.error("设备维修计划不存在或已被删除");
        }
    }

    @Operation(summary = "完成操作",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token")
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = {
                    @Content(examples = {
                            @ExampleObject(name = "id", description = "计划ID"),
                            @ExampleObject(name = "real_start_date", description = "实际开始时间"),
                            @ExampleObject(name = "real_end_date", description = "实际完成时间"),
                            @ExampleObject(name = "complete_user_id", description = "完成操作人id"),
                            @ExampleObject(name = "complete_user_name", description = "完成操作人姓名"),
                            @ExampleObject(name = "remark", description = "计划完成说明")
                    })
            }),
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败[具体原因]"),
                    @ApiResponse(responseCode = "0", description = "成功"),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "完成操作",businessType = BusinessType.UPDATE)
    @RequestMapping(value = "/RepairPlan/complete", method = RequestMethod.POST, produces = "application/json")
    public Object close(@Valid @RequestBody FinishedProductionPlanRepairDTO finishedProductionPlanRepairDTO) {
        log.info("params => " + finishedProductionPlanRepairDTO);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, finishedProductionPlanRepairDTO);
        ProductionPlanRepair productionPlanRepair = productionPlanRepairService.getById(finishedProductionPlanRepairDTO.getId());
        if (null != productionPlanRepair) {
            if (productionPlanRepair.getPlanState() == 1) {
                return AjaxResult.error("计划已完成，不能操作");
            }
            productionPlanRepair.setPlanState(1);
            productionPlanRepair.setRealStartDate(DateUtil.parseDate(finishedProductionPlanRepairDTO.getRealStartDate()));
            productionPlanRepair.setRealEndDate(DateUtil.parseDate(finishedProductionPlanRepairDTO.getRealEndDate()));
            productionPlanRepair.setCompleteUserId(finishedProductionPlanRepairDTO.getCompleteUserId());
            productionPlanRepair.setCompleteUserName(finishedProductionPlanRepairDTO.getCompleteUserName());
            productionPlanRepair.setRemark(finishedProductionPlanRepairDTO.getRemark());
            productionPlanRepairService.updateById(productionPlanRepair);
            return AjaxResult.success("设备维修计划完成操作成功");
        }
        else {
            return AjaxResult.error("设备维修计划不存在");
        }
    }
}
